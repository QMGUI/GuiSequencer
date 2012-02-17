/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;

import ModelPackage.ManualAction;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.*;

/**
 *
 * @author gillm
 */
public class PanelButton extends JPanel implements MouseListener {

    private Color off = new Color(255, 183, 119);
    private Color on = new Color(190, 225, 139);


    public ManualAction action;
    
    public PanelButton(ManualAction m){
        this.setBackground(off);
        this.action = m;
        addMouseListener(this);
    }
    
    @Override
    public void mouseClicked(MouseEvent me) {
    }

    @Override
    public void mousePressed(MouseEvent me) {
        action.click();
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
        this.setBackground(on);
        this.repaint();
    }

    @Override
    public void mouseExited(MouseEvent me) {
        this.setBackground(off);
        this.repaint();
    }
    
}
