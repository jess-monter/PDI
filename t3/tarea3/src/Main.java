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
  private JMenuItem   reinicio, jmi, jexit, waterMarkFilter, saveModified;
  private JPanel      container, containerWarhol, jpanel, jpanelbar, jPanelModified, jPanelWarhol;
  JLabel              image, modified, modifiedA, modifiedB, modifiedC, modifiedD;
  ImageIcon           ic;
  Image               img, test;
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
    j.setPreferredSize(new Dimension(1200, 816));
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
    jmenu = new JMenu("Archivo");
    jfiltros = new JMenu("Filtros");


    jmi = new JMenuItem("Abrir");
    jmi.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        JFileChooser fc = new JFileChooser();
        int result = fc.showOpenDialog(null);
        if (result == JFileChooser.APPROVE_OPTION) {
          File file = fc.getSelectedFile();
          try {

            test = ImageIO.read(file);
           
            Image scaledImage = test.getScaledInstance(600,408,Image.SCALE_SMOOTH);
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


    jexit = new JMenuItem("Salir");
    jexit.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        System.exit(0);
      }
    });

    saveModified = new JMenuItem("Guardar");
    saveModified.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent ae) {
        String input = JOptionPane.showInputDialog(j,
                "Nombre de la Imagen", 0);
        String name = input + ".png";
        try{
          ImageIO.write(tmp, "PNG", new File(name));
        }catch(IOException ex) {
          System.out.println("error");
        }
        
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


    waterMarkFilter = new JMenuItem("Remueve marca de agua");
    waterMarkFilter.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){

        int ancho = tmpNew.getWidth();
        int alto = tmpNew.getHeight();
        int promTotal = 0;

        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            if((rgb[0]-rgb[1]) > 10 || (rgb[0] - rgb[2]) > 10){
              promTotal = (int)((rgb[0]+rgb[1]+rgb[2])/3)+50;
              if(promTotal > 255){
                promTotal = 255;
              }
              Color nuevo = new Color(promTotal, promTotal, promTotal);
              //Color nuevo = new Color(255, 255, 255);
              int rgbNuevo = nuevo.getRGB();
              tmp.setRGB(i,j,rgbNuevo);
            }
          }
        }
   
        

        modified.setIcon(new ImageIcon(tmp));
      }
    });


    JMenuItem[] jFilters = {reinicio, waterMarkFilter};

    jmenu.add(jmi);
    jmenu.add(saveModified);
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