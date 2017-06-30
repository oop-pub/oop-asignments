package components;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import messaging.Message;
import messaging.MessageSave;
import messaging.MessageSuccess;
import types.TaskType;

public class ImageSaver extends Component {

	public ImageSaver() {
		super(TaskType.IMAGE_SAVE);
	}

	@Override
	public Message notify(Message message) {
		MessageSave save = (MessageSave) message;
		BufferedImage bufferedImage = new BufferedImage(save.getWidth(), save.getHeight(), BufferedImage.TYPE_3BYTE_BGR);
		int[][][] pixels = save.getPixels();
		for(int i = 0; i < bufferedImage.getHeight(); i++) {
			for(int j = 0; j < bufferedImage.getWidth(); j++) {
				bufferedImage.setRGB(j, i, (pixels[i][j][0] << 16) + (pixels[i][j][1] << 8) + (pixels[i][j][2] << 0));
			}
		}
		
		try {
			ImageIO.write(bufferedImage, "bmp", new File(save.getPath()));
		} catch(IOException e) {}
		
		return new MessageSuccess(null);
	}

}
