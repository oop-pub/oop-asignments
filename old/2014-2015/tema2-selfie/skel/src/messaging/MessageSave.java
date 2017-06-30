package messaging;

import types.TaskType;

public class MessageSave extends MessageImage {
	private String path;
	
	public MessageSave(TaskType taskType, int messageId) {
		super(taskType);
	}

	public MessageSave(TaskType taskType, int[][][] pixels,
			int width, int height, String path) {
		super(taskType, pixels, width, height);
		this.path = path;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
