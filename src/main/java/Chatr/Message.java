package Chatr;

public class Message
{
    private String sender;
    private String recipient;
    private String content;
    boolean isEmpty = false;

    public Message (){
        isEmpty = true;
    }

    public Message (String sender, String recipient, String content) {
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
    }

    public String getContent() {
        return this.content;
    }
    public String getSender(){
        return this.sender;
    }

    public String getRecipient(){
        return this.recipient;
    }

    public boolean isEmpty() {
        return isEmpty;
    }

}
