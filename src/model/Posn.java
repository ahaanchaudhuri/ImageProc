package model;

/**
 * Class representing any 2D Position.
 */
public class Posn {

  private final int xPos;

  private final int yPos;


  /**
   * Contructor for Posn which takes in 2 parameters.
   * @param xPos The X value of the position.
   * @param yPos The Y value of the position.
   */
  public Posn(int xPos,int yPos) {
    this.xPos = xPos;
    this.yPos = yPos;

  }

  /**
   * Returns the X Position.
   * @return the x position.
   */
  public int getXPos() {
    return this.xPos;
  }

  /**
   * Returns the Y Position.
   * @return the y position.
   */
  public int getYPos() {
    return this.yPos;
  }



}
