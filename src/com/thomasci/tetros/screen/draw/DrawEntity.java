package com.thomasci.tetros.screen.draw;

import com.thomasci.tetros.entity.Entity;

public abstract class DrawEntity {
	public abstract void drawEntity(Entity e, float viewX, int offsetX, int offsetY);
}
