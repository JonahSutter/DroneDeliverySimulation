
public class Order {
	private int timeStamp;
	private double[] location = new double[2];
	private Meal meal;

	/*
	 * Constructor for Order class
	 * @timeStamp int representing when the order arrives
	 * @location array representing the location
	 * @meal Meal that contains the meal info
	 */
	public Order(int timeStamp, double[] location, Meal meal) {
		this.meal = meal;
		this.timeStamp = timeStamp;
		this.location[0] = location[0];
		this.location[1] = location[1];
	}

	/*
	 * Getter for timeStamp
	 * @returns timeStamp
	 */
	public int getTime() {
		return timeStamp;
	}

	/*
	 * Getter for location
	 * @returns locations
	 */
	public double[] getLocation() {
		return location;
	}

	/*
	 * Getter for meal
	 * @returns meal
	 */
	public Meal getMeal() {
		return meal;
	}

}
