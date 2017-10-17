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
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import java.awt.TextArea;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;


public class Main {
  private JFrame      j;
  private JMenu       jmenu, jfiltros;
  private JMenuBar    jbar;
  private JMenuItem   jmi, jexit, recursivoColor, recursivoBlancoNegro;
  private JPanel      container, containerWarhol, jpanel, jpanelbar, jPanelModified, jPanelWarhol;
  JLabel              image, modified;
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


    recursivoColor = new JMenuItem("Imagenes recursivas a color");
    recursivoColor.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        Image scaledImageA = test.getScaledInstance(50,50,Image.SCALE_SMOOTH);
        BufferedImage tmpA = toBufferedImage(scaledImageA);
        
        int[] arr = new int[3];
        int prom = 0;
        int a =  tmpNew.getHeight() / 90;
        int l = tmpNew.getWidth() / 90;
        String s = "";
        int factor = 0;

        int cont = 0;

        while(cont < 10){
          for(int i=0+(cont*50); i<10+(cont*50); i++){
            for(int x=0+(cont*50); x<10+(cont*50); x++){

              for(int r=0; r<50; r++){
                for(int z=0; z<50; z++){
                  int argb = tmpA.getRGB(r,z);
                  int rgb[] = new int[] {
                    (argb >> 16) & 0xff, //red
                    (argb >>  8) & 0xff, //green
                    (argb      ) & 0xff  //blue
                  };

                  arr = PromedioRegion(i+(cont*50),50+(cont*50),x+(cont*50),50+(cont*50),tmpNew);
                  prom = (int)(arr[0]+arr[1]+arr[2])/3;
                  
                  if(prom>=0 || prom<16){
                    factor = -128;
                  }else if(prom >=16 || prom < 31){
                    factor = -112;
                  }else if(prom >=31 || prom < 48){
                    factor = -96;
                  }else if(prom >=48 || prom < 63){
                    factor = -80;
                  }else if(prom >=63 || prom < 79){
                    factor = -64;
                  }else if(prom >=79 || prom < 95){
                    factor = -48;
                  }else if(prom >=95 || prom < 111){
                    factor = -32;
                  }else if(prom >=111 || prom < 127){
                    factor = -16;
                  }else if(prom >=127 || prom < 143){
                    factor = 16;
                  }else if(prom >=143 || prom < 159){
                    factor = 32;
                  }else if(prom >=159 || prom < 175){
                    factor = 48;
                  }else if(prom >=175 || prom < 191){
                    factor = 64;
                  }else if(prom >=191 || prom < 207){
                    factor = 80;
                  }else if(prom >=207 || prom < 223){
                    factor = 96;
                  }else if(prom >=223 || prom < 239){
                    factor = 112;
                  }else if(prom >=239 || prom < 256){
                    factor = 128;
                  }

                  Color nuevo = new Color(normaliza(rgb[0]+factor),normaliza(rgb[1]+factor),normaliza(rgb[2]+factor));
                  int rgbNuevo = nuevo.getRGB();
                  tmp.setRGB(i, x, rgbNuevo);


                }
              }
            }
          }
          cont++;
        }



       
/*        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
            arr = PromedioRegion(k*l,(k*l)+l,h*a,(h*a)+a,tmpNew);
            prom = (int)(arr[0]+arr[1]+arr[2])/3;
            if(prom>=0 || prom<16){
              factor = -128;
            }else if(prom >=16 || prom < 31){
              factor = -112;
            }else if(prom >=31 || prom < 48){
              factor = -96;
            }else if(prom >=48 || prom < 63){
              factor = -80;
            }else if(prom >=63 || prom < 79){
              factor = -64;
            }else if(prom >=79 || prom < 95){
              factor = -48;
            }else if(prom >=95 || prom < 111){
              factor = -32;
            }else if(prom >=111 || prom < 127){
              factor = -16;
            }else if(prom >=127 || prom < 143){
              factor = 16;
            }else if(prom >=143 || prom < 159){
              factor = 32;
            }else if(prom >=159 || prom < 175){
              factor = 48;
            }else if(prom >=175 || prom < 191){
              factor = 64;
            }else if(prom >=191 || prom < 207){
              factor = 80;
            }else if(prom >=207 || prom < 223){
              factor = 96;
            }else if(prom >=223 || prom < 239){
              factor = 112;
            }else if(prom >=239 || prom < 256){
              factor = 128;
            }
            
            Color nuevo = new Color(normaliza(rgb[0]+factor),normaliza(rgb[1]+factor),normaliza(rgb[2]+factor));
            int rgbNuevo = nuevo.getRGB();
            tmp.setRGB(h, k, rgbNuevo);
          }
        }*/

        modified.setIcon(new ImageIcon(tmp));
        

        }


    });

    recursivoBlancoNegro = new JMenuItem("Imagenes recursivas blanco y negro");
    recursivoBlancoNegro.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        int[] arr = new int[3];
        int a =  tmpNew.getHeight() / 90;
        int l = tmpNew.getWidth() / 90;
        String s = "";
        if(tmpNew != null){
          for (int y = 0; y < 90; y++){
            if(y > 0) s += "\n";
            for (int x = 0 ; x < 90; x++){
                s += "M";
            }
          }
        }
        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
            arr = PromedioRegion(k*l,(k*l)+l,h*a,(h*a)+a,tmpNew);
            int gris = (int)(arr[0]+arr[1]+arr[2])/3;
            Color c = new Color(gris,gris,gris);
          }
        }
      }
    });


    JMenuItem[] jFilters = {recursivoColor, recursivoBlancoNegro};

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

  public int[] PromedioRegion(int inicioa, int fina, int iniciol, int finl, BufferedImage img){
    int rp =0;
    int gp =0;
    int bp =0;
    int cantidad = (img.getHeight() / 90)*(img.getWidth() /90) ;
    int[] rgbArray = new int[3];
    Color nuevo;
    for(int a = inicioa ; a < fina;a++){
      for(int b= iniciol;b< finl;b++){
        if(a<img.getWidth()){
          if(b<img.getHeight()){
            int argb = img.getRGB(a,b);
            int rgb[] = new int[] {
              (argb >> 16) & 0xff, //red
              (argb >>  8) & 0xff, //green
              (argb      ) & 0xff  //blue
            };

            rp+= rgb[0];
            gp+= rgb[1];
            bp+= rgb[2];
          }
        }
      }
    }
    rp = (int)(rp/cantidad);
    gp = (int)(gp/cantidad);
    bp = (int)(bp/cantidad);
    rgbArray[0] = rp;
    rgbArray[1] = gp;
    rgbArray[2] = bp;
    return rgbArray;
  }

  public int normaliza(int color){
    int valor = 0;
    if(color<0){
      valor = 0;
    }else if(color > 255){
      valor = 255;
    }
    return valor;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Main();
      }
    });
  }


}