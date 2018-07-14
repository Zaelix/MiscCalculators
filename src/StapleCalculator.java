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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;

public class StapleCalculator implements ActionListener, ItemListener {
	// USING STANDARD T50 ARROW STAPLES MADE OUT OF STEEL
	// SIZES INCLUDE 6MM, 8MM, 10MM, 12MM, 13MM, 14MM

	// AUSTENITIC STAINLESS STEEL
	// YIELD 275 - 310
	// TENSILE 560 - 755
	String[] aGradeOptions = new String[] { "301", "304", "304L", "305", "309S", "310S", "316", "316L" };

	// FERRITIC STAINLESS STEEL
	// YIELD 320 - 345
	// TENSILE 500 - 515
	String[] fGradeOptions = new String[] { "409", "430", "444", "3CR12" };

	// MARTENSITIC STAINLESS STEEL
	// YIELD 275 - 310
	// TENSILE 560 - 755
	String[] mGradeOptions = new String[] { "420", "431", "440A", "440B", "440C" };

	// DUPLEX STAINLESS STEEL
	String[] dGradeOptions = new String[] { "2001/2101", "2304", "2205", "2507" };

	JFrame frame = new JFrame();
	JPanel panel = new JPanel();
	JTextField weightField = new JTextField();
	JLabel weightText = new JLabel("kg");
	JComboBox<String> typeBox = new JComboBox<String>();
	JComboBox<String> gradeBox = new JComboBox<String>();
	JComboBox<String> diameterBox = new JComboBox<String>();

	// Info panel & Components
	JPanel infoPanel = new JPanel();
	JLabel typeLabel = new JLabel("Type: Austenitic");
	JLabel gradeLabel = new JLabel("Grade: ");
	JLabel yieldLabel = new JLabel("Yield Strength: ");
	JLabel countLabel = new JLabel("Minimum # of Staples: ");

	JButton calculate = new JButton();

	// Contains all yield strengths for steel types and grades
	HashMap<String, Integer> steels = new HashMap<String, Integer>();

	String steelType = "Austenitic";
	String steelGrade = "Null";
	
	float diameter = 0.006f;
	// in MPa. 1 MPa = 1 N/m2
	float stapleYieldStrength = 275;

	// cross-sectional area of the staple, in m2
	double stapleArea = -1;
	// number of staples needed to support the weight
	int stapleCount = -1;
	// mass in kg
	float pMass = 0f;
	// gravity force in m/s/s
	float gravity = 9.80665f;
	// force the mass exerts in Newtons
	float pForce = -1;

	public static void main(String[] args) {
		StapleCalculator calc = new StapleCalculator();
		calc.drawGUI();
	}

	void drawGUI() {
		initializeSteelMap();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		typeBox.addItem("Austenitic Stainless Steel");
		typeBox.addItem("Ferritic Stainless Steel");
		typeBox.addItem("Martensitic Stainless Steel");
		typeBox.addItem("Duplex Stainless Steel");
		typeBox.addItemListener(this);
		gradeBox.addItemListener(this);
		diameterBox.addItemListener(this);
		calculate.addActionListener(this);
		weightField.setPreferredSize(new Dimension(30, 20));

		fillGradeBox(aGradeOptions);
//		
//		diameterBox.addItem("6mm");
//		diameterBox.addItem("8mm");
//		diameterBox.addItem("10mm");
//		diameterBox.addItem("12mm");
//		diameterBox.addItem("13mm");
//		diameterBox.addItem("14mm");
		
		for(int i = 0; i < 37; i++) {
			diameterBox.addItem(i+" guage");
		}
		
		panel.setPreferredSize(new Dimension(400, 200));
		frame.add(panel);
		panel.add(weightField);
		panel.add(weightText);
		panel.add(typeBox);
		panel.add(gradeBox);
		panel.add(diameterBox);

		infoPanel.setPreferredSize(new Dimension(300, 140));
		typeLabel.setPreferredSize(new Dimension(300, 20));
		gradeLabel.setPreferredSize(new Dimension(300, 20));
		yieldLabel.setPreferredSize(new Dimension(300, 20));
		countLabel.setPreferredSize(new Dimension(300, 20));
		calculate.setText("Calculate!");
		infoPanel.add(typeLabel);
		infoPanel.add(gradeLabel);
		infoPanel.add(yieldLabel);
		infoPanel.add(calculate);
		infoPanel.add(countLabel);
		panel.add(infoPanel);

		frame.setVisible(true);
		frame.pack();

	}
	
	void calculateDiameterFromGuage(int guage) {
		double d = 0.127*Math.pow(92.0, (36.0-guage)/39.0);
		diameter = (float) d;
	}
	
