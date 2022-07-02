package xunfang.game.gui.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

/*
 * 僵尸抽象类
 */
public abstract class Zombie {

	public int blood;
	public int speed;

	public abstract void attack(Graphics g);

	public abstract void dead(Graphics g);

	public abstract Point getLocation();

	public abstract Image getCurrentImg();

	public abstract void gameUpdate();

	public abstract void draw(Graphics g);

	public abstract boolean isOffScreen();

	protected class AnimFrame {
		Image img;
		int fps;

		public AnimFrame(Image img, int fps) {
			super();
			this.img = img;
			this.fps = fps;
		}

	}

}
