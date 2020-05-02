import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Scanner;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
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
import javafx.stage.StageStyle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.image.*;
import javafx.scene.input.MouseEvent;


public class Main extends Application {
	//static Foods foodList = new Foods();
	private static Meals mealList = new Meals();
	private static Orders orderList = new Orders(38, 45, 60, 30);
	private static Foods foodList = new Foods();
	private static ArrayList<ArrayList<Double>> locationList = new ArrayList<ArrayList<Double>>();
	private static ArrayList<ArrayList<Double>> locationToSend = new ArrayList<ArrayList<Double>>();
	private static Background bg = new Background(
			new BackgroundFill(Color.web("#fffcf0"), null, null));

	private static Food f1 = new Food("1/4 lb Hamburger", 6);
	private static Food f2 = new Food("Fries", 4);
	private static Food f3 = new Food("12 oz Drink", 14);

	private static Image image = new Image(Main.class.getResourceAsStream("mapGroveCity.jpg"));
	private static double imageWidth = 4224;
	private static double imageHeight = 4224;
	private static double feetPerPixelWidth = imageWidth/500;
	private static double feetPerPixelHeight = imageHeight/500;
	private static double x0 = 420 * feetPerPixelWidth;
	private static double y0 = 310 * feetPerPixelHeight;


	public static void main(String[] args) {
		
		
		System.out.println(image.getWidth());
		System.out.println(image.getHeight());
		System.out.println(feetPerPixelWidth);
		System.out.println(feetPerPixelHeight);
		System.out.println(x0);
		System.out.println(y0);
		
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

		setLocation(locationList, new File("defaultLocation.xml"));

		launch(args);
	}


