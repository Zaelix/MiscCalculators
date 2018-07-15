
public class Tape {
	String name;
	// in mm
	double thickness;
	// in mm
	double width = 48;
	// in N/cm
	double tensile;
	// in N/cm
	double adhesion;
	
	double[] validWidths;
	
	public Tape(String name, double thickness, double tensile, double adhesion, double[] widths) {
		this.name = name;
		this.tensile = tensile;
		this.adhesion = adhesion;
		validWidths = widths;
	}
	
	public void setWidth(double w) {
		this.width = w;
	}
	
	
}
