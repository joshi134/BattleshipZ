public class DatenSchiffeversenken{
  private final  int feldlaenge =11;
  private int[][] spielfeld1 = new int[feldlaenge][feldlaenge];   // 0= leer 1= belegt 2= beschossenleer 3=beschossen getroffen  
  private int[][] spielfeld2 = new int[feldlaenge][feldlaenge];  
  private String NameSp1= "Player1";
  private String NameSp2= "Player2";
  
  
  public void addPL1(int i,int k , int wert){
    spielfeld1[i][k]=wert;  
  } 
  
  public void addPL2(int i,int k ,int wert){
    spielfeld2[i][k]=wert; 
  }
  
  public void nameSP1(String s){
    NameSp1=s; 
  } 
  
  public void nameSp2(String s){
    NameSp2 = s;
  }
  
  public String getNameSp1(){
    return NameSp1;    
  }
  public String getNameSp2(){
    return NameSp2;    
  }
  
  public int getPL1(int i, int k){                          //i =x K=y
    return spielfeld1[i][k] ;
  } 
  
  public int getPL2(int i, int k){
    return spielfeld2[i][k];
  }
  
  public int getLaenge(){
    return feldlaenge;
  }
  
  public void zeigeAlleBelegtenFelder(){
    for (int x1=0; x1<this.feldlaenge ;x1++ ) {
      for (int y1=0; y1<this.feldlaenge ;y1++ ) {
        System.out.print(this.getPL1(x1,y1)+" , ");
      } // end of for
      System.out.println(); 
    } // end of for
  }
  
}