import java.util.*;
import javax.swing.JOptionPane;

public class KI{
  private int letztesX;
  private int letztesY;
  private int randomX;
  private int randomY;
  private boolean go= false;
  private String nameSpieler="KI"; 
  private DatenSchiffeversenken daten ;
  private boolean lage;
  private int schuss=1;
  private int[] groessen= {5,4,3,2,2};
  private int indexRichtung=0;             // 0=hoch, 1=runter, 2=links, 3=rechts
  private int[] richtung=new int[4];       // 0= noch nicht vewrsucht, 1=versucht; 2=Treffer
  private boolean first=false;
  private KIGui kiGui;
  
  
  
  public  KI(KIGui gui){
    kiGui=gui;
  }
  
  public int getlaengeArray(){
    return this.groessen.length;
  }
  public int getIndex(int o){
    return  this.groessen[o];
  }
  public boolean getGo(){
    return this.go;
  }   
  
  public void setDaten(DatenSchiffeversenken da){
    daten = da; 
  }
  
  public void schiessen(){    // schieﬂt auf das Feld von spieler1
    while(schuss>0) {
      schuss=0; 
      if (first==false) {            //random schuss                                     
        this.zufall();
        this.schauTreffer(this.randomX,this.randomY);
        if (first==true) {
          this.zumTrefferpunkt();
          this.indexRichtung= ((int) (Math.random()*4));
        } // end of if
      } else {
        switch (this.indexRichtung) {
          case 0: 
          this.schiessHoch();
          break;
          case 1: 
          this.schiessRunter();
          break;
          case 2: 
          this.schiessLinks();
          break;
          case 3: 
          this.schiessRechts();
          break;
          default: 
        } // end of switch  
        if ((this.richtung[0]==1 && this.richtung[1]==2) || (this.richtung[0]==2 && this.richtung[1]==1)||(this.richtung[2]==2 && this.richtung[3]==1)||(this.richtung[2]==1 && this.richtung[3]==2)) {        // neu random schieﬂen schiff versenkt 
          for (int u=0;u<this.richtung.length ;u++ ) {
            this.richtung[u]=0;
          } // end of for
          first=false;
        } // end of if
      } // end of if-else 
      
    } 
    this.schuss=1;                                 
  }
  
  private void zumTrefferpunkt(){
    this.letztesX=this.randomX;
    this.letztesY=this.randomY;
    System.out.println(this.randomX);
  }
  
  private void schiessRunter(){
    if (this.letztesY-1>0) {                                   //schuss nach unten wenn mˆglich
      this.letztesY--;                                         // ‰ndert Y-Posttion nach unten 
      this.schauTreffer(this.letztesX ,this.letztesY); 
      this.indexRichtung=1;                                   // sagt letzter schuss war nach unten
      if (this.daten.getPL1(this.letztesX,this.letztesY)==0) {//wenn kein treffer dann zufall richtung
        if (this.richtung[1]==2) {                            // wenn schon mal ein treffer in der Richtung dann gegenrichtung
          this.indexRichtung=0;
        } else {
          int z= ((int) (Math.random()*4));
          while (this.richtung[z]>=1) {                       //schauen welche richtung noch nicht versucht wurde
            z= ((int) (Math.random()*4));
          } // end of while
          this.indexRichtung=z;
        } // end of if-else
      } else {
        this.richtung[1]=2;                                   // in die richtung wurde schon getroffen 
      } // end of if-else
    } else {                                                  // wenn nicht schuss nach oben 
      this.richtung[1]=1;                                     // sagt nach unten wurde schaut
      this.zumTrefferpunkt();
      this.schiessHoch();
    } // end of if-else
  }
  
