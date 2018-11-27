package lewisbb.cs3190.tsp;

import java.util.Arrays;

public class TSPMain {

	public static void main(String[] args) throws CloneNotSupportedException {
		if (args.length != 0) {
			String inputFile = args[0];
			System.out.println("Travelling Salesperson Problem");
			System.out.println("Random solution:");
			RandomTSPSolution rdmtsp = new RandomTSPSolution(inputFile);
			System.out.println(rdmtsp.solution(rdmtsp.route, 100));
			System.out.println("Two opt solution:");
			TwoOptTSPSolution twoopttsp = new TwoOptTSPSolution(inputFile);
			System.out.println(twoopttsp.solution(rdmtsp.route, 100));
			System.out.println("Artificial Immune System solution:");
			ArtificialImmuneSystemTSPSolution aistsp = new ArtificialImmuneSystemTSPSolution(inputFile, 1000, 100, 10);
			Tuple<int[], Double> solution = aistsp.solution((long) 100, 10.0);
			System.out.println("The best solution is: " + Arrays.toString(solution.getX()) + " with a cost of : " + solution.getY()+ ".");
		}
		else {
			System.out.println("Please enter a file.");
		}
	}

}
