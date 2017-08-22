import java.util.Random;
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
import javax.swing.JSlider;
import javax.swing.JOptionPane;


public class Main {
  private JFrame      j;
  private JMenu       jmenu, jfiltros;
  private JMenuBar    jbar;
  private JMenuItem   reinicio, jmi, jexit,randomFilter, redFilter, greenFilter, blueFilter, grayDivisionFilter, 
                      grayConstantFilter, grayRedGreenFilter, grayGreenBlueFilter, grayBlueRedFilter,
                      grayRedFilter, grayGreenFilter, grayBlueFilter, brightnessFilter, highContrastFilter,
                      inverseContrastFilter, mosaicFilter;
  private JPanel      container, jpanel, jpanelbar, jPanelModified;
  JLabel              image, modified;
  ImageIcon           ic;
  Image               img;
  BufferedImage       tmp, tmpNew;


  /**
   * Agrega elementos a un menu
   * @param menu Menu al que se le agregaran elementos
   * @param item Elemento a agregar
   */
  public static void agregaFiltrosMenu(JMenu menu, JMenuItem[] item) {
    for(int i=0; i<item.length; i++){
      menu.add(item[i]);
    }
  }

  /**
   * @constructor
   *
   */
  Main() {

    j = new JFrame("Filter Applicator");
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
    jfiltros = new JMenu("Filtros");


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
            tmpNew = toBufferedImage(scaledImage);
            tmp = toBufferedImage(scaledImage);
            image.setIcon(new ImageIcon(scaledImage));
            modified.setIcon(new ImageIcon(scaledImage));
            
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

    reinicio = new JMenuItem("Reinicio");
    reinicio.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            Color nuevo = new Color(rgb[0],rgb[1],rgb[2]);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });


    randomFilter = new JMenuItem("Random");
    randomFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        Random randRed = new Random();
        Random randGreen = new Random();
        Random randBlue = new Random();

        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            int red = randRed.nextInt(255);
            int green = randGreen.nextInt(255);
            int blue = randBlue.nextInt(255);

            Color nuevo = new Color(red, green, blue);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    redFilter = new JMenuItem("Rojo");
    redFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            Color nuevo = new Color(rgb[0],0,0);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    greenFilter = new JMenuItem("Verde");
    greenFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            Color nuevo = new Color(0,rgb[1],0);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    blueFilter = new JMenuItem("Azul");
    blueFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            Color nuevo = new Color(0,0,rgb[2]);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    grayDivisionFilter = new JMenuItem("Grises Division");
    grayDivisionFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };
            int div = (rgb[0]+rgb[1]+rgb[2])/3;
            Color nuevo = new Color(div,div,div);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    grayConstantFilter = new JMenuItem("Grises Constante");
    grayConstantFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            int color = (int)(rgb[0]*0.3 + rgb[1]*0.54 + rgb[2]*0.11);
            Color nuevo = new Color(color,color,color);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    grayRedGreenFilter = new JMenuItem("Grises RojoVerde");
    grayRedGreenFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };
            int color = (rgb[0] + rgb[1])/2;
            Color nuevo = new Color(color,color,color);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    grayGreenBlueFilter = new JMenuItem("Grises VerdeAzul");
    grayGreenBlueFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };
            int color = (rgb[1]+rgb[2])/2;
            Color nuevo = new Color(color,color,color);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    grayBlueRedFilter = new JMenuItem("Grises AzulRojo");
    grayBlueRedFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };
            int color = (rgb[0]+rgb[2])/2;
            Color nuevo = new Color(color,color,color);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    grayRedFilter = new JMenuItem("Grises Rojo");
    grayRedFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
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
      }
    });

    grayGreenFilter = new JMenuItem("Grises Verde");
    grayGreenFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            Color nuevo = new Color(rgb[1],rgb[1],rgb[1]);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    grayBlueFilter = new JMenuItem("Grises Azul");
    grayBlueFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            Color nuevo = new Color(rgb[2],rgb[2],rgb[2]);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });

    brightnessFilter = new JMenuItem("Brillo");
    brightnessFilter.addActionListener(new java.awt.event.ActionListener() {
      
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        String input = JOptionPane.showInputDialog(j,
                "Introduzca un numero entre -128 y 128", 0);
        int brightness = Integer.parseInt(input);
        int red, green, blue;
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            if(rgb[0] + brightness > 255){
              red = 255;
            }else if(rgb[0] + brightness < 0){
              red = 0;
            }else{
              red = rgb[0] + brightness;
            }

            if(rgb[1] + brightness > 255){
              green = 255;
            }else if(rgb[1] + brightness < 0){
              green = 0;
            }else{
              green = rgb[1] + brightness;
            }

            if(rgb[2] + brightness > 255){
              blue = 255;
            }else if(rgb[2] + brightness < 0){
              blue = 0;
            }else{
              blue = rgb[2] + brightness;
            }

            Color nuevo = new Color(red,green,blue);
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(i, j, rgbNuevo);
          }
        }
        modified.setIcon(new ImageIcon(tmp));
      }
    });


    highContrastFilter = new JMenuItem("Alto Contraste");
    highContrastFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
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
      }
    });

    inverseContrastFilter = new JMenuItem("Contraste Inverso");
    inverseContrastFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
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
      }
    });

    mosaicFilter = new JMenuItem("Mosaico");
    mosaicFilter.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent ae){
        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
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
      }
    });


    JMenuItem[] jFilters = {reinicio, randomFilter, redFilter, greenFilter, blueFilter, grayDivisionFilter, 
                        grayConstantFilter, grayRedGreenFilter, grayGreenBlueFilter, grayBlueRedFilter,
                        grayRedFilter, 
                        grayGreenFilter, grayBlueFilter, brightnessFilter,
                        highContrastFilter, inverseContrastFilter, mosaicFilter};


    jmenu.add(jmi);
    jmenu.add(jexit);
    jbar.add(jmenu);
    jbar.add(jfiltros);
    agregaFiltrosMenu(jfiltros, jFilters);
    


    j.setJMenuBar(jbar);
    j.pack();
    j.setResizable(false);
    j.setVisible(true);
  }




  /**
   * Convierte una imagen de tipo Image en una imagen de tipo BufferedImage
   * @param img La imagen a ser convertida
   * @return La imagen convertida.
   */
  public static BufferedImage toBufferedImage(Image img) {
      if (img instanceof BufferedImage) {
        return (BufferedImage) img;
      }

      BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

      Graphics2D bGr = bimage.createGraphics();
      bGr.drawImage(img, 0, 0, null);
      bGr.dispose();

      return bimage;
  }

  /**
   * Obtiene las componenetes RGB de un pixel en la posicion dada.
   *
   * @param img La imagen a ser procesada
   * @param x La posicion horizontal del pixel
   * @param y La posicion vertical del pixel
   * @return El arreglo con las componentes RGB del pixel
   */
  public static int[] getPixel(BufferedImage img, int x, int y) {
    int argb = img.getRGB(x,y);
    int rgb[] = new int[] {
      (argb >> 16) & 0xff, //red
      (argb >>  8) & 0xff, //green
      (argb      ) & 0xff  //blue
    };
    return rgb;
  }

  /**
   * Cambia las componentes RGB del pixel de una imagen en la posicion indicada.
   *
   * @param img La imagen a ser convertida
   * @param x Posicion horizontal del pixel
   * @param y Posicion vertical del pixel
   * @param red Componente roja RGB
   * @param green Componente verde RGB
   * @param blue Componente azul
   */
  public static void setPixel(BufferedImage img, int x, int y, int red, int green, int blue) {
    Color nuevo = new Color(red, green, blue);
    int rgbNuevo = nuevo.getRGB();
    img.setRGB(x, y, rgbNuevo);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Main();
      }
    });
  }


}