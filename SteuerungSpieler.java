import java.util.*;
import javax.swing.JOptionPane;
public class SteuerungSpieler{
  private Spieler spieler;
  private DatenSchiffeversenken daten ;
  private boolean go=false; 
  private int gr=0;
  private int schuss=0;
  private String nameSpieler="";
  
  
  public void schlie�en(){
    int result = JOptionPane.showConfirmDialog(null, "Zur�ck zum Hauptmen�?", "Schlie�en", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      //System.exit(0)  ;
      this.spieler.close();
    } else  {
      
    }     
  }
  
  public  SteuerungSpieler(Spieler sp){
    spieler = sp;
  }
  
  public void setDaten(DatenSchiffeversenken da){
    daten = da; 
  }
  
  public void zeige(String name){                         // macht die eingesetzten schiffe unsichtbar 
    for (int k=1;k<this.daten.getLaenge() ;k++ ) {
      for (int i=1;i<this.daten.getLaenge() ;i++ ) {
        if (this.pl1(name)) {
          int pl=this.daten.getPL1(i,k);
          if (pl>0) {
            this.spieler.setFarbeWeis(i,k);
          } // end of if
        } else {
          int pl=this.daten.getPL2(i,k);
          if (pl>0) {
            this.spieler.setFarbeWeis(i,k);
          } // end of if
        } // end of if-else
      } // end of for  
    } 
  }
  
  public void go(){
    go=true;
  }
  
  public int gedrueckt(int k ,int i, String name, String text){    // �ndert die Farbe der Felder     // k=x i=y
    int returnWert=0;
    if (text.equals("")) {
      if (go) {                               // schaune ob getroffen und gewonnen
        if (schuss==1) {
          schuss=0;
          returnWert= this.vergleich(name,i,k);   
          if (this.pl1(name)) {                    //sp1 schiesst auf feld von Sp2
            this.daten.addPL2(i,k,returnWert);
          } else {                                 //sp2 schiesst auf feld von Sp1
            this.daten.addPL1(i,k,returnWert);
          } // end of if-else 
          this.endVergleich();
        } else {
          JOptionPane.showMessageDialog(null,"Sie haben schon geschossen!","Fehler", JOptionPane.ERROR_MESSAGE);
        } // end of if-else
      } else {                                // schiffe setzen  
        returnWert=this.setSchiff(i,k,name); 
      } // end of if-else
    } else {
    } // end of if-else 
    if (returnWert==3) {
      schuss=1;
    } // end of if
    return returnWert;
  }
  
  public boolean getGo(){
    return this.go;
  }
  
  public int vergleich(String nam, int x ,int y){              // schaut ob auf dem Feld ein schiff ist 
    int returnWert=2;
    if (this.pl1(nam))  {
      int schiff= this.daten.getPL2(x,y);
      if (schiff== 1) {
        returnWert= 3 ;
      } // end of if
    } else {
      int schiff= this.daten.getPL1(x,y);
      if (schiff== 1) {
        returnWert= 3;
      } // end of if
    } // end of if-else
    return returnWert;
  }
  
  public void endVergleich(){              // schaut ob ein speiler Gewonnen hat 
    boolean pl1Win=true;
    for (int i=1;i<this.daten.getLaenge() ;i++ ) {             // geht alle Felder durch und schuat ob noch iens belegt ist
      for (int k=1;k<this.daten.getLaenge() ;k++ ) {
        if (this.daten.getPL2(k,i)==1) {                       //sp1 schaut auf feld von Sp2
          pl1Win=false;
        } // end of if
      } // end of for
    } // end of for
    boolean pl2Win=true;
    for (int i=1;i<this.daten.getLaenge() ;i++ ) {           // geht alle Felder durch und schuat ob noch iens belegt ist
      for (int k=1;k<this.daten.getLaenge() ;k++ ) {
        if (this.daten.getPL1(k,i)==1) {                     //sp2 schaut auf feld von Sp1
          pl2Win=false;
        } // end of if
      } // end of for
    } // end of for
    if (pl1Win) {                                        // Gibt eine Nachricht aus
      JOptionPane.showMessageDialog(null,"Gl�ckwunsch "+this.nameSpieler+" hat Gewonnen ","Ergebnis", JOptionPane.PLAIN_MESSAGE);
    } 
    if (pl2Win) {
      JOptionPane.showMessageDialog(null,"Gl�ckwunsch "+this.nameSpieler+" hat Gewonnen ","Ergebnis", JOptionPane.PLAIN_MESSAGE);
    } // end of if
    // schlie�t die Zwei Fenster der Spieler und �ffent Men� einf�gen 
  }
  
