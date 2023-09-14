package model;


/**
 * class for viewing the separate channels of an image.
 */
public class ChannelSeparate implements Transform {
  private String file;
  private String channel;
  private Pixel[][] channeled;
  private int height;
  private int width;
  private Pixel[][] image;

  /**
   * Constructor for the channelSeparate class.
   *
   * @param file    : input PPM file
   * @param channel : whichever channel wants to be viewed (red, green, blue)
   */
  public ChannelSeparate(String file, String channel, int height, int width, Pixel[][] image2) {
    this.file = file;
    this.channel = channel;
    this.height = height;
    this.width = width;
    image = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(image2[i], 0, image[i], 0, width);
    }
  }

  /**
   * Emphasizes the given channel of the image.
   *
   * @return a String equivalent of the PPM File with the transformation applied.
   */
  @Override
  public Pixel[][] transform() {
    String temp = file;
    channeled = new Pixel[height][width];
    if (channel.equals("red")) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int r = image[i][j].getRed();
          channeled[i][j] = new Pixel(r, r, r);
        }
      }
    } else if (channel.equals("green")) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int g = image[i][j].getGreen();
          channeled[i][j] = new Pixel(g, g, g);
        }
      }
    } else if (channel.equals("blue")) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int b = image[i][j].getBlue();
          channeled[i][j] = new Pixel(b, b, b);
        }
      }
    } else {
      throw new IllegalArgumentException();
    }
    return channeled;
  }

  @Override
  public Pixel[][] getImage() {
    return channeled;
  }
}
