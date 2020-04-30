import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class Orders {

	private int firstHour;
	private int secondHour;
	private int thirdHour;
	private int fourthHour;
	private ArrayList<Meal> mealList;
	private ArrayList<ArrayList<Double>> locationList;
	private ArrayList<Order> orders = new ArrayList<Order>();

	
	/*
	 * Constructor for Orders class
	 * @firstHour int containing num orders in first hour
	 * @secondHour int containing num orders in second hour
	 * @thirdHour int containing num orders in third hour
	 * @fouthHour int containing num orders in fourth hour
	 */
	public Orders(int firstHour, int secondHour, int thirdHour, int fourthHour) {
		this.firstHour = firstHour;
		this.secondHour = secondHour;
		this.thirdHour = thirdHour;
		this.fourthHour = fourthHour;
	}
	
	/*sets the meals to be used
	* @meals ArrayList<Meal> containing the meals to be used
	 */
	public void setMeals(ArrayList<Meal> meals) {
		mealList = meals;
	}
	
	/* sets the locations
	 * @locations array of values representing the locations to be used
	 */
	public void setLocations(ArrayList<ArrayList<Double>> locationList) {
		this.locationList = locationList;
	}

	/*
	 * Setting the hours to be used
	 * @firstHour int containing num orders in first hour
	 * @secondHour int containing num orders in second hour
	 * @thirdHour int containing num orders in third hour
	 * @fouthHour int containing num orders in fourth hour
	 * @meals ArrayList<Meal> containing the meals to be used
	 * @locations array of values representing the locations to be used
	 */
	public void setHourlyRate(int hourOne, int hourTwo, int hourThree, int hourFour) {
		firstHour = hourOne;
		secondHour = hourTwo;
		thirdHour = hourThree;
		fourthHour = hourFour;
	}

	public JSONObject saveOrders(){
		JSONObject save = new JSONObject();

		save.put("hour1", firstHour);
		save.put("hour2", secondHour);
		save.put("hour3", thirdHour);
		save.put("hour4", fourthHour);

		return save;
	}

	/*
	 * Constructor for Orders class
	 * @firstHour int containing num orders in first hour
	 * @secondHour int containing num orders in second hour
	 * @thirdHour int containing num orders in third hour
	 * @fouthHour int containing num orders in fourth hour
	 * @meals ArrayList<Meal> containing the meals to be used
	 * @locations array of values representing the locations to be used
	 */
	public Orders(int firstHour, int secondHour, int thirdHour, int fourthHour, ArrayList<Meal> meals, ArrayList<ArrayList<Double>> locationList) {
		this.firstHour = firstHour;
		this.secondHour = secondHour;
		this.thirdHour = thirdHour;
		this.fourthHour = fourthHour;
		mealList = meals;
		this.locationList = locationList;
	}

	/*
	 * Sets the orders to be used in the simulation
	 */
	public void setOrders() {
		int numSoFar = 0;
		int min = 0;

		orders = new ArrayList<Order>();

		Random rand = new Random();


		
		//sets the first hour
		while (numSoFar < firstHour) {
			if (rand.nextDouble() <= (firstHour - numSoFar)/(double)(60 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locationList.size());

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + mealList.get(mealPos).getPercentage()) {
					totalPercent += mealList.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locationList.get(location), mealList.get(mealPos)));
			}
			min++;
		}

		min = 60;
		numSoFar = 0;

		
		//sets the second hour
		while (numSoFar < secondHour) {

			if (rand.nextDouble() <= (secondHour - numSoFar)/(double)(120 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locationList.size());

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + mealList.get(mealPos).getPercentage()) {
					totalPercent += mealList.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locationList.get(location), mealList.get(mealPos)));
			}

			min++;
		}


		min = 120;
		numSoFar = 0;

		//sets the third hour
		while (numSoFar < thirdHour) {

			if (rand.nextDouble() <= (thirdHour - numSoFar)/(double)(180 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locationList.size());

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + mealList.get(mealPos).getPercentage()) {
					totalPercent += mealList.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locationList.get(location), mealList.get(mealPos)));
			}

			min++;
		}

		min = 180;
		numSoFar = 0;
		
		
		//sets the fourth hour
		while (numSoFar < fourthHour) {

			if (rand.nextDouble() <= (fourthHour - numSoFar)/(double)(240 - min)){
				double meal = rand.nextDouble();
				int location = rand.nextInt(locationList.size());

				int mealPos = 0;
				double totalPercent = 0;
				while (meal > totalPercent + mealList.get(mealPos).getPercentage()) {
					totalPercent += mealList.get(mealPos).getPercentage();
					mealPos++;
				}
				numSoFar++;
				orders.add(new Order(min, locationList.get(location), mealList.get(mealPos)));
			}

			min++;
		}
	}

	/*
	 *  getter for orders
	 * @returns the orders
	 */
	public ArrayList<Order> getOrders() {
		return orders;
	}

	/*
	 * getter for first hour
	 * @returns firstHour
	 */
	public int getFirstHour() {
		return firstHour;
	}

	/*
	 * getter for second hour
	 * @returns secondHour
	 */
	public int getSecondHour() {
		return secondHour;
	}

	/*
	 * getter for third hour
	 * @returns thirdHour
	 */
	public int getThirdHour() {
		return thirdHour;
	}

	/*
	 * getter for fourth hour
	 * @returns fourthHour
	 */
	public int getFourthHour() {
		return fourthHour;
	}


}
