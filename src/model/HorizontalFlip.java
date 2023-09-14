package model;


/**
 * Horizontal flip transformation of an image.
 */
public class HorizontalFlip implements Transform {

  private String file;
  private Pixel[][] hFlip;

  private int height;
  private int width;
  private Pixel[][] image;

  /**
   * Constructor for the horizontal flip class.
   *
   * @param file : input PPM file.
   */
  public HorizontalFlip(String file, int height, int width, Pixel[][] image2) {
    this.file = file;
    this.height = height;
    this.width = width;
    image = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(image2[i], 0, image[i], 0, width);
    }
  }

  /**
   * Horizontally flips the image.
   *
   * @return a String equivalent of the PPM File with the transformation applied.
   */
  @Override
  public Pixel[][] transform() {
    String temp = file;
    hFlip = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = width - 1; j >= 0; j--) {
        hFlip[i][width - j - 1] = image[i][j];
      }
    }
    return hFlip;
  }

  @Override
  public Pixel[][] getImage() {
    return hFlip;
  }
}
