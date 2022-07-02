package xunfang.game.gui.obj;

import java.awt.Image;

import xunfang.game.constants.PlantType;

/*
 * 植物抽象类
 */
public abstract class Plant {
	public long createTime;
	public int heal;

	public abstract Image getCurrentImage();

	public abstract PlantType getType();

}
