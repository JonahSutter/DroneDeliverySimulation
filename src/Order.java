
public class Order {
	private int timeStamp;
	private int[] location;
	private Meal meal;
	
	public Order(int timeStamp, int[] location, Meal meal) {
		this.meal = meal;
		this.timeStamp = timeStamp;
		this.location[0] = location[0];
		this.location[1] = location[1];
	}

}
