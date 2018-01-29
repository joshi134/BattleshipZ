import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

/**
  *
  * Beschreibung
  *
  * @version 1.0 vom 25.10.2017
  * @author 
  */

public class Spieler extends JFrame {
  // Anfang Attribute
  private JLabel nameFeld = new JLabel();
  private JTextField name = new JTextField();
  private Feld[][] spielfeld = new Feld[11][11];
  private JButton wieter = new JButton();
  private Schiffeversenken menu ;
  private SteuerungSpieler strgSP;
  private String titel;
  private JButton zeig = new JButton();
  private boolean lageB=false; // lage true = senkrecht  false= waggerecht
  private JButton lage = new JButton();
  private ImageIcon s5er = new ImageIcon("5erSchiff.PNG");
  private ImageIcon s4er = new ImageIcon("4erSchiff.PNG");
  private ImageIcon s3er = new ImageIcon("3erSchiff.PNG");
  private ImageIcon s2er = new ImageIcon("2erSchiff.PNG");
  private JButton schiff5 = new JButton(s5er);
  private JButton schiff4 = new JButton(s4er);
  private JButton schiff3 = new JButton(s3er);
  private JButton schiff21 = new JButton(s2er);
  private JButton schiff22 = new JButton(s2er);
  private JButton reset = new JButton();
  private boolean visibilitySchiff21=true;
  private boolean visibilitySchiff22=true;
  // Ende Attribute
  
