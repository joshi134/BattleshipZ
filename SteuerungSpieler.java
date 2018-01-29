import java.util.*;
import javax.swing.JOptionPane;
public class SteuerungSpieler{
  private Spieler spieler;
  private DatenSchiffeversenken daten ;
  private boolean go=false; 
  private int gr=0;
  private int schuss=0;
  private String nameSpieler="";
  
  
  public void schließen(){
    int result = JOptionPane.showConfirmDialog(null, "Soll das Programm geschlossen werde?", "Schließen", JOptionPane.YES_NO_CANCEL_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      System.exit(0)  ;
    } else if (result == JOptionPane.NO_OPTION) {
      
    }  
  }
  
  public  SteuerungSpieler(Spieler sp){
    spieler = sp;
  }
  
  public void setDaten(DatenSchiffeversenken da){
    daten = da; 
  }
  
  public void zeige(String name){                         // macht die eingesetzetn schiffe unsichtbar 
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
  
  public int gedrueckt(int i ,int k, String name, String text){    // ändert die Farbe der Felder 
    int returnWert=0;
    if (text.equals("")) {
      if (go) {                               // schaune ob getroffen und gewonnen
        if (schuss==1) {
          schuss=0;
          returnWert= this.vergleich(name,k,i);   
          if (this.pl1(name)) {                    //sp1 schiesst auf feld von Sp2
            this.daten.addPL2(k,i,returnWert);
          } else {                                 //sp2 schiesst auf feld von Sp1
            this.daten.addPL1(k,i,returnWert);
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
      JOptionPane.showMessageDialog(null,"Glückwunsch "+this.nameSpieler+" hat Gewonnen ","Ergebnis", JOptionPane.PLAIN_MESSAGE);
    } 
    if (pl2Win) {
      JOptionPane.showMessageDialog(null,"Glückwunsch "+this.nameSpieler+" hat Gewonnen ","Ergebnis", JOptionPane.PLAIN_MESSAGE);
    } // end of if
    // schließt die Zwei Fenster der Spieler und öffent Menü einfügen 
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
  
  public int setSchiff(int k,int i, String name ){
    int wert=0;
    if (this.gr==0) {
      JOptionPane.showMessageDialog(null,"Suchen Sie sich ein Schiff aus","Ergebnis", JOptionPane.PLAIN_MESSAGE); 
    } else {
      boolean lage = this.spieler.getLage();
      wert=1;
      if (pl1(name)) {                                    // bei Spieler1 schiffe setzen  
        boolean umgebung =this.schauUmgebungSP1(i,k);
        this.ueberRandSp1(i,k,lage,umgebung);             // schaut wegen Rand uns setzt schiffe    if
        if (umgebung==true) {                            // wenn die umgebung nicht frei ist keine änderung der Farbe
          wert=0; 
          this.zeigeSchiffFalschGesetzt();
        } // end of if
      }else {                                           // bei Spieler2 schiffe setzen 
        boolean umgebung =this.schauUmgebungSP2(i,k);
        this.ueberRandSp2(i,k,lage,umgebung);            // schaut wegen Rand uns setzt schiffe
        if (umgebung==true) {                            // wenn die umgebung nicht frei ist keine änderung der Farbe
          wert=0; 
          this.zeigeSchiffFalschGesetzt();
        } // end of if
      } // end of if-else
      
      
      this.gr=0;                                          
    } // end of if-else
    return wert;
  }
  
  public void setSchuss(){
    this.schuss=1;
  }
  
  public void ueberRandSp1(int y,int x,boolean lage,boolean umbegung){
    if (lage==true) {                                 // lage senkrecht 
      if (gr+y<=this.daten.getLaenge()) {              // schaut ob das Schiff nicht über den rand geht 
        if (umbegung==false) {
          for (int j=0;j<gr ;j++ ) {
            this.spieler.setFarbe(y+j,x,1);             // ändert Farbe des Felds 
            this.daten.addPL1(y+j,x,1);                 // setzt schiff bei den DAten
          } // end of for
        } else { 
          JOptionPane.showMessageDialog(null,"In der näheren Umgebung ist schon ein Schiff ","Ergebnis", JOptionPane.ERROR_MESSAGE); 
        } // end of if-else 
      } else {                                        // wenn ja anderes herum platzieren 
        for (int j=0;j<gr ;j++ ) {
          this.spieler.setFarbe(y-j,x,1);             // ändert Farbe des Felds
          this.daten.addPL1(y-j,x,1);                 // setzt schiff bei den DAten
        } // end of for
      } // end of if-else
    } else {                                         // lage wagerecht  
      if (x+gr<=this.daten.getLaenge()) {                                 // schaut ob das Schiff über den rand geht 
        if (umbegung==false) {
          for (int j=0;j<gr ;j++ ) {
            this.spieler.setFarbe(y,x+j,1);            // ändert Farbe des Felds
            this.daten.addPL1(y,x+j,1);               // setzt schiff bei den DAten
          } // end of for
        } else {
          JOptionPane.showMessageDialog(null,"In der näheren Umgebung ist schon ein Schiff ","Ergebnis", JOptionPane.ERROR_MESSAGE); 
        } // end of if-else 
      } else {                                      // wenn ja dann das Schiff anders herum paltzieren 
        for (int j=0;j<gr ;j++ ) {
          this.spieler.setFarbe(y,x-j,1);            // ändert Farbe des Felds
          this.daten.addPL1(y,x-j,1);                // setzt schiff bei den DAten
        } // end of for
      } // end of if-else
    } // end of if-else 
  }
  
  public void ueberRandSp2(int y, int x ,boolean lage,boolean umgebung){
    if (lage==true) {                               // lage senkrecht
      if (umgebung==false) {
        if (gr+y<=this.daten.getLaenge()) {            // schauen ob schiff über den rand geht 
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
        JOptionPane.showMessageDialog(null,"In der näheren Umgebung ist schon ein Schiff ","Ergebnis", JOptionPane.ERROR_MESSAGE); 
      } // end of if-else
    } else {
      if (umgebung==false) {
        if (gr+x<=this.daten.getLaenge()){             // lage waagerecht 
          for (int j=0;j<gr ;j++ ) {                  // schauen ob das Schiff über den rand geht 
            this.spieler.setFarbe(y,x+j,1);
            this.daten.addPL2(y,x+j,1);
          } // end of for
        } else {
          for (int j=0;j<gr ;j++ ) {
            this.spieler.setFarbe(y,x-j,1);
            this.daten.addPL2(y,x-j,1);
          } // end of for
        } // end of if-else
      } else {
        JOptionPane.showMessageDialog(null,"In der näheren Umgebung ist schon ein Schiff ","Ergebnis", JOptionPane.ERROR_MESSAGE); 
      } // end of if-else
    } // end of if-else
  }
  
  public void setname(String nameSp){
    this.nameSpieler=nameSp;
  }
  
  public boolean schauUmgebungSP1(int i, int k ){                                //Schaut das kein schiff in der umgebung ist       //x , y wert 
    boolean belegt=false;                                                     // variable ob frei ist 
    boolean lage = this.spieler.getLage();
    if (lage==true ) {                                                       // lage senkrecht 
      if (gr+i<=this.daten.getLaenge()) {
        for (int j=k-1;j<k+gr+1 ;j++ ) {                                       // geht alle herum liegenden Felder durch 
          for (int h=i-1;h<i+1 ;h++ ) {
            if (j<=0||j>=this.daten.getLaenge()||h<=0||h>=this.daten.getLaenge()) { // schauen ob umliegende position nicht auserhalb ist 
              
            } else {
              int schiffDa =this.daten.getPL1(h,j);                             // schaut ob schon ein schiff da ist
              if (schiffDa>0) {
                belegt=true;                                                    // wenn schiff das ist anzeige das belegt ist 
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } else {
        for (int j=k+1;j<k-gr-1 ;j-- ) {                                       // geht alle herum liegenden Felder durch 
          for (int h=i+1;h<i-1 ;h-- ) {
            if (j<=0||j>=this.daten.getLaenge()||h<=0||h>=this.daten.getLaenge()) { // schauen ob umliegende position nicht auserhalb ist 
              
            } else {
              int schiffDa =this.daten.getPL1(h,j);                             // schaut ob schon ein schiff da ist
              if (schiffDa>0) {
                belegt=true;                                                    // wenn schiff das ist anzeige das belegt ist 
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } // end of if-else
      
    } else {                                                                  // lage waagerecht 
      if (gr+k<=this.daten.getLaenge()) {
        for (int h=k-1;h<k+1 ;h++ ) {                                       // geht alle herum liegenden Felder durch 
          for (int j=i-1;j<i+gr+1 ;j++ ) {
            if (j<=0||j>=this.daten.getLaenge()||h<=0||h>=this.daten.getLaenge()) { // schauen ob umliegende position nicht auserhalb ist 
              
            } else {
              int schiffDa =this.daten.getPL1(j,h);                             // schaut ob schon ein schiff da ist
              if (schiffDa>0) {
                belegt=true;                                                    // wenn schiff das ist anzeige das belegt ist 
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } else {
        for (int h=k+1;h<k-1 ;h-- ) {                                       // geht alle herum liegenden Felder durch 
          for (int j=i+1;j<i-gr-1 ;j-- ) {
            if (j<=0||j>=this.daten.getLaenge()||h<=0||h>=this.daten.getLaenge()) { // schauen ob umliegende position nicht auserhalb ist 
              
            } else {
              int schiffDa =this.daten.getPL1(j,h);                             // schaut ob schon ein schiff da ist
              if (schiffDa>0) {
                belegt=true;                                                    // wenn schiff das ist anzeige das belegt ist 
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } // end of if-else
      
    } // end of if-else
    return belegt; 
  }
  
  public void zeigeNichtGetroffen(){                     // zeigt alles schiffe die nicht oder nur zum teil getroffen worden sind 
    
  }
  
  public boolean schauUmgebungSP2(int i, int k ){                                //Schaut das kein schiff in der umgebung ist    1wert=x wert 2wert=ywert
    boolean belegt=false;                                                     // variable ob frei ist 
    boolean lage = this.spieler.getLage();
    if (lage==true ) {                                                       // lage senkrecht 
      if (gr+i<=this.daten.getLaenge()) {
        for (int j=k-1;j<k+gr+1 ;j++ ) {                                       // geht alle herum liegenden Felder durch 
          for (int h=i-1;h<i+1 ;h++ ) {
            if (j<=0||j>=this.daten.getLaenge()||h<=0||h>=this.daten.getLaenge()) { // schauen ob umliegende position nicht auserhalb ist 
              
            } else {
              int schiffDa =this.daten.getPL2(h,j);                             // schaut ob schon ein schiff da ist
              if (schiffDa>0) {
                belegt=true;                                                    // wenn schiff das ist anzeige das belegt ist 
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } else {
        for (int j=k+1;j<k-gr-1 ;j-- ) {                                       // geht alle herum liegenden Felder durch 
          for (int h=i+1;h<i-1 ;h-- ) {
            if (j<=0||j>=this.daten.getLaenge()||h<=0||h>=this.daten.getLaenge()) { // schauen ob umliegende position nicht auserhalb ist 
              
            } else {
              int schiffDa =this.daten.getPL2(h,j);                             // schaut ob schon ein schiff da ist
              if (schiffDa>0) {
                belegt=true;                                                    // wenn schiff das ist anzeige das belegt ist 
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } // end of if-else
      
    } else {                                                                  // lage waagerecht 
      if (gr+k<=this.daten.getLaenge()) {
        for (int h=k-1;h<k+1 ;h++ ) {                                       // geht alle herum liegenden Felder durch 
          for (int j=i-1;j<i+gr+1 ;j++ ) {
            if (j<=0||j>=this.daten.getLaenge()||h<=0||h>=this.daten.getLaenge()) { // schauen ob umliegende position nicht auserhalb ist 
              
            } else {
              int schiffDa =this.daten.getPL2(j,h);                             // schaut ob schon ein schiff da ist
              if (schiffDa>0) {
                belegt=true;                                                    // wenn schiff das ist anzeige das belegt ist 
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } else {
        for (int h=k+1;h<k-1 ;h-- ) {                                       // geht alle herum liegenden Felder durch 
          for (int j=i+1;j<i-gr-1 ;j-- ) {
            if (j<=0||j>=this.daten.getLaenge()||h<=0||h>=this.daten.getLaenge()) { // schauen ob umliegende position nicht auserhalb ist 
              
            } else {
              int schiffDa =this.daten.getPL2(j,h);                             // schaut ob schon ein schiff da ist
              if (schiffDa>0) {
                belegt=true;                                                    // wenn schiff das ist anzeige das belegt ist 
              } // end of if
            } // end of if-else
          } // end of for
        } // end of for
      } // end of if-else
      
    } // end of if-else
    return belegt; 
  }
  
  public void resetDaten(String s){                     //löscht daten und die bereits gesetzten schiffe 
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
  
}