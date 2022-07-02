package xunfang.game.gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import xunfang.game.constants.GuiConstnat;

public class GameFrame extends JFrame {
	private static final long serialVersionUID = -1403111892023140699L;
	
	public GameFrame() {
		setTitle("植物大战僵尸");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		getContentPane().setLayout(new BorderLayout());
		getContentPane().add(new GamePanel(this), BorderLayout.CENTER);
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension dim = tk.getScreenSize();
		setLocation((dim.width - GuiConstnat.WIDTH) / 2, (dim.height - GuiConstnat.HEIGHT) / 2);
		pack();
		//setLocation(x, y)
		setVisible(true);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new GameFrame();
			}
		});
	}

}
