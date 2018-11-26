package lewisbb.cs3190.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ArtificialImmuneSystemTSPSolution extends TravellingSalesmanProblem{
	// initial population of solutions and an associated cost of the route
	private ArrayList<Tuple<int[], Double>> populationSolutions;
	// clonal pool where children of parents are stored
	private ArrayList<Tuple<int[], Double>> clonalPool;
	// number tracking the number of initial solutions
	private int populationSize;
	// number tracking the rate of replacement in the population pool
	private int replacementRate;
	// clone size factor (TODO: improve this comment)
	private int cloneSizeFactor;
	// best fitness encountered
	private double bestFitness;

	public ArtificialImmuneSystemTSPSolution(String inputFile, int populationSize, int replacementRate, int cloneSizeFactor, long seconds) throws CloneNotSupportedException {
		super(inputFile);
		this.populationSize = populationSize;
		this.replacementRate = replacementRate;
		this.cloneSizeFactor = cloneSizeFactor;
		populationSolutions = new ArrayList<Tuple<int[], Double>>();
		clonalPool = new ArrayList<Tuple<int[], Double>>();
		// creating the initial population
		for (int i = 0; i < populationSize; i++) {
			int[] newRoute = getRandomRoute(route);
			double newRouteCost = getCostOfRoute(newRoute);
			Tuple<int[], Double> newRouteAndCost = new Tuple<int[], Double>(newRoute, newRouteCost);
			populationSolutions.add(newRouteAndCost);
		}
		populationSolutions = sortPopulationPool(populationSolutions);
		bestFitness = populationSolutions.get(0).getY();
	}

	/**
	 * 
	 * @param seconds
	 * @param p
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@SuppressWarnings("unchecked") 
	public Tuple<int[], Double> solution(long seconds, double p) throws CloneNotSupportedException {
		// create clones of initial population and store them in clone pool
		for (int i = 0; i <= cloneSizeFactor; i++) {
			for (Tuple<int[], Double> solution: populationSolutions) {
				clonalPool.add((Tuple<int[], Double>) solution.clone());
			}
		}
		clonalPool = sortPopulationPool(clonalPool);
		Random rdm = new Random();
		long endCondition = System.nanoTime() + TimeUnit.SECONDS.toNanos(seconds);
		while(endCondition > System.nanoTime()){
			// for all clones in pool, apply inverse fitness proportional hyper mutation
			for (int i = 0; i < clonalPool.size(); i++) {
				Tuple<int[], Double> candidate = clonalPool.get(i);
				// calculate inverse fitness for the candidate
				double inverseFitness = 1 - normaliseFitness(candidate.getY());
				// calculate mutation rate : apply mutation with low rate to good solutions, high rate to bad solutions
				double mutationRate = Math.exp(-p * inverseFitness) / 10;
				if (rdm.nextDouble() < mutationRate) {
					Tuple<int[], Double> mutatedRoute = mutateRoute(candidate, mutationRate);
					clonalPool.set(i, mutatedRoute);
				}
				else {
				}
			}
			// select replacement rate best solutions from parents and clone pool
			ArrayList<Tuple<int[],Double>> combinedPool = new ArrayList<Tuple<int[],Double>>();
			clonalPool = sortPopulationPool(clonalPool);
			combinedPool.addAll(populationSolutions);
			for (int x = 0; x < populationSize; x++) {
				combinedPool.add(clonalPool.get(x));
			}
			combinedPool = sortPopulationPool(combinedPool);
			populationSolutions.clear();
			for (int x = 0; x < populationSize; x++) {
				populationSolutions.add(combinedPool.get(x));
			}
			populationSolutions = sortPopulationPool(populationSolutions);
			// metadynamics : replace the lowest fitness replacementRate solutions with random solutions
			for (int x = populationSolutions.size() - 1; x > populationSolutions.size() - (replacementRate); x--) {
				int[] newRoute = getRandomRoute(route);
				double newRouteCost = getCostOfRoute(newRoute);
				populationSolutions.set(x, new Tuple<int[], Double>(newRoute, newRouteCost));
			}
			//update best fitness encountered
			populationSolutions = sortPopulationPool(populationSolutions);
			bestFitness = populationSolutions.get(0).getY();
		}
		populationSolutions = sortPopulationPool(populationSolutions);
		return populationSolutions.get(0);
	}
	
	private Tuple<int[], Double> mutateRoute(Tuple<int[], Double> originalRoute, double mutationRate) throws CloneNotSupportedException{
		// for the number of mutations
		Random rdm = new Random();
		int[] newRouteArray = originalRoute.getX();
		for (int i = 0; i < mutationRate; i++) {
			int index0 = rdm.nextInt(newRouteArray.length - 1);
			int index1 = rdm.nextInt(newRouteArray.length - 1);
			if (index0 == index1 && index0 != 0) {
				index0 = index0 + 1 % index0;
			}
			else if(index0 == index1 && index0 != 0) {
				index0 = 1;
			}
			int tmp = newRouteArray[index0];
			newRouteArray[index0] = newRouteArray[index1];
			newRouteArray[index1] = tmp;
		}
		return new Tuple<int[], Double>(newRouteArray, getCostOfRoute(newRouteArray));
	}
	
	
	/**
	 * method to sort a population into lowest cost to highest
	 * @return 
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<Tuple<int[], Double>> sortPopulationPool(ArrayList<Tuple<int[], Double>> populationPool){
		ArrayList<Tuple<int[],Double>> sortedPool = (ArrayList<Tuple<int[], Double>>) populationPool.clone();
		boolean changeOccurred = true;
		while(changeOccurred){
			changeOccurred = false;
			for(int i = 0; i < sortedPool.size() - 1; i++){
				if(sortedPool.get(i).getY() > sortedPool.get(i+1).getY()){
					Collections.swap(sortedPool, i, i + 1);
					changeOccurred = true;
				}
			}
		}
	return sortedPool;
	}
	
	/**
	 * returns a normalised fitness, where a normalised fitness is the fitness divided by the best fitness
	 * @param fitness
	 * @param bestFitness
	 * @return
	 */
	private double normaliseFitness(double fitness) {
		return (fitness/bestFitness);
	}
}
