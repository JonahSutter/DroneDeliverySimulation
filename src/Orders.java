import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Orders {

	private int firstHour;
	private int secondHour;
	private int thirdHour;
	private int fourthHour;
	private ArrayList<Order> orders = new ArrayList<Order>();


	public Orders(int firstHour, int secondHour, int thirdHour, int fourthHour, ArrayList<Meal> meals, double[][] locations) {
		this.firstHour = firstHour;
		this.secondHour = secondHour;
		this.thirdHour = thirdHour;
		this.fourthHour = fourthHour;


		int numSoFar = 0;
		int min = 0;

		Random rand = new Random();



		while (numSoFar < firstHour) {
			if (rand.nextDouble() <= (firstHour - numSoFar)/(double)(60 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locations.length);

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + meals.get(mealPos).getPercentage()) {
					totalPercent += meals.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locations[location], meals.get(mealPos)));
			}
			min++;
		}

		min = 60;
		numSoFar = 0;

		while (numSoFar < secondHour) {

			if (rand.nextDouble() <= (secondHour - numSoFar)/(120 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locations.length);

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + meals.get(mealPos).getPercentage()) {
					totalPercent += meals.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locations[location], meals.get(mealPos)));
			}

			min++;
		}


		min = 120;
		numSoFar = 0;

		while (numSoFar < thirdHour) {

			if (rand.nextDouble() <= (thirdHour - numSoFar)/(180 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locations.length);

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + meals.get(mealPos).getPercentage()) {
					totalPercent += meals.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locations[location], meals.get(mealPos)));
			}

			min++;
		}

		min = 180;
		numSoFar = 0;

		while (numSoFar < fourthHour) {

			if (rand.nextDouble() <= (fourthHour - numSoFar)/(240 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locations.length);

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + meals.get(mealPos).getPercentage()) {
					totalPercent += meals.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locations[location], meals.get(mealPos)));
			}

			min++;
		}

	}

	public ArrayList<Order> getOrders() {
		return orders;
	}




}
