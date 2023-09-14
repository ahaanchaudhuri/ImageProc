package view;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.OverlayLayout;
import javax.swing.ImageIcon;
import javax.swing.ListSelectionModel;

import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Color;
import java.awt.Container;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import controller.GUIController;
import model.Pixel;

import static java.awt.image.BufferedImage.TYPE_INT_RGB;

/**
 * GUI view implementation that uses JSwing.
 */
public final class GUIViewImpl extends JFrame implements GUIView {
  private JList<String> imageList;
  private JList<String> commandList;
  private JButton saveB;
  private JButton openB;
  private JScrollPane imagePane;
  private JLabel imageLabel;

  private JButton enter;
  private DefaultListModel<String> ilist;
  private JPanel histogramPanel;
  private final JPanel histogram;

  /**
   * Initializes GUIViewImpl.
   */
  public GUIViewImpl() {
    super("Image Processor");
    this.histogram = new JPanel();
    setPreferredSize(new Dimension(1000, 1000));
    setLayout(new BorderLayout());
    setResizable(false);
    pack();
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    addComponentsToPane(getContentPane());
    setVisible(true);
  }

  /**
   * Renders a message.
   *
   * @param message to be transmitted.
   * @throws IllegalStateException if message cannot be rendered.
   */
  @Override
  public void renderMessage(String message) throws IllegalStateException {
    JOptionPane.showMessageDialog(this, message,
            "Message", JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * adds buttons to the view.
   *
   * @param feature of the controller
   */
  @Override
  public void addButtons(GUIController feature) {
    openB.addActionListener(evt -> {
      try {
        feature.loadImage();
      } catch (IOException e) {
        throw new RuntimeException(e);
      }
    });
    saveB.addActionListener(evt -> feature.saveImage(imageList.getSelectedValue()));
    enter.addActionListener(evt -> feature.apply(commandList.getSelectedValue(),
            imageList.getSelectedValue()));
    imageList.addListSelectionListener(evt ->
            feature.displayImage(imageList.getSelectedValue()));


  }

  /**
   * Adds an image to this view so that it can display it through the GUI.
   *
   * @param destinationName : the name of the image
   * @param frequencies     the (value,frequency) table for a histogram.
   */
  @Override
  public void addImage(String destinationName, Pixel[][] image, Map<Integer, int[]> frequencies) {
    this.updateImage(destinationName, image, frequencies);
    if (this.ilist.contains(destinationName)) {
      for (int i = 0; i < this.ilist.size(); i++) {
        if (destinationName.equals(this.ilist.get(i))) {
          this.imageList.setSelectedIndex(i);
        }
      }
    }
    this.imageList.setSelectedIndex(0);
    this.ilist.add(0, destinationName);
  }

  /**
   * Updates the view to display the image.
   *
   * @param imageName   the image to display
   * @param frequencies value,freq used for histogram.
   */
  @Override
  public void updateImage(String imageName, Pixel[][] image, Map<Integer, int[]> frequencies) {
    BufferedImage src = new BufferedImage(image[0].length, image.length, TYPE_INT_RGB);

    for (int i = 0; i < src.getHeight(); i++) {
      for (int j = 0; j < src.getWidth(); j++) {
        Color color = new Color(image[i][j].getRed(), image[i][j].getGreen(),
                image[i][j].getBlue());
        src.setRGB(j, i, color.getRGB());
      }
    }
    ByteArrayOutputStream output = new ByteArrayOutputStream();
    try {
      ImageIO.write(src, "png", output);
    } catch (IOException e) {
      System.out.println("error");
    }

    byte[] arr = output.toByteArray();
    ImageIcon imageIcon = new ImageIcon(arr);
    ImageIcon scaledIcon = imageIcon;

    if (imageIcon.getIconWidth() < 500 || imageIcon.getIconHeight() < 500) {
      double width = (double) imagePane.getWidth() / (double) imageIcon.getIconWidth();
      double height = (double) imagePane.getHeight() / (double) imageIcon.getIconHeight();
      double downsize = Math.min(width, height);
      Dimension d = new Dimension((int) (imageIcon.getIconWidth() * downsize),
              (int) (imageIcon.getIconHeight() * downsize));
      Image image2 = imageIcon.getImage();
      scaledIcon = new ImageIcon(image2.getScaledInstance(d.width, d.height,
              Image.SCALE_SMOOTH));
    }

    this.imageLabel.setIcon(scaledIcon);
    this.updateHistogram(frequencies);
  }

  /**
   * Returns  copy of list of images.
   */
  @Override
  public DefaultListModel<String> copy() {
    DefaultListModel<String> copy = new DefaultListModel<>();
    for (int i = 0; i < this.ilist.getSize(); i++) {
      copy.add(i, this.ilist.get(i));
    }
    return copy;
  }


  /**
   * Adds all components to the view.
   *
   * @param pane : pane for the JSWing image.
   */
  private void addComponentsToPane(Container pane) {
    JPanel mainPanel;
    JPanel imagePanel;
    JPanel bot;
    JPanel userInputPanel;

    //main panel
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setPreferredSize(new Dimension(1800, 1000));

    // image panel
    imagePanel = new JPanel();
    imagePanel.setBackground(new Color(0, 0, 0));
    imagePanel.setOpaque(true);

    imageLabel = new JLabel();
    imagePane = new JScrollPane(imageLabel);
    imagePane.setPreferredSize(new Dimension(1000, 1000));
    imagePanel.add(imagePane);

    mainPanel.add(imagePanel, BorderLayout.CENTER);

    bot = new JPanel();
    bot.setLayout(new BorderLayout());
    userInputPanel = new JPanel();
    userInputPanel.setLayout(new BorderLayout());

    JPanel loadSave = new JPanel();
    loadSave.setLayout(new FlowLayout());
    loadSave.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    // buttons
    openB = new JButton("Open");
    openB.setActionCommand("Open file");
    loadSave.add(openB);
    saveB = new JButton("Save");
    saveB.setActionCommand("Save file");
    loadSave.add(saveB);
    userInputPanel.add(loadSave, BorderLayout.PAGE_START);

    //command selection
    JPanel select = new JPanel();
    select.setLayout(new FlowLayout());
    List<String> commands = new ArrayList<>();
    commands.add("sepia");
    commands.add("greyscale");
    commands.add("sharpen");
    commands.add("blur");
    commands.add("red-component");
    commands.add("green-component");
    commands.add("blue-component");
    commands.add("value-component");
    commands.add("intensity-component");
    commands.add("luma-component");
    commands.add("vertical-flip");
    commands.add("horizontal-flip");
    commands.add("brighten");
    commands.add("mosaic");
    JPanel selImages = new JPanel();
    selImages.setPreferredSize(new Dimension(200, 200));
    selImages.setLayout(new BoxLayout(selImages, BoxLayout.X_AXIS));
    selImages.setBorder(BorderFactory.createTitledBorder("Choose image:"));
    select.add(selImages, BorderLayout.PAGE_START);

    // image list
    ilist = new DefaultListModel<>();
    imageList = new JList<>(ilist);
    imageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selImages.add(new JScrollPane(imageList));
    JPanel selCommands = new JPanel();
    selCommands.setPreferredSize(new Dimension(200, 200));
    selCommands.setLayout(new BoxLayout(selCommands, BoxLayout.X_AXIS));
    selCommands.setBorder(BorderFactory.createTitledBorder("Choose a command:"));
    select.add(selCommands, BorderLayout.PAGE_START);
    DefaultListModel<String> dataForCommandList = new DefaultListModel<>();
    for (String s : commands) {
      dataForCommandList.addElement(s);
    }

    // command list
    commandList = new JList<>(dataForCommandList);
    commandList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    selCommands.add(new JScrollPane(commandList));
    userInputPanel.add(select, BorderLayout.CENTER);

    // more buttons
    enter = new JButton("Enter");
    enter.setActionCommand("Enter Button");
    enter.setPreferredSize(new Dimension(250, 50));
    JPanel end = new JPanel();
    end.setLayout(new FlowLayout());
    end.add(enter);

    userInputPanel.add(end, BorderLayout.PAGE_END);
    bot.add(userInputPanel, BorderLayout.EAST);
    this.createHistogram();
    bot.add(histogramPanel, BorderLayout.CENTER);
    mainPanel.add(bot, BorderLayout.PAGE_END);
    pane.add(mainPanel, BorderLayout.EAST);
  }

  private void createHistogram() {
    histogramPanel = new JPanel();
    histogramPanel.setPreferredSize(new Dimension(400, 300));
    histogramPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    histogramPanel.setLayout(new BorderLayout());
  }

  private void updateHistogram(Map<Integer, int[]> frequencies) {
    histogram.removeAll();
    int[] temp = new int[8];
    temp[0] = frequencies.get(1)[0];
    temp[1] = frequencies.get(1)[0];
    temp[2] = frequencies.get(1)[1];
    temp[3] = frequencies.get(1)[1];
    temp[4] = frequencies.get(1)[2];
    temp[5] = frequencies.get(1)[2];
    temp[6] = frequencies.get(1)[3];
    temp[7] = frequencies.get(1)[3];

    for (int i = 0; i <= 255; i++) {
      for (int j = 0; j <= 7; j += 2) {
        temp[j] = Math.min(temp[j], frequencies.get(i)[j / 2]);
        temp[j] = Math.min(temp[j], frequencies.get(i)[j / 2]);
        temp[j + 1] = Math.max(temp[j + 1], frequencies.get(i)[j / 2]);
      }
    }
    Color[] c = new Color[4];
    c[0] = new Color(255, 0, 0);
    c[1] = new Color(0, 255, 0);
    c[2] = new Color(0, 0, 255);
    c[3] = new Color(0, 0, 0);
    histogram.setLayout(new BoxLayout(histogram, BoxLayout.LINE_AXIS));
    JPanel blank = new JPanel();
    blank.setPreferredSize(new Dimension(80, 0));
    blank.setMaximumSize(new Dimension(80, 0));
    blank.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    blank.setVisible(true);
    histogram.add(blank);
    JPanel line;
    for (int i = 0; i <= 255; i++) {
      line = new JPanel();
      line.setLayout(new OverlayLayout(line));
      JPanel newLine;
      for (int k = 0; k <= 3; k++) {
        int og = frequencies.get(i)[k];
        int size = (int) (((og - temp[2 * k]) / (double) (temp[2 * k + 1] - temp[2 * k])) * 250);
        newLine = new JPanel();
        newLine.setBackground(c[k]);
        newLine.setPreferredSize(new Dimension(1, size));
        newLine.setMaximumSize(new Dimension(1, size));
        newLine.setAlignmentY(Component.BOTTOM_ALIGNMENT);
        newLine.setVisible(true);
        line.add(newLine);
      }
      histogram.add(line);
    }
    histogram.revalidate();
    histogram.repaint();
    histogramPanel.add(histogram, BorderLayout.CENTER);
    JPanel endPage = new JPanel();
    endPage.setSize(new Dimension(400, 10));
    endPage.setVisible(true);
    histogramPanel.add(endPage, BorderLayout.PAGE_END);
    histogramPanel.repaint();
  }
}