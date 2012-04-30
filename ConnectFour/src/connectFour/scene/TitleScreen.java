/***
 * Connect Four
 * Class - TitleScreen
 * by Justin Dufresne
 */
package connectFour.scene;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import connectFour.Game;
import connectFour.ImageLoader;
import connectFour.View;
import connectFour.game.ConnectFourBoard;

public class TitleScreen extends Scene
{
	private static final long serialVersionUID = 1L;

	public TitleScreen(final Game game, final View owner)
	{
		this.game = game;
		this.owner = owner;
		setSize(owner.getContentPane().getWidth(), owner.getContentPane().getHeight());
		BG = ImageLoader.loadImage("images/titleBG.png");
		
		setLayout(null);
		
		JButton playButton = new JButton();
		playButton.setIcon(new ImageIcon(ImageLoader.loadImage("images/playButton.png")));
		playButton.setSize(playButton.getIcon().getIconWidth(), playButton.getIcon().getIconHeight());
		playButton.setBorderPainted(false);
		playButton.setLocation(429, 452);
		playButton.addActionListener(new ActionListener()
		{

			public void actionPerformed(ActionEvent evt) 
			{
				owner.setScene(new ConnectFourBoard(game, owner));
			}
			
		});
		
		JButton helpButton = new JButton();
		helpButton.setIcon(new ImageIcon(ImageLoader.loadImage("images/helpButton.png")));
		helpButton.setSize(helpButton.getIcon().getIconWidth(), helpButton.getIcon().getIconHeight());
		helpButton.setBorderPainted(false);
		helpButton.setLocation(429, 520);
		helpButton.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent evt)
			{
				owner.setScene(new HelpScreen(game, owner));
			}
			
		});
		
		add(playButton);
		add(helpButton);
	}
}
