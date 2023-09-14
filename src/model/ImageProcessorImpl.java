package model;

import java.util.HashMap;
import java.util.Map;

import static model.ImageUtil.getHeight;
import static model.ImageUtil.getWidth;


/**
 * Implementation of the ImageProcessor Interface.
 */
public class ImageProcessorImpl implements ImageProcessor {

  private Map<String, String> filenames;
  private Map<String, Pixel[][]> images;

  public ImageProcessorImpl() {
    this.images = new HashMap<>();
    this.filenames = new HashMap<>();
  }

  /**
   * adds an image to the images hashmap.
   *
   * @param imageName : name of the image
   * @param image     : 2d pixel array of the image.
   * @param fileName  : filepath of the image.
   */
  @Override
  public void addImage(String imageName, Pixel[][] image, String fileName) {
    images.put(imageName, image);
    filenames.put(imageName, fileName);
    System.out.println("added image " + imageName);
  }

  /**
   * returns the image associated with the imagename.
   *
   * @param imageName - name of the image.
   * @return 2d pixel array of image.
   */
  @Override
  public Pixel[][] getImage(String imageName) {
    return images.get(imageName);
  }


  /**
   * returns file path associated with image name.
   *
   * @param name - name of the image.
   * @return String filepath.
   */
  @Override
  public String getFileName(String name) {
    return filenames.get(name);
  }


  /**
   * blurs or sharpens an image.
   *
   * @param type            : sharpen or blur.
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param ftype           : ppm or other file type.
   */
  @Override
  public void blurSharpen(String type, String name, String destinationName, String ftype) {
    double[][] btransformation = new double[3][3];
    btransformation[0][0] = .0625;
    btransformation[0][1] = .125;
    btransformation[0][2] = .0625;
    btransformation[1][0] = .125;
    btransformation[1][1] = .25;
    btransformation[1][2] = .125;
    btransformation[2][0] = .0625;
    btransformation[2][1] = .125;
    btransformation[2][2] = .0625;
    double[][] stransformation = new double[5][5];
    stransformation[0][0] = -.125;
    stransformation[0][1] = -.125;
    stransformation[0][2] = -.125;
    stransformation[0][3] = -.125;
    stransformation[0][4] = -.125;
    stransformation[1][0] = -.125;
    stransformation[1][1] = .25;
    stransformation[1][2] = .25;
    stransformation[1][3] = .25;
    stransformation[1][4] = -.125;
    stransformation[2][0] = -.125;
    stransformation[2][1] = .25;
    stransformation[2][2] = 1;
    stransformation[2][3] = .25;
    stransformation[2][4] = -.125;
    stransformation[3][0] = -.125;
    stransformation[3][1] = .25;
    stransformation[3][2] = .25;
    stransformation[3][3] = .25;
    stransformation[3][4] = -.125;
    stransformation[4][0] = -.125;
    stransformation[4][1] = -.125;
    stransformation[4][2] = -.125;
    stransformation[4][3] = -.125;
    stransformation[4][4] = -.125;

    if (type.equals("blur")) {
      if (ftype.equals("ppm")) {
        Transform blur = new SharpenBlur(filenames.get(name),
                btransformation, getHeight(), getWidth(),
                ImageUtil.getImage());
        images.put(destinationName, blur.transform());
      } else if (ftype.equals("other")) {
        Transform blur = new SharpenBlur(filenames.get(name),
                btransformation, images.get(name)[0].length,
                images.get(name).length, images.get(name));
        images.put(destinationName, blur.transform());
      }
    } else if (type.equals("sharpen")) {
      if (ftype.equals("ppm")) {
        Transform sharp = new SharpenBlur(filenames.get(name),
                stransformation, getHeight(), getWidth(),
                ImageUtil.getImage());
        images.put(destinationName, sharp.transform());
      } else if (ftype.equals("other")) {
        Transform sharp = new SharpenBlur(filenames.get(name),
                stransformation, images.get(name)[0].length,
                images.get(name).length, images.get(name));
        images.put(destinationName, sharp.transform());
      }
    }
  }

  /**
   * flips an image vertically or horizontally.
   *
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param type            : sharpen or blur.
   * @param ftype           : ppm or other file type.
   */
  @Override
  public void flip(String name, String destinationName, String type, String ftype) {
    if (type.equals("vertical")) {
      if (ftype.equals("ppm")) {
        Transform vFlip = new VerticalFlip(filenames.get(name), getHeight(), getWidth(),
                ImageUtil.getImage());
        images.put(destinationName, vFlip.transform());
      } else if (ftype.equals("other")) {
        Transform vFlip = new VerticalFlip(filenames.get(name), images.get(name)[0].length,
                images.get(name).length, images.get(name));
        images.put(destinationName, vFlip.transform());
      }
    } else if (type.equals("horizontal")) {
      if (ftype.equals("ppm")) {
        Transform hFLip = new HorizontalFlip(filenames.get(name), getHeight(), getWidth(),
                ImageUtil.getImage());
        images.put(destinationName, hFLip.transform());
      } else if (ftype.equals("other")) {
        Transform hFlip = new HorizontalFlip(filenames.get(name), images.get(name)[0].length,
                images.get(name).length, images.get(name));
        images.put(destinationName, hFlip.transform());
      }
    }
  }

