package Chatr.View.CurrentChat;

import Chatr.Controller.Manager;
import Chatr.Helper.Enums.MessageType;
import Chatr.Helper.GIFLoader;
import Chatr.Model.Message;
import Chatr.View.CurrentChat.MessageCell.MessageCell;
import Chatr.View.Loader;
import at.mukprojects.giphy4j.entity.giphy.GiphyImage;
import at.mukprojects.giphy4j.entity.search.SearchFeed;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collections;


public class CurrentChatController extends Loader {
	private static Logger log = LogManager.getLogger(CurrentChatController.class);
	@FXML
	private ListView<Message> currentMessages;
	@FXML
	private AnchorPane chatHeader;
	@FXML
	private GridPane startMessage;
	@FXML
	private HBox bottomHBox;
	@FXML
	private Label currentChatName;
	@FXML
	private Label currentChatUsers;
	@FXML
	private TextArea textInput;
	@FXML
	private Button emojiButton;
	@FXML
	private Button sendButton;
	@FXML
	private TabPane sidebar;
	@FXML
	private VBox chatBox;
	@FXML
	private Tab gifTab;
	@FXML
	private Tab emojiTab;
	@FXML
	private Tab stickerTab;
	@FXML
	private FlowPane gifPane;
	@FXML
	private TextField gifText;
	@FXML
	private ScrollPane gifScroll;

	private String chatID;
	private boolean sidebarVisible;

	/**
	 * init UI links to Manager class and set up event Methods
	 */
	@FXML
	private void initialize() {
		addListeners();
		currentMessages.setCellFactory(param -> new MessageCell());
		sidebar.setVisible(sidebarVisible);
		bindings();
		chatHeader.setVisible(false);
		currentMessages.setVisible(false);
		bottomHBox.setVisible(false);
		startMessage.setVisible(true);
	}

	private void bindings(){
		chatHeader.managedProperty().bind(chatHeader.visibleProperty());
		currentMessages.managedProperty().bind(currentMessages.visibleProperty());
		startMessage.managedProperty().bind(startMessage.visibleProperty());
		bottomHBox.managedProperty().bind(bottomHBox.visibleProperty());
		gifPane.setVgap(2);
		gifPane.setHgap(2);
		gifPane.setPrefWrapLength(gifPane.getWidth());
	}

	private void addListeners() {
		// if \n in the text field sendAsync the message
		textInput.textProperty().addListener((obs, oldText, newText) -> {
			if (newText.contains("\n"))
				onSendButtonClick();
		});
		// if visible property of sidebar is changed update to managed property to match
		sidebar.managedProperty().bind(sidebar.visibleProperty());
		chatBox.widthProperty().addListener((observable, oldValue, newValue) -> {
			if (sidebarVisible && chatBox.getPrefWidth() + 10 > newValue.doubleValue()) {
				sidebar.setVisible(false);
				sidebarVisible = false;
			}
		});


		//Changes view when tabs are pressed
		sidebar.getSelectionModel().selectedItemProperty().addListener(
				(observable, oldValue, newValue) -> {
					//Only gif tab
					/*
					if (newValue.getId().equals("gifTab")) {
						showGIFs("", 25, 0, false);
					} else if (newValue.getId().equals("emojiTab")) {
						gifPane.getChildren().clear();
					} else if (newValue.getId().equals("stickerTab")) {
						gifPane.getChildren().clear();
					} else {
						gifPane.getChildren().clear();
					}
					*/
					//showGIFs("", 25, 0, false);
				});
	}

