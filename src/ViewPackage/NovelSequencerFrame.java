/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;
import ControllerPackage.FrameController;
import NovelPackage.*;

import java.awt.*;
import java.awt.Dimension;
import java.util.Vector;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

public class NovelSequencerFrame extends JPanel {

    public Vector<Ring> rings = new Vector<Ring>();
    JPanel chart = new RingChart(this);
    public boolean init = false;
    public int pos = 11;
    public String size;
    public FrameController parent;
    
    public NovelSequencerFrame(String size, FrameController fc){
        this.setLayout(null);
        this.size = size;
        this.parent = fc;
        this.setBackground(new Color(255, 255, 255));
        
        if(size.equals("large"))
            this.setBounds(5, 80, 551, 392);
        else if(size.equals("small"))
            this.setBounds(5, 60, 265, 147);
        
        
        for(int i = 0; i < 10; i ++){
            Ring ring = new Ring();
            ring.setStart(0);
            ring.setEnd(360);
            for(int j = 0; j < 12; j ++){
                 ring.addItem("Row "+i+", note number "+j, 40, Color.white);
            }
            rings.add(ring);
        }

        this.setPreferredSize(new Dimension(400, 400));
        
        
        redraw();
        init = true;

    }
    
    public void step() {
        pos = (pos == -1) ? 0 : pos + 1;
        pos = (pos == 12) ? 0 : pos;

        //pos is row
        int prev = (pos - 1);
        prev = (prev < 0) ? 11 : prev;


        for (int i = 0; i < 9; i++) {
            
            if(!rings.get(i).enabled.get(pos)){
                rings.get(i).setAsActiveRow(pos);
            } else {
                //make noise
                System.out.println("Note number: "+i);
            }
            
            if(!rings.get(i).enabled.get(prev)){
                rings.get(i).setAsInactiveRow(prev);
            }
            
        }
        this.redraw();
    }

    public void redraw(){
        
       this.remove(chart);
        
        chart = new RingChart(this);
        ((RingChart)chart).setRings(rings);
        Border border = new EmptyBorder(5,5,5,5);
        
       
        chart.setBounds(0,0, this.getWidth(), this.getHeight() - 30);
        

        ((RingChart)chart).setRings(rings);
        this.add(chart);
        this.repaint();
    }
}
