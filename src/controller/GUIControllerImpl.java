package controller;

import model.ImageProcessor;
import model.Pixel;
import view.GUIView;
import view.ViewImpl;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.Component;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.StringReader;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;
import static model.ImageUtil.getLString;

/**
 * Implementation of the GUIController interface.
 */
public class GUIControllerImpl implements GUIController {

  private final ImageProcessor model;
  private GUIView view;
  private PPMReader controller;

  public GUIControllerImpl(ImageProcessor model) {
    this.model = model;
    this.controller = new PPMReaderImpl(new StringReader(""), new ViewImpl(), model);
  }

  @Override
  public void loadImage() throws IOException {
    JFileChooser file = new JFileChooser("res");
    file.setFileFilter(new FileNameExtensionFilter("Images", "jpg",
            "ppm", "bpm", "png"));

    if (file.showOpenDialog((Component) this.view) == JFileChooser.APPROVE_OPTION) {
      File f = file.getSelectedFile();
      String destinationName = JOptionPane.showInputDialog("name: ");
      if (destinationName.equals("")) {
        this.view.renderMessage("Need to name file");
        return;
      }
      String path = f.getAbsolutePath();
      this.controller.load(destinationName, path);
      Pixel[][] image = this.model.getImage(destinationName);
      this.view.addImage(destinationName, image, this.model.getComponents(destinationName));
    }
  }

  @Override
  public void displayImage(String imageName) {
    Pixel[][] image = this.model.getImage(imageName);
    this.view.updateImage(imageName, image, this.model.getComponents(imageName));
  }

  @Override
  public void setView(GUIView view) {
    this.view = view;
    view.addButtons(this);
  }

  @Override
  public void saveImage(String imageName) {
    Objects.requireNonNull(imageName);
    DefaultListModel<String> vals = this.view.copy();
    if (vals.getSize() == 0) {
      this.view.renderMessage("No images to save.");
      return;
    }
    JFileChooser fileChooser = new JFileChooser("res");
    int response = fileChooser.showSaveDialog((Component) this.view);
    fileChooser.setFileFilter(new FileNameExtensionFilter(
            "Images", "jpg", "png", "bmp", "ppm"));
    if (response == JFileChooser.CANCEL_OPTION) {
      return;
    }
    if (response == JFileChooser.APPROVE_OPTION) {
      File f = fileChooser.getSelectedFile();
      String destinationPath = f.getAbsolutePath();
      Pixel[][] image = this.model.getImage(imageName);
      File fileOutput = new File(destinationPath);
      if (f.getAbsolutePath().endsWith(".ppm")) {
        Path dest = Paths.get(destinationPath);
        try {
          Files.writeString(dest, getLString(image), StandardCharsets.UTF_8);
        } catch (IOException e) {
          throw new IllegalArgumentException("File save failed");
        }
        return;
      }
      BufferedImage src = new BufferedImage(image.length, image[0].length, TYPE_INT_RGB);
      for (int i = 0; i < src.getHeight(); i++) {
        for (int j = 0; j < src.getWidth(); j++) {
          Color color = new Color(image[i][j].getRed(), image[i][j].getGreen(),
                  image[i][j].getBlue());
          src.setRGB(j, i, color.getRGB());
        }
      }
      try {
        ImageIO.write(src,
                destinationPath.substring(destinationPath.length() - 3), new
                        FileOutputStream(fileOutput));
      } catch (IOException e) {
        return;
      }

    }

    this.view.renderMessage("saved.");
  }

  @Override
  public void apply(String command, String imageName) {
    Objects.requireNonNull(command);
    Objects.requireNonNull(imageName);
    String destinationImage = JOptionPane.showInputDialog("Save as: ");

    if (destinationImage == null) {
      this.view.renderMessage("invalid");
      return;
    }

    int inc = 0;
    if (command.equals("brighten")) {
      String increment = JOptionPane.showInputDialog("Increment By: ");
      try {
        inc = Integer.parseInt(increment);
      } catch (IllegalArgumentException e) {
        return;
      }
    }
    if (command.equals("mosaic")) {
      String increment = JOptionPane.showInputDialog("NumSeeds: ");
      try {
        inc = Integer.parseInt(increment);
        if (inc < 0) {
          this.view.renderMessage("must be a non-negative number");
        }
      } catch (IllegalArgumentException e) {
        this.view.renderMessage("must be a non-negative number");
      }
    }
    switch (command) {
      case "vertical-flip":
        this.model.flip(imageName, destinationImage, "vertical", "other");
        break;
      case "horizontal-flip":
        this.model.flip(imageName, destinationImage, "horizontal", "other");
        break;
      case "mosaic":
        this.model.mosaic(inc, imageName, destinationImage, "other");
        break;
      case "brighten":
        this.model.brighten(inc, imageName, destinationImage, "other");
        break;
      case "sepia":
        this.model.colorTransform(imageName, destinationImage, "other");
        break;
      case "red-component":
        this.model.channelSeparate("red", imageName, destinationImage, "other");
        break;
      case "green-component":
        this.model.channelSeparate("green", imageName, destinationImage, "other");
        break;
      case "blue-component":
        this.model.channelSeparate("blue", imageName, destinationImage, "other");
        break;
      case "value-component":
        this.model.channelSeparate("value", imageName, destinationImage, "other");
        break;
      case "intensity-component":
        this.model.channelSeparate("intensity", imageName, destinationImage, "other");
        break;
      case "luma-component":
        this.model.channelSeparate("luma", imageName, destinationImage, "other");
        break;
      case "blur":
        this.model.blurSharpen("blur", imageName, destinationImage, "other");
        break;
      case "sharpen":
        this.model.blurSharpen("sharpen", imageName, destinationImage, "blur");
        break;
      default:
        return;
    }
    Pixel[][] image = this.model.getImage(destinationImage);
    this.view.addImage(destinationImage, image, this.model.getComponents(destinationImage));
  }
}