  public Spieler(String title, Schiffeversenken m) { 
    // Frame-Initialisierung
    super(title); 
    titel=title;
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 382; 
    int frameHeight = 308;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // Anfang Komponenten
    this.addWindowListener(new WindowAdapter() {            // schlieﬂen 
      public void windowClosing(WindowEvent e) { 
      strgSP.schlieﬂen(); 
      } 
    });
    
    nameFeld.setBounds(8, 10, 43, 19);
    nameFeld.setText("Name:");
    cp.add(nameFeld);
    name.setBounds(48, 8, 97, 25);
    cp.add(name);
    wieter.setBounds(240, 256, 57, 17);
    wieter.setText("Weiter");
    wieter.setMargin(new Insets(2, 2, 2, 2));
    wieter.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        wieter_ActionPerformed(evt);
      }
    });
    cp.add(wieter);
    zeig.setBounds(24, 256, 129, 17);
    zeig.setText("Verdecken/Zeigen");
    zeig.setMargin(new Insets(2, 2, 2, 2));
    zeig.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        zeig_ActionPerformed(evt);
      }
    });
    cp.add(zeig);
    
    this.wieter.setVisible(false);
    lage.setBounds(304, 256, 65, 17);
    lage.setText("Lage: -");
    lage.setMargin(new Insets(2, 2, 2, 2));
    lage.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        lage_ActionPerformed(evt);
      }
    });
    cp.add(lage);
    schiff5.setBounds(336, 8, 17, 57);
    schiff5.setText("5");
    schiff5.setMargin(new Insets(2, 2, 2, 2));
    schiff5.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        schiff5_ActionPerformed(evt);
      }
    });
    cp.add(schiff5);
    schiff4.setBounds(336, 72, 17, 49);
    schiff4.setText("4");
    schiff4.setMargin(new Insets(2, 2, 2, 2));
    schiff4.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        schiff4_ActionPerformed(evt);
      }
    });
    cp.add(schiff4);
    schiff3.setBounds(336, 128, 17, 41);
    schiff3.setText("3");
    schiff3.setMargin(new Insets(2, 2, 2, 2));
    schiff3.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        schiff3_ActionPerformed(evt);
      }
    });
    cp.add(schiff3);
    schiff21.setBounds(336, 176, 17, 33);
    schiff21.setText("2");
    schiff21.setMargin(new Insets(2, 2, 2, 2));
    schiff21.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        schiff21_ActionPerformed(evt);
      }
    });
    cp.add(schiff21);
    schiff22.setBounds(336, 216, 17, 33);
    schiff22.setText("2");
    schiff22.setMargin(new Insets(2, 2, 2, 2));
    schiff22.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        schiff22_ActionPerformed(evt);
      }
    });
    cp.add(schiff22);
    reset.setBounds(240, 256, 57, 17);
    reset.setText("Reset");
    reset.setMargin(new Insets(2, 2, 2, 2));
    reset.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        reset_ActionPerformed(evt);
      }
    });
    cp.add(reset);
    // Ende Komponenten
    
    menu = m;
    strgSP= new SteuerungSpieler(this);
    this.addFelder(cp,title);
    //this.setFelder(cp);
  } // end of public Spieler
  
  // Anfang Methoden
  public void zeigen(boolean b){
    setVisible(b);
  }
  
  public void addFelder(Container cp, String titel){        // erstellt alle felder 
    int yK=48;
    int zahl =1;
    char text='a';
    for (int i=0;i<11 ;i++ ) {  //I=Y
      int xK=20;
      for (int k=0;k<11 ;k++ ) {    // K=x
        if (k==0 && i==0) {
          this.spielfeld[i][k] = new Feld("/",xK,yK,25,17,k,i,this.strgSP,titel);
        } else {
          if (i==0 && k>0 ) {
            this.spielfeld[i][k] = new Feld(""+zahl,xK,yK,25,17,k,i,this.strgSP,titel);
            zahl++;
          } else {
            this.spielfeld[i][k] = new Feld("",xK,yK,25,17,k,i,this.strgSP,titel);
          } // end of if-else
          if (k==0 && i>0) {
            this.spielfeld[i][k] = new Feld(""+text,xK,yK,25,17,k,i,this.strgSP,titel);
            text++;
          } 
        } // end of if-else
        xK=xK+25;
        cp.add(spielfeld[i][k].getButton());
      } // end of for
      yK=yK+17;
    } // end of for
  }  
  
  public void wieter_ActionPerformed(ActionEvent evt) {
    this.menu.welchsleSpieler();
    this.strgSP.setSchuss();
    this.strgSP.setname(this.name.getText());
    this.strgSP.go();
  } // end of wieter_ActionPerformed
  
  public void setDaten(DatenSchiffeversenken da){
    this.strgSP.setDaten(da);
  }
  
  public void setFarbeWeis(int i,int k){
    this.spielfeld[i][k].setWhite();
  }
  
  public void setFarbe(int i,int k, int j){
    this.spielfeld[i][k].setBackgroundC(j);
  }
  
  public boolean  getLage(){
    return this.lageB;
  }
  
  public void zeig_ActionPerformed(ActionEvent evt) {         // macht alle Schiffe eines Spieler unkenntlich 
    this.wieter.setVisible(true);
    this.zeig.setVisible(false);
    this.strgSP.zeige(this.titel);
    this.menu.welchsleSpieler();
    this.strgSP.setSchuss();
    this.strgSP.go();
    this.lage.setVisible(false);
  } // end of zeig_ActionPerformed
  
  public void lage_ActionPerformed(ActionEvent evt) {
    this.lageB =! this.lageB;
    if (this.lageB) {
      this.lage.setText("Lage: |");
    } else {
      this.lage.setText("Lage: -");
    } // end of if-else
  } // end of lage_ActionPerformed
  
  public void schiff5_ActionPerformed(ActionEvent evt) {
    this.strgSP.setGr(5);
    this.schiff5.setVisible(false);
  } // end of schiff5_ActionPerformed
  
  public void schiff4_ActionPerformed(ActionEvent evt) {
    this.strgSP.setGr(4);
    this.schiff4.setVisible(false);
  } // end of schiff4_ActionPerformed
  
  public void schiff3_ActionPerformed(ActionEvent evt) {
    this.strgSP.setGr(3);
    this.schiff3.setVisible(false);
  } // end of schiff3_ActionPerformed
  
  public void schiff21_ActionPerformed(ActionEvent evt) {
    this.strgSP.setGr(2);
    this.visibilitySchiff21=false;
    this.schiff21.setVisible(false);
  } // end of schiff21_ActionPerformed
  
  public void schiff22_ActionPerformed(ActionEvent evt) {
    this.strgSP.setGr(2);
    this.visibilitySchiff22=false; 
    this.schiff22.setVisible(false);
  } // end of schiff22_ActionPerformed
  
  public void reset_ActionPerformed(ActionEvent evt) {
    this.strgSP.resetDaten(this.titel);
    this.schiff5.setVisible(true);
    this.schiff4.setVisible(true);
    this.schiff3.setVisible(true);
    this.schiff21.setVisible(true);
    this.schiff22.setVisible(true);
  } // end of reset_ActionPerformed
  
  public void zeigSchiff5(){
    this.schiff5.setVisible(true);
  }
  public void zeigSchiff4(){
    this.schiff4.setVisible(true);
  }
  public void zeigSchiff3(){
    this.schiff3.setVisible(true);
  }
  public void zeigSchiff21(){
    this.visibilitySchiff21=true;
    this.schiff21.setVisible(true);
  } 
  public void zeigSchiff22(){
    this.visibilitySchiff22=true; 
    this.schiff22.setVisible(true);
  }
  
  public boolean getSchiff21Visible(){
    return this.visibilitySchiff21;
  }
  
  public boolean getSchiff22Visible(){
    return this.visibilitySchiff22;
  }
  
  // Ende Methoden
} // end of class Spieler
                                  
                                   