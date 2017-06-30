import types.FlashType;

import components.Component;

/**
 * Optional class for building instances based on a string
 *
 */
public class Factory {
	private static Factory instance = new Factory();
	private Factory(){}
	
	public static Factory createFactory() {
		return instance;
	}
	
	public Component getComponent(String name) {
		//TODO: optional
		
		return null;
	}
	
	public FlashType getFlashType(String string) {
		//TODO: optional

		return null;
	}
	
}
