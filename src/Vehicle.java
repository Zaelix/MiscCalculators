

public abstract class Vehicle {
	public boolean canFly;
	public int totalMiles;
	
	int getTotalMiles() {return totalMiles;}
	abstract int calculateMilage();
	void refuel() {}
	abstract void operate();
}
class Car extends Vehicle {
	int calculateMilage() {return 0;}
	void refuel() {}
	void operate() {}
	
}
