package view;

import java.util.Map;

import javax.swing.DefaultListModel;

import model.Pixel;
import controller.GUIController;

/**
 * View interface for a GUI.
 */
public interface GUIView extends IView {

  /**
   * Adds an image to the view.
   *
   * @param destinationName : name of image.
   * @param frequencies     value, frequency for histogram.
   * @param image           : 2d pixel image to be saved.
   */
  public void addImage(String destinationName, Pixel[][] image, Map<Integer, int[]> frequencies);

  /**
   * adds features to the view.
   *
   * @param feature - feature to be added.
   */
  public void addButtons(GUIController feature);


  /**
   * Updates the view to display  current image.
   *
   * @param imageName   : the image to display
   * @param frequencies : value, freq for histogram.
   */
  public void updateImage(String imageName, Pixel[][] image, Map<Integer, int[]> frequencies);

  /**
   * copies current list of images.
   *
   * @return copy of list of images loaded in the program
   */
  public DefaultListModel<String> copy();

}