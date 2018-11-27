package lewisbb.cs3190.tsp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class ArtificialImmuneSystemTSPSolution extends TravellingSalesmanProblem{
	// initial population of solutions and an associated cost of the route
	private ArrayList<SolutionTuple> populationSolutions;
	// clonal pool where children of parents are stored
	private ArrayList<SolutionTuple> clonalPool;
	// number tracking the number of initial solutions
	private int populationSize;
	// number tracking the rate of replacement in the population pool
	private int replacementRate;
	// clone size factor (TODO: improve this comment)
	private int cloneSizeFactor;
	// best solution encountered
	private SolutionTuple bestSolution;
	// best fitness encountered
	private double bestFitness;

	public ArtificialImmuneSystemTSPSolution(String inputFile, int populationSize, int replacementRate, int cloneSizeFactor) throws CloneNotSupportedException {
		super(inputFile);
		this.populationSize = populationSize;
		this.replacementRate = replacementRate;
		this.cloneSizeFactor = cloneSizeFactor;
		populationSolutions = new ArrayList<SolutionTuple>();
		clonalPool = new ArrayList<SolutionTuple>();
		// creating the initial population
		for (int i = 0; i < populationSize; i++) {
			int[] newRoute = getRandomRoute(route);
			double newRouteCost = getCostOfRoute(newRoute);
			SolutionTuple newRouteAndCost = new SolutionTuple(newRoute, newRouteCost);
			populationSolutions.add(newRouteAndCost);
		}
		populationSolutions = quickSort(populationSolutions, 0, populationSolutions.size() - 1);
		bestSolution = populationSolutions.get(0);
		bestFitness = bestSolution.getY();
	}

	/**
	 * 
	 * @param seconds
	 * @param p
	 * @return
	 * @throws CloneNotSupportedException
	 */
	@SuppressWarnings("unchecked") 
	public SolutionTuple solution(long generations, double p) throws CloneNotSupportedException {
		// create clones of initial population and store them in clone pool
		for (int i = 0; i <= cloneSizeFactor; i++) {
			for (SolutionTuple solution: populationSolutions) {
				clonalPool.add((SolutionTuple) solution.clone());
			}
		}
		clonalPool = quickSort(clonalPool, 0, populationSolutions.size() - 1);
		Random rdm = new Random();
		
		for (int i = 0; i <= generations; i++) {
			System.out.println("Generation : " + i);
			// for all clones in pool, apply inverse fitness proportional hyper mutation
			for (int j = 0; j < clonalPool.size(); j++) {
				SolutionTuple candidate = clonalPool.get(j);
				// calculate inverse fitness for the candidate
				double inverseFitness = 1 - normaliseFitness(candidate.getY());
				// calculate mutation rate : apply mutation with low rate to good solutions, high rate to bad solutions
				double mutationRate = Math.exp(inverseFitness);
				if (rdm.nextDouble() < mutationRate) {
					SolutionTuple mutatedRoute = mutateRoute(candidate, mutationRate);
					clonalPool.set(j, mutatedRoute);
				}
				else {
				}
			}
			// select replacement rate best solutions from parents and clone pool
			ArrayList<SolutionTuple> combinedPool = new ArrayList<SolutionTuple>();
			clonalPool = quickSort(clonalPool, 0, populationSolutions.size() - 1);
			combinedPool.addAll(populationSolutions);
			for (int x = 0; x < populationSize; x++) {
				combinedPool.add(clonalPool.get(x));
			}
			clonalPool = quickSort(clonalPool, 0, populationSolutions.size() - 1);
			populationSolutions.clear();
			for (int x = 0; x < populationSize; x++) {
				populationSolutions.add(combinedPool.get(x));
			}
			populationSolutions = quickSort(populationSolutions, 0, populationSolutions.size() - 1);
			// metadynamics : replace the lowest fitness replacementRate solutions with random solutions
			for (int x = populationSolutions.size() - 1; x > populationSolutions.size() - (replacementRate); x--) {
				int[] newRoute = getRandomRoute(route);
				double newRouteCost = getCostOfRoute(newRoute);
				populationSolutions.set(x, new SolutionTuple(newRoute, newRouteCost));
			}
			//update best fitness encountered
			populationSolutions = quickSort(populationSolutions, 0, populationSolutions.size() - 1);
			bestSolution = populationSolutions.get(0);
			bestFitness = bestSolution.getY();
			System.out.println("Best solution so far: " + Arrays.toString(bestSolution.getX()) + " : " + bestFitness);
		}
		return populationSolutions.get(0);
	}
	
	private SolutionTuple mutateRoute(SolutionTuple originalRoute, double mutationRate) throws CloneNotSupportedException{
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
		return new SolutionTuple(newRouteArray, getCostOfRoute(newRouteArray));
	}

	
	/**
	 * method to quick sort a population pool
	 */
	@SuppressWarnings("unchecked")
	private ArrayList<SolutionTuple> quickSort(ArrayList<SolutionTuple> pool, int lowerIndex, int higherIndex){
		// if the arraylist is empty, end the sort
		if (pool.size() == 0) {
			return null;
		}
		// starting from the first element and last element
		int i = lowerIndex;
		int j = higherIndex;
		// take the pivot
		int pivot = lowerIndex + (higherIndex - lowerIndex) / 2;
		SolutionTuple pivotSolution = pool.get(pivot);
		double pivotFitness = pivotSolution.getY();
		while (i <= j) {
			// find a number from the left of the pivot that is greater than the pivot
			while (pool.get(i).getY() < pivotFitness && i > 0 && i < higherIndex) {
				i++;
			}
			// find a number from the right of the pivot that is less than the pivot
			while (pool.get(j).getY() > pivotFitness && j > 0 && j > lowerIndex) {
				j--;
			}
			// exchange positions of i and j when they are found
			if (i <= j) {
				SolutionTuple tmp = populationSolutions.get(i);
				populationSolutions.set(i, populationSolutions.get(j));
				populationSolutions.set(j, tmp);
				i++;
				j--;
			}
		}
		// recursively call quicksort
		if (lowerIndex < j) {
			return quickSort(pool, lowerIndex, j);
		}
		else if (i < higherIndex) {
			return quickSort(pool, i, higherIndex);
		}
		return pool;
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
