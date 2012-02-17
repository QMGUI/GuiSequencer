/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;

/**
 *
 * @author gillm
 */
import java.awt.*;
import java.awt.event.*;
public class BooleanSquare extends RoundedPanel implements MouseListener
{
    public boolean isEnabled = false;
    public boolean isRow = false;
    
    public int x = 99;
    public int y = 99;

    public BooleanSquare(int x, int y, String size){
        super((size.equals("small") ? true : false));
        this.x = x;
        this.y = y;

        this.setBackground(new Color(255,255,255,255));
        addMouseListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }       
        
    @Override
    public void mouseEntered(MouseEvent e) {
       if(!isEnabled) this.setBackground(new Color(243,125,122));
    }

    @Override
    public void mouseExited(MouseEvent e) {
       if(!isEnabled) {
           if(!isRow)
             this.setBackground(new Color(255,255,255));
           else
             this.setBackground(new Color(240,240,240,240));
       }
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
    }
    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseClicked(MouseEvent e){
        isEnabled = (!isEnabled) ? true : false;
        if(isEnabled)
           this.setBackground(new Color(243,125,122));
        else
            this.setBackground(new Color(255,255,255));
    }
    
    public void setAsEnabled(){
        this.isEnabled = true;
        this.setBackground(new Color(243,125,122));
    }
    
    public void setAsDisabled(){
        this.isEnabled = false;
        this.setBackground(new Color(255,255,255));
    }
    
    public void play(){
        System.out.println("playing");
    }
}

