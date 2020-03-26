import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
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
	
	public void mainPage(Stage primaryStage) {
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
		
	}
	
	public static void meals(Stage primaryStage) {
		try {
			primaryStage.setTitle("Meals Page");
			
			Label label = new Label("Dromedary Drones");
			
			label.setFont(new Font("Arial", 40));
			label.setLayoutX(80);

	        Button addFood = new Button("Add Food");
	        Button addMeal = new Button("Add Meal");
	        
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
	        addFood.setLayoutY(200);
	        addMeal.setLayoutX(180);
	        addMeal.setLayoutY(250);
	        
	        addFood.setPrefHeight(40);
	        addFood.setPrefWidth(140);
	        addMeal.setPrefHeight(40);
	        addMeal.setPrefWidth(140);
	        
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
		
	}
	
	public static void uploadMapPage(Stage primaryStage) {
		
	}
}
