package xunfang.game.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import xunfang.game.constants.GuiConstnat;
import xunfang.game.constants.PlantType;
import xunfang.game.gui.obj.SeedCard;
import xunfang.game.utils.ImageUtil;

/*
 * 植物卡片框
 */
public class PlantsBar implements GuiConstnat {
	// 卡片格子数量
	private int plantSum;
	// 太阳数量和卡片选择窗口
	private Image seedBank;
	// 卡片
	private SeedCard[] cards;
	// 太阳数量
	private int lights;
	// 画笔颜色，显示太阳数量
	private Font lightFont;

	public PlantsBar() {
		seedBank = ImageUtil.loadImage("SeedBank.png");
		// allSeedImg = ImageUtil.loadImage("allseeds.png");
		plantSum = 7;
		cards = new SeedCard[plantSum];
		for (int i = 0; i < plantSum; ++i) {
			cards[i] = new SeedCard(new Point(SEED_OFFSET + ADD_SUN_OFFSET
					+ (CARD_WIDTH + CARD_GAP_W) * i, TOP_OFFSET), new Point(i,
					0));
		}
		lightFont = new Font(Font.DIALOG, Font.BOLD, 20);
		lights = 1000;
	}

	public void gameUpdate(long time) {
		for (SeedCard sc : cards) {
			sc.gameUpdate();
		}
	}

	// 选择植物卡片
	public PlantType selectedPlant(Point pos) {
		// 判断是否坐标是否在指定范围
		if ((pos.x > SEED_OFFSET + ADD_SUN_OFFSET)
				&& (pos.x < SEED_OFFSET + ADD_SUN_OFFSET
						+ seedBank.getWidth(null)) && (pos.y > TOP_OFFSET)
				&& (pos.y < TOP_OFFSET + seedBank.getHeight(null))) {
			// 判断哪一张卡片被选择，是否可选
			for (SeedCard sc : cards) {
				if (sc.mouseIn(pos.x, pos.y) && sc.isEnable()) {
					if (lights >= sc.getCost()) {
						System.out.println("This plant will use our "
								+ sc.getCost() + " lights. we have" + lights);
						sc.reset();
						lights -= sc.getCost();

						return sc.getPlantType();
					}
				}
			}
		}
		return PlantType.NONE;
	}

	public void draw(Graphics g) {
		g.drawImage(seedBank, GuiConstnat.SEED_OFFSET, 0, null);
		for (SeedCard sc : cards) {
			sc.draw(g);
		}
		g.setColor(Color.BLACK);
		g.setFont(lightFont);
		g.drawString(String.valueOf(lights), SEED_OFFSET
				+ ADD_SUN_COUNT_X_OFFSET, ADD_SUN_COUNT_y_OFFSET);
		// cards[0].draw(g);

		/*
		 * g.drawImage(allSeedImg, SEED_OFFSET + ADD_SUN_OFFSET, TOP_OFFSET,
		 * SEED_OFFSET + ADD_SUN_OFFSET + CARD_WIDTH, TOP_OFFSET + CARD_HEIGHT,
		 * 0, 0, CARD_WIDTH, CARD_HEIGHT, null);
		 */

	}

	// 收集阳光
	public void collectSun(int num) {
		lights += num;
	}
}
