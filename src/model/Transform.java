package model;

import java.io.IOException;

/**
 * General version of any image transformation.
 */
public interface Transform {


  /**
   * Applies the transformation of the class.
   *
   * @return String that is the PPM equivalent of the image of the transformation.
   * @throws IOException if there is a file error.
   */
  Pixel[][] transform();

  /**
   * returns this image (2d pixel).
   * @return this image (2d pixel).
   */
  Pixel[][] getImage();
}
