package Chatr.Converstation;

public class Message {
	private String sender;
	private String content;
	private boolean isEmpty = false;
	// timestamp is always guaranteed to be set
	private final long timestamp = System.currentTimeMillis();

	public Message() {
		isEmpty = true;
	}

	public Message(String sender, String content) {
		this.sender = sender;

		this.content = content;
	}

	public String getContent() {
		return this.content;
	}

	public String getSender() {
		return this.sender;
	}

	public long getTime() {
		return this.timestamp;
	}

	public boolean isEmpty() {
		return isEmpty;
	}

	// make debug printing easier
	@Override
	public String toString() {
		return String.format("%s | %5s | %s", timestamp, sender, content);
	}
}
