/***
 * Connect Four
 * Class - Controller 
 * by Justin Dufresne
 */
package connectFour;

public class Controller implements Runnable 
{
	private View view;
	private boolean running;
	public Controller(View view)
	{
		this.view = view;
		new Thread(this).start();
	}
	public void run()
	{ 
		long lastLoopTime = System.nanoTime(); 
		final long OPTIMAL_TIME = 1000000000 / 30; 
		running = true;
		while (running)   
		{     
     
			long now = System.nanoTime();   
			lastLoopTime = now;  
			
			// update and redraw everything  
			view.update();
			
			if ((System.nanoTime() - lastLoopTime) > OPTIMAL_TIME)
			{
				// Do not sleep. We are already late.
			}
			else 
			{
				try {
					Thread.sleep( ( OPTIMAL_TIME - (System.nanoTime() - lastLoopTime) ) / 1000000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public boolean isRunning()
	{
		return running;
	}
	public void setRunning(boolean running)
	{
		this.running = running;
	}
}
