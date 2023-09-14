package model;

import static model.ImageUtil.getMaxValue;


/**
 * Transforms an image by working on its rgb values.
 */
public class ColorTransform implements Transform {

  private String file;
  private final double[][] transformation;

  private Pixel[][] changed;

  private int height;
  private int width;

  private Pixel[][] image;

  /**
   * Constructor for the BrightenDarken class.
   *
   * @param file           : The ppm file for the image.
   * @param transformation : the 2d matrix operation to transform by.
   */
  public ColorTransform(String file, double[][] transformation, int height,
                        int width, Pixel[][] image2) {
    this.file = file;
    this.transformation = transformation;
    this.height = height;
    this.width = width;
    image = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(image2[i], 0, image[i], 0, width);
    }
  }

  /**
   * Applies the transformation of the class.
   *
   * @return String that is the PPM equivalent of the image of the transformation.
   */
  @Override
  public Pixel[][] transform() {
    String temp = file;
    changed = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int rChange = (int) ((image[i][j].getRed() * transformation[0][0]) +
                (image[i][j].getGreen() * transformation[0][1]) +
                (image[i][j].getBlue() * transformation[0][2]));
        int gChange = (int) ((image[i][j].getRed() * transformation[1][0]) +
                (image[i][j].getGreen() * transformation[1][1]) +
                (image[i][j].getBlue() * transformation[1][2]));
        int bChange = (int) ((image[i][j].getRed() * transformation[2][0]) +
                (image[i][j].getGreen() * transformation[2][1]) +
                (image[i][j].getBlue() * transformation[2][2]));
        int rmax = Math.min(getMaxValue(), rChange);
        int gmax = Math.min(getMaxValue(), gChange);
        int bmax = Math.min(getMaxValue(), bChange);
        changed[i][j] = new Pixel(rmax, gmax, bmax);
      }
    }


    return changed;
  }

  @Override
  public Pixel[][] getImage() {
    return changed;
  }
}
