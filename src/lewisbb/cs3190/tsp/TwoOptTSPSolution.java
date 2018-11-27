package lewisbb.cs3190.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class TwoOptTSPSolution extends TravellingSalesmanProblem{
	
	public TwoOptTSPSolution(String inputFile) {
		super(inputFile);
	}
	
	/**
	 * attempts to solve the travelling salesperson problem
	 * @param exampleRoute
	 * @param seconds
	 * @return
	 */
	public String solution(int[] exampleRoute, int generations) {
		// stores the best result
		int[] bestRoute = null;
		// and how much it costs
		double bestRouteCost = Double.MAX_VALUE;
		// for the number of seconds given, generate random routes
		for (int i = 0; i <= generations; i++){
			// generate a random route
			int[] newRandomRoute = getRandomRoute(exampleRoute);
			// get the local optima for it
			bestRoute = getLocalOptima(newRandomRoute);
			bestRouteCost = getCostOfRoute(bestRoute);
		}
		return "The best route according to twoOptTSPSolution is " + Arrays.toString(bestRoute) + " with a cost of " + bestRouteCost + ".";
	}
	
	/**
	 * returns the local optima for a given route
	 * @param localOptimaRandom
	 * @return
	 */
	private int[] getLocalOptima(int[] localOptimaRandom) {
		// contains the best solution from the previous neighbourhood
		int[] previousBest = localOptimaRandom;
		// contains the best solution from the current neighbourhood
		int[] currentBest = new int[localOptimaRandom.length];
		// generate the neighbourhood of solutions for the route
		while (!Arrays.equals(previousBest, currentBest)) {
			ArrayList<int[]> neighbourhood = getTwoOptNeighbourhood(previousBest);
			// find the best solution in that neighbourhood
			currentBest = getBestRouteInNeighbourhood(neighbourhood);
			// if that route is the best solution in the neighbourhood, return it as the optimum
			if (Arrays.equals(currentBest, previousBest)) {
				return currentBest;
			}
			// otherwise, generate a new neighbourhood with the new solution
			else {
				previousBest = currentBest;
				currentBest = new int[localOptimaRandom.length];
			}
		}
		System.out.println("currentBest : " + Arrays.toString(currentBest));
		return currentBest;
	}
	
	/**
	 * Returns the 2 opt neighbourhood of a given route.
	 * @param tour
	 * @return
	 */
	private ArrayList<int[]> getTwoOptNeighbourhood(int[] tour){
		ArrayList<int[]> neighbourhood = new ArrayList<int[]>();
		// for every item in the tour
		for (int x = 0; x <= tour.length -1; x++) {
			// switches with every item in the tour
			for (int y = 0; y <= tour.length -1; y++) {
				int[] twoOpt = tour.clone();
				int tmp = twoOpt[y];
				twoOpt[y] = twoOpt[x];
				twoOpt[x] = tmp;
				boolean match = false;
				// if the NEW tour already exists in the neighbourhood, don't add it again
				for (int[] route: neighbourhood) {
					if (Arrays.equals(twoOpt,route)) {
						match = true;
					}
				}
				if (match == false) {
					neighbourhood.add(twoOpt);
				}
			}
		}
		return neighbourhood;
	}
	
	/**
	 * Returns the best route in a given neighbourhood.
	 * @param neighbourhood
	 * @return
	 */
	private int[] getBestRouteInNeighbourhood(ArrayList<int[]> neighbourhood) {
		int[] bestRoute = null;
		double bestCost = Double.MAX_VALUE;
		for (int[] route: neighbourhood) {
			double cost = getCostOfRoute(route);
			if (cost < bestCost) {
				bestRoute = route;
				bestCost = cost;
			}
		}
		return bestRoute;
	}
}
