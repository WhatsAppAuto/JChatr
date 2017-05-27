package Chatr.View.Login;

import Chatr.Controller.Login;
import Chatr.Controller.Manager;
import Chatr.Model.Exceptions.EmailException;
import Chatr.Model.Exceptions.PasswordException;
import Chatr.Model.Exceptions.UserIDException;
import Chatr.Model.Exceptions.UserNameException;
import Chatr.Model.User;
import Chatr.View.ChatList.ChatListController;
import Chatr.View.Loader;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class LoginController extends Loader {

	@FXML
	private Button registrerButton;
	@FXML
	private Label userIdLabel;
	@FXML
	private TextField userId;
	@FXML
	private Label eMailLabel;
	@FXML
	private TextField eMail;
	@FXML
	private Label usernameLabel;
	@FXML
	private TextField username;
	@FXML
	private Label passwordLabel;
	@FXML
	private TextField password;
	@FXML
	private AnchorPane parent;
	private static Logger log = LogManager.getLogger(LoginController.class);

	@FXML
	private void intitialize() {

	}

	@FXML
	private void onRegisterButtonClick() {
		String userID = this.userId.getText();
		String email = this.eMail.getText();
		String userName = this.username.getText();
		String password = this.password.getText();

		try {
			User user = Login.loginUser(userID, password);
			Manager.setLocalUser(user);
			Manager.startUpdateLoop();
			changeScene();
		} catch (UserIDException e) {
			log.error(e);
		} catch (UserNameException e) {
			log.error(e);
		} catch (EmailException e) {
			log.error(e);
		} catch (PasswordException e) {
			log.error(e);
		}
	}


	private void changeScene() {
		ChatListController clc = new ChatListController();
		parent.getChildren().clear();
		parent.getChildren().add(clc.getView());
	}
}