import java.util.ArrayList;
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

	}

	public static void probabilityPage(Stage primaryStage) {
		try {
			primaryStage.setTitle("Meal Probabilities");
			Label label = new Label("Dromedary Drones");

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);


			Label mealLabel = new Label("Meals:");
			mealLabel.setFont(new Font("Arial",20));
			mealLabel.setLayoutX(125);
			mealLabel.setLayoutY(72);

			Label mealProbLabel = new Label("(%):");
			mealProbLabel.setFont(new Font("Arial",20));
			mealProbLabel.setLayoutX(265);
			mealProbLabel.setLayoutY(72);

	        Button home = new Button("Home");

	        ListView<String> listFood = new ListView<String>();
	        ListView<Double> listProbs = new ListView<Double>();

	        ObservableList<String> foodItem =FXCollections.observableArrayList (
		            "Cheeseburger", "Hamburger", "Fries", "Drink");
		    /*
	        for (int i = 0; i < mealsList.size(); i++) {
		    	foodItem.add(mealsList.get(i).getName());
		    }
		    */
	        listFood.setItems(foodItem);

	        double fake = 25.2;
	        double fake1 = 13.2;
	        double fake2 = 25.7;
	        double fake3 = 07.8;
	        double fake4 = 9.5;

		    ObservableList<Double> foodProbs =FXCollections.observableArrayList (
		    		fake, fake1, fake2, fake3, fake4);
		    /*
		    for (int i = 0; i < mealsList.size(); i++) {
		    	foodProbs.add(mealsList.get(i).getProbability());
		    }
		    */
		    listProbs.setItems(foodProbs);
		    listProbs.setEditable(true);
		    listProbs.setCellFactory(DoubleFieldListCell.forListView());
		    listProbs.setCellFactory(TextFieldListCell.forListView());

	        Pane root = new Pane();

	        listFood.setPrefWidth(140);
	        listFood.setPrefHeight(300);
	        listProbs.setPrefWidth(70);
	        listProbs.setPrefHeight(300);

	        listFood.setLayoutX(125);
	        listFood.setLayoutY(100);
	        listProbs.setLayoutX(265);
	        listProbs.setLayoutY(100);


	        root.getChildren().add(listProbs);
	        root.getChildren().add(listFood);
	        root.getChildren().add(home);
	        root.getChildren().add(label);
	        root.getChildren().addAll(mealLabel,mealProbLabel);

	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {

	            @Override public void handle(ActionEvent e) {
		            double total = 0;
		            for (int i = 0; i < listProbs.getItems().size(); i++) {
		            	try {
		            		String entryValue = listProbs.getItems().get(i);
		            		double val = Double.parseDouble(entryValue);
		            		if (val < 0) {
		            			System.out.println("Less than 0!");
		            		}
		            		total += val;
		            	} catch (Exception E) {
		            		System.out.println("Error parsing double!");
		            	}
		            }
		            if (total == 100) {
		                mainPage(primaryStage);
		            } else if (total > 100) {
		            	System.out.println("Total greater than 100. You are over by: " + (total-100));
		            } else {
		            	System.out.println("Total less than 100. You are under by: " + (100-total));
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

	        Button addFood = new Button("Add Food");
	        Button addMeal = new Button("Add Meal");

	        Button home = new Button("Home");
	        ListView<String> listFood = new ListView<String>();
	        ListView<String> listMeals = new ListView<String>();

	        ObservableList<String> food =FXCollections.observableArrayList (
		            "Cheeseburger", "Hamburger", "Fries", "Drink");
		        listFood.setItems(food);

		    ObservableList<String> meals =FXCollections.observableArrayList (
			        "Cheeseburger Combo", "Hamburger Combo", "Fries & Drink");
			    listMeals.setItems(meals);

	        Pane root = new Pane();

	        addFood.setLayoutX(180);
	        addFood.setLayoutY(100);
	        addMeal.setLayoutX(180);
	        addMeal.setLayoutY(150);
	        home.setLayoutX(180);
	        home.setLayoutY(360);
	        
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

	        addMeal.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	                addMealPage(primaryStage);
	            }
	        });

	        home.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
	            @Override public void handle(ActionEvent e) {
	            	//mainPage(primaryStage);
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

			Label label = new Label("Dromedary Drones");
			Label name = new Label("Food Name");
			Label weight = new Label("Food Weight");

			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

			name.setFont(new Font("Arial", 12));
			name.setLayoutX(180);
			name.setLayoutY(180);

			weight.setFont(new Font("Arial", 12));
			weight.setLayoutX(180);
			weight.setLayoutY(230);

	        Button saveFood = new Button("Save Food");
	        Button cancel = new Button("Cancel");

	        TextField foodName = new TextField();
	        TextField foodWeight = new TextField();


	        Pane root = new Pane();

	        saveFood.setLayoutX(180);
	        saveFood.setLayoutY(300);
	        cancel.setLayoutX(180);
	        cancel.setLayoutY(350);

	        saveFood.setPrefHeight(40);
	        saveFood.setPrefWidth(140);
	        cancel.setPrefHeight(40);
	        cancel.setPrefWidth(140);

	        foodName.setLayoutX(180);
	        foodName.setLayoutY(200);
	        foodWeight.setLayoutX(180);
	        foodWeight.setLayoutY(250);

	        root.getChildren().add(saveFood);
	        root.getChildren().add(cancel);
	        root.getChildren().add(foodName);
	        root.getChildren().add(foodWeight);
	        root.getChildren().add(label);
	        root.getChildren().add(name);
	        root.getChildren().add(weight);

	        saveFood.setOnAction((EventHandler<ActionEvent>) new EventHandler<ActionEvent>() {
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

	        Button button1 = new Button("Save Information");
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
	        		FileChooser fileChooser = new FileChooser();
	        		fileChooser.setTitle("Open Resource File");
	        		fileChooser.showOpenDialog(primaryStage);
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
}
