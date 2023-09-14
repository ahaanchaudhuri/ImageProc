package view;

/**
 * A Blank View which is has no function other than as a placeholder in a controller delegate
 * that is used for the GUI.
 */
public class ViewImpl implements IView {

  /**
   * Render the given message.
   *
   * @param message to be rendered.
   * @throws IllegalStateException if render was unsuccessful.
   */
  @Override
  public void renderMessage(String message) throws IllegalStateException {
    System.out.print(message);
  }

}