  public boolean pl1(String n){
    boolean b=false; 
    if (n.equals("Player 1")) {
      b= true;
    }
    return b;
  }
  
  public void setGr(int g){
    gr=g;
  }
  
  public int setSchiff(int k,int i, String name ){                      //k=y i=x
    int wert=0;
    if (this.gr==0) {
      JOptionPane.showMessageDialog(null,"Suchen Sie sich ein Schiff aus","Ergebnis", JOptionPane.PLAIN_MESSAGE); 
    } else {
      boolean lage = this.spieler.getLage();
      wert=1;
      if (pl1(name)) {                                    // bei Spieler1 schiffe setzen  
        boolean umgebung =this.schauUmgebungSP1(k,i);
        if (umgebung==true) {                            // wenn die umgebung nicht frei ist keine �nderung der Farbe
          wert=0; 
          this.zeigeSchiffFalschGesetzt();
        } else {
          this.ueberRandSp1(k,i,lage);             // schaut wegen Rand uns setzt schiffe    
        } // end of if-else
      }else {                                           // bei Spieler2 schiffe setzen 
        boolean umgebung =this.schauUmgebungSP2(k,i);
        if (umgebung==true) {                            // wenn die umgebung nicht frei ist keine �nderung der Farbe
          wert=0; 
          this.zeigeSchiffFalschGesetzt();
        } else {
          this.ueberRandSp2(k,i,lage);            // schaut wegen Rand uns setzt schiffe
        } // end of if-else
      } // end of if-else
      
      
      this.gr=0;                                          
    } // end of if-else
    return wert;
  }
  
  public void setSchuss(){
    this.schuss=1;
  }
  
  public void ueberRandSp1(int y,int x,boolean lage){     //Speichert Schiffe in DAten 
    if (lage==true) {                                 // lage senkrecht 
      if (gr+y<=this.daten.getLaenge()) {              // schaut ob das Schiff nicht �ber den rand geht 
        for (int j=0;j<gr ;j++ ) {
          this.spieler.setFarbe(y+j,x,1);             // �ndert Farbe des Felds 
          this.daten.addPL1(y+j,x,1);                 // setzt schiff bei den DAten
        } // end of for
      } else {                                        // wenn ja anderes herum platzieren 
        for (int j=0;j<gr ;j++ ) {
          this.spieler.setFarbe(y-j,x,1);             // �ndert Farbe des Felds
          this.daten.addPL1(y-j,x,1);                 // setzt schiff bei den DAten
        } // end of for
      } // end of if-else
    } else {                                         // lage wagerecht  
      if (x+gr<=this.daten.getLaenge()) {                                 // schaut ob das Schiff �ber den rand geht 
        for (int j=0;j<gr ;j++ ) {
          this.spieler.setFarbe(y,x+j,1);            // �ndert Farbe des Felds
          this.daten.addPL1(y,x+j,1);               // setzt schiff bei den DAten
        } // end of for 
      } else {                                      // wenn ja dann das Schiff anders herum paltzieren 
        for (int j=0;j<gr ;j++ ) {
          this.spieler.setFarbe(y,x-j,1);            // �ndert Farbe des Felds
          this.daten.addPL1(y,x-j,1);                // setzt schiff bei den DAten
        } // end of for
      } // end of if-else
    } // end of if-else 
  }
  
