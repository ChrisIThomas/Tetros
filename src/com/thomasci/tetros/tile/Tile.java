package com.thomasci.tetros.tile;

import com.thomasci.tetros.world.World;

public class Tile {
	public final int id;
	private boolean isAir = false, shouldDropOn = false, oneWay = false;
	protected int icon;
	
	public Tile(int id, int icon) {
		this.id = id;
		if (tiles[id] != null) System.out.println("Overriding Tile " + id);
		tiles[id] = this;
		this.icon = icon;
	}
	
	public boolean isAir() {
		return isAir;
	}
	
	public boolean shouldDropOn() {
		return shouldDropOn;
	}
	
	public boolean isOneWay() {
		return oneWay;
	}
	
	public int getIcon() {
		return icon;
	}
	
	public int getIcon(World world, int x, int y, long time) {
		return icon;
	}
	
	public void updateTile(World world, int x, int y) {}
	
	
	public Tile setIsAir(boolean isAir) {
		this.isAir = isAir;
		return this;
	}
	
	public Tile setShouldDropOn(boolean shouldDropOn) {
		this.shouldDropOn = shouldDropOn;
		return this;
	}
	
	public Tile setOneWay(boolean oneWay) {
		this.oneWay = oneWay;
		return this;
	}
	
	private static final Tile[] tiles = new Tile[128];
	public static final Tile AIR = new Tile(0, 0).setIsAir(true);
	public static final TileDirt DIRT = new TileDirt(1);
	public static final TileGrass LONGGRASS = (TileGrass) new TileGrass(2).setIsAir(true);
	public static final TileFlower FLOWER = (TileFlower) new TileFlower(3).setIsAir(true);
	public static final TileStone STONE = new TileStone(4);
	public static final Tile PACKAGE = new Tile(5, 18);
	public static final TileCoinMachine COINMACHINESPAWN = new TileCoinMachine(6);
	public static final TileWood WOOD = new TileWood(7);
	public static final Tile WOODSUPPORT = new Tile(8, 20).setIsAir(true).setShouldDropOn(true);
	public static final TileGrave GRAVE = (TileGrave) new TileGrave(9).setIsAir(true);
	public static final Tile ZOM00 = new Tile(10, 26);
	public static final Tile ZOM10 = new Tile(11, 27);
	public static final Tile ZOM01 = new Tile(12, 28);
	public static final Tile ZOM11 = new Tile(13, 29);
	public static final TileZombieSpawner ZOMBIESPAWNER = new TileZombieSpawner(14);
	public static final Tile WOODPLATFORM = new Tile(15, 30).setIsAir(true).setOneWay(true).setShouldDropOn(true);
	public static final TileCavemanRubble CAVEMANRUBBLE = (TileCavemanRubble) new TileCavemanRubble(16).setIsAir(true);
	public static final Tile CAV0 = new Tile(17, 33).setIsAir(true);
	public static final Tile CAV1 = new Tile(18, 34).setIsAir(true);
	public static final TileCavemanSpawner CAVEMANSPAWNER = new TileCavemanSpawner(19);
	public static final TileObelisk OB0 = (TileObelisk) new TileObelisk(20, 35).setIsAir(true);
	public static final TileObelisk OB1 = (TileObelisk) new TileObelisk(21, 37).setIsAir(true);
	public static final TileObelisk OB2 = (TileObelisk) new TileObelisk(22, 39).setIsAir(true);
	public static final TileMinotaurSpawner MINOTAURSPAWNER = new TileMinotaurSpawner(23);
}
