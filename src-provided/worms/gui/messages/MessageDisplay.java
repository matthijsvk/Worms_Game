package worms.gui.messages;

import java.util.LinkedList;

import worms.gui.GUIConstants;

public class MessageDisplay {
	private LinkedList<Message> messages = new LinkedList<Message>();
	private long currentMessageDisplayedSince;

	public MessageDisplay() {
	}

	public void addMessage(String message, MessageType type) {
		Message newMessage = new Message(message, type);
		if (messages.isEmpty() || !messages.getLast().equals(newMessage))
			this.messages.add(newMessage);
	}

	private boolean isDisplayingMessage() {
		return currentMessageDisplayedSince > 0;
	}

	private double currentDisplayTime() {
		return (System.currentTimeMillis() - currentMessageDisplayedSince) / 1000.0;
	}

	private Message currentMessage() {
		return messages.peek();
	}

	private void gotoNextMessage() {
		if (!messages.isEmpty()) {
			currentMessageDisplayedSince = System.currentTimeMillis();
		} else {
			currentMessageDisplayedSince = 0;
		}
	}

	public Message getMessage() {
		if (isDisplayingMessage()) {
			if (currentDisplayTime() >= GUIConstants.MESSAGE_DISPLAY_TIME) {
				messages.remove();
				gotoNextMessage();
			}
		} else {
			gotoNextMessage();
		}
		return currentMessage();
	}
}