/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ControllerPackage;
import ModelPackage.*;

import ViewPackage.*;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

/**
 *
 * @author gillm
 */
public class InterfaceChooser extends JFrame{
    
    public String size;
    private JLabel title = new JLabel();
    private JLabel subText = new JLabel(); 
    private JPanel conventionalButton;
    private JPanel novelButton;
    private JPanel exitButton;
    
    
    public InterfaceChooser(String size){
        this.setLayout(null);
        this.size = size;
        if(size.equals("large")) largeStyle();
        else if(size.equals("small")) smallStyle();
        else throw new IllegalArgumentException("Invalid argument for InterfaceChooser, valid: large/small, given: "+size);
        
        title.setText("Cascade Sequencer");
        subText.setText("Please select a theme.");
        populateFrame();
    }
    
    private void largeStyle(){
        title.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        title.setBounds(15, 15, 300, 25);
        subText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subText.setBounds(18, 35, 300, 25);
            
        
        conventionalButton = new ButtonSwitcher(path("/Resources/conventional_large_buttonbg_640.png"), path("/Resources/conventional_large_buttonbg_640_on.png"), new ManualAction() {
            @Override
                public void click(){
                    InterfaceChooser t = InterfaceChooser.this;
                    t.hide();
                    FrameController window = new FrameController("Music Sequencer", "conventional", "large");
                    window.showFrame();
                }
            } );
        conventionalButton.setBounds(64, 120, 512, 80);
        
        novelButton = new ButtonSwitcher(path("/Resources/novel_large_buttonbg_640.png"), path("/Resources/novel_large_buttonbg_640_on.png"),  new ManualAction() {               
            @Override
                public void click(){
                    InterfaceChooser t = InterfaceChooser.this;
                    t.hide();
                    FrameController window = new FrameController("Music Sequencer", "novel", "large");
                    window.showFrame();
                }
            } );
        novelButton.setBounds(64, 220, 512, 80);
        
        exitButton = new ButtonSwitcher(path("/Resources/exit_large.png"), path("/Resources/exit_large_on.png"), new ManualAction(){
            @Override
                public void click(){
                    System.exit(0);
                }
            });
        exitButton.setBounds(380, 380, 197, 50);

    }
    
    private void smallStyle(){
        title.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        title.setBounds(15, 15, 300, 25);
        subText.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subText.setBounds(18, 35, 300, 25);
        
        conventionalButton = new ButtonSwitcher(path("/Resources/conventional_small_buttonbg_320.png"), path("/Resources/conventional_small_buttonbg_320_on.png"), new ManualAction() {
            @Override
                public void click(){         
                    InterfaceChooser t = InterfaceChooser.this;
                    t.hide();
                    FrameController window = new FrameController("Music Sequencer", "conventional", "small");
                    window.showFrame();
                }
            } );
        conventionalButton.setBounds(32, 70, 256, 37);
        
        novelButton = new ButtonSwitcher(path("/Resources/novel_small_buttonbg_320.png"), path("/Resources/novel_small_buttonbg_320_on.png"),  new ManualAction() {               
            @Override
                public void click(){
                    InterfaceChooser t = InterfaceChooser.this;
                    t.hide();
                    FrameController window = new FrameController("Music Sequencer", "novel", "small");
                    window.showFrame();
                }
            } );
        novelButton.setBounds(32, 120, 256, 37);
        
        exitButton = new ButtonSwitcher(path("/Resources/exit_small.png"), path("/Resources/exit_small_on.png"), new ManualAction(){
                public void click(){
                    System.exit(0);
                }
            });
        exitButton.setBounds(192, 178, 96, 37);
    }
    
    private void populateFrame(){
        this.setBackground(Color.white);
        if(this.size.equals("large"))
            this.setSize(640, 480);
        else
            this.setSize(320, 240);
        
        this.setVisible(true);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.add(title);
        this.add(subText);
        this.add(conventionalButton);
        this.add(novelButton);
        this.add(exitButton);
        this.repaint();
    }
    
    public String path(String uri){
        return this.getClass().getResource(uri).toString().replace("file:", "");
    }
}
