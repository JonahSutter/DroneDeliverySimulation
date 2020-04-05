import java.util.ArrayList;

public class Meals {
	private ArrayList<Meal> meals;
	
	public Meals() {
		meals = new ArrayList<Meal>();
	}
	
	
	public void addMeal(Meal meal) {
		meals.add(meal);
	}
	
	public void deleteMeal(Meal meal) {
		meals.remove(meal);
	}
	
	public void checkPercentage() {
		
	}

}