	@Override
	public void start(Stage primaryStage) {

		try {
			
			//setting labels and buttons
			primaryStage.setTitle("Starting Screen");

			Label label = new Label("Dromedary Drones");
			dromedaryDronesTextStyle(label);

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

	        //Add the button styles
	        addButtonStyleNormal(button1);
	        addButtonStyleNormal(button2);

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
			//Color the scene background
			root.setBackground(bg);
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
			dromedaryDronesTextStyle(label);

	        Button button1 = new Button("Start Simulation");
	        Button button2 = new Button("Edit Meals");
	        Button button3 = new Button("Edit Probabilities");
	        Button button4 = new Button("Exit");
	        //adding map button
	        Button button5 = new Button("Map");


	        //Setting the layouts of the buttons
	        Pane root = new Pane();
	        button1.setLayoutX(180);
	        button1.setLayoutY(160);
	        button2.setLayoutX(180);
	        button2.setLayoutY(210);
	        button3.setLayoutX(180);
	        button3.setLayoutY(260);
	        button4.setLayoutX(180);
	        button4.setLayoutY(360);
	        //adding map button
	        button5.setLayoutX(180);
	        button5.setLayoutY(310);

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

	        //Add the button styles
	        addButtonStyleNormal(button1);
	        addButtonStyleNormal(button2);
	        addButtonStyleNormal(button3);
	        addButtonStyleNormal(button4);
	        addButtonStyleNormal(button5);

	        //adding buttons and labels to root
	        root.getChildren().add(button1);
	        root.getChildren().add(button2);
	        root.getChildren().add(button3);
	        root.getChildren().add(button4);
	        root.getChildren().add(button5);
	        root.getChildren().add(label);

	        //Add styles to buttons and labels
	        addButtonStyleNormal(button1);

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
	               map(primaryStage);
	            }
	        });

	        //finalize and show page
			Scene scene = new Scene(root,500,500);
			//Color the scene background
			root.setBackground(bg);
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
			dromedaryDronesTextStyle(label);
			label.setLayoutX(250);

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
				fifoSeries.getData().add(new XYChart.Data<String, Integer>(categoryName, fifoTimeData.get(i)));
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
				knapsackSeries.getData().add(new XYChart.Data<String, Integer>(categoryName, knapsackTimeData.get(i)));
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

	        //Add the button styles
	        addButtonStyleNormal(button1);
	        addButtonStyleNormal(button2);
	        addButtonStyleNormal(button3);

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
			//Color the scene background
			root.setBackground(bg);
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
			dromedaryDronesTextStyle(label);

			//Add the meals chart Label
			Label mealLabel = new Label("Meals:");
			setMediumLabelStyle(mealLabel,250,69);

			//Add the meals chart Probability label
			Label mealProbLabel = new Label("(%):");
			setMediumLabelStyle(mealProbLabel,390,69);

			//Add a label that tells the user what the percentages total to
			Label totalLabel = new Label("Percentages Total To:");
		    totalLabel.setFont(new Font("Arial",15));
		    totalLabel.setLayoutX(25);
		    totalLabel.setLayoutY(230);

		    //Calculate the total percentage of meal probabilities
		    double total = 0;
		    for (int i = 0; i < mealList.getMeals().size(); i++) {
		    	total += mealList.getMeals().get(i).getPercentage()*10000/100;
		    }
		    //Add a label with the total percentage of meal probabilities
		    Label totalVal = new Label(total + "%");
		    totalVal.setFont(new Font("Arial Black",15));
		    totalVal.setLayoutX(160);
		    totalVal.setLayoutY(250);
            totalVal.setTextFill(Color.web("#1f942f"));

		    //Add a label for the Shift order values
		    Label mealsPerShift = new Label("Meals per shift:");
		    setMediumLabelStyle(mealsPerShift,30,69);

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
		    	foodProbs.add("" + mealList.getMeals().get(i).getPercentage()*10000/100);
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
		        	String results = testMealProbabilities(listProbs, totalVal, foodProbs);
		        	if (!results.equalsIgnoreCase("Success") && !results.equalsIgnoreCase("SuccessParsing")) {
		        		failedAlert(results);
		        	}
		        }
		    });

		  //Set the layout for the meal name display
	        listFood.setPrefWidth(140);
	        listFood.setPrefHeight(200);
	        listFood.setLayoutX(250);
	        listFood.setLayoutY(100);

	        //Set the layout for the meal probability display
	        listProbs.setLayoutX(390);
	        listProbs.setLayoutY(100);
	        listProbs.setPrefWidth(70);
	        listProbs.setPrefHeight(200);



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
		    labeledShifts.setLayoutY(100);

		    //Set the layout for the shift order values
		    shiftProbabilities.setLayoutX(150);
		    shiftProbabilities.setPrefHeight(100);
		    shiftProbabilities.setPrefWidth(60);
		    shiftProbabilities.setLayoutY(100);

		    //Add a listener to the shift order values that verifies valid entries
		    //   and updates the value if a the number entered was valid.
		    shiftProbabilities.getItems().addListener(new ListChangeListener<Object>() {
		        @Override
		        public void onChanged(ListChangeListener.Change change) {
		        	String results = testProbabilities(shiftProbabilities);
		        	if (!results.equalsIgnoreCase("Success")) {
		        		failedAlert(results);
		        	}
		        }
		    });
		    //Set the shift order values to be editable
		    shiftProbabilities.setEditable(true);
		    shiftProbabilities.setCellFactory(TextFieldListCell.forListView());

		    //Create a home button and position it
	        Button home = new Button("Home");
	        home.setLayoutX(180);
	        home.setLayoutY(320);
	        home.setPrefWidth(140);

	        //Add the button styles
	        addButtonStyleNormal(home);

	        //Add all the created elements to the screen
	        Pane root = new Pane();
	        root.getChildren().add(listProbs);
	        root.getChildren().add(listFood);
	        root.getChildren().add(home);
	        root.getChildren().add(label);
	        root.getChildren().addAll(totalVal,totalLabel);
	        root.getChildren().addAll(mealLabel,mealProbLabel,mealsPerShift);
	        root.getChildren().addAll(labeledShifts,shiftProbabilities);

	        //Set the home button to only work if there are no errors on the page
	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

	            @Override public void handle(ActionEvent e) {
	            	String resultProbs = testProbabilities(shiftProbabilities);
	            	String resultMealProbs = testMealProbabilities(listProbs, totalVal, foodProbs);
	            	if (resultProbs.equalsIgnoreCase("Success") && resultMealProbs.equalsIgnoreCase("Success")) {
		                mainPage(primaryStage);
	            	} else {
		            	failedAlert("All values must be valid before leaving.");
		            }
	            }
	        });

	        //Have the GUI page display
			Scene scene = new Scene(root,500,500);
			//Color the scene background
			root.setBackground(bg);
			primaryStage.setScene(scene);
			primaryStage.show();

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	public static String testMealProbabilities(ListView listProbs, Label totalVal, ObservableList<String> foodProbs) {
		double total = 0;
        for (int i = 0; i < listProbs.getItems().size(); i++) {
        	try {
        		Object entryValue = listProbs.getItems().get(i);
        		double val = Double.parseDouble((String)entryValue);
        		if (val < 0) {
        			return("Invalid Meal Percentage:\n\tA value less than 0 was entered");
        		} else if (val > 100) {
        			return("Invalid Meal Percentage: \n\t A value greater than 100 was entered.");
        		}
        		total += val;
        	} catch (Exception E) {
    			return("Invalid Meal Percentage: \n\t A non-numerical value was entered.");
        	}
        }
        for (int i = 0; i < mealList.getMeals().size(); i++) {
        	double percent = Double.parseDouble(foodProbs.get(i))/100;
        	mealList.getMeals().get(i).setPercentage(percent);
	    }
        total = (total * 1000)/1000;
        if (total == 100) {
            totalVal.setText("100.00%");
            totalVal.setTextFill(Color.web("#1f942f"));
            return "Success";
        } else {
        	totalVal.setText(String.format("%3.2f%%",total));
        	totalVal.setTextFill(Color.web("#9c291c"));
        	return "SuccessParsing";
        }
	}

	public static String testProbabilities(ListView shiftProbabilities) {
		for (int i = 0; i < shiftProbabilities.getItems().size(); i++) {
        	try {
        		Object entryValue = shiftProbabilities.getItems().get(i);
        		int val = Integer.parseInt((String)entryValue);
        		if (val < 0) {
        			return("Invalid Shift Entry: \t\nA value less than 0 was entered.");
        		} else {
        			ObservableList<String> data = shiftProbabilities.getItems();
        			orderList.setHourlyRate(Integer.parseInt(data.get(0)), Integer.parseInt(data.get(1)),
        					Integer.parseInt(data.get(2)), Integer.parseInt(data.get(3)));
        		}
        	} catch (Exception E) {
        		return("Invalid Shift Entry: \n\tA non-integer value was entered.");
        	}
        }
		return "Success";
	}

	public static void meals(Stage primaryStage) {

		try {
			//Set the title of the page
			primaryStage.setTitle("Meals Page");

			//declare labels and set initial values
			Label label = new Label("Dromedary Drones");
			dromedaryDronesTextStyle(label);
			Label mealsLabel = new Label("Meals");
			Label foods = new Label("Foods");
			Label errorLabel = new Label("");

			//set layout, font, and colors of labels
			setMediumLabelStyle(mealsLabel,352,72);

			setMediumLabelStyle(foods,44,72);

			errorLabel.setFont(new Font("Arial", 20));
			errorLabel.setLayoutX(160);
			errorLabel.setLayoutY(60);
			errorLabel.setTextFill(Color.web("#cc0000"));


			//Add buttons and their initial values
	        Button removeFood = new Button("Remove Food");
	        Button editFood = new Button("Edit Food");
	        Button addFood = new Button("+");
	        Button addMeal = new Button("+");
	        Button clickMeal = new Button("Click a Meal");
	        Button clickFood = new Button("Click a Food");
	        Button editMeal = new Button("Edit Meal");
	        Button removeMeal = new Button("Remove Meal");
	        Button home = new Button("Home");
	        Button saveMeals = new Button("Save Current Meals");
	        Button loadMeals = new Button("Load List of Meals");
	        Button saveFoods = new Button("Save Current Foods");
	        Button loadFoods = new Button("Load List of Foods");

	        //Set list views to display foods and meals
	        ListView<String> listFood = new ListView<String>();
	        ListView<String> listMeals = new ListView<String>();

	        //create observable lists
	        ObservableList<String> food =FXCollections.observableArrayList ();
		    ObservableList<String> meals =FXCollections.observableArrayList ();


		  //Adds foods to the listView from the foodList, displaying their name
	        for (int i = 0; i < foodList.getFoods().size(); i++) {
	        	food.add(foodList.getFoods().get(i).getName());
	        }
		        listFood.setItems(food);

		        listFood.setOnMouseClicked(new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event) {
		            	clickMeal.setVisible(false);
		            	clickFood.setVisible(false);
		                displayFoodOrMealButtons(removeFood, editFood, removeMeal, editMeal, true);
		            }
		        });


			//Adds meals to the listView from the mealList, displaying their name
		    ArrayList<Meal> mealArray = new ArrayList<Meal>();
		    mealArray = mealList.getMeals();
		    for (int i = 0; i < mealArray.size(); i++) {
		    	meals.add((mealArray.get(i).getName()));
		    }

			    listMeals.setItems(meals);
			    listMeals.setOnMouseClicked(new EventHandler<MouseEvent>() {
		            @Override
		            public void handle(MouseEvent event) {
		            	clickMeal.setVisible(false);
		            	clickFood.setVisible(false);
		            	displayFoodOrMealButtons(removeFood, editFood, removeMeal, editMeal, false);
		            }
		        });

			//Create the pane
	        Pane root = new Pane();

	        //Set layout of Buttons
	        addFood.setLayoutX(123);
	        addFood.setLayoutY(70);

	        addMeal.setLayoutX(430);
	        addMeal.setLayoutY(70);

	        home.setLayoutX(180);
	        home.setLayoutY(285);

	        clickMeal.setLayoutX(180);
	        clickMeal.setLayoutY(185);
	        clickFood.setLayoutX(180);
	        clickFood.setLayoutY(235);

	        //Set dimensions of buttons
	        removeFood.setPrefHeight(40);
	        removeFood.setPrefWidth(140);

	        editFood.setPrefHeight(40);
	        editFood.setPrefWidth(140);

	        addFood.setPrefHeight(20);
	        addFood.setPrefWidth(25);

	        addMeal.setPrefHeight(20);
	        addMeal.setPrefWidth(25);

	        editMeal.setPrefHeight(40);
	        editMeal.setPrefWidth(140);

	        removeMeal.setPrefHeight(40);
	        removeMeal.setPrefWidth(140);

	        home.setPrefHeight(40);
	        home.setPrefWidth(140);
	        clickMeal.setPrefSize(140,40);
	        clickFood.setPrefSize(140, 40);

	        saveMeals.setPrefHeight(40);
	        saveMeals.setPrefWidth(140);
	        loadMeals.setPrefHeight(40);
	        loadMeals.setPrefWidth(140);

	        saveMeals.setLayoutX(330);
	        saveMeals.setLayoutY(340);
	        loadMeals.setLayoutX(330);
	        loadMeals.setLayoutY(390);

	        saveFoods.setPrefHeight(40);
	        saveFoods.setPrefWidth(140);
	        loadFoods.setPrefHeight(40);
	        loadFoods.setPrefWidth(140);


	        saveFoods.setLayoutX(30);
	        saveFoods.setLayoutY(340);

	        loadFoods.setLayoutX(30);
	        loadFoods.setLayoutY(390);


	        //Set dimensions and layout of food and Meal lists
	        listFood.setPrefWidth(140);
	        listFood.setPrefHeight(230);

	        listMeals.setPrefWidth(140);
	        listMeals.setPrefHeight(230);

	        listFood.setLayoutX(30);
	        listFood.setLayoutY(100);
	        listMeals.setLayoutX(330);
	        listMeals.setLayoutY(100);


	      //Add the button styles
	        addButtonStyleNormal(removeFood);
	        addButtonStyleNormal(editFood);
	        addButtonStyleSmall(addFood,4,4);
	        addButtonStyleSmall(addMeal,4,4);
	        addButtonStyleNormal(editMeal);
	        addButtonStyleNormal(removeMeal);
	        addButtonStyleNormal(home);
	        addButtonStyleNormal(saveFoods);
	        addButtonStyleNormal(loadFoods);
	        addButtonStyleNormal(saveMeals);
	        addButtonStyleNormal(loadMeals);
	        addButtonStyleDisabled(clickMeal);
	        addButtonStyleDisabled(clickFood);


	        //Set edit buttons to hide on first load
	        removeFood.setVisible(false);
	        editFood.setVisible(false);
	        editMeal.setVisible(false);
	        removeMeal.setVisible(false);
