package com.thomasci.tetros.shop;

import com.thomasci.tetros.Globals;
import com.thomasci.tetros.tile.Tile;
import com.thomasci.tetros.world.World;

public class ShopItemUpgrade2 extends ShopItemTileTemp {
	public ShopItemUpgrade2(Tile tile, String name, int price) {
		super(tile, name, price);
	}
	
	@Override
	public void purchase(World world) {
		super.purchase(world);
		Globals.changeMaxCoins(3000);
	}
}
