package components;

import messaging.Message;
import types.TaskType;

public abstract class Component {
	private TaskType taskType;

	public Component(TaskType taskType) {
		super();
		this.taskType = taskType;
	}

	public TaskType getTaskType() {
		return taskType;
	}

	public void setTaskType(TaskType taskType) {
		this.taskType = taskType;
	}
	
	public abstract Message notify(Message message);
	
}
