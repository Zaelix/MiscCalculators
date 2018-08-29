import java.text.NumberFormat;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class PrimeCalculatorSieve {
	
	public static void main(String[] args) {
		sieveOfEratosthenes(200000000);
	}
	
	public static List<Integer> sieveOfEratosthenes(int n) {
	    boolean prime[] = new boolean[n + 1];
	    Arrays.fill(prime, true);
	    for (int p = 2; p * p <= n; p++) {
	        if (prime[p]) {
	            for (int i = p * 2; i <= n; i += p) {
	                prime[i] = false;
	            }
	        }
	    }
	    List<Integer> primeNumbers = new LinkedList<>();
	    int count = 0;
	    for (int i = 2; i <= n; i++) {
	        if (prime[i]) {
	            primeNumbers.add(i);
				//String y = NumberFormat.getNumberInstance(Locale.US).format(i);
				//String z = NumberFormat.getNumberInstance(Locale.US).format(count);
	            //System.out.println(y + " is prime number " + z);
	            System.out.println(i + " is prime number " + count);
	            count++;
	        }
	    }
	    return primeNumbers;
	}
}
