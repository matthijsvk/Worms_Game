package worms.gui.messages;

public class Message {
	private final String message;
	private final MessageType type;

	public Message(String message, MessageType type) {
		this.message = message;
		this.type = type;
	}
	
	public String getText() {
		return message;
	}
	public MessageType getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((message == null) ? 0 : message.hashCode());
		result = prime * result
				+ ((type == null) ? 0 : type.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Message other = (Message) obj;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (type != other.type)
			return false;
		return true;
	}
	
	
}