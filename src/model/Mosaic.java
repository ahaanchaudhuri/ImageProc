package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Transformation to Mosaic an image.
 */
public class Mosaic implements Transform {

  private String file;

  private Pixel[][] changed;

  private int height;
  private int width;
  private int seeds;

  private Map<Integer, Posn> map = new HashMap<>();

  private ArrayList<Seeds> seedList;

  private Pixel[][] image;

  /**
   * Constructor for the Mosaic class that takes in 5 parameters.
   * @param file PPM file for the mosaic.
   * @param seeds the sumber of seeds for the mosaic to have
   * @param height height of the image
   * @param width width of the image
   * @param image2 the image.
   */
  public Mosaic(String file, int seeds, int height, int width, Pixel[][] image2) {
    this.file = file;
    if (seeds < 0) {
      throw new IllegalArgumentException("no negative seeds");
    }
    else {
      this.seeds = seeds;
    }
    this.height = height;
    this.width = width;
    this.seedList = new ArrayList<>();
    image = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(image2[i], 0, image[i], 0, width);
    }
    this.initializeMap();
    this.initializeSeedArray();
  }


  /**
   * Mock Constructor for the Mosaic class that takes in 6 parameters including the
   * seeds for Mosaic.
   * @param file PPM file for the mosaic.
   * @param seeds the sumber of seeds for the mosaic to have
   * @param height height of the image
   * @param width width of the image
   * @param image2 the image.
   * @param seedList the seeds for mosaic.
   */
  public Mosaic(String file, int seeds, int height, int width, Pixel[][] image2,
                ArrayList<Seeds> seedList) {
    this.file = file;
    this.seeds = seeds;
    this.height = height;
    this.width = width;
    this.seedList = seedList;
    image = new Pixel[height][width];
    for (int i = 0; i < height; i++) {
      System.arraycopy(image2[i], 0, image[i], 0, width);
    }
    this.initializeMap();
    this.initializeSeedArray();
  }

  private void initializeMap() {
    int height = this.image.length;
    int width = this.image[0].length;
    int counter = 0;

    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        map.put(counter, new Posn(row, col));
        counter += 1;
      }
    }
  }

  private void initializeSeedArray() {
    Random random = new Random();

    int height = this.image.length;
    int width = this.image[0].length;
    int totalPixels = width * height;
    int temp;



    for (int i = 0; i < this.seeds; i++) {
      boolean flag = false;
      while (!flag) {
        Seeds tempSeed;
        temp = random.nextInt(totalPixels);
        tempSeed = new Seeds(temp);
        if (!seedList.contains(tempSeed)) {
          seedList.add(tempSeed);
          flag = true;
          break;
        }
        else {
          flag = false;
        }
      }
    }
  }

  private double findDistance(Posn posn1, Posn posn2) {
    double diffX = posn2.getXPos() - posn1.getXPos();
    double diffY = posn2.getYPos() - posn1.getYPos();

    double d = Math.sqrt((diffX * diffX) + (diffY * diffY));
    return d;
  }


  private Pixel posToPixel(Posn pos) {
    Pixel pixel = this.image[pos.getXPos()][pos.getYPos()];
    return pixel;
  }

  private Seeds closestSeed(Posn pos) {
    Posn returnPos = new Posn(-1,-1);
    double currDist = 10000000.0;
    Seeds returnSeed = null;

    for (int i = 0; i < seedList.size(); i++) {
      Seeds currentSeed = seedList.get(i);
      int seedPos = currentSeed.returnPosn();
      double distBetween = this.findDistance(pos, map.get(seedPos));
      if (distBetween < currDist) {
        currDist = distBetween;
        returnPos = map.get(seedPos);
        returnSeed = currentSeed;
      }
    }
    return returnSeed;
  }

  private Pixel getAverageCluster(Seeds seed) {
    ArrayList<Integer> cluster = seed.returnCluster();
    int clusterLength = cluster.size();

    int totalRed = 0;
    int totalBlue = 0;
    int totalGreen = 0;

    for (int e : cluster) {
      Posn seedPos = map.get(e);
      Pixel pixel = image[seedPos.getXPos()][seedPos.getYPos()];

      totalRed += pixel.getRed();
      totalBlue += pixel.getBlue();
      totalGreen += pixel.getGreen();
    }

    return new Pixel(totalRed / clusterLength, totalGreen / clusterLength,
            totalBlue / clusterLength);

  }


  /**
   * Mosaics the image in the class.
   * @return A new mosaiced array of Pixels.
   */
  public Pixel[][] transform() {
    changed = this.image;
    int totalPixels = width * height;

    System.out.println(totalPixels);
    for (int i = 0; i < totalPixels; i++) {
      Posn pixelPos = map.get(i);
      // Posn closestSeed = this.closestSeed(pixelPos);
      Seeds closestSeed = this.closestSeed(pixelPos);
      closestSeed.addToCluster(i);
    }

    for (Seeds e : this.seedList) {
      for (int i : e.returnCluster()) {
        Posn pos = map.get(i);
        changed[pos.getXPos()][pos.getYPos()] = this.getAverageCluster(e);
      }
    }
    return changed;
  }


  @Override
  public Pixel[][] getImage() {
    return changed;
  }
}
