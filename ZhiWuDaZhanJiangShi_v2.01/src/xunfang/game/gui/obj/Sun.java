package xunfang.game.gui.obj;

import java.awt.Graphics;
import java.awt.Image;

import xunfang.game.constants.GuiConstnat;
import xunfang.game.utils.ImageUtil;

public class Sun {
	private Image normalImg;
	private int posX, posY;
	private static final int FPS_PER_PIXEL = 10;
	private int counter;
	// 收集一个阳光获得的数量
	private int number = 25;

	public Sun(int bornX, int bornY) {
		normalImg = ImageUtil.loadImage("sun.png");
		posX = bornX;
		posY = bornY;
	}

	public Image getCurrentImage() {
		return normalImg;
	}

	public void gameUpdate() {
		if (isOffScreen()) {
			return;
		}

		if (++counter > FPS_PER_PIXEL) {
			posY += 4;
			counter = 0;
		}

	}

	public void draw(Graphics g) {
		if (isOffScreen()) {
			return;
		}
		g.drawImage(getCurrentImage(), posX, posY, null);
	}

	public boolean isOffScreen() {
		if (posY + GuiConstnat.MAP_RECT_HEIGHT / 2 > GuiConstnat.HEIGHT) {
			return true;
		}
		return false;
	}

	public int hashCode() {
		return posY * 1000 + posX;
	}

	public int isClicked(int x, int y) {
		if (x > posX + 5 && y > posY + 5 && x < posX + normalImg.getWidth(null)
				&& y < posY + normalImg.getHeight(null)) {
			return number;
		}
		return 0;
	}

	public boolean equals(Object obj) {
		Sun sun = (Sun) obj;
		System.out.println("**********************" + sun.posX);
		return sun.posX == this.posX && sun.posY == this.posY;
	}
}
