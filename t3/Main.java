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
  private JMenuItem   reinicio, jmi, jexit, waterMarkFilter;
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
    j.setPreferredSize(new Dimension(1100, 600));
    j.setLayout(new GridBagLayout());
 
    jpanel = new JPanel();
    jpanel.setLayout(new BorderLayout());
    image = new JLabel("");
    modified = new JLabel("");
    modifiedA = new JLabel("NORTHWEST");
    modifiedB = new JLabel("NORTHEAST");
    modifiedC = new JLabel("SOUTHWEST");
    modifiedD = new JLabel("SOUTHEAST");
    

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


    jexit = new JMenuItem("Salir");
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


    waterMarkFilter = new JMenuItem("Remueve marca de agua");
    waterMarkFilter.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        int dimX = 1;
        int dimY = 1;
        int ancho = tmpNew.getWidth();
        int alto = tmpNew.getHeight();

        int w = divPlus(dimX, ancho);
        int h = divPlus(dimY, alto);

        int total = w*h;

        for(int i = 0; i < tmpNew.getWidth(); i++){
          for(int j = 0; j < tmpNew.getHeight(); j++){
            int argb = tmpNew.getRGB(i,j);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            if(rgb[0] > rgb[1]){
              System.out.println(i + "," + j + " =R:" + rgb[0] + " G:" + rgb[1] + " B:" + rgb[2]);
            }
          }
        }

        for(int i=0; i<ancho;){
          for(int j=0; j<alto;){
            int sumRed = 0;
            int sumGreen = 0;
            int sumBlue = 0;
            int promRed = 0;
            int promGreen = 0;
            int promBlue = 0;

            for(int k = i; k < i+w; k++){
              for(int l = j; l < j+h; l++){
                int argb = tmpNew.getRGB(k,l);
                int rgb[] = new int[] {
                  (argb >> 16) & 0xff, //red
                  (argb >>  8) & 0xff, //green
                  (argb      ) & 0xff  //blue
                };

                sumRed += rgb[0];
                sumGreen += rgb[1];
                sumBlue += rgb[2];
              }
            }
            promRed = (int)sumRed/total;
            promBlue = (int)sumBlue/total;
            promGreen = (int)sumGreen/total;
            Color nuevo = new Color(promRed, promGreen, promBlue);

            for(int n=i; n<i+w; n++){
              for(int m=j; m<j+h; m++){
                int rgbNuevo = nuevo.getRGB();
                if(promRed>promBlue && promRed > promGreen){
                  tmp.setRGB(n, m, rgbNuevo);
                }
              }
            }
            j+= h;
          }
          i += w;
        }

        modified.setIcon(new ImageIcon(tmp));
      }
    });


    JMenuItem[] jFilters = {reinicio, waterMarkFilter};

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


  public BufferedImage convolucion(BufferedImage imgOld, BufferedImage imgNew, double[][] matriz, double factor, double bias){
    int ancho = imgOld.getWidth();
    int alto = imgOld.getHeight();
    for(int i = 0; i<ancho;i++){
      for(int j = 0; j<alto;j++){
        Color c = null;
        double t = 0;
        Color tc = new Color(imgOld.getRGB(i, j));
        double conRed = 0;
        double conGreen = 0;
        double conBlue = 0;
        int mati = 0;
        int matj = 0;
        int matPerPixel = (int)matriz.length/2;
        for(int k = i-matPerPixel;k<=i+matPerPixel;k++){
          for(int l = j-matPerPixel;l<=j+matPerPixel;l++){
            int rgb = 0;
            try{
              rgb = imgOld.getRGB(k,l);
            }catch (Exception e) {}
            Color ca = new Color(rgb);
            conRed += ca.getRed()*matriz[mati][matj];
            conGreen += ca.getGreen()*matriz[mati][matj];
            conBlue += ca.getBlue()*matriz[mati][matj];
            matj++;
          }
          matj = 0;
          mati++;
        }
        c = new Color(rangoCorrecto((int)(factor*conRed+bias)),
                      rangoCorrecto((int)(factor*conGreen+bias)),
                      rangoCorrecto((int)(factor*conBlue+bias)));
        imgNew.setRGB(i,j,c.getRGB());
      }
    }
    return imgNew;
  }

  public static int rangoCorrecto(int val){
    if(val > 255){
      return 255;
    }else if(val < 0){
      return 0;
    }else{
      return val;
    }
  }



  /**
   * Ajusta las dimensiones de un recuadro para el filtro mosaico.
   * @param tamCuadro tamanio del recuadro
   * @param tamImg tamanio de la imagen
   * @return el valor adecuado.
   */
  public static int divPlus(int tamCuadro, int tamImg) {
    if(tamImg%tamCuadro!=0){
      for(int i=tamCuadro; i<tamImg; i++){
        if(tamImg%tamCuadro!=0){
          tamCuadro += 1;
        }else{
          i = tamImg;
        }
      }
    }else{
      return tamCuadro;
    }
    return tamCuadro;
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