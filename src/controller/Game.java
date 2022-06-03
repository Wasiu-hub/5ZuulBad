package controller;

import model.Command;
import model.Player;
import model.Room;
import util.CommandWords;
import util.Parser;

/**
 * This class is the main class of the "World of Zuul" application. "World of
 * Zuul" is a very simple, text based adventure game. Users can walk around some
 * scenery. That's all. It should really be extended to make it more
 * interesting!
 * 
 * To play this game, create an instance of this class and call the "play"
 * method.
 * 
 * This main class creates and initialises all the others: it creates all rooms,
 * creates the parser and starts the game. It also evaluates and executes the
 * commands that the parser returns.
 * 
 * @author Michael Kolling and David J. Barnes
 * @version 2011.07.31
 */

public class Game {
	private Parser parser;
	private Room currentRoom;
	private Player player;

	/**
	 * The main method: this is what happens first when the program is run. This
	 * just creates a new Game object, then plays the game.
	 */
	public static void main(String[] args) {
		Game theGame = new Game();
		theGame.play();
	}

	/**
	 * Create the game and initialise its internal map.
	 */
	public Game() {
		createRooms();
		parser = new Parser();
		player = new Player();
	}

	/**
	 * Create all the rooms and link their exits together.
	 */
	private void createRooms() {
		Room outside, theater, pub, lab, office, tree; // variable for each destination

		// create the rooms(above variables) with descriptions below
		// and link them together
		outside = new Room("outside the main entrance of the university");
		theater = new Room("in a lecture theater");
		pub = new Room("in the campus pub");
		lab = new Room("in a computing lab");
		office = new Room("in the computing admin office");
		// tree created here corresponds to lecture note page 13
		tree = new Room ("a huge old oak tree");

		// initialise room exits 
		// below more readable way of creating the map
		// outside.setExits(null, theater, lab, pub);
		outside.setExit("east", theater);
		outside.setExit("south", lab);
		outside.setExit("west", pub);
		outside.setExit("up", tree);
		tree.setExit("down", outside);

		// theater.setExit(null, null, null, outside);
		theater.setExit("west", outside);
		
		// pub.setExits(null, outside, null, null);
		pub.setExit("west", outside);
		
		// lab.setExits(outside, office, null, null);
		lab.setExit("north", outside);
		lab.setExit("east", office);
		
		// office.setExits(null, null, null, lab);
		office.setExit("west", lab);

		currentRoom = outside; // start game outside
	}

	/**
	 * Main play routine. Loops until end of play.
	 */
	public void play() {
		printWelcome();

		// Enter the main command loop. Here we repeatedly read commands and
		// execute them until the game is over.

		boolean finished = false;
		while (!finished) {
			Command command = parser.getCommand();
			finished = processCommand(command);
			System.out.println(player);
		}
		System.out.println("Thank you for playing.  Good bye.");
	}

	/**
	 * Print out the opening message for the player.
	 */
	private void printWelcome() {
		System.out.println();
		System.out.println("Welcome to the World of Zuul!");
		System.out.println("World of Zuul is a new, incredibly boring adventure game.");
		System.out.println("Type 'help' if you need help.");
		System.out.println();
		look();
	}

	/**
	 * Given a command, process (that is: execute) the command.
	 * 
	 * @param command The command to be processed.
	 * @return true If the command ends the game, false otherwise.
	 */
	private boolean processCommand(Command command) {
		boolean wantToQuit = false;

		if (command.isUnknown()) {
			System.out.println("I don't know what you mean...");
			return false;
		}

		String commandWord = command.getCommandWord();
		if (commandWord.equals("help")) {
			printHelp();
		} else if (commandWord.equals("go")) {
			goRoom(command);
		} else if (commandWord.equals("quit")) {
			wantToQuit = quit(command);
		} else if(commandWord.equals("look")) {
			look();
		}

		return wantToQuit;
	}
	
	public void look() { // tells us where we are and where we can go
		System.out.println("You are " + currentRoom.getDescription());
		System.out.print("Exits: ");

		String destinations = currentRoom.getExit();

		System.out.println(destinations); // printing where we can go

		System.out.println();
	}

	// implementations of user commands:

	/**
	 * Print out some help information. Here we print some stupid, cryptic message
	 * and a list of the command words.
	 */
	private void printHelp() {
		System.out.println("You are lost. You are alone. You wander");
		System.out.println("around at the university.");
		System.out.println();
		System.out.println("Your command words are:");
		//System.out.println("   go quit help"); // commented out to solve 
		// the problem explained on lecture not page 14, point 3
		System.out.println(CommandWords.listCommands());
		
	}

	/**
	 * Try to go in one direction. If there is an exit, enter the new room,
	 * otherwise print an error message.
	 */
	private void goRoom(Command command) {
		if (!command.hasSecondWord()) {
			// if there is no second word, we don't know where to go...
			System.out.println("Go where?");
			return;
		}

		String direction = command.getSecondWord();

		// Try to leave current room.
		Room nextRoom = null;
		nextRoom = currentRoom.getExit(direction); // giving exit in this direction

		if (nextRoom == null) {
			System.out.println("There is no door!");
		} else {
			currentRoom = nextRoom;
			look();
		}
	}

	/**
	 * "Quit" was entered. Check the rest of the command to see whether we really
	 * quit the game.
	 * 
	 * @return true, if this command quits the game, false otherwise.
	 */
	private boolean quit(Command command) {
		if (command.hasSecondWord()) {
			System.out.println("Quit what?");
			return false;
		} else {
			return true; // signal that we want to quit
		}
	}
}

// OUTPUT IN CONSOLE BELOW
/**
Welcome to the World of Zuul!
World of Zuul is a new, incredibly boring adventure game.
Type 'help' if you need help.

You are outside the main entrance of the university
Exits: east south west up 

> look
You are outside the main entrance of the university
Exits: east south west up 

model.Player@3d24753a
>  */
