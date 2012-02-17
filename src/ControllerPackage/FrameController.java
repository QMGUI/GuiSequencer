/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPackage;
import ViewPackage.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
/**
 *
 * @author gillm
 */
public class FrameController {
    
    public JFrame window;
    private JLabel title;
    public JPanel sequencerFrame;
    public JPanel positionFrame;
    public JPanel menu;
    public JPanel browser;
    
    public String type;
    public String size;
    private boolean large;
    public Timer timer;
    public int delay = 500;
    public ActionListener taskPerformer;
    public boolean initialised = false;
    
    public final Color WHITE = new Color(255,255,255);
    
    public FrameController(String title, String type, String size){
        this.type = type;
        this.size = size;
        this.large = size.equals("large") ? true : false;
        //this.browser = new ViewSaved(size);
        
        window = new JFrame();
        
        
        
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        if(large)
            window.setSize(640, 480);
        else
            window.setSize(320, 240);
        window.setLayout(null);
        window.setBackground(WHITE);
        
        positionFrame = new PositionFrame(size);
        sequencerFrame = (type.equals(("conventional"))) ? new SequencerFrame(size, this) :  new NovelSequencerFrame(size, this);
        
        
        menu = new MenuPanel(size, this);
        window.add(sequencerFrame);
        window.add(positionFrame);
        window.add(menu);
        
        addTitle();
        
          
        taskPerformer = new ActionListener() {
              public void actionPerformed(ActionEvent evt) {
                    FrameController t = FrameController.this;
                    if(t.type.equals("conventional")){
                        ((PositionFrame)positionFrame).step();
                        ((SequencerFrame)sequencerFrame).step();
                    } else {
                         ((PositionFrame)positionFrame).step();
                         ((NovelSequencerFrame)sequencerFrame).step();                       
                    }
                    initialised = true;
                }
        };
        
        timer = new Timer(delay, taskPerformer);
        timer.stop();
    }
    
    public void showFrame(){
        window.setVisible(true);
    }
    
    public void addTitle(){
        title = new JLabel();
        if(type.equals("conventional"))
            title.setText("Music Sequencer - Conventional Mode");
        else if(type.equals("novel"))
            title.setText("Music Sequencer - Novel Mode");
        else
            throw new IllegalArgumentException("Invalid type. Accepted values: conventional, novel");
        title.setFont(new Font("Segoe UI", Font.PLAIN, large ? 18 : 14));
        title.setBounds(15, 15, 400, 25);
        window.add(title);
    }
    
    
    
    
}
