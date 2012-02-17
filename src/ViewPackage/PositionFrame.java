/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;

/**
 *
 * @author gillm
 */
import javax.swing.*;
import java.awt.*;


public class PositionFrame extends JPanel
{
   private JLabel[] imageArray = new JLabel[12];
   public int pos = 11;
   public String size;
   private ImageIcon[] icons = new ImageIcon[2];
   private boolean large;
   
   public PositionFrame(String size){
     this.large = (size.equals("large")) ? true : false;
     this.setLayout(null);
     this.setBackground(new Color(255,255,255));
     
     if(large)
        this.setBounds(5, 50, 550, 20);
     else
        this.setBounds(5, 50, 275, 10); 
     
     icons[0] = new ImageIcon(large ? path("/Resources/Position_Off.png") : path("/Resources/Position_Off_Small.png"));
     icons[1] = new ImageIcon(large ? path("/Resources/Position_On.png") : path("/Resources/Position_On_Small.png"));

     
     createItems();
   } 
   
   private void createItems(){
       for(int i = 0; i < imageArray.length; i ++){
         imageArray[i] = new JLabel();
         imageArray[i].setIcon(icons[0]);
         if(large)
            imageArray[i].setBounds(57 + (42 * i), 0, 20, 20);
         else
            imageArray[i].setBounds(28 + (20 * i), -5, 20, 20);             
         this.add(imageArray[i]);
       }
   }
   
   public void step(){
       if(pos != -1)
        imageArray[pos].setIcon(icons[0]);
       pos = (pos == -1) ? 0 : pos + 1;
       pos = (pos == 12) ? 0 : pos;

       imageArray[pos].setIcon(icons[1]);
       
   }
   
   public void stop(){
       imageArray[pos].setIcon(icons[0]);
       pos = -1;
   }
   
   public String path(String uri){
        return this.getClass().getResource(uri).toString().replace("file:", "");
    }
}
