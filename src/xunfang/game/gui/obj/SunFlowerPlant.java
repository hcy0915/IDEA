package xunfang.game.gui.obj;

import java.awt.Image;

import xunfang.game.constants.PlantType;
import xunfang.game.utils.ImageUtil;

public class SunFlowerPlant extends Plant {
	private Image normalImg;

	public SunFlowerPlant(long time) {
		normalImg = ImageUtil.loadImage("sun_flower1.png");
		createTime = time;
		heal = 256;
	}

	public Image getCurrentImage() {
		return normalImg;
	}

	@Override
	public PlantType getType() {

		return PlantType.SunFlower;
	}
}
