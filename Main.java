import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.*;
import java.awt.event.*;

import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


class Main {
  private JFrame      j;
  private JMenu       jmenu;
  private JMenuBar    jbar;
  private JMenuItem   jmi, jexit;
  private JPanel      container, jpanel, jpanelbar, jPanelModified;
  private JButton     jpre, jnext;
  JLabel              image, modified;
  ImageIcon           ic;
  Image               img;

  Main() {
    j = new JFrame("Image Viewer");
    j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    j.setPreferredSize(new Dimension(1100, 600));
    j.setLayout(new GridBagLayout());

    jpanel = new JPanel();
    jpanel.setLayout(new BorderLayout());
    image = new JLabel("");
    modified = new JLabel("");
    

    jPanelModified = new JPanel();

    jPanelModified.add(modified, BorderLayout.CENTER);
    jpanel.add(image, BorderLayout.CENTER);

    container = new JPanel();
    container.setLayout(new GridLayout(1,2));
    container.add(jpanel);
    container.add(jPanelModified);

    j.add(container);

    jpanelbar = new JPanel();
    jpanelbar.setLayout(new GridBagLayout());
    jpanelbar.setBackground(Color.red);

    // Creating Menu
    jbar = new JMenuBar();
    jmenu = new JMenu("File");
    jmi = new JMenuItem("Open");
    jmi.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();
          try {

            Image test = ImageIO.read(file);
           
            Image scaledImage = test.getScaledInstance(500,500,Image.SCALE_SMOOTH);
            BufferedImage tmp = toBufferedImage(scaledImage);

            image.setIcon(new ImageIcon(scaledImage));
            modified.setIcon(new ImageIcon(scaledImage));

/*            try{
              Thread.sleep(3000);
            }catch(InterruptedException ex){
              Thread.currentThread().interrupt();
            }*/

            for(int i = 0; i < tmp.getWidth(); i++){
              for(int j = 0; j < tmp.getHeight(); j++){
                int argb = tmp.getRGB(i,j);
                int rgb[] = new int[] {
                  (argb >> 16) & 0xff, //red
                  (argb >>  8) & 0xff, //green
                  (argb      ) & 0xff  //blue
                };

                Color nuevo = new Color(rgb[0],rgb[0],rgb[0]);
                int rgbNuevo = nuevo.getRGB();
                tmp.setRGB(i, j, rgbNuevo);
              }
            }


            modified.setIcon(new ImageIcon(tmp));


          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    });



    jexit = new JMenuItem("Exit");
    jexit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        System.exit(0);
      }
    });
    jmenu.add(jmi);
    jmenu.add(jexit);
    jbar.add(jmenu);
    j.setJMenuBar(jbar);

      
    j.pack();
    j.setResizable(false);
    j.setVisible(true);
  }


  public void setPixel() {

  }


  /**
   * Converts a given Image into a BufferedImage
   *
   * @param img The Image to be converted
   * @return The converted BufferedImage
   */
  public static BufferedImage toBufferedImage(Image img) {
      if (img instanceof BufferedImage) {
        return (BufferedImage) img;
      }

      // Create a buffered image with transparency
      BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

      // Draw the image on to the buffered image
      Graphics2D bGr = bimage.createGraphics();
      bGr.drawImage(img, 0, 0, null);
      bGr.dispose();

      // Return the buffered image
      return bimage;
  }

  public static int[] getPixel(BufferedImage img, int x, int y) {
    int argb = tmp.getRGB(x,y);
    int rgb[] = new int[] {
      (argb >> 16) & 0xff, //red
      (argb >>  8) & 0xff, //green
      (argb      ) & 0xff  //blue
    };
    return rgb;
  }

  //Use also to last algorithm
  public static void setPixel(BufferedImage img, int x, int y, int red, int green, int blue) {
    Color nuevo = new Color(red, green, blue);
    int rgbNuevo = nuevo.getRGB();
    img.setRGB(x, y, rgbNuevo);
  }

  public static BufferedImage toRandom(BufferedImage img) {
    return null;
  }

  public static BufferedImage toRed(BufferedImage img) {
    return null;

  }

  public static BufferedImage toGreen(BufferedImage img) {
    return null;

  }

  public static BufferedImage toBlue(BufferedImage img) {
    return null;

  }

  public static BufferedImage toGrayDivision(BufferedImage img) {
    return null;

  }

  public static BufferedImage toGrayConstant(BufferedImage img) {
    return null;

  }

  public static BufferedImage toGrayRed(BufferedImage img) {
    return null;

  }

  public static BufferedImage toGrayGreen(BufferedImage img) {
    return null;

  }

  public static BufferedImage toGrayBlue(BufferedImage img) {
    return null;

  }

  public static BufferedImage toGrayRedGreen(BufferedImage img) {
    return null;

  }

  public static BufferedImage toGrayGreenBlue(BufferedImage img) {
    return null;

  }

  public static BufferedImage toGrayBlueRed(BufferedImage img) {
    return null;

  }

  public static BufferedImage toBrightness(BufferedImage img, int brightness) {
    return null;
  }

  public static BufferedImage toHighContrast(BufferedImage img) {
    return null;
  }

  public static BufferedImage toInverseContrast(BufferedImage img) {
    return null;
  }

  public static BufferedImage toMosaic(BufferedImage img) {
    return null;
  }




  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Main();
      }
    });
  }


}