import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
/**
 * Implementation of the travelling salesperson problem used for CS3910.
 * @author Lewis
 *
 */
public class Main {
	// matrix containing nodes in a representation of the problem
	private double[][] nodes;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
//		char[] route = new char[4];
//		
//		route[0] = '0';
//		route[1] = '1';
//		route[2] = '2';
//		route[3] = '3';
//		nodes = getExampleMatrix();
		
	    char[] route = new char[10];
	    route[0] = '0';
	    route[1] = '1';
	    route[2] = '2';
	    route[3] = '3';
	    route[4] = '4';
	    route[5] = '5';
	    route[6] = '6';
	    route[7] = '7';
	    route[8] = '8';
	    route[9] = '9';

		nodes = getMatrixFromCSV("ulysses16.csv");
		
		route = getRandomRoute(route);
		System.out.println("Travelling Salesman:");
		System.out.println(route);
		System.out.println("Cost of route: " + getCostOfRoute(route));
	}

	/**
	 * Returns the cost of a route given an array of nodes to visit
	 * @param routeToEvaluate
	 * @return
	 */
	private int getCostOfRoute(char[] routeToEvaluate) {
		int cost = 0;
		for (int i = 0; i < routeToEvaluate.length - 1; i++) {
			int x = Character.getNumericValue(routeToEvaluate[i]);
			int y = Character.getNumericValue(routeToEvaluate[i + 1]);
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
	private char[] getRandomRoute(char[] destinations) {
		Random rdm = new Random();
		char[] newRoute = new char[destinations.length + 1];
		
		for (int i = destinations.length - 1; i > 0; i --) {
			int index = rdm.nextInt(i+1);
			char a = destinations[index];
			destinations[index] = destinations[i];
			destinations[i] = a;
		}
		for (int i = 0; i < destinations.length; i++) {
			newRoute[i] = destinations[i];
		}
		newRoute[newRoute.length - 1] = newRoute[0];
		return newRoute;
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
					csvMatrix[x][y] = getCostBetweenCities(fromX, fromY, toX, toY);
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
	 * @return
	 */
	private double[][] getExampleMatrix(){
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