import java.util.Arrays;

public class Tuple<T, E> implements Cloneable{ 
	private T x; 
	private E y; 
	
	public Tuple(T x, E y) { 
		this.x = x; 
		this.y = y; 
	} 
	
	public void setX(T x) {
		this.x = x;
	}
	
	public T getX(){
		return x;
	}
	
	public void setY(E y) {
		this.y = y;
	}
	
	public E getY() {
		return y;
	}
	
	public Object clone() throws CloneNotSupportedException{
		return super.clone();
	}
	
	public String toString() {
		return x.toString() + " : " + y.toString();
	}
}
