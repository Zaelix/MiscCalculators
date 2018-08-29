import java.text.NumberFormat;
import java.util.Locale;

public class PrimeCalculator {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrimeCalculator pc = new PrimeCalculator();
		pc.start();
	}

	public void start() {
		int x = 3;
		int nth = 1;
		while (true) {
			if (isPrime(x)) {
				if (nth % 1000 == 0) {
					String y = NumberFormat.getNumberInstance(Locale.US).format(x);
					String z = NumberFormat.getNumberInstance(Locale.US).format(nth);
					System.out.println(y + " is the " + z + "th Prime");
				}
				nth++;
			}
			x++;
		}
	}

	boolean isPrime(int n) {
		// check if n is a multiple of 2
		if (n % 2 == 0)
			return false;
		// if not, then just check the odds
		int root = (int) Math.ceil(Math.sqrt(n));
		for (int i = 3; i <= root; i += 2) {
			if (n % i == 0)
				return false;
		}
		return true;
	}
}
