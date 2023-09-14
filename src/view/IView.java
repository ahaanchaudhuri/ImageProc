package view;

/**
 * Used to view output.
 */
public interface IView {

  /**
   * Renders a message.
   *
   * @param message to be transmitted.
   * @throws IllegalStateException when the message could not be rendered.
   */
  public void renderMessage(String message) throws IllegalStateException;

}