package com.thomasci.tetros;

public class Globals {
	private static int coins = 0;
	private static int maxCoins = 500;
	public static int renderCount = 0;
	public static int chickens = 0;
	
	public static void changeCoins(int amount) {
		coins += amount;
		if (coins < 0) coins = 0;
		else if (coins > maxCoins) coins = maxCoins;
	}
	
	public static int getCoins() {
		return coins;
	}
	
	public static void changeMaxCoins(int amount) {
		maxCoins += amount;
		if (maxCoins < 0) maxCoins = 0;
		if (coins > maxCoins) coins = maxCoins;
	}
	
	public static int getMaxCoins() {
		return maxCoins;
	}
}
