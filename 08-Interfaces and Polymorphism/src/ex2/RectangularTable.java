package ex2;

public class RectangularTable implements Table {
	private double length = 0.0;
	private double width  = 0.0;

	RectangularTable (double length, double width) {
		this.length = length;
		this.width  = width;
	}

	@Override
	public double getArea () {
		return length * width;
	}

	@Override
	public String toString () {
		return "RectangularTable - Length: " + length + "\nRectangularTable - Width: " + width;
	}
}