  public void ueberRandSp2(int y, int x ,boolean lage){
    if (lage==true) {                               // lage senkrecht     
      if (gr+y<=this.daten.getLaenge()) {            // schauen ob schiff �ber den rand geht 
        for (int j=0;j<gr ;j++ ) {
          this.spieler.setFarbe(y+j,x,1);
          this.daten.addPL2(y+j,x,1);
        } // end of for
      } else {
        for (int j=0;j<gr ;j++ ) {
          this.spieler.setFarbe(y-j,x,1);
          this.daten.addPL2(y-j,x,1);
        } // end of for
      } // end of if-else
    } else {
      if (gr+x<=this.daten.getLaenge()){             // lage waagerecht 
        for (int j=0;j<gr ;j++ ) {                  // schauen ob das Schiff �ber den rand geht 
          this.spieler.setFarbe(y,x+j,1);
          this.daten.addPL2(y,x+j,1);
        } // end of for
      } else {
        for (int j=0;j<gr ;j++ ) {
          this.spieler.setFarbe(y,x-j,1);
          this.daten.addPL2(y,x-j,1);
        } // end of for
      } // end of if-else
    } // end of if-else
  }
  
  public void setname(String nameSp){
    this.nameSpieler=nameSp;
  }
  
  public boolean schauUmgebungSP1(int i, int k ){                                //Schaut das kein schiff in der umgebung ist       //y , x wert 
    boolean belegt=false;                                                        // variable ob frei ist 
    boolean lage = this.spieler.getLage();
    if (lage ==true) {                                                           //lage = senkrecht 
      if (i+gr+1<this.daten.getLaenge()) {                                       //funktioniert
        for (int y=k-1;y<=k+1 ;y++ ) {
          for (int x=i-1;x<i+this.gr+1 ;x++ ) {
            if ((x<=0||x>=this.daten.getLaenge())||(y<=0||y>=this.daten.getLaenge())) {
              
            } else {
              if (this.daten.getPL1(x,y)>0) {
                belegt=true;
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } else {
        for (int x=k-1;x<=k+1 ;x++ ) {                                            //funktioniert 
          for (int y=i-gr;y<i+1 ;y++ ) {
            if ((x<=0||x>=this.daten.getLaenge())||(y<=0||y>this.daten.getLaenge())) {
              
            } else {
              if (this.daten.getPL1(y,x)>0) {
                belegt=true;
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } // end of if-else
    } else {                                                                      //funktioniert 
      if (k+gr<this.daten.getLaenge()) {
        for (int x=k-1;x<k+gr+1 ;x++ ) {
          for (int y=i-1;y<=i+1 ;y++ ) {
            if ((x<=0||x>this.daten.getLaenge())||(y<=0||y>=this.daten.getLaenge())) {
              
            } else {
              if (this.daten.getPL1(y,x)>0) {
                belegt=true;
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } else {                                                                   //funktioniert  
        for (int x=k+1;x>k-gr ;x-- ) {
          for (int y=i-1;y<=i+1 ;y++ ) {
            if ((x<=0||x>=this.daten.getLaenge())||(y<=0||y>=this.daten.getLaenge())) {
              
            } else {
              if (this.daten.getPL1(y,x)>0) {
                belegt=true;
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for);
      } // end of if-else
    } // end of if-else
    if (belegt==true) {
      JOptionPane.showMessageDialog(null,"In der n�heren Umgebung ist schon ein Schiff ","Ergebnis", JOptionPane.ERROR_MESSAGE);
    } // end of if
    return belegt;
  }
  
  public void zeigeNichtGetroffen(){                     // zeigt alles schiffe die nicht oder nur zum teil getroffen worden sind 
    
  }
  
  public boolean schauUmgebungSP2(int i, int k ){                                //Schaut das kein schiff in der umgebung ist    1wert=x wert 2wert=ywert
    boolean belegt=false;                                                     // variable ob frei ist 
    boolean lage = this.spieler.getLage();
    if (lage ==true) {                                                           //lage = senkrecht 
      if (i+gr+1<this.daten.getLaenge()) {                                       //funktioniert
        for (int y=k-1;y<=k+1 ;y++ ) {
          for (int x=i-1;x<i+this.gr+1 ;x++ ) {
            if ((x<=0||x>=this.daten.getLaenge())||(y<=0||y>=this.daten.getLaenge())) {
              
            } else {
              if (this.daten.getPL2(x,y)>0) {
                belegt=true;
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } else {
        for (int x=k-1;x<=k+1 ;x++ ) {                                            //funktioniert 
          for (int y=i-gr;y<i+1 ;y++ ) {
            if ((x<=0||x>=this.daten.getLaenge())||(y<=0||y>this.daten.getLaenge())) {
              
            } else {
              if (this.daten.getPL2(y,x)>0) {
                belegt=true;
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } // end of if-else
    } else {                                                                      //funktioniert 
      if (k+gr<this.daten.getLaenge()) {
        for (int x=k-1;x<k+gr+1 ;x++ ) {
          for (int y=i-1;y<=i+1 ;y++ ) {
            if ((x<=0||x>this.daten.getLaenge())||(y<=0||y>=this.daten.getLaenge())) {
              
            } else {
              if (this.daten.getPL2(y,x)>0) {
                belegt=true;
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } else {                                                                   //funktioniert  
        for (int x=k+1;x>k-gr ;x-- ) {
          for (int y=i-1;y<=i+1 ;y++ ) {
            if ((x<=0||x>=this.daten.getLaenge())||(y<=0||y>=this.daten.getLaenge())) {
              
            } else {
              if (this.daten.getPL2(y,x)>0) {
                belegt=true;
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for);
      } // end of if-else
    } // end of if-else
    if (belegt==true) {
      JOptionPane.showMessageDialog(null,"In der n�heren Umgebung ist schon ein Schiff ","Ergebnis", JOptionPane.ERROR_MESSAGE);
    } // end of if
    return belegt;
  }
  
  
  public void resetDaten(String s){                     //l�scht daten und die bereits gesetzten schiffe 
    if (this.pl1(s)) {
      for (int i=0;i<this.daten.getLaenge() ;i++ ) {
        for (int k=0;k<this.daten.getLaenge() ;k++ ) {
          this.daten.addPL1(i,k,0);
          this.spieler.setFarbeWeis(i,k);
        } // end of for
      } // end of for
    } else {
      for (int i=0;i<this.daten.getLaenge() ;i++ ) {
        for (int k=0;k<this.daten.getLaenge() ;k++ ) {
          this.daten.addPL2(i,k,0);
          this.spieler.setFarbeWeis(i,k);
        } // end of for
      } // end of for
    } // end of if-else
  }
  
  public void zeigeSchiffFalschGesetzt(){
    switch (gr) {
      case  2: 
      if (this.spieler.getSchiff21Visible()==false) {
        this.spieler.zeigSchiff21();
      } else {
        this.spieler.zeigSchiff22();
      } // end of if-else
      break;
      case  3: 
      this.spieler.zeigSchiff3();
      break;
      case  4: 
      this.spieler.zeigSchiff4();
      break;
      case  5: 
      this.spieler.zeigSchiff5();
      break;
      default: 
      
    } // end of switch
  }
  
  boolean schauAlleSchiffeGesetzt(){
    boolean alleSchiffeGesetzt=true;
    for (int b=0;b<this.spieler.getLaengeVisibility() ;b++ ) {
      if (this.spieler.alleSchiffeWeg(b)) {
        alleSchiffeGesetzt=false;
      } // end of if
    } // end of for
    return   alleSchiffeGesetzt;
  }
  
}