  private void schiessHoch(){
    if (this.letztesY+1<10) {                             // schuﬂ nach oben wenn mˆglich  
      this.letztesY++;                                     // ‰ndert Y-Postion nach oben
      this.schauTreffer(this.letztesX ,this.letztesY);  
      this.indexRichtung=0;                               //sagt letzter schuss war nach oben 
      if(this.daten.getPL1(this.letztesX,this.letztesY)==0){//wenn kein treffer dann zufall richtung
        if (this.richtung[0]==2) {                            // wenn schon mal ein treffer in der Richtung dann gegenrichtung
          this.indexRichtung=1;
        } else {
          int z= ((int) (Math.random()*4));
          while (this.richtung[z]>=1) {                       //schauen welche richtung noch nciht versucht wurde
            z= ((int) (Math.random()*4));
          } // end of while
          this.indexRichtung=z;
        } // end of if-else
      } else {
        this.richtung[0]=2;                                   // in die richtung wurde schon getrofeen 
      } // end of if-else
    } else {                                              // wenn nicht nach unten
      this.richtung[0]=1;                                 // nach oben wurde versucht 
      this.zumTrefferpunkt();
      this.schiessRunter();
    } // end of if-else
  }
  
  private void schiessRechts(){
    if (this.letztesX+1<10) {                            // schuﬂ nach rechts wenn mˆglich
      this.letztesX++;                                    //ƒndert X-Postition nach rechts
      this.schauTreffer(this.letztesX ,this.letztesY);
      this.indexRichtung=3;                               //sagt letzter schuss war nach Rechts
      if(this.daten.getPL1(this.letztesX,this.letztesY)==0) {//wenn kein treffer dann zufall richtung
        if (this.richtung[3]==2) {                            // wenn schon mal ein treffer in der Richtung dann gegenrichtung
          this.indexRichtung=2;
        } else {
          int z= ((int) (Math.random()*4));
          while (this.richtung[z]>=1) {                       //schauen welche richtung noch nciht versucht wurde
            z= ((int) (Math.random()*4));
          } // end of while
          this.indexRichtung=z;
        } // end of if-else
      } else {
        this.richtung[3]=2;                                   // in die richtung wurde schon getrofeen 
      } // end of if-else
    } else {                                             // wenn nicht nach unten 
      this.richtung[3]=1;                                // nach Rechts wurde versucht 
      this.zumTrefferpunkt();
      this.schiessLinks();
    } // end of if-else
  }
  
  private void schiessLinks(){                              
    if (this.letztesX-1>0) {                               //schuﬂ nach Links wenn mˆglich 
      this.letztesX--;                                     //ƒndert X-Postition nach links 
      this.schauTreffer(this.letztesX ,this.letztesY);
      this.indexRichtung=3;                               //sagt letzter schuss war nach Links
      if(this.daten.getPL1(this.letztesX,this.letztesY)==0) {//wenn kein treffer dann zufall richtung
        if (this.richtung[2]==2) {                            // wenn schon mal ein treffer in der Richtung dann gegenrichtung
          this.indexRichtung=3;
        } else {
          int z= ((int) (Math.random()*4));
          while (this.richtung[z]>=1) {                       //schauen welche richtung noch nciht versucht wurde
            z= ((int) (Math.random()*4));
          } // end of while
          this.indexRichtung=z;
        } // end of if-else
      } else {
        this.richtung[2]=2;                                   // in die richtung wurde schon getrofeen 
      } // end of if-else  
    } else {                                               // wenn nicht nach rechts 
      this.richtung[2]=1;                                 // links wurde versucht 
      this.zumTrefferpunkt();
      this.schiessRechts();
    } // end of if-else
  }
  
  private void schauTreffer(int Rx,int Ry){
    if (this.daten.getPL1(Rx, Ry)==0) {          //NichtsPlatziert
      this.kiGui.setFeldColor(Rx,Ry,2);
      schuss=0;
    } // end of if
    if (this.daten.getPL1(Rx, Ry)==1) {         //schiff da Treffer 
      this.kiGui.setFeldColor(Rx,Ry,3);
      schuss=1;
      this.letztesX=Rx;
      this.letztesY=Ry;
      first= true;
    } // end of if
    if (this.daten.getPL1(Rx, Ry)>1) {         // schon draufgeschossen 
      schuss=1;
    } // end of if
  }
  
