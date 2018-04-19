import javax.swing.JOptionPane;
public class SteuerungSchiffeversenken{
  private Schiffeversenken gui;
  private DatenSchiffeversenken daten; 
  
  public SteuerungSchiffeversenken(Schiffeversenken pGUI){
    gui = pGUI;
    daten = new DatenSchiffeversenken();
  }
  
  public DatenSchiffeversenken getDaten(){
    return daten;
  }
  
  
  public void schliessen(){
    int result = JOptionPane.showConfirmDialog(null, "Soll das Programm geschlossen werde?", "Schlieﬂen", JOptionPane.YES_NO_OPTION);
    if (result == JOptionPane.YES_OPTION) {
      System.exit(0)  ;
      this.gui.close();
    } else  {
      
    } 
  }
}