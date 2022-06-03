package model;

public class Player {

	private int score;
	private int health;
	private Room currentLocation; // as required in lecture note page 21
	
	public Player() {
		score = 0;
		health = 10;
		currentLocation = null;
	}
	
	/**
	 * @return the score
	 */
	public int getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(int score) {
		this.score = score;
	}
	/**
	 * @return the health
	 */
	public int getHealth() {
		return health;
	}
	/**
	 * @param health the health to set
	 */
	public void setHealth(int health) {
		this.health = health;
	}
	
	
	
	/**
	 * @return the currentLocation
	 */
	public Room getCurrentLocation() {
		return currentLocation;
	}

	/**
	 * @param currentLocation the currentLocation to set
	 */
	public void setCurrentLocation(Room currentLocation) {
		this.currentLocation = currentLocation;
	}

	public void move() { // corresponds to the instruction on lecture note page 20
		score = score + 1;
		health =  health - 2;
	}
	
	public String toString() {
		String description = "";
		description = description + "Score is " + score +"\n";
		description = description + "Health is " + health + "\n";
		return description;
	}
}


