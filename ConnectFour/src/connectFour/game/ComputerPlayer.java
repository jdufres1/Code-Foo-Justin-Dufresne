/***
 * Connect Four
 * Class - ComputerPlayer
 * by Justin Dufresne
 */
package connectFour.game;



public class ComputerPlayer 
{
	private ConnectFourBoard board;
	public ComputerPlayer(ConnectFourBoard board)
	{
		this.board = board;
	}
	public int move()
	{
		double[] values = {0, 0, 0, 0, 0, 0, 0};
		values = evaluate(values, ConnectFourBoard.clonePieces(board.getPieces()));
		ConnectFourPiece tempPiece = board.getCurrentPiece().clonePiece();
		int[] moves = getPossibleMoves(tempPiece, board.getPieces());
		int col = moves[0];
		for (int i = 1; i < moves.length; i++)
		{
			if (values[moves[i]] < values[col])
			{
				col = moves[i];
			}
		}
		return col;
	}
	public double[] evaluate(double[] values, ConnectFourPiece[][] pieces)
	{
		ConnectFourPiece tempPiece = board.getCurrentPiece().clonePiece();
		int[] moves = getPossibleMoves(tempPiece, board.getPieces());
		
		for (int i = 0; i < moves.length; i++)
		{
			values[moves[i]] -= 2;
			values[moves[i]] = evaluatePosition(-1, values[moves[i]], moves[i], tempPiece, pieces);
		}
		
		return values;
	}
	public double evaluatePosition(int player, double value, int col, ConnectFourPiece piece, ConnectFourPiece[][] pieces)
	{
		/* Priority List
		 * A move that will win
		 * A move that will prevent the opponent from winning next turn
		 * A move that will guarantee you a win next turn
		 * The move that is adjacent to the most of your pieces
		 * The move that blocks the most of the opponent's pieces
		 * A random move
		 * A move that will allow the opponent to win
		 * An invalid move
		 */
		ConnectFourPiece[][] tempPieces = ConnectFourBoard.clonePieces(pieces);
		
		// Find out how many of the opponent's pieces you'll be next to.
		ConnectFourPiece oppPiece = new ConnectFourPiece(board, -player, -1, col);
		piece.setCol(col);
		oppPiece.testDrop(tempPieces);
		int numOppAdjacent = oppPiece.getNumAdjacent(tempPieces);
		value -= numOppAdjacent;
		
		// Find out if the opponent would win if they took this spot. If they would, then we should block it.
		if (oppPiece.checkWin(tempPieces))
		{
			value += player*49;
		}
		oppPiece.reset(tempPieces);
		
		// If this space will win, then take it.
		piece.testDrop(tempPieces);
		if (piece.checkWin(tempPieces))
		{
			value += player*50;
		}
		
		// If taking this spot would allow the opponent to make a winning move, then we don't want to take it.
		oppPiece.setCol(col);
		if (oppPiece.validMove(tempPieces))
		{
			oppPiece.testDrop(tempPieces);
			if (oppPiece.checkWin(tempPieces))
			{
				value -= player*50;
			}
			oppPiece.reset(tempPieces);
		}
		
		// Find out how many of our own pieces we are adjacent to. 
		int numAdjacent = piece.getNumAdjacent(tempPieces);
		value -= numAdjacent;
		
		// We should take spaces that are right next to the opponent if none of the other conditions have been met.
		if (piece.nextToOpponent(tempPieces))
		{
			// But don't take spots that are too high up.
			value -= 4 - Math.abs(col - 3);
		}
		
		// Take spots that are closer to the center.
		if(player == -1)
		{
			value += 5 - piece.getRow();
		}
		
		piece.reset(tempPieces);
		return value;
	}
	
	public int[] getPossibleMoves(ConnectFourPiece piece, ConnectFourPiece[][] pieces)
	{
		// Return all the columns that we can play in. 
		
		int[] moves = new int[7];
		int count = 0;
		for (int i = 0; i < pieces[0].length; i++)
		{
			piece.setCol(i);
			if (piece.validMove(pieces))
			{
				moves[i] = i;
				count++;
			}
			else
			{
				moves[i] = -1;
			}
		}
		
		int[] possibleMoves = new int[count];
		int index = 0;
		for (int i = 0; i < moves.length; i++)
		{
			if (moves[i] != -1)
			{
				possibleMoves[index] = moves[i];
				index++;
			}
		}
		
		return possibleMoves;
	}
}
