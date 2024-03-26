package uk.co.gurbir.PROJECT;

import java.io.IOException;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.input.DragEvent;
import model.Timer;

public class StrategoController {

    @FXML
    private Label timer_label;
    
    @FXML private ImageView B10_image_view;
    
    @FXML private GridPane map_grid_pane;

    @FXML
    public void initialize() {
        Timer timerRunnable = new Timer(0, 0, 0) {
            @Override
            public void run() {
                System.out.println("Thread started...");
                try {
                    while (!Thread.currentThread().isInterrupted()) {
                        setSeconds(getSeconds() + 1);
                        if (getSeconds() == 60) {
                            setSeconds(0);
                            setMinutes(getMinutes() + 1);
                            if (getMinutes() == 60) {
                                setMinutes(0);
                                setHours(getHours() + 1);
                            }
                        }
                        // Update the timer label on the JavaFX Application Thread
                        String time = this.toString();
                        Platform.runLater(() -> timer_label.setText(time));
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt(); // Restore the interrupted status
                    System.out.println("Timer thread interrupted");
                }
            }
        };
        Thread timerThread = new Thread(timerRunnable);
        timerThread.start(); // Start the timer thread
        
        B10_image_view.setOnDragDetected(event -> {
            Dragboard db = B10_image_view.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(B10_image_view.getImage());
            db.setContent(content);
            event.consume();
        });

        map_grid_pane.setOnDragOver(event -> {
            if (event.getGestureSource() != map_grid_pane && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        map_grid_pane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage()) {
                // Calculate the cell row and column based on the event's X and Y coordinates and the cell size
                double cellWidth = 100; // Assuming each cell is 100px wide
                double cellHeight = 100; // Assuming each cell is 100px tall

                // Get the X and Y coordinates of the drop relative to the GridPane
                double x = event.getX();
                double y = event.getY();

                int column = (int) x / (int) cellWidth;
                int row = (int) y / (int) cellHeight;

                // Add the image to the calculated cell
                ImageView droppedImage = new ImageView(db.getImage());
                map_grid_pane.add(droppedImage, column, row);
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });

        B10_image_view.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                B10_image_view.setImage(null); // Corrected to use B10_image_view
            }
            event.consume();
        });
    }
}
