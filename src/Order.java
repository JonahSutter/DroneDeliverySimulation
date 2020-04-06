
public class Order {
	private int timeStamp;
	private double[] location;
	private Meal meal;

	public Order(int timeStamp, double[] location, Meal meal) {
		this.meal = meal;
		this.timeStamp = timeStamp;
		this.location[0] = location[0];
		this.location[1] = location[1];
	}

	public int getTime() {
		return timeStamp;
	}

	public double[] getLocation() {
		return location;
	}

	public Meal getMeal() {
		return meal;
	}

}
