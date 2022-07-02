package xunfang.game.gui;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import xunfang.game.constants.GuiConstnat;
import xunfang.game.constants.PlantType;
import xunfang.game.gui.obj.Bullet;
import xunfang.game.gui.obj.BulletNormal;
import xunfang.game.gui.obj.Plant;
import xunfang.game.gui.obj.SingleBulletPlant;
import xunfang.game.gui.obj.Sun;
import xunfang.game.gui.obj.SunFlowerPlant;
import xunfang.game.gui.obj.Tiger;
import xunfang.game.gui.obj.Zombie;
import xunfang.game.gui.obj.ZombieBucket;
import xunfang.game.gui.obj.ZombieConehead;
import xunfang.game.gui.obj.ZombieNormal;
import xunfang.game.utils.ImageUtil;

/*
 * 游戏界面，草地，植物，僵尸，包括水印
 */
public class PlantsMap implements GuiConstnat {

	// 格子
	private Plant[][] plantsMap;
	// 水印
	private Image waterMark;
	// 水印位置
	private Point waterMarkPos;
	// 僵尸
	private List<Zombie> zombies = new ArrayList<Zombie>();
	// 太阳
	private List<Sun> suns = new ArrayList<Sun>();
	// 豌豆子弹
	private List<Bullet> bullets = new ArrayList<Bullet>();

	// private Set<Zombie> zombies = new HashSet<Zombie>();
	// private Set<Sun> suns = new HashSet<Sun>();
	// private Set<Bullet> bullets = new HashSet<Bullet>();

	public PlantsMap() {
		plantsMap = new Plant[MAP_ROW][MAP_COL];

		// 初始测试使用的僵尸和太阳
		Zombie z1 = new ZombieBucket((int) (GuiConstnat.WIDTH * 0.9),
				(int) (GuiConstnat.MAP_RECT_HEIGHT * 4.5));
		// Zombie z2 = new ZombieBucket((int) (GuiConstnat.WIDTH * 0.9),
		// (int) (GuiConstnat.MAP_RECT_HEIGHT * 3.5));
		// Zombie z3 = new ZombieBucket((int) (GuiConstnat.WIDTH * 0.9),
		// (int) (GuiConstnat.MAP_RECT_HEIGHT * 2.5));
		// Zombie z4 = new ZombieBucket((int) (GuiConstnat.WIDTH * 0.9),
		// (int) (GuiConstnat.MAP_RECT_HEIGHT * 1.5));
		// Zombie z5 = new ZombieBucket((int) (GuiConstnat.WIDTH * 0.9),
		// (int) (GuiConstnat.MAP_RECT_HEIGHT * 0.5));
		zombies.add(z1);
		// zombies.add(z2);
		// zombies.add(z3);
		// zombies.add(z4);
		// zombies.add(z5);
		// 初始测试太阳
		Sun s1 = new Sun(360, -40);
		// Sun s2 = new Sun(120, -20);
		// Sun s3 = new Sun(720, -50);
		suns.add(s1);
		// suns.add(s2);
		// suns.add(s3);

		waterMark = ImageUtil.loadImage("WaterMark.png");
		waterMarkPos = new Point(WIDTH - waterMark.getWidth(null), HEIGHT
				- waterMark.getHeight(null));
	}

	// 游戏更新，下一帧
	public void gameUpdate(long time, Graphics g) {
		createZombie(time);
		createSun(time);

		Iterator<Zombie> zit = zombies.iterator();
		while (zit.hasNext()) {
			Zombie z = zit.next();
			int col = (z.getLocation().x - MAP_WEST_OFFSET / 2)
					/ MAP_RECT_WIDTH;
			;
			int row = (z.getLocation().y - MAP_TOP_OFFSET / 2)
					/ MAP_RECT_HEIGHT;

			if (col >= 0 && row >= 0 && col < plantsMap[row].length
					&& row < plantsMap.length && plantsMap[row][col] != null) {
				z.attack(g);
				plantsMap[row][col].heal--;
				System.out.println(plantsMap[row][col].heal);
				if (plantsMap[row][col].heal <= 0) {
					plantsMap[row][col] = null;
					System.out.println("Eatting end.");
				}

			} else {
				z.gameUpdate();
			}

			if (z.isOffScreen()) {
				zit.remove();
				System.out.println("GAME OVER!");
			}
		}

		Iterator<Bullet> bit = bullets.iterator();
		while (bit.hasNext()) {
			Bullet b = bit.next();
			b.gameUpdate();
			if (b.isOffScreen()) {
				bit.remove();
				// System.out.println("A bullet was pass. Total are "
				// + bullets.size());
			}
		}

		Iterator<Sun> sunit = suns.iterator();
		while (sunit.hasNext()) {
			Sun s = sunit.next();
			s.gameUpdate();
			if (s.isOffScreen()) {
				sunit.remove();
				System.out.println("A sun was lost. Total are " + suns.size());
			}
		}

	}

	public void createZombie(long time) {
		// 每1024帧产生一个僵尸
		if (time % 1024 == 53) {
			int r = (int) (Math.random() * 100);
			Zombie z;
			if (r > 90) {
				z = new ZombieBucket((int) (GuiConstnat.WIDTH * 0.9),
						(int) (GuiConstnat.MAP_RECT_HEIGHT * ((int) (Math
								.random() * 4) + 0.5)));
				System.out.print("A new BucketZombie create.");
			} else if (r > 60) {
				z = new ZombieConehead((int) (GuiConstnat.WIDTH * 0.9),
						(int) (GuiConstnat.MAP_RECT_HEIGHT * ((int) (Math
								.random() * 4) + 0.5)));
				System.out.print("A new ConeheadZombie create.");
			} else {
				z = new ZombieNormal((int) (GuiConstnat.WIDTH * 0.9),
						(int) (GuiConstnat.MAP_RECT_HEIGHT * ((int) (Math
								.random() * 4) + 0.5)));
				System.out.print("A new NormalZombie create.");
			}
			zombies.add(z);
			System.out.println(" New we have to deal with " + zombies.size()
					+ " zombies.");

		}
	}

