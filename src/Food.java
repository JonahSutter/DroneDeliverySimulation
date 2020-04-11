
public class Food {

	private String name;
	private double weight;

	public Food(String name, double weight) {
		this.name = name;
		this.weight = weight;
	}

    public boolean equals(Food other) {
		if (this.name.equals(other.name) && this.weight == other.weight) {
			return true;
		}
		return false;
	}

    public double getWeight() {
    	return this.weight;
    }

    public String getName() {
    	return name;
    }

}
