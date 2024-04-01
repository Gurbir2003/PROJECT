package uk.co.gurbir.PROJECT;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import model.Board;
import model.Color;
import model.Piece;
import model.PieceType;
import model.Position;
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
    @FXML private ImageView B1_image_view;
    @FXML private ImageView B0_1_image_view;
    @FXML private ImageView B0_2_image_view;
    @FXML private ImageView B0_3_image_view;
    @FXML private ImageView B0_4_image_view;
    @FXML private ImageView B0_5_image_view;
    @FXML private ImageView B0_6_image_view;
    @FXML private ImageView B11_image_view;
    
    private ImageView[] player_images;
    
    private Map<ImageView, Position> playerPiecesPosition = new HashMap<>();
    private Map<ImageView, Position> aiPiecesPosition = new HashMap<>();
    
    private ImageView draggedImageView = null;
    private ImageView selectedPiece = null;
    
    @FXML private GridPane map_grid_pane;
    
    @FXML private Button player_placement_button;
    @FXML private Button ai_placement_button;
    @FXML private Button start_button;
    @FXML private Button reset_button;
    
    private List<Thread> threads = new ArrayList<>();
    
    private Timer timer;
    private Board board;
    private Map<ImageView, Piece> imageViewToPieceMap = new HashMap<>();

    @FXML
    public void initialize() {
    	start_button.setText("Start");
    	start_button.setDisable(true);
    	
    	board = new Board();
    	timer = new Timer();
        timer_label.textProperty().bind(timer.timeStringProperty());
        
        setupPlayerImageArray();
        setupImageViewToPieceMapping();
        setupBoard();

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
                    
                    if (!isPositionOccupiedByEither(newPosition) || isSwappingWithItself(newPosition)) {
                        moveImageViewToNewPosition(draggedImageView, newPosition);
                        draggedImageView.setFitWidth(CELL_WIDTH);
                        draggedImageView.setFitHeight(CELL_HEIGHT);
                        
                        Piece piece = imageViewToPieceMap.get(draggedImageView);
                        if (piece != null) {
                            board.placePiece(newRow, newColumn, piece);
                        }
                        
                        board.displayBoard();
                        
                        success = true;
                    }
                    else {
                    	if (isPositionOccupiedByEither(newPosition)) {
                    	    ImageView pieceAtNewPosition = getPieceAtPosition(newPosition);
                    	    pieceAtNewPosition.setFitWidth(CELL_WIDTH);
                    	    pieceAtNewPosition.setFitHeight(CELL_HEIGHT);
                    	    Position oldPosition = playerPiecesPosition.get(draggedImageView);

                    	    if (oldPosition != null) {
                    	        // Retrieve pieces for swap
                    	        Piece draggedPiece = imageViewToPieceMap.get(draggedImageView);
                    	        Piece targetPiece = imageViewToPieceMap.get(pieceAtNewPosition);

                    	        // Swap pieces on the model board
                    	        board.placePiece(newPosition.getRow(), newPosition.getColumn(), draggedPiece);
                    	        board.placePiece(oldPosition.getRow(), oldPosition.getColumn(), targetPiece);

                    	        // Update view
                    	        map_grid_pane.getChildren().removeAll(draggedImageView, pieceAtNewPosition);
                    	        map_grid_pane.add(draggedImageView, newPosition.getColumn(), newPosition.getRow());
                    	        map_grid_pane.add(pieceAtNewPosition, oldPosition.getColumn(), oldPosition.getRow());
                    	        playerPiecesPosition.put(draggedImageView, newPosition);
                    	        playerPiecesPosition.put(pieceAtNewPosition, oldPosition);

                    	        pieceAtNewPosition.setFitWidth(CELL_WIDTH);
                    	        pieceAtNewPosition.setFitHeight(CELL_HEIGHT);
                    	        
                    	        board.displayBoard();

                    	        success = true;
                    	    }
	                    } else {
	                        map_grid_pane.getChildren().remove(draggedImageView);
	
	                        draggedImageView.setFitWidth(CELL_WIDTH);
	                        draggedImageView.setFitHeight(CELL_HEIGHT);
	
	                        map_grid_pane.add(draggedImageView, newColumn, newRow);
	                        playerPiecesPosition.put(draggedImageView, newPosition);
	
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
        List<ImageView> imagesList = new ArrayList<>();
        
        // Use reflection to get all fields in the class
        Field[] fields = this.getClass().getDeclaredFields();
        
        for (Field field : fields) {
            if (ImageView.class.isAssignableFrom(field.getType())) {
                try {
                    field.setAccessible(true);
                    
                    // Add the ImageView to the list
                    if (!field.getName().equals("draggedImageView") && 
                    		!field.getName().equals("selectedPiece")) {
                    	ImageView imageView = (ImageView) field.get(this);
                    	imageView.setOnMouseClicked(this::handlePieceSelection);
                    	imagesList.add(imageView);
                    	playerPiecesPosition.put(imageView, new Position(-1, -1));
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        player_images = imagesList.toArray(new ImageView[0]);
    }


    private boolean areAllPiecesPlaced() {
        return playerPiecesPosition.size() == 40 && aiPiecesPosition.size() == 40;
    }
    
    private boolean isPositionOccupiedByEither(Position position) {
        return playerPiecesPosition.containsValue(position) || aiPiecesPosition.containsValue(position);
    }

    private ImageView getPieceAtPosition(Position position) {
        for (Map.Entry<ImageView, Position> entry : playerPiecesPosition.entrySet()) {
            if (entry.getValue().equals(position)) {
                return entry.getKey();
            }
        }
        return null;
    }
    
    private void setupImageViewToPieceMapping() {
        Field[] fields = this.getClass().getDeclaredFields();
        
        for (Field field : fields) {
            if (ImageView.class.isAssignableFrom(field.getType()) && field.getName().startsWith("B")) {
                try {
                    field.setAccessible(true);
                    ImageView imageView = (ImageView) field.get(this);
                    Piece piece = mapNameToPiece(field.getName());
                    if (piece != null) {
                        imageViewToPieceMap.put(imageView, piece);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    
    private Piece mapNameToPiece(String fieldName) {
        // Remove the first character and anything after the underscore
        String numberStr = fieldName.substring(1).split("_")[0];
        
        try {
            int number = Integer.parseInt(numberStr);
            // Map the number to PieceType
            switch (number) {
                case 10:
                    return new Piece(PieceType.MARSHAL, Color.BLUE);
                case 9:
                    return new Piece(PieceType.GENERAL, Color.BLUE);
                case 8:
                    return new Piece(PieceType.COLONEL, Color.BLUE);
                case 7:
                    return new Piece(PieceType.MAJOR, Color.BLUE);
                case 6:
                    return new Piece(PieceType.CAPTAIN, Color.BLUE);
                case 5:
                    return new Piece(PieceType.LIEUTENANT, Color.BLUE);
                case 4:
                    return new Piece(PieceType.SERGEANT, Color.BLUE);
                case 3:
                    return new Piece(PieceType.MINER, Color.BLUE);
                case 2:
                    return new Piece(PieceType.SCOUT, Color.BLUE);
                case 0:
                    return new Piece(PieceType.BOMB, Color.BLUE);
                case 1:
                    return new Piece(PieceType.SPY, Color.BLUE);
                case 11:
                    return new Piece(PieceType.FLAG, Color.BLUE);
                default:
                    return null;
            }
        } catch (NumberFormatException e) {
            return null;
        }
    }

    
    
    @FXML
    void randomPlayerPlacement(ActionEvent event) {
        int minRow = map_grid_pane.getRowConstraints().size() - 4;
        int maxRow = map_grid_pane.getRowConstraints().size() - 1;
        int columns = map_grid_pane.getColumnConstraints().size();

        for (ImageView piece : player_images) {
            Position randomPosition;
            Piece modelPiece = imageViewToPieceMap.get(piece);
            do {
                int randomRow = minRow + (int) (Math.random() * ((maxRow - minRow) + 1));
                int randomColumn = (int) (Math.random() * columns);
                randomPosition = new Position(randomRow, randomColumn);
            } while (isPositionOccupiedByEither(randomPosition));

            // Place piece on UI
            map_grid_pane.getChildren().remove(piece);
            piece.setFitWidth(CELL_WIDTH);
            piece.setFitHeight(CELL_HEIGHT);
            map_grid_pane.add(piece, randomPosition.getColumn(), randomPosition.getRow());
            playerPiecesPosition.put(piece, randomPosition);

            // Place corresponding Piece on the model Board
            if (modelPiece != null) {
                board.placePiece(randomPosition.getRow(), randomPosition.getColumn(), modelPiece);
            }
            
            board.displayBoard();
        }

        if (areAllPiecesPlaced()) {
            System.out.println("All pieces have been randomly placed. Game can start.");
            start_button.setDisable(false);
        }
        player_placement_button.setDisable(true);
    }


    
    @FXML
    void randomAIPlacement(ActionEvent event) {
    	Thread aiPlacementThread = new Thread(() -> {
            int minRow = 0;
            int maxRow = 3;
            int columns = map_grid_pane.getColumnConstraints().size();
            Image redPieceImage = new Image(getClass().getResourceAsStream("images/R12.png"));

            for (int i = 0; i < 40; i++) {
                ImageView redPiece = new ImageView(redPieceImage);
                Platform.runLater(() -> {
                	Position randomPosition;
                    redPiece.setFitWidth(CELL_WIDTH);
                    redPiece.setFitHeight(CELL_HEIGHT);
                    do {
                        int randomRow = minRow + (int) (Math.random() * ((maxRow - minRow) + 1));
                        int randomColumn = (int) (Math.random() * columns);
                        randomPosition = new Position(randomRow, randomColumn);
                    } while (isPositionOccupiedByEither(randomPosition));

                    map_grid_pane.add(redPiece, randomPosition.getColumn(), randomPosition.getRow());
                    aiPiecesPosition.put(redPiece, randomPosition);
                });

                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    System.out.println("AI placement thread was interrupted");
                    break;
                }
            }
            if (areAllPiecesPlaced()) {
            	start_button.setDisable(false);
                System.out.println("All pieces have been placed. Game can start.");
            }
        });
        threads.add(aiPlacementThread);
        aiPlacementThread.start();
        ai_placement_button.setDisable(true);
    }

    
    private boolean isSwappingWithItself(Position newPosition) {
        Position currentPosition = playerPiecesPosition.get(draggedImageView);
        return currentPosition != null && currentPosition.equals(newPosition);
    }

    private void moveImageViewToNewPosition(ImageView imageView, Position newPosition) {
        if (map_grid_pane.getChildren().contains(imageView)) {
            map_grid_pane.getChildren().remove(imageView);
        }
        GridPane.setColumnIndex(imageView, newPosition.getColumn());
        GridPane.setRowIndex(imageView, newPosition.getRow());
        map_grid_pane.getChildren().add(imageView);
        playerPiecesPosition.put(imageView, newPosition);
    }

    private void setupBoard() {
        for (int row = 0; row < 10; row++) {
            for (int col = 0; col < 10; col++) {
                final int finalRow = row;
                final int finalCol = col;
                
                StackPane cellPane = new StackPane();
                cellPane.setPrefSize(CELL_WIDTH, CELL_HEIGHT);
                cellPane.setOnMouseClicked(event -> handleSquareClick(event, finalRow, finalCol));
                
                if (board.isWaterCell(new Position(finalRow, finalCol))) {
                	cellPane.setStyle("-fx-background-color: #0000ff;");
                }
                
                map_grid_pane.add(cellPane, col, row);
            }
        }
    }
    
    
    private void handlePieceSelection(MouseEvent event) {
        ImageView clickedPiece = (ImageView) event.getSource();
        
        // Check if it's the player's turn and the piece can be moved
        if (gameStarted && canPieceBeMoved(clickedPiece)) {
            if (selectedPiece != null) {
                //clearHighlightedMoves(); // Clear any previously highlighted moves
            	System.out.println("Piece selected");
            }
            selectedPiece = clickedPiece;
            highlightValidMoves(clickedPiece);
        }
    }
    
    
    private boolean canPieceBeMoved(ImageView clickedPiece) {
        Position pos = playerPiecesPosition.get(clickedPiece);
        return board.lookAround(pos.getRow(), pos.getColumn());
    }
    
    private void highlightValidMoves(ImageView piece) {
        Position piecePosition = playerPiecesPosition.get(piece);
        List<Position> validMoves = board.calculateValidMoves(piecePosition.getRow(), piecePosition.getColumn());
        
        for (Position move : validMoves) {
            StackPane cellPane = (StackPane) getNodeFromGridPane(map_grid_pane, move.getColumn(), move.getRow());
            cellPane.setStyle("-fx-background-color: lightgreen;");
        }
    }

    
    private Node getNodeFromGridPane(GridPane gridPane, int col, int row) {
        for (Node node : gridPane.getChildren()) {
            if (GridPane.getColumnIndex(node) != null && GridPane.getRowIndex(node) != null &&
                GridPane.getColumnIndex(node) == col && GridPane.getRowIndex(node) == row) {
                return node;
            }
        }
        return null;
    }
    
    private void movePieceInView(ImageView piece, Position newPosition) {
        map_grid_pane.getChildren().remove(piece);
        playerPiecesPosition.put(piece, newPosition);
        GridPane.setColumnIndex(piece, newPosition.getColumn());
        GridPane.setRowIndex(piece, newPosition.getRow());
        map_grid_pane.add(piece, newPosition.getColumn(), newPosition.getRow());
    }
    
    private void handleSquareClick(MouseEvent event, int row, int col) {
        if (selectedPiece == null) {
            return;
        }

        Position fromPosition = playerPiecesPosition.get(selectedPiece);
        Position toPosition = new Position(row, col);

        if (board.isValidMove(fromPosition, toPosition)) {
            // Perform the move in the model
            boolean moveSuccessful = board.movePiece(fromPosition.getRow(), fromPosition.getColumn(), toPosition.getRow(), toPosition.getColumn());

            if (moveSuccessful) {
                // Update the view
                movePieceInView(selectedPiece, toPosition);
                clearHighlightedMoves();
                selectedPiece = null; // Deselect the piece after moving

                board.displayBoard(); // For debugging, can be removed later
            } else {
                // Move was not successful; handle accordingly later
            }
        } else {
            // Move is invalid; possibly show some feedback to user
        }
    }

    
    
    private void clearHighlightedMoves() {
        for (Node node : map_grid_pane.getChildren()) {
            if (node instanceof StackPane) {
                StackPane cellPane = (StackPane) node;
               
                cellPane.setStyle("");
                
                Position pos = getPositionFromNode(cellPane);
                if (board.isWaterCell(pos)) {
                    cellPane.setStyle("-fx-background-color: #0000ff;");
                }
            }
        }
    }
    
    private Position getPositionFromNode(Node node) {
        Integer row = GridPane.getRowIndex(node);
        Integer col = GridPane.getColumnIndex(node);
        return new Position(row != null ? row : 0, col != null ? col : 0);
    }


    @FXML
    void startGame(ActionEvent event) {
    	gameStarted = true;
    	timer.start();
        start_button.setDisable(true);
        System.out.println("Game started!");
    }
    
    @FXML
    void resetGame(ActionEvent event) {
    	timer.stop();
    	System.out.println("Reset done");
    }
    
    
    public void shutdown() {
    	timer.stop();
        for (Thread thread : threads) {
            thread.interrupt();
        }
    }
}