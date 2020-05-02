import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONArray;
//import org.json.simple.JSONException;
import org.json.simple.JSONObject;
import java.io.FileWriter;

public class Simulation {

	private static Meals mealList;		//Stores all meal options for the simulation maybe not needed
	private static Orders orderList;	//Used to generate orders for the simulation
	private static Foods foodList;		//Stores food options for the simulation maybe not needed

	private static ArrayList<Double> knapSack(ArrayList<Order> orderList) {
		int time = 0; 		//Every increment of time = 1 second
		int pointer = 0; 	//Used to go through the generated orders list
		int numDelivered = 0;

		//List of the orders the Drone has to load
		ArrayList<Order> currentOrders = new ArrayList<Order>();

		//List of Orders the Drone is going to deliver this run
		ArrayList<Order> ordersToDeliver = new ArrayList<Order>();

		//List of meals we skipped over when doing knapsack packing
		ArrayList<Order> skippedOrders = new ArrayList<Order>();

		//List of each delivery time
		ArrayList<Double> timeToDelivery = new ArrayList<Double>();

		double droneSpeed = 36.6667; 	//Speed is in feet per second. (20mph)
		double dropOffTime = 30; 		//Time is in seconds (30 sec)
		double maxWeight = 192; 		//Weight in ounces (12 lbs)
		double turnAround = 150;		//Time is in seconds (2.5 min)
		double maxFlightTime = 1140;	//Max time drone allowed in air (95% of 20 minutes - in seconds)
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

				//Initialize weight of meals on the drone
				double currWeight = 0;
				boolean overweight = false;
				//Keeps track of values necessary to add skipped orders
				int numAdded = 0;
				int point = 0;

				//If there are skipped orders, add them onto the delivery in FIFO order
				while (skippedOrders.size() > 0 && !overweight) {
					double mealWeight = skippedOrders.get(0).getMeal().getWeight();
					if (currWeight + mealWeight <= maxWeight) {
						currWeight += mealWeight;
						ordersToDeliver.add((skippedOrders.get(0)));
						currentOrders.remove(0);
					} else {
						overweight = true;
						System.out.println("Overweight BOI");
					}
				}

				//If there is room left and orders to be packed, use Knapsack to pack
				while (currentOrders.size() > 0 && !overweight) {
					double minWeight = 1000;
					int mealPos = 0;

					//Find the lowest weight meal (Greedy Knapsack)
					for (int i = 0; i < currentOrders.size(); i++) {
						double mealWeight = currentOrders.get(i).getMeal().getWeight();
						if (mealWeight < minWeight) {
							mealPos = i;
							minWeight = mealWeight;
						}
					}

					if (currWeight + currentOrders.get(mealPos).getMeal().getWeight() <= maxWeight) {
						//Add the lowest weight meal to the ordersToDeliver
						ordersToDeliver.add(currentOrders.get(mealPos));
						//Calculate the highest-positioned meal used
						point = (point < mealPos + numAdded) ? mealPos + numAdded : point;
						numAdded++;
						currWeight += currentOrders.get(mealPos).getMeal().getWeight();
						currentOrders.remove(mealPos);
					} else {
						overweight = true;
						System.out.println("Overweight BOI");
					}
				}


				//Add all of the skipped meals to the skipped orders list
				while (point-numAdded > 0) {
					skippedOrders.add(currentOrders.get(0));
					currentOrders.remove(0);
					numAdded++;
				}

				//Do Traveling salesman
				organizeRouteBacktracking(ordersToDeliver);

				//Calculate how long the drone will be gone and add on the
				//		turn-around time (for putting in a new battery)
				double[] prevPoint = new double[] {0,0};
				double timeDroneGone = 0;
				for (int i = 0; i < ordersToDeliver.size(); i++) {
					double[] newPoint = {ordersToDeliver.get(i).getLocation().get(0), ordersToDeliver.get(i).getLocation().get(0)};

					if (newPoint[0]==prevPoint[0] && newPoint[1]==prevPoint[1]) {

						//If there is time left on the drone's flight, calculate time for order and add
						if (timeDroneGone + dropOffTime <= maxFlightTime) {
							timeDroneGone += dropOffTime;

							//Add the time it took to deliver this order to the list of delivery times
							timeToDelivery.add((time + dropOffTime) - ordersToDeliver.get(i).getTime()*60);
						}

						//If no time left for the drone to fly, don't calculate delivery time
						//	and move the rest of the orders back to skippedOrders
						else {
							for (int j = i; j < ordersToDeliver.size(); j++) {
								skippedOrders.add(ordersToDeliver.get(j));
							}
							break;
						}
					}
					else {
						double distance = Math.sqrt(Math.pow(prevPoint[0]-newPoint[0],2)+Math.pow(prevPoint[1]-newPoint[1], 2));

						//If there is time left on the drone's flight, calculate time for order and add
						if (timeDroneGone + distance/droneSpeed + dropOffTime <= maxFlightTime) {
							timeDroneGone += distance/droneSpeed + dropOffTime;

							//Add the time it took to deliver this order to the list of delivery times
							timeToDelivery.add((time + distance/droneSpeed + dropOffTime) - ordersToDeliver.get(i).getTime()*60);
						}


						//If no time left for the drone to fly, don't calculate delivery time
						//	and move the rest of the orders back to skippedOrders
						else {
							for (int j = i; j < ordersToDeliver.size(); j++) {
								skippedOrders.add(ordersToDeliver.get(j));
							}
							break;
						}
					}
					prevPoint = newPoint;
				}

				//Set the drone's return time to AFTER it has returned AND new batteries have been put in
				returnTime = timeDroneGone + time + turnAround;

				numDelivered += ordersToDeliver.size();
				ordersToDeliver = new ArrayList<Order>();
			}

