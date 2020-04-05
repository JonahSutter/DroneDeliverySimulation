
public class Simulation {

	private void knapSack() {
		//**Pretend orderList is the list of orders**

		/*
		int time = 0; 		//Every increment of time = 1 second
		int pointer = 0; 	//Used to go through the generated orders list

		//List of current orders the Drone can see
		ArrayList<Order> currentOrders = new ArrayList<Order>();

		//List of meals we skipped over when doing knapsack packing
		ArrayList<Order> skippedOrders = new ArrayList<Order>();

		double droneSpeed = 29.3333 	//Speed is in feet per second. (20mph)
		double dropoffTime = 30  		//Time is in seconds (30 sec)
		double maxWeight = 192 			//Weight in ounces (12 lbs)
		double turnAround = 180 		//Time is in seconds (3 min)
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
					double maxWeight = 0;
					int mealPos = 0;

					for (int i = 0; i < currentOrders.size(); i++) {
						double mealWeight = currentOrder.get(i).getWeight();
						if (mealWeight > maxWeight) {
							mealPos = i;
							maxWeight = mealWeight;
						}
					}
				}

				//Calculate how long the drone will be gone and add on the
				//		turn-around time (for putting in a new battery)
				returnTime = timeDroneGone + time + turnAroud;
			}

			//Increment the time by one second
			time++;
		}
		*/
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
