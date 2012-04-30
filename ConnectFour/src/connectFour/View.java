/***
 * Connect Four
 * Class - View
 * by Justin Dufresne
 */
package connectFour;

import javax.swing.JFrame;

import connectFour.scene.Scene;
import connectFour.scene.TitleScreen;

public class View extends JFrame
{
	private static final long serialVersionUID = 1L;
	private Scene content;
	private int width = 1000;
	private int height = 750;
	public View(Game game)
	{
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(width, height);
		setLocationRelativeTo(null);
		setVisible(true);
		content = new TitleScreen(game, this);
		setFocusable(true);
		setContentPane(content);
	}
	public void setScene(Scene scene)
	{
		content = scene;
		setContentPane(scene);
	}
	public Scene getScene()
	{
		return content;
	}
	public void update()
	{
		content.update();
	}
}
