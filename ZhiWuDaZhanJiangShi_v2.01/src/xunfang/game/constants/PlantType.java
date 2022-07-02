package xunfang.game.constants;

import java.awt.Image;

import xunfang.game.utils.ImageUtil;

/*
 * 枚举类，存放植物类型
 */
public enum PlantType {
	Tiger(null),
	
	NONE(null), 
	SunFlower(ImageUtil.loadImage("sun_flower1.png")), 
	SingleBullet(ImageUtil.loadImage("single_bullet_plant1.png")), 
	Cherry(ImageUtil.loadImage("tiger.png")), 
	SmallStone(ImageUtil.loadImage("tiger.png")), 
	Mine(ImageUtil.loadImage("tiger.png")), 
	ColdBullet(ImageUtil.loadImage("tiger.png")), 
	Eat(ImageUtil.loadImage("tiger.png")), 
	DoubBullet(null);
	
	

	private Image moveImg;

	PlantType(Image moveImg) {
		this.moveImg = moveImg;
	}

	public Image getMoveImg() {
		return moveImg;
	}

}
