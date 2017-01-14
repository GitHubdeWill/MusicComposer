package MidiUtil;

import java.awt.Component;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ImageReader extends Component {

  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	BufferedImage image;

public int[][] marchThroughImage() {
    int w = image.getWidth();
    int h = image.getHeight();
    System.out.println("width, height: " + w + ", " + h);

    int[][] p = new int[3][h*w];
    int index = 0;
    for (int i = 0; i < h; i++) {
      for (int j = 0; j < w; j++) {
        System.out.println("x,y: " + j + ", " + i);
        int pixel = image.getRGB(j, i);
        p[0][index] = ((pixel >> 16) & 0xff)%12;
        p[1][index] = ((pixel >> 8) & 0xff)%12;
        p[2][index] = ((pixel) & 0xff)%12;
        index++;
      }
    }
    return p;
  }

  public ImageReader(String file) {
    try {
    	image = 
        ImageIO.read(new FileInputStream(file));
    } catch (IOException e) {
      System.err.println(e.getMessage());
    }
  }
}