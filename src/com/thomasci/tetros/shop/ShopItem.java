package com.thomasci.tetros.shop;

import java.awt.Image;

import com.thomasci.tetros.item.Item;
import com.thomasci.tetros.screen.GameImages;
import com.thomasci.tetros.state.StateBuy;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public abstract class ShopItem {
	protected static Image tiles, items;
	
	public ShopItem() {
		if (tiles == null) tiles = GameImages.getImage("tiles");
		if (items == null) items = GameImages.getImage("items");
	}
	
	public abstract int getPrice();
	public abstract int getIconId();
	public abstract String getName();
	public abstract boolean leaveShopAfterPurchase();
	public abstract void purchase(World world);
	
	public Image getSourceImage() {
		return tiles;
	}
	
	public static void prepareItems() {
		StateBuy.addShopItem(new ShopItemTile(Tile.COINMACHINESPAWN, "Buy Coin Machine", 50));
		StateBuy.addShopItem(new ShopItemItem(Item.MACHINEUPGRADE, "Buy Coin Upgrade Mk.1", 200));
		StateBuy.addShopItem(new ShopItemItem(Item.MACHINEUPGRADE2, "Buy Coin Upgrade Mk.2", 1000));
		StateBuy.addShopItem(new ShopItemTile(Tile.DIRT, "Buy Grass", 25));
		StateBuy.addShopItem(new ShopItemTile(Tile.WOOD, "Buy Wooden Block", 40));
		StateBuy.addShopItem(new ShopItemTile(Tile.WOODSUPPORT, "Buy Wooden Support", 50));
		StateBuy.addShopItem(new ShopItemTile(Tile.WOODPLATFORM, "Buy Wooden Platform", 50));
		StateBuy.addShopItem(new ShopItemUpgrade1(Tile.ZOMBIESPAWNER, "Buy Graveyard", 500));
		StateBuy.addShopItem(new ShopItemUpgrade2(Tile.CAVEMANSPAWNER, "Buy Campsite", 2000));
		StateBuy.addShopItem(new ShopItemTileTemp(Tile.MINOTAURSPAWNER, "Buy Ritual Site", 5000));
		StateBuy.addShopItem(new ShopItemItem(Item.CLUB, "Buy Club", 250));
		StateBuy.addShopItem(new ShopItemItem(Item.AXE, "Buy Axe", 250));
		StateBuy.addShopItem(new ShopItemItem(Item.BOW, "Buy Bow", 1000));
		StateBuy.addShopItem(new ShopItemItem(Item.SHOVEL, "Buy Shovel", 250));
		StateBuy.addShopItem(new ShopItemItem(Item.DRUMSTICK, "Buy Food", 100));
	}
}
