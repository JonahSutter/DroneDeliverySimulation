import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Orders {

	private int firstHour;
	private int secondHour;
	private int thirdHour;
	private int fourthHour;
	private ArrayList<Meal> meals;
	private ArrayList<Order> orders;


	public Orders(int firstHour, int secondHour, int thirdHour, int fourthHour, ArrayList<Meal> meals, double[][] locations) {
		this.firstHour = firstHour;
		this.secondHour = secondHour;
		this.thirdHour = thirdHour;
		this.fourthHour = fourthHour;

		ArrayList<Meal> percentages = new ArrayList<Meal>();

		for (Meal m : meals) {
			this.meals.add(m);

			for (int i = 0; i < m.getPercentage(); i++) {
				percentages.add(m);
			}

		}


		int numSoFar = 0;
		int min = 0;

		Random rand = new Random();



		while (numSoFar < firstHour) {


			if (rand.nextDouble() <= (firstHour - numSoFar)/(60 - min)){
				int meal = rand.nextInt(100);
				int location = rand.nextInt(locations.length);

				orders.add(new Order(min, locations[location], percentages.get(meal)));

			}

			min++;
		}

		min = 60;

		while (numSoFar < secondHour) {

			if (rand.nextDouble() <= (secondHour - numSoFar)/(120 - min)){
				int meal = rand.nextInt(100);
				int location = rand.nextInt(locations.length);

				orders.add(new Order(min, locations[location], percentages.get(meal)));

			}

			min++;
		}


		min = 120;

		while (numSoFar < thirdHour) {

			if (rand.nextDouble() <= (thirdHour - numSoFar)/(180 - min)){
				int meal = rand.nextInt(100);
				int location = rand.nextInt(locations.length);

				orders.add(new Order(min, locations[location], percentages.get(meal)));

			}

			min++;
		}

		min = 180;

		while (numSoFar < fourthHour) {

			if (rand.nextDouble() <= (fourthHour - numSoFar)/(240 - min)){
				int meal = rand.nextInt(100);
				int location = rand.nextInt(locations.length);

				orders.add(new Order(min, locations[location], percentages.get(meal)));

			}

			min++;
		}


	}

	public ArrayList<Order> getOrders() {
		return orders;
	}




}
