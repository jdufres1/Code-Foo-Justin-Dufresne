/***
 * License Plate Maker
 * Class - LicensePlateMakerTest
 * by Justin Dufresne
 */
package licensePlates;

import javax.swing.JFrame;

public class LicensePlateMakerTest
{
	public static void main(String[] args)
	{
		JFrame application = new JFrame("License Plate Maker");
		application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		application.setSize(500,250);
		application.setLocationRelativeTo(null);
		application.setContentPane(new LicensePlateMakerPanel(application));
		application.setVisible(true);
	}
}
