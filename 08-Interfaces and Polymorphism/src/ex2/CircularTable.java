package ex2;

public class CircularTable implements Table {
	private double radius;

	public CircularTable(double radius) {
		this.radius = radius;
	}

	@Override
	public double getArea () {
		return Math.PI * radius * radius;
	}

	public double getPerimetre () {
		return 2.0 * Math.PI * radius;
	}

	@Override
	public String toString () {
		return "RectangularTable - radius: " + radius;
	}
}
