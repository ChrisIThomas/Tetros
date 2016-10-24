package com.thomasci.tetros;

import com.thomasci.tetros.screen.GameScreen;

public class Launcher {
	public static void main(String[] args) {
		Game game = new Game();
		GameScreen.instance().createFrame("Tetros");
		game.initialize();
		game.run();
		game.finalize();
		System.exit(0);
	}
}
