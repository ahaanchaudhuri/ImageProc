package model;

import java.io.IOException;

/**
 * class for the vertical flip of an image.
 */
public class VerticalFlip implements Transform {
  private String file;

  private Pixel[][] vFlip;
  int height;
  int width;

  private Pixel[][] image;

  /**
   * constructor for the verticalFlip class.
   *
   * @param file : input PPM file.
   */
  public VerticalFlip(String file, int height, int width, Pixel[][] image2) {
    this.file = file;
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
   * @return a vertically flipped version of this image.
   * @throws IOException if the file is incorrect.
   */
  @Override
  public Pixel[][] transform() {
    vFlip = new Pixel[height][width];
    String temp = file;
    for (int i = height - 1; i >= 0; i--) {
      for (int j = 0; j < width; j++) {
        vFlip[height - i - 1][j] = image[i][j];
      }
    }
    return vFlip;
  }

  @Override
  public Pixel[][] getImage() {
    return vFlip;
  }


}
