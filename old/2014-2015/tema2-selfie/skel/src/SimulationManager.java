import messaging.MessageCenter;


public class SimulationManager {
	private MessageCenter messageCenter;
	
	public SimulationManager(String networkConfigFile) {
		this.messageCenter = buildNetwork(networkConfigFile);
	}
	
	
	/**
	 * Builds the network of message centers.
	 * @param networkConfigFile configuration file
	 * @return the first message center from the config file
	 */
	private MessageCenter buildNetwork(String networkConfigFile) {
		return null;
	}
	
	
	/**
	 * Reads the commands from stdin and uses the messageCenter to solve all the tasks
	 */
	public void start() {
		
		/* 
		 * Example of usage when the MessageCenter will be implemented *
		 
		MessageLoad load = new MessageLoad(TaskType.IMAGE_LOAD, "image_input.jpg");
		MessageImage image = (MessageImage)this.messageCenter.publish(load);
		
		image.generateId(); //pentru ca utilizam acelasi mesaj image trebuie sa-i generam un nou id
		
		MessageSave save = new MessageSave(TaskType.IMAGE_SAVE, 
					image.getPixels(), image.getWidth(), image.getHeight(), 
					destFile);
		MessageSuccess success = (MessageSuccess)this.messageCenter.publish(save);
		
		
		*/
		
	}
	
	
	/**
	 * Main method
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		if(args.length != 1) {
			System.out.println("Usage: java SimulationManager <network_config_file>");
			return;
		}
		SimulationManager simulationManager = new SimulationManager(args[0]);
		simulationManager.start();
	}

}
