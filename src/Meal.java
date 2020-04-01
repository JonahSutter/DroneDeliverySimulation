import java.util.ArrayList;

public class Meal {

	private String name;
	private double percentage;
	private ArrayList<Food> foodItems;
	
	public Meal(String name, double percentage, ArrayList<Food> foodItems) {
		this.name = name;
		this.percentage = percentage;
		this.foodItems = foodItems;
	}
	
	public Meal(String name, ArrayList<Food> foodItems) {
		this.name = name;
		this.foodItems = foodItems;
		this.percentage = 0.0;
	}

	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public double getWeight() {
		double weight = 0.0;
		for (int i = 0; i < foodItems.size(); i++) {
			weight += (foodItems.get(i)).getWeight();
		}
		return weight;
	}
	
	public ArrayList<Food> getFoods() {
		return this.foodItems;
	}
	
	public double getPercentage() {
		return this.percentage;
	}
	
	public void setPercentage(double percentage) {
		if (percentage >= 0.0 && this.percentage < 100.0) {
			this.percentage = percentage;
		}
		else {
			System.out.println("Invalid percentage");
		}
	}
	
	public boolean hasFood() {
		if (this.foodItems.size() != 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public void renameFoodFromMeal() {
		
	}
	
	public void addFoodToMeal(Food food) {
		this.foodItems.add(food);
	}
	
}
