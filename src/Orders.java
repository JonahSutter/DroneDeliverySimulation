import java.util.ArrayList;
import java.util.Queue;
import java.util.Random;

public class Orders {

	private int firstHour;
	private int secondHour;
	private int thirdHour;
	private int fourthHour;
	private ArrayList<Meal> mealList;
	private double[][] locationList;
	private ArrayList<Order> orders = new ArrayList<Order>();

	public Orders(int firstHour, int secondHour, int thirdHour, int fourthHour) {
		this.firstHour = firstHour;
		this.secondHour = secondHour;
		this.thirdHour = thirdHour;
		this.fourthHour = fourthHour;
	}

	public void setMeals(ArrayList<Meal> meals) {
		mealList = meals;
	}

	public void setLocations(double[][] locations) {
		locationList = locations;
	}

	public void setHourlyRate(int hourOne, int hourTwo, int hourThree, int hourFour) {
		firstHour = hourOne;
		secondHour = hourTwo;
		thirdHour = hourThree;
		fourthHour = hourFour;
	}

	public Orders(int firstHour, int secondHour, int thirdHour, int fourthHour, ArrayList<Meal> meals, double[][] locations) {
		this.firstHour = firstHour;
		this.secondHour = secondHour;
		this.thirdHour = thirdHour;
		this.fourthHour = fourthHour;
		mealList = meals;
		locationList = locations;
	}

	public ArrayList<Order> getOrders() {
		int numSoFar = 0;
		int min = 0;

		Random rand = new Random();



		while (numSoFar < firstHour) {
			if (rand.nextDouble() <= (firstHour - numSoFar)/(double)(60 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locationList.length);

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + mealList.get(mealPos).getPercentage()) {
					totalPercent += mealList.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locationList[location], mealList.get(mealPos)));
			}
			min++;
		}

		min = 60;
		numSoFar = 0;

		while (numSoFar < secondHour) {

			if (rand.nextDouble() <= (secondHour - numSoFar)/(double)(120 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locationList.length);

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + mealList.get(mealPos).getPercentage()) {
					totalPercent += mealList.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locationList[location], mealList.get(mealPos)));
			}

			min++;
		}


		min = 120;
		numSoFar = 0;

		while (numSoFar < thirdHour) {

			if (rand.nextDouble() <= (thirdHour - numSoFar)/(double)(180 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locationList.length);

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + mealList.get(mealPos).getPercentage()) {
					totalPercent += mealList.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locationList[location], mealList.get(mealPos)));
			}

			min++;
		}

		min = 180;
		numSoFar = 0;

		while (numSoFar < fourthHour) {

			if (rand.nextDouble() <= (fourthHour - numSoFar)/(double)(240 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locationList.length);

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + mealList.get(mealPos).getPercentage()) {
					totalPercent += mealList.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locationList[location], mealList.get(mealPos)));
			}

			min++;
		}

		return orders;
	}

	public int getFirstHour() {
		return firstHour;
	}

	public int getSecondHour() {
		return secondHour;
	}

	public int getThirdHour() {
		return thirdHour;
	}

	public int getFourthHour() {
		return fourthHour;
	}


}
