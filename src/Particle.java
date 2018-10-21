/**
 * class representing a particle for particle swarm optimisation
 * @author Lewis
 *
 */
public class Particle {
	// antenna array used in the problem this particle is attempting to solve
	private AntennaArray antennaArray;
	// current position of the particle
	private double[] position;
	// previous position of the particle
	private double[] previousPosition;
	// velocity that the particle should move in represented as a vector
	private double[] velocity;
	// best solution encountered by the particle
	private double[] personalBest;
	// value for the best solution encountered by the particle
	private double personalBestValue;
	// inertial coefficient representing tendency to continue moving in same direction
	private double inertialCoefficient;
	// cognitive coefficient representing a particle's "memory"
	private double cognitiveCoefficient;
	// social coefficient representing the best known result according to other particles
	private double socialCoefficient;
	
	public Particle(AntennaArray antennaArray, double inertialCoefficient, double cognitiveCoefficient, double socialCoefficient) {
		this.antennaArray = antennaArray;
		this.inertialCoefficient = inertialCoefficient;
		this.cognitiveCoefficient = cognitiveCoefficient;
		this.socialCoefficient = socialCoefficient;
		position = AntennaArrayProblem.getRandomValidSolution(antennaArray);
		velocity = new double[]{0.0, 0.0, 0.0};
		personalBest = position;
		personalBestValue = antennaArray.evaluate(position);
	}
	
	/**
	 * updates this particle's position by adding the particle's vector to its current position
	 */
	public void updatePosition() {
		previousPosition = position;
		for (int i = 0; i < position.length; i++) {
			position[i] = position[i] + velocity[i];
		}
	}
	
	/**
	 * updates this particle's vector
	 */
	public void updateVelocity() {
		
	}
	
	/**
	 * evaluates if the particle's current position is better than the best position encountered, then updates it accordingly
	 */
	public void evaluateCurrentPosition() {
		double positionEvaluation = antennaArray.evaluate(position);
		if (positionEvaluation < personalBestValue) {
			personalBest = position;
			personalBestValue = positionEvaluation;
		}
	}
	
	/**
	 * returns the best solution encountered by this particle
	 * @return
	 */
	public double[] getPersonalBest() {
		return personalBest;
	}
	
	/**
	 * returns the value of the best solution encountered by this particle
	 * @return
	 */
	public double getPersonalBestValue() {
		return personalBestValue;
	}
}
