import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.UnaryOperator;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
public class Main extends Application {

	private static Food f1 = new Food("1/4 lb Hamburger", 6); 
	private static Food f2 = new Food("Fries", 4); 
	private static Food f3 = new Food("12 oz Drink", 14); 
	private static ArrayList<Food> foodsList = new ArrayList<Food>(Arrays.asList(f1,f2,f3)); 
	private static ArrayList<String> foodNames = new ArrayList<String>(Arrays.asList("1/4 lb Hamburger", "Fries", "12 oz Drink")); 
	
	public static void main(String[] args) {
		launch(args);
	}


	@Override
	public void start(Stage primaryStage) {

		try {
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



	        Pane root = new Pane();
	        button1.setLayoutX(180);
	        button1.setLayoutY(200);
	        button2.setLayoutX(180);
	        button2.setLayoutY(250);


	        root.getChildren().add(button1);
	        root.getChildren().add(button2);
	        root.getChildren().add(label);

	        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {

	        		mainPage(primaryStage);
	            }
	        });


	        button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                System.exit(1);
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

	public static void mainPage(Stage primaryStage) {

		try {

			primaryStage.setTitle("Main Page");

			Label label = new Label("Dromedary Drones");

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

	        Button button1 = new Button("Start Simulation");
	        Button button2 = new Button("Edit Meals");
	        Button button3 = new Button("Edit Probabilities");
	        Button button4 = new Button("Exit");



	        Pane root = new Pane();
	        button1.setLayoutX(180);
	        button1.setLayoutY(200);
	        button2.setLayoutX(180);
	        button2.setLayoutY(250);
	        button3.setLayoutX(180);
	        button3.setLayoutY(300);
	        button4.setLayoutX(180);
	        button4.setLayoutY(350);

	        button1.setPrefHeight(40);
	        button1.setPrefWidth(140);
	        button2.setPrefHeight(40);
	        button2.setPrefWidth(140);
	        button3.setPrefHeight(40);
	        button3.setPrefWidth(140);
	        button4.setPrefHeight(40);
	        button4.setPrefWidth(140);

	        root.getChildren().add(button1);
	        root.getChildren().add(button2);
	        root.getChildren().add(button3);
	        root.getChildren().add(button4);
	        root.getChildren().add(label);

	        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                simmulationPage(primaryStage);
	            }
	        });

	        button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
	            }
	        });

	        button3.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                probabilityPage(primaryStage);
	            }
	        });

	        button4.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                System.exit(1);
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

	public static void simmulationPage(Stage primaryStage) {
		try {

			primaryStage.setTitle("Simulation Results");

			Label label = new Label("Dromedary Drones");
			Label label2 = new Label("Simulation Complete!");
			Label label3 = new Label("First in First Out");
			Label label4 = new Label("Knapsack Packing");
			//FIFO labels
			Label fifo1 = new Label("Average Delivery Time:");
			Label fifo2 = new Label("Worst Delivery Time:");
			//Knapsack Labels
			Label knap1 = new Label("Average Delivery Time:");
			Label knap2 = new Label("Worst Delivery Time:");

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
			//Knapsack labels
			knap1.setFont(new Font("Arial", 18));
			knap1.setLayoutX(530);
			knap1.setLayoutY(177);
			knap2.setFont(new Font("Arial", 18));
			knap2.setLayoutX(530);
			knap2.setLayoutY(213);

			Button button1 = new Button("Save Data");
			Button button2 = new Button("Home");
			Button button3 = new Button("Exit");



			Pane root = new Pane();
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
			root.getChildren().add(knap1);
			root.getChildren().add(knap2);


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
			primaryStage.setTitle("Meal Probabilities");
			Label label = new Label("Dromedary Drones");

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);


			Label mealLabel = new Label("Meals:");
			mealLabel.setFont(new Font("Arial",20));
			mealLabel.setLayoutX(250);
			mealLabel.setLayoutY(72);

			Label mealProbLabel = new Label("(%):");
			mealProbLabel.setFont(new Font("Arial",20));
			mealProbLabel.setLayoutX(390);
			mealProbLabel.setLayoutY(72);

			 Label totalLabel = new Label("Percentages Total To:");
		    totalLabel.setFont(new Font("Arial",15));
		    totalLabel.setLayoutX(245);
		    totalLabel.setLayoutY(385);

		    Label totalVal = new Label("100");
		    totalVal.setFont(new Font("Arial",15));
		    totalVal.setLayoutX(395);
		    totalVal.setLayoutY(385);

		    Label errorLabel = new Label("");
		    errorLabel.setFont(new Font("Arial",15));
		    errorLabel.setLayoutX(135);
		    errorLabel.setLayoutY(420);

		    Label errorLabel2 = new Label("");
		    errorLabel2.setFont(new Font("Arial", 15));
		    errorLabel2.setLayoutX(30);
		    errorLabel2.setLayoutY(325);

		    Label mealsPerShift = new Label("Meals per shift:");
		    mealsPerShift.setFont(new Font("Arial",20));
		    mealsPerShift.setLayoutX(30);
		    mealsPerShift.setLayoutY(72);

	        ListView<String> listFood = new ListView<String>();
	        ListView<String> listProbs = new ListView<String>();

	        ObservableList<String> foodItem =FXCollections.observableArrayList (
		            "Cheeseburger", "Hamburger", "Fries", "Drink");
		    /*
	        for (int i = 0; i < Orders.getMealList().size(); i++) {
		    	foodItem.add(Orders.getMealList().get(i).getName());
		    }
		    */
	        listFood.setItems(foodItem);

		    ObservableList<String> foodProbs =FXCollections.observableArrayList (
		    		"10", "22.5", "30.4", "25.4", "11.7");
		    /*
		    for (int i = 0; i < Orders.getMealList().size(); i++) {
		    	foodItem.add(Orders.getMealList().get(i).getPercentage());
		    }
		    */
		    listProbs.setItems(foodProbs);
		    listProbs.setEditable(true);
		    listProbs.setCellFactory(TextFieldListCell.forListView());
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
		        }
		    });

	        ListView<String> labeledShifts = new ListView<String>();
	        ListView<String> shiftProbabilities = new ListView<String>();

		    ObservableList<String> shiftLabels =FXCollections.observableArrayList (
		            "First Shift: ", "Second Shift: ", "Third Shift: ", "Fourth Shift: ");
		    ObservableList<String> shiftProbs =FXCollections.observableArrayList (
		            "10", "15", "18", "17");
		    shiftProbabilities.setItems(shiftProbs);
		    labeledShifts.setItems(shiftLabels);

		    labeledShifts.setLayoutX(25);
		    labeledShifts.setPrefHeight(100);
		    labeledShifts.setPrefWidth(125);
		    labeledShifts.setLayoutY(200);

		    shiftProbabilities.setLayoutX(150);
		    shiftProbabilities.setPrefHeight(100);
		    shiftProbabilities.setPrefWidth(60);
		    shiftProbabilities.setLayoutY(200);

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
		        }
		    });
		    shiftProbabilities.setEditable(true);
		    shiftProbabilities.setCellFactory(TextFieldListCell.forListView());

	        Button home = new Button("Home");
	        Pane root = new Pane();

	        home.setLayoutX(130);
	        home.setLayoutY(385);
	        home.setPrefWidth(75);


	        listFood.setPrefWidth(140);
	        listFood.setPrefHeight(275);
	        listProbs.setPrefWidth(70);
	        listProbs.setPrefHeight(275);

	        listFood.setLayoutX(250);
	        listFood.setLayoutY(100);
	        listProbs.setLayoutX(390);
	        listProbs.setLayoutY(100);


	        root.getChildren().add(listProbs);
	        root.getChildren().add(listFood);
	        root.getChildren().add(home);
	        root.getChildren().add(label);
	        root.getChildren().addAll(totalVal,totalLabel,errorLabel);
	        root.getChildren().addAll(mealLabel,mealProbLabel,mealsPerShift);
	        root.getChildren().addAll(labeledShifts,shiftProbabilities,errorLabel2);

	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

	            @Override public void handle(ActionEvent e) {
		            if (errorLabel.getText() == "" && errorLabel2.getText() == "") {
		            	if (totalVal.getText() == "100") {
			            	/*
			    		    for (int i = 0; i < Orders.getMealList().size(); i++) {
			    		    	String entryValue = listProbs.getItems().get(i);
			            		double val = Double.parseDouble(entryValue);
			    		    	Orders.getMealList().get(i).setPercentage(val);
			    		    }
			    		    */
			                mainPage(primaryStage);
		            	} else {
			            	errorLabel.setText("Invalid Summation: \n\tMeal percentages must total 100.");
			            }
		            }
	            }
	        });

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
			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

			mealsLabel.setFont(new Font("Arial", 20));
			mealsLabel.setLayoutX(370);
			mealsLabel.setLayoutY(80);

			foods.setFont(new Font("Arial", 20));
			foods.setLayoutX(70);
			foods.setLayoutY(80);


			  //adding remove food button 
	        Button removeFood = new Button("Remove Food"); 
	        //adding edit food button 
	        Button editFood = new Button("Edit Food"); 
	        Button addFood = new Button("Add Food");
	        Button addMeal = new Button("Add Meal");

	        Button home = new Button("Home");
	        ListView<String> listFood = new ListView<String>();
	        ListView<String> listMeals = new ListView<String>();
	     
	        ObservableList<String> food =FXCollections.observableArrayList (foodNames);
		        listFood.setItems(food);
		        

		    ObservableList<String> meals =FXCollections.observableArrayList ();
			    listMeals.setItems(meals);

	        Pane root = new Pane();

	        addFood.setLayoutX(180);
	        addFood.setLayoutY(100);
	        addMeal.setLayoutX(180);
	        addMeal.setLayoutY(150);
	        home.setLayoutX(180);
	        home.setLayoutY(360);
	        
	        //adding remove food button 
	        removeFood.setLayoutX(180);
	        removeFood.setLayoutY(200);
	        //adding edit food button
	        editFood.setLayoutX(180);
	        editFood.setLayoutY(250);
	        
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

	        root.getChildren().add(home);
	        root.getChildren().add(foods);
	        root.getChildren().add(mealsLabel);

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
	            	if(!checkIfEmpty(foodName)&& !checkIfEmpty(foodWeight) && checkWeightIsNum(foodWeight) && checkWeight(foodWeight)) {
	            	String addName = foodName.getText(); 
	            	double w = Double.parseDouble(foodWeight.getText()); 
	      	        //need to figure out how to get the weight 
	      	        Food f = new Food(addName, w);
	      	        foodsList.add(f); 
	      	        foodNames.add(addName); 
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

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

			mealNameHere.setFont(new Font("Arial", 12));
			mealNameHere.setLayoutX(220);
			mealNameHere.setLayoutY(80);

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

	        ObservableList<String> mealOptions = FXCollections.observableArrayList(
	        		"Cheeseburger",
	        		"Habmurger",
	        		"Fries",
	        		"Drink",
	        		"None"
	        		);

	        final ComboBox option1 = new ComboBox(mealOptions);
	        final ComboBox option2 = new ComboBox(mealOptions);
	        final ComboBox option3 = new ComboBox(mealOptions);
	        final ComboBox option4 = new ComboBox(mealOptions);

	        option1.setValue("Cheeseburger");
	        option2.setValue("Hamburgerr");
	        option3.setValue("Fries");
	        option4.setValue("Drink");

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

	        mealWeight1.setLayoutX(310);
	        mealWeight1.setLayoutY(150);
	        mealWeight1.setPrefWidth(40);

	        mealWeight2.setLayoutX(310);
	        mealWeight2.setLayoutY(200);
	        mealWeight2.setPrefWidth(40);

	        mealWeight3.setLayoutX(310);
	        mealWeight3.setLayoutY(250);
	        mealWeight3.setPrefWidth(40);

	        mealWeight4.setLayoutX(310);
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

	        saveMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                meals(primaryStage);
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

	public static void saveInformation(Stage primaryStage) {

		try {
			primaryStage.setTitle("Saving Screen");
			
			Label label = new Label("Dromedary Drones");

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

			
	        Button home = new Button("Home");

	        home.setLayoutX(180);
	        home.setLayoutY(250);
	        home.setPrefHeight(40);
	        home.setPrefWidth(140);
	        Button button1 = new Button("Save Information");
	        Button button2 = new Button("Exit");

	        button1.setLayoutX(130);
	        button1.setPrefWidth(140);
	        button2.setPrefHeight(40);
	        button2.setPrefWidth(140);



	        Pane root = new Pane();
	        button1.setLayoutX(180);
	        button1.setLayoutY(200);
	        button2.setLayoutX(180);
	        button2.setLayoutY(300);


	        root.getChildren().add(button1);
	        root.getChildren().add(button2);
	        root.getChildren().add(label);
	        root.getChildren().add(home);

	        button1.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	        		FileChooser fileChooser = new FileChooser();
	        		fileChooser.setTitle("Open Resource File");
	        		fileChooser.showOpenDialog(primaryStage);
	            }
	        });
	        

	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	        		mainPage(primaryStage);
	            }
	        });




	        button2.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                System.exit(1);
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
	            	for(int i = 0; i<foodsList.size(); i++) {
	            		if(foodsList.get(i).getName().equals(origName)) {
	            			foodsList.get(i).changeWeight(newW);
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
		for(int i = 0; i<foodNames.size(); i++) {
			if(foodNames.get(i).equals(name)) {
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
		
		label.setFont(new Font("Arial", 40));
		label.setLayoutX(80);
		
		name.setFont(new Font("Arial", 12));
		name.setLayoutX(180);
		name.setLayoutY(80);
		
		newName.setFont(new Font("Arial", 12));
		newName.setLayoutX(180);
		newName.setLayoutY(130);
		
        Button saveChanges = new Button("Save Changes");
        Button cancel = new Button("Cancel");
        
        TextField foodName = new TextField();
     
        TextField newFoodName = new TextField();
        
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
       
        root.getChildren().add(saveChanges);
        root.getChildren().add(cancel);
        root.getChildren().add(foodName);
        root.getChildren().add(newName); 
        root.getChildren().add(newFoodName); 
        root.getChildren().add(label);
        root.getChildren().add(name);
        
        saveChanges.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
            	if(checkNames(foodName)) {
            	String origName = foodName.getText(); 
            	String newN = newFoodName.getText(); 
            	for(int j = 0; j< foodNames.size(); j++) {
            		if(foodNames.get(j).equals(origName)) {
            			foodNames.set(j, newN); 
            		}//ends if 
            	}//ends for 
            	for(int i = 0; i<foodsList.size(); i++) {
            		if(foodsList.get(i).getName().equals(origName)) {
            			foodsList.get(i).changeName(newN);
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
	        remove.setLayoutX(180);
	        remove.setLayoutY(300);
	        cancel.setLayoutX(180);
	        cancel.setLayoutY(350);
	        remove.setPrefHeight(40);
	        remove.setPrefWidth(140);
	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);   
	        foodName.setLayoutX(180);
	        foodName.setLayoutY(200);
	        //add to the pane
	        Pane root = new Pane();
	        root.getChildren().add(remove);
	        root.getChildren().add(cancel);
	        root.getChildren().add(foodName);
	        root.getChildren().add(label);
	        root.getChildren().add(name);
	        //if you hit the remove button 
	        remove.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	if(checkNames(foodName)) {
	            	String removeName = foodName.getText();
	            	//need to remove the food from the array
	            	for(int i = 0; i<foodsList.size(); i++) {
	            		if(foodsList.get(i).getName().equals(removeName)) {
	            			foodsList.remove(foodsList.get(i)); 
	            		}
	            	}
	            	//removes from the names list for the display
	            	for(int j = 0; j< foodNames.size(); j++) {
	            		if(foodNames.get(j).equals(removeName)) {
	            			foodNames.remove(j); 
	            		}
	            	}
	            	//goes back to the meals page
	                meals(primaryStage);
	            }
	            }//ends if names
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
		}
        
	}//ends remove food page
	
	
	
	
}
