/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NovelPackage;

import ViewPackage.*;

import java.awt.*;
import java.util.Vector;

import javax.swing.JPanel;
import javax.swing.JToolTip;
import javax.swing.ToolTipManager;

import java.awt.event.*;

public class RingChart extends JPanel implements ComponentListener, MouseMotionListener, MouseListener{
    Vector<Ring> rings;
    
    public int mouseover;
    
    public NovelSequencerFrame parent;

    public RingChart(NovelSequencerFrame parent) {
        this.parent = parent;
        
        setOpaque(false);
        addComponentListener(this);
        rings = new Vector<Ring>();
 
        ToolTipManager.sharedInstance().registerComponent(this);
        addMouseMotionListener(this);
        addMouseListener(this);
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D)g;
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
        Stroke s = new BasicStroke(1.25f);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, getWidth(), getHeight());
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setStroke(s);
    
        Insets insets = this.getInsets();
        int h = this.getHeight() - insets.top - insets.bottom;
        int w = this.getWidth() - insets.left - insets.right;
        int m = Math.min(h, w);

        for(int r = 0; r < rings.size(); r++){
            Ring ring = rings.get(r);
            double rw = ((double)m / ((double)rings.size()+1))/2;
            ring.setRingWidth(rw);
            ring.setRadius(2*rw +rw*(double)r);
            ring.setCenter((double)this.getWidth()/2.0, (double)this.getHeight()/2.0);
            ring.createSegments();          

            for (int index = 0; index < ring.count(); index++) {
                g2.setColor(ring.getColor(index));
                g2.fill(ring.getSegment(index));
                g2.setColor(Color.BLACK);
                g2.draw(ring.getSegment(index));
            }
        }
    
    }
    
    public JToolTip createToolTip(){
        JToolTip tooltip = super.createToolTip();
        tooltip.setBackground(new Color(255,255,140,128));
        this.repaint(); // TODO: will this bog us down?
        return tooltip;
    }
    
    

    public String getToolTipText(java.awt.event.MouseEvent e) {
        
        for(int r = 0; r < rings.size(); r++){
            Ring ring = rings.get(r);
            for (int index = 0; index < ring.count(); index++) {
                if (ring.getSegment(index).contains(e.getPoint())) {
                    return ring.getLabel(index)+": "+ring.getValue(index);
                }
            }
        }
        return super.getToolTipText(e);
    }

    public void setRings(Vector<Ring> rings){
        this.rings = rings;
    }
    
    @Override
    public void componentHidden(ComponentEvent arg0) {  }

    @Override
    public void componentMoved(ComponentEvent arg0) {   }

    @Override
    public void componentResized(ComponentEvent arg0) {
        repaint();

        
    }

    @Override
    public void componentShown(ComponentEvent arg0) {   }    
    
    
    @Override
    public void mouseMoved(MouseEvent e) {
        int start = mouseover;
        for(int r = 0; r < rings.size(); r++){
            Ring ring = rings.get(r);
            ring.mouseOut();
            for (int index = 0; index < ring.count(); index++) {
                if (ring.getSegment(index).contains(e.getPoint())) {
                    ring.setMousedOver(index);
                    break;
                }
            }
            parent.redraw();
        }
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

        @Override
    public void mouseEntered(MouseEvent e) {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
    }
    @Override
    public void mousePressed(MouseEvent e){
    }
    @Override
    public void mouseClicked(MouseEvent e){
         for(int r = 0; r < rings.size(); r++){
            Ring ring = rings.get(r);
            for (int index = 0; index < ring.count(); index++) {
                if (ring.getSegment(index).contains(e.getPoint())) {
                    System.out.println("Clicked "+ring.getLabel(index));
                    ring.toggleStatus(index);
                    parent.redraw();
                    return;
                }
            }
        }
    }

}

