package com.thomasci.tetros.screen;

import java.util.HashMap;

import com.thomasci.tetros.entity.*;
import com.thomasci.tetros.screen.draw.*;

public class DrawRegistry {
	private static final HashMap<Class<? extends Entity>, DrawEntity> draws = new HashMap<Class<? extends Entity>, DrawEntity>();
	
	public static void addEntity(Class<? extends Entity> cl, DrawEntity drawer) {
		draws.put(cl, drawer);
	}
	
	public void registerBaseEntities() {
		DrawDropPiece dropPiece = new DrawDropPiece();
		addEntity(EntityPlayer.class, new DrawEntityPlayer());
		addEntity(EntityCaveman.class, new DrawEntityCaveman());
		addEntity(EntityZombie.class, new DrawEntityZombie());
		addEntity(EntityItem.class, new DrawEntityItem());
		addEntity(EntityDropPiece.class, dropPiece);
		addEntity(EntityDropItem.class, dropPiece);
		addEntity(EntityParticle.class, new DrawEntityParticle());
		addEntity(EntityCoinMachine.class, new DrawEntityCoinMachine());
		addEntity(EntityZombieSpawner.class, new DrawEntityZombieSpawner());
		addEntity(EntityCavemanSpawner.class, new DrawEntityCavemanSpawner());
		addEntity(EntityChicken.class, new DrawEntityChicken());
		addEntity(EntityMinotaur.class, new DrawEntityMinotaur());
		addEntity(EntityArrow.class, new DrawEntityArrow());
		addEntity(EntityMinotaurSpawner.class, new DrawEntityMinotaurSpawner());
	}
	
	public void drawEntity(Entity e, float viewX, int offsetX, int offsetY) {
		if (draws.containsKey(e.getClass())) {
			draws.get(e.getClass()).drawEntity(e, viewX, offsetX, offsetY);
		}
	}
}
