package Chatr.View.ChatList;

import Chatr.Controller.Manager;
import Chatr.Model.Chat;
import Chatr.View.ChatList.ChatCell.ChatCell;
import Chatr.View.CurrentChat.CurrentChatController;
import Chatr.View.Loader;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatListController extends Loader {
	@FXML
	private Button newChatButton;
	@FXML
	private Button searchButton;
	@FXML
	private Label userName;
	@FXML
	private ListView<Chat> chatsList;
	@FXML
	private AnchorPane currentChatAnchor;
	private CurrentChatController currentChat;
	private static Logger log = LogManager.getLogger(ChatListController.class);


	@FXML
	private void initialize() {
		addListeners();
		linkUpdateProperties();
		CurrentChatController chatView = new CurrentChatController();
		setChatWindow(chatView.getView());
		currentChat = (CurrentChatController) chatView.getController();
		chatsList.setCellFactory(param -> new ChatCell());
	}

	/**
	 * adds the listeners for the required properties to get executed when
	 * changes in the UI occur
	 */
	private void addListeners() {
		chatsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue)
				-> switchChat(newValue));
	}

	/**
	 * Building Update links from UI properties to Manager methods
	 * all links are guaranteed to get updated at a specified interval
	 */
	private void linkUpdateProperties() {
		userName.textProperty().bind(Manager.getLocalUserName());
		Bindings.bindContent(chatsList.getItems(), Manager.getUserChats());
	}

	/**
	 * sets new Chat in Manager and creates corresponding chat Window
	 *
	 * @param chat the chat to switch to
	 */
	private void switchChat(Chat chat) {
		currentChat.swtich(chat.getID().get());
		log.debug("switched to Chat: " + chat);
	}

	/**
	 * displays the given Parent in the currentChatAnchor AnchorPane
	 *
	 * @param parent window to display
	 */
	private void setChatWindow(Parent parent) {
		currentChatAnchor.getChildren().clear();
		currentChatAnchor.getChildren().add(parent);
	}
}
