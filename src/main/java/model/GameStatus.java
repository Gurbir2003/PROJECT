package model;
/**
 * Defines the possible states of a game.
 * 
 * - MENU: The game is at the main menu.
 * - RUNNING: The game is currently in progress.
 * - PAUSED: The game is paused.
 * - WON: The game has been won by the player.
 */

public enum GameStatus {
	MENU, RUNNING, PAUSED, WON;
}
