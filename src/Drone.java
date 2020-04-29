public class Drone {
	private String name;
	private double maxWeight;
	private double maxSpeed;
	private double batteryLife;
	
	
	public Drone(String name, double maxWeight, double maxSpeed, double batteryLife) {
		this.name = name;
		this.maxWeight = maxWeight;
		this.maxSpeed = maxSpeed;
		this.batteryLife = batteryLife;
	}
	
	public double getSpeedFeet() {
		return 0.0;
	}
	
}
