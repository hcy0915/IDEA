package xunfang.game.gui;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import xunfang.game.constants.GuiConstnat;

public class GamePanel extends JPanel {

	private static final long serialVersionUID = 3709471684157101646L;
	private GameFrame frame;
	private CardLayout layout;
	
	private MenuPanel menu;
	private PlayPanel play;
	
	
	
	public GamePanel(GameFrame frame) {
		this.frame = frame;
		
		layout = new CardLayout();
		setLayout(layout);
		setPreferredSize(new Dimension(GuiConstnat.WIDTH, GuiConstnat.HEIGHT));
		
		menu = new MenuPanel(this);
		play = new PlayPanel();
		add(menu, "menu");
		add(play, "play");
	}
	
	



	public void switchToGame() {
		layout.show(this, "play");
		play.startGame();
	}

}
