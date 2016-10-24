package com.thomasci.tetros.state;

import java.awt.Color;
import java.awt.Image;
import java.util.ArrayList;

import com.thomasci.tetros.Game;
import com.thomasci.tetros.Globals;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;
import com.thomasci.tetros.screen.GameSounds;
import com.thomasci.tetros.shop.ShopItem;

public class StateBuy extends GameState {
	private Image image;
	private float pressTime;
	private int baseX, baseY;
	private int width = 316, height = 268;
	private int btnX = 0, btnY = 28;
	private int selectionX = 88, selectionY = 0;
	private int arrowsX = 284;
	private int selection = 0;
	private static final ArrayList<ShopItem> items = new ArrayList<ShopItem>();
	
	@Override
	public void initialize() {
		image = GameImages.getImage("stateBuy");
	}
	
	@Override
	public void finalize() {
		
	}
	
	@Override
	public void tick(float dt) {
		if (pressTime > 0) pressTime -= dt;
	}
	
	@Override
	public void draw(long time) {
		GameScreen screen = GameScreen.instance();
		baseX = (screen.getImage().getWidth() - width) / 2;
		baseY = (screen.getImage().getHeight() - height) / 2;
		screen.getImage().drawText("Coins:", baseX, baseY + 12, Color.WHITE);
		screen.getImage().drawText(Globals.getCoins() + "/" + Globals.getMaxCoins(), baseX + 12, baseY + 24, Color.WHITE);
		screen.getImage().drawImage(image, baseX + btnX, baseY + btnY, 80, 80, pressTime <= 0 ? 0 : 20, 0, 20, 20);
		int selOffset = selection - 2;
		if (selOffset >= items.size() - 4) selOffset = items.size() - 4;
		if (selOffset < 0) selOffset = 0;
		for (int i = 0; i < 4; i++) {
			screen.getImage().drawImage(image,
					baseX + selectionX, baseY + selectionY + i * 68, 192, 64,
					0, selOffset + i == selection ? 36 : 20, 48, 16);
			if (selOffset + i < items.size()) {
				ShopItem item = items.get(selOffset + i);
				int ix = item.getIconId();
				int iy = ix / 16 * 4;
				ix = ix % 16 * 4;
				screen.getImage().drawImage(item.getSourceImage(),
						baseX + selectionX + 16, baseY + selectionY + i * 68 + 12, 16, 16,
						ix, iy, 4, 4);
				screen.getImage().drawText("" + item.getPrice(),
						baseX + selectionX + 5, baseY + selectionY + i * 68 + 46,
						Color.WHITE);
				screen.getImage().drawText(item.getName(),
						baseX + selectionX + 48, baseY + selectionY + i * 68 + 30,
						Color.WHITE);
			}
		}
		screen.getImage().drawImage(image, baseX + btnX, baseY + btnY, 80, 80, pressTime <= 0 ? 0 : 20, 0, 20, 20);
		screen.getImage().drawImage(image, baseX + arrowsX, baseY, 32, 16,
				selection <= 2 ? 48 : 40, 0, 8, 4);
		screen.getImage().drawImage(image, baseX + arrowsX, baseY + height - 16, 32, 16,
				selOffset >= items.size() - 4 ? 48 : 40, 4, 8, 4);
	}
	
	@Override
	public void keyPress(int key) {
		if (key == 66 || key == 27) { // B
			Game.instance().swapToWorldState();
		} else if (key == 32) { //Space
			if (pressTime <= 0) {
				pressTime = 0.1f;
				Globals.changeCoins(1);
				GameSounds.getSound("coin").play();
			}
		} else if (key == 38) { //Up
			selection--;
			if (selection < 0) selection = 0;
			else GameSounds.getSound("btn").play();
		} else if (key == 40) { //Down
			selection++;
			if (selection >= items.size()) selection = items.size() - 1;
			else GameSounds.getSound("btn").play();
		} else if (key == 10) { //Return
			ShopItem item = items.get(selection);
			if (Game.isDebugging()) {
				item.purchase(Game.instance().getWorldState().getWorld());
				if (item.leaveShopAfterPurchase()) {
					Game.instance().swapToWorldState();
				}
				GameSounds.getSound("btn").play();
			} else if (Globals.getCoins() >= item.getPrice()) {
				Globals.changeCoins(-item.getPrice());
				item.purchase(Game.instance().getWorldState().getWorld());
				if (item.leaveShopAfterPurchase()) {
					Game.instance().swapToWorldState();
				}
				GameSounds.getSound("btn").play();
			}
		}
	}
	
	@Override
	public void keyDown(int key, long time) {}
	
	@Override
	public void keyRelease(int key) {}
	
	@Override
	public void mousePress(int btn, int x, int y) {
		if (x >= baseX + btnX && x < baseX + btnX + 80 &&
			y >= baseY + btnY && y < baseY + btnY + 80) {
			if (pressTime <= 0) {
				pressTime = 0.1f;
				Globals.changeCoins(1);
				GameSounds.getSound("coin").play();
			}
		}
	}
	
	@Override
	public void mouseRelease(int btn, int x, int y) {}
	
	@Override
	public boolean drawWorld() {
		return true;
	}
	
	@Override
	public boolean pauseGame() {
		return false;
	}
	
	public static void addShopItem(ShopItem i) {
		items.add(i);
	}
	
	public static void removeShopItem(ShopItem i) {
		items.remove(i);
	}
}
