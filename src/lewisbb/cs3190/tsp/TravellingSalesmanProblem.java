package lewisbb.cs3190.tsp;

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
public class TravellingSalesmanProblem {
	// matrix containing nodes in a representation of the problem
	protected double[][] nodes;
	protected int[] route;

	public TravellingSalesmanProblem(String inputFile) {		
		nodes = getMatrixFromCSV(inputFile);		
		route = new int[nodes.length];
		for (int i = 0; i < nodes.length; i++) {
			route[i] = i;
		}
	}

	/**
	 * Returns the cost of a route given an array of nodes to visit
	 * @param routeToEvaluate
	 * @return
	 */
	protected double getCostOfRoute(int[] routeToEvaluate) {
		double cost = 0;
		for (int i = 0; i < routeToEvaluate.length - 1; i++) {
			int x = routeToEvaluate[i];
			int y = routeToEvaluate[i + 1];
			cost += nodes[x][y];
		}
		cost += nodes[routeToEvaluate[routeToEvaluate.length - 1]][routeToEvaluate[0]];
		return cost;
	}
	
	/**
	 * Returns a legal randomised route given an array of nodes that it needs to visit
	 * Based on the Durstenfield shuffle
	 * @param destinations
	 * @return
	 */
	protected int[] getRandomRoute(int[] sourceRoute) {
		Random rdm = new Random();
		int[] toRandomise = sourceRoute.clone();
		for (int i = 0; i < toRandomise.length; i++) {
			int index = rdm.nextInt(toRandomise.length - 1);
			int a = toRandomise[index];
			toRandomise[index] = toRandomise[i];
			toRandomise[i] = a;
		}
		return toRandomise;
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
}