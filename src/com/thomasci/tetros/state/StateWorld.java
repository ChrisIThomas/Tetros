package com.thomasci.tetros.state;

import java.awt.Image;
import java.util.ArrayList;

import com.thomasci.tetros.Controller;
import com.thomasci.tetros.Game;
import com.thomasci.tetros.Globals;
import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityChicken;
import com.thomasci.tetros.entity.EntityParticle;
import com.thomasci.tetros.entity.EntityPlayer;
import com.thomasci.tetros.item.Item;
import com.thomasci.tetros.screen.DrawRegistry;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.screen.GameScreen;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class StateWorld extends GameState {
	private World world;
	private float viewX, viewY, cloudX, coinTime, chickenTime;
	private boolean paused = false;
	private Image tiles, items, clouds, gui;
	private DrawRegistry entityDrawer;
	private Controller controller;
	
	public StateWorld() {
		world = new World(this, 256, 48);
		tiles = GameImages.getImage("tiles");
		items = GameImages.getImage("items");
		clouds = GameImages.getImage("clouds");
		gui = GameImages.getImage("gui");
		entityDrawer = new DrawRegistry();
		entityDrawer.registerBaseEntities();
		coinTime = 10.0f;
		chickenTime = 1.0f;
	}
	
	@Override
	public void initialize() {
		
	}
	
	@Override
	public void finalize() {
		
	}
	
	public void setController(Controller c) {
		controller = c;
	}
	
	public Controller getController() {
		return controller;
	}
	
	public World getWorld() {
		return world;
	}
	
	public void pause() {
		paused = true;
	}
	
	public void unpause() {
		paused = false;
	}
	
	@Override
	public void tick(float dt) {
		if (!paused) {
			chickenTime -= dt;
			if (chickenTime <= 0) {
				if (Globals.chickens < 15) {
					int x;
					while (true) {
						x = world.getRandom().nextInt(world.getWidth());
						if (world.distance(world.getPlayer().getX(), x) > GameScreen.instance().getImage().getViewWidth() / 2) {
							world.spawnEntity(new EntityChicken(world, x, world.getTopHeight(x) + 1));
							break;
						}
					}
				}
				chickenTime = 10.0f + world.getRandom().nextFloat() * 10.0f;
			}
			
			ArrayList<Entity> ents = world.getEntities();
			for (int i = 0; i < ents.size(); i++) {
				Entity e = ents.get(i);
				e.tick(dt);
				if (e.isDead()) {
					e.onKill();
					ents.remove(i);
					i--;
				}
			}
			
			ArrayList<EntityParticle> particles = world.getParticles();
			for (int i = 0; i < particles.size(); i++) {
				EntityParticle e = particles.get(i);
				e.tick(dt);
				if (e.isDead()) {
					particles.remove(i);
					i--;
				}
			}
			
			if (controller != null) {
				viewX = controller.getViewX();
				viewY = controller.getViewY();
			} else {
				viewX = 0;
				viewY = 0;
			}
			cloudX = (cloudX + dt) % (clouds.getWidth(null) * 2);
			
			coinTime -= dt;
			if (coinTime <= 0) {
				coinTime = 10.0f;
				Globals.changeCoins(1);
			}
		}
	}
	
	@Override
	public void draw(long time) {
		GameScreen screen = GameScreen.instance();
		
		int cloudWidth = clouds.getWidth(null);
		int cloudTiles = (int) Math.ceil(screen.getImage().getWidth() / (cloudWidth));
		for (int i = -1; i < cloudTiles; i++) {
			screen.getImage().drawImage(clouds, (int) cloudX + i * cloudWidth * 2, 0, cloudWidth * 2, clouds.getHeight(null) * 2, 0, 0, cloudWidth, clouds.getHeight(null));
		}
		
		int scrHeight = screen.getImage().getHeight();
		int viewHeight = (int) Math.ceil(screen.getImage().getHeight() / 16f);
		int viewWidth = screen.getImage().getViewWidth();
		int ix, iy;
		float viewX = this.viewX - screen.getImage().getWidth() / 32f + 0.5f;
		float viewY = this.viewY - scrHeight / 32f + 1;
		if (viewY + viewHeight >= world.getHeight())
			viewY = world.getHeight() - viewHeight;
		if (viewY < 0)
			viewY = 0;
		int ox = (int) ((viewX - (int) viewX) * 16);
		int oy = (int) ((viewY - (int) viewY) * 16);
		
		for (Entity e : world.getEntities()) {
			entityDrawer.drawEntity(e, this.viewX, (int) (viewX * 16), (int) (viewY * 16));
		}
		
		for (int y = 0; y < viewHeight; y++) {
			for (int x = -1; x < viewWidth; x++) {
				int vx = x + (int) viewX;
				int vy = y + (int) viewY;
				Tile t = world.getTileAt(vx, vy);
				int tx = vx % world.getWidth();
				if (tx < 0)
					tx += world.getWidth();
				ix = t.getIcon(world, tx, vy, time);
				iy = ix / 16 * 4;
				ix = ix % 16 * 4;
				screen.getImage().drawImage(tiles, x * 16 - ox, -y * 16 + scrHeight - 16 + oy, 16, 16, ix, iy, 4, 4);
			}
		}
		
		for (EntityParticle e : world.getParticles()) {
			entityDrawer.drawEntity(e, this.viewX, (int) (viewX * 16), (int) (viewY * 16));
		}
		
		EntityPlayer player = world.getPlayer();
		if (player.getHeldItem() != null) {
			Item item = player.getHeldItem();
			ix = item.getIcon();
			iy = ix / 16 * 4;
			ix = ix % 16 * 4;
			screen.getImage().drawImage(items, screen.getImage().getWidth() / 2 - 16, scrHeight - 80, 32, 32, ix, iy, 4, 4);
		}
		screen.getImage().drawImage(gui, screen.getImage().getWidth() / 2 - 64, scrHeight - 40, 128, 32, 0, 8, 32, 8);
		float healthWidth = player.getHealth() / player.getMaxHealth();
		screen.getImage().drawImage(gui,
				screen.getImage().getWidth() / 2 - 60, scrHeight - 36, (int) (120 * healthWidth), 24,
				0, 0, (int) (30 * healthWidth), 6);
	}
	
	@Override
	public void keyPress(int key) {
		if (key == 80) { // P
			if (paused)
				unpause();
			else
				pause();
		}
		if (!paused) {
			if (key == 27) { // ESC
				Game.instance().swapState(new StateQuit());
			} else if (key == 66) { // B
				if (controller == world.getPlayer() && !world.getPlayer().isDead())
					Game.instance().swapState(new StateBuy());
			}
			if (controller != null) {
				controller.keyPress(key);
			}
		}
	}
	
	@Override
	public void keyDown(int key, long time) {
		if (!paused) {
			if (controller != null) {
				controller.keyDown(key, time);
			}
		}
	}
	
	@Override
	public void keyRelease(int key) {
		if (!paused) {
			if (controller != null) {
				controller.keyRelease(key);
			}
		}
	}
	
	@Override
	public void mousePress(int btn, int x, int y) {}
	
	@Override
	public void mouseRelease(int btn, int x, int y) {}
	
	@Override
	public boolean drawWorld() {
		return false;
	}
	
	@Override
	public boolean pauseGame() {
		return false;
	}
}
