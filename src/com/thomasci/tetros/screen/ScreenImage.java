package com.thomasci.tetros.screen;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ScreenImage extends BufferedImage {
	private final int[] pixels;
	private Graphics2D g;
	
	public ScreenImage(int width, int height) {
		super(width, height, BufferedImage.TYPE_INT_ARGB);
		g = (Graphics2D) getGraphics();
		pixels = ((DataBufferInt) getRaster().getDataBuffer()).getData();
	}
	
	public void clear() {
		int black = 255 << 24;
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = black;
		}
	}
	
	public void clear(int r, int g, int b) {
		int black = (255 << 24) + (r << 16) + (g << 8) + b;
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = black;
		}
	}
	
	public void shade(float scale) {
		int r, g, b;
		for (int i = 0; i < pixels.length; i++) {
			r = (int) (((pixels[i] >> 16) & 0xFF) * scale);
			g = (int) (((pixels[i] >> 8) & 0xFF) * scale);
			b = (int) ((pixels[i] & 0xFF) * scale);
			pixels[i] = (255 << 24) + (r << 16) + (g << 8) + b;
		}
	}
	
	public void drawPixel(int x, int y, int col) {
		pixels[x + y * getWidth()] = col;
	}
	
	public void drawText(String text, int x, int y, Color col) {
		g.setColor(col);
		g.drawChars(text.toCharArray(), 0, text.length(), x, y);
	}
	
	public void drawImage(Image image, int x, int y, int w, int h, int sx, int sy, int sw, int sh) {
		g.drawImage(image, x, y, x + w, y + h, sx, sy, sx + sw, sy + sh, null);
	}
	
	public int getViewWidth() {
		return (int) Math.ceil(getWidth() / 16f) + 2;
	}
}
