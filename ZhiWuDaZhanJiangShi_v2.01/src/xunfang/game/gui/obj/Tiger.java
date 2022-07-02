package xunfang.game.gui.obj;

import java.awt.Image;

import xunfang.game.constants.PlantType;
import xunfang.game.utils.ImageUtil;

/**
 * @author ChenHuaxian
 * @createTime 2012-11-18上午12:19:57
 * @lastEditTime 2012年11月18日 00:38:05
 * @write get tiger picture
 * 
 */
public class Tiger extends Plant {

	private Image normalImg;

	public Tiger() {
		normalImg = ImageUtil.loadImage("tiger.png");
		heal = 128;
	}

	@Override
	public Image getCurrentImage() {

		return normalImg;
	}

	@Override
	public PlantType getType() {

		return PlantType.Tiger;
	}

}
