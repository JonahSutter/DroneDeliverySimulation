import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;


import java.io.*;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.image.*;


public class Main extends Application {
	//static Foods foodList = new Foods();
	private static Meals mealList = new Meals();
	private static Orders orderList = new Orders(15, 17, 22, 15);
	private static Foods foodList = new Foods();
	private static double[][] locationList;

	private static Food f1 = new Food("1/4 lb Hamburger", 6);
	private static Food f2 = new Food("Fries", 4);
	private static Food f3 = new Food("12 oz Drink", 14);
//	private static ArrayList<Food> foodsList = new ArrayList<Food>(Arrays.asList(f1,f2,f3));
//	private static ArrayList<String> foodNames = new ArrayList<String>(Arrays.asList("1/4 lb Hamburger", "Fries", "12 oz Drink"));

	public static void main(String[] args) {
		Food hamburger = new Food("1/4 lb Hamburger", 6);
		Food fries = new Food("Fries", 4);
		Food drink = new Food("12 oz Drink", 14);
		foodList.addFoodItem(hamburger);
		foodList.addFoodItem(fries);
		foodList.addFoodItem(drink);
		//Adding in basic meal combos as outlined in requirements for the project
		mealList.addMeal(new Meal("Basic Combo",0.55,new ArrayList<Food>() {{add(hamburger);add(fries);add(drink);}}));
		mealList.addMeal(new Meal("Drinkless Combo",0.20,new ArrayList<Food>() {{add(hamburger);add(fries);}}));
		mealList.addMeal(new Meal("Drinkless Double",0.15,new ArrayList<Food>() {{add(hamburger);add(fries);add(hamburger);}}));
		mealList.addMeal(new Meal("Double Burger",0.10,new ArrayList<Food>() {{add(hamburger);add(fries);add(drink);add(hamburger);}}));

		orderList.setMeals(mealList.getMeals()); //TODO we need to fix this data redundancy if possible

		double[][] locationList = new double[25][];
		for (int i = 0; i < 25; i++) {
			locationList[i] = new double[2];
		}

		//Random list of locations
		//TODO: Replace with actual values from Grove City Campus delivery points
		locationList[0] = new double[] {0,0};
		locationList[1] = new double[] {40,50};
		locationList[2] = new double[] {90,60};
		locationList[3] = new double[] {100,100};
		locationList[4] = new double[] {0,100};
		locationList[5] = new double[] {90,8};
		locationList[6] = new double[] {10,30};
		locationList[7] = new double[] {70,65.23};
		locationList[8] = new double[] {48.32,23.51};
		locationList[9] = new double[] {109.3,142.6};
		locationList[10] = new double[] {241,42};
		locationList[11] = new double[] {123,75};
		locationList[12] = new double[] {352,129};
		locationList[13] = new double[] {185,135};
		locationList[14] = new double[] {185,138};
		locationList[15] = new double[] {194,144};
		locationList[16] = new double[] {150,74};
		locationList[17] = new double[] {143,93};
		locationList[18] = new double[] {164,103};
		locationList[19] = new double[] {251,172};
		locationList[20] = new double[] {214,198};
		locationList[21] = new double[] {213,164};
		locationList[22] = new double[] {218,300};
		locationList[23] = new double[] {245,271};
		locationList[24] = new double[] {295,142};
		orderList.setLocations(locationList);

		launch(args);
	}


	@Override
	public void start(Stage primaryStage) {

		try {

			//setting labels and buttons
			primaryStage.setTitle("Starting Screen");

			Label label = new Label("Dromedary Drones");

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

	        Button button1 = new Button("Open");
	        Button button2 = new Button("Exit");

	        button1.setPrefHeight(40);
	        button1.setPrefWidth(140);
	        button2.setPrefHeight(40);
	        button2.setPrefWidth(140);


	        //setting layout of buttons
	        Pane root = new Pane();
	        button1.setLayoutX(180);
	        button1.setLayoutY(200);
	        button2.setLayoutX(180);
	        button2.setLayoutY(250);

	        //adding to root
	        root.getChildren().add(button1);
	        root.getChildren().add(button2);
	        root.getChildren().add(label);

	        //buttons sends to main page
	        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {

	        		mainPage(primaryStage);
	            }
	        });