	/**
	 * Searches GIFs on giphy and displays them
	 *
	 * @param searchstring Search gifs with this string, if empty diaplays trending gifs
	 * @param limit        Limits how many gifs are shown
	 * @param offset       Offset from the giflist
	 * @param moreBtn      Boolean if the more Gifs button shall be shown or not
	 */
	public void showGIFs(String searchstring, int limit, int offset, boolean moreBtn) {
		SearchFeed gifFeed = GIFLoader.getGIFUrl(searchstring, limit, offset);
		log.trace("GIF feed size: " + gifFeed.getDataList().size() + " Limit: " + limit + " Offset: " + offset);
		int feedSize = gifFeed.getDataList().size();
		if (feedSize == 0) {
			return;
		}

		int gifSize[] = new int[feedSize];

		gifSize = GIFLoader.calcWidth(gifSize, gifFeed, (int) gifScroll.getWidth(), feedSize, (int) gifPane.getHgap());

		for (int i = 0; i < feedSize; i++) {
			GiphyImage gifImage = gifFeed.getDataList().get(i).getImages().getFixedHeightSmall();
			ImageView gifIV = new ImageView();
			gifIV.setFitWidth(gifSize[i]);
			gifIV.setFitHeight(Double.parseDouble(gifImage.getHeight()));
			gifIV.setId(gifFeed.getDataList().get(i).getImages().getFixedHeight().getUrl());
			int width = Integer.parseInt(gifFeed.getDataList().get(i).getImages().getFixedHeight().getWidth());
			int height = Integer.parseInt(gifFeed.getDataList().get(i).getImages().getFixedHeight().getHeight());
			gifIV.setOnMouseClicked(event -> sendGIF(gifIV.getId(), width, height));
			gifPane.getChildren().add(gifIV);
			gifIV.imageProperty().bind(GIFLoader.loadGIF(gifImage));
		}

		//Test

		ImageView sep = new ImageView("/icons/gifsep.png");
		sep.setFitHeight(0);
		sep.setFitWidth(gifPane.getWidth());
		gifPane.getChildren().add(sep);
		if (moreBtn || (feedSize%25 != 0)) {
			Button moreGif = new Button("more GIFs");
			moreGif.setOnAction(event -> moreGIFs(searchstring, limit, moreGif));
			gifPane.getChildren().add(moreGif);
		}
	}


	public void switchChat(String chatID) {
		reset();
		this.chatID = chatID;
		linkProperties();
	}

	private void reset() {
		currentMessages.itemsProperty().unbind();
		currentMessages.itemsProperty().getValue().clear();
		currentChatName.textProperty().unbind();
		currentChatName.textProperty().set("");
		currentChatUsers.textProperty().unbind();
		currentChatUsers.textProperty().set("");
		textInput.clear();
		chatID = "";
		chatHeader.setVisible(true);
		currentMessages.setVisible(true);
		bottomHBox.setVisible(true);
		startMessage.setVisible(false);
		//gifPane.getChildren().clear();
	}

	/**
	 * Building Update links from UI properties to Manager methods
	 * all links are guaranteed to get updated at a specified interval
	 */
	private void linkProperties() {
		Bindings.bindContent(
				currentMessages.itemsProperty().get(), Manager.getChatMessages(chatID)
		);
		currentChatName.textProperty().bind(Manager.getChatName(chatID));
		currentChatUsers.textProperty().bind(Bindings.concat(
				Manager.getChatMembers(chatID)
		));
	}

	/**
	 * Loads and displays more gifs
	 *
	 * @param searchstring Search query for the gif search
	 * @param limit        Limit the returned gifs
	 * @param button       The more Gifs button
	 */
	private void moreGIFs(String searchstring, int limit, Object button) {
		if (limit < 100) {
			limit = limit + 25;
			gifPane.getChildren().remove(button);
			showGIFs(searchstring, limit, (limit - 25), true);
		} else {
			gifPane.getChildren().remove(button);
		}
	}

	/**
	 * Sends the gif to the chat
	 *
	 * @param url Giphy url
	 */
	private void sendGIF(String url, int width, int height) {
		if(width>500){
			width = 500;
		}
		Manager.addMessage(url, MessageType.GIF, width, height, null);
		log.error("Gif send with h,w,url:" + height + "," + width + "," + url);
	}

	/**
	 * Method to be executed when the sendAsync button is clicked
	 */
	@FXML
	private void onSendButtonClick() {
		String userInput = textInput.getText();
		if (!userInput.trim().isEmpty()) {
			Manager.addMessage(userInput, MessageType.TEXT);
			Platform.runLater(() -> textInput.clear());
		}
	}

	@FXML
	private void onEmojiButtonClick() {
		sidebarVisible = !sidebarVisible;
		sidebar.setVisible(sidebarVisible);
		if(sidebarVisible) {
			gifPane.getChildren().clear();
			showGIFs("", 25, 0, false);
		}
	}

	/**
	 * Method to be executed when the gif search button is clicked
	 */
	@FXML
	private void onGIFButtonClick() {
		gifPane.getChildren().clear();
		String gifSearch = gifText.getText();
		gifPane.getChildren().clear();
		showGIFs(gifSearch, 25, 0, true);
	}

}
