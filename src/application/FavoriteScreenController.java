package application;

import java.io.IOException;

import daisty.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class FavoriteScreenController {
    User user = new User();

    @FXML
    private Button getRecipesBtn;

    @FXML
    private Button favoritesBtn;

    @FXML
    private Button settingsBtn;

    @FXML
    private Button logOutBtn;

    @FXML
    private GridPane favGrid;

    @FXML
    void toFavorites(ActionEvent event) throws IOException {
        Parent mainSceneParent = FXMLLoader.load(getClass().getResource("FavoriteScreen.fxml"));
        Scene mainScene = new Scene(mainSceneParent);
        Stage appStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        user = (User) appStage.getUserData();
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
        //appStage.setUserData(user);
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
