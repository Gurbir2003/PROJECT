package uk.co.gurbir.PROJECT;
/**
 * Controls the gameplay logic for a Stratego game in a JavaFX application. 
 * Manages the game state, including player and AI piece placement, turn handling, and user interactions through a graphical interface.
 *
 * Key responsibilities include initialising the game state, handling drag-and-drop for piece placement, starting the game, and managing turns between the player and AI. 
 * It utilises a Board model to manage the game logic and a Timer for tracking gameplay duration.
 * The controller also handles user interactions such as piece selection, movement, and executing actions like attacking or moving a piece on the game board.
 *
 * Key Methods:
 * - initialise(): Sets up the game board and pieces for the start of the game.
 * - startGame(ActionEvent): Begins the game, enabling turn-based play.
 * - resetGame(ActionEvent): Resets the game to its initial state.
 * - showRules(ActionEvent): Displays the game rules to the player.
 * - shutdown(): Cleans up resources on application close.
 */


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.lang.reflect.Field;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import model.AttackStatus;
import model.Board;
import model.Color;
import model.MoveStatus;
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
    
    @FXML private ListView<String> history_listview;
    @FXML private ListView<String> red_listview;
    @FXML private ListView<String> blue_listview;
    
    private ImageView[] player_images;
    
    private Map<ImageView, Position> playerPiecesPosition = new HashMap<>();
    private Map<ImageView, Position> aiPiecesPosition = new HashMap<>();
    private Map<ImageView, Piece> imageViewToPieceMapPlayer = new HashMap<>();
    
    private Map<ImageView, ImageView> imageViewToCoverMapAI = new HashMap<>();
    
    private Image coverImage = new Image(getClass().getResourceAsStream("images/R12.png"));
    
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
    
    private boolean isPlayerTurn = true;
    
    private MessageService messageService = new MessageService();

    @FXML
    public void initialize() {
    	start_button.setText("Start");
    	start_button.setDisable(true);
    	
    	board = new Board(messageService);
    	timer = new Timer();
        timer_label.textProperty().bind(timer.timeStringProperty());
        
        setupPlayerImageArray();
        setupImageViewToPieceMapping();
        setupBoard();
        dragAndDropMapSetup();
        listenForMessages();
    }
    
    private void listenForMessages() {
    	messageService.messageProperty().addListener((obs, oldMsg, newMsg) -> {
            Platform.runLater(() -> history_listview.getItems().add(timer + ": " + newMsg));
            history_listview.scrollTo(history_listview.getItems().size() - 1);
        });
    }
    
    private void dragAndDropMapSetup() {
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
                        
                        Piece piece = imageViewToPieceMapPlayer.get(draggedImageView);
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
                    	        Piece draggedPiece = imageViewToPieceMapPlayer.get(draggedImageView);
                    	        Piece targetPiece = imageViewToPieceMapPlayer.get(pieceAtNewPosition);

                    	        board.placePiece(newPosition.getRow(), newPosition.getColumn(), draggedPiece);
                    	        board.placePiece(oldPosition.getRow(), oldPosition.getColumn(), targetPiece);

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
	                    	messageService.addMessage("All pieces have been placed. Game can start.");
	                    }
                    }
                }
            }

            event.setDropCompleted(success);
            event.consume();
        });
    }

    private void setupPlayerImageArray() {
        List<ImageView> imagesList = new ArrayList<>();

        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            if (ImageView.class.isAssignableFrom(field.getType())) {
                try {
                    field.setAccessible(true);
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
        for(ImageView image: player_images) {
        	setupDraggablePiece(image);
        }
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

    private boolean areAllPiecesPlaced() {
        return playerPiecesPosition.size() == 40 && aiPiecesPosition.size() == 40;
    }
    
    private boolean isPositionOccupiedByEither(Position position) {
        return playerPiecesPosition.containsValue(position) || 
        		aiPiecesPosition.containsValue(position);
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
                        imageViewToPieceMapPlayer.put(imageView, piece);
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private Piece mapNameToPiece(String fieldName) {
        String numberStr = fieldName.substring(1).split("_")[0];
        
        try {
            int number = Integer.parseInt(numberStr);

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
    
    @FXML
    void randomPlayerPlacement(ActionEvent event) {
    	Thread playerPlacementThread = new Thread(() -> {
            int minRow = map_grid_pane.getRowConstraints().size() - 4;
            int maxRow = map_grid_pane.getRowConstraints().size() - 1;
            int columns = map_grid_pane.getColumnConstraints().size();
            
            Collections.shuffle(Arrays.asList(player_images));
            
            for (ImageView piece : player_images) {
                Platform.runLater(() -> {
                    Position randomPosition;
                    do {
                        int randomRow = minRow + (int) (Math.random() * ((maxRow - minRow) + 1));
                        int randomColumn = (int) (Math.random() * columns);
                        randomPosition = new Position(randomRow, randomColumn);
                    } while (isPositionOccupiedByEither(randomPosition));
                    
                    piece.setFitWidth(CELL_WIDTH);
                    piece.setFitHeight(CELL_HEIGHT);
                    map_grid_pane.add(piece, randomPosition.getColumn(), randomPosition.getRow());
                    playerPiecesPosition.put(piece, randomPosition);

                    Piece modelPiece = imageViewToPieceMapPlayer.get(piece);
                    if (modelPiece != null) {
                        board.placePiece(randomPosition.getRow(), randomPosition.getColumn(), modelPiece);
                    }
                });
                
                try {
                    Thread.sleep(500); 
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    return; 
                }
            }
            
            Platform.runLater(() -> {
                if (areAllPiecesPlaced()) {
                	messageService.addMessage("All player pieces have been randomly placed.\nGame can start");
                    start_button.setDisable(false);
                }
            });

        });
    	threads.add(playerPlacementThread);
    	playerPlacementThread.start();
        player_placement_button.setDisable(true);
    }
    
    @FXML
    void randomAIPlacement(ActionEvent event) {
        Thread aiPlacementThread = new Thread(() -> {
            int minRow = 0;
            int maxRow = 3;
            int columns = map_grid_pane.getColumnConstraints().size();

            Map<PieceType, Integer> AIPiecesSet = board.getAiPlayer().getPieceSet();
            List<Piece> allAIPieces = generateAIPiecesFromMap(AIPiecesSet);
        

            int pieceIndex = 0;
            for (int row = minRow; row <= maxRow && pieceIndex < allAIPieces.size(); row++) {
                for (int col = 0; col < columns && pieceIndex < allAIPieces.size(); col++) {
                    if (!board.isWaterCell(new Position(row, col))) {
                    	final int finalRow = row;
                    	final int finalCol = col;
                        Piece currentPiece = allAIPieces.get(pieceIndex++);
                        final ImageView aiPieceImageView = new ImageView();
                        aiPieceImageView.setMouseTransparent(true);
                        aiPieceImageView.setFitWidth(CELL_WIDTH);
                        aiPieceImageView.setFitHeight(CELL_HEIGHT);
                        aiPieceImageView.setImage(getImageForPiece(currentPiece));
                        
                        final ImageView coverImageView = new ImageView(coverImage);
                        coverImageView.setMouseTransparent(true);
                        coverImageView.setFitWidth(CELL_WIDTH);
                        coverImageView.setFitHeight(CELL_HEIGHT);
                        
                        Platform.runLater(() -> {
                            map_grid_pane.add(aiPieceImageView, finalCol, finalRow);
                            aiPiecesPosition.put(aiPieceImageView, new Position(finalRow, finalCol));
                            board.placePiece(finalRow, finalCol, currentPiece);

                           
                            map_grid_pane.add(coverImageView, finalCol, finalRow);

                            
                            imageViewToCoverMapAI.put(aiPieceImageView, coverImageView);
                        });

                    
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            Thread.currentThread().interrupt();
                        }
                    }
                }
            }
            
            Platform.runLater(() -> {
                if (areAllPiecesPlaced()) {
                    start_button.setDisable(false);
                    messageService.addMessage("All pieces have been placed. Game can start.");
                }
            });
        });
        threads.add(aiPlacementThread);
        aiPlacementThread.start();
        ai_placement_button.setDisable(true);
    }


    private List<Piece> generateAIPiecesFromMap(Map<PieceType, Integer> allAIPieces) {
        List<Piece> pieces = new ArrayList<>();
        for (Map.Entry<PieceType, Integer> entry : allAIPieces.entrySet()) {
            PieceType type = entry.getKey();
            Integer count = entry.getValue();
            for (int i = 0; i < count; i++) {
                pieces.add(new Piece(type, Color.RED));
            }
        }
        return pieces;
    }
    
    private Image getImageForPiece(Piece aiPiece) {
    	String pieceName = "R" + String.format("%02d", aiPiece.getPieceType().getRank());
	    String imageName = "images/" + pieceName + ".png";
	    Image pieceImage = new Image(getClass().getResourceAsStream(imageName));
	    return pieceImage;
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

    private void handlePieceSelection(MouseEvent event) {
        ImageView clickedPiece = (ImageView) event.getSource();
        
        if (gameStarted && canPieceBeMoved(clickedPiece)) {
            if (selectedPiece != null) {
                clearHighlightedMoves();
            }
            selectedPiece = clickedPiece;
            highlightValidMoves(clickedPiece);
        }
    }
    
    private boolean canPieceBeMoved(ImageView clickedPiece) {
        Position pos = playerPiecesPosition.get(clickedPiece);
        Piece piece = imageViewToPieceMapPlayer.get(clickedPiece);

    
        if (piece.getPieceType() == PieceType.BOMB || piece.getPieceType() == PieceType.FLAG) {
            return false; // Bombs and Flags cannot move.
        }
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
        if (selectedPiece == null || !gameStarted) {
        	messageService.addMessage("No piece selected or game hasn't started.");
            return;
        }

        Position fromPosition = playerPiecesPosition.get(selectedPiece);
        Position toPosition = new Position(row, col);
        
        if (board.isEnemyPieceAt(toPosition, Color.BLUE)) {
        	handleAttackOutcome(fromPosition, toPosition);
        	aiMove();
        } 
        else if (board.isValidMove(fromPosition, toPosition)) {
        	MoveStatus moveStatus = board.movePiece(fromPosition.getRow(), fromPosition.getColumn(), toPosition.getRow(), toPosition.getColumn());
            if (moveStatus == MoveStatus.SIMPLE) {
                movePieceInView(selectedPiece, toPosition);
                clearHighlightedMoves();
                selectedPiece = null;
                toggleTurn();
                aiMove();
            }
        } else {
        	messageService.addMessage("Selected move is not an attack or invalid target.");
        }
        board.displayBoard();
    }
    
    private void handleAttackOutcome(Position fromPosition, Position toPosition) {
        Piece attackingPiece = imageViewToPieceMapPlayer.get(selectedPiece);
        Piece defendingPiece = board.getBoard()[toPosition.getRow()][toPosition.getColumn()].getPiece();

        if (defendingPiece.getPieceType().getName().equals("Flag")){
        	Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("WON");
            dialog.setHeaderText("You Won!! Well done!");
            ButtonType okButtonType = ButtonType.OK;
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
            String text = "You won the game! Congratulation :)";
            dialog.setContentText(text);
            dialog.showAndWait();
            shutdown();
        }
        
        if (attackingPiece == null || defendingPiece == null) {
        	messageService.addMessage("Invalid attack scenario.");
            return;
        }

        ImageView defenderImageView = findImageViewByPosition(toPosition, aiPiecesPosition);
        ImageView coverImageView = imageViewToCoverMapAI.get(defenderImageView);
        revealAIPieceTemporarily(defenderImageView, coverImageView);
        int attackResult = attackingPiece.attack(defendingPiece);
        board.movePiece(fromPosition.getRow(), fromPosition.getColumn(), toPosition.getRow(), toPosition.getColumn());

        switch (attackResult) {
            case 1: // Attacker wins.
            	updateListView(defenderImageView, defendingPiece);
                if (defenderImageView != null) {
                    map_grid_pane.getChildren().removeAll(defenderImageView, coverImageView);
                    aiPiecesPosition.remove(defenderImageView);
                    imageViewToCoverMapAI.remove(defenderImageView);
                }
                movePieceInView(selectedPiece, toPosition);
                messageService.addMessage("Attacker wins and moves to defender's position.");
                break;
            case -1: // Defender wins
            	updateListView(selectedPiece, attackingPiece);
                map_grid_pane.getChildren().remove(selectedPiece);
                playerPiecesPosition.remove(selectedPiece);
                imageViewToPieceMapPlayer.remove(selectedPiece);
                messageService.addMessage("Defender wins, attacker removed.");
                break;
            case 0: // Draw
            	updateListView(selectedPiece, attackingPiece);
                updateListView(defenderImageView, defendingPiece);
                map_grid_pane.getChildren().remove(selectedPiece);
                playerPiecesPosition.remove(selectedPiece);
                imageViewToPieceMapPlayer.remove(selectedPiece);
                if (defenderImageView != null) {
                    map_grid_pane.getChildren().removeAll(defenderImageView, coverImageView);
                    aiPiecesPosition.remove(defenderImageView);
                    imageViewToCoverMapAI.remove(defenderImageView);
                }
                messageService.addMessage("Both pieces are removed.");
                break;
        }
        clearHighlightedMoves();
        selectedPiece = null;
        toggleTurn();
    }


    
    private void toggleTurn() {
        isPlayerTurn = !isPlayerTurn;
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
    
    
    private void aiMove() {
        if (!isPlayerTurn) {
            Thread aiMoveThread = new Thread(() -> {
                try {
                    Thread.sleep(500);
                    Platform.runLater(() -> {
                        List<Position> movablePositions = getMovableAIPieces();
                        if (!movablePositions.isEmpty()) {
                            int randomIndex = (int) (Math.random() * movablePositions.size());
                            Position selectedPiecePosition = movablePositions.get(randomIndex);
                            List<Position> validMoves = board.calculateValidMoves(selectedPiecePosition.getRow(), selectedPiecePosition.getColumn());
                            if (!validMoves.isEmpty()) {
                                Position moveTo = validMoves.get((int) (Math.random() * validMoves.size()));
                                if (board.isEnemyPieceAt(moveTo, Color.RED)) {
                                	System.out.println("AI trying to attack!");
                                    handleAIAttackOutcome(selectedPiecePosition, moveTo);
                                } else if (board.isValidMove(selectedPiecePosition, moveTo)) {
                                    board.movePiece(selectedPiecePosition.getRow(), selectedPiecePosition.getColumn(), moveTo.getRow(), moveTo.getColumn());
                                    ImageView movingPieceView = findImageViewByPosition(selectedPiecePosition, aiPiecesPosition);
                                    ImageView coverView = imageViewToCoverMapAI.get(movingPieceView);
                                    if (movingPieceView != null) {
                                        updatePiecePositionInView(movingPieceView, coverView, selectedPiecePosition, moveTo);
                                    }
                                    toggleTurn();
                                    board.displayBoard();
                                }
                            }
                        }
                    });
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            });
            threads.add(aiMoveThread);
            aiMoveThread.start();
        }
    }

    private void updatePiecePositionInView(ImageView pieceView, ImageView coverView, Position fromPosition, Position toPosition) {
        aiPiecesPosition.put(pieceView, toPosition);
        if (coverView != null) {
            imageViewToCoverMapAI.put(pieceView, coverView); 
        }
        map_grid_pane.getChildren().removeAll(pieceView, coverView);
        map_grid_pane.add(pieceView, toPosition.getColumn(), toPosition.getRow());
        if (coverView != null) {
            map_grid_pane.add(coverView, toPosition.getColumn(), toPosition.getRow()); 
        }
    }

    
    private void handleAIAttackOutcome(Position fromPosition, Position toPosition) {
        Piece attackingPiece = board.getBoard()[fromPosition.getRow()][fromPosition.getColumn()].getPiece();
        Piece defendingPiece = board.getBoard()[toPosition.getRow()][toPosition.getColumn()].getPiece();
        
        if (defendingPiece.getPieceType().getName().equals("Flag")){
        	Dialog<String> dialog = new Dialog<>();
            dialog.setTitle("LOST");
            dialog.setHeaderText("You Lost!! Well done!");
            ButtonType okButtonType = ButtonType.OK;
            dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
            String text = "You lost the game! Try again :(";
            dialog.setContentText(text);
            dialog.showAndWait();
            shutdown();
        }

        if (attackingPiece == null || defendingPiece == null) {
        	messageService.addMessage("Invalid attack scenario for AI.");
            return;
        }

        ImageView attackerImageView = findImageViewByPosition(fromPosition, aiPiecesPosition);
        ImageView defenderImageView = findImageViewByPosition(toPosition, playerPiecesPosition);
        ImageView coverView = imageViewToCoverMapAI.get(attackerImageView);
        revealAIPieceTemporarily(attackerImageView, coverView);
        board.movePiece(fromPosition.getRow(), fromPosition.getColumn(), toPosition.getRow(), toPosition.getColumn());
        
        int attackResult = attackingPiece.attack(defendingPiece);

        switch (attackResult) {
            case 1: // Attacker (AI) wins
            	updateListView(defenderImageView, defendingPiece);
                if (defenderImageView != null) {
                    map_grid_pane.getChildren().remove(defenderImageView);
                    playerPiecesPosition.remove(defenderImageView);
                    imageViewToPieceMapPlayer.remove(defenderImageView);
                }
                ;
                if (attackerImageView != null) {
                	
                    updatePiecePositionInView(attackerImageView, coverView, fromPosition, toPosition);
                }
                
                messageService.addMessage("AI wins and moves to player's position.");
                break;
            case -1: // Defender (Player) wins
            	updateListView(attackerImageView, attackingPiece);
                if (attackerImageView != null) {
                	map_grid_pane.getChildren().removeAll(attackerImageView, coverView);
                    aiPiecesPosition.remove(attackerImageView);
                    imageViewToCoverMapAI.remove(attackerImageView);
                }
                messageService.addMessage("Player defends successfully, AI piece removed.");
                break;
            case 0: // Draw
            	updateListView(attackerImageView, attackingPiece);
                updateListView(defenderImageView, defendingPiece);
                if (attackerImageView != null) {
                	map_grid_pane.getChildren().removeAll(attackerImageView, coverView);
                    aiPiecesPosition.remove(attackerImageView);
                    imageViewToCoverMapAI.remove(attackerImageView);
                }
                if (defenderImageView != null) {
                    map_grid_pane.getChildren().remove(defenderImageView);
                    playerPiecesPosition.remove(defenderImageView);
                    imageViewToPieceMapPlayer.remove(defenderImageView);
                }
                messageService.addMessage("Both pieces are removed in a draw.");
                break;
        }
        selectedPiece = null;
        toggleTurn();
        clearHighlightedMoves();
    }

    private ImageView findImageViewByPosition(Position position, Map<ImageView, Position> positionMap) {
        for (Map.Entry<ImageView, Position> entry : positionMap.entrySet()) {
            if (entry.getValue().equals(position)) {
                return entry.getKey();
            }
        }
        return null;
    }

    private List<Position> getMovableAIPieces() {
        List<Position> movablePositions = new ArrayList<>();
        for (int row = 0; row <= 3; row++) {
            for (int col = 0; col < board.getNumCols(); col++) {
                Position position = new Position(row, col);
                Piece piece = board.getBoard()[row][col].getPiece();
                if (piece != null && piece.getPieceColor() == Color.RED &&
                        !(piece.getPieceType() == PieceType.BOMB || piece.getPieceType() == PieceType.FLAG) &&
                        board.lookAround(row, col)) {
                    movablePositions.add(position);
                }
            }
        }
        return movablePositions;
    }
    
    private void revealAIPieceTemporarily(ImageView aiPiece, ImageView cover) {
        Platform.runLater(() -> {
            map_grid_pane.getChildren().remove(cover);
        });

        PauseTransition pause = new PauseTransition(Duration.seconds(5));
        pause.setOnFinished(event -> Platform.runLater(() -> {
            Position position = aiPiecesPosition.get(aiPiece);
            Piece piece = null;
            if (position != null) {
            	piece = board.getBoard()[position.getRow()][position.getColumn()].getPiece();
            }
            if (piece != null && piece.getPieceColor() == Color.RED) {
                if (!map_grid_pane.getChildren().contains(cover) && aiPiecesPosition.containsKey(aiPiece)) {
                    map_grid_pane.add(cover, GridPane.getColumnIndex(aiPiece), GridPane.getRowIndex(aiPiece));
                }
            }
        }));
        pause.play();
    }
    
    private void updateListView(ImageView imageView, Piece piece) {
        String pieceName = piece.getPieceType().toString();
        Platform.runLater(() -> {
            if (piece.getPieceColor() == Color.RED) {
                red_listview.getItems().add(pieceName);
                red_listview.scrollTo(red_listview.getItems().size() - 1);
            } else if (piece.getPieceColor() == Color.BLUE) {
                blue_listview.getItems().add(pieceName);
                blue_listview.scrollTo(blue_listview.getItems().size() - 1);
            }
        });
    }
    
    @FXML
    private void showRules(ActionEvent event) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Stratego Rules");
        dialog.setHeaderText("Game Rules");
        ButtonType okButtonType = ButtonType.OK;
        dialog.getDialogPane().getButtonTypes().addAll(okButtonType);
        String rulesText = "Stratego is a strategy based game for two opposing players on a board of 10x10 squares. " +
                "Each player controls 40 pieces representing individual officer ranks in an army. " +
                "The objective of the game is to find and capture the opponent's Flag, or to capture so many enemy pieces " +
                "that the opponent cannot make any further moves.\n\n" +
                "Players cannot see the ranks of one another's pieces, so disinformation and discovery are important " +
                "facets to gameplay.";
        dialog.setContentText(rulesText);
        dialog.showAndWait();
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
        
        Platform.runLater(() -> {
            Platform.exit();
            System.exit(0);
        });
    }
}
