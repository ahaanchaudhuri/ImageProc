package model;

import java.util.ArrayList;

/**
 * Class representing a singular Seed for Mosaicing.
 */
public class Seeds {

  private final int position;

  private ArrayList<Integer> cluster;


  /**
   * Constructor which takes in the Seeds Position.
   * @param position the position of the Seed.
   */
  public Seeds(int position) {
    this.position = position;
    this.cluster = new ArrayList<>();
  }


  /**
   * Returns the seeds position.
   * @return the seed position.
   */
  public int returnPosn() {
    return this.position;
  }


  /**
   * Returns the cluster a representation of Pixels which are closest to the Seed.
   * @return the cluster of other Pixels which are closest to the Seed.
   */
  public ArrayList<Integer> returnCluster() {
    return this.cluster;
  }

  /**
   * Adds a Pixel position to the cluster of Seeds.
   * @param toAdd the Pixel position to the cluster of Seeds.
   */
  public void addToCluster(int toAdd) {
    cluster.add(toAdd);
  }


}
