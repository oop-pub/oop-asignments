package messaging;

public abstract class MessageCenter {
	private String centerName;
		
	public MessageCenter(String centerName) {
		super();
		this.centerName = centerName;
	}

	public Message publish(Message message)	{
		System.out.println(centerName);
		return publishAlgorithm(message);
	}
	
	protected abstract Message publishAlgorithm(Message message);
}
