package xunfang.game.gui.obj;

import java.awt.Image;

import xunfang.game.constants.PlantType;
import xunfang.game.utils.ImageUtil;

public class SingleBulletPlant extends Plant {
	private Image normalImg;

	public SingleBulletPlant(long time) {
		normalImg = ImageUtil.loadImage("single_bullet_plant1.png");
		createTime = time;
		heal = 512;
	}

	public Image getCurrentImage() {
		return normalImg;
	}

	public PlantType getType() {

		return PlantType.SingleBullet;
	}

}
