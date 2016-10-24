package com.thomasci.tetros.screen;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.thomasci.tetros.Game;

public class GameScreen extends JPanel implements KeyListener, MouseListener, WindowListener {
	private static final long serialVersionUID = 2L;
	private static GameScreen instance;
	private int width, height;
	private boolean created = false;
	private boolean fullscreen = false;
	private ScreenImage image;
	private JFrame frame;
	private ArrayList<Key> keys = new ArrayList<Key>();
	private ArrayList<MouseButton> buttons = new ArrayList<MouseButton>();
	
	public GameScreen(int width, int height) {
		instance = this;
		this.width = width;
		this.height = height;
		setSize(width, height);
		image = new ScreenImage(width / 2, height / 2);
	}
	
	public void createFrame(String title) {
		frame = new JFrame(title);
		frame.setBackground(Color.BLACK);
		
		frame.add(this);
		frame.addKeyListener(this);
		frame.addMouseListener(this);
		frame.addWindowListener(this);
		frame.setResizable(false);
		frame.setIgnoreRepaint(true);
		frame.pack();
		
		frame.setLocation((getToolkit().getScreenSize().width - frame.getWidth()) / 2, (getToolkit().getScreenSize().height - frame.getHeight()) / 2);
		frame.setVisible(true);
		
		created = true;
	}
	
	public void toggleFullscreen() {
		if (created) {
			fullscreen = !fullscreen;
			if (fullscreen) {
				frame.setVisible(false);
				frame.dispose();
				frame.setUndecorated(true);
				width = getToolkit().getScreenSize().width;
				height = getToolkit().getScreenSize().height;
				image = new ScreenImage(width / 2, height / 2);
				frame.pack();
				frame.setVisible(true);
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
			} else {
				frame.setVisible(false);
				frame.dispose();
				frame.setUndecorated(false);
				width = 800;
				height = 600;
				image = new ScreenImage(width / 2, height / 2);
				frame.pack();
				frame.setVisible(true);
				frame.setExtendedState(JFrame.NORMAL);
				frame.setLocation((getToolkit().getScreenSize().width - frame.getWidth()) / 2, (getToolkit().getScreenSize().height - frame.getHeight()) / 2);
				frame.pack();
			}
		}
	}
	
	public void updateInput() {
		Game game = Game.instance();
		long time = System.currentTimeMillis();
		for (int i = 0; i < keys.size(); i++) {
			Key k = keys.get(i);
			if (k.press) {
				game.keyPress(k.id);
				k.press = false;
			}
			game.keyDown(k.id, time - k.time);
			if (k.release) {
				game.keyRelease(k.id);
				keys.remove(i);
				i--;
			}
		}
		for (int i = 0; i < buttons.size(); i++) {
			MouseButton b = buttons.get(i);
			if (b.press) {
				game.mousePress(b.id, b.x, b.y);
				b.press = false;
			}
			if (b.release) {
				game.mouseRelease(b.id, b.x, b.y);
				buttons.remove(i);
				i--;
			}
		}
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(image, 0, 0, width, height, null);
		g.dispose();
	}
	
	@Override
	public Dimension getPreferredSize() {
		return new Dimension(width, height);
	}
	
	public static GameScreen instance() {
		return instance;
	}
	
	public JFrame getJFrame() {
		return frame;
	}
	
	public ScreenImage getImage() {
		return image;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	@Override
	public void windowActivated(WindowEvent e) {}
	
	@Override
	public void windowClosed(WindowEvent e) {}
	
	@Override
	public void windowClosing(WindowEvent e) {
		Game.instance().end();
	}
	
	@Override
	public void windowDeactivated(WindowEvent e) {}
	
	@Override
	public void windowDeiconified(WindowEvent e) {}
	
	@Override
	public void windowIconified(WindowEvent e) {}
	
	@Override
	public void windowOpened(WindowEvent e) {}
	
	@Override
	public void keyPressed(KeyEvent e) {
		for (Key k: keys) {
			if (k.id == e.getKeyCode()) return;
		}
		keys.add(new Key(e.getKeyCode()));
	}
	
	@Override
	public void keyReleased(KeyEvent e) {
		for (Key k: keys) {
			if (k.id == e.getKeyCode()) k.release = true;
		}
	}
	
	@Override
	public void keyTyped(KeyEvent e) {}
	
	private class Key {
		private final int id;
		private long time;
		private boolean press;
		private boolean release;
		
		private Key(int id) {
			this.id = id;
			time = System.currentTimeMillis();
			press = true;
		}
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) {}

	@Override
	public void mouseExited(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		for (MouseButton b: buttons) {
			if (b.id == e.getButton()) return;
		}
		buttons.add(new MouseButton(e.getButton(), e.getX() / 2, e.getY() / 2));
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		for (MouseButton b: buttons) {
			if (b.id == e.getButton()) b.release = true;
		}
	}
	
	private class MouseButton {
		private final int id, x, y;
		private boolean press;
		private boolean release;
		
		private MouseButton(int id, int x, int y) {
			this.id = id;
			this.x = x;
			this.y = y;
			press = true;
		}
	}
}
