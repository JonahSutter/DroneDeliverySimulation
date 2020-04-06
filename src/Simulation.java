import java.util.ArrayList;

public class Simulation {
	private ArrayList<Order> orderList;

	private void knapSack() {
		int time = 0; 		//Every increment of time = 1 second
		int pointer = 0; 	//Used to go through the generated orders list

		//List of current orders the Drone can see
		ArrayList<Order> currentOrders = new ArrayList<Order>();

		//List of meals we skipped over when doing knapsack packing
		ArrayList<Order> skippedOrders = new ArrayList<Order>();

		double droneSpeed = 29.3333; 	//Speed is in feet per second. (20mph)
		double dropOffTime = 30; 		//Time is in seconds (30 sec)
		double maxWeight = 192; 		//Weight in ounces (12 lbs)
		double turnAround = 180;		//Time is in seconds (3 min)
		double returnTime = 0;			//Tells when the drone is due back

		//Loop through all the orders generated from a 4-hour shift
		while (pointer < orderList.size()) {

			//Add the next order from the list once we pass the time marker
			if (orderList.get(pointer).getTime() <= time) {
				currentOrders.add(orderList.get(pointer));
				pointer++;
			}


			//The drone will only leave if there is a current order
				//(Except for the first 5 minutes - special case)
			if (returnTime <= time && currentOrders.size() > 0 && time >= 300) {

				//Pack the orders (Don't go overweight)
				boolean overweight = false;
				while (currentOrders.size() > 0 && !overweight) {
					double tempMaxWeight = 0;
					int mealPos = 0;

					//TODO: Finish knapsack
					for (int i = 0; i < currentOrders.size(); i++) {
						double mealWeight = currentOrders.get(i).getMeal().getWeight();
						if (mealWeight > maxWeight) {
							mealPos = i;
							maxWeight = mealWeight;
						}
					}
				}

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

				//Calculate how long the drone will be gone and add on the
				//		turn-around time (for putting in a new battery)
				prevPoint = new double[] {0,0};
				double timeDroneGone = 0;
				for (int i = 0; i < currentOrders.size(); i++) {
					double[] newPoint = currentOrders.get(i).getLocation();
					if (newPoint[0]==prevPoint[0] && newPoint[1]==prevPoint[1]) {
						timeDroneGone += dropOffTime;
					} else {
						double distance = Math.sqrt(Math.pow(prevPoint[0]-newPoint[0],2)+Math.pow(prevPoint[1]-newPoint[1], 2));
						timeDroneGone += distance/droneSpeed + dropOffTime;
					}
					prevPoint = newPoint;
				}

				returnTime = timeDroneGone + time + turnAround;
			}

			//Increment the time by one second
			time++;
		}
	}

	private void FIFO() {

	}

	public void runSimulation() {

	}

	public void displayMethod() {

	}

	public void saveResults() {

	}

}