	        //button exits
	        button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                System.exit(1);
	            }
	        });

	        //show the scene
			Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();


			new AnimationTimer() {
				@Override
				public void handle(long now) {


				}
			}.start();


		} catch(Exception e) {
			e.printStackTrace();
		}

	}

	/*
	 * Displaying the mainPage
	 */
	public static void mainPage(Stage primaryStage) {

		try {

			//Setting title and buttons
			primaryStage.setTitle("Main Page");

			Label label = new Label("Dromedary Drones");

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

	        Button button1 = new Button("Start Simulation");
	        Button button2 = new Button("Edit Meals");
	        Button button3 = new Button("Edit Probabilities");
	        Button button4 = new Button("Exit");
	        //adding map button
	        Button button5 = new Button("Map");


	        //Setting the layouts of the buttons
	        Pane root = new Pane();
	        button1.setLayoutX(180);
	        button1.setLayoutY(200);
	        button2.setLayoutX(180);
	        button2.setLayoutY(250);
	        button3.setLayoutX(180);
	        button3.setLayoutY(300);
	        button4.setLayoutX(180);
	        button4.setLayoutY(350);
	        //adding map button
	        button5.setLayoutX(180);
	        button5.setLayoutY(400);

	        button1.setPrefHeight(40);
	        button1.setPrefWidth(140);
	        button2.setPrefHeight(40);
	        button2.setPrefWidth(140);
	        button3.setPrefHeight(40);
	        button3.setPrefWidth(140);
	        button4.setPrefHeight(40);
	        button4.setPrefWidth(140);
	        //adding map button
	        button5.setPrefHeight(40);
	        button5.setPrefWidth(140);


	        //adding buttons and labels to root
	        root.getChildren().add(button1);
	        root.getChildren().add(button2);
	        root.getChildren().add(button3);
	        root.getChildren().add(button4);
	        root.getChildren().add(button5);
	        root.getChildren().add(label);

	        //buttons sends to simulation page
	        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                simulationPage(primaryStage);
	            }
	        });

	      //buttons sends to meals page
	        button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	        });

	      //buttons sends to probability page
	        button3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                probabilityPage(primaryStage);
	            }
	        });

	      //buttons exits
	        button4.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                System.exit(1);
	            }
	        });


	      //button sends to map page
	        button5.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	               // map(primaryStage);
	            }
	        });

	        //finalize and show page
			Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();


			new AnimationTimer() {
				@Override
				public void handle(long now) {


				}
			}.start();


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void simulationPage(Stage primaryStage) {
		Simulation sim = new Simulation(foodList, mealList, orderList);
		sim.runSimulation();

		JSONParser jsonParser = new JSONParser();


		try {
			FileReader reader = new FileReader("temp.json");
			Object obj = jsonParser.parse(reader);
			JSONObject data = (JSONObject) obj;

			primaryStage.setTitle("Simulation Results");

			Label label = new Label("Dromedary Drones");
			Label label2 = new Label("Simulation Complete!");
			Label label3 = new Label("First in First Out");
			Label label4 = new Label("Knapsack Packing");
			//FIFO labels
			JSONObject FIFOJson = (JSONObject) data.get("FIFO");
			String fifoAvg = String.format("%.4f sec", FIFOJson.get("avgTime")); //turn the double data into a formatted string
			String fifoWorst = String.format("%.4f sec", FIFOJson.get("worstTime"));
			Label fifo1 = new Label("Average Delivery Time:");
			Label fifo2 = new Label("Worst Delivery Time:");
			Label fifo3 = new Label(fifoAvg);
			Label fifo4 = new Label(fifoWorst);
			//Knapsack Labels
			JSONObject KnapsackJson = (JSONObject) data.get("Knapsack");
			String knapsackAvg = String.format("%.4f sec", KnapsackJson.get("avgTime")); //turn the double data into a formatted string
			String knapsackWorst = String.format("%.4f sec", KnapsackJson.get("worstTime"));
			Label knap1 = new Label("Average Delivery Time:");
			Label knap2 = new Label("Worst Delivery Time:");
			Label knap3 = new Label(knapsackAvg);
			Label knap4 = new Label(knapsackWorst);

			//Label Settings
			label.setFont(new Font("Arial", 40));
			label.setLayoutX(280);
			label2.setFont(new Font("Arial", 36));
			label2.setLayoutX(278);
			label2.setLayoutY(65);
			label3.setFont(new Font("Arial", 30));
			label3.setLayoutX(93);
			label3.setLayoutY(125);
			label4.setFont(new Font("Arial", 30));
			label4.setLayoutX(593);
			label4.setLayoutY(125);
			//FIFO labels
			fifo1.setFont(new Font("Arial", 18));
			fifo1.setLayoutX(45);
			fifo1.setLayoutY(177);
			fifo2.setFont(new Font("Arial", 18));
			fifo2.setLayoutX(45);
			fifo2.setLayoutY(213);
			fifo3.setFont(new Font("Arial", 18));
			fifo3.setLayoutX(238);
			fifo3.setLayoutY(177);
			fifo4.setFont(new Font("Arial", 18));
			fifo4.setLayoutX(219);
			fifo4.setLayoutY(213);
			//Knapsack labels
			knap1.setFont(new Font("Arial", 18));
			knap1.setLayoutX(530);
			knap1.setLayoutY(177);
			knap2.setFont(new Font("Arial", 18));
			knap2.setLayoutX(530);
			knap2.setLayoutY(213);
			knap3.setFont(new Font("Arial", 18));
			knap3.setLayoutX(722);
			knap3.setLayoutY(177);
			knap4.setFont(new Font("Arial", 18));
			knap4.setLayoutX(701);
			knap4.setLayoutY(213);

			Button button1 = new Button("Save Data");
			Button button2 = new Button("Home");
			Button button3 = new Button("Exit");

			//Bar charts
			//FIFO chart
			final CategoryAxis fifoTimeAxis = new CategoryAxis();	//X axis - displays time periods
			final NumberAxis fifoNumAxis = new NumberAxis();		//Y axis - displays quantity delivered
			final BarChart<String, Number> fifoChart = new BarChart<String, Number>(fifoTimeAxis, fifoNumAxis);
			fifoChart.setTitle("FIFO Delivery Times");
			fifoTimeAxis.setLabel("Delivery Time");
			fifoNumAxis.setLabel("Num Delivered");

			//FIFO Data
			JSONArray fifoData = (JSONArray) FIFOJson.get("data");	//pulls the raw data from the json
			ArrayList<Integer> fifoTimeData = new ArrayList<Integer>();
			fifoTimeData.add(0);	//put a 0 in the first position
			double time;	//will also be used in knapsack data
			int timeslot;	//will also be used in knapsack
			for(int i = 0; i < fifoData.size(); i++){
				time = (Double) fifoData.get(i);
				timeslot = (int) time / 30;	//this will tell us what timeslot it belongs in (0 for 0-30 sec and so on)

				if(fifoTimeData.size() <= timeslot){
					while(fifoTimeData.size() <= timeslot){
						fifoTimeData.add(0);	//insert 0 until the arraylist is of the correct size
					}
					fifoTimeData.set(timeslot, fifoTimeData.get(timeslot) +1);	//increment the timeslot
				}
				else{
					fifoTimeData.set(timeslot, fifoTimeData.get(timeslot) + 1); //increment the timeslot
				}
			}


			String categoryName;
			XYChart.Series fifoSeries = new XYChart.Series();
			fifoSeries.setName("Number of Orders Delivered");
			for(int i = 0; i < fifoTimeData.size(); i++) {	//puts all the data into the graph
				categoryName = String.format("%d - %d", i*30, (i+1)*30);
				fifoSeries.getData().add(new XYChart.Data(categoryName, fifoTimeData.get(i)));
			}
			fifoChart.getData().addAll(fifoSeries);//adds all the data from the series to the chart
			fifoChart.setLegendVisible(false);	//hides the legend

			//Knapsack Chart
			final CategoryAxis knapsackTimeAxis = new CategoryAxis();
			final NumberAxis knapsackNumAxis = new NumberAxis();
			final BarChart<String, Number> knapsackChart = new BarChart<String, Number>(knapsackTimeAxis, knapsackNumAxis);
			knapsackChart.setTitle("Knapsack Delivery Times");
			knapsackTimeAxis.setLabel("Delivery Time");
			knapsackNumAxis.setLabel("Num Delivered");

			//Knapsack Data
			JSONArray knapsackData = (JSONArray) KnapsackJson.get("data");
			ArrayList<Integer> knapsackTimeData = new ArrayList<Integer>();
			knapsackTimeData.add(0);
			for(int i = 0; i < knapsackData.size(); i++){
				time = (Double) knapsackData.get(i);
				timeslot = (int) time / 30;	//this will tell us what timeslot it belongs in (0 for 0-30 sec and so on)

				if(knapsackTimeData.size() <= timeslot){
					while(knapsackTimeData.size() <= timeslot){
						knapsackTimeData.add(0);	//insert 0 until the arraylist is of the correct size
					}
					knapsackTimeData.set(timeslot, knapsackTimeData.get(timeslot) +1);
				}
				else{
					knapsackTimeData.set(timeslot, knapsackTimeData.get(timeslot) + 1); //increment the position
				}
			}


			XYChart.Series knapsackSeries = new XYChart.Series();
			knapsackSeries.setName("Number of Orders Delivered");
			for(int i = 0; i < knapsackTimeData.size(); i++) {	//puts all the data into the graph
				categoryName = String.format("%d - %d", i*30, (i+1)*30);
				knapsackSeries.getData().add(new XYChart.Data(categoryName, knapsackTimeData.get(i)));
			}
			knapsackChart.getData().addAll(knapsackSeries);
			knapsackChart.setLegendVisible(false);

			Pane root = new Pane();
			fifoChart.setLayoutX(0);
			fifoChart.setLayoutY(249);
			knapsackChart.setLayoutX(540);
			knapsackChart.setLayoutY(249);

			fifoChart.setPrefSize(338, 252);	//set the graph's dimensions
			knapsackChart.setPrefSize(338, 252);

			button1.setLayoutX(370);
			button1.setLayoutY(364);
			button2.setLayoutX(370);
			button2.setLayoutY(319);
			button3.setLayoutX(383);
			button3.setLayoutY(409);

			button1.setPrefHeight(32);
			button1.setPrefWidth(161);
			button2.setPrefHeight(32);
			button2.setPrefWidth(161);
			button3.setPrefHeight(32);
			button3.setPrefWidth(135);

			root.getChildren().add(button1);
			root.getChildren().add(button2);
			root.getChildren().add(button3);
			root.getChildren().add(label);
			root.getChildren().add(label2);
			root.getChildren().add(label3);
			root.getChildren().add(label4);
			root.getChildren().add(fifo1);
			root.getChildren().add(fifo2);
			root.getChildren().add(fifo3);
			root.getChildren().add(fifo4);
			root.getChildren().add(knap1);
			root.getChildren().add(knap2);
			root.getChildren().add(knap3);
			root.getChildren().add(knap4);
			root.getChildren().add(fifoChart);
			root.getChildren().add(knapsackChart);


			button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					//TODO: go to the save screen
					saveInformation(primaryStage);
				}
			});

			button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					mainPage(primaryStage);
				}
			});


			button3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					System.exit(1);
				}
			});

			Scene scene = new Scene(root,900,500);

			primaryStage.setScene(scene);
			primaryStage.show();


			new AnimationTimer() {
				@Override
				public void handle(long now) {


				}
			}.start();


		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void probabilityPage(Stage primaryStage) {
		try {
			//Set the title of the window
			primaryStage.setTitle("Meal Probabilities");

			//Keep the label at the top
			Label label = new Label("Dromedary Drones");
			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);


			//Add the meals chart Label
			Label mealLabel = new Label("Meals:");
			mealLabel.setFont(new Font("Arial",20));
			mealLabel.setLayoutX(250);
			mealLabel.setLayoutY(72);

			//Add the meals chart Probability label
			Label mealProbLabel = new Label("(%):");
			mealProbLabel.setFont(new Font("Arial",20));
			mealProbLabel.setLayoutX(390);
			mealProbLabel.setLayoutY(72);

			//Add a label that tells the user what the percentages total to
			Label totalLabel = new Label("Percentages Total To:");
		    totalLabel.setFont(new Font("Arial",15));
		    totalLabel.setLayoutX(245);
		    totalLabel.setLayoutY(385);

		    //Calculate the total percentage of meal probabilities
		    double total = 0;
		    for (int i = 0; i < mealList.getMeals().size(); i++) {
		    	total += mealList.getMeals().get(i).getPercentage();
		    }
		    //Add a label with the total percentage of meal probabilities
		    Label totalVal = new Label(total + "");
		    totalVal.setFont(new Font("Arial",15));
		    totalVal.setLayoutX(395);
		    totalVal.setLayoutY(385);

		    //Create an error label for the Shift order values
		    Label errorLabel = new Label("");
		    errorLabel.setFont(new Font("Arial",15));
		    errorLabel.setLayoutX(135);
		    errorLabel.setLayoutY(420);

		    //Create an error label for the meal probability values
		    Label errorLabel2 = new Label("");
		    errorLabel2.setFont(new Font("Arial", 15));
		    errorLabel2.setLayoutX(30);
		    errorLabel2.setLayoutY(325);

		    //Add a label for the Shift order values
		    Label mealsPerShift = new Label("Meals per shift:");
		    mealsPerShift.setFont(new Font("Arial",20));
		    mealsPerShift.setLayoutX(30);
		    mealsPerShift.setLayoutY(72);

		    //Create a display for the meals
	        ListView<String> listFood = new ListView<String>();
	        //Create a display for the meal probabilities
	        ListView<String> listProbs = new ListView<String>();

	        //Create a list that holds the meal names
	        ObservableList<String> foodItem =FXCollections.observableArrayList ();
	        //Create a list that holds the meal probabilities
		    ObservableList<String> foodProbs =FXCollections.observableArrayList ();

		    //Add the meals and their probabilities to their respective list.
	        for (int i = 0; i < mealList.getMeals().size(); i++) {
		    	foodItem.add(mealList.getMeals().get(i).getName());
		    	foodProbs.add("" + mealList.getMeals().get(i).getPercentage()*100);
		    }

	        //Link the list that holds the valuse to the display values
		    listFood.setItems(foodItem);
		    listProbs.setItems(foodProbs);

		    //Allow the list of probabilities to be editable
		    listProbs.setEditable(true);
		    listProbs.setCellFactory(TextFieldListCell.forListView());
		    //Every time a probability is updated, check if it's a valid entry and recalculate total percentage value
		    listProbs.getItems().addListener(new ListChangeListener<Object>() {
		        @Override
		        public void onChanged(ListChangeListener.Change change) {
		        	double total = 0;
		        	errorLabel.setText("");
		            for (int i = 0; i < listProbs.getItems().size(); i++) {
		            	try {
		            		String entryValue = listProbs.getItems().get(i);
		            		double val = Double.parseDouble(entryValue);
		            		if (val < 0) {
		            			errorLabel.setText("Invalid Meal Percentage: \n\t A value less than 0 was entered.");
		            		} else if (val > 100) {
		            			errorLabel.setText("Invalid Meal Percentage: \n\t A value greater than 100 was entered.");
		            		}
		            		total += val;
		            	} catch (Exception E) {
	            			errorLabel.setText("Invalid Meal Percentage: \n\t A non-numer value was entered.");
		            	}
		            }
		            total = (total * 1000)/1000;
		            if (total == 100) {
		                totalVal.setText("100");
		            } else {
		            	totalVal.setText(String.format("%3.2f",total));
		            }
		            for (int i = 0; i < mealList.getMeals().size(); i++) {
		            	double percent = Double.parseDouble(foodProbs.get(i))/100;
		            	mealList.getMeals().get(i).setPercentage(percent);
				    }
		        }
		    });

		  //Set the layout for the meal name display
	        listFood.setPrefWidth(140);
	        listFood.setPrefHeight(275);
	        listFood.setLayoutX(250);
	        listFood.setLayoutY(100);

	        //Set the layout for the meal probability display
	        listProbs.setLayoutX(390);
	        listProbs.setLayoutY(100);
	        listProbs.setPrefWidth(70);
	        listProbs.setPrefHeight(275);



		    //Create a list to display the shifts
	        ListView<String> labeledShifts = new ListView<String>();
	        //Create a list to display the shift order values
	        ListView<String> shiftProbabilities = new ListView<String>();

	        //Create a list to hold the shift titles
		    ObservableList<String> shiftLabels =FXCollections.observableArrayList (
		            "First Shift: ", "Second Shift: ", "Third Shift: ", "Fourth Shift: ");
		    //Create a list to hold the shift order values
		    ObservableList<String> shiftProbs =FXCollections.observableArrayList ();
		    //Insert the shift order values into the corresponding list
		    shiftProbs.add("" + orderList.getFirstHour());
		    shiftProbs.add("" + orderList.getSecondHour());
		    shiftProbs.add("" + orderList.getThirdHour());
		    shiftProbs.add("" + orderList.getFourthHour());

		    //Set the display lists for the shifts to hold their respective items
		    shiftProbabilities.setItems(shiftProbs);
		    labeledShifts.setItems(shiftLabels);

		    //Set the layout for the shift labels
		    labeledShifts.setLayoutX(25);
		    labeledShifts.setPrefHeight(100);
		    labeledShifts.setPrefWidth(125);
		    labeledShifts.setLayoutY(200);

		    //Set the layout for the shift order values
		    shiftProbabilities.setLayoutX(150);
		    shiftProbabilities.setPrefHeight(100);
		    shiftProbabilities.setPrefWidth(60);
		    shiftProbabilities.setLayoutY(200);

		    //Add a listener to the shift order values that verifies valid entries
		    //   and updates the value if a the number entered was valid.
		    shiftProbabilities.getItems().addListener(new ListChangeListener<Object>() {
		        @Override
		        public void onChanged(ListChangeListener.Change change) {
		        	errorLabel2.setText("");
		            for (int i = 0; i < shiftProbabilities.getItems().size(); i++) {
		            	try {
		            		String entryValue = shiftProbabilities.getItems().get(i);
		            		int val = Integer.parseInt(entryValue);
		            		if (val < 0) {
		            			errorLabel2.setText("Invalid Shift Entry: \t\nValue less than 0)");
		            		}
		            	} catch (Exception E) {
	            			errorLabel2.setText("Invalid Shift Entry: \n\tNot an integer value.");
		            	}
		            }
		            if (errorLabel2.getText() == "") {
            			ObservableList<String> data = shiftProbabilities.getItems();
            			orderList.setHourlyRate(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(1)),
            					Integer.parseInt(data.get(2)), Integer.parseInt(data.get(3)));
		            }
		        }
		    });
		    //Set the shift order values to be editable
		    shiftProbabilities.setEditable(true);
		    shiftProbabilities.setCellFactory(TextFieldListCell.forListView());

		    //Create a home button and position it
	        Button home = new Button("Home");
	        home.setLayoutX(130);
	        home.setLayoutY(385);
	        home.setPrefWidth(75);

	        //Add all the created elements to the screen
	        Pane root = new Pane();
	        root.getChildren().add(listProbs);
	        root.getChildren().add(listFood);
	        root.getChildren().add(home);
	        root.getChildren().add(label);
	        root.getChildren().addAll(totalVal,totalLabel,errorLabel);
	        root.getChildren().addAll(mealLabel,mealProbLabel,mealsPerShift);
	        root.getChildren().addAll(labeledShifts,shiftProbabilities,errorLabel2);

	        //Set the home button to only work if there are no errors on the page
	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

	            @Override public void handle(ActionEvent e) {
		            if (errorLabel.getText() == "" && errorLabel2.getText() == "") {
		            	if (totalVal.getText() == "100") {
			                mainPage(primaryStage);
		            	} else {
			            	errorLabel.setText("Invalid Summation: \n\tMeal percentages must total 100.");
			            }
		            }
	            }
	        });

	        //Have the GUI page display
			Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void meals(Stage primaryStage) {

		try {
			primaryStage.setTitle("Meals Page");

			Label label = new Label("Dromedary Drones");

			Label mealsLabel = new Label("Meals");
			Label foods = new Label("Foods");
			Label errorLabel = new Label("");

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

			mealsLabel.setFont(new Font("Arial", 20));
			mealsLabel.setLayoutX(370);
			mealsLabel.setLayoutY(80);

			foods.setFont(new Font("Arial", 20));
			foods.setLayoutX(70);
			foods.setLayoutY(80);

			errorLabel.setFont(new Font("Arial", 20));
			errorLabel.setLayoutX(160);
			errorLabel.setLayoutY(60);
			errorLabel.setTextFill(Color.web("#cc0000"));

			  //adding remove food button
	        Button removeFood = new Button("Remove Food");
	        //adding edit food button
	        Button editFood = new Button("Edit Food");
	        Button addFood = new Button("Add Food");
	        Button addMeal = new Button("Add Meal");
	        Button editMeal = new Button("Edit Meal");
	        Button removeMeal = new Button("Remove Meal");

	        Button home = new Button("Home");
	        ListView<String> listFood = new ListView<String>();
	        ListView<String> listMeals = new ListView<String>();
	        ObservableList<String> food =FXCollections.observableArrayList ();
	        for (int i = 0; i < foodList.getFoods().size(); i++) {
	        	food.add(foodList.getFoods().get(i).getName());
	        }
		        listFood.setItems(food);


		    ObservableList<String> meals =FXCollections.observableArrayList ();

//	        ObservableList<String> food =FXCollections.observableArrayList (
//			        "Cheeseburger", "Hamburger", "Fries", "Drink");
//			    listFood.setItems(food);

			//Adds meals to the listView from the mealList, displaying their name
		    ArrayList<Meal> mealArray = new ArrayList<Meal>();
		    mealArray = mealList.getMeals();
		    for (int i = 0; i < mealArray.size(); i++) {
		    	meals.add((mealArray.get(i).getName()));
		    }

			    listMeals.setItems(meals);

	        Pane root = new Pane();

	        addFood.setLayoutX(180);
	        addFood.setLayoutY(100);
	        addMeal.setLayoutX(180);
	        addMeal.setLayoutY(250);
	        editMeal.setLayoutX(180);
	        editMeal.setLayoutY(300);
	        removeMeal.setLayoutX(180);
	        removeMeal.setLayoutY(350);
	        home.setLayoutX(180);
	        home.setLayoutY(450);

	        //adding remove food button
	        removeFood.setLayoutX(180);
	        removeFood.setLayoutY(200);
	        //adding edit food button
	        editFood.setLayoutX(180);
	        editFood.setLayoutY(150);

	        //adding remove food button
	        removeFood.setPrefHeight(40);
	        removeFood.setPrefWidth(140);
	        //adding edit food button
	        editFood.setPrefHeight(40);
	        editFood.setPrefWidth(140);

	        addFood.setPrefHeight(40);
	        addFood.setPrefWidth(140);
	        addMeal.setPrefHeight(40);
	        addMeal.setPrefWidth(140);

	        editMeal.setPrefHeight(40);
	        editMeal.setPrefWidth(140);

	        removeMeal.setPrefHeight(40);
	        removeMeal.setPrefWidth(140);

	        home.setPrefHeight(40);
	        home.setPrefWidth(140);

	        listFood.setPrefWidth(140);
	        listFood.setPrefHeight(300);
	        listMeals.setPrefWidth(140);
	        listMeals.setPrefHeight(300);

	        listFood.setLayoutX(30);
	        listFood.setLayoutY(100);
	        listMeals.setLayoutX(330);
	        listMeals.setLayoutY(100);

	      //adding remove food button
	        root.getChildren().add(removeFood);
	        //adding edit food button
	        root.getChildren().add(editFood);

	        root.getChildren().add(addFood);
	        root.getChildren().add(addMeal);
	        root.getChildren().add(listFood);
	        root.getChildren().add(listMeals);
	        root.getChildren().add(label);
	        root.getChildren().add(editMeal);
	        root.getChildren().add(home);
	        root.getChildren().add(foods);
	        root.getChildren().add(mealsLabel);
	        root.getChildren().add(removeMeal);
	        root.getChildren().add(errorLabel);

	        addFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                addFoodPage(primaryStage);
	            }
	        });

	      //adding removing food button
	        removeFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                removeFoodPage(primaryStage);
	            }
	        });

	      //adding removing food button
	        editFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                editFoodPage(primaryStage);
	            }
	        });


	        addMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                addMealPage(primaryStage);
	            }
	        });

	        editMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	        	@Override public void handle(ActionEvent e) {
	        		try {
	        			editMealPage(primaryStage, mealList.getMeals().get(listMeals.getSelectionModel().getSelectedIndex()));
	        		}
	        		catch (Exception f){
	        			errorLabel.setText("Please select a meal");
	        		}

	        	}
	        });

	        removeMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	try {
	            		mealList.deleteMeal(mealList.getMeals().get(listMeals.getSelectionModel().getSelectedIndex()));
		                meals(primaryStage);
	            	}
	            	catch (Exception f) {
	            		errorLabel.setText("Please select a meal");
	            	}
	            }
	        });

	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	mainPage(primaryStage);
	            }
	        });

			removeFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
				@Override public void handle(ActionEvent e) {
					removeFoodPage(primaryStage);
				}
			});

			Scene scene = new Scene(root,500,500);

			primaryStage.setScene(scene);
			primaryStage.show();


			new AnimationTimer() {
				@Override
				public void handle(long now) {


				}
			}.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void addFoodPage(Stage primaryStage) {
		try {
			primaryStage.setTitle("Add Food Page");
			//add the labels
			Label label = new Label("Dromedary Drones");
			Label name = new Label("Food Name");
			Label weight = new Label("Food Weight (oz)");
			//size the labels
			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);
			name.setFont(new Font("Arial", 12));
			name.setLayoutX(180);
			name.setLayoutY(180);
			weight.setFont(new Font("Arial", 12));
			weight.setLayoutX(180);
			weight.setLayoutY(230);
			//add buttons
	        Button saveFood = new Button("Save Food");
	        Button cancel = new Button("Cancel");
	        //add text fields
	        TextField foodName = new TextField();
	        TextField foodWeight = new TextField();
	        //position of buttons
	        saveFood.setLayoutX(180);
	        saveFood.setLayoutY(300);
	        cancel.setLayoutX(180);
	        cancel.setLayoutY(350);
	        saveFood.setPrefHeight(40);
	        saveFood.setPrefWidth(140);
	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);
	        //position of text fields
	        foodName.setLayoutX(180);
	        foodName.setLayoutY(200);
	        foodWeight.setLayoutX(180);
	        foodWeight.setLayoutY(250);
	        //add to the root
	        Pane root = new Pane();
	        root.getChildren().add(saveFood);
	        root.getChildren().add(cancel);
	        root.getChildren().add(foodName);
	        root.getChildren().add(foodWeight);
	        root.getChildren().add(label);
	        root.getChildren().add(name);
	        root.getChildren().add(weight);

	        //if you hit the same food button
	        saveFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	if(!checkIfEmpty(foodName)&& !checkIfEmpty(foodWeight) && !checkDuplicateName(foodName) && checkWeightIsNum(foodWeight) && checkWeight(foodWeight)) {
	            	String addName = foodName.getText();
	            	double w = Double.parseDouble(foodWeight.getText());
	      	        //need to figure out how to get the weight
	      	        Food f = new Food(addName, w);
	      	        foodList.addFoodItem(f);
	                meals(primaryStage);
	            }
	            }
	        });
	        //if you hit the cancel button
	        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	        });
			Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();
			new AnimationTimer() {
				@Override
				public void handle(long now) {
				}
			}.start();
		} catch(Exception e) {
			e.printStackTrace();
		}//ends catch
	}//ends the method
	public static boolean checkDuplicateName(TextField t) {
		String tryName = t.getText();
		for(int i = 0; i<foodList.getFoods().size(); i++) {
			if(foodList.getFoods().get(i).getName().equals(tryName)) {
				failedAlert("This food is already listed");
				return true;
			}
		}
		return false;
	}
	public static boolean checkWeight(TextField t) {
		double w = Double.parseDouble(t.getText());
		//checks that weight is in the right bounds
		if(w>0) {
			return true;
		}else {
			failedAlert("Weight must be greater than 0");
			return false;
		}
	}//ends check weight
	public static boolean checkWeightIsNum(TextField t) {
		String s = t.getText();
		for(int i = 0; i<s.length(); i++) {
			char c = s.charAt(i);
			if(Character.isAlphabetic(c)) {
				failedAlert("Weight must be only numbers");
				return false;
			}
		}//ends for loop
		return true;
	}//ends check weight is num
	public static void failedAlert(String s) {
		Alert fail = new Alert(AlertType.INFORMATION);
		fail.setHeaderText("Failed");
		fail.setContentText(s);
		fail.showAndWait();
	}//ends alert message
	public static boolean checkIfEmpty(TextField t) {
		if(t.getText().trim().isEmpty()) {
			failedAlert("One or more fields is empty");
			return true;
		}else {
			return false;
		}
	}//ends check if empty
	public static void addMealPage(Stage primaryStage) {
		try {
			primaryStage.setTitle("Add Meal Page");
			Label label = new Label("Dromedary Drones");
			Label mealNameHere = new Label("Meal Name");
			Label errorLabel = new Label("");

			ListView<String> listFoods = new ListView<String>();

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

			mealNameHere.setFont(new Font("Arial", 12));
			mealNameHere.setLayoutX(220);
			mealNameHere.setLayoutY(80);

			errorLabel.setFont(new Font("Arial", 12));
			errorLabel.setLayoutX(220);
			errorLabel.setLayoutY(50);
			errorLabel.setTextFill(Color.web("#cc0000"));

	        Button saveMeal = new Button("Save Meal");
	        Button cancel = new Button("Cancel");

	        TextField mealName = new TextField();
	        mealName.setPromptText("Enter meal name.");

	        TextField mealWeight1 = new TextField();
	        TextField mealWeight2 = new TextField();
	        TextField mealWeight3 = new TextField();
	        TextField mealWeight4 = new TextField();

	        mealWeight1.setPromptText("0.0");
	        mealWeight2.setPromptText("0.0");
	        mealWeight3.setPromptText("0.0");
	        mealWeight4.setPromptText("0.0");


		    ObservableList<String> foods = FXCollections.observableArrayList(
		    		"None"
		    		);
		    ArrayList<Food> foodArray = new ArrayList<Food>();
//		    foodArray = foodList.getFoods();
//		    for (int i = 0; i < foodArray.size(); i++) {
//		    	foods.add((foodArray.get(i).getName()));
//		    }
//			    listFoods.setItems(foods);
			    for (int i = 0; i < foodList.getFoods().size(); i++) {
			    	foods.add((foodList.getFoods().get(i).getName()));
			    }
				    listFoods.setItems(foods);


	        final ComboBox<String> option1 = new ComboBox<String>(foods);
	        final ComboBox<String> option2 = new ComboBox<String>(foods);
	        final ComboBox<String> option3 = new ComboBox<String>(foods);
	        final ComboBox<String> option4 = new ComboBox<String>(foods);

	        option1.setValue(foods.get(0));
	        option2.setValue(foods.get(0));
	        option3.setValue(foods.get(0));
	        option4.setValue(foods.get(0));

	        Pane root = new Pane();

	        saveMeal.setLayoutX(180);
	        saveMeal.setLayoutY(400);
	        cancel.setLayoutX(180);
	        cancel.setLayoutY(450);

	        saveMeal.setPrefHeight(40);
	        saveMeal.setPrefWidth(140);
	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);

	        mealName.setLayoutX(180);
	        mealName.setLayoutY(100);

	        option1.setLayoutX(180);
	        option1.setLayoutY(150);

	        option2.setLayoutX(180);
	        option2.setLayoutY(200);

	        option3.setLayoutX(180);
	        option3.setLayoutY(250);

	        option4.setLayoutX(180);
	        option4.setLayoutY(300);

	        mealWeight1.setLayoutX(330);
	        mealWeight1.setLayoutY(150);
	        mealWeight1.setPrefWidth(40);

	        mealWeight2.setLayoutX(330);
	        mealWeight2.setLayoutY(200);
	        mealWeight2.setPrefWidth(40);

	        mealWeight3.setLayoutX(330);
	        mealWeight3.setLayoutY(250);
	        mealWeight3.setPrefWidth(40);

	        mealWeight4.setLayoutX(330);
	        mealWeight4.setLayoutY(300);
	        mealWeight4.setPrefWidth(40);

	        root.getChildren().add(saveMeal);
	        root.getChildren().add(cancel);
	        root.getChildren().add(mealName);
	        root.getChildren().add(label);
	        root.getChildren().add(mealNameHere);
	        root.getChildren().add(option1);
	        root.getChildren().add(option2);
	        root.getChildren().add(option3);
	        root.getChildren().add(option4);
	        root.getChildren().add(mealWeight1);
	        root.getChildren().add(mealWeight2);
	        root.getChildren().add(mealWeight3);
	        root.getChildren().add(mealWeight4);
	        root.getChildren().add(errorLabel);

	        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
	        TextFormatter<String> formatter1 = new TextFormatter<String>((UnaryOperator<TextFormatter.Change>) change -> {
	            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	        });

	        TextFormatter<String> formatter2 = new TextFormatter<String>((UnaryOperator<TextFormatter.Change>) change -> {
	            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	        });

	        TextFormatter<String> formatter3 = new TextFormatter<String>((UnaryOperator<TextFormatter.Change>) change -> {
	            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	        });

	        TextFormatter<String> formatter4 = new TextFormatter<String>((UnaryOperator<TextFormatter.Change>) change -> {
	            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	        });

	        mealWeight1.setTextFormatter(formatter1);
	        mealWeight2.setTextFormatter(formatter2);
	        mealWeight3.setTextFormatter(formatter3);
	        mealWeight4.setTextFormatter(formatter4);


	        saveMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {

	            	ArrayList<Food> newMeal = new ArrayList<Food>();	//list of foods to add to meal
	            	String newMealName;
	            	boolean hasValidFood = false;
	            	boolean hasValidWeight = true;

	            	System.out.println(mealName.getText().toString());

	            	//If meal does not have a name, display invalidName
	            	if (mealName.getText().equals("")) {
	            		errorLabel.setLayoutX(220);
	            		errorLabel.setText("Invalid Name");
	            	}

	            	else if (!option1.getValue().toString().equals("None")
	            			|| !option2.getValue().toString().equals("None")
	            			|| !option3.getValue().toString().equals("None")
	            			|| !option4.getValue().toString().equals("None")) {
	            		hasValidFood = true;

	            		if (!option1.getValue().toString().equals("None")) {
	            			if (mealWeight1.getText().toString().equals("")) {
	            				//skip
	            				errorLabel.setLayoutX(150);
	            				errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}
	            			else {
	    		            	String food1Name = (String) option1.getValue();
	    		            	double food1Weight = Double.parseDouble(mealWeight1.getText());
	    		            	Food newFood = new Food(food1Name, food1Weight);
	    		            	newMeal.add(newFood);
	            			}
	            		}
	            		if (!option2.getValue().toString().equals("None")) {
	            			if (mealWeight2.getText().toString().equals("")) {
	            				//skip
	            				errorLabel.setLayoutX(150);
	            				errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}
	            			else {
	    		            	String food2Name = (String) option2.getValue();
	    		            	double food2Weight = Double.parseDouble(mealWeight2.getText());
	    		            	Food newFood = new Food(food2Name, food2Weight);
	    		            	newMeal.add(newFood);
	            			}
	            		}
            			if (!option3.getValue().toString().equals("None")) {
	            			if (mealWeight3.getText().toString().equals("")) {
	            				//skip
	            				errorLabel.setLayoutX(150);
	            				errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}
	            			else {
	    		            	String food3Name = (String) option3.getValue();
	    		            	double food3Weight = Double.parseDouble(mealWeight3.getText());
	    		            	Food newFood = new Food(food3Name, food3Weight);
	    		            	newMeal.add(newFood);
	            			}
            			}
            			if (!option4.getValue().toString().equals("None")) {
	            			if (mealWeight4.getText().toString().equals("")) {
	            				//skip
	            				errorLabel.setLayoutX(150);
	            				errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}
	            			else {
	    		            	String food4Name = (String) option4.getValue();
	    		            	double food4Weight = Double.parseDouble(mealWeight4.getText());
	    		            	Food newFood = new Food(food4Name, food4Weight);
	    		            	newMeal.add(newFood);
	            			}
            			}
            			if (hasValidFood && hasValidWeight) {
    		            	newMealName = mealName.getText().toString();
    		            	Meal testMeal = new Meal(newMealName, 0, newMeal);
    		            	mealList.addMeal(testMeal);
    		                meals(primaryStage);
            			}
	            	}

	            	else {
	            		errorLabel.setLayoutX(150);
	            		errorLabel.setText("Please select a food to add to the meal");
	            	}

	            	System.out.println(option1.getValue().toString());
	            }
	        });


	        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	        });

			Scene scene = new Scene(root,500,500);

			primaryStage.setScene(scene);
			primaryStage.show();


			new AnimationTimer() {
				@Override
				public void handle(long now) {


				}
			}.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void editMealPage(Stage primaryStage, Meal mealToBeEdited) {
		try {
			Pane root = new Pane();
			ArrayList<Food>mealFoods = new ArrayList<Food>();
			mealFoods = mealToBeEdited.getFoods();

			primaryStage.setTitle("Edit Meal Page");

			Label label = new Label("Dromedary Drones");
			Label mealNameHere = new Label("Meal Name");
			Label errorLabel = new Label("");

			ListView<String> listFoods = new ListView<String>();

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

			mealNameHere.setFont(new Font("Arial", 12));
			mealNameHere.setLayoutX(220);
			mealNameHere.setLayoutY(80);

			errorLabel.setFont(new Font("Arial", 12));
			errorLabel.setLayoutX(220);
			errorLabel.setLayoutY(50);
			errorLabel.setTextFill(Color.web("#cc0000"));

	        Button saveMeal = new Button("Save Meal");
	        Button cancel = new Button("Cancel");

	        TextField mealName = new TextField();
	        mealName.setText(mealToBeEdited.getName());

		    ObservableList<String> foods = FXCollections.observableArrayList(
		    		"None"
		    		);
		    ArrayList<Food> foodArray = new ArrayList<Food>();
			   for (int i = 0; i < foodList.getFoods().size(); i++) {
			    	foods.add((foodList.getFoods().get(i).getName()));
			    }
				listFoods.setItems(foods);

			//Creates an array of comboBoxes to hold food options
			ComboBox[] foodOptions = new ComboBox[4];

			//Creates 4 comboBoxes
			for (int i = 0; i < 4; i++) {
				ComboBox<String> cb = new ComboBox<String>();
				cb.setLayoutX(180);
				cb.setLayoutY(150 + 50*i);
				cb.setValue(foods.get(0));
				cb.setItems(foods);
				root.getChildren().add(cb);
				foodOptions[i] = cb;
			}

	        //Array to hold to textFields for the weight of the foods in the meal
	        TextField[] foodWeights = new TextField[4];

	        //Creates 4 textFields to hold food weights and orders them nicely, storing them in TextField[i]
	        for (int i = 0; i < 4; i++) {
	        	TextField tf = new TextField();
	        	tf.setPrefWidth(40);
	        	tf.setLayoutX(330);
	        	tf.setLayoutY(150 + i*50);
	        	tf.setPromptText("0.0");
	        	root.getChildren().add(tf);
	        	foodWeights[i] = tf;
	        }

	        //Fills in the names and weights of the foods
	        for (int i = 0; i < mealFoods.size(); i++) {
	        	foodWeights[i].setText(Double.toString(mealFoods.get(i).getWeight()));
	        	foodOptions[i].setValue(mealFoods.get(i).getName().toString());
	        }


	        saveMeal.setLayoutX(180);
	        saveMeal.setLayoutY(400);
	        cancel.setLayoutX(180);
	        cancel.setLayoutY(450);

	        saveMeal.setPrefHeight(40);
	        saveMeal.setPrefWidth(140);
	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);

	        mealName.setLayoutX(180);
	        mealName.setLayoutY(100);

	        root.getChildren().add(saveMeal);
	        root.getChildren().add(cancel);
	        root.getChildren().add(mealName);
	        root.getChildren().add(label);
	        root.getChildren().add(mealNameHere);
	        root.getChildren().add(errorLabel);

	        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");
	        TextFormatter<String> formatter1 = new TextFormatter<String>((UnaryOperator<TextFormatter.Change>) change -> {
	            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	        });

	        TextFormatter<String> formatter2 = new TextFormatter<String>((UnaryOperator<TextFormatter.Change>) change -> {
	            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	        });

	        TextFormatter<String> formatter3 = new TextFormatter<String>((UnaryOperator<TextFormatter.Change>) change -> {
	            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	        });

	        TextFormatter<String> formatter4 = new TextFormatter<String>((UnaryOperator<TextFormatter.Change>) change -> {
	            return pattern.matcher(change.getControlNewText()).matches() ? change : null;
	        });

	        foodWeights[0].setTextFormatter(formatter1);
	        foodWeights[1].setTextFormatter(formatter2);
	        foodWeights[2].setTextFormatter(formatter3);
	        foodWeights[3].setTextFormatter(formatter4);


	        saveMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {

	            	ArrayList<Food> newMeal = new ArrayList<Food>();	//list of foods to add to meal
	            	String newMealName;
	            	boolean hasValidFood = false;
	            	boolean hasValidWeight = false;

	            	System.out.println(mealName.getText().toString());

	            	//If meal does not have a name, display invalidName, and only do it once
	            	if (mealName.getText().equals("")) {
	            		errorLabel.setLayoutX(220);
	            		errorLabel.setText("Invalid Name");
	            	}

	            	else if (!foodOptions[0].getValue().toString().equals("None")
	            			|| !foodOptions[1].getValue().toString().equals("None")
	            			|| !foodOptions[2].getValue().toString().equals("None")
	            			|| !foodOptions[3].getValue().toString().equals("None")) {
	            		hasValidFood = true;

	            		if (!foodOptions[0].getValue().toString().equals("None")) {
	            			if (foodWeights[0].getText().toString().equals("")) {
	            				//skip
	            				errorLabel.setLayoutX(150);
	    	            		errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}
	            			else {
	    		            	String food1Name = (String) foodOptions[0].getValue();
	    		            	double food1Weight = Double.parseDouble(foodWeights[0].getText());
	    		            	Food newFood = new Food(food1Name, food1Weight);
	    		            	newMeal.add(newFood);
	    		            	hasValidWeight = true;
	            			}
	            		}
	            		if (!foodOptions[1].getValue().toString().equals("None")) {
	            			if (foodWeights[1].getText().toString().equals("")) {
	            				//skip
	            				errorLabel.setLayoutX(150);
	    	            		errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}
	            			else {
	    		            	String food2Name = (String) foodOptions[1].getValue();
	    		            	double food2Weight = Double.parseDouble(foodWeights[1].getText());
	    		            	Food newFood = new Food(food2Name, food2Weight);
	    		            	newMeal.add(newFood);
	    		            	hasValidWeight = true;
	            			}
	            		}
            			if (!foodOptions[2].getValue().toString().equals("None")) {
	            			if (foodWeights[2].getText().toString().equals("")) {
	            				//skip
	            				errorLabel.setLayoutX(150);
	    	            		errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}
	            			else {
	    		            	String food3Name = (String) foodOptions[2].getValue();
	    		            	double food3Weight = Double.parseDouble(foodWeights[2].getText());
	    		            	Food newFood = new Food(food3Name, food3Weight);
	    		            	newMeal.add(newFood);
	    		            	hasValidWeight = true;
	            			}
            			}
            			if (!foodOptions[3].getValue().toString().equals("None")) {
	            			if (foodWeights[3].getText().toString().equals("")) {
	            				//skip
	            				errorLabel.setLayoutX(150);
	    	            		errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}
	            			else {
	    		            	String food4Name = (String) foodOptions[3].getValue();
	    		            	double food4Weight = Double.parseDouble(foodWeights[3].getText());
	    		            	Food newFood = new Food(food4Name, food4Weight);
	    		            	newMeal.add(newFood);
	    		            	hasValidWeight = true;
	            			}
            			}
            			if (hasValidFood && hasValidWeight) {
    		            	newMealName = mealName.getText().toString();

    		            	//Gets size of list of foods for this meal
    		            	int size = mealToBeEdited.getFoods().size();

    		            	//Remove all foods
    		            	for (int i = 0; i < size; i++ ) {
    		            		System.out.println(mealToBeEdited.getFoods().get(0).getName());
    		            		mealToBeEdited.removeFoodFromMeal(mealToBeEdited.getFoods().get(0));
    		            	}

    		            	//Add all foods back in
    		            	for (int i = 0; i < newMeal.size(); i++) {
    		            		mealToBeEdited.addFoodToMeal(newMeal.get(i));
    		            	}

    		            	//Set the name of the meal
    		            	mealToBeEdited.setName(newMealName);
    		                meals(primaryStage);
            			}
	            	}

	            	else {
	            		errorLabel.setLayoutX(150);
	            		errorLabel.setText("Please select a food to add to the meal");
	            	}
	            }
	        });

	        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	        });

			Scene scene = new Scene(root,500,500);

			primaryStage.setScene(scene);
			primaryStage.show();


			new AnimationTimer() {
				@Override
				public void handle(long now) {


				}
			}.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static void uploadMapPage(Stage primaryStage) {

	}

	/*
	 * page to save info
	 */
	public static void saveInformation(Stage primaryStage) {

		try {

			//setting buttons and title
			primaryStage.setTitle("Saving Screen");

			Label label = new Label("Dromedary Drones");

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

	        Button home = new Button("Home");

	        //setting layout and width of buttons
	        home.setLayoutX(180);
	        home.setLayoutY(250);
	        home.setPrefHeight(40);
	        home.setPrefWidth(140);
	        Button button1 = new Button("Save Information");
	        Button button2 = new Button("Exit");

	        button1.setLayoutX(130);
	        button1.setPrefWidth(140);
	        button1.setPrefHeight(40);
	        button1.setPrefWidth(140);
	        button2.setPrefHeight(40);
	        button2.setPrefWidth(140);




	        Pane root = new Pane();
	        button1.setLayoutX(180);
	        button1.setLayoutY(200);
	        button2.setLayoutX(180);
	        button2.setLayoutY(300);


	        //add buttons to root
	        root.getChildren().add(button1);
	        root.getChildren().add(button2);
	        root.getChildren().add(label);
	        root.getChildren().add(home);

	        //button that sends to file explorer and saves
	        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	           	  FileChooser fileChooser = new FileChooser();

                  //Set extension filter
                  FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
                  fileChooser.getExtensionFilters().add(extFilter);

                  //Show save file dialog
                  File file = fileChooser.showSaveDialog(primaryStage);


                  fileChooser.getExtensionFilters().add(extFilter);

	                if (file != null) {
	                	SaveFile(file);
	                }

	            }
	        });


	        //button that sends to main page
	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	        		mainPage(primaryStage);
	            }
	        });



	        //button that exits
	        button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                System.exit(1);
	            }
	        });

	        //show the scene
			Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();


			new AnimationTimer() {
				@Override
				public void handle(long now) {


				}
			}.start();


		} catch(Exception e) {
			e.printStackTrace();
		}


	}

	/*
	 * Method to save to a file
	 * @param file is the file to be saved
	 */
	 private static void SaveFile(File file){
	        try {

	        	//get where our data is
	        	File json = new File("temp.json");

	        	Scanner scn = new Scanner(json);

	        	String content = "";

	        	//save the data to a string
	        	while (scn.hasNextLine()) {
	        		content += scn.nextLine();
	        	}

	        	//write the string to the file
	        	 PrintWriter writer;
	             writer = new PrintWriter(file);
	             writer.println(content);
	             writer.close();


	        } catch (IOException ex) {

	           System.out.println("File Logging Error");

	        }

	    }


	public static void editFoodPage(Stage primaryStage) {
		try {
			primaryStage.setTitle("Edit Food Page");
			Label label = new Label("Dromedary Drones");
			//set label positions
			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);
			//add buttons
	        Button editName = new Button("Change Food Name");
	        Button editWeight = new Button("Change Food Weight");
	        Button cancel = new Button("Cancel");
	        //sets position of buttons
	        editName.setLayoutX(180);
	        editName.setLayoutY(250);
	        editWeight.setLayoutX(180);
	        editWeight.setLayoutY(300);
	        cancel.setLayoutX(180);
	        cancel.setLayoutY(350);
	        editName.setPrefHeight(40);
	        editName.setPrefWidth(140);
	        editWeight.setPrefHeight(40);
	        editWeight.setPrefWidth(140);
	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);
	        //add to the pane
	        Pane root = new Pane();
	        root.getChildren().add(editName);
	        root.getChildren().add(editWeight);
	        root.getChildren().add(cancel);
	        root.getChildren().add(label);
	        //if you choose edit name
	        editName.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	editFoodName(primaryStage);
	            }
	        });
	        //if you chose edit weight
	        editWeight.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	editFoodWeight(primaryStage);
	            }
	        });
	        //if you chose cancel
	        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	            });
	        Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();
			new AnimationTimer() {
				@Override
				public void handle(long now) {
				}
			}.start();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}//ends method
	public static void editFoodWeight(Stage primaryStage) {
		try {
			//add label and titles
			primaryStage.setTitle("Change Food Weight Page");
			Label label = new Label("Dromedary Drones");
			Label name = new Label("Food Name");
			Label newWeight = new Label("New Food Weight (oz)");
			Pane root = new Pane();
			//set positions of labels
			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);
			name.setFont(new Font("Arial", 12));
			name.setLayoutX(180);
			name.setLayoutY(80);
			newWeight.setFont(new Font("Arial", 12));
			newWeight.setLayoutX(180);
			newWeight.setLayoutY(130);
			//add buttons
	        Button saveChanges = new Button("Save Changes");
	        Button cancel = new Button("Cancel");
	        //add text fields
	        TextField foodName = new TextField();
	        TextField newFoodWeight = new TextField();
	        //sets position of buttons
	        saveChanges.setLayoutX(180);
	        saveChanges.setLayoutY(300);
	        cancel.setLayoutX(180);
	        cancel.setLayoutY(350);
	        saveChanges.setPrefHeight(40);
	        saveChanges.setPrefWidth(140);
	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);
	        //set position of text box
	        foodName.setLayoutX(180);
	        foodName.setLayoutY(100);
	        newFoodWeight.setLayoutX(180);
	        newFoodWeight.setLayoutY(150);
	        //adds to the pane
	        root.getChildren().add(saveChanges);
	        root.getChildren().add(cancel);
	        root.getChildren().add(foodName);
	        root.getChildren().add(name);
	        root.getChildren().add(newFoodWeight);
	        root.getChildren().add(newWeight);
	        root.getChildren().add(label);
	        //if you hit the save button
	        saveChanges.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	String origName = foodName.getText();
	            	if(checkNames(foodName) && checkWeightIsNum(newFoodWeight) && checkWeight(newFoodWeight)) {
	            	double newW = Double.parseDouble(newFoodWeight.getText());
	            	for(int i = 0; i<foodList.getFoods().size(); i++) {
	            		if(foodList.getFoods().get(i).getName().equals(origName)) {
	            			foodList.getFoods().get(i).changeWeight(newW);
	            		}//ends if
	            	}//ends for loop
	                meals(primaryStage);
	            	}//ends if weight
	            }
	        });
	        //if you hit the cancel button
	        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                editFoodPage(primaryStage);
	            }
	            });
	        Scene scene = new Scene(root,500,500);
			primaryStage.setScene(scene);
			primaryStage.show();
			new AnimationTimer() {
				@Override
				public void handle(long now) {
				}
			}.start();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}//ends edit food weight
	public static boolean checkNames(TextField t) {
		String name = t.getText();
		for(int i = 0; i<foodList.getFoods().size(); i++) {
			if(foodList.getFoods().get(i).getName().equals(name)) {
				return true;
			}
		}
		failedAlert("That is not a current food");
		return false;

	}//ends check names method
	public static void editFoodName(Stage primaryStage) {
		try {
		primaryStage.setTitle("Change Food Name Page");
		Label label = new Label("Dromedary Drones");
		Label name = new Label("Current Food Name");
		Label newName = new Label("New Food Name");

		Label newWeight = new Label("New Food Weight");
		Label weight = new Label("Current Food Weight");

		label.setFont(new Font("Arial", 40));
		label.setLayoutX(80);

		name.setFont(new Font("Arial", 12));
		name.setLayoutX(180);
		name.setLayoutY(80);

		newName.setFont(new Font("Arial", 12));
		newName.setLayoutX(180);
		newName.setLayoutY(130);

		weight.setFont(new Font("Arial", 12));
		weight.setLayoutX(180);
		weight.setLayoutY(180);

		newWeight.setFont(new Font("Arial", 12));
		newWeight.setLayoutX(180);
		newWeight.setLayoutY(230);

        Button saveChanges = new Button("Save Changes");
        Button cancel = new Button("Cancel");

        TextField foodName = new TextField();

        TextField newFoodName = new TextField();

        TextField newFoodWeight = new TextField();


        Pane root = new Pane();

        saveChanges.setLayoutX(180);
        saveChanges.setLayoutY(300);
        cancel.setLayoutX(180);
        cancel.setLayoutY(350);

        saveChanges.setPrefHeight(40);
        saveChanges.setPrefWidth(140);
        cancel.setPrefHeight(40);
        cancel.setPrefWidth(140);

        //food name text box is first
        foodName.setLayoutX(180);
        foodName.setLayoutY(100);



        //new food name goes second
        newFoodName.setLayoutX(180);
        newFoodName.setLayoutY(150);



        //last is new food weight
        newFoodWeight.setLayoutX(180);
        newFoodWeight.setLayoutY(250);

        root.getChildren().add(saveChanges);
        root.getChildren().add(cancel);
        root.getChildren().add(foodName);

//        root.getChildren().add(newName);
//        root.getChildren().add(newFoodName);

        root.getChildren().add(label);
        root.getChildren().add(name);

        saveChanges.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(checkNames(foodName)) {
            	String origName = foodName.getText();
            	String newN = newFoodName.getText();
            	for(int j = 0; j< foodList.getFoods().size(); j++) {
            		if(foodList.getFoods().get(j).getName().equals(origName)) {
            			foodList.getFoods().get(j).changeName(newN);
            		}//ends if
            	}//ends for
                meals(primaryStage);
            }//ends if
            }
        });

        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                editFoodPage(primaryStage);
            }
            });
        Scene scene = new Scene(root,500,500);

		primaryStage.setScene(scene);
		primaryStage.show();


		new AnimationTimer() {
			@Override
			public void handle(long now) {


			}
		}.start();
	} catch(Exception e) {
		e.printStackTrace();
	}

	}//ends edit food page

	public static void removeFoodPage(Stage primaryStage) {
		try {
			primaryStage.setTitle("Remove Food Page");

			//add the labels

			Label label = new Label("Dromedary Drones");
			Label name = new Label("Food Name");

			//add position for labels


			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

			name.setFont(new Font("Arial", 12));
			name.setLayoutX(180);
			name.setLayoutY(180);
			//add the buttons
	        Button remove = new Button("Remove");
	        Button cancel = new Button("Cancel");

	        //add the text fields

	        TextField foodName = new TextField();

	        //set position of text fields and buttons

	        Pane root = new Pane();

	        remove.setLayoutX(180);
	        remove.setLayoutY(300);
	        cancel.setLayoutX(180);
	        cancel.setLayoutY(350);

	        remove.setPrefHeight(40);
	        remove.setPrefWidth(140);
	        cancel.setPrefHeight(40);

	        cancel.setPrefWidth(140);

	        cancel.setPrefWidth(140);

	        foodName.setLayoutX(180);
	        foodName.setLayoutY(200);



	        root.getChildren().add(remove);
	        root.getChildren().add(cancel);
	        root.getChildren().add(foodName);
	        root.getChildren().add(label);
	        root.getChildren().add(name);


	        remove.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	if(checkNames(foodName)) {
	            	String removeName = foodName.getText();
	            	//need to remove the food from the array
	            	for(int i = 0; i<foodList.getFoods().size(); i++) {
	            		if(foodList.getFoods().get(i).getName().equals(removeName)) {
	            			foodList.getFoods().remove(foodList.getFoods().get(i));
	            		}
	            	}
	            	//goes back to the meals page
	                meals(primaryStage);
	            }
	            }//ends if names
	        });

	        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	        });

			Scene scene = new Scene(root,500,500);

			primaryStage.setScene(scene);
			primaryStage.show();

			new AnimationTimer() {
				@Override
				public void handle(long now) {

				}
			}.start();
		} catch(Exception e) {
			e.printStackTrace();
		}

	}//ends remove food page

	public static void map(Stage primaryStage) {
		try {
		Image gcc = new Image(Main.class.getResourceAsStream("campusMapGCC.PNG"), 1000.0,1000.0, true, true);
		ImageView selectedImage = new ImageView();
		selectedImage.setImage(gcc);
		Pane root = new Pane();
		root.getChildren().addAll(selectedImage);

		Scene scene = new Scene(root,500,500);
		primaryStage.setScene(scene);
		primaryStage.show();
		new AnimationTimer() {
			@Override
			public void handle(long now) {
			}
		}.start();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}

}
