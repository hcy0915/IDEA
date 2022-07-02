package xunfang.game.gui.obj;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;

import xunfang.game.constants.GuiConstnat;
import xunfang.game.constants.PlantType;
import xunfang.game.utils.ImageUtil;

/*
 * 植物卡片
 */
public class SeedCard implements GuiConstnat {
	// 冷却时间百分比
	private float percent;

	private static Image allSeed = ImageUtil.loadImage("allseeds.png");
	private static Image allSeedDark = ImageUtil.loadImage("allseeds_dark.png");

	// 卡片所在游戏界面位置
	private Point pos;
	// 卡片所在数据图片（植物图鉴）的位置
	private Point coord;
	// 植物类型
	private PlantType plantType;
	// 冷却时间
	private int freezingSeconds;
	// 冷却时间计数器
	private int count;
	// 卡片是否可用
	private boolean enable = false;
	// 植物所消耗的阳光数
	private int cost;

	public PlantType getPlantType() {
		return plantType;
	}

	public SeedCard(Point pos, Point coord) {
		this.pos = pos;
		this.coord = coord;
		plantType = seedMap[coord.y][coord.x];
		percent = .5f;

		init(plantType);
		count = 0;
	}

	public boolean isEnable() {
		return enable;
	}

	// 判断坐标是否在指定范围（本植物卡片的图标范围）
	public boolean mouseIn(int x, int y) {
		if ((x > pos.x) && (x < pos.x + CARD_WIDTH) && (y > pos.y)
				&& (y < pos.y + CARD_HEIGHT)) {
			return true;
		}
		return false;
	}

	// 不同植物冷却时间
	private void init(PlantType plantType) {
		freezingSeconds = 256;
		switch (plantType) {
		case SunFlower:
			freezingSeconds = 4;
			cost = 50;
			break;
		case SingleBullet:
			freezingSeconds = 8;
			cost = 100;
			break;
		case Cherry:
			freezingSeconds = 32;
			cost = 150;
			break;
		case SmallStone:
			freezingSeconds = 24;
			cost = 50;
			break;
		case Mine:
			freezingSeconds = 16;
			cost = 25;
			break;
		case ColdBullet:
			freezingSeconds = 48;
			cost = 175;
			break;
		case Eat:
			freezingSeconds = 64;
			cost = 150;
			break;
		case DoubBullet:
			freezingSeconds = 128;
			cost = 200;
			break;
		}

	}

	// 重置冷却时间
	public void reset() {
		count = 0;
		enable = false;
	}

	public void gameUpdate() {

		percent = ((float) count) / (freezingSeconds * DEFAULT_FPS);
		if (count >= freezingSeconds * DEFAULT_FPS) {
			enable = true;
		} else {
			++count;
		}
	}

	// 卡片绘画
	public void draw(Graphics g) {
		int picX = CARD_WIDTH * coord.x + CARD_GAP_W * coord.x;
		int picY = CARD_HEIGHT * coord.y + CARD_GAP_H * coord.y;
		int topH = (int) (CARD_HEIGHT * percent);

		// 在明图片中以坐标截图显示，随线程不断更新展现动态效果
		g.drawImage(allSeed, pos.x, pos.y, pos.x + CARD_WIDTH, pos.y + topH,
				picX, picY, picX + CARD_WIDTH, picY + topH, null);
		// 暗图片截图
		g.drawImage(allSeedDark, pos.x, pos.y + topH, pos.x + CARD_WIDTH, pos.y
				+ CARD_HEIGHT, picX, picY + topH, picX + CARD_WIDTH, picY
				+ CARD_HEIGHT, null);

	}

	public int getCost() {
		return cost;
	}

}
