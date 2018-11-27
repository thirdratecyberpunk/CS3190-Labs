package lewisbb.cs3190.tsp;

import java.util.Arrays;

public class SolutionTuple implements Cloneable{ 
	private int[] x; 
	private double y; 
	
	public SolutionTuple(int[] x, double y) { 
		this.x = x; 
		this.y = y; 
	} 
	
	public void setX(int[] x){
		this.x = x;
	}
	
	public int[] getX(){
		return x;
	}
	
	public void setY(double y){
		this.y = y;
	}
	
	public double getY() {
		return y;
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public String toString() {
		return x.toString() + " : " + y;
	}

	public int compareTo(SolutionTuple arg0) {
		if (arg0.getY() == this.y) {
			return 0;
		}
		else if (arg0.getY() > this.y) {
			return 1;
		}
		else {
			return -1;
		}
	}
}
