/***
 * Connect Four
 * Class - ConnectFourBoard
 * by Justin Dufresne
 */
package connectFour.game;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import connectFour.Game;
import connectFour.ImageLoader;
import connectFour.View;
import connectFour.scene.HelpScreen;
import connectFour.scene.Scene;
import connectFour.scene.TitleScreen;

public class ConnectFourBoard extends Scene
{
	private static final long serialVersionUID = 1L;
	private ConnectFourPiece[][] pieces;
	private ConnectFourPiece currentPiece;
	private int currentPlayer = 1;
	private BufferedImage boardIMG; 
	private int boardX;
	private int boardY;
	private boolean gameOver = false;
	private ComputerPlayer cpu;
	private KeyHandler handler = new KeyHandler();
	
	public ConnectFourBoard(final Game game, final View owner)
	{
		this.game = game;
		this.owner = owner;
		setLayout(null);
		cpu = new ComputerPlayer(this);
		owner.addKeyListener(handler);
		setBackground(Color.WHITE);
		setSize(owner.getContentPane().getWidth(), owner.getContentPane().getHeight());
		BG = ImageLoader.loadImage("images/bg.png");
		boardIMG = ImageLoader.loadImage("images/board.png");
		ConnectFourPiece.initImages();
		boardX = (getWidth() - boardIMG.getWidth()) / 2;
		boardY = (getHeight() - boardIMG.getHeight()) / 2 + 5;
		pieces = new ConnectFourPiece[6][7];
		for (int i = 0; i < pieces.length; i++)
		{
			for (int j = 0; j < pieces[i].length; j++)
			{
				pieces[i][j] = new ConnectFourPiece();
			}
		}
		
		JPanel toolBar = new JPanel();
		toolBar.setSize(90, 30);
		toolBar.setLocation(0, 0);
		toolBar.setLayout(new GridLayout(0, 3));
		toolBar.setBackground(new Color(0,0,0,0));
		FocusHandler focusHandler = new FocusHandler();
		
		JButton homeButton = new JButton();
		homeButton.setBorderPainted(false);
		homeButton.setBackground(new Color(0,0,0,0));
		homeButton.setIcon(new ImageIcon(ImageLoader.loadImage("images/home.png")));
		homeButton.setToolTipText("Go Home");
		homeButton.setSize(30, 30);
		homeButton.addFocusListener(focusHandler);
		homeButton.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent evt)
			{
				// This key listener has to be removed or it will cause bugs. 
				owner.removeKeyListener(handler);
				owner.setScene(new TitleScreen(game, owner));
			}
			
		});
		
		JButton newGameButton = new JButton();
		newGameButton.setBorderPainted(false);
		newGameButton.setBackground(new Color(0,0,0,0));
		newGameButton.setIcon(new ImageIcon(ImageLoader.loadImage("images/new.png")));
		newGameButton.setToolTipText("Start a new game");
		newGameButton.setSize(30, 30);
		newGameButton.addFocusListener(focusHandler);
		newGameButton.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent evt)
			{
				newGame();
			}
			
		});
		
		JButton helpButton = new JButton();
		helpButton.setBorderPainted(false);
		helpButton.setBackground(new Color(0,0,0,0));
		helpButton.setIcon(new ImageIcon(ImageLoader.loadImage("images/help.png")));
		helpButton.setToolTipText("Help");
		helpButton.setSize(30, 30);
		helpButton.addFocusListener(focusHandler);
		helpButton.addActionListener(new ActionListener()
		{
			
			public void actionPerformed(ActionEvent evt)
			{
				owner.setScene(new HelpScreen(game, owner, ConnectFourBoard.this));
			}
			
		});
		
		toolBar.add(homeButton);
		toolBar.add(newGameButton);
		toolBar.add(helpButton);
		
		add(toolBar);
		
		newTurn();
		owner.requestFocus();
	}
	public void newGame()
	{
		currentPlayer = 1;
		for (int i = 0; i < pieces.length; i++)
		{
			for (int j = 0; j < pieces[i].length; j++)
			{
				pieces[i][j] = new ConnectFourPiece();
			}
		}
		owner.requestFocus();
		newTurn();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g.create();
		if (currentPiece != null) currentPiece.draw(g2);
		for(ConnectFourPiece[] row : pieces)
		{
			for (ConnectFourPiece piece : row)
			{
					piece.draw(g2);
			}
		}
		g2.drawImage(boardIMG, boardX, boardY, this);
	}
	public void newTurn()
	{
		currentPiece = new ConnectFourPiece(currentPlayer, this);
		if (currentPlayer == -1)
		{
			currentPiece.setCol(cpu.move());
			currentPiece.drop(pieces);
		}
	}
	public void update()
	{
		repaint();
	}
	public void endTurn()
	{
		currentPlayer = -currentPlayer;
		newTurn();
	}
	public void draw()
	{
		currentPiece = null;
		JOptionPane.showMessageDialog(this, "Draw!");
		newGame();
	}
	public void win()
	{ 
		currentPiece = null;
		String winner;
		if (currentPlayer == 1)
		{
			winner = "You";
			game.gameWon();
		}
		else
		{
			winner = "The computer player";
			game.gameLost();
		}
		
		JOptionPane.showMessageDialog(this, winner + " won!\nGames Won: " + game.getGamesWon() + "\nGames Lost: " + game.getGamesLost());
		newGame();
	}
	public int getBoardX()
	{
		return boardX;
	}
	public int getBoardY()
	{
		return boardY;
	}

	public ConnectFourPiece[][] getPieces()
	{
		return pieces;
	}
	
	public static ConnectFourPiece[][] clonePieces(ConnectFourPiece[][] piecesToClone)
	{
		ConnectFourPiece[][] clone = new ConnectFourPiece[6][7];
		
		for (int i = 0; i < piecesToClone.length; i++)
		{
			for (int j = 0; j < piecesToClone[i].length; j++)
			{
				clone[i][j] = piecesToClone[i][j].clonePiece();
			}
		}
		
		return clone;
	}

	private class KeyHandler implements KeyListener
	{
		private boolean enterPressed = false;
		public void keyPressed(KeyEvent evt) 
		{
			int key = evt.getKeyCode();
			if (currentPlayer == 1 && !gameOver)
			{
				if (key == KeyEvent.VK_LEFT)
				{
					currentPiece.moveLeft();
				}
				if (key == KeyEvent.VK_RIGHT)
				{
					currentPiece.moveRight();
				}
				if (key == KeyEvent.VK_ENTER)
				{
					enterPressed = true;
				}
			}
		}
	
		public void keyReleased(KeyEvent evt)
		{
			int key = evt.getKeyCode();
			if (currentPlayer == 1 && !gameOver)
			{
				if (key == KeyEvent.VK_ENTER)
				{
					if (enterPressed)
					{
						enterPressed = false;
						if (currentPiece.validMove(pieces))
						{
							currentPiece.drop(pieces);
						}
					}
				}
			}
		}
	
		public void keyTyped(KeyEvent evt) {}
	}

	public ConnectFourPiece getCurrentPiece() 
	{
		return currentPiece;
	}
	private class FocusHandler implements FocusListener
	{
		// When one of the buttons in the toolBar is clicked, but the cursor is taken off the button before the mouse button is released, 
		//  the button gets the focus, but no action is carried out. This listener will force the button to give the focus back to the game.
		public void focusGained(FocusEvent evt) 
		{
			owner.requestFocus();
		}

		public void focusLost(FocusEvent evt) 
		{
			
		}
		
	}
}
