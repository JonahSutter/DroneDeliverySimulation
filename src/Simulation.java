
public class Simulation {

	private void knapSack() {
		//Pretend orderList is the list of orders
		/*
		int time = 0; //Every increment of time = 1 second
		int pointer = 0; //Used to go through the generated orders list

		//List of current orders the Drone can see
		ArrayList<Meal> currentOrders = new ArrayList<Meal>();

		double droneSpeed = 29.3333 //Speed is in feet per second
		int dropoffTime = 30  //Time is in seconds
		int maxWeight = 192 //Weight in ounces
		int turnAround = 180 //Time is in seconds
		boolean wasSent = false;

		while (pointer < orderList.size()) {

			//Add the next order from the list once we pass the time marker
			if (orderList.get(pointer).getTime() <= time) {
				currentOrders.add(orderList.get(pointer));
				pointer++;
			}

			//The drone will only leave if there is a current order
			//(Except for the first 5 minutes - special case)
			if (currentOrders.size() > 0 && time >= 300) {
				//Pack the orders (Don't go overweight)

				//Calculate how long the drone will be gone
				//   and increment the time by that value - 1
				//   to "fast-forward" through the deliveries
				wasSent = true;

			}


			//If the drone was sent we increment the time by turnAround - 1
			//   to essentially "fast-forward" through the battery change.
			if (wasSent) {
				time += turnAround - 1;
				wasSent = false;
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
