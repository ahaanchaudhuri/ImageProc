package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SeedsTest {

  Seeds seed1;
  Posn pos1;

  private void init() {
    seed1 = new Seeds(10);
  }

  @Test
  void returnPosn() {
    init();
    assertEquals(10, seed1.returnPosn());
  }

  @Test
  void returnCluster() {
    init();
    List<Integer> list = new ArrayList<>();
    assertEquals(list, seed1.returnCluster());
    seed1.addToCluster(20);
    list.add(20);
    assertEquals(list, seed1.returnCluster());
  }

  @Test
  void addToCluster() {
    init();
    List<Integer> list = new ArrayList<>();
    assertEquals(list, seed1.returnCluster());
    seed1.addToCluster(20);
    list.add(20);
    assertEquals(list, seed1.returnCluster());
  }
}