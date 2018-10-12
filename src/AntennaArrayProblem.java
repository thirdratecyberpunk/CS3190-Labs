import java.util.Arrays;
import java.util.Random;

/**
 * Implementation of the antenna array problem used in CS3910.
 * @author lewis
 *
 */
public class AntennaArrayProblem {

	public static void main(String[] args) {
		new AntennaArrayProblem();
	}
	
	public AntennaArrayProblem() {
		AntennaArray aa = new AntennaArray(3, 90);
		double[] design = getRandomValidSolution(aa);
		System.out.println("Antenna Array design problem");
		System.out.println(Arrays.toString(design));
		System.out.println(aa.evaluate(design));
	}
	
	/**
	 * Returns a valid double of positions given an AntennaArray instance.
	 * @param aa
	 * @return
	 */
	private double[] getRandomValidSolution(AntennaArray aa) {
		boolean valid = false;
		double[] newRandomSolution = null;
		double[][] bounds = aa.bounds();
		double maxApertureSize = (bounds.length) / 2.0;
		while (valid == false) {
			newRandomSolution = new double[bounds.length];
			// generates a number between the bounds for each 
			for (int i = 0; i < bounds.length - 1; i++) {
				double start = bounds[i][0];
				double end = bounds[i][1];
				double random = new Random().nextDouble();
				double result = start + (random * (end - start));
				newRandomSolution[i] = result;
			}
			// sets the final result to the maximum aperture size
			newRandomSolution[bounds.length - 1] = maxApertureSize;
			if (aa.is_valid(newRandomSolution)) {
				valid = true;
			}
		}
		return newRandomSolution;
	}

}
