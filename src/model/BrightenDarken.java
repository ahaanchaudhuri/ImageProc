package model;

import model.Pixel;
import model.Transform;

import java.io.IOException;


/**
 * Transformation to brighten or darken an image.
 */
public class BrightenDarken implements Transform {

  private String file;
  private int change;

  private Pixel[][] changed;

  private int height;
  private int width;
  private Pixel[][] image;

  /**
   * Constructor for the BrightenDarken class.
   *
   * @param file   : The ppm file for the image.
   * @param change : the amount to brighten/darken by.
   */
  public BrightenDarken(String file, int change, int height, int width, Pixel[][] image2) {
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
   * Brightens or darkens the image.
   *
   * @return a String equivalent of the PPM File with the transformation applied.
   * @throws IOException if the file is incorrect.
   */
  @Override
  public Pixel[][] transform() {
    String temp = file;
    changed = new Pixel[height][width];
    int max = 255;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        if (change >= 0) {
          changed[i][j] = new Pixel(Math.min(max, image[i][j].getRed() + change),
                  Math.min(max, image[i][j].getGreen() + change),
                  Math.min(max, image[i][j].getBlue() + change));
        } else {
          changed[i][j] = new Pixel(Math.max(0, image[i][j].getRed() + change),
                  Math.max(0, image[i][j].getGreen() + change),
                  Math.max(0, image[i][j].getBlue() + change));
        }
      }
    }
    return changed;
  }

  @Override
  public Pixel[][] getImage() {
    return changed;
  }
}
