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
	private static double globalBestValue;
	
	public Swarm(AntennaArray problemInstance, int problemSize, double inertialCoefficient, double cognitiveCoefficient, double socialCoefficient) {
		this.problemInstance = problemInstance;
		particles = new ArrayList<Particle>();
		double swarmSize = (20 + Math.floor(Math.sqrt(problemSize)));
		globalBestValue = Double.MAX_VALUE;
		for (int i = 0; i < swarmSize; i++) {
			particles.add(new Particle(problemInstance, inertialCoefficient, cognitiveCoefficient, socialCoefficient));
		}
	}
	
	/**
	 * attempts to solve the antenna array problem using particle swarm optimisation
	 * @param seconds
	 * @return
	 */
	public String particleSwarmOptimisationSolution(long seconds) {
		BasicEx swarmVisualiser = new BasicEx();
		swarmVisualiser.setVisible(true);
		long endCondition = System.nanoTime() + TimeUnit.SECONDS.toNanos(seconds);
		//		REPEAT UNTIL ( termination condition IS satisfied )
		while(endCondition > System.nanoTime()){
			//		UPDATE global best;
			for (Particle particle: particles) {
				double[] particleBest = particle.getPersonalBest();
				double particleBestValue = particle.getPersonalBestValue();
				if (problemInstance.is_valid(particleBest) && particleBestValue < globalBestValue) {
					globalBest = particleBest;
					globalBestValue = particleBestValue;
				}
			}
			//		FOR EACH ( particle in population ) DO
			for (Particle particle: particles) {
				// UPDATE velocity and position
				particle.updateVelocity();
				particle.updatePosition();
				// then EVALUATE new position and UPDATE personal best;
				particle.evaluateCurrentPosition();
			}
			swarmVisualiser.updatePositions(this);
		}
		return ("The best solution is " + Arrays.toString(globalBest) + "\nVALID : " + problemInstance.is_valid(globalBest) + " \nValue of " + globalBestValue + ".");
	}
	
	public static double[] getGlobalBest() {
		return globalBest;
	}
	
	public int getSwarmSize() {
		return particles.size();
	}
	
	public Particle getParticleAtLocation(int location) {
		return particles.get(location);
	}
}
