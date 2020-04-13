import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
//import org.json.simple.JSONException;
import org.json.simple.JSONObject;
import java.io.FileWriter;

public class Simulation {

	private static Meals mealList;
	private static Orders orderList;
	private static Foods foodList;

	private static ArrayList<Double> knapSack(ArrayList<Order> orderList) {
		int time = 0; 		//Every increment of time = 1 second
		int pointer = 0; 	//Used to go through the generated orders list
		int numDelivered = 0;

		//List of current orders the Drone can see
		ArrayList<Order> currentOrders = new ArrayList<Order>();

		//List of meals we skipped over when doing knapsack packing
		ArrayList<Order> skippedOrders = new ArrayList<Order>();

		//List of Orders the Drone is going to deliver this run
		ArrayList<Order> ordersToDeliver = new ArrayList<Order>();

		//List of each delivery time
		ArrayList<Double> timeToDelivery = new ArrayList<Double>();

		double droneSpeed = 29.3333; 	//Speed is in feet per second. (20mph)
		double dropOffTime = 30; 		//Time is in seconds (30 sec)
		double maxWeight = 192; 		//Weight in ounces (12 lbs)
		double turnAround = 180;		//Time is in seconds (3 min)
		double returnTime = 0;			//Tells when the drone is due back

		//Loop through all the orders generated from a 4-hour shift
		while (numDelivered < orderList.size()) {

			//Add the next order from the list once we pass the time marker
			if (pointer < orderList.size() && orderList.get(pointer).getTime()*60 <= time) {
				currentOrders.add(orderList.get(pointer));
				pointer++;
			}


			//The drone will only leave if there is a current order
				//(Except for the first 5 minutes - special case)
			if (returnTime <= time && currentOrders.size() > 0 && time >= 300) {

				//Pack the orders
				double currWeight = 0;
				boolean overweight = false;

				//If there are skipped orders, add them onto the delivery.
				while (skippedOrders.size() > 0 && !overweight) {
					double mealWeight = skippedOrders.get(0).getMeal().getWeight();
					if (currWeight + mealWeight <= maxWeight) {
						currWeight += mealWeight;
						ordersToDeliver.add((skippedOrders.get(0)));
						currentOrders.remove(0);
					} else {
						overweight = true;
					}
				}

				//Pack the orders with Knapsack
				while (currentOrders.size() > 0 && !overweight) {
					double minWeight = 0;
					int mealPos = 0;

					//Find the lowest weight meal (Greedy Knapsack)
					for (int i = 0; i < currentOrders.size(); i++) {
						double mealWeight = currentOrders.get(i).getMeal().getWeight();
						if (mealWeight < minWeight) {
							mealPos = i;
							minWeight = mealWeight;
						}
					}

					//Add the lowest weight meal to the ordersToDeliver
					ordersToDeliver.add(currentOrders.get(mealPos));
					currentOrders.remove(mealPos);
				}

				while (currentOrders.size() > 0) {
					skippedOrders.add(currentOrders.get(0));
					currentOrders.remove(0);
				}

				//Do Traveling salesman
				organizeRoute(ordersToDeliver);

				//Calculate how long the drone will be gone and add on the
				//		turn-around time (for putting in a new battery)
				double[] prevPoint = new double[] {0,0};
				double timeDroneGone = 0;
				for (int i = 0; i < ordersToDeliver.size(); i++) {
					double[] newPoint = ordersToDeliver.get(i).getLocation();
					if (newPoint[0]==prevPoint[0] && newPoint[1]==prevPoint[1]) {
						timeDroneGone += dropOffTime;
						timeToDelivery.add((time + dropOffTime) - ordersToDeliver.get(i).getTime()*60);
					} else {
						double distance = Math.sqrt(Math.pow(prevPoint[0]-newPoint[0],2)+Math.pow(prevPoint[1]-newPoint[1], 2));
						timeDroneGone += distance/droneSpeed + dropOffTime;
						timeToDelivery.add((time + distance/droneSpeed + dropOffTime) - ordersToDeliver.get(i).getTime()*60);
					}
					prevPoint = newPoint;
				}

				returnTime = timeDroneGone + time + turnAround;

				numDelivered += ordersToDeliver.size();
				ordersToDeliver = new ArrayList<Order>();
			}

			//Increment the time by one second
			time++;
		}

		return timeToDelivery;
	}

