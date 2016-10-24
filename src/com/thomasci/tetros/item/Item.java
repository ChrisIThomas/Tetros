package com.thomasci.tetros.item;

import com.thomasci.tetros.entity.EntityItem;
import com.thomasci.tetros.entity.EntityLiving;
import com.thomasci.tetros.world.World;

public class Item {
	private final int id;
	private int icon;
	
	public Item(int id, int icon) {
		this.id = id;
		if (items[id] != null) System.out.println("Overriding Tile " + id);
		items[id] = this;
		this.icon = icon;
	}
	
	public final EntityItem dropItemAt(World world, float x, float y) {
		EntityItem item = new EntityItem(world, x, y, this);
		world.spawnEntity(item);
		return item;
	}
	
	/**
	 * Called just before the entity given equips the item
	 * @param e - the entity that is going to equip this item
	 * @return true if the entity can equip the item
	 */
	public boolean onEquip(EntityLiving e) {
		return true;
	}
	
	/**
	 * Called just before the entity given drops the item
	 * @param e - the entity that is going to drop this item
	 * @return true if the entity can drop the item
	 */
	public boolean onDrop(EntityLiving e) {
		return true;
	}
	
	public void onUse(EntityLiving e) {}
	
	public int getID() {
		return id;
	}
	
	public int getIcon() {
		return icon;
	}
	
	private static final Item[] items = new Item[128];
	public static final ItemClub CLUB = new ItemClub(0);
	public static final ItemAxe AXE = new ItemAxe(1);
	public static final ItemShovel SHOVEL = new ItemShovel(2);
	public static final ItemDrumstick DRUMSTICK = new ItemDrumstick(3);
	public static final ItemMachineUpgrade MACHINEUPGRADE = new ItemMachineUpgrade(4);
	public static final ItemBow BOW = new ItemBow(5);
	public static final ItemMachineUpgrade2 MACHINEUPGRADE2 = new ItemMachineUpgrade2(6);
}
