package model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PosnTest {
  Posn pos1;

  private void init() {
    pos1 = new Posn(10, 10);
  }

  @Test
  void getXPos() {
    init();
    assertEquals(10, pos1.getXPos());

  }

  @Test
  void getYPos() {
    init();
    assertEquals(10, pos1.getYPos());
  }
}