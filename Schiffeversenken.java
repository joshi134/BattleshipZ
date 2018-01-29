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

public class Schiffeversenken extends JFrame {
  // Anfang Attribute
  private SteuerungSchiffeversenken strg;
  private JLabel ueberschrift = new JLabel();
  private JButton pvsp = new JButton();
  private JButton ende = new JButton();
  private Spieler playerOne = new Spieler("Player 1",this);
  private Spieler playerTwo = new Spieler("Player 2",this);
  private int zug =1 ;
  private JButton pvsc = new JButton();
  private JButton onlinePvsP = new JButton();
  // Ende Attribute
  
  public Schiffeversenken(String title) { 
    // Frame-Initialisierung
    super(title);
    setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
    int frameWidth = 239; 
    int frameHeight = 283;
    setSize(frameWidth, frameHeight);
    Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
    int x = (d.width - getSize().width) / 2;
    int y = (d.height - getSize().height) / 2;
    setLocation(x, y);
    setResizable(false);
    Container cp = getContentPane();
    cp.setLayout(null);
    // Anfang Komponenten
    this.addWindowListener(new WindowAdapter() {            // schlie�en 
      public void windowClosing(WindowEvent e) { 
        strg.schliessen(); 
      } 
    });
    
    ueberschrift.setBounds(48, 24, 147, 33);
    ueberschrift.setText("Schiffeversenken Men�");
    cp.add(ueberschrift);
    pvsp.setBounds(48, 64, 145, 33);
    pvsp.setText("Player vs. Player");
    pvsp.setMargin(new Insets(2, 2, 2, 2));
    pvsp.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        pvsp_ActionPerformed(evt);
      }
    });
    cp.add(pvsp);
    ende.setBounds(48, 208, 145, 33);
    ende.setText("Exit");
    ende.setMargin(new Insets(2, 2, 2, 2));
    ende.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        ende_ActionPerformed(evt);
      }
    });
    cp.add(ende);
    pvsc.setBounds(48, 112, 145, 33);
    pvsc.setText("Player vs. Computer");
    pvsc.setMargin(new Insets(2, 2, 2, 2));
    pvsc.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        pvsc_ActionPerformed(evt);
      }
    });
    cp.add(pvsc);
    onlinePvsP.setBounds(48, 160, 145, 33);
    onlinePvsP.setText("Online Player vs. Player");
    onlinePvsP.setMargin(new Insets(2, 2, 2, 2));
    onlinePvsP.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        onlinePvsP_ActionPerformed(evt);
      }
    });
    cp.add(onlinePvsP);
    // Ende Komponenten
    strg = new SteuerungSchiffeversenken(this);
    setVisible(true);
  } // end of public Schiffeversenken
  
  // Anfang Methoden
  public void pvsp_ActionPerformed(ActionEvent evt) {
    playerOne = new Spieler("Player 1",this);
    playerTwo = new Spieler("Player 2",this);
    this.setVisible(true);
    this.playerOne.setDaten(this.strg.getDaten());
    this.playerTwo.setDaten(this.strg.getDaten());
    this.welchsleSpieler();
  } // end of pvsp_ActionPerformed
  
  public void welchsleSpieler(){
    if (this.zug%2==0) {
      this.playerOne.zeigen(false);
      this.playerTwo.zeigen(true);
    } else {
      this.playerOne.zeigen(true);
      this.playerTwo.zeigen(false);
    } // end of if-else
    this.zug++;
  }
  
  public void ende_ActionPerformed(ActionEvent evt) {
    
  } // end of ende_ActionPerformed
  
  public void pvsc_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einf�gen
  } // end of pvsc_ActionPerformed
  
  public void onlinePvsP_ActionPerformed(ActionEvent evt) {
    // TODO hier Quelltext einf�gen
  } // end of onlinePvsP_ActionPerformed
  
  // Ende Methoden
  
  public static void main(String[] args) {
    new Schiffeversenken("Schiffeversenken");
  } // end of main
  
} // end of class Schiffeversenken
