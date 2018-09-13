package vaseis;

import java.util.*;


public class ZipfGenerator {
	
	private Random rand;
	
	/**
	 * constructs a power law object using an external random generator
	 * @param r random generator passed in
	 */
	public ZipfGenerator(Random r){
		rand=r;
	}
	/**
	 * constructs a power law object using an internal random generator
	 */
	public ZipfGenerator(){
		rand=new Random();
	}
	
	/**
	 * get uniformly distributed double in [0, 1]
	 */ 
	public double getRand () {
		return rand.nextDouble();
	}
	
	/**
	 * get uniformly distributed integer in [0, N - 1]
	 */
	public int getRandInt (int N) {
		return rand.nextInt(N);
	}
	

	
	/**
	 * selects item using power law
	 * probability of selecting array item:
	 * p(ni) = k * (ni^p)
	 * k is a normalisation constant
	 * p(ni) = 0 if ni is zero, even when p < 0
	 * 
	 * 
	 * @param nums array of numbers ni
	 * @param p exponent p
	 * @return index in [0, array size - 1]
	 */

	public int select (double[] nums, double p) {
		// make array of probabilities
		double[] probs = new double[nums.length];
		for (int i = 0; i < probs.length; i++) {
			if (nums[i] == 0)  
				probs[i] = 0; 	
			else 
				probs[i] = Math.pow(nums[i], p); 
		}
		
		// sum probabilities
		double sum = 0;
		for (int i = 0; i < probs.length; i++) {
			sum += probs[i];
		}
		
		// obtain random number in range [0, sum]
		double r = sum * getRand();
		
		// subtract probs until result negative
		// no of iterations gives required index
		int i;
		for (i = 0; i < probs.length; i++) {
			r -= probs[i];
			if (r < 0) { break; }
		}
		return i;
	}
	
	
	/**
	 * select item using Zipf's law
	 * @param size of ranked array
	 * @return index in [0, array size - 1]
	 */ 
	public int zipf (int size) {
		// make array of numbers
		double[] nums = new double[size];
		for (int i = 0; i < nums.length; i++) {
			nums[i] = i + 1;
		}
		// get index using special case of power law
		return select(nums, -1.0);
	}
	
	
	/**
	 * test purpose main
	 * @param args command line inputs
	 */
	public static void main (String [] args) {
		ZipfGenerator p=new ZipfGenerator(new Random(System.currentTimeMillis()));

		for (int i = 0; i < 10; i++) {
			System.out.println("Zipf: " + p.zipf(2));
		}
	}
}