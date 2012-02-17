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
import java.awt.event.*;

public class RolloverButton extends JPanel implements MouseListener
{
    public int stage = 0;
    JLabel currentImage = new JLabel();
    ImageIcon[] ico = new ImageIcon[3];
    String path;
public boolean toggle = false;

    public RolloverButton(String path){
        this.path = path;
        for(int i = 0; i < ico.length; i ++)
           ico[i] = new ImageIcon(path+i+".png");
        this.setBounds(0,0, ico[0].getIconWidth()+10, ico[0].getIconHeight()+20);
        addMouseListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        currentImage.setIcon(new ImageIcon(path+"0.png"));
        currentImage.setBounds(0,0,120,80);
        
        this.add(currentImage);
        
    }
    
    public void mouseEntered(MouseEvent e) {
       switch(stage){
        case 0 :
            currentImage.setIcon(ico[1]);
            break;
        case 1 :
            currentImage.setIcon(ico[2]);
            break;
        case 2 :
            currentImage.setIcon(ico[1]);
            break;
       }
    }

    public void mouseExited(MouseEvent e) {  
        currentImage.setIcon(ico[stage]);
    }
    
    public void mouseReleased(MouseEvent e){
    }
    public void mousePressed(MouseEvent e){
    }
    public void mouseClicked(MouseEvent e){
        switch(stage){
            case 0:
                stage = 1;
                break;
            case 1:
                stage = 2;
                break;
            case 2:
                stage = 1;
                break;
        }
        currentImage.setIcon(ico[stage]);
        toggle = (toggle) ? false : true;
    }
}
