package model;

import java.util.Map;

/**
 * Interface for an imageProcessor. Contains methods for transformations, and for
 * operating on an image.
 */
public interface ImageProcessor {


  /**
   * returns an image by the given imageName.
   *
   * @param imageName - name of the image.
   * @return 2d pixel array representation of the image.
   */
  public Pixel[][] getImage(String imageName);

  /**
   * returns the file path associated with an image name.
   *
   * @param imageName - name of the image.
   * @return String filepath of the image.
   */
  public String getFileName(String imageName);

  /**
   * Gets components (rgb) of the image to use in the histogram display.
   *
   * @param name : name of the image.
   * @return Map of rgb components.
   */
  public Map<Integer, int[]> getComponents(String name);

  /**
   * Sharpens or blurs an image.
   *
   * @param type            : sharpen or blur.
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param ftype           : ppm or other file type.
   */
  public void blurSharpen(String type, String name, String destinationName, String ftype);

  /**
   * Sharpens or blurs an image.
   *
   * @param type            : sharpen or blur.
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param ftype           : ppm or other file type.
   */
  public void flip(String name, String destinationName, String type, String ftype);

  /**
   * Brightens or darkens an image.
   *
   * @param amount          : amount to brighten or darken by.
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param ftype           : ppm or other file type.
   */
  public void brighten(int amount, String name, String destinationName, String ftype);

  public void mosaic(int seeds, String name, String destinationName, String ftype);



  /**
   * Transforms an image by applying a color filter (like sepia).
   *
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param ftype           : ppm or other file type.
   */
  public void colorTransform(String name, String destinationName, String ftype);

  /**
   * separates an image into separate channels.
   *
   * @param type            : which type of component to separate into.
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param ftype           : ppm or other file type.
   */
  public void channelSeparate(String type, String name, String destinationName, String ftype);


  /**
   * adds an image to the list of images.
   *
   * @param imageName : name of the image
   * @param image     : 2d pixel array of the image.
   * @param fileName  : filepath of the image.
   */
  public void addImage(String imageName, Pixel[][] image, String fileName);
}
