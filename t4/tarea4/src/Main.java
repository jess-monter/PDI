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
  private JMenuItem   jmi, jexit, coloresSinLetra, tonosGris, letrasBlancoNegro, coloresLetrasTonosGris, 
                      tonosGrisLetrasGris, coloresTexto, naipes, dominoBlancas, dominoNegras;
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
    j.setPreferredSize(new Dimension(500, 500));
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
    //container.add(jPanelModified);

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


    coloresSinLetra = new JMenuItem("Colores sin Letras");
    coloresSinLetra.addActionListener(new ActionListener(){
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

        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane = new JTextPane(doc);

        JFrame f = new JFrame("Colores sin Letras");
        try{
          doc.insertString(0, s, null);
        }catch(BadLocationException e){
          System.out.println(e);
        }

        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
              Style estilo = sc.addStyle("ConstantWidth", null);
              arr = PromedioRegion(k*l,(k*l)+l,h*a,(h*a)+a,tmpNew);
              Color c = new Color(arr[0],arr[1],arr[2]);
              StyleConstants.setForeground(estilo, c);
              doc.setCharacterAttributes(contador++, 1, estilo, false);
          }
        }
        //pane.setFont(new Font("Courier New", Font.PLAIN, 18));
        f.getContentPane().add(new JScrollPane(pane));
        f.setSize(1300, 600);
        f.setVisible(true);
      }
    });

    tonosGris = new JMenuItem("Tonos de Gris");
    tonosGris.addActionListener(new ActionListener(){
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

        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane = new JTextPane(doc);

        JFrame f = new JFrame("Tonos de Gris");
        //f.setSize(600,600);
        try{
          doc.insertString(0, s, null);
        }catch(BadLocationException e){
          System.out.println(e);
        }

        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
              Style estilo = sc.addStyle("ConstantWidth", null);
              arr = PromedioRegion(k*l,(k*l)+l,h*a,(h*a)+a,tmpNew);
              int gris = (int)(arr[0]+arr[1]+arr[2])/3;
              Color c = new Color(gris,gris,gris);
              StyleConstants.setForeground(estilo, c);
              doc.setCharacterAttributes(contador++, 1, estilo, false);
          }
        }
        //pane.setFont(new Font("Courier New", Font.PLAIN, 18));
        f.getContentPane().add(new JScrollPane(pane));
        f.setSize(1300, 600);
        f.setVisible(true);        
      }
    });

    letrasBlancoNegro = new JMenuItem("Letras blanco y negro");
    letrasBlancoNegro.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        int[] arr = new int[3];
        int a =  tmpNew.getHeight() / 90;
        int l = tmpNew.getWidth() / 90;
        String s = "";
        if(tmpNew != null){
          for (int y = 0; y < 90; y++){
            if(y > 0) s += "\n";
            for (int x = 0 ; x < 90; x++){
              int[] prom =  PromedioRegion(x*l,(x*l)+l,y*a,(y*a)+a,tmpNew);
              int med = (int)(prom[0]+prom[1]+prom[2])/3;
              if (med >= 0 && med < 17){
                s += "M";
              }else if(med >= 17 && med < 34){
                s += "N";
              }else if(med >= 34 && med < 52){
                s += "H";
              }else if(med >= 52 && med < 62){
                s += "#";
              }else if(med >= 62 && med < 86){
                s += "Q";
              }else if(med >= 86 && med < 103){
                s += "U";
              }else if(med >= 103 && med < 120){
                s += "A";
              }else if(med >= 120 && med < 138){
                s += "D";
              }else if(med >= 138 && med < 155){
                s += "0";
              }else if(med >= 155 && med < 172){
                s += "Y";
              }else if(med >= 172 && med < 189){
                s += "2";
              }else if(med >= 189 && med < 206){
                s += "$";
              }else if(med >= 206 && med < 223){
                s += "%";
              }else if(med >= 223 && med < 240){
                s += "+";
              }else if(med >= 240 && med < 257){
                s += "-";
              }
            }
          }
        }

        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane = new JTextPane(doc);
        Font font = new Font("Courier New", Font.PLAIN, 10);
        pane.setFont(font);
        JFrame f = new JFrame("Letras Blanco y Negro");

        try{
          doc.insertString(0, s, null);
        }catch(BadLocationException e){
          System.out.println(e);
        }

        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
              Style estilo = sc.addStyle("ConstantWidth", null);
              Color c = new Color(0,0,0);
              StyleConstants.setForeground(estilo, c);
              doc.setCharacterAttributes(contador++, 1, estilo, false);
          }
        }

        f.getContentPane().add(new JScrollPane(pane));
        f.setSize(1300, 600);
        f.setVisible(true);            
      }
    });

    coloresLetrasTonosGris = new JMenuItem("Colores + letras tonos de gris");
    coloresLetrasTonosGris.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        int[] arr = new int[3];
        int a =  tmpNew.getHeight() / 90;
        int l = tmpNew.getWidth() / 90;
        String s = "";
        if(tmpNew != null){
          for (int y = 0; y < 90; y++){
            if(y > 0) s += "\n";
            for (int x = 0 ; x < 90; x++){
              int[] prom =  PromedioRegion(x*l,(x*l)+l,y*a,(y*a)+a,tmpNew);
              int med = (int)(prom[0]+prom[1]+prom[2])/3;
              if (med >= 0 && med < 17){
                s += "M";
              }else if(med >= 17 && med < 34){
                s += "N";
              }else if(med >= 34 && med < 52){
                s += "H";
              }else if(med >= 52 && med < 62){
                s += "#";
              }else if(med >= 62 && med < 86){
                s += "Q";
              }else if(med >= 86 && med < 103){
                s += "U";
              }else if(med >= 103 && med < 120){
                s += "A";
              }else if(med >= 120 && med < 138){
                s += "D";
              }else if(med >= 138 && med < 155){
                s += "0";
              }else if(med >= 155 && med < 172){
                s += "Y";
              }else if(med >= 172 && med < 189){
                s += "2";
              }else if(med >= 189 && med < 206){
                s += "$";
              }else if(med >= 206 && med < 223){
                s += "%";
              }else if(med >= 223 && med < 240){
                s += "+";
              }else if(med >= 240 && med < 257){
                s += "-";
              }
            }
          }
        }

        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane = new JTextPane(doc);
        Font font = new Font("Courier New", Font.PLAIN, 10);
        //Font font = new Font("Courier 10 Pitch", Font.PLAIN, 10);
        
        pane.setFont(font);
        JFrame f = new JFrame("Colores + letras tonos de Gris");

        try{
          doc.insertString(0, s, null);
        }catch(BadLocationException e){
          System.out.println(e);
        }

        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
              Style estilo = sc.addStyle("ConstantWidth", null);
              arr = PromedioRegion(k*l,(k*l)+l,h*a,(h*a)+a,tmpNew);
              Color c = new Color(arr[0],arr[1],arr[2]);
              StyleConstants.setForeground(estilo, c);
              doc.setCharacterAttributes(contador++, 1, estilo, false);
          }
        }

        f.getContentPane().add(new JScrollPane(pane));
        f.setSize(1300, 600);
        f.setVisible(true);           
      }
    });

    tonosGrisLetrasGris = new JMenuItem("Tonos de gris + letras tonos de gris");
    tonosGrisLetrasGris.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        int[] arr = new int[3];
        int a =  tmpNew.getHeight() / 90;
        int l = tmpNew.getWidth() / 90;
        String s = "";
        if(tmpNew != null){
          for (int y = 0; y < 90; y++){
            if(y > 0) s += "\n";
            for (int x = 0 ; x < 90; x++){
              int[] prom =  PromedioRegion(x*l,(x*l)+l,y*a,(y*a)+a,tmpNew);
              int med = (int)(prom[0]+prom[1]+prom[2])/3;
              if (med >= 0 && med < 17){
                s += "M";
              }else if(med >= 17 && med < 34){
                s += "N";
              }else if(med >= 34 && med < 52){
                s += "H";
              }else if(med >= 52 && med < 62){
                s += "#";
              }else if(med >= 62 && med < 86){
                s += "Q";
              }else if(med >= 86 && med < 103){
                s += "U";
              }else if(med >= 103 && med < 120){
                s += "A";
              }else if(med >= 120 && med < 138){
                s += "D";
              }else if(med >= 138 && med < 155){
                s += "0";
              }else if(med >= 155 && med < 172){
                s += "Y";
              }else if(med >= 172 && med < 189){
                s += "2";
              }else if(med >= 189 && med < 206){
                s += "$";
              }else if(med >= 206 && med < 223){
                s += "%";
              }else if(med >= 223 && med < 240){
                s += "+";
              }else if(med >= 240 && med < 257){
                s += "-";
              }
            }
          }
        }

        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane = new JTextPane(doc);
        Font font = new Font("Courier New", Font.PLAIN, 10);
        //Font font = new Font("Courier 10 Pitch", Font.PLAIN, 10);
        
        pane.setFont(font);
        JFrame f = new JFrame("Letras Blanco y Negro");

        try{
          doc.insertString(0, s, null);
        }catch(BadLocationException e){
          System.out.println(e);
        }

        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
              Style estilo = sc.addStyle("ConstantWidth", null);
              arr = PromedioRegion(k*l,(k*l)+l,h*a,(h*a)+a,tmpNew);
              int prom = (int)(arr[0]+arr[1]+arr[2])/3;
              Color c = new Color(prom,prom,prom);
              StyleConstants.setForeground(estilo, c);
              doc.setCharacterAttributes(contador++, 1, estilo, false);
          }
        }

        f.getContentPane().add(new JScrollPane(pane));
        f.setSize(1300, 600);
        f.setVisible(true);           
      }
    });

    coloresTexto = new JMenuItem("Colores con Texto");
    coloresTexto.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        String input = JOptionPane.showInputDialog(j, "Introduzca la cadena a procesar", 0);
        int[] arr = new int[3];
        int a =  tmpNew.getHeight() / 90;
        int l = tmpNew.getWidth() / 90;
        int aux = 0;
        String s = "";
        if(tmpNew != null){
          for (int y = 0; y < 90; y++){
            if(y > 0) s += "\n";
            for (int x = 0 ; x < 90; x++){
              if(aux == input.length()){
                s += " ";
                aux = 0;
              }else{
                s += input.substring(aux, aux+1);
                aux++;
              }
            }
          }
        }

        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane = new JTextPane(doc);
        Font font = new Font("Courier New", Font.PLAIN, 10);
        //Font font = new Font("Courier 10 Pitch", Font.PLAIN, 10);
        
        pane.setFont(font);
        JFrame f = new JFrame("Colores con texto");

        try{
          doc.insertString(0, s, null);
        }catch(BadLocationException e){
          System.out.println(e);
        }

        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
              Style estilo = sc.addStyle("ConstantWidth", null);
              arr = PromedioRegion(k*l,(k*l)+l,h*a,(h*a)+a,tmpNew);
              Color c = new Color(arr[0],arr[1],arr[2]);
              StyleConstants.setForeground(estilo, c);
              doc.setCharacterAttributes(contador++, 1, estilo, false);
          }
        }

        f.getContentPane().add(new JScrollPane(pane));
        f.setSize(1300, 600);
        f.setVisible(true);

      }
    });

    naipes = new JMenuItem("Naipes");
    naipes.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        int[] prom = new int[3];
        int a =  tmpNew.getHeight() / 90;
        int l = tmpNew.getWidth() / 90;
        String s = "";
        if(tmpNew != null){
          for (int y = 0; y < 90; y++){
            if(y > 0) s += "\n";
            for (int x = 0 ; x < 90; x++){
              prom = PromedioRegion(x*l,(x*l)+l,y*a,(y*a)+a,tmpNew);
              int med = (int)(prom[0]+prom[1]+prom[2])/3;
              if (med >= 0 && med < 23){
                  s += "Z";
              } else if(med >= 23 && med < 46){
                  s += "W";
              }else if(med >= 46 && med < 69){
                  s += "V";
              }else if(med >= 69 && med < 92){
                  s += "U";
              }else if(med >= 92 && med < 115){
                  s += "T";
              }else if(med >= 115 && med < 138){
                  s += "S";
              }else if(med >= 138 && med < 161){
                  s += "R";
              }else if(med >= 161 && med < 184){
                  s += "Q";
              }else if(med >= 184 && med < 207){
                  s += "P";
              }else if(med >= 207 && med < 230){
                  s += "O";
              }else if(med >= 230 && med < 256){
                  s += "N";
              }
            }
          }
        }

        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane = new JTextPane(doc);
        Font font = new Font("PlayingCards", Font.PLAIN, 20);
        pane.setFont(font);
        JFrame f = new JFrame("Naipes");

        try{
          doc.insertString(0, s, null);
        }catch(BadLocationException e){
          System.out.println(e);
        }
        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
              Style estilo = sc.addStyle("ConstantWidth", null);
              Color c = new Color(0,0,0);
              StyleConstants.setForeground(estilo, c);
              doc.setCharacterAttributes(contador++, 1, estilo, false);
          }
        }

        f.getContentPane().add(new JScrollPane(pane));
        f.setSize(1300, 600);
        f.setVisible(true);            

      }
    });

    dominoBlancas = new JMenuItem("Domino Blancas");
    dominoBlancas.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        int[] prom = new int[3];
        int a =  tmpNew.getHeight() / 90;
        int l = tmpNew.getWidth() / 90;
        String s = "";
        if(tmpNew != null){
          for (int y = 0; y < 90; y++){
            if(y > 0) s += "\n";
            for (int x = 0 ; x < 90; x++){
              prom = PromedioRegion(x*l,(x*l)+l,y*a,(y*a)+a,tmpNew);
              int med = (int)(prom[0]+prom[1]+prom[2])/3;
              if (med >= 0 && med < 37){
                  s += "6";
              } else if(med >= 37 && med < 74){
                  s += "5";
              }else if(med >= 74 && med < 111){
                  s += "4";
              }else if(med >= 111 && med < 148){
                  s += "3";
              }else if(med >= 148 && med < 185){
                  s += "2";
              }else if(med >= 185 && med < 222){
                  s += "1";
              }else if(med >= 222 && med < 256){
                  s += "0";
              }
            }
          }
        }

        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane = new JTextPane(doc);
        Font font = new Font("Las Vegas White Dominoes", Font.PLAIN, 10);
        pane.setFont(font);
        JFrame f = new JFrame("Domino Blancas");

        try{
          doc.insertString(0, s, null);
        }catch(BadLocationException e){
          System.out.println(e);
        }
        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
              Style estilo = sc.addStyle("ConstantWidth", null);
              Color c = new Color(0,0,0);
              StyleConstants.setForeground(estilo, c);
              doc.setCharacterAttributes(contador++, 1, estilo, false);
          }
        }

        f.getContentPane().add(new JScrollPane(pane));
        f.setSize(1300, 600);
        f.setVisible(true);            
      }
    });

    dominoNegras = new JMenuItem("Domino Negras");
    dominoNegras.addActionListener(new ActionListener(){
      public void actionPerformed(java.awt.event.ActionEvent evt){
        int[] prom = new int[3];
        int a =  tmpNew.getHeight() / 90;
        int l = tmpNew.getWidth() / 90;
        String s = "";
        if(tmpNew != null){
          for (int y = 0; y < 90; y++){
            if(y > 0) s += "\n";
            for (int x = 0 ; x < 90; x++){
              prom = PromedioRegion(x*l,(x*l)+l,y*a,(y*a)+a,tmpNew);
              int med = (int)(prom[0]+prom[1]+prom[2])/3;
              if (med >= 0 && med < 37){
                  s += "6";
              } else if(med >= 37 && med < 74){
                  s += "5";
              }else if(med >= 74 && med < 111){
                  s += "4";
              }else if(med >= 111 && med < 148){
                  s += "3";
              }else if(med >= 148 && med < 185){
                  s += "2";
              }else if(med >= 185 && med < 222){
                  s += "1";
              }else if(med >= 222 && med < 256){
                  s += "0";
              }
            }
          }
        }

        StyleContext sc = new StyleContext();
        DefaultStyledDocument doc = new DefaultStyledDocument(sc);
        JTextPane pane = new JTextPane(doc);
        Font font = new Font("Las Vegas Black Dominoes", Font.PLAIN, 10);
        pane.setFont(font);
        JFrame f = new JFrame("Domino Negras");

        try{
          doc.insertString(0, s, null);
        }catch(BadLocationException e){
          System.out.println(e);
        }
        
        int contador = 0;
        for (int h = 0; h < 90; h++){
          if(h > 0) contador++ ;
          for (int k = 0 ; k < 90; k++){
              Style estilo = sc.addStyle("ConstantWidth", null);
              Color c = new Color(0,0,0);
              StyleConstants.setForeground(estilo, c);
              doc.setCharacterAttributes(contador++, 1, estilo, false);
          }
        }

        f.getContentPane().add(new JScrollPane(pane));
        f.setSize(1300, 600);
        f.setVisible(true);       
      }
    });



    JMenuItem[] jFilters = {coloresSinLetra, tonosGris, letrasBlancoNegro, 
                            coloresLetrasTonosGris, tonosGrisLetrasGris, coloresTexto, 
                            naipes, dominoBlancas, dominoNegras};

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
              //Color c1=new Color(img.getRGB(a, b));
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
    nuevo = new Color(rp,gp,bp);

    int promedio = (int)((rp + gp + bp)/3);
    return rgbArray;
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        new Main();
      }
    });
  }


}