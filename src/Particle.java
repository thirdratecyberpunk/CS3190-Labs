/**
 * class representing a particle for particle swarm optimisation
 * @author blackbul
 *
 */
public class Particle {
	// antenna array used in the problem this particle is attempting to solve
	private AntennaArray antennaArray;
	// current position of the particle
	private double[] position;
	// velocity that the particle should move in
	private double[] velocity;
	// best solution encountered by the particle
	private double[] personalBest;
	// value for the best solution encountered by the particle
	private double personalBestValue;
	// mathematical constant
	private static double theta = 1/(2 * Math.log(2));
	// mathematical constant
	private static double phi = 1/2 + Math.log(2);
	
	public Particle(AntennaArray antennaArray) {
		this.antennaArray = antennaArray;
		position = AntennaArrayProblem.getRandomValidSolution(antennaArray);
		personalBest = position;
		personalBestValue = antennaArray.evaluate(position);
	}
}
