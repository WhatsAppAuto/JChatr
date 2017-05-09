package Chatr.View.ChatList;

import Chatr.Controller.Manager;
import Chatr.Converstation.Conversation;
import Chatr.View.CurrentChat.CurrentChatView;
import Chatr.View.UpdateService;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ChatListPresenter {
	@FXML
	private Button newChatButton;
	@FXML
	private Button searchButton;
	@FXML
	private Label userName;
	@FXML
	private ListView<Conversation> chatsList;
	@FXML
	private AnchorPane currentChat;
	private static Logger log = LogManager.getLogger(ChatListPresenter.class);


	@FXML
	private void initialize() {
		addListeners();
		linkUpdateProperties();
		CurrentChatView chatView = new CurrentChatView();
		setChatWindow(chatView.getView());
	}

	/**
	 * adds the listeners for the required properties to get executed when
	 * changes in the UI occur
	 */
	private void addListeners() {
		chatsList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
			Parent newChat = switchChat(newValue);
			setChatWindow(newChat);
		});
	}

	/**
	 * Building Update links from UI properties to Manager methods
	 * all links are guaranteed to get updated at a specified interval
	 */
	private void linkUpdateProperties() {
		UpdateService.linkLowPriority(userName.textProperty(),
				Manager::getUserName
		);
		UpdateService.linkHighPriority(chatsList.itemsProperty(),
				Manager::getUserChats
		);
	}

	/**
	 * sets new Conversation in Manager and creates corresponding chat Window
	 * @param conversation the conversation to switch to
	 * @return newly created chat window
	 */
	private Parent switchChat(Conversation conversation) {
		Manager.setCurrentChat(conversation);
		CurrentChatView chatView = new CurrentChatView();
		log.debug("switched to Chat: " + conversation);
		return chatView.getView();
	}

	/**
	 * displays the given Parent in the currentChat AnchorPane
	 * @param parent window to display
	 */
	private void setChatWindow(Parent parent) {
		currentChat.getChildren().clear();
		currentChat.getChildren().add(parent);
	}
}