			//Increment the time by one second
			time++;
		}

		//Return the list of times it took to deliver each order.
		return timeToDelivery;
	}

	private static ArrayList<Double> FIFO(ArrayList<Order> orderList) {
		int time = 0; 		//Every increment of time = 1 second
		int pointer = 0; 	//Used to go through the generated orders list
		int numDelivered = 0; //The number of orders delivered so far

		//List of current orders the Drone can see
		ArrayList<Order> currentOrders = new ArrayList<Order>();

		//List of meals we skipped over when doing knapsack packing
		ArrayList<Order> ordersToDeliver = new ArrayList<Order>();

		//List of each delivery time
		ArrayList<Double> timeToDelivery = new ArrayList<Double>();

		double droneSpeed = 36.6667; 	//Speed is in feet per second. (20mph)
		double dropOffTime = 30; 		//Time is in seconds (30 sec)
		double maxWeight = 192; 		//Weight in ounces (12 lbs)
		double turnAround = 150;		//Time is in seconds (2.5 min)
		double maxFlightTime = 1140;	//Max time drone allowed in air (95% of 20 minutes - in seconds)
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

				//If the drone isn't overweight, try and add the next meal in the list
				while (currentOrders.size() > 0 && !overweight) {
					double mealWeight = currentOrders.get(0).getMeal().getWeight();
					if (currWeight + mealWeight <= maxWeight) {
						currWeight += mealWeight;
						ordersToDeliver.add((currentOrders.get(0)));
						currentOrders.remove(0);
					} else {
						System.out.println("Overweight BOI");
						overweight = true;
					}
				}

				//Do Traveling salesman
				organizeRouteBacktracking(ordersToDeliver);

				//Calculate how long the drone will be gone and add on the
				//		turn-around time (for putting in a new battery)
				double[] prevPoint = new double[] {0,0};
				double timeDroneGone = 0;
				for (int i = 0; i < ordersToDeliver.size(); i++) {
					double[] newPoint = {ordersToDeliver.get(i).getLocation().get(0), ordersToDeliver.get(i).getLocation().get(0)};
					if (newPoint[0]==prevPoint[0] && newPoint[1]==prevPoint[1]) {

						//If there is time left on the drone's flight, calculate time for order and add
						if (timeDroneGone + dropOffTime <= maxFlightTime) {
							timeDroneGone += dropOffTime;

							//Add the time it took to deliver this order to the list of delivery times
							timeToDelivery.add((time + dropOffTime) - ordersToDeliver.get(i).getTime()*60);
						}

						//If no time left for the drone to fly, don't calculate delivery time
						//	and move the rest of the orders back to currentOrders
						else {
							for (int j = i; j < ordersToDeliver.size(); j++) {
								currentOrders.add(ordersToDeliver.get(j));
							}
							System.out.print("Too long flying");
							break;
						}
					}
					else {
						double distance = Math.sqrt(Math.pow(prevPoint[0]-newPoint[0],2)+Math.pow(prevPoint[1]-newPoint[1], 2));

						//If there is time left on the drone's flight, calculate time for order and add
						if (timeDroneGone + distance/droneSpeed + dropOffTime <= maxFlightTime) {
							timeDroneGone += distance/droneSpeed + dropOffTime;

							//Add the time it took to deliver this order to the list of delivery times
							timeToDelivery.add((time + distance/droneSpeed + dropOffTime) - ordersToDeliver.get(i).getTime()*60);
						}


						//If no time left for the drone to fly, don't calculate delivery time
						//	and move the rest of the orders back to currentOrders
						else {
							for (int j = i; j < ordersToDeliver.size(); j++) {
								currentOrders.add(ordersToDeliver.get(j));
							}
							System.out.print("Too long flying");
							break;
						}
					}
					prevPoint = newPoint;
				}
				//Calculate distance back to "Home Base"
				double distance = Math.sqrt(Math.pow(prevPoint[0],2)+Math.pow(prevPoint[1], 2));

				//Set the drone's return time to AFTER it has flown home AND new batteries have been put in
				returnTime = timeDroneGone + time + turnAround + distance/droneSpeed;

				numDelivered += ordersToDeliver.size();
				//Reset the ordersToDeliver array
				ordersToDeliver = new ArrayList<Order>();
			}

			//Increment the time by one second
			time++;
		}
		//Return the list of times it took to deliver each order.
		return timeToDelivery;
	}

	private static ArrayList<Integer> recursiveBacktrack(ArrayList<Order> orderList, boolean[] visited,
			double[] prevPos, int count, double minDist, double currDistance,
			ArrayList<Integer> lastVisited, ArrayList<Integer> bestLastVisited) {

		if (count == orderList.size()) {
			double distance = Math.sqrt(Math.pow(prevPos[0]-0, 2)+Math.pow(prevPos[1]-0, 2));
			return minDist < currDistance + distance ? bestLastVisited : lastVisited;
		}

		for (int i = 0; i < orderList.size(); i++)
        {
            if (visited[i] == false)
            {
            	double[] currentPoint = {orderList.get(i).getLocation().get(0), orderList.get(i).getLocation().get(0)};
    			currDistance += Math.sqrt(Math.pow(prevPos[0]-currentPoint[0],2)+Math.pow(prevPos[1]-currentPoint[1], 2));

    			// Mark as visited
            	visited[i] = true;
            	lastVisited.add(i);
                bestLastVisited = recursiveBacktrack(orderList, visited, currentPoint,
                		count + 1, minDist, currDistance, lastVisited, bestLastVisited);

                // Mark ith node as unvisited
                visited[i] = false;
            }
        }

		return bestLastVisited;
	}

	private static void organizeRouteBacktracking(ArrayList<Order> currentOrders) {
		double[] prevPoint = new double[] {0,0};
		double shortestFlightPath = 1 << 25;
		boolean[] visited = new boolean[currentOrders.size()];
		ArrayList<Integer> bestVisitOrder = new ArrayList<Integer>();

		bestVisitOrder = recursiveBacktrack(currentOrders, visited, prevPoint, 0,
				shortestFlightPath, 0, new ArrayList<Integer>(), bestVisitOrder);

		ArrayList<Order> tempVersion = new ArrayList<Order>();

		for (int i = 0; i < bestVisitOrder.size(); i++) {
			tempVersion.add(currentOrders.get(bestVisitOrder.get(i)));
		}
		currentOrders = tempVersion;
	}

	/***
	 * Used to run the simulation,
	 * uses displayMethod to generate a .json file with all data output called temp.json
	 */
	public void runSimulation() {
		ArrayList<Double> testResults = new ArrayList<Double>();		//stores delivery times for Knapsack simulation
		ArrayList<Double> FIFOtestResults = new ArrayList<Double>();	//stores delivery times for FIFO simulation

		orderList.setOrders();	//gets the orders class ready
		testResults = knapSack(orderList.getOrders()); //feed orders into their sims and record results
		FIFOtestResults = FIFO(orderList.getOrders());
		for(int i = 0; i < 49; i++){ //repeat the above so that total # of runs = 50
			orderList.setOrders();
			testResults.addAll(knapSack(orderList.getOrders()));
			FIFOtestResults.addAll(FIFO(orderList.getOrders()));
		}
		displayMethod(FIFOtestResults, testResults); //create the json results file
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

			if(time > FIFOWorst){	//find the worst delivery time
				FIFOWorst = time;
			}
		}
		FIFOAvg = FIFOAvg/FIFO.size(); //calculate average delivery time

		//Knapsack Data
		double KnapsackAvg = 0;
		double KnapsackWorst = 0;

		for(double time: Knapsack){
			KnapsackAvg += time;

			if(time > KnapsackWorst){	//find the worst delivery time
				KnapsackWorst = time;
			}
		}
		KnapsackAvg = KnapsackAvg / Knapsack.size(); //calculate avg delivey time

		//Save to JSON doc
		JSONObject data = new JSONObject();		//outermost object
		JSONObject FIFOObj = new JSONObject();	//data from the FIFO sim
		JSONObject KnapObj = new JSONObject();	//data from the Knapsack sim
		JSONArray FIFOData = new JSONArray();	//array of raw FIFO delivery times
		JSONArray KnapData = new JSONArray();	//array of raw knapsack delivery times


		FIFOObj.put("avgTime",FIFOAvg);		//set fifo avg delivery time
		FIFOObj.put("worstTime", FIFOWorst);//set fifo worst delivery time
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
		try {	//put the json data in a file
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

	/***
	 * Creates an instance of the simulation class based on the parameters passed
	 * @param fList	a list of the potential foods
	 * @param mList	a list of the potetial meals
	 * @param oList	an instance of the orders class for generation orders in sim
	 */
	public Simulation(Foods fList, Meals mList, Orders oList) {
		foodList = fList;
		mealList = mList;
		orderList = oList;
	}

}
