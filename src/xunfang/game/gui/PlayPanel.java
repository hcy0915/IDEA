package xunfang.game.gui;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import xunfang.game.constants.GuiConstnat;
import xunfang.game.constants.PlantType;
import xunfang.game.utils.ImageUtil;

/*
 * 游戏界面
 */
public class PlayPanel extends JPanel implements MouseListener,
		MouseMotionListener, Runnable {
	private static final long serialVersionUID = 3888079631986134186L;

	// 帧数计数器
	private long time = 0;
	// 背景
	private Image allBgImage;
	// 左上角卡片框
	private PlantsBar plantsBar;
	// 坐标
	private PlantsMap plantsMap;
	// 图片类型
	private PlantType plantType;

	// 画图工具
	private Graphics dbg;
	private Image dbImage = null;

	private static final int NO_DELAYS_PER_YIELD = 16;
	private static int MAX_FRAME_SKIPS = 10;
	private Thread animator;
	private volatile boolean isPaused = false;
	private volatile boolean running = false;
	private long period;

	private long startTime;

	public PlayPanel() {
		super();
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		init();
	}

	private void init() {
		// 初始化鼠标图片为空
		plantType = PlantType.NONE;
		// 加载背景图片
		allBgImage = ImageUtil.loadImage("background1.jpg");
		plantsBar = new PlantsBar();
		plantsMap = new PlantsMap();

		// 周期，时间标记，用于整体同步
		period = 1000 / GuiConstnat.DEFAULT_FPS;

		// 图片运动线程
		animator = new Thread(this);

	}

	public void startGame() {
		animator.start();
	}

	// 坐标标记，用于记录各个图片的位置
	List<Point> ps = new LinkedList<Point>();

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}

	@Override
	public void run() {

		startTime = System.currentTimeMillis();
		long beforeTime, afterTime, timeDiff, sleepTime;

		int overSleepTime = 0;
		int noDelays = 0;
		int excess = 0;

		beforeTime = System.currentTimeMillis();
		running = true;

		while (running) {
			time++;
			/*
			 * if (System.currentTimeMillis() - startTime > 5000) { running =
			 * false; }
			 */
			gameUpdate(time);
			gameRender();
			paintScreen();

			/*
			 * 帧同步的实现
			 */
			afterTime = System.currentTimeMillis();
			timeDiff = afterTime - beforeTime;
			sleepTime = (period - timeDiff) - overSleepTime;

			if (sleepTime > 0) { // some time left in this cycle
				try {
					Thread.sleep(sleepTime); // already in ms
				} catch (InterruptedException ex) {
				}
				overSleepTime = (int) ((System.currentTimeMillis() - afterTime) - sleepTime);
			} else { // sleepTime <= 0; the frame took longer than the period
				excess -= sleepTime; // store excess time value
				overSleepTime = 0;

				if (++noDelays >= NO_DELAYS_PER_YIELD) {
					Thread.yield(); // give another thread a chance to run
					noDelays = 0;
				}
			}

			beforeTime = System.currentTimeMillis();

			/*
			 * If frame animation is taking too long, update the game state
			 * without rendering it, to get the updates/sec nearer to the
			 * required FPS.
			 */
			int skips = 0;
			while ((excess > period) && (skips < MAX_FRAME_SKIPS)) {
				excess -= period;
				gameUpdate(time); // update state but don't render
				skips++;
			}
		}

	}

	private void paintScreen() {
		Graphics g;
		try {
			g = this.getGraphics();
			if ((g != null) && (dbImage != null))
				g.drawImage(dbImage, 0, 0, null);
			/*
			 * 获取默认工具包，参见API。 同步此工具包的图形状态。某些窗口系统可能会缓存图形事件。
			 * 此方法确保显示是最新的。这在动画制作时很有用。
			 */
			Toolkit.getDefaultToolkit().sync();
			g.dispose();
		} catch (Exception e) {
			System.out.println("Graphics error: " + e);
		}
	}

	private void gameRender() {
		if (dbImage == null) {
			dbImage = createImage(getWidth(), getHeight());
			if (dbImage == null) {
				System.out.println("dbImage is null");
				return;
			} else
				dbg = dbImage.getGraphics();
			if (dbg instanceof Graphics2D) {
				Graphics2D g2 = (Graphics2D) dbg;
				g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
						RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

			}
		}
		dbg.drawImage(allBgImage, 0, 0, getWidth(), getHeight(),
				GuiConstnat.LEFT_OFFSET, 0, GuiConstnat.LEFT_OFFSET
						+ getWidth(), getHeight(), null);
		plantsBar.draw(dbg);
		plantsMap.draw(dbg, time);
		drawMouseImg(dbg);
	}

	private void drawMouseImg(Graphics g) {
		if (choosingPlant != PlantType.NONE) {
			g.drawImage(choosingPlant.getMoveImg(), mouseX
					- GuiConstnat.CARD_WIDTH / 2, mouseY
					- GuiConstnat.CARD_HEIGHT / 2, null);
		}
	}

	int times = 0;

	private void gameUpdate(long time) {
		plantsBar.gameUpdate(time);
		plantsMap.gameUpdate(time, dbg);
	}

	// 选择的植物类型
	private PlantType choosingPlant = PlantType.NONE;

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	private int mouseX;
	private int mouseY;

	@Override
	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();

		// 太阳花收集
		plantsBar.collectSun(plantsMap.collectSun(x, y));

		// 种植植物
		if (choosingPlant != PlantType.NONE) {
			if (plantsMap.inTheMap(x, y)) {
				choosingPlant = plantsMap.putPlantInMap(choosingPlant, x, y,
						time);
			}
		}
		// 植物卡片选择
		else {
			choosingPlant = plantsBar.selectedPlant(new Point(e.getX(), e
					.getY()));
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub

	}

}
