import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TapeCalculator implements ActionListener, ItemListener{
	static double LB_KG_CONVERSIONRATE = 0.45359237;
	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JTextField weightField = new JTextField();
	JLabel weightText = new JLabel("kg");

	JPanel resultsPanel = new JPanel();
	JLabel lengthLabel = new JLabel("Length of adhesive needed: ");
	JLabel stripsLabel = new JLabel("Number of strips needed: ");
	
	
	JComboBox<String> tapeBox = new JComboBox<String>();
	JComboBox<Double> widthBox = new JComboBox<Double>();
	JButton calculate = new JButton("Calculate");
	
	Tape selectedTape;
	double selectedWidth = 48;

	HashMap<String, Tape> tapes = new HashMap<String, Tape>();
	
	double weight = 0;
	
	public static void main(String[] args) {
		TapeCalculator tc = new TapeCalculator();
		tc.createGUI();
	}
	
	void createGUI() {
		initializeTapes();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		panel.setPreferredSize(new Dimension(400, 100));
		weightField.setPreferredSize(new Dimension(40, 20));
		tapeBox.setPreferredSize(new Dimension(100, 20));
		widthBox.setPreferredSize(new Dimension(60, 20));

		resultsPanel.setPreferredSize(new Dimension(250, 100));
		lengthLabel.setPreferredSize(new Dimension(250, 20));
		stripsLabel.setPreferredSize(new Dimension(250, 20));
		
		
		frame.add(panel);
		panel.add(weightField);
		panel.add(weightText);
		panel.add(tapeBox);
		panel.add(widthBox);
		
		panel.add(calculate);
		
		panel.add(resultsPanel);
		resultsPanel.add(lengthLabel);
		resultsPanel.add(stripsLabel);
		tapeBox.addItemListener(this);
		widthBox.addItemListener(this);
		calculate.addActionListener(this);
		frame.pack();
	}
	
	void initializeTapes() {
		tapes.put("3M 1900", new Tape("3M 1900", 0.147, 33, 6.8, new double[] {48,72}));
		tapes.put("3M 3900", new Tape("3M 3900", 0.19, 49, 5.8, new double[] {48}));
		tapes.put("3M 3939", new Tape("3M 3939", 0.22, 47.3, 6.3, new double[] {24,48,72,96}));
		tapes.put("3M 6969", new Tape("3M 6969", 0.25, 59.5, 5.6, new double[] {48,72}));
		tapes.put("3M 8979N", new Tape("3M 8979N", 0.31, 63, 5.3, new double[] {48}));
		tapeBox.addItem("3M 1900");
		tapeBox.addItem("3M 3900");
		tapeBox.addItem("3M 3939");
		tapeBox.addItem("3M 6969");
		tapeBox.addItem("3M 8979N");
		widthBox.addItem(48.0);
		selectedTape = tapes.get("3M 1900");
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource().equals(calculate)) {
			weight = Double.parseDouble(weightField.getText());
			System.out.println("Calculating with weight " + weight + " and width " + selectedWidth);
			float gravity = 9.80665f;
			double pForce = weight * gravity;
			System.out.println(pForce);
			System.out.println(selectedTape.adhesion);
			System.out.println(selectedTape.width);
			double cmOfTapeNeeded = Math.ceil(pForce / (selectedTape.adhesion * (selectedTape.width / 48.0)));
			double stripsNeeded = Math.ceil(pForce / (selectedTape.tensile * (selectedTape.width / 48.0)));
			lengthLabel.setText("Length of adhesive needed: " + cmOfTapeNeeded + "cm");
			stripsLabel.setText("Number of strips needed: " + stripsNeeded);
			System.out.println("Length of adhesive needed: "+cmOfTapeNeeded);
			System.out.println("Number of strips of tape needed: " + stripsNeeded);
		}
	}
	
	void getWeight() {
		String w = weightField.getText();
		if (w.length() > 0) {
			weight = Double.parseDouble(w);
		}
		else {
			weight = 0;
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if (e.getSource().equals(tapeBox) && e.getStateChange() == ItemEvent.SELECTED) {
			selectedTape = tapes.get(tapeBox.getSelectedItem());
			widthBox.removeAllItems();
			for(int i = 0; i < selectedTape.validWidths.length; i++) {
				widthBox.addItem(selectedTape.validWidths[i]);
			}
			selectedWidth = selectedTape.validWidths[0];
			System.out.println("Selected tape is now " + selectedTape.name);
		}
		if (e.getSource().equals(widthBox) && e.getStateChange() == ItemEvent.SELECTED) {
			selectedWidth = (double) widthBox.getSelectedItem();
			System.out.println("Selected width is now " + selectedWidth);
		}
	}
}
