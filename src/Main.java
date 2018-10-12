import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
/**
 * Implementation of the travelling salesperson problem used for CS3910.
 * @author Lewis
 */
public class Main {
	// matrix containing nodes in a representation of the problem
	private double[][] nodes;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
//		int[] route = new int[4];
//		int[] route = {0,1,2,3};
//		nodes = getExampleMatrix(new double[4][4]);
		
//	    int[] route = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15};
//		nodes = getMatrixFromCSV("ulysses16.csv");		
//		System.out.println("Travelling Salesperson");
//		System.out.println("Random search:");
//		System.out.println(randomTSPSolution(route, 1));
//		System.out.println("Two OPT local search:");
//		System.out.println(twoOptTSPSolution(route, 1));
		AntennaArray aa = new AntennaArray(3, 90);
		double[] design = {0.2, 0.8, 1.5};
		System.out.println(aa.is_valid(design));
		System.out.println(aa.evaluate(design));
	}

	/**
	 * Returns the cost of a route given an array of nodes to visit
	 * @param routeToEvaluate
	 * @return
	 */
	private double getCostOfRoute(int[] routeToEvaluate) {
		double cost = 0;
		for (int i = 0; i < routeToEvaluate.length - 1; i++) {
			int x = routeToEvaluate[i];
			int y = routeToEvaluate[i + 1];
			cost += nodes[x][y];
		}
		return cost;
	}
	
	/**
	 * Returns a legal randomised route given an array of nodes that it needs to visit
	 * Based on the Durstenfield shuffle
	 * @param destinations
	 * @return
	 */
	private int[] getRandomRoute(int[] sourceRoute) {
		Random rdm = new Random();
		int[] randomisedRoute = new int[sourceRoute.length];
		int[] toRandomise = sourceRoute.clone();
		for (int i = toRandomise.length - 1; i > 0; i--) {
			int index = rdm.nextInt(i+1);
			int a = toRandomise[index];
			toRandomise[index] = toRandomise[i];
			toRandomise[i] = a;
		}
		for (int i = 0; i < toRandomise.length; i++) {
			randomisedRoute[i] = toRandomise[i];
		}
		return randomisedRoute;
	}
	
	/**
	 * Returns the length of a hypothenuse given the lengths of two sides
	 */
	private double getCostBetweenCities(double xa, double xb, double ya, double yb) {
		return Math.sqrt(Math.pow(xa - xb, 2) + Math.pow(ya - yb, 2));
	}
	
	/**
	 * populates a matrix based on the values in the csv
	 * @param filename
	 * @return
	 */
	private double[][] getMatrixFromCSV(String filename){
		// load in the csv to count the length
		Scanner reader;
		int lines = 0;
		File filenameFile = new File(filename);
		try {
			// counts the number of lines in the csv
			BufferedReader br = new BufferedReader(new FileReader(filename));
			while (br.readLine() != null){
				lines++;
			}
			br.close();
			lines -= 2;
			
			// defines a matrix the size of the number of cities 
			double[][] csvMatrix = new double[lines][lines];
			// defines an array to hold the values from the csv
			String[][] cityValues = new String[lines][3];

			BufferedReader br2 = new BufferedReader(new FileReader(filename));
			int cityIncrement = 0;
			String line = "";
			while ((line = br2.readLine()) != null) {
				if (line.split(",")[0].matches("[0-9]+")){
					cityValues[cityIncrement] = line.split(",");
					cityIncrement++;
				}
			}
			// populates a matrix with the calculated distances from the csv contents
			for (int x = 0; x < lines - 1; x++) {
				for (int y = 0; y < lines - 1; y++) {
					double fromX = Double.parseDouble(cityValues[x][1]);
					double fromY = Double.parseDouble(cityValues[x][2]);
					double toX = Double.parseDouble(cityValues[y][1]);
					double toY = Double.parseDouble(cityValues[y][2]);
					csvMatrix[x][y] = getCostBetweenCities(fromX, toX, fromY, toY);
				}
			}
			return csvMatrix;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * returns the hardcoded Travelling Salesperson structure example from Lab 1 
	 */
	private double[][] getExampleMatrix(double[][] nodes){
		nodes[0][0] = 0;
		nodes[0][1] = 20;
		nodes[0][2] = 42;
		nodes[0][3] = 35;
		nodes[1][0] = 20;
		nodes[1][1] = 0;
		nodes[1][2] = 30;
		nodes[1][3] = 34;
		nodes[2][0] = 42;
		nodes[2][1] = 30;
		nodes[2][2] = 0;
		nodes[2][3] = 12;
		nodes[3][0] = 35;
		nodes[3][1] = 34;
		nodes[3][2] = 12;
		nodes[3][3] = 0;
		return nodes;
	}
	
	/**
	 * attempts to solve the TSP by generating random routes and returning the best result after a certain amount of time
	 * @param cities
	 * @param seconds
	 * @return
	 */
	private String randomTSPSolution(int[] exampleRoute, int seconds){
		// stores the best result
		int[] bestRoute = null;
		// and how much it costs
		double bestRouteCost = Double.MAX_VALUE;
		// for the number of seconds given, generate random routes
		long endCondition = System.nanoTime() + TimeUnit.SECONDS.toNanos(seconds);
		while(endCondition > System.nanoTime()){
			int[] newRoute = getRandomRoute(exampleRoute);
			double newCost = getCostOfRoute(newRoute);
			if (newCost < bestRouteCost){
				bestRoute = newRoute;
				bestRouteCost = newCost;
			}
		}
		return "The best route is " + Arrays.toString(bestRoute) + " with a cost of " + bestRouteCost + ".";
	}
	
	/**
	 * attempts to solve the travelling salesperson problem
	 * @param exampleRoute
	 * @param seconds
	 * @return
	 */
	private String twoOptTSPSolution(int[] exampleRoute, int seconds) {
		// stores the best result
		int[] bestRoute = null;
		// and how much it costs
		double bestRouteCost = Double.MAX_VALUE;
		// for the number of seconds given, generate random routes
		long endCondition = System.nanoTime() + TimeUnit.SECONDS.toNanos(seconds);
		while(endCondition > System.nanoTime()){
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