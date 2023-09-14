package controller;

import java.io.IOException;

/**
 * General version of PPMReader, open to further implementation.
 */
public interface PPMReader {

  /**
   * runs the PPM transformer program.
   *
   * @throws IllegalStateException if Illegal input/output detected.
   */
  public void run() throws IllegalStateException, IOException;

  void load(String path, String destName) throws IOException;
}
