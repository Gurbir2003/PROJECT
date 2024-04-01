#CHANGELOG

## [V0.2.1]
### Added
- Game class removed as it was for the game logic and not required anymore for the game GUI
- Board now handles all board movements and rules within the game
- Refactoring and cleaning of classes Board and StrategoController

### Modified
- Board now that care of checking if water cell rather than Controller.

### Deleted
- Game class
- Stratego class
- Strategy Design Pattern


## [V0.2.0]
### Added
- GUI improvement with auto placement of pieces for the AI using a thread
- GUI improvement with creation of a group of threads destroyed on application ending
- Link between Model (Game) and View (FXML) using the Controller
- Piece placement on both logical and visual board
- Piece movement on both logical and visual board
- Added a Pane on each GridPane cell for highlighting them when user select a piece
- Highlight of cell that can be reached by a player movement.
- Added 2 Enum's (AttackStatus, MoveStatus)

### Modified
- Timer class now handling the timer thread and not the Controller anymore.
- Setup of ImageView array refactored to follow the DRY principle.
- Board class now using the two Enum's.

## [V0.1.8]
- GUI improvement with auto placement of pieces for the player
- GUI improvement with swap of pieces possible
- GUI improvement with player able to start the game when all pieces placed
- GUI improvement with water displayed in the centre of the board
- GUI improvement with impossibility to place a player piece out of the boundary of the player part of the board
- GUI improvement with a start and reset button

## [V0.1.7]
### Added
- Code so that pieces can now be drag and dropped.

## [V0.1.6]
### Added
- FXML and CSS code for updated UI design.
- Game pieces and rules implementation on the game board.

## [V0.1.5]
### Added
- Initial board display layout draft with new graphic elements.
- New images for board display and game pieces.

### Changed
- Added new CSS code for enhanced styling and layout.

## [V0.1.4]
### Added
- New FXML code for additional UI components.

## [V0.1.3]
### Added
- New functionalities for improved game mechanics.

## [V0.1.2]
### Added
- Code to display size panels for functionalities.

### Changed
- Initial board layout/design using JavaFX and SceneBuilder for a more intuitive interface.

## [V0.1.1]
### Fixed
- Major issue that prevented project progress. Resolved underlying bug affecting game logic.

## [V0.1.0]
### Changed
- General code merge with refactoring and consolidation of features.


## [V0.0.15]
### Added
- Code to `board` and `game` class for movement constraints and player turn logic.


## [V0.0.14]
### Changed
- Updated `readme.md` with additional documentation and setup instructions.
- Refactored `Game` class and added piece attack functionality.
- Updated `Diary.txt` with project notes and development diary.

### Added
- Comprehensive JavaDoc for various classes enhancing code readability and maintainability.

## [V0.0.13]
### Changed
- Edited `stratego` class to enable piece movement on the board.

## [V0.0.12]
### Changed
- Edited `Readme.md` to include application running instructions.

### Added
- Comments for `BoardTest` class and test suites for game logic verification.

## [V0.0.11]
### Added
- New tests for piece interactions, especially with special pieces like FLAG, SCOUT, SERGEANT, MAJOR, COLONEL, GENERAL.

### Fixed
- Errors in `board` and `timer` classes affecting game timing and other errors.

### Changed
- Edited `stratego` class to better display the physical game board.

## [V0.0.10]
### Added
- Code to `stratego.java` and `piecetype.java` for rank demonstrations within the game logic.

## [V0.0.9]
### Added
- Creation and implementation of the `board` class for the game's core logic.

## [V0.0.8]
### Added
- Piece class to encompass `PieceType` and `PieceColor`.
- Colour class for representation of team colours in the game.
- Square classes for the board structure and game play functionality.

### Changed
- Added and improved tests for piece combat logic.

## [V0.0.7]
### Added
- Test implementations for piece rank and naming, employing TDD methodologies.

## [V0.0.6]
### Changed
- Updated `pom.xml` for project build enhancements.

## [V0.0.5]
### Tested
- Concurrency and thread safety within the game's engine.

## [V0.0.4]
### Changed
- Updated `Diary.txt` and documented the installation of SceneBuilder for UI design.

## [V0.0.3]
### Added
- Initial project setup with files and configurations.

## [V0.0.2]
### Added
- Addition of `Diary.txt` for project tracking and notes.

## [V0.0.1]
### Added
- Initial commit to establish the repository.
