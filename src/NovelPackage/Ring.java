/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package NovelPackage;

import java.awt.Color;
import java.awt.Shape;
import java.util.Vector;

public class Ring {
    private Vector<String> Labels;
    private Vector<Double> Values;
    private Vector<Color> Colors;
    public Vector<Boolean> enabled;
    private double x;
    private double y;
    private double radius;
    private double ringwidth;
    private double start;
    private double end;
    private Shape[] segments;
    
    
    public Ring(){
        Labels = new Vector<String>();
        Values = new Vector<Double>();
        Colors = new Vector<Color>();
        enabled = new Vector<Boolean>();
        this.start = 0;
        this.end = 360;
        segments = null;
    }
    
    public void setStart(double start){
        this.start = start;
    }
    
    public void setEnd(double end){
        this.end = end;
    }
    
    public double getValue(int i){
        if(this.segments==null)
            this.createSegments();
        return Values.get(i);
    }
    
    public int count(){
        if(this.segments==null){
            this.createSegments();
        }
        return Values.size();
    }
    
    public String getLabel(int index){
        return Labels.get(index);
    }
    
    public Color getColor(int index){
        return Colors.get(index);
    }
    
    public void setActive(int index){
        Colors.set(index, Color.black);
    }
    
    public void setAsEnabled(int index){
        Colors.set(index, new Color(243,125,122));
    }
    
    public void setAsActiveRow(int index){
        Colors.set(index, new Color(230, 230, 230));
    }
    
    public void setAsInactiveRow(int index){
        Colors.set(index, new Color(255, 255, 255));
    }    
    
    
    public void toggleStatus(int index){
        
        if(enabled.get(index) == true){
            enabled.set(index, false);
            setAsDisabled(index);
        } else {
            enabled.set(index, true);
            setAsEnabled(index);
        }
    }
    
    
    
    
    public void setAsDisabled(int index){
        Colors.set(index, new Color(255,255,255));
    }
    
    public void setMousedOver(int index){
        Colors.set(index, new Color(243,125,123));
    }
    
    public void setMouseOut(int index){
        if((!Colors.get(index).equals(new Color(230, 230, 230)))  &&  (!Colors.get(index).equals(new Color(243,125,122))))
            Colors.set(index, new Color(255,255,255));
    }   
    
    public void mouseOut(){
        int i = 0;
        for(boolean act : enabled){
            if(act == false) setMouseOut(i);
            i ++;
        }
    }
    
    public void setCenter(double x, double y){
        this.x = x; 
        this.y = y;
    }

    public void setRadius(double radius){
        this.radius = radius;
    }
    
    public void setRingWidth(double width){
        this.ringwidth = width;
    }
    
    public void addItem(String label, double val, Color color){
        Labels.add(label);
        Values.add(val);
        Colors.add(color);
        enabled.add(false);
    }
    
    public Shape getSegment(int index){
        if(this.segments==null){
            this.createSegments();
        }
                        
        return segments[index];
    }
    
    public void createSegments(){
        Shape[] shapes = new Shape[Values.size()];
        double sum = 0;
        double span = this.end - this.start;
        for(int i=0; i<Values.size(); i++){
            sum += Values.get(i);
        }
        
        double strt = this.start;
        for(int i=0; i<Values.size(); i++){
            double extent = (Values.get(i) / sum) *span;
            ArcSegment arc = new ArcSegment();
            shapes[i] = arc.Create(this.x,this.y,this.radius,this.ringwidth,strt,extent);
            strt += extent;
        }
        
        this.segments = shapes;
        
    }
}


