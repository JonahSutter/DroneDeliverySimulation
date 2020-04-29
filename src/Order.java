import java.util.ArrayList;

public class Order {
	private int timeStamp;
	private ArrayList<Double> location = new ArrayList<Double>();
	private Meal meal;

	/*
	 * Constructor for Order class
	 * @timeStamp int representing when the order arrives
	 * @location array representing the location
	 * @meal Meal that contains the meal info
	 */
	public Order(int timeStamp, ArrayList<Double> location, Meal meal) {
		this.meal = meal;
		this.timeStamp = timeStamp;
		this.location.add(location.get(0));
		this.location.add(location.get(1));
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
	public ArrayList<Double> getLocation() {
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