	private static ArrayList<Double> FIFO(ArrayList<Order> orderList) {
		int time = 0; 		//Every increment of time = 1 second
		int pointer = 0; 	//Used to go through the generated orders list
		int numDelivered = 0;

		//List of current orders the Drone can see
		ArrayList<Order> currentOrders = new ArrayList<Order>();

		//List of meals we skipped over when doing knapsack packing
		ArrayList<Order> ordersToDeliver = new ArrayList<Order>();

		//List of each delivery time
		ArrayList<Double> timeToDelivery = new ArrayList<Double>();

		double droneSpeed = 29.3333; 	//Speed is in feet per second. (20mph)
		double dropOffTime = 30; 		//Time is in seconds (30 sec)
		double maxWeight = 192; 		//Weight in ounces (12 lbs)
		double turnAround = 180;		//Time is in seconds (3 min)
		double returnTime = 0;			//Tells when the drone is due back

		//Loop through all the orders generated from a 4-hour shift
		while (numDelivered < orderList.size()) {

			//Add the next order from the list once we pass the time marker
			if (pointer < orderList.size() && orderList.get(pointer).getTime()*60 <= time) {
				currentOrders.add(orderList.get(pointer));
				pointer++;
			}

			//The drone will only leave if there is an order to deliver and it is "home"
				//(Except for the first 5 minutes - special case)
			if (returnTime <= time && currentOrders.size() > 0 && time >= 300) {

				//Pack the orders using FIFO
				boolean overweight = false;
				double currWeight = 0;

				while (currentOrders.size() > 0 && !overweight) {
					double mealWeight = currentOrders.get(0).getMeal().getWeight();
					if (currWeight + mealWeight <= maxWeight) {
						currWeight += mealWeight;
						ordersToDeliver.add((currentOrders.get(0)));
						currentOrders.remove(0);
					} else {
						overweight = true;
					}
				}

				//Do Traveling salesman
				organizeRoute(currentOrders);

				//Calculate how long the drone will be gone and add on the
				//		turn-around time (for putting in a new battery)
				double[] prevPoint = new double[] {0,0};
				double timeDroneGone = 0;
				for (int i = 0; i < ordersToDeliver.size(); i++) {
					double[] newPoint = ordersToDeliver.get(i).getLocation();
					if (newPoint[0]==prevPoint[0] && newPoint[1]==prevPoint[1]) {
						timeDroneGone += dropOffTime;
						timeToDelivery.add((time + dropOffTime) - ordersToDeliver.get(i).getTime()*60);
					} else {
						double distance = Math.sqrt(Math.pow(prevPoint[0]-newPoint[0],2)+Math.pow(prevPoint[1]-newPoint[1], 2));
						timeDroneGone += distance/droneSpeed + dropOffTime;
						timeToDelivery.add((time + distance/droneSpeed + dropOffTime) - ordersToDeliver.get(i).getTime()*60);
					}
					prevPoint = newPoint;
				}
				returnTime = timeDroneGone + time + turnAround;

				numDelivered += ordersToDeliver.size();
				//Reset the ordersToDeliver array
				ordersToDeliver = new ArrayList<Order>();
			}

			//Increment the time by one second
			time++;
		}
		return timeToDelivery;
	}

	private static void organizeRoute(ArrayList<Order> currentOrders) {
		double[] prevPoint = new double[] {0,0};
		//Greedy Traveling Salesman (Done by re-organizing the list of current Orders)
		for (int i = 0; i < currentOrders.size(); i++) {
			double[] comparisonPoint = currentOrders.get(i).getLocation();
			double minDist;
			int pos = i;
			if (comparisonPoint[0] == prevPoint[0] && comparisonPoint[1] == prevPoint[1]) {
				minDist = 0;
			} else {
				minDist = Math.sqrt(Math.pow(prevPoint[0]-comparisonPoint[0],2)+Math.pow(prevPoint[1]-comparisonPoint[1], 2));
				for (int j = i; j < currentOrders.size(); j++) {
					double distance;
					double[] newPoint = currentOrders.get(j).getLocation();
					if (newPoint[0]==prevPoint[0] && newPoint[1]==prevPoint[1]) {
						distance = 0;
					} else {
						distance = Math.sqrt(Math.pow(prevPoint[0]-newPoint[0],2)+Math.pow(prevPoint[1]-newPoint[1], 2));
					}

					if (j == 0 || distance < minDist) {
						pos = j;
						minDist = distance;
					}
					prevPoint = newPoint;
				}
				if (pos != i) {
					Order temp = currentOrders.get(i);
					currentOrders.set(i, currentOrders.get(pos));
					currentOrders.set(pos, temp);
				}
			}
		}
	}

	public void runSimulation() {
		ArrayList<Double> testResults = new ArrayList<Double>();
		ArrayList<Double> FIFOtestResults = new ArrayList<Double>();

		orderList.setOrders();
		testResults = knapSack(orderList.getOrders());
		FIFOtestResults = FIFO(orderList.getOrders());
		for(int i = 0; i < 49; i++){
			orderList.setOrders();
			testResults.addAll(knapSack(orderList.getOrders()));
			FIFOtestResults.addAll(FIFO(orderList.getOrders()));
		}
		displayMethod(FIFOtestResults, testResults);
	}


