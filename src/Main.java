import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
/**
 * Implementation of the travelling salesperson problem used for CS3910.
 * @author lewis
 *
 */
public class Main {
	// matrix containing nodes in a representation of the problem
	private double[][] nodes;

	public static void main(String[] args) {
		new Main();
	}
	
	public Main() {
		nodes = new double[4][4];
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
		
		char[] route = new char[4];
		
		route[0] = '0';
		route[1] = '1';
		route[2] = '2';
		route[3] = '3';
		
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
}
