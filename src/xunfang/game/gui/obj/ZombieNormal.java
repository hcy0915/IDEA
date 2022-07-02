package xunfang.game.gui.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import xunfang.game.constants.GuiConstnat;
import xunfang.game.utils.ImageUtil;

/*
 * 普通的僵尸
 */
public class ZombieNormal extends Zombie implements GuiConstnat {
	// 将多张图片存储在链表，用于动态显示
	private List<AnimFrame> frames;
	// 计数使用，图片数
	private int currentFrame;
	// 动态图片的总帧数
	int sumFPS;
	// 图片显示位置
	private int posX, posY;
	private Image attackImg;
	private Image deadImg;

	// 计数器
	private static final int FPS_PER_PIXEL = 10;
	private int counter;

	public ZombieNormal(int bornX, int bornY) {
		frames = new ArrayList<AnimFrame>();
		posX = bornX;
		posY = bornY;
		blood = 4;
		speed = 2;
		init();
		attackImg = ImageUtil.loadImage("Zombi_digger_pickaxe.png");
		deadImg = ImageUtil.loadImage("zombie_dead/Zombie_charred1.png");
	}

	private void init() {
		counter = 0;
		currentFrame = 0;
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_normal/z_00_01.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_normal/z_00_02.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_normal/z_00_03.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_normal/z_00_04.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_normal/z_00_05.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_normal/z_00_06.png"),
				(int) (0.5f * DEFAULT_FPS)));
		frames.add(new AnimFrame(ImageUtil
				.loadImage("zombie_normal/z_00_07.png"),
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