  private  void setSchiff(){                      //k=y i=x
    int gr=0;
    for (int i=0;i<this.getlaengeArray() ;i++ ) {
      gr=this.groessen[i];
      this.randomX = ((int) (Math.random()*10))+1;
      this.randomY =  ((int) (Math.random()*10))+1;
      int randomlage= ((int) (Math.random()*100))+1;
      if (randomX%2==0) {
        this.lage=false;
      } else {
        this.lage=true;
      } // end of if-else
      if (this.schauUmgebungKI(randomY,randomX)==false) {
        this.speichereSchiffeKI(randomY,randomX,gr);
        //System.out.println(randomX+" , "+randomY);
      } else {
        i--;
      } // end of if-else
    } // end of for
    this.go=true;
    this.warten();
  }
  
  private  void endVergleich(){              // schaut ob ein speiler Gewonnen hat 
    boolean kiWin=true;
    for (int i=1;i<this.daten.getLaenge() ;i++ ) {             // geht alle Felder durch und schuat ob noch iens belegt ist
      for (int k=1;k<this.daten.getLaenge() ;k++ ) {
        if (this.daten.getPL1(k,i)==1) {                       //sp1 schaut auf feld von Sp2
          kiWin=false;
        } // end of if
      } // end of for
    } // end of for
    if (kiWin) {                                        // Gibt eine Nachricht aus
      JOptionPane.showMessageDialog(null,"Gl¸ckwunsch "+this.nameSpieler+" hat Gewonnen ","Ergebnis", JOptionPane.PLAIN_MESSAGE);
    } 
  }
  
  public  void setSchuss(){
    this.schuss=1;
  }
  
  private void speichereSchiffeKI(int y,int x, int gr){
    if (lage==true) {                                 // lage senkrecht 
      if (gr+y<=this.daten.getLaenge()) {              // schaut ob das Schiff nicht ¸ber den rand geht 
        for (int j=0;j<gr ;j++ ) {
          this.daten.addPL2(y+j,x,1);                 // setzt schiff bei den DAten
        } // end of for
      } else {                                        // wenn ja anderes herum platzieren 
        for (int j=0;j<gr ;j++ ) {                                                                                  
          this.daten.addPL2(y-j,x,1);                 // setzt schiff bei den DAten
        } // end of for
      } // end of if-else
    } else {                                         // lage wagerecht  
      if (x+gr<=this.daten.getLaenge()) {                                 // schaut ob das Schiff ¸ber den rand geht 
        for (int j=0;j<gr ;j++ ) {
          this.daten.addPL2(y,x+j,1);               // setzt schiff bei den DAten
        } // end of for 
      } else {                                      // wenn ja dann das Schiff anders herum paltzieren 
        for (int j=0;j<gr ;j++ ) {                                                                                          
          this.daten.addPL2(y,x-j,1);                // setzt schiff bei den DAten
        } // end of for
      } // end of if-else
    } // end of if-else 
  }
  
  private  boolean schauUmgebungKI(int i, int k ){                                //Schaut das kein schiff in der umgebung ist  //y , x wert 
    boolean belegt=false;
    int gr=0;                                                                   
    if (lage ==true) {                                                           //lage = senkrecht 
      if (i+gr+1<this.daten.getLaenge()) {                                       //funktioniert
        for (int y=k-1;y<=k+1 ;y++ ) {
          for (int x=i-1;x<i+gr+1 ;x++ ) {
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
    return belegt;
  }
  
  private void warten(){
    for (int i=0;i<100000 ;i++ ) {
      for (int k=0;k<1000 ;k++ ) {
        
      } // end of for
    } // end of for
  } 
  
  public void drueckButton(){
    if (this.go==true) {
      this.schiessen();
      this.endVergleich();
    } else {
      this.zufall();
      this.setSchiff();
    } // end of if-else
  }
  
  private void zufall(){
    this.randomX = ((int) (Math.random()*10))+1;
    this.randomY =  ((int) (Math.random()*10))+1;
  } 
  
  public int getX(){
    return this.randomX;
  }
  
  public int getY(){
    return this.randomY;
  } 
  
  public void schlieﬂen(){
    int result = JOptionPane.showConfirmDialog(null, "Zur¸ck zum Hauptmen¸?", "Schlieﬂen", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      this.kiGui.close();
    } else  {
      
    }     
  }      
}