	public void createSun(long time) {
		// 每512帧产生一个太阳
		if (time % 512 == 29) {
			Sun s = new Sun((int) (GuiConstnat.WIDTH * Math.random()),
					(int) (GuiConstnat.CARD_HEIGHT * (Math.random() + 1)));

			suns.add(s);

			System.out.println("A new Sun create. Total are " + suns.size());
		}
	}

	public void draw(Graphics g, long time) {

		// 遍历每个格子，画出植物
		for (int i = 0; i < MAP_COL; ++i) {
			for (int j = 0; j < MAP_ROW; ++j) {
				int x = MAP_WEST_OFFSET + i * MAP_RECT_WIDTH;
				int y = MAP_TOP_OFFSET + j * MAP_RECT_HEIGHT;

				Plant p = plantsMap[j][i];

				if (p != null) {
					Image img = p.getCurrentImage();
					g.drawImage(img, x + 3, y + 5, null);

					// 豌豆炮每256帧发射一颗子弹
					if (p.getType() == PlantType.SingleBullet
							&& (time - p.createTime) % 256 == 7) {
						Bullet bullet = new BulletNormal(x, y);
						bullets.add(bullet);
					}
					// 每朵太阳花每1280帧产生一个太阳
					if (p.getType() == PlantType.SunFlower
							&& (time - p.createTime) % 1280 == 0) {
						Sun sun = new Sun(x, y);
						suns.add(sun);
					}
				}

			}
		}

		// 画出僵尸
		Iterator<Zombie> zit = zombies.iterator();
		while (zit.hasNext()) {
			zit.next().draw(g);
		}

		// 画子弹
		Iterator<Bullet> bit = bullets.iterator();
		while (bit.hasNext()) {
			Bullet b = bit.next();
			if (bulletBomb(b, zombies, g)) {
				b.attack(g);
				bit.remove();
			} else {
				b.draw(g);
			}
		}

		// 画太阳
		Iterator<Sun> sunit = suns.iterator();
		while (sunit.hasNext()) {
			sunit.next().draw(g);
		}

		addWaterMark(g);
	}

	// 判断子弹是否打在僵尸上，应该使用更优化的方法，或者更简洁的方法
	private boolean bulletBomb(Bullet b, List<Zombie> zs, Graphics g) {

		Iterator<Zombie> zit = zombies.iterator();

		while (zit.hasNext()) {
			Zombie z = zit.next();
			if ((b.getLocation().x - z.getLocation().x) < Normal_Zombie_WIDTH
					&& z.getLocation().x - b.getLocation().x < Bullet_Normal_WIDTH * 2
					&& (b.getLocation().y - z.getLocation().y) < (Normal_Zombie_HEIGHT)
					&& b.getLocation().y > z.getLocation().y) {
				// System.out.println("子弹" + b.getLocation() + "僵尸"
				// + z.getLocation());
				z.blood--;
				if (z.blood <= 0) {
					z.dead(g);
					zit.remove();
					System.out
							.println("We have kill one zombie, it still have "
									+ zombies.size());
				}
				return true;
			}

		}
		return false;
	}

	private void addWaterMark(Graphics g) {
		g.drawImage(waterMark, waterMarkPos.x, waterMarkPos.y, null);
	}

	// 判断坐标点是否在游戏界面草地里
	public boolean inTheMap(int posX, int posY) {
		if ((posX > MAP_WEST_OFFSET)
				&& (posX < MAP_WEST_OFFSET + MAP_COL * MAP_RECT_WIDTH)
				&& (posY > MAP_TOP_OFFSET)
				&& (posY < MAP_TOP_OFFSET + MAP_ROW * MAP_RECT_HEIGHT)) {
			return true;
		}
		return false;
	}

	// 放置植物
	public PlantType putPlantInMap(PlantType pt, int posX, int posY, long time) {
		Plant p = null;
		switch (pt) {
		case SunFlower:
			p = new SunFlowerPlant(time);
			break;
		case SingleBullet:
			p = new SingleBulletPlant(time);
			break;
		default:
			p = new Tiger();
		}
		if (p != null) {
			int col = (posX - MAP_WEST_OFFSET) / MAP_RECT_WIDTH;
			int row = (posY - MAP_TOP_OFFSET) / MAP_RECT_HEIGHT;
			if (plantsMap[row][col] == null) {
				plantsMap[row][col] = p;
				pt = PlantType.NONE;
			}
		}
		return pt;
	}

	// 判断是否点击了阳光
	public int collectSun(int x, int y) {

		int sum = 0;

		Iterator<Sun> it = suns.iterator();
		while (it.hasNext()) {
			Sun s = it.next();
			int num = s.isClicked(x, y);
			if (num > 0) {
				sum += num;
				it.remove();
				System.out.println("We have catch one sun, it still have "
						+ suns.size());

			}
		}

		// System.out.println(suns.size());
		// for (Sun sun : suns) {
		// System.out.println("原始地址" + sun);
		// for (Sun s : suns) {
		// int num = s.isClicked(x, y);
		// if (num > 0) {
		// sum += num;
		// // System.out.println(suns.size() + "sun********" + s);
		// suns.remove(s);
		// // System.out.println(suns.size() + "," + s);
		// }

		return sum;

	}
}
