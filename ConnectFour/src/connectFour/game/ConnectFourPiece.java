/***
 * Connect Four
 * Class - ConnectFourPiece
 * by Justin Dufresne
 */
package connectFour.game;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import connectFour.ImageLoader;


public class ConnectFourPiece 
{
	private ConnectFourBoard board;
	private int player;
	private String color;
	private int row;
	private int col;
	private BufferedImage image;
	private static HashMap<String, BufferedImage> images = new HashMap<String, BufferedImage>();
	public ConnectFourPiece()
	{
		player = 0;
	}
	public ConnectFourPiece(int currentPlayer, ConnectFourBoard board)
	{
		player = currentPlayer;
		if (currentPlayer == 1)
		{
			color = "red";
		}
		if (currentPlayer == -1)
		{
			color = "black";
		}
		image = images.get(color);
		this.board = board;
		row = -1;
		if (currentPlayer == 1)
			col = 0;
		else col = 6;
	}
	public ConnectFourPiece(ConnectFourBoard board, int player, int row, int col)
	{
		// Constructor for clones
		this.board = board;
		this.player = player;
		this.row = row;
		this.col = col;
	}
	public ConnectFourPiece clonePiece()
	{
		ConnectFourPiece clone = new ConnectFourPiece(board, player, row, col);
		return clone;
	}
	public void draw(Graphics2D g2)
	{
		if (player == 0) return;
		int x = col*75 + board.getBoardX() + 9;
		int y = row*75 + board.getBoardY();
		g2.drawImage(image, x, y, board);
	}
	public void moveLeft()
	{
		if (col > 0)
			col -= 1;
	}
	public void moveRight()
	{
		if (col < 6)
			col += 1;
	}
	public void moveDown()
	{
		row += 1;
	}
	public boolean validMove(ConnectFourPiece[][] pieces)
	{
		for (int i = 0; i < 6; i++)
		{
			if (pieces[i][col].getPlayer() == 0)
			{
				return true;
			}
		}
		return false;
	}
	public void drop(ConnectFourPiece[][] pieces)
	{
		for (int i = 0; i < 6; i++)
		{
			if (pieces[i][col].getPlayer() == 0)
			{
				moveDown();
			}
			else 
			{
				place(pieces);
				return;
			}
		}
		place(pieces);
	}
	public void place(ConnectFourPiece[][] pieces)
	{
		pieces[row][col] = this;
		board.repaint();
		
		if (checkDraw(pieces))
		{
			board.draw();
		}
		else if (checkWin(pieces))
		{
			board.win();
		}
		else 
			board.endTurn();
	}
	public void testDrop(ConnectFourPiece[][] pieces)
	{
		// Combines the drop and place methods, but does not check if this piece wins. Nor does it end the turn. Used in the Computer AI.
		for (int i = 0; i < 6; i++)
		{
			if (pieces[i][col].getPlayer() == 0)
			{
				moveDown();
			}
			else 
			{
				pieces[row][col] = this;
				return;
			}
		}
		pieces[row][col] = this;
	}
	public boolean same(ConnectFourPiece piece)
	{
		// Returns whether the piece passed as a parameter is on the same team as the piece which owns this method.
		return piece.getPlayer() == player;
	}
	public boolean notOpponent(ConnectFourPiece piece)
	{
		// Returns whether the piece passed as a parameter is the opponent or not.
		if (piece.getPlayer() == 0 || piece.getPlayer() == player)
			return true;
		else return false;
	}
	public boolean nextToOpponent(ConnectFourPiece[][] pieces)
	{
		if (col < 6 && !notOpponent(pieces[row][col+1]))
			return true;
		if (col > 0 && !notOpponent(pieces[row][col-1]))
			return true;
		return false;
	}
	public boolean checkDraw(ConnectFourPiece[][] pieces)
	{
		// Return whether or not there is a draw.
		for (int i = 0; i < pieces.length; i++)
		{
			for (int j = 0; j < pieces[i].length; j++)
			{
				if (pieces[i][j].getPlayer() == 0)
				{
					return false;
				}
			}
		}
		return true;
	}
	public void reset(ConnectFourPiece[][] pieces)
	{
		// gets this piece out of the board's pieces array.
		pieces[row][col] = new ConnectFourPiece();
		row = -1;
		col = 0;
	}
	public int getNumAdjacent(ConnectFourPiece[][] pieces)
	{
		// This will be used by the AI to determine how many adjacent pieces of the same color there are in any direction.
		// It will count any pieces of the same color no matter how many empty spaces there are between them, as long as
		//  they are not separated by a piece of the opposite color.
		// It will also NOT count any pieces of the same color if they are in a direction which does not have enough space
		//  for four in a row.
		
		if(row == -1) return 0;
		int numAdjacent = 0;
		int numSpaces = 0;
		int count = 0;
		int tempRow = row;
		int tempCol = col;
		
		while (tempRow > 0 && notOpponent(pieces[tempRow - 1][tempCol]))
		{
			numSpaces++;
			if (same(pieces[tempRow - 1][tempCol]))
			{
				count++;
				numAdjacent++;
			}
			tempRow--;
		}
		tempRow = row;
		tempCol = col;
		while (tempRow < pieces.length - 1 && notOpponent(pieces[tempRow + 1][tempCol]))
		{
			numSpaces++;
			if (same(pieces[tempRow + 1][tempCol]))
			{
				count++;
				numAdjacent++;
			}
			tempRow++;
		}
		if (numSpaces < 3) numAdjacent -= count;
		
		count = 0;
		numSpaces = 0;
		tempRow = row;
		tempCol = col;
		
		while (tempCol > 0 && notOpponent(pieces[tempRow][tempCol - 1]))
		{
			numSpaces++;
			if (same(pieces[tempRow][tempCol - 1]))
			{
				count++;
				numAdjacent++;
			}
			tempCol--;
		}
		tempRow = row;
		tempCol = col;
		while (tempCol < pieces[0].length - 1 && notOpponent(pieces[tempRow][tempCol + 1]))
		{
			numSpaces++;
			if (same(pieces[tempRow][tempCol + 1]))
			{
				numAdjacent++;
				count++;
			}
			tempCol++;
		}
		if (numSpaces < 3) numAdjacent -= count;
		
		count = 0;
		numSpaces = 0;
		tempRow = row;
		tempCol = col;
		
		while (tempRow > 0 && tempCol > 0 && notOpponent(pieces[tempRow - 1][tempCol - 1]))
		{
			numSpaces++;
			if (same(pieces[tempRow - 1][tempCol - 1]))
			{
				numAdjacent++;
				count++;
			}
			tempRow--;
			tempCol--;
		}
		tempRow = row;
		tempCol = col;
		while (tempRow < pieces.length - 1 && tempCol < pieces[0].length - 1 && notOpponent(pieces[tempRow + 1][tempCol + 1]))
		{
			numSpaces++;
			if (same(pieces[tempRow + 1][tempCol + 1]))
			{
				numAdjacent++;
				count++;
			}
			tempRow++;
			tempCol++;
		}
		if (numSpaces < 3) numAdjacent -= count;
		
		numSpaces = 0;
		count = 0;
		tempRow = row;
		tempCol = col;
		
		while (tempRow > 0 && tempCol < pieces[0].length - 1 && notOpponent(pieces[tempRow - 1][tempCol + 1]))
		{
			numSpaces++;
			if (same(pieces[tempRow - 1][tempCol + 1]))
			{
				numAdjacent++;
				count++;
			}
			tempRow--;
			tempCol++;
		}
		tempRow = row;
		tempCol = col;
		while (tempRow < pieces.length - 1 && tempCol > 0 && notOpponent(pieces[tempRow + 1][tempCol - 1]))
		{
			numSpaces++;
			if (same(pieces[tempRow + 1][tempCol - 1]))
			{
				numAdjacent++;
				count++;
			}
			tempRow++;
			tempCol--;
		}
		if (numSpaces < 3) numAdjacent -= count;
		
		count = 0;
		return numAdjacent;
	}
	public boolean checkWin(ConnectFourPiece[][] pieces)
	{
		if (row == -1) return false;
		int numAdjacent = 0;
		
		int tempRow = row;
		int tempCol = col;

		while (tempRow > 0 && same(pieces[tempRow - 1][tempCol]))
		{
			numAdjacent++;
			tempRow--;
		}
		if (numAdjacent >= 3) 
			return true;
		else
		{
			tempRow = row;
			tempCol = col;
			while (tempRow < pieces.length - 1 && same(pieces[tempRow + 1][tempCol]))
			{
				numAdjacent++;
				tempRow++;
			}
		}
		if (numAdjacent >= 3) 
			return true;
		numAdjacent = 0;
		tempRow = row;
		tempCol = col;
		
		while (tempCol > 0 && same(pieces[tempRow][tempCol - 1]))
		{
			numAdjacent++;
			tempCol--;
		}
		if (numAdjacent >= 3) 
			return true;
		else {
			tempRow = row;
			tempCol = col;
			while (tempCol < pieces[0].length - 1 && same(pieces[tempRow][tempCol + 1]))
			{
				numAdjacent++;
				tempCol++;
			}
		}
		if (numAdjacent >= 3) 
			return true;
		numAdjacent = 0;
		tempRow = row;
		tempCol = col;
		while (tempRow > 0 && tempCol > 0 && same(pieces[tempRow - 1][tempCol - 1]))
		{
			numAdjacent++;
			tempRow--;
			tempCol--;
		}
		if (numAdjacent >= 3) 
			return true;
		else {
			tempRow = row;
			tempCol = col;
			while (tempRow < pieces.length - 1 && tempCol < pieces[0].length - 1 && same(pieces[tempRow + 1][tempCol + 1]))
			{
				numAdjacent++;
				tempRow++;
				tempCol++;
			}
		}
		if (numAdjacent >= 3) 
			return true;
		numAdjacent = 0;
		tempRow = row;
		tempCol = col;
		
		while (tempRow > 0 && tempCol < pieces[0].length - 1 && same(pieces[tempRow - 1][tempCol + 1]))
		{
			numAdjacent++;
			tempRow--;
			tempCol++;
		}
		if (numAdjacent >= 3) 
			return true;
		else {
			tempRow = row;
			tempCol = col;
			while (tempRow < pieces.length - 1 && tempCol > 0 && same(pieces[tempRow + 1][tempCol - 1]))
			{
				numAdjacent++;
				tempRow++;
				tempCol--;
			}
		}
		if (numAdjacent >= 3) 
			return true;
		
		return false;
		
	}
	public String getColor()
	{
		return color;
	}
	public int getPlayer()
	{
		return player;
	}
	public int getCol()
	{
		return col;
	}
	public void setCol(int col)
	{
		this.col = col;
	}
	public static void initImages()
	{
		images.put("red", ImageLoader.loadImage("images/red.png"));
		images.put("black", ImageLoader.loadImage("images/black.png"));
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) 
	{
		this.row = row;
	}
}
