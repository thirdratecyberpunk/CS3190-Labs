import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

/**
 * class representing swarm of particles for particle swarm optimisation
 * @author Lewis
 *
 */
public class Swarm {
	// instance of antenna array swarm is used for 
	private AntennaArray problemInstance;
	// particles in the swarm
	private ArrayList<Particle> particles;
	// best solution encountered by the swarm
	private static double[] globalBest;
	// cost of the best solution encountered by the swarm
	private double globalBestValue;
	
	
	public Swarm(AntennaArray problemInstance, int problemSize, double inertialCoefficient, double cognitiveCoefficient, double socialCoefficient) {
		this.problemInstance = problemInstance;
		particles = new ArrayList<Particle>();
//		double swarmSize = (20 + Math.floor(Math.sqrt(problemSize)));
		double swarmSize = 1.0;
		globalBest = AntennaArrayProblem.getRandomValidSolution(problemInstance);
		globalBestValue = problemInstance.evaluate(globalBest);
		for (int i = 0; i < swarmSize; i++) {
			particles.add(new Particle(problemInstance, inertialCoefficient, cognitiveCoefficient, socialCoefficient));
		}
	}
	
	/**
	 * attempts to solve the antenna array problem using particle swarm optimisation
	 * @param seconds
	 * @return
	 */
	public String particleSwarmOptimisationSolution(int seconds) {
		long endCondition = System.nanoTime() + TimeUnit.SECONDS.toNanos(seconds);
		//		REPEAT UNTIL ( termination condition IS satisfied )
		while(endCondition > System.nanoTime()){
			//		UPDATE global best;
			for (Particle particle: particles) {
				double particleBest = particle.getPersonalBestValue();
				if (particleBest < globalBestValue) {
					globalBest = particle.getPersonalBest();
					globalBestValue = particleBest;
				}
			}
			//		FOR EACH ( particle in population ) DO
			for (Particle particle: particles) {
				//		1. UPDATE velocity and position;
				particle.updateVelocity();
				particle.updatePosition();
				//		2. EVALUATE new position and UPDATE personal best;
				particle.evaluateCurrentPosition();
			}
		}
		return ("The best solution is " + Arrays.toString(globalBest) + " with a value of " + globalBestValue + ".");
	}
	
	public static double[] getGlobalBest() {
		return globalBest;
	}
}
