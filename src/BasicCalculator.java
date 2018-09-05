import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class BasicCalculator implements ActionListener {
	JFrame frame = new JFrame();
	JButton[] numpad = new JButton[11];
	JTextField equation = new JTextField();
	JTextField field = new JTextField();

	double num1 = 0;
	double num2 = 0;

	public static void main(String[] args) {
		BasicCalculator bc = new BasicCalculator();
		bc.start();
	}

	void start() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(300, 400);
		frame.setPreferredSize(new Dimension(300, 400));
		frame.setVisible(true);
		JPanel panel = new JPanel();
		JPanel display = new JPanel();
		JPanel numpad = new JPanel();
		JPanel operations = new JPanel();

		Dimension buttonSize = new Dimension(45, 30);
		Font eqFont = new Font("Arial", 0, 12);
		Font fFont = new Font("Arial", 0, 20);

		panel.setPreferredSize(new Dimension(300, 400));
		display.setPreferredSize(new Dimension(275, 90));
		numpad.setPreferredSize(new Dimension(150, 150));
		operations.setPreferredSize(new Dimension(100, 150));

		equation.setEditable(false);
		equation.setPreferredSize(new Dimension(250, 25));
		equation.setFont(eqFont);
		equation.setText("");
		equation.setHorizontalAlignment(JTextField.RIGHT);

		field.setEditable(false);
		field.setPreferredSize(new Dimension(250, 25));
		field.setFont(fFont);
		field.setText("");
		field.setHorizontalAlignment(JTextField.RIGHT);

		JButton clear = new JButton("Clear");
		clear.setPreferredSize(new Dimension(250, 25));
		clear.addActionListener(this);

		frame.add(panel);
		panel.add(display);
		display.add(equation);
		display.add(field);
		display.add(clear);

		panel.add(numpad);
		for (int i = 1; i < 11; i++) {
			JButton b = new JButton(i + "");
			if (i == 10) {
				b.setText("0");
			}
			b.setPreferredSize(buttonSize);
			b.addActionListener(this);
			this.numpad[i - 1] = b;
			numpad.add(b);
		}
		JButton period = new JButton(".");
		period.setPreferredSize(buttonSize);
		period.addActionListener(this);
		this.numpad[10] = period;
		numpad.add(period);

		panel.add(operations);
		JButton add = new JButton("+");
		JButton subtract = new JButton("-");
		JButton multiply = new JButton("x");
		JButton divide = new JButton("/");
		JButton equals = new JButton("=");
		add.setPreferredSize(buttonSize);
		subtract.setPreferredSize(buttonSize);
		multiply.setPreferredSize(buttonSize);
		divide.setPreferredSize(buttonSize);
		equals.setPreferredSize(buttonSize);
		operations.add(add);
		operations.add(subtract);
		operations.add(multiply);
		operations.add(divide);
		operations.add(equals);
		add.addActionListener(this);
		subtract.addActionListener(this);
		multiply.addActionListener(this);
		divide.addActionListener(this);
		equals.addActionListener(this);

		frame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		JButton pressed = (JButton) e.getSource();
		String bt = pressed.getText();
		if (bt.equals("Clear")) {
			clear();
		}

		switch (bt) {
		case "0":
		case "1":
		case "2":
		case "3":
		case "4":
		case "5":
		case "6":
		case "7":
		case "8":
		case "9":
			type(bt);
			break;
		case ".":
			if (!field.getText().contains(".")) {
				if (field.getText().length() == 0) {
					type("0" + bt);
				} else {
					type(bt);
				}
				break;
			}
		}

		if (bt.equals("+")) {
			add();
		}
		
		if (bt.equals("-")) {
			subtract();
		}
		
		if (bt.equals("*")) {
			multiply();
		}
		
		if (bt.equals("/")) {
			divide();
		}

		if (bt.equals("=")) {
			calculate();
		}

	}

	public void clear() {
		num1 = 0;
		num2 = 0;
		field.setText("");
		equation.setText("");
	}

	public void type(String num) {
		field.setText(field.getText() + num);
	}

	public void add() {
		num1 = num1 + Double.parseDouble(field.getText());
		equation.setText(equation.getText() + field.getText() + "+");
		field.setText("");
	}
	
	public void subtract() {
		num1 = num1 - Double.parseDouble(field.getText());
		equation.setText(equation.getText() + field.getText() + "-");
		field.setText("");
	}
	
	public void multiply() {
		num1 = num1 * Double.parseDouble(field.getText());
		equation.setText(equation.getText() + field.getText() + "*");
		field.setText("");
	}
	
	public void divide() {
		num1 = num1 / Double.parseDouble(field.getText());
		equation.setText(equation.getText() + field.getText() + "/");
		field.setText("");
	}

	public void calculate() {

		char op;
		
		if(field.getText().length() > 0) {
			num2 = Double.parseDouble(field.getText());
		}
		if(equation.getText().length() > 0) {
			op = equation.getText().charAt(equation.getText().length() - 1);
		}
		else {
			op = ' ';
		}
		equation.setText(equation.getText() + field.getText());
		switch (op) {
		case '+':
			field.setText((num1 + num2) + "");
			break;
		case '-':
			field.setText((num1 - num2) + "");
			break;
		case '*':
			field.setText((num1 * num2) + "");
			break;
		case '/':
			field.setText((num1 / num2) + "");
			break;
		default:
			field.setText(num2+"");
		}
	}
}