	void calculateAreaFromGuage(int guage) {
		double area = 0.012668*Math.pow(92, (36.0-guage)/19.5);
		stapleArea = area * 2.0;
	}

	void initializeSteelMap() {
		steels.put("Austenitic - 301", 275);
		steels.put("Austenitic - 304", 290);
		steels.put("Austenitic - 304L", 270);
		steels.put("Austenitic - 305", 262);
		steels.put("Austenitic - 309S", 310);
		steels.put("Austenitic - 310S", 310);
		steels.put("Austenitic - 316", 290);
		steels.put("Austenitic - 316L", 290);

		steels.put("Ferritic - 409", 320);
		steels.put("Ferritic - 430", 345);
		steels.put("Ferritic - 444", 340);
		steels.put("Ferritic - 3CR12", 340);

		steels.put("Martensitic - 420", 1000);
		steels.put("Martensitic - 431", 1070);
		steels.put("Martensitic - 440A", 1655);
		steels.put("Martensitic - 440B", 1860);
		steels.put("Martensitic - 440C", 1895);

		steels.put("Duplex - 2001/2101", 480);
		steels.put("Duplex - 2304", 400);
		steels.put("Duplex - 2205", 460);
		steels.put("Duplex - 2507", 530);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource().equals(calculate)) {
			System.out.println("CALCULATING...");
			if(weightField.getText().length() > 0) {
				pMass = Float.parseFloat(weightField.getText());
			}
			pForce = pMass * gravity;
			calculateDiameterFromGuage(getGuage());
			calculateAreaFromGuage(getGuage());
			System.out.println("stapleYieldStrength: " + stapleYieldStrength);
			System.out.println("stapleArea: " + stapleArea);
			System.out.println(pForce);
			float stapleStrengthInNewtons = (float) (stapleYieldStrength * stapleArea);
			System.out.println("stapleStrengthInNewtons: " + stapleStrengthInNewtons);
			stapleCount = (int) Math.ceil(pForce / stapleStrengthInNewtons);
			System.out.println("stapleCount: " + stapleCount);
			countLabel.setText("Max # of Staples: " + stapleCount);
		}
	}

	void checkTypeBox(ItemEvent e) {
		if (e.getSource().equals(typeBox) && e.getStateChange() == ItemEvent.SELECTED) {
			gradeBox.removeAllItems();
			if (typeBox.getSelectedItem().equals("Austenitic Stainless Steel")) {
				steelType = "Austenitic";
				typeLabel.setText("Type: Austenitic");
				fillGradeBox(aGradeOptions);
			} else if (typeBox.getSelectedItem().equals("Ferritic Stainless Steel")) {
				steelType = "Ferritic";
				typeLabel.setText("Type: Ferritic");
				fillGradeBox(fGradeOptions);
			} else if (typeBox.getSelectedItem().equals("Martensitic Stainless Steel")) {
				steelType = "Martensitic";
				typeLabel.setText("Type: Martensitic");
				fillGradeBox(mGradeOptions);
			} else if (typeBox.getSelectedItem().equals("Duplex Stainless Steel")) {
				steelType = "Duplex";
				typeLabel.setText("Type: Duplex");
				fillGradeBox(dGradeOptions);
			}
			frame.pack();
		}
	}

	void checkGradeBox(ItemEvent e) {
		if (e.getSource() == gradeBox && e.getStateChange() == ItemEvent.SELECTED) {
			steelGrade = (String) gradeBox.getSelectedItem();
			System.out.println("Grade: " + steelType + " - " + steelGrade);
			gradeLabel.setText("Grade: " + gradeBox.getSelectedItem());
			yieldLabel.setText("Yield: " + steels.get(steelType + " - " + steelGrade) + "MPa");
			stapleYieldStrength = steels.get(steelType + " - " + steelGrade);
			System.out.println("X = " + stapleYieldStrength);
			frame.pack();
		}
	}

	void fillGradeBox(String[] options) {
		for (int i = 0; i < options.length; i++) {
			gradeBox.addItem(options[i]);
		}
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub

		checkTypeBox(e);
		checkGradeBox(e);
		checkDiameterBox(e);
	}

	int getGuage() {
		String d = (String) diameterBox.getSelectedItem();
		d = d.replaceAll(" guage", "");
		int guage = Integer.parseInt(d);
		System.out.println("Guage = " + guage);
		return guage;
	}
	void checkDiameterBox(ItemEvent e) {
		// TODO Auto-generated method stub
		if (e.getSource() == diameterBox && e.getStateChange() == ItemEvent.SELECTED) {
			int guage = getGuage();
			calculateDiameterFromGuage(guage);
			System.out.println("Diameter is now: " + diameter);
			frame.pack();
		}
	}
}
