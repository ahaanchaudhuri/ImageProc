package controller;

import view.GUIView;

import java.io.IOException;

/**
 * interface for GUI Controller.
 */
public interface GUIController {
  /**
   * Sets view when the program starts.
   *
   * @param view to be displayed.
   */
  public void setView(GUIView view);

  /**
   * Loads an image using the PPMReader Controller.
   *
   * @throws IOException if file issues occur.
   */
  public void loadImage() throws IOException;

  /**
   * Selects and displays an image to the view.
   *
   * @param imageName name of  image to display.
   */
  public void displayImage(String imageName);

  /**
   * applys a command on the currently selected image.
   *
   * @param command   name of the command
   * @param imageName name of the image
   */
  public void apply(String command, String imageName);

  /**
   * Save the currently selected image.
   *
   * @param imageName image selected to be saved
   */
  public void saveImage(String imageName);


}