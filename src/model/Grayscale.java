package model;


import static model.ImageUtil.getMaxValue;


/**
 * Provides a grayscale of the given image.
 */
public class Grayscale implements Transform {
  private String file;
  private String change;
  private Pixel[][] changed;

  private int height;
  private int width;
  private Pixel[][] image;

  /**
   * constructor for the grayscale class.
   *
   * @param file   : input file
   * @param change : which type of grayscale method to apply.
   */
  public Grayscale(String file, String change, int height, int width, Pixel[][] image2) {
    this.file = file;
    this.change = change;
    this.height = height;
    this.width = width;
    image = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(image2[i], 0, image[i], 0, width);
    }
  }

  /**
   * creates a grayscaled version of this image.
   *
   * @return a String equivalent of the PPM File with the transformation applied.
   */
  @Override
  public Pixel[][] transform() {
    String temp = file;
    changed = new Pixel[height][width];
    if (change.equals("intensity")) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int average = Math.min(getMaxValue(), image[i][j].getRed() +
                  image[i][j].getGreen()
                  + image[i][j].getBlue() / 3);
          changed[i][j] = new Pixel(average, average, average);
        }
      }
    } else if (change.equals("luma")) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          double lumR = .2126 * image[i][j].getRed();
          double lumG = .7152 * image[i][j].getGreen();
          double lumB = .0722 * image[i][j].getBlue();
          int luma = (int) (lumR + lumG + lumB);
          changed[i][j] = new Pixel(luma, luma, luma);
        }
      }
    } else if (change.equals("red")) {
      Transform redSeparate = new ChannelSeparate(file, "red", height, width, image);
      return redSeparate.transform();
    } else if (change.equals("green")) {
      Transform greenSeparate = new ChannelSeparate(file, "green", height, width, image);
      return greenSeparate.transform();
    } else if (change.equals("blue")) {
      Transform blueSeparate = new ChannelSeparate(file, "blue", height, width, image);
      return blueSeparate.transform();
    } else if (change.equals("value")) {
      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          int val = Math.max(image[i][j].getRed(), image[i][j].getGreen());
          int max = Math.max(val, image[i][j].getBlue());

          changed[i][j] = new Pixel(max, max, max);
        }
      }
    } else {
      throw new IllegalArgumentException();
    }
    return changed;
  }

  @Override
  public Pixel[][] getImage() {
    return changed;
  }
}
