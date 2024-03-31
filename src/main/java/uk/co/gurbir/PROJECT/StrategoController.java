package uk.co.gurbir.PROJECT;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import model.Timer;

public class StrategoController {
	
	private static final int CELL_WIDTH = 80;
	private static final int CELL_HEIGHT = 80;
	
	private boolean gameStarted = false;

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
        
        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return row == position.row && column == position.column;
        }

        @Override
        public int hashCode() {
            return Objects.hash(row, column);
        }
    }
    
    private ImageView draggedImageView = null;
    
    @FXML private GridPane map_grid_pane;
    
    @FXML private Button placement_button;
    @FXML private Button start_button;
    @FXML private Button reset_button;

    @FXML
    public void initialize() {
    	start_button.setText("Start");
    	start_button.setDisable(true);
        
        setupPlayerImageArray();
        
        for(ImageView image: player_images) {
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
                double cellWidth = CELL_WIDTH;
                double cellHeight = CELL_HEIGHT;
                int newColumn = (int) event.getX() / (int) cellWidth;
                int newRow = (int) event.getY() / (int) cellHeight;

                if (newRow >= map_grid_pane.getRowConstraints().size() - 4) {
                    Position newPosition = new Position(newRow, newColumn);
                    
                    if (!isPositionOccupied(newPosition) || isSwappingWithItself(newPosition)) {
                        moveImageViewToNewPosition(draggedImageView, newPosition);
                        success = true;
                    }
                    else {
	                    if (isPositionOccupied(newPosition)) {
	                        ImageView pieceAtNewPosition = getPieceAtPosition(newPosition);
	                        Position oldPosition = imagePositions.get(draggedImageView);
	
	                        if (oldPosition != null) {
	                            map_grid_pane.getChildren().removeAll(draggedImageView, pieceAtNewPosition);
	
	                            map_grid_pane.add(draggedImageView, newPosition.column, newPosition.row);
	                            map_grid_pane.add(pieceAtNewPosition, oldPosition.column, oldPosition.row);
	
	                            imagePositions.put(draggedImageView, newPosition);
	                            imagePositions.put(pieceAtNewPosition, oldPosition);
	                            success = true;
	                        }
	                    } else {
	                        map_grid_pane.getChildren().remove(draggedImageView);
	
	                        draggedImageView.setFitWidth(cellWidth);
	                        draggedImageView.setFitHeight(cellHeight);
	
	                        map_grid_pane.add(draggedImageView, newColumn, newRow);
	                        imagePositions.put(draggedImageView, newPosition);
	
	                        success = true;
	                    }
	
	                    if (success && areAllPiecesPlaced()) {
	                    	start_button.setDisable(false);
	                        System.out.println("All pieces have been placed. Game can start.");
	                    }
                    }
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });
        
        setupWaterCells();
    }
    
    
    private void setupDraggablePiece(ImageView imageView) {
        imageView.setOnDragDetected(event -> {
        	if (!gameStarted) {
	            draggedImageView = imageView;
	            Dragboard db = imageView.startDragAndDrop(TransferMode.MOVE);
	            ClipboardContent content = new ClipboardContent();
	            content.putImage(imageView.getImage());
	            db.setContent(content);
	            event.consume();
        	}
        });

        imageView.setOnDragDone(event -> {
            if (event.getTransferMode() == TransferMode.MOVE) {
                draggedImageView = null;
            }
            event.consume();
        });
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

    private boolean areAllPiecesPlaced() {
        return imagePositions.size() == 40;
    }
    
    private boolean isPositionOccupied(Position position) {
        return imagePositions.containsValue(position);
    }

    private ImageView getPieceAtPosition(Position position) {
        for (Map.Entry<ImageView, Position> entry : imagePositions.entrySet()) {
            if (entry.getValue().equals(position)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    
    @FXML
    void randomPlacement(ActionEvent event) {
        int minRow = map_grid_pane.getRowConstraints().size() - 4;
        int maxRow = map_grid_pane.getRowConstraints().size() - 1;
        int columns = map_grid_pane.getColumnConstraints().size();

        for (ImageView piece : player_images) {
            Position randomPosition;
            do {
                int randomRow = minRow + (int) (Math.random() * ((maxRow - minRow) + 1));
                int randomColumn = (int) (Math.random() * columns);
                randomPosition = new Position(randomRow, randomColumn);
            } while (isPositionOccupied(randomPosition));

            map_grid_pane.getChildren().remove(piece);
            
            piece.setFitWidth(CELL_WIDTH);
            piece.setFitHeight(CELL_HEIGHT);
            
            map_grid_pane.add(piece, randomPosition.column, randomPosition.row);
            imagePositions.put(piece, randomPosition);
        }

        if (areAllPiecesPlaced()) {
            System.out.println("All pieces have been randomly placed. Game can start.");
            placement_button.setDisable(true);
            start_button.setDisable(false);
        }
    }
    
    private boolean isSwappingWithItself(Position newPosition) {
        Position currentPosition = imagePositions.get(draggedImageView);
        return currentPosition != null && currentPosition.equals(newPosition);
    }

    private void moveImageViewToNewPosition(ImageView imageView, Position newPosition) {
        if (map_grid_pane.getChildren().contains(imageView)) {
            map_grid_pane.getChildren().remove(imageView);
        }
        GridPane.setColumnIndex(imageView, newPosition.column);
        GridPane.setRowIndex(imageView, newPosition.row);
        map_grid_pane.getChildren().add(imageView);
        imagePositions.put(imageView, newPosition);
    }
    
    private void setupWaterCells() {
        int[] waterRows = {4, 5}; // Rows for water cells
        int[] waterColumns = {2, 3, 6, 7}; // Columns for water cells

        for (int row : waterRows) {
            for (int col : waterColumns) {
                Pane waterPane = new Pane();
                waterPane.setStyle("-fx-background-color: #0000ff;");
                map_grid_pane.add(waterPane, col, row);
            }
        }
    }
    
    @FXML
    void startGame(ActionEvent event) {
    	gameStarted = true;
        startTimer();
        start_button.setDisable(true);
        System.out.println("Game started!");
    }
    
    @FXML
    void resetGame(ActionEvent event) {
    	System.out.println("Reset done");
    }
    
    private void startTimer() {
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
                    Thread.currentThread().interrupt();
                    System.out.println("Timer thread interrupted");
                }
            }
        };
        Thread timerThread = new Thread(timerRunnable);
        timerThread.start(); // Start the timer thread
    }
}
