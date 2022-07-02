package xunfang.game.gui.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

public abstract class Bullet {
	public abstract Point getLocation();

	public abstract Image getCurrentImg();

	public abstract void gameUpdate();

	public abstract void draw(Graphics g);

	public abstract boolean isOffScreen();

	public abstract void attack(Graphics g);

}
