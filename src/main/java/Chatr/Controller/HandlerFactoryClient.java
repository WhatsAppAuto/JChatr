package Chatr.Controller;

import Chatr.Helper.Enums.Request;
import Chatr.Model.Chat;
import Chatr.Model.Message;
import Chatr.Model.User;
import Chatr.Server.Transmission;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;

import java.util.Set;
import java.util.function.Predicate;

/**
 * Created by haags on 19.06.17.
 */
public class HandlerFactoryClient {

    public static HandlerClient getInstance(Request requestType){
        switch (requestType){
            case MESSAGE:
                return new MessageHandler(Manager.userChats);

            case CONVERSATION:
                return new ConversationHandler(Manager.userChats);

            case USER:
                return new UserHandler(Manager.users);

        }

        return null;
    }


    private static class MessageHandler extends HandlerClient{

        MessageHandler(ListProperty<Chat> userchats){
                super.userChats= userchats;
        }

        @Override
        public void processClient(Transmission t) {
            switch (t.getCRUD()){
                case CREATE: {
                    Platform.runLater(()->{
                        super.userChats.forEach(
                                chat -> {
                                    if (chat.getID().get().equals(t.getConversationID())) {
                                        chat.addMessage(t.getMessage());
                                    }
                                }
                        );
                    });

                    break;
                }

                case UPDATE:{
                    //No clear implementation of Update
                }
            }
        }

    }

    private static  class ConversationHandler extends HandlerClient{
        ConversationHandler(ListProperty<Chat> userchats){
            super.userChats = userchats;
        }
        @Override
        public void processClient(Transmission t) {
            switch (t.getCRUD()){
                case CREATE: {
                    super.userChats.add(t.getChat());
                }
                break;

                case UPDATE: {
                    //No clear implementation of Update
                }
                break;

                case DELETE: {
                    super.userChats.removeIf(p -> p.getID().get().equals(t.getConversationID()));
                }
                break;
            }
        }
    }

    private static class UserHandler extends HandlerClient{
        UserHandler(ListProperty<User> users){
            super.users= users;
        }
        @Override
        public void processClient(Transmission t) {
            switch (t.getCRUD()){
                case CREATE:
                    //See LOGIN in Client
                    break;
                case READ:
                    //No clear implementation of READ
                    break;

                case UPDATE:{
                    super.users.forEach(user -> {
                        if(user.getUserID()==t.getUserID()){
                            User u= t.getUser();
                            user.setUserName(u.getUserName()).setEmail(u.getEmail());
                        }

                    });
                }
            }
        }
    }
}






