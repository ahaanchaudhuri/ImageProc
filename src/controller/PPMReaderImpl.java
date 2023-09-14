package controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

import javax.imageio.ImageIO;

import model.ImageProcessor;
import model.Pixel;
import view.IView;

import static model.ImageUtil.getImage;
import static model.ImageUtil.getLString;
import static model.ImageUtil.readPPM;


/**
 * Implementation of the PPMReader controller. Supports several commands.
 */
public class PPMReaderImpl implements PPMReader {

  private Readable input;
  private IView view;
  private ImageProcessor model;

  Appendable destination;

  /**
   * Constructor for the PPMReader.
   *
   * @param input       : readable input, can be system.in.
   * @param destination : destination to output to.
   * @param model       : model to work with.
   */
  public PPMReaderImpl(Readable input, IView destination, ImageProcessor model) {
    this.input = input;
    this.view = destination;
    this.model = model;
  }

  /**
   * Writes messages to the console.
   *
   * @param message : message to be written to the console.
   * @throws IllegalStateException if the destination is invalid.
   */
  private void writeMessage(String message) throws IllegalStateException, IOException {
    this.view.renderMessage(message);

  }

  /**
   * Loads the given filepath.
   * @param name name of the image
   * @param path filepath
   * @throws IOException if filepath is invalid.
   */
  public void load(String name, String path) throws IOException {
    if (path.contains(".ppm")) {
      readPPM(path);
      model.addImage(name, getImage(), path);
    } else if (path.contains(".png") || path.contains(".jpg") || path.contains(".bmp")) {
      BufferedImage read = ImageIO.read(new FileInputStream(path));
      Pixel[][] temp = new Pixel[read.getHeight()][read.getWidth()];
      for (int i = 0; i < read.getHeight(); i++) {
        for (int j = 0; j < read.getWidth(); j++) {
          Color color = new Color(read.getRGB(i, j));
          int blue = color.getBlue();
          int green = color.getGreen();
          int red = color.getRed();
          temp[j][i] = new Pixel(red, green, blue);
        }
      }
      model.addImage(name, temp, path);
    }
  }

  /**
   * runs the PPM transformer program.
   *
   * @throws IllegalStateException if Illegal input/output detected.
   */
  @Override
  public void run() throws IllegalStateException, IOException {
    Scanner sc = new Scanner(input);
    boolean quit = false;
    while (!quit) { //continue until the user quits
      writeMessage("Type instruction: "); //prompt for the instruction name
      String userInstruction = sc.next(); //take an instruction name
      switch (userInstruction) {
        case "load":
          try {

            String path = sc.next();
            File file = new File(path);
            String name = sc.next();
            load(name, path);
          } catch (IllegalArgumentException | IOException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "horizontal-flip":
          try {
            String name = sc.next();
            String destinationName = sc.next();
            if (model.getFileName(name).contains(".ppm")) {
              readPPM(model.getFileName(name));
              model.flip(name, destinationName, "horizontal", "ppm");
            } else if (model.getFileName(name).contains(".png")) {
              model.flip(name, destinationName, "horizontal", "other");
            }
          } catch (IllegalArgumentException | IOException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "vertical-flip":
          try {
            String name = sc.next();
            String destinationName = sc.next();

            if (model.getFileName(name).contains(".ppm")) {
              readPPM(model.getFileName(name));
              model.flip(name, destinationName, "vertical", "ppm");
            } else if (model.getFileName(name).contains(".png")) {
              model.flip(name, destinationName, "vertical", "other");
            }
          } catch (IllegalArgumentException | IOException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "brighten":
          try {
            int value = Integer.parseInt(sc.next());
            String name = sc.next();
            String destinationName = sc.next();
            if (model.getFileName(name).contains(".ppm")) {
              readPPM(model.getFileName(name));
              model.brighten(value, name, destinationName, "ppm");
            } else if (model.getFileName(name).contains(".png")) {
              model.brighten(value, name, destinationName, "other");
            }
          } catch (IllegalArgumentException | IOException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "mosaic":
          try {
            int value = Integer.parseInt(sc.next());
            String name = sc.next();
            String destinationName = sc.next();
            if (model.getFileName(name).contains(".ppm")) {
              readPPM(model.getFileName(name));
              model.mosaic(value, name, destinationName, "ppm");
            } else if (model.getFileName(name).contains(".png")) {
              model.brighten(value, name, destinationName, "other");
            }
          } catch (IllegalArgumentException | IOException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "component":
          try {
            String type = sc.next();
            String name = sc.next();
            String destinationName = sc.next();
            if (model.getFileName(name).contains(".ppm")) {
              readPPM(model.getFileName(name));
              model.channelSeparate(type, name, destinationName, "ppm");
            } else if (model.getFileName(name).contains(".png")) {
              model.channelSeparate(type, name, destinationName, "other");
            }
          } catch (IllegalArgumentException | IOException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "transform":
          try {
            String type = sc.next();
            String name = sc.next();
            String destinationName = sc.next();
            if (type.equals("sepia")) {
              if (model.getFileName(name).contains(".ppm")) {
                readPPM(model.getFileName(name));
                model.colorTransform(name, destinationName, "ppm");
              } else if (model.getFileName(name).contains(".png")) {
                model.colorTransform(name, destinationName, "other");
              }
            }

          } catch (IllegalArgumentException | IOException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "filter":
          try {
            String type = sc.next();
            String name = sc.next();
            String destinationName = sc.next();
            if (model.getFileName(name).contains(".ppm")) {
              readPPM(model.getFileName(name));
              model.blurSharpen(type, name, destinationName, "ppm");
            } else if (model.getFileName(name).contains(".png")) {
              model.blurSharpen(type, name, destinationName, "other");
            }
          } catch (IllegalArgumentException | IOException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "save":
          try {
            String path = sc.next();
            String name = sc.next();
            String type = sc.next();
            //if (model.getImage(name) != null) {
            //filenames.put(name, path);
            Path dest = Paths.get(path);
            if (type.equals("ppm") || type.equals("txt")) {
              Files.writeString(dest, getLString(model.getImage(name)), StandardCharsets.UTF_8);
            } else if (type.equals("png") || type.equals("bmp") || type.equals("jpg")) {
              File outputFile = new File(path);
              BufferedImage newImage = new BufferedImage(model.getImage(name).length,
                      model.getImage(name)[0].length, BufferedImage.TYPE_INT_RGB);
              for (int i = 0; i < model.getImage(name)[0].length; i++) {
                for (int j = 0; j < model.getImage(name).length; j++) {
                  int r = model.getImage(name)[i][j].getRed();
                  int g = model.getImage(name)[i][j].getGreen();
                  int b = model.getImage(name)[i][j].getBlue();
                  int col = (r << 16) | (g << 8) | b;
                  Color color = new Color(r, g, b);
                  newImage.setRGB(j, i, color.getRGB());
                }
              }
              ImageIO.write(newImage, type, new FileOutputStream(outputFile));
            }
            //}
          } catch (IllegalArgumentException | IOException e) {
            writeMessage("Error: " + e.getMessage() + System.lineSeparator());
          }
          break;
        case "q": //quit
        case "quit": //quit
          quit = true;
          break;
        default: //error due to unrecognized instruction
          writeMessage("Undefined instruction: " + userInstruction + System.lineSeparator());
      }
    }
  }
}
