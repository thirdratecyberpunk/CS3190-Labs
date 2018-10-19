import java.util.ArrayList;

/**
 * class representing swarm of particles for particle swarm optimisation
 * @author blackbul
 *
 */
public class Swarm {
	// instance of antenna array swarm is used for 
	private AntennaArray problemInstance;
	// particles in the swarm
	private ArrayList<Particle> particles;
	// best solution encountered by the swarm
	private double[] globalBest;
	
	public Swarm(AntennaArray problemInstance) {
		this.problemInstance = problemInstance;
		globalBest = AntennaArrayProblem.getRandomValidSolution(problemInstance);
	}
}
