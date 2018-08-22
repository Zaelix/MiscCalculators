
public class PrimeCalculator {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrimeCalculator pc = new PrimeCalculator();
		pc.start();
	}

	public void start() {
		int x = 0;
		int nth = 1;
		while(true) {
			if(numberIsPrime(x)) {
				System.out.println(x + " is the " +nth + "th Prime");
				nth++;
			}
			x++;
		}
	}
	
	public boolean numberIsPrime(int n) {
		for (int i = 2; i < n; i++) {
			if(n%i==0) {
				return false;
			}
		}
		return true;
	}
}