//	        clickMeal.setDisable(true);
//	        clickFood.setDisable(true);

	        //Add Every javaFX element to the pane so it will be displayed
	        root.getChildren().add(removeFood);
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
	        root.getChildren().add(saveMeals);
	        root.getChildren().add(loadMeals);
	        root.getChildren().add(saveFoods);
	        root.getChildren().add(loadFoods);
	        root.getChildren().add(clickMeal);
	        root.getChildren().add(clickFood);


	        loadMeals.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	loadMealsPage(primaryStage);

	            }//ends event handler
	        });

	        saveMeals.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	saveMeals(primaryStage);

	            }//ends event handler
	        });

	        saveFoods.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	saveFoods(primaryStage);

	            }//ends event handler
	        });

	        loadFoods.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                loadFoodsPage(primaryStage);
	            }
	        });

	        //if the user selects add food button goes to add food page


	        //Give addFood button functionality

	        addFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                addFoodPage(primaryStage);
	            }
	        });


	      //if the user selects remove food button goes to remove food page

	      //Give removeFood button functionality

	        removeFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	try {
	            		foodList.deleteFoodItem(foodList.getFoods().get(listFood.getSelectionModel().getSelectedIndex()));
		                meals(primaryStage);
	            	}
	            	catch (Exception f) {
	            		errorLabel.setText("Please select the food item to delete");
	            	}
	            }
	        });


	      //if the user selects edit food button goes to the edit food page

	      //Give editFood button functionality

	        editFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	try {
	        			editFoodPageNew(primaryStage, foodList.getFoods().get(listFood.getSelectionModel().getSelectedIndex()));
	        		}
	        		catch (Exception f){
	        			errorLabel.setText("Please select a food");
	        		}
	            }
	        });

	        //Give addMeal button functionality
	        addMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                addMealPage(primaryStage);
	            }
	        });

	        //Give editMeal button functionality and basic error checking
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

	        //Give removeMeal button functionality and basic error checking
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

	        //Give home button functionality
	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	mainPage(primaryStage);
	            }
	        });

