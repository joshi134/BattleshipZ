import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class KIGui  extends JFrame  {
  final private  String authoren="© by Mario"; 
  private JButton wieterKI = new JButton();
  private JButton schieﬂen = new JButton();
  private KI ki; 
  private int xKi;
  private int yKi;
  
  // Anfang Attribute
  private JLabel nameFeld = new JLabel();
  private JLabel author = new JLabel();
  private JTextField name = new JTextField();
  private Feld[][] spielfeld = new Feld[11][11];
  private Schiffeversenken menu ;
  private String titel;
  
  
  public KIGui(String title, Schiffeversenken m){
    // Frame-Initialisierung
    super(title); 
    titel=title;
    setDefaultCloseOperation(this.DO_NOTHING_ON_CLOSE);
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
        ki.schlieﬂen(); 
      } 
    });
    
    nameFeld.setBounds(8, 10, 55, 19);
    nameFeld.setText("Name: KI");
    cp.add(nameFeld);
    
    
    // Ende Komponenten
    
    
    //this.setFelder(cp);
    
    wieterKI.setBounds(304, 240, 65, 17);
    wieterKI.setText("Weiter");
    wieterKI.setMargin(new Insets(2, 2, 2, 2));
    wieterKI.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        wieterKI_ActionPerformed(evt);
      }
    });
    cp.add(wieterKI);
    schieﬂen.setBounds(304, 240, 65, 17);
    schieﬂen.setText("schieﬂen");
    schieﬂen.setMargin(new Insets(2, 2, 2, 2));
    schieﬂen.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        schieﬂen_ActionPerformed(evt);
      }
    });
    cp.add(schieﬂen);
    author.setBounds(304, 260, 107, 19);
    author.setText(this.authoren);
    cp.add(author);
    this.schieﬂen.setVisible(false);
    menu = m;
    this.addFelder(cp,title); 
    ki=new KI(this);
  }
  
  public void close(){
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
  }
  
  public void zeigen(boolean b){
    setVisible(b);
  }
  
  public void setDaten(DatenSchiffeversenken da){
    this.ki.setDaten(da);
  }
  
  public void setFeldColor(int x, int y , int i){
    this.spielfeld[x][y].setBackgroundC(i); 
  }
  
  public void wieterKI_ActionPerformed(ActionEvent evt) {
    this.ki.drueckButton();
    this.wieterKI.setVisible(false);
    this.schieﬂen.setVisible(true);
    this.menu.welchsleKI();
    this.ki.setSchuss();
    
  } // end of wieter_ActionPerformed 
  
  public void schieﬂen_ActionPerformed(ActionEvent evt) {
    ki.drueckButton();
    this.menu.welchsleKI();
    this.ki.setSchuss();
  } // end of wieter_ActionPerformed  
  
  public void addFelder(Container cp, String titel){        // erstellt alle felder 
    int yK=48;
    int zahl =1;
    char text='a';
    for (int i=0;i<11 ;i++ ) {  //I=Y
      int xK=20;
      for (int k=0;k<11 ;k++ ) {    // K=x
        if (k==0 && i==0) {
          this.spielfeld[i][k] = new Feld("/",xK,yK,25,17,k,i,this.ki,titel);
        } else {
          if (i==0 && k>0 ) {
            this.spielfeld[i][k] = new Feld(""+zahl,xK,yK,25,17,k,i,this.ki,titel);
            zahl++;
          } else {
            this.spielfeld[i][k] = new Feld("",xK,yK,25,17,k,i,this.ki,titel);
          } // end of if-else
          if (k==0 && i>0) {
            this.spielfeld[i][k] = new Feld(""+text,xK,yK,25,17,k,i,this.ki,titel);
            text++;
          } 
        } // end of if-else
        xK=xK+25;
        cp.add(spielfeld[i][k].getButton());
      } // end of for
      yK=yK+17;
    } // end of for
  } 
  
} 