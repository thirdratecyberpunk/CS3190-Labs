
public class AntennaArrayProblem {

	public static void main(String[] args) {
		new AntennaArrayProblem();
	}
	
	public AntennaArrayProblem() {
		AntennaArray aa = new AntennaArray(3, 90);
		double[] design = {0.2, 0.8, 1.5};
		System.out.println(aa.is_valid(design));
		System.out.println(aa.evaluate(design));
	}
	
	private double[] getRandomSolution(AntennaArray aa) {
		return null;
	}

}
