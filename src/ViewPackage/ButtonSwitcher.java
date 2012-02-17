/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;
import ModelPackage.*;

/**
 *
 * @author gillm
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class ButtonSwitcher extends JPanel implements MouseListener
{
    private JLabel img = new JLabel();
    private ImageIcon onImg;
    private ImageIcon offImg;
    public ManualAction action;
    private boolean image;
    public JLabel text;
    private Color colorOff;
    private Color colorOn;
    private JLabel textLabel;
    public boolean toggle = false;
    boolean wanted = true;
    public String actionCommand = "image";

    public ButtonSwitcher(String off, String on, ManualAction action){
        addMouseListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setLayout(null);
        onImg = new ImageIcon(on);
        
        
        offImg = new ImageIcon(off);

        this.img.setIcon(offImg);
        this.img.setBounds(0, 0, offImg.getIconWidth(), offImg.getIconHeight());
        this.add(this.img);
        this.setBounds(0, 0, offImg.getIconWidth(), offImg.getIconHeight());
        this.action = action;
        image = true;
    }
    
    public ButtonSwitcher(String off, String on, ManualAction action, String actionCommand){
        this(off, on, action);
        this.actionCommand = actionCommand;
    } 
    
    public ButtonSwitcher(String off, String on, ManualAction action, boolean wanted){
        this(off, on, action);
        this.wanted = wanted;
    }
      
    public ButtonSwitcher(String txt, ManualAction action){
        addMouseListener(this);
        this.actionCommand = txt;
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setLayout(null);
        
        
        textLabel = new JLabel("<html><center><b>"+txt+"</b></center></html>", SwingConstants.CENTER);
        textLabel.setForeground(new Color(153, 153, 153));
        textLabel.setFont(textLabel.getFont().deriveFont(14.0f ));
        
        onImg = new ImageIcon(path("/Resources/btn_large_on.png"));
        offImg = new ImageIcon(path("/Resources/btn_large_off.png"));
        
        
        this.img.setIcon(offImg);
        this.add(textLabel);
        this.img.setBounds(0, 0, offImg.getIconWidth(), offImg.getIconHeight());
        this.textLabel.setBounds(1, 0, offImg.getIconWidth(), offImg.getIconHeight());
        
        this.add(this.img);
        
        this.setBounds(0, 0, offImg.getIconWidth(), offImg.getIconHeight());
        this.action = action;
        image = true;
    }
    
    public ButtonSwitcher(String txt, Color c1, Color c2, ManualAction action){
        addMouseListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.setLayout(null);
        this.colorOff = c1;
        this.colorOn = c2;
        this.action = action;
        text = new JLabel("<html><center>"+txt+"</center></html>", SwingConstants.CENTER);
        text.setBounds(-10, 1, 100, 45);
        this.add(text);
        image = false;
        this.setBackground(colorOff);
        this.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220)));
    }
        
    @Override
    public void mouseEntered(MouseEvent e) {
        if(image) 
            this.img.setIcon(onImg);
        else
            this.setBackground(colorOn);
        if(textLabel != null)
            textLabel.setForeground(new Color(0, 51, 102));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if(image)
            this.img.setIcon(offImg);
        else
            this.setBackground(colorOff);
        
        if((image)&&(toggle && wanted)){
            this.img.setIcon(onImg);
        }
        
        if((textLabel != null) && (!toggle && wanted))
            textLabel.setForeground(new Color(153, 153, 153));
    }
    
    public void offAction(){
        if(image)
            this.img.setIcon(offImg);
        else
            this.setBackground(colorOff);
        
        if(textLabel != null)
            textLabel.setForeground(new Color(153, 153, 153));     
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
    }
    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseClicked(MouseEvent e){
        this.toggle = (this.toggle) ? false : true;
        if(action != null) this.action.click();
    }
    
    public String path(String uri){
        return this.getClass().getResource(uri).toString().replace("file:", "");
    }
}

