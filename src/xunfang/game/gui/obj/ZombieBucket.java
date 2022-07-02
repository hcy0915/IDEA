package xunfang.game.gui.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import xunfang.game.constants.GuiConstnat;
import xunfang.game.utils.ImageUtil;

/*
 * 铁头盔僵尸
 */
public class ZombieBucket extends Zombie implements GuiConstnat {
	private List<AnimFrame> frames;
	private Image attackImg;
	private Image deadImg;
	private int currentFrame;
	int sumFPS;
	private int posX, posY;

	private static final int FPS_PER_PIXEL = 10;
	private int counter;

	public ZombieBucket(int bornX, int bornY) {
		frames = new ArrayList<AnimFrame>();
		posX = bornX;
		posY = bornY;
		blood = 16;
		speed = 1;
		init();
		attackImg = ImageUtil.loadImage("Zombi_digger_pickaxe.png");
		deadImg = ImageUtil.loadImage("zombie_dead/Zombie_charred1.png");
	}

	private void init() {
		counter = 0;
		currentFrame = 0;
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_bucket/z_02_01.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_bucket/z_02_02.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_bucket/z_02_03.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_bucket/z_02_04.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_bucket/z_02_05.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_bucket/z_02_06.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_bucket/z_02_07.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_bucket/z_02_08.png"),
				(int) (0.5f * DEFAULT_FPS)));

		sumFPS = 0;
		for (AnimFrame af : frames) {
			sumFPS += af.fps;
		}
	}

	@Override
	public void gameUpdate() {
		if (isOffScreen()) {
			return;
		}
		++currentFrame;
		if (currentFrame > sumFPS) {
			currentFrame = 0;
		}
		if (++counter > FPS_PER_PIXEL) {
			posX -= speed;
			counter = 0;
		}

	}

	@Override
	public Image getCurrentImg() {
		if (currentFrame > sumFPS) {
			return frames.get(0).img;
		}
		int sum = 0;
		for (int i = 0; i < frames.size(); ++i) {
			sum += frames.get(i).fps;
			if (currentFrame < sum) {
				return frames.get(i).img;
			}
		}
		return frames.get(0).img;
	}

	public void draw(Graphics g) {
		if (isOffScreen()) {
			return;
		}
		g.drawImage(getCurrentImg(), posX, posY, null);
	}

	public void attack(Graphics g) {
		g.drawImage(attackImg, posX + GuiConstnat.MAP_RECT_WIDTH, posY
				+ GuiConstnat.MAP_RECT_HEIGHT / 8, null);
	}

	public void dead(Graphics g) {
		g.drawImage(deadImg, posX + GuiConstnat.MAP_RECT_WIDTH, posY
				+ GuiConstnat.MAP_RECT_HEIGHT / 8, null);
	}

	@Override
	public boolean isOffScreen() {
		// 与NormalZombie尺寸一样
		if (posX + Normal_Zombie_WIDTH < 0) {
			return true;
		}
		return false;
	}

	public Point getLocation() {
		return new Point(posX, posY);
	}

	public int hashCode() {
		return posY * 1000 + posX;
	}
}
