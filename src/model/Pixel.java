package model;

/**
 * Class to represent a pixel of an image.
 */
public class Pixel {
  private int red;
  private int green;
  private int blue;

  /**
   * Constructor for the pixel class.
   *
   * @param red   : the amount of red (from a scale of 0 to some integer maximum.)
   * @param green : the amount of green (from a scale of 0 to some integer maximum.)
   * @param blue  : the amount of blue (from a scale of 0 to some integer maximum.)
   *              all must be integers >= 0.
   */
  public Pixel(int red, int green, int blue) {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("invalid components.");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * method to return the value of red for this pixel.
   *
   * @return integer value of red.
   */
  public int getRed() {
    return red;
  }

  /**
   * method to return the value of green for this pixel.
   *
   * @return integer value of green.
   */
  public int getGreen() {
    return green;
  }

  /**
   * method to return the value of blue for this pixel.
   *
   * @return integer value of blue.
   */
  public int getBlue() {
    return blue;
  }

  /**
   * method to return this pixel as a string.
   *
   * @return String formatted version of this pixel.
   */
  public String toString() {
    return "" + red + " " + green + " " + blue + " ";
  }
}
