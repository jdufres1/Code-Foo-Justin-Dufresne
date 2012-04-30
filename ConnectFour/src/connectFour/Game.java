/***
 * Connect Four
 * Class - Game 
 * by Justin Dufresne
 */

package connectFour;

public class Game 
{
	private View view;
	private Controller ctl;
	private boolean paused = false;
	private int gamesWon = 0;
	private int gamesLost = 0;
	public Game()
	{
		view = new View(this);
		ctl = new Controller(view);
	}
	public static void main(String[] args)
	{
		new Game();
	}
	public boolean isRunning()
	{
		return ctl.isRunning();
	}
	public void setRunning(boolean running)
	{
		ctl.setRunning(running);
	}
	public boolean isPaused()
	{
		return paused;
	}
	public void setPaused(boolean paused)
	{
		this.paused = paused;
	}
	public void gameWon()
	{
		gamesWon++;
	}
	public int getGamesWon()
	{
		return gamesWon;
	}
	public void gameLost()
	{
		gamesLost++;
	}
	public int getGamesLost()
	{
		return gamesLost;
	}
}
