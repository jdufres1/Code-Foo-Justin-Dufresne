/***
 * Connect Four
 * Class - HelpScreen
 * by Justin Dufresne
 */
package connectFour.scene;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import connectFour.Game;
import connectFour.ImageLoader;
import connectFour.View;
import connectFour.game.ConnectFourBoard;

public class HelpScreen extends Scene
{
	private static final long serialVersionUID = 1L;
	private ConnectFourBoard currentBoard;
	public HelpScreen(final Game game, final View owner)
	{
		init(game, owner, false);
	}
	public HelpScreen(final Game game, final View owner, ConnectFourBoard currentBoard)
	{
		// This will be called when the user asks for the help screen while in a game. 
		// When the user presses back from the help screen, they will be returned to
		//  their game instead of the home screen.
		this.currentBoard = currentBoard;
		init(game, owner, true);
	}
	public void init(final Game game, final View owner, boolean returnToGame)
	{
		this.game = game;
		this.owner = owner;
		setSize(owner.getContentPane().getWidth(), owner.getContentPane().getHeight());
		BG = ImageLoader.loadImage("images/helpBG.png");
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		JLabel leftArrow = new JLabel();
		leftArrow.setMinimumSize(new Dimension(50, 50));
		leftArrow.setIcon(new ImageIcon(ImageLoader.loadImage("images/leftArrow.png")));
		c.gridx = 0;
		c.gridy= 0;
		add(leftArrow, c);
		
		JLabel rightArrow = new JLabel();
		rightArrow.setMinimumSize(new Dimension(50, 50));
		rightArrow.setIcon(new ImageIcon(ImageLoader.loadImage("images/rightArrow.png")));
		c.gridx = 1;
		add(rightArrow, c);
		
		JLabel instruction1 = new JLabel("- Choose column");
		instruction1.setMinimumSize(new Dimension(150, 50));
		c.gridx = 2;
		c.gridwidth = 3;
		add(instruction1, c);
		
		JLabel enterButton = new JLabel();
		enterButton.setMinimumSize(new Dimension(100, 50));
		enterButton.setIcon(new ImageIcon(ImageLoader.loadImage("images/enter.png")));
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 2;
		add(enterButton, c);
		
		JLabel instruction2 = new JLabel("- Drop piece");
		instruction2.setMinimumSize(new Dimension(150, 50));
		c.gridx = 2;
		c.gridwidth = 3;
		add(instruction2, c);
		
		JButton backButton = new JButton();
		backButton.setIcon(new ImageIcon(ImageLoader.loadImage("images/backButton.png")));
		backButton.setMinimumSize(new Dimension(200, 50));
		backButton.setBorderPainted(false);
		backButton.setBackground(new Color(0,0,0,0));
		if (!returnToGame)
		{
			backButton.addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent evt) 
				{
					owner.setScene(new TitleScreen(game, owner));
				}	
				
			});
		}
		else 
		{
			backButton.addActionListener(new ActionListener()
			{

				public void actionPerformed(ActionEvent evt) 
				{
					owner.setScene(currentBoard);
				}	
				
			});
		}
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 4;
		add(backButton, c);
		
	}
}
