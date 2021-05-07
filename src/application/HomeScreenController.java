package application;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import daisty.MealList;
import daisty.Recipe;
import daisty.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HomeScreenController {

	private User user = new User();
	
	private Recipe selectedRecipe = new Recipe();
	
	private MealList lists = new MealList();
	
	final ObservableList<String> mealList = FXCollections.observableArrayList("breakfast", "lunch", "dinner", "snack");
	@FXML
	private Button getRecipesBtn;

	@FXML
	private Button favoritesBtn;

	@FXML
	private Button settingsBtn;

	@FXML
	private Button logOutBtn;
	
	@FXML
	private ChoiceBox<String> meal;
	
	@FXML
	private Label mealResponse;
	
	@FXML
	private Button randomBtn;
	
	@FXML
    private Button getInstructions;
	
	@FXML
	private ImageView foodImage;
	
	@FXML
	private Label foodName;
	
	@FXML
	private Label symbol;
	
	@FXML
	private Label prepTime;
	
	@FXML
	private Label foodIngredients;
	
	@FXML
	private Label foodCalories;
	
	@FXML
	private Label foodSource;
	
	@FXML
	private Button heartBtn;
	//private boolean isFavorite = false;	// link to userInfo
	
	private ArrayList<Recipe> breakfastList = new ArrayList<>();
	private ArrayList<Recipe> lunchList = new ArrayList<>();
	private ArrayList<Recipe> dinnerList = new ArrayList<>();
	private ArrayList<Recipe> snackList = new ArrayList<>();
	
	private ArrayList<Recipe> temp = new ArrayList<>();
	
	
	
	@FXML
	private void initialize() {
		if (meal != null)
			meal.setItems(mealList);
	}
	
	@FXML
	public void setMeal(User user) {
	this.user = user;
	String eatPractice = user.getEatingPractice();
	String temp3 = "";
	if (eatPractice.equals("Vegan"))
	temp3 = "vegan";
	if (eatPractice.equals("Vegetarian"))
	temp3 = "veget";
	if (eatPractice.equals("Other"))
	temp3 = "other";

	addMeal("src/recipeInfo/breakfast.txt", temp3, breakfastList);
	addMeal("src/recipeInfo/lunch.txt", temp3, lunchList);
	addMeal("src/recipeInfo/dinner.txt", temp3, dinnerList);
	addMeal("src/recipeInfo/snack.txt", temp3, snackList);
	
	lists.setbreakfastList(breakfastList);
	lists.setdinnerList(dinnerList);
	lists.setlunchList(lunchList);
	lists.setsnackList(snackList);
	this.user.setLists(lists);
	this.user.setFavorites(temp);
	}

	private void addMeal(String mealFile, String str, ArrayList<Recipe> mealList) 
	{
		try {
			File theFile = new File(mealFile);
			Scanner inputFile = new Scanner(theFile);
			
			while (inputFile.hasNextLine()) {
				Recipe recipe = new Recipe();
				String nextline = inputFile.nextLine();
				if (nextline.substring(0, 5).equals(str) || str.equals("other"))
				{
					recipe.setPath(nextline);
					File file = new File("src/recipeInfo/" + nextline + ".txt");
					Scanner input = new Scanner(file);
					String nl = input.nextLine();
					recipe.setName(nl);
					nl = input.nextLine();
					recipe.setCalories(Integer.parseInt(nl));
					nl = input.nextLine();
					recipe.setTime(nl);
					nl = input.nextLine();
					recipe.setSource(nl);
					nl = input.nextLine();
					ArrayList<String> temp1 = new ArrayList<>();
					while (!nl.equals("done")) {
						temp1.add(nl);
						nl = input.nextLine();
					}
					recipe.setIngredients(temp1);
					ArrayList<String> temp2 = new ArrayList<>();

					while (input.hasNextLine())
					{
						nl = input.nextLine();
						temp2.add(nl);
					}
					recipe.setCookingInstructions(temp2);
					mealList.add(recipe);
					// add favorites
					if (user.getFavNames() != null)
					{
						if (user.getFavNames().contains(nextline))
						{
							recipe.setFavorite(true);
							temp.add(recipe);
						}
					}
					input.close();
				}
			}
			inputFile.close();
		} 
		catch(IOException ioe) {
			System.out.println("error adding recipe");
		}
	}

	@FXML
	void updateMeal(ActionEvent event) { // update this code later
		//setMeals(event);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		
		user = (User) appStage.getUserData();
		
		this.breakfastList = this.user.getLists().getbreakfastList();
		this.lunchList = this.user.getLists().getlunchList();
		this.dinnerList = this.user.getLists().getdinnerList();
		this.snackList = this.user.getLists().getsnackList();
		
		String food = meal.getValue();
		System.out.println(food);
		
		Random rand = new Random();
		if (food.equals("breakfast"))
		{
			int index = rand.nextInt(breakfastList.size());
	        selectedRecipe = breakfastList.get(index);
		}
		if (food.equals("lunch"))
		{
			int index = rand.nextInt(lunchList.size());
	        selectedRecipe = lunchList.get(index);
		}
		if (food.equals("dinner"))
		{
			int index = rand.nextInt(dinnerList.size());
	        selectedRecipe = dinnerList.get(index);
		}
		if (food.equals("snack"))
		{
			int index = rand.nextInt(snackList.size());
	        selectedRecipe = snackList.get(index);
		}
		
		File file = new File("src/recipeInfo/recipeImage/" + selectedRecipe.getPath() + ".jpeg");
        Image image = new Image(file.toURI().toString());
        foodImage.setImage(image);
        
		foodName.setText(selectedRecipe.getName());
		prepTime.setText("Time: " + selectedRecipe.getTime());
		String temp4 = "";
		for (int i = 0; i < selectedRecipe.getIngredients().size(); i++)
		{
			temp4 += selectedRecipe.getIngredients().get(i) + "\n";
		}
		
		foodIngredients.setText(temp4);
		foodCalories.setText("Calories: " + String.valueOf(selectedRecipe.getCalories()));
		foodSource.setText("Source: " + selectedRecipe.getSource());
		
		foodImage.setVisible(true);
		foodName.setVisible(true);
		symbol.setVisible(true);
		prepTime.setVisible(true);
		foodIngredients.setVisible(true);
		foodCalories.setVisible(true);
		foodSource.setVisible(true);
		heartBtn.setVisible(true);
		getInstructions.setVisible(true);
	}

	@FXML
	void addToFav(ActionEvent event) throws IOException{
		String Fav;
		
		if (!selectedRecipe.isFavorite()) // add to favorite
		{
			heartBtn.getStyleClass().clear();
			heartBtn.getStyleClass().add("heart-button-favorited");
			Fav = "Recipe has been added to favorites.";
			
			FileWriter file = new FileWriter ("src/userInfo/" + user.getUsername().toLowerCase() + ".txt", true);
	    	PrintWriter outputFile1 = new PrintWriter (file);
	    	outputFile1.println(selectedRecipe.getPath());
			outputFile1.close();
			
		} else {
			heartBtn.getStyleClass().clear();
			heartBtn.getStyleClass().add("heart-button");
			Fav = "Recipe has been removed from favorites.";
		}
//		isFavorite = !isFavorite;
		System.out.println(Fav);
	}
	
    @FXML
    void toInstructions(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("RecipeScreen.fxml"));
    	Parent mainSceneParent = loader.load();
    	Scene mainScene = new Scene(mainSceneParent);
    	
		RecipeScreenController controller = (RecipeScreenController) loader.getController(); 
		controller.initialize(selectedRecipe);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(mainScene);
		appStage.show();
    }
	
	@FXML
    void toFavorites(ActionEvent event) throws IOException {
		Parent mainSceneParent = FXMLLoader.load(getClass().getResource("FavoriteScreen.fxml"));
		Scene mainScene = new Scene(mainSceneParent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		user = (User) appStage.getUserData();
		if (user.getFavorites() != null)
		{
			for (int i = 0; i < user.getFavorites().size(); i++)
			{
				System.out.println(user.getFavorites().get(i).getName());
			}
		}
		appStage.setUserData(user);
		appStage.setScene(mainScene);
		appStage.show();
    }

    @FXML
    void toRecipes(ActionEvent event) throws IOException {
    	Parent mainSceneParent = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
		Scene mainScene = new Scene(mainSceneParent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		user = (User) appStage.getUserData();
		appStage.setUserData(user);
		appStage.setScene(mainScene);
		appStage.show();
    }

    @FXML
    void toSettings(ActionEvent event) throws IOException {
    	FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsScreen.fxml"));
    	Parent mainSceneParent = loader.load();
    	Scene mainScene = new Scene(mainSceneParent);
    	
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		user = (User) appStage.getUserData();
		SettingsScreenController controller = loader.getController(); 
		controller.initialize(user);
		appStage.setUserData(user);
		appStage.setScene(mainScene);
		appStage.show();
    }
    
    @FXML
    void logOut(ActionEvent event) throws IOException {
    	Parent mainSceneParent = FXMLLoader.load(getClass().getResource("SignInScreen.fxml"));
		Scene mainScene = new Scene(mainSceneParent);
		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		appStage.setScene(mainScene);
		appStage.show();
    }
}
