package messaging;

import types.TaskType;

public class MessageLoad extends Message {

	private String path;
	
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public MessageLoad(TaskType taskType, String path) {
		super(taskType);
		this.path = path;
	}

}
