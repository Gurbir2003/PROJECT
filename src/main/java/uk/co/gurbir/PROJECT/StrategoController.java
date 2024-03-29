package uk.co.gurbir.PROJECT;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

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
    @FXML private ImageView B9_image_view;
    @FXML private ImageView B8_1_image_view;
    @FXML private ImageView B8_2_image_view;
    @FXML private ImageView B7_1_image_view;
    @FXML private ImageView B7_2_image_view;
    @FXML private ImageView B7_3_image_view;
    @FXML private ImageView B6_1_image_view;
    @FXML private ImageView B6_2_image_view;
    @FXML private ImageView B6_3_image_view;
    @FXML private ImageView B6_4_image_view;
    @FXML private ImageView B5_1_image_view;
    @FXML private ImageView B5_2_image_view;
    @FXML private ImageView B5_3_image_view;
    @FXML private ImageView B5_4_image_view;
    @FXML private ImageView B4_1_image_view;
    @FXML private ImageView B4_2_image_view;
    @FXML private ImageView B4_3_image_view;
    @FXML private ImageView B4_4_image_view;
    @FXML private ImageView B3_1_image_view;
    @FXML private ImageView B3_2_image_view;
    @FXML private ImageView B3_3_image_view;
    @FXML private ImageView B3_4_image_view;
    @FXML private ImageView B3_5_image_view;
    @FXML private ImageView B2_1_image_view;
    @FXML private ImageView B2_2_image_view;
    @FXML private ImageView B2_3_image_view;
    @FXML private ImageView B2_4_image_view;
    @FXML private ImageView B2_5_image_view;
    @FXML private ImageView B2_6_image_view;
    @FXML private ImageView B2_7_image_view;
    @FXML private ImageView B2_8_image_view;
    @FXML private ImageView BS_image_view;
    @FXML private ImageView BB_1_image_view;
    @FXML private ImageView BB_2_image_view;
    @FXML private ImageView BB_3_image_view;
    @FXML private ImageView BB_4_image_view;
    @FXML private ImageView BB_5_image_view;
    @FXML private ImageView BB_6_image_view;
    @FXML private ImageView BF_image_view;





    
    private ImageView[] player_images;
    
    private Map<ImageView, Position> imagePositions = new HashMap<>();

    private static class Position {
        int row;
        int column;

        Position(int row, int column) {
            this.row = row;
            this.column = column;
        }
    }
    
    private ImageView draggedImageView = null;
    
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
        
        setupPlayerImageArray();
        
        for(ImageView image: player_images) {
        	System.out.println(image.getId());
        	setupDraggablePiece(image);
        }
        
        map_grid_pane.setOnDragOver(event -> {
            if (event.getGestureSource() != map_grid_pane && event.getDragboard().hasImage()) {
                event.acceptTransferModes(TransferMode.MOVE);
            }
            event.consume();
        });

        map_grid_pane.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasImage() && draggedImageView != null) {
                double cellWidth = 80;
                double cellHeight = 80;
                int newColumn = (int) event.getX() / (int) cellWidth;
                int newRow = (int) event.getY() / (int) cellHeight;

                map_grid_pane.getChildren().remove(draggedImageView);
                
                draggedImageView.setFitWidth(cellWidth);
                draggedImageView.setFitHeight(cellHeight);

                map_grid_pane.add(draggedImageView, newColumn, newRow);
                imagePositions.put(draggedImageView, new Position(newRow, newColumn));
                
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
        });
    }
    
    
    private void setupDraggablePiece(ImageView imageView) {
        imageView.setOnDragDetected(event -> {
            draggedImageView = imageView;
            Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
            ClipboardContent content = new ClipboardContent();
            content.putImage(imageView.getImage());
            db.setContent(content);
            event.consume();
        });

        imageView.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                draggedImageView = null;
            }
            event.consume();
        });
        System.out.println("Piece id: " + imageView.getId());
    }
    
    private void setupPlayerImageArray() {
        player_images = new ImageView[40];
        player_images[0] = B10_image_view;
        player_images[1] = B9_image_view;
        player_images[2] = B8_1_image_view;
        player_images[3] = B8_2_image_view;
        player_images[4] = B7_1_image_view;
        player_images[5] = B7_2_image_view;
        player_images[6] = B7_3_image_view;
        player_images[7] = B6_1_image_view;
        player_images[8] = B6_2_image_view;
        player_images[9] = B6_3_image_view;
        player_images[10] = B6_4_image_view;
        player_images[11] = B5_1_image_view;
        player_images[12] = B5_2_image_view;
        player_images[13] = B5_3_image_view;
        player_images[14] = B5_4_image_view;
        player_images[15] = B4_1_image_view;
        player_images[16] = B4_2_image_view;
        player_images[17] = B4_3_image_view;
        player_images[18] = B4_4_image_view;
        player_images[19] = B3_1_image_view;
        player_images[20] = B3_2_image_view;
        player_images[21] = B3_3_image_view;
        player_images[22] = B3_4_image_view;
        player_images[23] = B3_5_image_view;
        player_images[24] = B2_1_image_view;
        player_images[25] = B2_2_image_view;
        player_images[26] = B2_3_image_view;
        player_images[27] = B2_4_image_view;
        player_images[28] = B2_5_image_view;
        player_images[29] = B2_6_image_view;
        player_images[30] = B2_7_image_view;
        player_images[31] = B2_8_image_view;
        player_images[32] = BS_image_view;
        player_images[33] = BB_1_image_view;
        player_images[34] = BB_2_image_view;
        player_images[35] = BB_3_image_view;
        player_images[36] = BB_4_image_view;
        player_images[37] = BB_5_image_view;
        player_images[38] = BB_6_image_view;
        player_images[39] = BF_image_view;
    }
}
