package components;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;

import javax.imageio.ImageIO;

import messaging.Message;
import messaging.MessageImage;
import messaging.MessageLoad;
import types.TaskType;

public class ImageLoader extends Component {

	public ImageLoader() {
		super(TaskType.IMAGE_LOAD);
	}

	@Override
	public Message notify(Message message) {
		MessageLoad load = (MessageLoad) message;
		try {
			BufferedImage bufferedImage = ImageIO.read(new File(load.getPath()));
			MessageImage messageImage = new MessageImage(null);
			
			byte[] pixelsByte = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
			int index = 0;
			int[][][] pixels = new int[bufferedImage.getHeight()][bufferedImage.getWidth()][3];
			
			messageImage.setHeight(bufferedImage.getHeight());
			messageImage.setWidth(bufferedImage.getWidth());
			messageImage.setPixels(pixels);
			
			for(int i = 0; i < bufferedImage.getHeight(); i++) {
				for(int j = 0; j < bufferedImage.getWidth(); j++) {
					for(int k = 0; k < 3; k++) {
						pixels[i][j][2-k] = ((int)pixelsByte[index++] + 256) % 256;
					}
				}
				
			}
			return messageImage;
		}
		catch(Exception e) {
			System.err.println("File not found!");
			return new MessageImage(null); 
		}
		
	}

}
