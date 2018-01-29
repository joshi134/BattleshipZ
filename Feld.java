import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
public class Feld {
  private String name;
  private JButton feld= new JButton();
  private boolean gedrueckt= false;
  private boolean belegt= false;
  private int positionX;
  private int positionY;
  private SteuerungSpieler strg;
  
  
  public Feld(String text,int x,int y, int l, int h,int xP, int yP,SteuerungSpieler stg,String n){
    feld.addActionListener(new ActionListener() { 
      public void actionPerformed(ActionEvent evt) { 
        feld_Action(evt);
      }
    });
    name= n; 
    feld.setBounds(x,y,l,h);
    feld.setText(text);
    feld.setMargin(new Insets(2, 2, 2, 2));
    positionX=xP;
    positionY=yP;
    this.setWhite();
    strg=stg;
  }
  
  private void feld_Action(ActionEvent evt){
    int wert = strg.gedrueckt(this.getX() ,this.getY(),this.getName(), this.gibText() );
    boolean start= this.strg.getGo();
    this.setBackgroundC(wert);
  } 
  
  public void setText(String text){
    feld.setText(text);
  }
  
  public JButton getButton(){
    return feld;
  }
  public int getY(){
    return this.positionY;
  }
  public int getX(){
    return this.positionX;
  }
  
  public String getName(){
    return name;
  }
  
  public String gibText(){
    return feld.getText();
  }
  
  public void setBackgroundC(int i){
    switch (i) {
      case  0: 
      feld.setBackground(Color.WHITE);
      break;
      case  1: 
      feld.setBackground(Color.BLACK);
      break;
      case  2: 
      feld.setBackground(Color.BLUE);
      break;
      case  3: 
      feld.setBackground(Color.RED);
      break;
      default: 
      
    } // end of switch
  }
  
  public void setWhite(){
    feld.setBackground(Color.WHITE);
  }
  
}