  /**
   * brightens or darkens an image by the increment given.
   *
   * @param amount          : amount to brighten or darken by.
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param ftype           : ppm or other file type.
   */
  @Override
  public void brighten(int amount, String name, String destinationName, String ftype) {
    if (ftype.equals("ppm")) {
      Transform bd = new BrightenDarken(filenames.get(name), amount,
              getHeight(), getWidth(),
              ImageUtil.getImage());
      images.put(destinationName, bd.transform());
    } else {
      Transform bd = new BrightenDarken(filenames.get(name), amount,
              images.get(name)[0].length,
              images.get(name).length, images.get(name));
      images.put(destinationName, bd.transform());
    }
  }


  @Override
  public void mosaic(int seeds, String name, String destinationName, String ftype) {
    if (ftype.equals("ppm")) {
      Transform bd = new Mosaic(filenames.get(name), seeds,
              getHeight(), getWidth(),
              ImageUtil.getImage());
      images.put(destinationName, bd.transform());
    } else {
      Transform bd = new Mosaic(filenames.get(name), seeds,
              images.get(name)[0].length,
              images.get(name).length, images.get(name));
      images.put(destinationName, bd.transform());
    }
  }

  /**
   * Transforms the color of an image with a filter.
   *
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param ftype           : ppm or other file type.
   */
  @Override
  public void colorTransform(String name, String destinationName, String ftype) {
    double[][] transformation = new double[3][3];
    transformation[0][0] = .393;
    transformation[0][1] = .769;
    transformation[0][2] = .189;
    transformation[1][0] = .349;
    transformation[1][1] = .686;
    transformation[1][2] = .168;
    transformation[2][0] = .272;
    transformation[2][1] = .534;
    transformation[2][2] = .131;
    if (ftype.equals("ppm")) {
      Transform sep = new ColorTransform(filenames.get(name),
              transformation, getHeight(), getWidth(),
              ImageUtil.getImage());
      images.put(destinationName, sep.transform());
    } else {
      Transform sep = new ColorTransform(filenames.get(name),
              transformation, images.get(name)[0].length,
              images.get(name).length, images.get(name));
      images.put(destinationName, sep.transform());
    }
  }

  /**
   * Separates the channels of an image.
   *
   * @param type            : which type of component to separate into.
   * @param name            : name of the image.
   * @param destinationName : new name for the image after the transformation.
   * @param ftype           : ppm or other file type.
   */
  @Override
  public void channelSeparate(String type, String name, String destinationName, String ftype) {
    if (ftype.equals("ppm")) {
      if (type.equals("red") || type.equals("green") || type.equals("blue")) {
        Transform channel = new ChannelSeparate(filenames.get(name), type,
                getHeight(), getWidth(), ImageUtil.getImage());
        images.put(destinationName, channel.transform());
      } else if (type.equals("value") || type.equals("intensity") || type.equals("luma")) {
        Transform grey = new Grayscale(filenames.get(name), type, getHeight(), getWidth(),
                ImageUtil.getImage());
        images.put(destinationName, grey.transform());
      }
    } else {
      if (type.equals("red") || type.equals("green") || type.equals("blue")) {
        Transform channel = new ChannelSeparate(filenames.get(name), type,
                images.get(name)[0].length,
                images.get(name).length, images.get(name));
        images.put(destinationName, channel.transform());
      } else if (type.equals("value") || type.equals("intensity") || type.equals("luma")) {
        Transform grey = new Grayscale(filenames.get(name), type, images.get(name)[0].length,
                images.get(name).length, images.get(name));
        images.put(destinationName, grey.transform());
      }
    }
  }

  /**
   * Finds the components of the image.
   *
   * @param name : name of the image.
   * @return Map of the components.
   */
  @Override
  public Map<Integer, int[]> getComponents(String name) {
    HashMap<Integer, int[]> freq = new HashMap<>();
    for (int k = 0; k <= 255; k++) {
      freq.put(k, new int[]{0, 0, 0, 0});
    }
    Pixel[][] image = this.images.get(name);
    int[] current;
    for (int i = 0; i < image[0].length; i++) {
      for (int j = 0; j < image.length; j++) {
        int r = image[i][j].getRed();
        int g = image[i][j].getGreen();
        int b = image[i][j].getBlue();
        int total = (int) ((r + g + b) / 3.0);
        current = new int[]{r, g, b, total};
        for (int m = 0; m <= 3; m++) {
          int[] temp = freq.get(current[m]);
          temp[m] += 1;
          freq.put(current[m], temp);
        }
      }
    }
    return freq;
  }
}
