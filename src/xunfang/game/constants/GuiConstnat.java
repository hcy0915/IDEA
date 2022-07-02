package xunfang.game.constants;

public interface GuiConstnat {
	int WIDTH = 800;
	int HEIGHT = 600;

	int LEFT_OFFSET = 220;
	int SEED_OFFSET = 15;

	int ADD_SUN_OFFSET = 79;
	int ADD_SUN_COUNT_X_OFFSET = 16;
	int ADD_SUN_COUNT_y_OFFSET = 80;
	int TOP_OFFSET = 9;
	int CARD_WIDTH = 50;
	int CARD_HEIGHT = 70;
	int CARD_GAP_W = 2;
	int CARD_GAP_H = 1;

	int DEFAULT_FPS = 60;

	int MAP_WEST_OFFSET = 32;
	int MAP_TOP_OFFSET = 82;

	int MAP_COL = 9;
	int MAP_ROW = 5;

	int MAP_RECT_WIDTH = 81;
	int MAP_RECT_HEIGHT = 98;

	int PLANT_WIDTH = 75;
	int PLANT_HEIGHT = 90;

	int Normal_Zombie_WIDTH = 90;
	int Normal_Zombie_HEIGHT = 130;

	int Bullet_Normal_WIDTH = 28;
	int Bullet_Normal_HEIGHT = 28;

	// 存储植物图鉴，在这里添加更多的植物
	PlantType[][] seedMap = new PlantType[][] {
			{ PlantType.SingleBullet, PlantType.SunFlower, PlantType.Cherry,
					PlantType.SmallStone, PlantType.Mine, PlantType.ColdBullet,
					PlantType.Eat, PlantType.DoubBullet }, {} };
}