//	        //Give removeFood button functionality
//			removeFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
//				@Override public void handle(ActionEvent e) {
//					removeFoodPage(primaryStage);
//				}
//			});

			//Create the scene
			Scene scene = new Scene(root,500,500);
			//Color the scene background
			root.setBackground(bg);

			//Have the GUI page display
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
	}	//End meals function

	public static void editFoodPageNew(Stage primaryStage, Food foodToBeEdited) {
		try {
		//title of the page
		primaryStage.setTitle("Edit Food Page");

		//create labels
		Label label = new Label("Dromedary Drones");
		dromedaryDronesTextStyle(label);

        //create name
        Label name = new Label("Food Name");
		name.setFont(new Font("Arial", 12));
		name.setLayoutX(180);
		name.setLayoutY(80);
        TextField newFoodName = new TextField();
        newFoodName.setText(foodToBeEdited.getName());
        newFoodName.setLayoutX(180);
        newFoodName.setLayoutY(100);

        //create weight
      	Label weight = new Label("Food Weight");
      	weight.setFont(new Font("Arial", 12));
      	weight.setLayoutX(180);
      	weight.setLayoutY(130);
      	TextField newFoodWeight = new TextField();
      	newFoodWeight.setText(Double.toString(foodToBeEdited.getWeight()));
      	newFoodWeight.setLayoutX(180);
      	newFoodWeight.setLayoutY(150);


		//create buttons
        Button saveChanges = new Button("Save Changes");
        Button cancel = new Button("Cancel");

        //set position of buttons
        saveChanges.setLayoutX(180);
        saveChanges.setLayoutY(300);
        saveChanges.setPrefHeight(40);
        saveChanges.setPrefWidth(140);
        cancel.setLayoutX(180);
        cancel.setLayoutY(350);
        cancel.setPrefHeight(40);
        cancel.setPrefWidth(140);


	    //Add the button styles
        addButtonStyleNormal(cancel);
        addButtonStyleNormal(saveChanges);

        //adds everything to the pane
        Pane root = new Pane();
        //adds the labels
        root.getChildren().add(label);
        root.getChildren().add(name);
        root.getChildren().add(weight);
        //adds the text fields
        root.getChildren().add(newFoodName);
        root.getChildren().add(newFoodWeight);
        //add buttons
        root.getChildren().add(saveChanges);
        root.getChildren().add(cancel);

        //if the user presses the save button
        saveChanges.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

            	//gets the info from text fields
            	if(!checkIfEmpty(newFoodWeight) && !checkIfEmpty(newFoodName)
            	   && checkWeightIsNum(newFoodWeight) && checkWeight(newFoodWeight)) {
            	String newN = newFoodName.getText();
            	double newW = Double.parseDouble(newFoodWeight.getText());
            	foodToBeEdited.changeName(newN);
            	foodToBeEdited.changeWeight(newW);
                meals(primaryStage);
            	}

            }
        });

        //if the user presses the cancel button goes back to the edit food page
        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                meals(primaryStage);
            }
            });
        //creates a new scene
        Scene scene = new Scene(root,500,500);
      //Color the scene background
		root.setBackground(bg);
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

	public static void saveMeals(Stage primaryStage) {
	   	 FileChooser fileChooser = new FileChooser();

	     //Set extension filter
	     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	     fileChooser.getExtensionFilters().add(extFilter);

	     //Show save file dialog
	     File file = fileChooser.showSaveDialog(primaryStage);


	     fileChooser.getExtensionFilters().add(extFilter);

	       if (file != null) {
	       	 try {
		        	String content = "";
		        	for(int index = 0; index < mealList.getMeals().size(); index++ ) {
		        		content = content + mealList.getMeals().get(index).getName() + ","
		        				+ mealList.getMeals().get(index).getPercentage() + ",";
		        		for (int i = 0; i < mealList.getMeals().get(index).getFoods().size(); i++) {
		        			if (i != 0) {
		        				content = content + ",";
		        			}
		        			content = content + mealList.getMeals().get(index).getFoods().get(i).getName();
		        		}
		        		content = content + "\n";
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

		}


	public static void loadMealsPage(Stage primaryStage) {
		 FileChooser fileChooser = new FileChooser();
	     //Set extension filter
	     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	     fileChooser.getExtensionFilters().add(extFilter);
	     //Show save file dialog
	     File file = fileChooser.showOpenDialog(primaryStage);
	     fileChooser.getExtensionFilters().add(extFilter);

	     //as long as the file is not empty
	       if (file != null) {
	       	 try {
		        	Scanner sc = new Scanner(file);
		        	boolean isFormattingGood = true;
		        	while(sc.hasNext()) {
		        		//reads in the line
		        		String s = sc.nextLine();
		        		System.out.println(checkMeal(s));
		        		if (checkMeal(s) == false) {
		        			System.out.println("Invalid formatting?");
		        			isFormattingGood = false;
		        			break;
		        		}

		        	}//ends while

		        	if (isFormattingGood == true) {
			        	//now have to make the new meals, and replace the old ones with them.
			        	while (mealList.getMeals().size() != 0) {
			        		mealList.deleteMeal(mealList.getMeals().get(0));
			        	}


			        	sc = new Scanner(file);
			        	while (sc.hasNext()) {
				        	String s = sc.nextLine();

				        	Scanner stringScanner = new Scanner(s);
				        	//if there is a comma or a new line
							stringScanner.useDelimiter(",|\\n");

							//get meal name
							String mealName = stringScanner.next();
							//get meal percentage then turn it into a double
							String percentage = stringScanner.next();
							double percentageDouble = Double.parseDouble(percentage);


							ArrayList<Food> mealFoods = new ArrayList<Food>();
							while (stringScanner.hasNext()) {
								String foodName = stringScanner.next();
								System.out.println(foodName);
								for (int i = 0; i < foodList.getFoods().size(); i++) {
									if (foodName.equals(foodList.getFoods().get(i).getName())) {
										mealFoods.add(foodList.getFoods().get(i));
										break;
									}
								}
							}
							Meal newMeal = new Meal(mealName, percentageDouble, mealFoods);
							mealList.addMeal(newMeal);
							System.out.println(mealList.getMeals().size());
							//sc.next();
			        	}
		        	}

		        	//System.out.println(mealList.getMeals().get(0).getFoods().get(0));
		        	//refreshes the page
		        	meals(primaryStage);


		        } catch (IOException ex) {
		           System.out.println("File Logging Error");

		        }
	       }


	}//ends loadmealspage

	public static boolean checkMeal(String meal) {

		try {
			boolean hasFood = false;
			Scanner stringScanner = new Scanner(meal);
			//if there is a comma or a new line
			stringScanner.useDelimiter(",|\\n");

			String mealName = stringScanner.next();
			if (mealName.equals("")) {
				return false;
			}

			String percentage = stringScanner.next();
			double percentageDouble = Double.parseDouble(percentage);
			if (percentageDouble < 0) {
				return false;
			}

			while (stringScanner.hasNext()) {
				hasFood = false;
				//check to see if the food exists
				String foodName = stringScanner.next();
				for (int i = 0; i < foodList.getFoods().size(); i++) {
					if (foodName.equals(foodList.getFoods().get(i).getName())) {
						hasFood = true;
					}
				}
				if (hasFood = false) {
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Invalid formatting");
			return false;
		}

		return true;
	}


	public static void loadFoodsPage(Stage primaryStage) {
		 FileChooser fileChooser = new FileChooser();
	     //Set extension filter
	     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	     fileChooser.getExtensionFilters().add(extFilter);
	     //Show save file dialog
	     File file = fileChooser.showOpenDialog(primaryStage);
	     fileChooser.getExtensionFilters().add(extFilter);

	     //as long as the file is not empty
	       if (file != null) {
	       	 try {
		        	Scanner sc = new Scanner(file);
		        	while(sc.hasNext()) {
		        		//reads in the line
		        		String s = sc.nextLine();


		        		//new scanner for the line
		        		Scanner stringScanner = new Scanner(s);
		        		//if there is a comma or a new line
		        		stringScanner.useDelimiter(",|\\n");
		        		//gets the name and weight from string
		        		String name = stringScanner.next();
		        		String weight = stringScanner.next();
		        		double weightDouble = Double.parseDouble(weight);


		        		//checks if the food already exists
		        		boolean duplicate = false;
		        		for(int i = 0; i<foodList.size(); i++) {
		        			if(foodList.getFoods().get(i).getName().equals(name)) {
		        				duplicate = true;
		        			}//ends if
		        		}//ends for


		        		//as long as it isnt a duplicate, adds the food
		        		if(duplicate == false) {
		        		//creates the new food
		        		Food f = new Food(name, weightDouble);
		        		//adds the new food to the list
		        		foodList.addFoodItem(f);
		        		}//ends if
		        	}//ends while


		        	//refreshes the page
		        	meals(primaryStage);


		        } catch (IOException ex) {
		           System.out.println("File Logging Error");

		        }
	       }


	}//ends loadfoodspage

	public static void saveFoods(Stage primaryStage) {
   	 FileChooser fileChooser = new FileChooser();

     //Set extension filter
     FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
     fileChooser.getExtensionFilters().add(extFilter);

     //Show save file dialog
     File file = fileChooser.showSaveDialog(primaryStage);


     fileChooser.getExtensionFilters().add(extFilter);

       if (file != null) {
       	 try {
	        	String content = "";
	        	for(int index = 0; index<foodList.size(); index++ ) {
	        		content = content + foodList.getFoods().get(index).getName() + ", "
	        				+ foodList.getFoods().get(index).getWeight() + "\n";
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

	}//ends save foods page

	public static void addFoodPage(Stage primaryStage) {
		try {
			//creates title of page
			primaryStage.setTitle("Add Food Page");

			//add the labels
			Label label = new Label("Dromedary Drones");
			dromedaryDronesTextStyle(label);

			Label name = new Label("Food Name");
			name.setFont(new Font("Arial", 12));
			name.setLayoutX(180);
			name.setLayoutY(80);
	        TextField foodName = new TextField();
	        foodName.setLayoutX(180);
	        foodName.setLayoutY(100);

			Label weight = new Label("Food Weight (oz)");
			weight.setFont(new Font("Arial", 12));
			weight.setLayoutX(180);
			weight.setLayoutY(130);
	        TextField foodWeight = new TextField();
	        foodWeight.setLayoutX(180);
	        foodWeight.setLayoutY(150);

			//create buttons
	        Button saveFood = new Button("Save Food");
	        Button cancel = new Button("Cancel");

	        //position of buttons
	        saveFood.setLayoutX(180);
	        saveFood.setLayoutY(300);
	        cancel.setLayoutX(180);
	        cancel.setLayoutY(350);
	        saveFood.setPrefHeight(40);
	        saveFood.setPrefWidth(140);
	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);

	      //Add the button styles
	        addButtonStyleNormal(saveFood);
	        addButtonStyleNormal(cancel);

	        //add to the root
	        Pane root = new Pane();
	        root.getChildren().add(saveFood);
	        root.getChildren().add(cancel);
	        root.getChildren().add(foodName);
	        root.getChildren().add(foodWeight);
	        root.getChildren().add(label);
	        root.getChildren().add(name);
	        root.getChildren().add(weight);

	        //if you hit the save button
	        saveFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	//checks if the text field is empty, if the user is trying to add a food already listed, if the weight is a num, and that the weight is in bounds
	            	if(!checkIfEmpty(foodName)&& !checkIfEmpty(foodWeight) && !checkDuplicateName(foodName) && checkWeightIsNum(foodWeight) && checkWeight(foodWeight)) {
	            	//gets info from text fields
	            	String addName = foodName.getText();
	            	double w = Double.parseDouble(foodWeight.getText());
	      	        //creates the new food and adds to list
	      	        Food f = new Food(addName, w);
	      	        foodList.addFoodItem(f);
	                meals(primaryStage);
	            }//ends if
	            }
	        });

	        //if you hit the cancel button goes back to meals page
	        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	        });

	        //creates a new scene
			Scene scene = new Scene(root,500,500);
			//Color the scene background
			root.setBackground(bg);
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
		//gets info from the field
		String tryName = t.getText();
		//iterates list to see if the food already exists and sends failed notification
		for(int i = 0; i<foodList.getFoods().size(); i++) {
			if(foodList.getFoods().get(i).getName().equals(tryName)) {
				failedAlert("This food is already listed");
				return true;
			}//ends if
		}//ends for
		return false;
	}//ends checkDuplicateName

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
		//gets info from text
		String s = t.getText();
		for(int i = 0; i<s.length(); i++) {
			//gets characters and checks if character is number and sends failed alert
			char c = s.charAt(i);
			if(Character.isAlphabetic(c)) {
				failedAlert("Weight must be only numbers");
				return false;
			}//ends if
		}//ends for loop
		return true;
	}//ends check weight is num

	public static void failedAlert(String s) {
		Alert fail = new Alert(AlertType.INFORMATION);
		fail.setHeaderText("ERROR");
		fail.setContentText(s);
		fail.showAndWait();
	}//ends alert message method

	public static boolean checkIfEmpty(TextField t) {
		if(t.getText().trim().isEmpty()) {
			failedAlert("One or more fields is empty");
			return true;
		}else {
			return false;
		}//ends else
	}//ends check if empty method



	public static void addMealPage(Stage primaryStage) {
		try {
			//Set the title of the page
			primaryStage.setTitle("Add Meal Page");

			//Create labels and set their initial values
			Label label = new Label("Dromedary Drones");
			dromedaryDronesTextStyle(label);

			Label mealNameHere = new Label("Meal Name");
			Label errorLabel = new Label("");

			//Set label fonts, layouts and colors
			mealNameHere.setFont(new Font("Arial", 12));
			mealNameHere.setLayoutX(220);
			mealNameHere.setLayoutY(80);

			errorLabel.setFont(new Font("Arial", 12));
			errorLabel.setLayoutX(220);
			errorLabel.setLayoutY(50);
			errorLabel.setTextFill(Color.web("#cc0000"));

			//Creates buttons and sets their labels
	        Button saveMeal = new Button("Save Meal");
	        Button cancel = new Button("Cancel");

	        //Creates text fields for each food item and weight
	        TextField mealName = new TextField();
	        TextField mealWeight1 = new TextField();
	        TextField mealWeight2 = new TextField();
	        TextField mealWeight3 = new TextField();
	        TextField mealWeight4 = new TextField();

	        //Sets prompts for text fields
	        mealName.setPromptText("Enter meal name.");
	        mealWeight1.setPromptText("0.0");
	        mealWeight2.setPromptText("0.0");
	        mealWeight3.setPromptText("0.0");
	        mealWeight4.setPromptText("0.0");


	        //Create observableList to hold foods
		    ObservableList<String> foods = FXCollections.observableArrayList(
		    		"None"
		    		);


		    //Fill the observableList foods with food items from foodList
			    for (int i = 0; i < foodList.getFoods().size(); i++) {
			    	foods.add((foodList.getFoods().get(i).getName()));
			    }


			//Create comboBoxes to hold foods options
	        final ComboBox<String> option1 = new ComboBox<String>(foods);
	        final ComboBox<String> option2 = new ComboBox<String>(foods);
	        final ComboBox<String> option3 = new ComboBox<String>(foods);
	        final ComboBox<String> option4 = new ComboBox<String>(foods);

	        //set combo box to display its first value
	        option1.setValue(foods.get(0));
	        option2.setValue(foods.get(0));
	        option3.setValue(foods.get(0));
	        option4.setValue(foods.get(0));

	        //Create new pane
	        Pane root = new Pane();

	        //Set layouts of buttons
	        saveMeal.setLayoutX(180);
	        saveMeal.setLayoutY(400);

	        cancel.setLayoutX(180);
	        cancel.setLayoutY(450);

	        //Set dimensions for buttons
	        saveMeal.setPrefHeight(40);
	        saveMeal.setPrefWidth(140);

	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);

	        //set layout of TextFields
	        mealName.setLayoutX(180);
	        mealName.setLayoutY(100);

	        mealWeight1.setLayoutX(330);
	        mealWeight1.setLayoutY(150);

	        mealWeight2.setLayoutX(330);
	        mealWeight2.setLayoutY(200);

	        mealWeight3.setLayoutX(330);
	        mealWeight3.setLayoutY(250);

	        mealWeight4.setLayoutX(330);
	        mealWeight4.setLayoutY(300);

	        //Set dimensions of TextFields
	        mealWeight1.setPrefWidth(40);
	        mealWeight2.setPrefWidth(40);
	        mealWeight3.setPrefWidth(40);
	        mealWeight4.setPrefWidth(40);

	        //Set layout for ComboBoxes
	        option1.setLayoutX(180);
	        option1.setLayoutY(150);

	        option2.setLayoutX(180);
	        option2.setLayoutY(200);

	        option3.setLayoutX(180);
	        option3.setLayoutY(250);

	        option4.setLayoutX(180);
	        option4.setLayoutY(300);

	      //Add the button styles
	        addButtonStyleNormal(saveMeal);
	        addButtonStyleNormal(cancel);

	        //Add javaFX elements to the pane so they are displayed
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

	        //Create pattern to only allow numeric characters and periods ( . ) in a text fields
	        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");

	        //Set formatters to that weights can only recieve numeric values and periods ( . )
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

	        //Apply formatters to the textFields for weights
	        mealWeight1.setTextFormatter(formatter1);
	        mealWeight2.setTextFormatter(formatter2);
	        mealWeight3.setTextFormatter(formatter3);
	        mealWeight4.setTextFormatter(formatter4);


	        //Adds functionality and error checking to saveMeal button
	        saveMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {

	            	ArrayList<Food> newMeal = new ArrayList<Food>();	//list of foods to add to meal
	            	String newMealName;									//Name for new meal
	            	boolean hasValidFood = false;						//If a valid food is selected
	            	boolean hasValidWeight = true;						//If a valid weight is entered

	            	//If meal does not have a name, display invalidName
	            	if (mealName.getText().equals("")) {
	            		errorLabel.setLayoutX(220);
	            		errorLabel.setText("Invalid Name");
	            	}

	            	//If meal does have a name, check to see if there are any foods selected
	            	else if (!option1.getValue().toString().equals("None")
	            			|| !option2.getValue().toString().equals("None")
	            			|| !option3.getValue().toString().equals("None")
	            			|| !option4.getValue().toString().equals("None")) {
	            		hasValidFood = true;

	            		//If it has a valid food, check the weights now

	            		//If option1 has an option other than "None" selected, check the weight
	            		if (!option1.getValue().toString().equals("None")) {

	            			//If the food does not have a valid weight, display an error label, set hasValidWeight to false
	            			if (mealWeight1.getText().toString().equals("")) {
	            				errorLabel.setLayoutX(150);
	            				errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}

	            			//if the food has a valid weight, add the food to the newMeal list
	            			else {
	    		            	String food1Name = option1.getValue();
	    		            	double food1Weight = Double.parseDouble(mealWeight1.getText());
	    		            	Food newFood = new Food(food1Name, food1Weight);
	    		            	newMeal.add(newFood);
	            			}
	            		}

	            		//If option2 has an option other than "None" selected, check the weight
	            		if (!option2.getValue().toString().equals("None")) {

	            			//If the food does not have a valid weight, display an error label, set hasValidWeight to false
	            			if (mealWeight2.getText().toString().equals("")) {
	            				errorLabel.setLayoutX(150);
	            				errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}

	            			//if the food has a valid weight, add the food to the newMeal list
	            			else {
	    		            	String food2Name = option2.getValue();
	    		            	double food2Weight = Double.parseDouble(mealWeight2.getText());
	    		            	Food newFood = new Food(food2Name, food2Weight);
	    		            	newMeal.add(newFood);
	            			}
	            		}

	            		//If option3 has an option other than "None" selected, check the weight
            			if (!option3.getValue().toString().equals("None")) {

            				//If the food does not have a valid weight, display an error label, set hasValidWeight to false
	            			if (mealWeight3.getText().toString().equals("")) {
	            				errorLabel.setLayoutX(150);
	            				errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}

	            			//if the food has a valid weight, add the food to the newMeal list
	            			else {
	    		            	String food3Name = option3.getValue();
	    		            	double food3Weight = Double.parseDouble(mealWeight3.getText());
	    		            	Food newFood = new Food(food3Name, food3Weight);
	    		            	newMeal.add(newFood);
	            			}
            			}

            			//If option4 has an option other than "None" selected, check the weight
            			if (!option4.getValue().toString().equals("None")) {

            				//If the food does not have a valid weight, display an error label, set hasValidWeight to false
	            			if (mealWeight4.getText().toString().equals("")) {
	            				errorLabel.setLayoutX(150);
	            				errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}

	            			//if the food has a valid weight, add the food to the newMeal list
	            			else {
	    		            	String food4Name = option4.getValue();
	    		            	double food4Weight = Double.parseDouble(mealWeight4.getText());
	    		            	Food newFood = new Food(food4Name, food4Weight);
	    		            	newMeal.add(newFood);
	            			}
            			}

            			//If hasValidFood and hasValid weight are true, create a new meal using newMealName and newMeal
            			if (hasValidFood && hasValidWeight) {
    		            	newMealName = mealName.getText().toString();
    		            	Meal testMeal = new Meal(newMealName, 0, newMeal);
    		            	mealList.addMeal(testMeal);
    		                meals(primaryStage);
            			}
	            	}

	            	//If no foods are selected, display an error label
	            	else {
	            		errorLabel.setLayoutX(150);
	            		errorLabel.setText("Please select a food to add to the meal");
	            	}
	            }
	        });


	        //Adds functionality to the cancel button
	        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	        });


	        //Have the GUI page display
			Scene scene = new Scene(root,500,500);
			//Color the scene background
			root.setBackground(bg);
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
	}	//end EditMealsPage function

	public static void editMealPage(Stage primaryStage, Meal mealToBeEdited) {
		try {
			//Create the pane
			Pane root = new Pane();

			//Set the title of the page
			primaryStage.setTitle("Edit Meal Page");

			//Create labels and set their initial values
			Label label = new Label("Dromedary Drones");
			dromedaryDronesTextStyle(label);

			Label mealNameHere = new Label("Meal Name");
			Label errorLabel = new Label("");

			//Set layout, font, and color of labels
			mealNameHere.setFont(new Font("Arial", 12));
			mealNameHere.setLayoutX(220);
			mealNameHere.setLayoutY(80);

			errorLabel.setFont(new Font("Arial", 12));
			errorLabel.setLayoutX(220);
			errorLabel.setLayoutY(50);
			errorLabel.setTextFill(Color.web("#cc0000"));

			//Create buttons and set their text
	        Button saveMeal = new Button("Save Meal");
	        Button cancel = new Button("Cancel");

	        //Create textField for the name of the meal
	        TextField mealName = new TextField();
	        //Set text to name of the meal to be edited
	        mealName.setText(mealToBeEdited.getName());
	        //Set layout of textField
	        mealName.setLayoutX(180);
	        mealName.setLayoutY(100);

	        //Create a list to hold the foods of the meal to be edited
			ArrayList<Food>mealFoods = new ArrayList<Food>();
			//Fill the list with the foods of the meal to be edited
			mealFoods = mealToBeEdited.getFoods();

			//Set layout of buttons
	        saveMeal.setLayoutX(180);
	        saveMeal.setLayoutY(400);

	        cancel.setLayoutX(180);
	        cancel.setLayoutY(450);

	        //Set button dimensions
	        saveMeal.setPrefHeight(40);
	        saveMeal.setPrefWidth(140);

	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);

			//Create listView of foods
			ListView<String> listFoods = new ListView<String>();


			//Create an observableList to hold the names of foods
		    ObservableList<String> foods = FXCollections.observableArrayList(
		    		"None"
		    		);

		       //Fills the observableList with the names of the foods in the meal to be edited
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

	      //Add the button styles
	        addButtonStyleNormal(saveMeal);
	        addButtonStyleNormal(cancel);

	        //Adds javaFX button to pane so they are displayed
	        root.getChildren().add(saveMeal);
	        root.getChildren().add(cancel);
	        root.getChildren().add(mealName);
	        root.getChildren().add(label);
	        root.getChildren().add(mealNameHere);
	        root.getChildren().add(errorLabel);

	        //Create pattern that only allows numerical characters and periods ( . )
	        Pattern pattern = Pattern.compile("\\d*|\\d+\\.\\d*");

	        //Set formatters to that weights can only recieve numeric values and periods ( . )
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

	        //Appy formatters to textFields for weights
	        foodWeights[0].setTextFormatter(formatter1);
	        foodWeights[1].setTextFormatter(formatter2);
	        foodWeights[2].setTextFormatter(formatter3);
	        foodWeights[3].setTextFormatter(formatter4);


	        //Add functionality and error checking to saveMeal button
	        saveMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {

	            	ArrayList<Food> newMeal = new ArrayList<Food>();	//list of foods to add to meal
	            	String newMealName;									//New name of meal to be edited
	            	boolean hasValidFood = false;						//If has a valid food
	            	boolean hasValidWeight = true;						//If has a valid weight

	            	//If meal does not have a name, display invalidName, and only do it once
	            	if (mealName.getText().equals("")) {
	            		errorLabel.setLayoutX(220);
	            		errorLabel.setText("Invalid Name");
	            	}

	            	//If meal has a name, check if any foods are selected
	            	else if (!foodOptions[0].getValue().toString().equals("None")
	            			|| !foodOptions[1].getValue().toString().equals("None")
	            			|| !foodOptions[2].getValue().toString().equals("None")
	            			|| !foodOptions[3].getValue().toString().equals("None")) {
	            		hasValidFood = true;

	            		//If any foods are selected, check to see if they have a valid weight

	            		//If the first food in foodOptions has an option other than "None" selected, check the weight
	            		if (!foodOptions[0].getValue().toString().equals("None")) {

	            			//If the food does not have a valid weight, display an error label, set hasValidWeight to false
	            			if (foodWeights[0].getText().toString().equals("")) {
	            				errorLabel.setLayoutX(150);
	    	            		errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}

	            			//If the food has a valid weight, add the food to the newMeal list of foods
	            			else {
	    		            	String food1Name = (String) foodOptions[0].getValue();
	    		            	double food1Weight = Double.parseDouble(foodWeights[0].getText());
	    		            	Food newFood = new Food(food1Name, food1Weight);
	    		            	newMeal.add(newFood);
	            			}
	            		}

	            		//If the second food in foodOptions has an option other than "None" selected, check the weight
	            		if (!foodOptions[1].getValue().toString().equals("None")) {

	            			//If the food does not have a valid weight, display an error label, set hasValidWeight to false
	            			if (foodWeights[1].getText().toString().equals("")) {
	            				errorLabel.setLayoutX(150);
	    	            		errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}

	            			//If the food has a valid weight, add the food to the newMeal list of foods
	            			else {
	    		            	String food2Name = (String) foodOptions[1].getValue();
	    		            	double food2Weight = Double.parseDouble(foodWeights[1].getText());
	    		            	Food newFood = new Food(food2Name, food2Weight);
	    		            	newMeal.add(newFood);
	            			}
	            		}

	            		//If the third food in foodOptions has an option other than "None" selected, check the weight
            			if (!foodOptions[2].getValue().toString().equals("None")) {

            				//If the food does not have a valid weight, display an error label, set hasValidWeight to false
	            			if (foodWeights[2].getText().toString().equals("")) {
	            				errorLabel.setLayoutX(150);
	    	            		errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}

	            			//If the food has a valid weight, add the food to the newMeal list of foods
	            			else {
	    		            	String food3Name = (String) foodOptions[2].getValue();
	    		            	double food3Weight = Double.parseDouble(foodWeights[2].getText());
	    		            	Food newFood = new Food(food3Name, food3Weight);
	    		            	newMeal.add(newFood);
	            			}
            			}

            			//If the fourth food in foodOptions has an option other than "None" selected, check the weight
            			if (!foodOptions[3].getValue().toString().equals("None")) {

            				//If the food does not have a valid weight, display an error label, set hasValidWeight to false
	            			if (foodWeights[3].getText().toString().equals("")) {
	            				errorLabel.setLayoutX(150);
	    	            		errorLabel.setText("Please enter a weight for every food");
	            				hasValidWeight = false;
	            			}

	            			//If the food has a valid weight, add the food to the newMeal list of foods
	            			else {
	    		            	String food4Name = (String) foodOptions[3].getValue();
	    		            	double food4Weight = Double.parseDouble(foodWeights[3].getText());
	    		            	Food newFood = new Food(food4Name, food4Weight);
	    		            	newMeal.add(newFood);
	            			}
            			}
            			//If the meal has a valid food and each food has a valid weight edit the meal
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

	            	//If no food is selected, display an error label
	            	else {
	            		errorLabel.setLayoutX(150);
	            		errorLabel.setText("Please select a food to add to the meal");
	            	}
	            }
	        });

	        //Add functionality for cancel button
	        cancel.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	        });


	        //Have the GUI page display
			Scene scene = new Scene(root,500,500);
			//Color the scene background
			root.setBackground(bg);
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
	}	//End editMealsPage function

	/*
	 * page to save info
	 */
	public static void saveInformation(Stage primaryStage) {

		try {

			//setting buttons and title
			primaryStage.setTitle("Saving Screen");

			Label label = new Label("Dromedary Drones");
			dromedaryDronesTextStyle(label);

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


	      //Add the button styles
	        addButtonStyleNormal(home);
	        addButtonStyleNormal(button1);
	        addButtonStyleNormal(button2);

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
			//Color the scene background
			root.setBackground(bg);
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

	public static void map(Stage primaryStage) {
		try {

			//sets the title of the page
			primaryStage.setTitle("Remove Food Page");

			//create the labels
			Label label = new Label("Dromedary Drones");
			dromedaryDronesTextStyle(label);

			//creates the buttons
			Button changeImage = new Button("Change image");
	        Button update = new Button("Update Locations");
	        Button load = new Button("Load Locations");
	        Button save = new Button("Save Location");
	        Button home = new Button("Home");

	        //set position of text fields and buttons
	        update.setLayoutX(180);
	        update.setLayoutY(160);
	        save.setLayoutX(180);
	        save.setLayoutY(210);
	        load.setLayoutX(180);
	        load.setLayoutY(260);
	        changeImage.setLayoutX(180);
	        changeImage.setLayoutY(310);
	        home.setLayoutX(180);
	        home.setLayoutY(360);

	        //sets the height and width of buttons
	        changeImage.setPrefHeight(40);
	        changeImage.setPrefWidth(140);
	        update.setPrefHeight(40);
	        update.setPrefWidth(140);
	        load.setPrefHeight(40);
	        load.setPrefWidth(140);
	        save.setPrefHeight(40);
	        save.setPrefWidth(140);
	        home.setPrefHeight(40);
	        home.setPrefWidth(140);


	        //Add the button styles
	        addButtonStyleNormal(changeImage);
	        addButtonStyleNormal(update);
	        addButtonStyleNormal(load);
	        addButtonStyleNormal(save);
	        addButtonStyleNormal(home);

	        //adds to the pane
	        Pane root = new Pane();
	        root.getChildren().add(changeImage);
	        root.getChildren().add(update);
	        root.getChildren().add(load);
	        root.getChildren().add(save);
	        root.getChildren().add(label);
	        root.getChildren().add(home);

		      //if the user presses the cancel button goes back to meals page
	        update.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	updateMap(primaryStage);
	            }
	        }); //ends cancel action


	      //if the user presses the cancel button goes back to meals page
	        changeImage.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {

	           	   FileChooser fileChooser = new FileChooser();

		           	File file = fileChooser.showOpenDialog(primaryStage);


		           	if (file != null) {
		           		try {
//			                image = new Image("file:" + file.getAbsolutePath());
		           		}
		           		catch(Exception e1) {
		           			e1.printStackTrace();
		           		}

		           	}

	                updateMap(primaryStage);
	            }
	        }); //ends cancel action

	        //if the user presses the cancel button goes back to meals page
	        load.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {

	           	   FileChooser fileChooser = new FileChooser();

		           	File file = fileChooser.showOpenDialog(primaryStage);
	                if (file != null) {
	                    setLocation(locationList, file);
	                }
	                //map(primaryStage);
	            }
	        }); //ends cancel action

	        //saving files
	        save.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {

	           	   FileChooser fileChooser = new FileChooser();

	               //Set extension filter
	               FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
	               fileChooser.getExtensionFilters().add(extFilter);

	               //Show save file dialog
  	               File file = fileChooser.showSaveDialog(primaryStage);


	               fileChooser.getExtensionFilters().add(extFilter);

	               if (file != null) {



					try {
						DocumentBuilderFactory documentFactory = DocumentBuilderFactory.newInstance();

			            DocumentBuilder documentBuilder = documentFactory.newDocumentBuilder();

			            Document document = documentBuilder.newDocument();

			            // root element
			            Element root = document.createElement("locations");

			            for (int i = 0; i < locationList.size(); i++) {
			            	Element location = document.createElement("location");

			            	Element x = document.createElement("x");
			                x.appendChild(document.createTextNode(locationList.get(i).get(0).toString()));

			                Element y = document.createElement("y");
			                y.appendChild(document.createTextNode(locationList.get(i).get(1).toString()));


			                location.appendChild(x);
			                location.appendChild(y);

			                root.appendChild(location);
			            }

			            document.appendChild(root);

			            TransformerFactory transformerFactory = TransformerFactory.newInstance();
			            Transformer transformer = transformerFactory.newTransformer();
			            DOMSource domSource = new DOMSource(document);
			            StreamResult streamResult = new StreamResult(file);

			            // If you use
			            // StreamResult result = new StreamResult(System.out);
			            // the output will be pushed to the standard output ...
			            // You can use that for debugging

			            transformer.transform(domSource, streamResult);

					} catch (ParserConfigurationException | TransformerException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

	                }

	                map(primaryStage);
	            }
	        }); //ends cancel action

	        //if the user presses the cancel button goes back to meals page
	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                mainPage(primaryStage);
	            }
	        }); //ends cancel action

	        //creates the scene
			Scene scene = new Scene(root,500,500);
			//Color the scene background
			root.setBackground(bg);
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

	}//ends map method

	public static void updateMap(Stage primaryStage) {
		try {
		Pane root = new Pane();


		//creates the image
		ImageView selectedImage = new ImageView();
//		selectedImage.setImage(image);


		//the image will resize with the window
		selectedImage.fitWidthProperty().bind(primaryStage.widthProperty());
		selectedImage.fitHeightProperty().bind(primaryStage.heightProperty());

		//adds to the pane
		root.getChildren().addAll(selectedImage);

		//creates the buttons
        Button back = new Button("Back");

        //set position of text fields and buttons
        back.setLayoutX(400);
        back.setLayoutY(450);


        //sets the height and width of buttons
        back.setPrefHeight(40);
        back.setPrefWidth(100);


        for (ArrayList<Double> location : locationList) {
        	double x = location.get(0);
        	double y = location.get(1);

		     Button bt = new Button();
			 bt.setShape(new Circle(5));

			 bt.setPrefSize(4, 4);
			 bt.setStyle("-fx-background-color: Red");

			 bt.setLayoutX(x);
			 bt.setLayoutY(y);


			 bt.setOnAction(new EventHandler<ActionEvent>() {
				 @Override public void handle(ActionEvent e) {
                     //set button pressed values
					 root.getChildren().remove(bt);

					 for (int i = 0; i < locationList.size(); i++) {
						 if ((locationList.get(i).get(0) == bt.getLayoutX()) && (locationList.get(i).get(1) == bt.getLayoutY())) {
							 locationList.remove(locationList.get(i));
						 }
					 }
					 
					updateToSend();
				 }
			 });		
				

		     root.getChildren().addAll(bt);

        }

        //if the user presses the cancel button goes back to meals page
        back.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                map(primaryStage);
            }
        }); //ends cancel action

		//set action
		 selectedImage.setOnMouseClicked(new EventHandler<MouseEvent>() {

			 @Override
			 public void handle(MouseEvent event) {
				 //if the button has been pressed

			     Button bt = new Button();
				 bt.setShape(new Circle(5));

				 bt.setPrefSize(4, 4);
				 bt.setStyle("-fx-background-color: Red");

				 bt.setTranslateX(event.getSceneX() - 8);
				 bt.setTranslateY(event.getSceneY() - 8);

				 locationList.add(new ArrayList<Double>());
				 locationList.get(locationList.size() - 1).add(event.getSceneX());
				 locationList.get(locationList.size() - 1).add(event.getSceneY());
				 
				locationToSend = updateToSend();
				orderList.setLocations(locationToSend);

				 bt.setOnAction(new EventHandler<ActionEvent>() {
					 @Override public void handle(ActionEvent e) {
                         //set button pressed values
						 root.getChildren().remove(bt);

						 for (int i = 0; i < locationList.size(); i++) {
							 if ((locationList.get(i).get(0) == event.getSceneX()) && (locationList.get(i).get(1) == event.getSceneY())) {
								 locationList.remove(locationList.get(i));
									locationToSend = updateToSend();
									orderList.setLocations(locationToSend);
							 }
						 }
					 }
				 });

			     root.getChildren().addAll(bt);
			 }
		 });

		//Add the button styles
		 addButtonStyleNormal(back);

		root.getChildren().addAll(back);


		//creates the scene
		Scene scene = new Scene(root,500,500);
		//Color the scene background
		root.setBackground(bg);
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


	}//ends map method

	public static void setLocation(ArrayList<ArrayList<Double>> locations, File file){

		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db;
			db = dbf.newDocumentBuilder();
			Document doc = db.parse(file);
			doc.getDocumentElement().normalize();

			NodeList nList = doc.getElementsByTagName("location");
			ArrayList<ArrayList<Double>> tempList = new ArrayList<ArrayList<Double>>();


			for (int i = 0; i < nList.getLength(); i++) {
				tempList.add(new ArrayList<Double>());
				Node nNode = nList.item(i);

				if (nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement = (Element) nNode;
					double x = Double.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent());
					double y = Double.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent());
					
					tempList.get(i).add(x);
					tempList.get(i).add(y);
					
				}
			}
			
			locationList = tempList;
			
			locationToSend = updateToSend();
			orderList.setLocations(locationToSend);


		} catch (ParserConfigurationException | SAXException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void dromedaryDronesTextStyle(Label label) {
		label.setStyle("-fx-font-size: 40px;" +
				"-fx-font-family: 'Arial Black';" +
				"-fx-fill: #818181;" +
				"-fx-effect: innershadow( three-pass-box , rgba(0,0,0,0.7) , 6, 0.0 , 0 , 2 );");
		label.setLayoutX(45);
	}

	public static void setMediumLabelStyle(Label label, int xPos, int yPos) {
		label.setLayoutX(xPos);
		label.setLayoutY(yPos);
		label.setStyle("-fx-font-size: 23px;" +
				"-fx-font-family: Arial;" +
				"-fx-font-weight: bold;" +
				"-fx-font-fill: #ffffff;");

	}

	public static void displayFoodOrMealButtons(Button foodEdit, Button foodRemove, Button mealEdit, Button mealRemove, boolean hide) {
        foodEdit.setLayoutX(180);
        foodEdit.setLayoutY(235);
        foodRemove.setLayoutX(180);
        foodRemove.setLayoutY(185);

        mealEdit.setLayoutX(180);
        mealEdit.setLayoutY(235);
        mealRemove.setLayoutX(180);
        mealRemove.setLayoutY(185);

        foodEdit.setVisible(hide);
        foodRemove.setVisible(hide);
        mealEdit.setVisible(!hide);
        mealRemove.setVisible(!hide);
	}


	public static void addButtonStyleNormal(Button button) {
		String basic = "-fx-effect: dropshadow(gaussian, #828282, 10, 0.1, 0, 5);" +
				"-fx-background-radius:14px;" +
				"-fx-border-radius:12px;" +
				"-fx-border-width:1.5px;" +
				"-fx-border-style: solid;"+
				"-fx-border-color: #f7b4a1;" +
				"-fx-display:inline-block;" +
				"-fx-cursor:pointer;" +
				"-fx-text-color:#000000;" +
				"-fx-font-family:Arial;" +
				"-fx-font-size:12px;" +
				"-fx-font-weight:bold;" +
				"-fx-padding:10px 10px;" +
				"-fx-text-decoration:none;";

		String mainStyle = "-fx-background-color:linear-gradient(to bottom, #ffe0ac 5%, #e6ca9c 100%);" +
				basic;

		String hoverStyle = "-fx-background-color:linear-gradient(to bottom, #ffe0ac 15%, #ffbdab  100%);" +
				basic;

		String clickStyle =
			"-fx-background-color:linear-gradient(to bottom, #f2d3a0 45%, #f7b4a1 100%);" +
			basic;

		//Set the button's main style
		button.setStyle(mainStyle);
		button.setTextOverrun(OverrunStyle.CLIP);

		//Set the button to do special things on Hover and revert after
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(mainStyle));

        //Set the button to "move down" when clicked and "go back" when released
        button.setOnMousePressed(e -> button.setStyle(clickStyle));
        button.setOnMouseReleased(e -> button.setStyle(mainStyle));
	}
	
	public static ArrayList<ArrayList<Double>> updateToSend(){

		
		ArrayList<ArrayList<Double>> tempList = new ArrayList<ArrayList<Double>>();
		
		for (ArrayList<Double> location: locationList) {

			ArrayList<Double> temp = new ArrayList<Double>();
			
			double x = location.get(0);
			double y = location.get(1);
			
			x = x * feetPerPixelWidth;
			y = y * feetPerPixelHeight;
			
			
			if (x < x0) {
				x = x - x0;
			}
			else {
				x = x - x0;
			}
			
			if (y < x0) {
				y = (-y) + y0;
			}
			else {
				y = (-y) + y0;
			}
			
			temp.add(x);
			temp.add(y);
			
			tempList.add(temp);
			
			System.out.println(x + " " + y);
			
		}
		
		return tempList;
	}

	public static void addButtonStyleSmall(Button button,int padX, int padY) {
		String basic = "-fx-effect: dropshadow(gaussian, #d1bfa1, 10, 0.1, 0, 5);" +
				"-fx-background-radius:3px;" +
				"-fx-border-radius:3px;" +
				"-fx-border-width:1.5px;" +
				"-fx-border-style: solid;"+
				"-fx-border-color: #f7b4a1;" +
				"-fx-display:inline-block;" +
				"-fx-cursor:pointer;" +
				"-fx-text-color:#000000;" +
				"-fx-font-family:Arial;" +
				"-fx-font-size:12px;" +
				"-fx-font-weight:bold;" +
				"-fx-padding:" + padX + "px " + padY + "px;" +
				"-fx-text-decoration:none;";

		String mainStyle = "-fx-background-color:linear-gradient(to bottom, #ffe0ac 5%, #e6ca9c 100%);" +
				basic;

		String hoverStyle = "-fx-background-color:linear-gradient(to bottom, #ffe0ac 15%, #ffbdab  100%);" +
				basic;

		String clickStyle =
			"-fx-background-color:linear-gradient(to bottom, #f2d3a0 45%, #f7b4a1 100%);" +
			basic;

		//Set the button's main style
		button.setStyle(mainStyle);
		button.setTextOverrun(OverrunStyle.CLIP);

		//Set the button to do special things on Hover and revert after
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(mainStyle));

        //Set the button to "move down" when clicked and "go back" when released
        button.setOnMousePressed(e -> button.setStyle(clickStyle));
        button.setOnMouseReleased(e -> button.setStyle(mainStyle));
	}

	public static void addButtonStyleDisabled(Button button) {
		String basic = "-fx-effect: dropshadow(gaussian, #828282, 10, 0.1, 0, 5);" +
				"-fx-background-radius:14px;" +
				"-fx-border-radius:12px;" +
				"-fx-border-width:1.5px;" +
				"-fx-border-style: solid;"+
				"-fx-border-color: #d19a8a;" +
				"-fx-display:inline-block;" +
				"-fx-cursor:pointer;" +
				"-fx-text-color:#000000;" +
				"-fx-font-family:Arial;" +
				"-fx-font-size:12px;" +
				"-fx-font-weight:bold;" +
				"-fx-padding:10px 10px;" +
				"-fx-text-decoration:none;";

		String mainStyle = "-fx-background-color:linear-gradient(to bottom, #f0e0c7 5%, #d4cbbc 100%);" +
				basic;

		button.setStyle(mainStyle);
	}

}
