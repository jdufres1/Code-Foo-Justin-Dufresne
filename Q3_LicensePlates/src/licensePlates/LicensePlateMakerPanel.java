/***
 * License Plate Maker
 * Class - LicensePlateMakerPanel
 * by Justin Dufresne
 */
package licensePlates;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

public class LicensePlateMakerPanel extends JPanel
{
	private static final long serialVersionUID = 1L;
	private JEditorPane output;
	public LicensePlateMakerPanel(JFrame owner)
	{
		// The constructor sets up the application layout. All calculation occurs after the Calculate button is pressed.
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		
		c.ipady = 10;
		
		JLabel inputLabel = new JLabel("Enter the population:");
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 0.3;
		add(inputLabel, c);
		
		final JSpinner input = new JSpinner(new SpinnerNumberModel(1, 1, 10000000000L, 1));
		// The max is set to to 10 billion to keep the numbers realistic. There are only 6.8 billion people in the world, after all.
		c.gridx = 1;
		c.gridy = 0;
		c.ipadx = 100;
		c.weightx = 0.7;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(input, c);
		
		c.weightx = 0.0;
		
		JButton calcButton = new JButton("Calculate");
		c.gridx = 0;
		c.gridy = 1;
		c.gridwidth = 3;
		add(calcButton, c);
		calcButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt)
			{
				long population = ((Double) input.getValue()).longValue();
				PatternData result = calculatePattern(population);
				String resultString = "Population: " + population + "\n" + 
						  "Pattern: " + result.getPattern() + "\n" +
						  "Total plates produced: " + result.getNumPlates() + "\n" +
						  "Excess plates produced: " + (result.getNumPlates() - population);
				output.setText(resultString);
			}
		});
		
		output = new JEditorPane();
		output.setEditable(false);
		c.gridx = 0;
		c.gridy = 2;
		c.ipadx = 300;
		c.ipady = 100;
		add(output, c);
	}
	
	public PatternData calculatePattern(long population)
	{
		if (population == 1) return new PatternData("N", 1);
		long numPlates = 1;
		long result = Long.MAX_VALUE;
		String resultPattern = "";
		LinkedList<PatternData> patterns = new LinkedList<PatternData>();
		/*
		 * I'm imagining the list of all possible patterns as something like a multiplication table, i.e.:
		 * ___||N^0| |N^1| |N^2| |N^3
		 * ----------------------------
		 * L^0|| 1 | | 10| |100| |1000 
		 * L^1|| 26| |260| |2600||26000
		 * L^2||676| |6760||67600|676000
		 *
		 * The LinkedList "patterns" is acting as a queue performing a sort of breadth-first search
		 * to find the answer. 
		 *
		 * The first entry in the queue has an empty pattern and a numPlates value of 1 (though technically no N's and no L's
		 *  should make a pattern with 0 plates. If the population is actually 1, the program will have already returned a 
		 *  pattern of "N". This dummy entry just acts as a starting point.
		 */
		patterns.addLast(new PatternData("", 1));
		while(!patterns.isEmpty())	
		{
			// The numPlates total we're looking at is the first element of the queue.
			numPlates = patterns.getFirst().getNumPlates();	
			String pattern = patterns.getFirst().getPattern();
			// Pop this element.
			patterns.removeFirst();
			// If the numPlates is less than the population, push both possibilities (adding a number or adding a letter) onto the queue
			if (numPlates < population)	
			{
				if (!entryExists(patterns, numPlates * 10))
					patterns.addLast(new PatternData(pattern + "N", numPlates*10));
				if (!entryExists(patterns, numPlates * 26))
					patterns.addLast(new PatternData(pattern + "L", numPlates*26));
			}
			else	
			{
				// The current numPlates is greater than the population. If it is less than the current "result"
				// then it will produce a smaller excess than the last possible answer we found. 
				if  (numPlates < result)
				{
					result = numPlates;
					resultPattern = pattern;
				}
			}
		}
		return (new PatternData(resultPattern, result));
	}
	public boolean entryExists(LinkedList<PatternData> patterns, long numPlates)
	{
		// If an entry with the given pattern already exists in the pattern queue, return true. Else, return false.
		for(PatternData pattern : patterns)
		{
			if (numPlates == pattern.getNumPlates())
			{
				return true;
			}
		}
		return false;
	}
	private static class PatternData
	{
		String pattern;
		long numPlates;
		public PatternData(String pattern, long numPlates)
		{
			this.pattern = pattern;
			this.numPlates = numPlates;
		}
		public String getPattern()
		{
			return pattern;
		}
		public long getNumPlates()
		{
			return numPlates;
		}
	}
}
