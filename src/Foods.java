import java.util.ArrayList;

public class Foods {
	private ArrayList<Food> foods;
	
	public Foods() {
		foods = new ArrayList<Food>();
	}
	
	
	public void deleteFoodItem(Food food) {
		foods.remove(food);
	}
	
	public void addFoodItem(Food food) {
		foods.add(food);
	}

}