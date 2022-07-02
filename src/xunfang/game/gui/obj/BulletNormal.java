package xunfang.game.gui.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import xunfang.game.constants.GuiConstnat;
import xunfang.game.utils.ImageUtil;

public class BulletNormal extends Bullet implements GuiConstnat {
	private Image normalImg;
	private Image attackImg;
	private int posX, posY;
	private static final int FPS_PER_PIXEL = 10;
	private int counter;

	public BulletNormal(int bornX, int bornY) {
		normalImg = ImageUtil.loadImage("bullet_01.png");
		attackImg = ImageUtil.loadImage("PeaBulletHit.png");
		posX = bornX;
		posY = bornY;
	}

	public Image getCurrentImg() {
		return normalImg;
	}

	public void gameUpdate() {
		if (isOffScreen()) {
			return;
		}

		if (++counter > FPS_PER_PIXEL) {
			posX += 24;
			counter = 0;
		}

	}

	public void draw(Graphics g) {
		if (isOffScreen()) {
			return;
		}
		g.drawImage(getCurrentImg(), posX + GuiConstnat.MAP_RECT_WIDTH, posY
				+ GuiConstnat.MAP_RECT_HEIGHT / 8, null);
	}

	public boolean isOffScreen() {
		if (posX + GuiConstnat.MAP_RECT_WIDTH > GuiConstnat.WIDTH) {
			return true;
		}
		return false;
	}

	public void attack(Graphics g) {
		g.drawImage(attackImg, posX + GuiConstnat.MAP_RECT_WIDTH, posY
				+ GuiConstnat.MAP_RECT_HEIGHT / 8, null);
	}

	public Point getLocation() {
		return new Point(posX, posY);
	}

	public int hashCode() {
		return posY * 1000 + posX;
	}

	public boolean equals(Object obj) {
		BulletNormal b = (BulletNormal) obj;
		return b.posX == this.posX && b.posY == this.posY;
	}

}
