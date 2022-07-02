package xunfang.game.gui;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import xunfang.game.utils.ImageUtil;
/*
 * 游戏加载进入界面
 */
public class MenuPanel extends JPanel {

	private static final long serialVersionUID = 8836916379229465102L;

	private Image bgImage;
	private Image startOverImg;
	private Image startLeaveImg;
	
	private GamePanel gamePanel;
	
	public MenuPanel(GamePanel gamePanel) {
		super();
		this.gamePanel = gamePanel;
		bgImage = ImageUtil.loadImage("menu.png");
		startOverImg = ImageUtil.loadImage("start_over.png");
		startLeaveImg = ImageUtil.loadImage("start_leave.png");
		setBackground(Color.red);
		setLayout(null);
		Cursor cursor =
            Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);

		JButton startBtn = new JButton();
		startBtn.setIgnoreRepaint(true);
		startBtn.setFocusable(false);
		startBtn.setToolTipText("开始游戏了");
		startBtn.setBorder(null);
		startBtn.setContentAreaFilled(false);
		startBtn.setCursor(cursor);
		startBtn.setIcon(new ImageIcon(startLeaveImg));
		startBtn.setRolloverIcon(new ImageIcon(startOverImg));
		startBtn.setPressedIcon(new ImageIcon(startOverImg));
		startBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				switchToGame();
			}
		});
		add(startBtn);
		startBtn.setBounds(246, 544, startOverImg.getWidth(null), startOverImg.getHeight(null));
	}
	
	public void switchToGame() {
		gamePanel.switchToGame();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), null);
	}
	
	
	
	

}
