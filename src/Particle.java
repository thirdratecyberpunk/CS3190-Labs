import java.util.Arrays;
import java.util.Random;

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
		System.out.println(Arrays.toString(position));
		velocity = getDifferenceBetweenVectors(AntennaArrayProblem.getRandomValidSolution(antennaArray), position);
		personalBest = position;
		personalBestValue = antennaArray.evaluate(position);
	}
	
	/**
	 * updates this particle's position by adding the particle's vector to its current position
	 */
	public void updatePosition() {
		double[] tmpPosition = position;
		for (int i = 0; i < tmpPosition.length; i++) {
			tmpPosition[i] = tmpPosition[i] + velocity[i];
		}
		System.out.println("Position : " + Arrays.toString(tmpPosition));
		System.out.println("Position valid : " + antennaArray.is_valid(tmpPosition));
		if (antennaArray.is_valid(tmpPosition)){
			position = tmpPosition;
			double positionValue = antennaArray.evaluate(position);
			if (positionValue < personalBestValue){
				personalBest = position;
				personalBestValue = positionValue;
			}
		}
		else{
//			System.exit(1);
		}
		System.out.println("________________");
//		System.exit(1);
	}
	
	/**
	 * updates this particle's vector
	 */
	public void updateVelocity() {
		double[] random0 = generateRandomUniformVector(velocity.length);
		double[] random1 = generateRandomUniformVector(velocity.length);
		// calculates differences
		double[] personalBestVelocityDifference = getDifferenceBetweenVectors(personalBest, velocity);
		double[] personalBestGlobalBestDifference = getDifferenceBetweenVectors(Swarm.getGlobalBest(), personalBest);
		double[] newVelocity = new double[velocity.length];
		for (int i = 0; i < velocity.length - 1; i++){
			newVelocity[i] = (inertialCoefficient * velocity[i]) + (cognitiveCoefficient * random0[i] * personalBestVelocityDifference[i]) + (socialCoefficient * random1[i] * personalBestGlobalBestDifference[i]);
		}
		velocity = newVelocity;
		System.out.println("Velocity : " + Arrays.toString(velocity));
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
	
	/**
	 * takes two identical size vectors and returns a new vector of the difference between the two
	 * @return vectorDifference
	 */
	public double[] getDifferenceBetweenVectors(double[] vector0, double[] vector1) {
		double[] vectorDifference = new double[vector0.length];
		// error handling
		if (vector0.length != vector1.length){
			for (int i = 0; i < vector0.length; i++){
				vectorDifference[i] = Double.MAX_VALUE;
			}
		}
		else{
			for (int i = 0; i < vector0.length; i++){
				vectorDifference[i] = vector0[i] - vector1[i];
			}
		}
		return vectorDifference;
	}
	
	/**
	 * returns a new random uniform vector
	 * @param dimensions
	 * @return
	 */
	public double[] generateRandomUniformVector(int dimensions) {
		double[] newUniformVector = new double[dimensions];
		Random r = new Random();
		for (int i = 0; i < dimensions; i++) {
			newUniformVector[i] = r.nextDouble();
		}
		return newUniformVector;
	}
}