	/***
	 * Takes in delivery times from FIFO and knapsack, processes the data,
	 * and puts it into a JSON file called temp.json
	 * @param FIFO arraylist containing delivery times for FIFO
	 * @param Knapsack arraylist containing delivery times for knapsack
	 */
	public static void displayMethod(ArrayList<Double> FIFO, ArrayList<Double> Knapsack) {

		//FIFO data
		double FIFOAvg = 0;
		double FIFOWorst = 0;

		for(double time : FIFO){//go through all the times for FIFO
			FIFOAvg += time;

			if(time > FIFOWorst){
				FIFOWorst = time;
			}
		}
		FIFOAvg = FIFOAvg/FIFO.size();

		//Knapsack Data
		double KnapsackAvg = 0;
		double KnapsackWorst = 0;

		for(double time: Knapsack){
			KnapsackAvg += time;

			if(time > KnapsackWorst){
				KnapsackWorst = time;
			}
		}
		KnapsackAvg = KnapsackAvg / Knapsack.size();

		//Save to JSON doc
		JSONObject data = new JSONObject();
		JSONObject FIFOObj = new JSONObject();
		JSONObject KnapObj = new JSONObject();
		JSONArray FIFOData = new JSONArray();
		JSONArray KnapData = new JSONArray();


		FIFOObj.put("avgTime",FIFOAvg);
		FIFOObj.put("worstTime", FIFOWorst);
		for(double time : FIFO){
			FIFOData.add(time);
		}
		FIFOObj.put("data", FIFOData);

		KnapObj.put("avgTime", KnapsackAvg);
		KnapObj.put("worstTime", KnapsackWorst);
		for(double time: Knapsack){
			KnapData.add(time);
		}
		KnapObj.put("data", KnapData);

		data.put("FIFO", FIFOObj);
		data.put("Knapsack", KnapObj);

		//System.out.println(data.toString());
		try {
			FileWriter file = new FileWriter("temp.json");
			file.write(data.toString());
			file.flush();
			file.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void saveResults() {

	}

	public static void main(String[] args) {
		Foods foodList = new Foods();
		Meals mealList = new Meals();
		Food hamburger = new Food("Hamburger",6);
		Food cheeseburger = new Food("Cheeseburger",7);
		Food fries = new Food("Fries",3);
		Food drink = new Food("Drink",9);
		foodList.addFoodItem(hamburger);
		foodList.addFoodItem(cheeseburger);
		foodList.addFoodItem(fries);
		foodList.addFoodItem(drink);
		mealList.addMeal(new Meal("Basic",0.7,new ArrayList<Food>() {{add(hamburger);add(fries);}}));
		mealList.addMeal(new Meal("Drink Only",0.1,new ArrayList<Food>() {{add(drink);}}));
		mealList.addMeal(new Meal("Cheesey",0.2,new ArrayList<Food>() {{add(cheeseburger);add(cheeseburger);add(cheeseburger);}}));
		ArrayList<Order> orderList = new ArrayList<Order>();
		double[][] locationList = new double[25][];
		for (int i = 0; i < 25; i++) {
			locationList[i] = new double[2];
		}

		locationList[0] = new double[] {0,0};
		locationList[1] = new double[] {40,50};
		locationList[2] = new double[] {90,60};
		locationList[3] = new double[] {100,100};
		locationList[4] = new double[] {0,100};
		locationList[5] = new double[] {90,8};
		locationList[6] = new double[] {10,30};
		locationList[7] = new double[] {70,65.23};
		locationList[8] = new double[] {48.32,23.51};
		locationList[9] = new double[] {109.3,142.6};
		locationList[10] = new double[] {241,42};
		locationList[11] = new double[] {123,75};
		locationList[12] = new double[] {352,129};
		locationList[13] = new double[] {185,135};
		locationList[14] = new double[] {185,138};
		locationList[15] = new double[] {194,144};
		locationList[16] = new double[] {150,74};
		locationList[17] = new double[] {143,93};
		locationList[18] = new double[] {164,103};
		locationList[19] = new double[] {251,172};
		locationList[20] = new double[] {214,198};
		locationList[21] = new double[] {213,164};
		locationList[22] = new double[] {218,300};
		locationList[23] = new double[] {245,271};
		locationList[24] = new double[] {295,142};

		Orders orderInfo = new Orders(20,20,20,25,mealList.getMeals(),locationList);

		ArrayList<Double> testResults = new ArrayList<Double>();
		testResults = knapSack(orderInfo.getOrders());
		//Added code for testing displayMethod()
		ArrayList<Double> FIFOtestResults = new ArrayList<Double>();
		FIFOtestResults = knapSack(orderInfo.getOrders());
		displayMethod(FIFOtestResults, testResults);
		//end changes

		for (int i = 0; i < testResults.size(); i++) {
			System.out.println("Position " + i + " = " + testResults.get(i));
		}
	}

	public Simulation(Foods fList, Meals mList, Orders oList) {
		foodList = fList;
		mealList = mList;
		orderList = oList;

	}

}
