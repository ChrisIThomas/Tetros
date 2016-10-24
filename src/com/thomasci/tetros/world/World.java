package com.thomasci.tetros.world;

import java.util.ArrayList;
import java.util.Random;

import com.thomasci.tetros.entity.Entity;
import com.thomasci.tetros.entity.EntityDropPiece;
import com.thomasci.tetros.entity.EntityParticle;
import com.thomasci.tetros.entity.EntityPlayer;
import com.thomasci.tetros.state.StateWorld;
import com.thomasci.tetros.tile.Tile;

public class World {
	private final StateWorld state;
	private final int width, height, halfWidth;
	private final Tile[][] tiles;
	private final ArrayList<Entity> ents;
	private final ArrayList<EntityParticle> particles;
	private final Random rand = new Random();
	private EntityPlayer player;
	private EntityDropPiece piece;
	
	public World(StateWorld state, int width, int height) {
		this.state = state;
		this.width = width;
		this.halfWidth = width / 2;
		this.height = height;
		tiles = new Tile[height][width];
		ents = new ArrayList<Entity>();
		particles = new ArrayList<EntityParticle>();
		for (int i = 0; i < tiles.length; i++) {
			for (int j = 0; j < tiles[i].length; j++) {
				tiles[i][j] = Tile.AIR;
			}
		}
		int minHeight = 16;
		for (int h = 0; h < minHeight; h++) {
			for (int i = 0; i < width; i++) {
				tiles[h][i] = Tile.DIRT;
			}
		}
		
		int spacing = 9;
		int maxHeight = 6;
		int x = 0;
		int leftHeight = 0;
		int rightHeight = rand.nextInt(maxHeight);
		int firstHeight = rightHeight;
		for (int i = 0; i < width; i++) {
			if (i % spacing == 0) {
				leftHeight = rightHeight;
				if (i + spacing * 2 > width) rightHeight = firstHeight;
				else rightHeight = rand.nextInt(maxHeight);
				x = 0;
			}
			int h = (int) (leftHeight + (rightHeight - leftHeight) * x / spacing) + minHeight;
			int dirtH = rand.nextInt(3) + 8;
			for (int k = 0; k < h; k++) {
				if (k < h - dirtH) tiles[k][i] = Tile.STONE;
				else tiles[k][i] = Tile.DIRT;
			}
			tiles[h][i] = rand.nextInt(8) == 0 ? Tile.FLOWER : Tile.LONGGRASS;
			x++;
		}
		
		int playerHeight = height - 1;
		for (int y = playerHeight; y >= 0; y--) {
			if (!tiles[y][0].isAir()) {
				playerHeight = y + 1;
				break;
			}
		}
		player = new EntityPlayer(this, 0, playerHeight);
		spawnEntity(player);
		state.setController(player);
	}
	
	public void dropPiece(EntityDropPiece p) {
		if (piece != null) ents.remove(piece);
		piece = p;
		spawnEntity(piece);
		state.setController(piece);
	}
	
	public void setTileAt(int x, int y, Tile t) {
		if (y < 0 || y >= tiles.length) return;
		x = x % width;
		if (x < 0) x += width;
		if (t == null) t = Tile.AIR;
		tiles[y][x] = t;
		tiles[y][x].updateTile(this, x, y);
		if (y > 0) tiles[y - 1][x].updateTile(this, x, y - 1);
		if (y < height) tiles[y + 1][x].updateTile(this, x, y + 1);
		x = (x + 1) % width;
		tiles[y][x].updateTile(this, x, y);
		x -= 2;
		if (x < 0) x += width;
		tiles[y][x].updateTile(this, x, y);
	}
	
	public Tile getTileAt(int x, int y) {
		if (y < 0 || y >= tiles.length) return null;
		x = x % tiles[y].length;
		if (x < 0) x += tiles[y].length;
		return tiles[y][x];
	}
	
	public int getTopHeight(int x) {
		if (x < 0) x += width;
		x = x % width;
		for (int i = height - 1; i >= 0; i--) {
			if (!tiles[i][x].isAir()) return i;
		}
		return 0;
	}
	
	public void spawnEntity(Entity e) {
		ents.add(e);
		e.onSpawn();
	}
	
	public void spawnParticle(EntityParticle p) {
		particles.add(p);
		if (particles.size() > 512) particles.remove(0);
	}
	
	public Entity getEntityNear(float x, float y, float dist, Class<? extends Entity> type, Entity... exceptions) {
		dist *= dist;
		main: for (Entity e: ents) {
			if (!type.isAssignableFrom(e.getClass())) continue;
			for (Entity e2: exceptions) {
				if (e == e2) continue main;
			}
			if (sq(distance(x, e.getX())) + sq(y - e.getY()) <= dist) return e;
		}
		return null;
	}
	
	public Entity getEntityAt(float x, float y, float w, float h, Class<? extends Entity> type, Entity... exceptions) {
		main: for (Entity e: ents) {
			if (!type.isAssignableFrom(e.getClass())) continue;
			for (Entity e2: exceptions) {
				if (e == e2) continue main;
			}
			if (distance(x, e.getX()) <= w && abs(y - e.getY()) <= h) return e;
		}
		return null;
	}
	
	public ArrayList<Entity> getAllEntitiesNear(float x, float y, float dist, Class<? extends Entity> type, Entity... exceptions) {
		dist *= dist;
		ArrayList<Entity> nearby = new ArrayList<Entity>();
		main: for (Entity e: ents) {
			if (!type.isAssignableFrom(e.getClass())) continue;
			for (Entity e2: exceptions) {
				if (e == e2) continue main;
			}
			if (sq(distance(x, e.getX())) + sq(y - e.getY()) <= dist) nearby.add(e);
		}
		return nearby;
	}
	
	public int countEntitiesNear(float x, float y, float radius, Class<? extends Entity> type) {
		int count = 0;
		radius *= radius;
		for (Entity e: ents) {
			if (!type.isAssignableFrom(e.getClass())) continue;
			if (sq(distance(x, e.getX())) + sq(y - e.getY()) <= radius) count++;
		}
		return count;
	}
	
	public int directionTo(float srcX, float destX) {
		if (srcX == destX) return 0;
		if (srcX < halfWidth) {
			if (destX < halfWidth) {
				return sign(destX - srcX);
			} else {
				if (destX - srcX <= halfWidth) return 1;
				return -1;
			}
		} else {
			if (destX > halfWidth) {
				return sign(destX - srcX);
			} else {
				if (srcX - destX <= halfWidth) return -1;
				return 1;
			}
		}
	}
	
	public float distance(float x1, float x2) {
		if (x1 > x2) {
			float f = x1;
			x1 = x2;
			x2 = f;
		}
		if (x2 - x1 > halfWidth) return abs((x1 + width) - x2);
		return abs(x2 - x1);
	}
	
	public Random getRandom() {
		return rand;
	}
	
	public StateWorld getState() {
		return state;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHalfWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public ArrayList<Entity> getEntities() {
		return ents;
	}
	
	public ArrayList<EntityParticle> getParticles() {
		return particles;
	}
	
	private float abs(float a) {
		return a < 0 ? -a : a;
	}
	
	private int sign(float a) {
		if (a == 0) return 0;
		return a < 0 ? -1 : 1;
	}
	
	private float sq(float a) {
		return a * a;
	}
}
