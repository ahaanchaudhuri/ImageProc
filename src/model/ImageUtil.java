package model;


import java.io.IOException;
import java.util.Scanner;
import java.io.FileNotFoundException;
import java.io.FileInputStream;


/**
 * This class contains utility methods to read a PPM image from file and simply print its contents.
 * Feel free to change this method
 * as required.
 */
public class ImageUtil {

  /**
   * Read an image file in the PPM format and print the colors.
   *
   * @param filename the path of the file.
   */
  private static int width;
  private static int height;
  private static Pixel[][] image;

  private static int maxValue;

  /**
   * Reads a ppm file.
   *
   * @param filename filepath of the file.
   * @throws IOException if the file is invalid.
   */
  public static void readPPM(String filename) throws IOException {
    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filename));
    } catch (FileNotFoundException e) {
      System.out.println("File " + filename + " not found!");
      return;
    }
    StringBuilder builder = new StringBuilder();

    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s + System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;


    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    width = sc.nextInt();
    System.out.println("Width of image: " + width);
    height = sc.nextInt();
    System.out.println("Height of image: " + height);
    maxValue = sc.nextInt();
    //System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);
    image = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();
        Pixel thisPixel = new Pixel(r, g, b);
        //System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);
        image[i][j] = thisPixel;
      }
    }
  }

  /**
   * Maximum value of the image's channels.
   *
   * @return integer aforementioned max value.
   */
  public static int getMaxValue() {
    return (int) 255;
  }

  /**
   * width of the image.
   *
   * @return the width of the image as an integer
   */
  public static int getWidth() {
    return width;
  }

  /**
   * height of the image.
   *
   * @return the height of the image as an integer
   */
  public static int getHeight() {
    return height;
  }

  /**
   * String version of an array.
   *
   * @param transformed : 2d pixel array representing an image.
   * @return String version of a given 2d pixel array.
   */
  public static String getLString(Pixel[][] transformed) {
    StringBuilder flipped = new StringBuilder();
    flipped.append("P3");
    flipped.append(System.getProperty("line.separator"));
    flipped.append("" + getWidth());
    flipped.append(" " + getHeight());
    flipped.append(System.getProperty("line.separator"));
    flipped.append("" + 255);
    flipped.append(System.getProperty("line.separator"));
    for (int i = 0; i < getHeight(); i++) {
      for (int j = 0; j < getWidth(); j++) {
        flipped.append(transformed[i][j].toString());
      }
    }
    return flipped.toString();
  }

  /**
   * Returns a copy of the original image as a 2d pixel array.
   *
   * @return 2d pixel array representation of the image.
   */
  public static Pixel[][] getImage() {
    Pixel[][] returned = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        returned[i][j] = image[i][j];
      }
    }
    return returned;
  }


}

