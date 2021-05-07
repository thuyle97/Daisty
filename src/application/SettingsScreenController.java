package application;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

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
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SettingsScreenController {

    final ObservableList<String> allergiesList = FXCollections.observableArrayList("N/A");
    final ObservableList<String> calList =
            FXCollections.observableArrayList("1500", "2000", "2500", "3000");

    private User selectedUser = new User();
    @FXML
    private CheckBox vgnBox;

    @FXML
    private CheckBox vgtrn;

    @FXML
    private CheckBox isOther;

    @FXML
    private DatePicker datePicker;

    @FXML
    private Label fnLbl;

    @FXML
    private TextField boxFn;

    @FXML
    private TextField boxLn;

    @FXML
    private ComboBox<String> calorieCount;

    @FXML
    private Label fnResponse;

    @FXML
    private Label lnResponse;

    @FXML
    private Label dobResponse;

    @FXML
    private Label calResponse;

    @FXML
    private TextField boxAllergy;

    @FXML
    private Button addAllergy;

    @FXML
    private ComboBox<String> viewAllergies;

    @FXML
    private Button update;

    @FXML
    private Button edit;
    
    @FXML
    private Button backButton;
    
    @FXML
    private Label updateMessage;
    

    @FXML
    private void handleAddAllergy(ActionEvent event) {
        if (allergiesList.contains("N/A"))
            allergiesList.clear();
        String addMe = boxAllergy.getText();
        if (addMe.equals("") || allergiesList.contains(addMe)) {
            return;
        }
        allergiesList.add(addMe);
        boxAllergy.clear();
    }

    @FXML
    public void initialize(User user) {
    	selectedUser = user;
        viewAllergies.setItems(allergiesList);	 // find out how to keep user inputed values?
        calorieCount.setItems(calList); 		// needs more customizability?
        
        setInfor(user);
    }

    private void setInfor(User user)
    {
    	boxFn.setText(user.getFirstName());
        boxLn.setText(user.getLastName());
        datePicker.setPromptText(user.getDOB().toString());
        if (user.getEatingPractice() == "Vegan")
        {
        	vgnBox.setSelected(true);
        	vgtrn.setSelected(false);
        	isOther.setSelected(false);
        }
        else if (user.getEatingPractice() == "Vegetarian")
        {
        	vgnBox.setSelected(false);
        	vgtrn.setSelected(true);
        	isOther.setSelected(false);
        }
        else
        {
        	vgnBox.setSelected(false);
        	vgtrn.setSelected(false);
        	isOther.setSelected(true);
        }
        allergiesList.remove(0);
        allergiesList.addAll(user.getAllergies());
        calorieCount.setPromptText(user.getCalorieIntake());
        boxFn.setDisable(true);
        boxLn.setDisable(true);
        datePicker.setDisable(true);
        vgnBox.setDisable(true);
    	vgtrn.setDisable(true);
    	isOther.setDisable(true);
    	boxAllergy.setDisable(true);
    	calorieCount.setDisable(true);
    }
    
    /*
     * re-enables fields and changes submit button label to update
     */
    @FXML
    void enableEdit(ActionEvent event) {
        update.setVisible(true);
        boxFn.setDisable(false);
        boxLn.setDisable(false);
        datePicker.setDisable(false);
        vgnBox.setDisable(false);
    	vgtrn.setDisable(false);
    	isOther.setDisable(false);
    	boxAllergy.setDisable(false);
    	calorieCount.setDisable(false);
    }

    @FXML
    void updateUser(ActionEvent event) throws IOException
    {
    	if (datePicker.getValue() != null && calorieCount.getValue() != null)
    	{
    		FileWriter file = new FileWriter ("src/userInfo/" + selectedUser.getUsername().toLowerCase() + ".txt");
        	PrintWriter outputFile1 = new PrintWriter (file);
        	outputFile1.println(selectedUser.getPassword());
        	outputFile1.println(boxFn.getText());
        	outputFile1.println(boxLn.getText());
        	outputFile1.println(datePicker.getValue());
        	outputFile1.println(vgnBox.isSelected());
        	outputFile1.println(vgtrn.isSelected());
        	outputFile1.println(isOther.isSelected());
        	outputFile1.println(allergiesList);
        	outputFile1.println(calorieCount.getValue());
        	outputFile1.close();
        	
        	selectedUser.setFirstName(boxFn.getText());
        	selectedUser.setLastName(boxLn.getText());
        	selectedUser.setDOB(datePicker.getValue());
    		if (vgnBox.isSelected())
    			selectedUser.setEatingPractice("Vegan");
    		if (vgtrn.isSelected())
    			selectedUser.setEatingPractice("Vegetarian");
    		if (isOther.isSelected())
    			selectedUser.setEatingPractice("Other");
    		selectedUser.setAllergies(allergiesList);
    		selectedUser.setCalorieIntake(calorieCount.getValue());
    		
    		FXMLLoader loader = new FXMLLoader(getClass().getResource("SettingsScreen.fxml"));
        	Parent mainSceneParent = loader.load();
        	Scene mainScene = new Scene(mainSceneParent);
        	
    		Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    		SettingsScreenController controller = loader.getController(); 
    		controller.initialize(selectedUser);
    		//appStage.setUserData(user);
    		appStage.setScene(mainScene);
    		appStage.show();
    	}
    	else
    	{
    		updateMessage.setVisible(true);
    	}
    }
    
    @FXML
    void backHome(ActionEvent actionEvent) throws IOException
    {
    	Parent homePage = FXMLLoader.load(getClass().getResource("HomeScreen.fxml"));
        Scene homeScene = new Scene(homePage);
        Stage appStage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        appStage.setScene(homeScene);
        appStage.show();
    }
    public ObservableList<String> getAllergies() {
        return allergiesList;
    }

    public void setAllergies(ObservableList<String> addMe) {
        addMe = allergiesList;
    }

}
