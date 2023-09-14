package model;

import static model.ImageUtil.getMaxValue;

/**
 * Modifies an image by applying a matrix to "sharpen" or "blur".
 */
public class SharpenBlur implements Transform {

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
   * @param transformation : the 2d matrix operation (the kernel) to transform by.
   */
  public SharpenBlur(String file, double[][] transformation, int height,
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
    int mid = this.transformation.length / 2;
    int vCeiling;
    int vFloor;
    int hCeiling;
    int hFloor;
    double rChange;
    double gChange;
    double bChange;
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        vCeiling = this.transformation.length;
        hCeiling = this.transformation[0].length;
        vFloor = 0;
        hFloor = 0;
        //Checking vertical bounds of kernel in the image
        if (i < mid) {
          vFloor = mid - i;
        } else if (i + mid > height - 1) {
          vCeiling = height - i + mid;
        }
        //Checking the horizontal bounds of kernel in the image
        if (j < mid) {
          hFloor = mid - j;
        } else if (j + mid > width - 1) {
          hCeiling = width - j + mid;
        }
        rChange = 0;
        gChange = 0;
        bChange = 0;
        for (int y = vFloor; y < vCeiling; y++) {
          for (int x = hFloor; x < hCeiling; x++) {
            rChange += this.transformation[y][x]
                    * this.image[i - mid + y][j - mid + x].getRed();
            gChange += this.transformation[y][x]
                    * this.image[i - mid + y][j - mid + x].getGreen();
            bChange += this.transformation[y][x]
                    * this.image[i - mid + y][j - mid + x].getBlue();
          }
        }

        int rmax = Math.min(getMaxValue(), (int) rChange);
        if (rmax < 0) {
          rmax = 0;
        }
        int gmax = Math.min(getMaxValue(), (int) gChange);
        if (gmax < 0) {
          gmax = 0;
        }
        int bmax = Math.min(getMaxValue(), (int) bChange);
        if (bmax < 0) {
          bmax = 0;
        }
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
