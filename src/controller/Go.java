package controller;

import model.ImageProcessor;
import model.ImageProcessorImpl;
import view.GUIView;
import view.IView;
import view.GUIViewImpl;

import java.io.IOException;

/**
 * contains the main method, used to run the program.
 */
public class Go {
  /**
   * Main method for running program.
   *
   * @param args arguments.
   * @throws IOException if files are invalid.
   */
  public static void main(String[] args) throws IOException {
    StringBuilder builder = new StringBuilder();
    ImageProcessor model = new ImageProcessorImpl();
    IView simpleView;
    GUIView guiView;
    GUIController guiController;
    PPMReader controller;

    StringBuilder contents = new StringBuilder();

    for (String s : args) {
      contents.append(s);
    }

    // accepts argument from user - GUI
    if (contents.toString().equals("")) {
      guiView = new GUIViewImpl();
      guiController = new GUIControllerImpl(model);
      guiController.setView(guiView);
    }
  }
}

