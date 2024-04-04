package model;
/**
 * Enumerates the possible outcomes of attempting to move a piece on the game board.
 * This enumeration helps in identifying the result of a move action, which can be used for feedback to the player, game logic decisions, and updating the game state.
 */

public enum MoveStatus {
	
	INVALID_DIRECTION, OUT_OF_BOUND, SIMPLE, ATTACK, FLAG_CAPTURE, INVALID_SCOUT_MOVEMENT, ERROR;

}
