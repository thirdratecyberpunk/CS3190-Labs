package lewisbb.cs3190.tsp;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class RandomTSPSolution extends TravellingSalesmanProblem{

	public RandomTSPSolution(String inputFile) {
		super(inputFile);
	}

	/**
	 * attempts to solve the TSP by generating random routes and returning the best result after a certain amount of time
	 * @param cities
	 * @param seconds
	 * @return
	 */
	public String solution(int[] exampleRoute, int generations){
		int[] bestRoute = null;
		// and how much it costs
		double bestRouteCost = Double.MAX_VALUE;
		// for the number of seconds given, generate random routes
		for (int i = 0; i <= generations; i++) {
			int[] newRoute = getRandomRoute(exampleRoute);
			double newCost = getCostOfRoute(newRoute);
			if (newCost < bestRouteCost){
				bestRoute = newRoute;
				bestRouteCost = newCost;
			}
		}
		return "The best route is " + Arrays.toString(bestRoute) + " with a cost of " + bestRouteCost + ".";
	}
}
