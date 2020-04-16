
public class Food {

	//a food is comprised of a weight and name 
	private String name;
	private double weight;

	//the constructor accepts the name and weight 
	public Food(String name, double weight) {
		this.name = name;
		this.weight = weight;
	}//ends constructor

	//this method checks if two foods are equal 
    public boolean equals(Food other) {
		if (this.name.equals(other.name) && this.weight == other.weight) {
			return true;
		}
		return false;
	}//ends equals

    //returns weight
    public double getWeight() {
    	return this.weight;
    }//ends get weight

    //returns name
    public String getName() {
    	return this.name;
    }//ends get name

    public void changeWeight(double w) {
    	weight = w;
    }//ends change weight

    public void changeName(String n) {
    	this.name = n;
    }//ends change name
}//ends class
