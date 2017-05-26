package Chatr.View;


import Chatr.Controller.Manager;
import Chatr.View.ChatList.ChatListController;
import Chatr.View.Login.LoginController;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class JavaFX extends Application {
	private Stage primaryStage;
	private AnchorPane rootLayout;
	private AnchorPane currentChat;
	private Logger log = LogManager.getLogger(JavaFX.class);

	public static void initGUI(String[] args) {
		launch(JavaFX.class, args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		log.info("started JavaFX Thread: " + Manager.getTime());
		this.primaryStage = primaryStage;
		this.primaryStage.setTitle("Chatr");
		initializeLoginLayout();
		primaryStage.show();
	}

	private void initializeLoginLayout() {
		LoginController login = new LoginController();
		Scene scene = new Scene(login.getView());
		scene.getStylesheets().addAll(
				getClass().getResource("/jbootx/bootstrap3.css").toExternalForm(),
				getClass().getResource("/css/chatr.css").toExternalForm()
		);
		primaryStage.setScene(scene);
	}
}
