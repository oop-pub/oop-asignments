package messaging;

import types.TaskType;

public class MessageImage extends Message {

	private int[][][] pixels;
	private int width, height;
	
	public MessageImage(TaskType taskType, int[][][] pixels,
			int width, int height) {
		super(taskType);
		this.pixels = pixels;
		this.width = width;
		this.height = height;
	}
	
	public MessageImage(TaskType taskType) {
		super(taskType);
	}

	public int[][][] getPixels() {
		return pixels;
	}

	public void setPixels(int[][][] pixels) {
		this.pixels = pixels;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}
}
