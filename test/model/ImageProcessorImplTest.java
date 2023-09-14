package model;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ImageProcessorImplTest {
  Mosaic mosaic;
  Pixel[][] image2;

  ImageProcessorImpl model;

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
    model = new ImageProcessorImpl();
    model.addImage("i", image2, "res/crab.png");
  }

  @Test
  void mosaic() {
    init();

    model.mosaic(1,"i", "i2", "png");


    for (int i = 0; i < 2; i++) {
      for (int j = 0; j < 2; j++) {
        assertEquals(image2[i][j].getBlue(),model.getImage("i2")[i][j].getBlue());
        assertEquals(image2[i][j].getRed(),model.getImage("i2")[i][j].getRed());
        assertEquals(image2[i][j].getGreen(),model.getImage("i2")[i][j].getGreen());
      }
    }
  }
}