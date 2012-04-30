/***
 * Connect Four
 * Class - Scene
 * by Justin Dufresne
 */
package connectFour.scene;

import javax.swing.JPanel;

import connectFour.Game;
import connectFour.View;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Scene extends JPanel
{
	private static final long serialVersionUID = 1L;
	protected Game game;
	protected View owner;
	protected BufferedImage BG;
	public void update()
	{
		repaint();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		g.drawImage(BG, 0, 0, this);
	}
	public View getOwner()
	{
		return owner;
	}
}
