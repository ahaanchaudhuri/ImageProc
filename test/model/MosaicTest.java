package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MosaicTest {

  Mosaic mosaic;
  Pixel[][] image2;

  private void init() {
    image2 = new Pixel[2][2];
    ArrayList<Seeds> seeds = new ArrayList<>();
    Seeds seed1 = new Seeds(1);
    seeds.add(seed1);
    image2[0][0] = new Pixel(10,10,10);
    image2[0][1] = new Pixel(10,10,10);
    image2[1][0] = new Pixel(10,10,10);
    image2[1][1] = new Pixel(10,10,10);
    mosaic = new Mosaic("file", 1, 2, 2, image2, seeds);
  }

  @Test
  void transform() {
    init();


    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(image2[i][j].getBlue(),mosaic.transform()[i][j].getBlue());
        assertEquals(image2[i][j].getRed(),mosaic.transform()[0][0].getRed());
        assertEquals(image2[i][j].getGreen(),mosaic.transform()[0][0].getGreen());
      }
    }

  }

  @Test
  void getImage() {

    init();
    mosaic.transform();
    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(image2[i][j].getBlue(),mosaic.getImage()[i][j].getBlue());
        assertEquals(image2[i][j].getRed(),mosaic.getImage()[0][0].getRed());
        assertEquals(image2[i][j].getGreen(),mosaic.getImage()[0][0].getGreen());
      }
    }

  }
}