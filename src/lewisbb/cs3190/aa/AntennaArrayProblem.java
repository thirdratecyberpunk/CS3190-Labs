package lewisbb.cs3190.aa;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Implementation of the antenna array problem used in CS3910.
 * @author Lewis
 *
 */
public class AntennaArrayProblem {

	public static void main(String[] args) {
		new AntennaArrayProblem();
	}
	
	public AntennaArrayProblem() {
		AntennaArray antennaArray = new AntennaArray(3, 90);
		System.out.println("Antenna Array design problem");
		double inertialCoefficient = 0.721;
		double phi = 1.1193;
		double cognitiveCoefficient = phi;
		double socialCoefficient = phi;
		System.out.println("Random search solution:");
		System.out.println(getRandomSearchSolution(antennaArray, 4));
		System.out.println("Particle swarm optimisation solution:");
		Swarm swarm = new Swarm(antennaArray, 3, inertialCoefficient, cognitiveCoefficient, socialCoefficient);
		System.out.println(swarm.particleSwarmOptimisationSolution((long) 4));
	}
	
	/**
	 * Returns a valid double of positions given an AntennaArray instance.
	 * @param antennaArray
	 * @return
	 */
	public static double[] getRandomValidSolution(AntennaArray antennaArray) {
		boolean valid = false;
		double[] newRandomSolution = null;
		double[][] bounds = antennaArray.bounds();
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
			if (antennaArray.is_valid(newRandomSolution)) {
				valid = true;
			}
			Arrays.sort(newRandomSolution);
		}
		return newRandomSolution;
	}
	
	/**
	 * attempts to solve the antenna problem by generating random antennas and returning the best result after a certain amount of time
	 * @param aa
	 * @param seconds
	 * @return
	 */
	private String getRandomSearchSolution(AntennaArray aa, int seconds){
		double[] bestSolution = null;
		double bestSolutionCost = Double.MAX_VALUE;
		long endCondition = System.nanoTime() + TimeUnit.SECONDS.toNanos(seconds);
		while(endCondition > System.nanoTime()){
			double[] newSolution = getRandomValidSolution(aa);
			double newCost = aa.evaluate(newSolution);
			if (newCost < bestSolutionCost){
				bestSolution = newSolution;
				bestSolutionCost = newCost;
			}
		}
		return "The best solution is " + Arrays.toString(bestSolution) + " with a value of " + bestSolutionCost + ".";
	}
}
