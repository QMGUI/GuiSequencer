/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;

/**
 *
 * @author gillm
 */
import ControllerPackage.FrameController;
import ModelPackage.ManualAction;
import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
public class MenuPanel extends JPanel
{  
   public JPanel bpmUpArrow;
   public JPanel bpmDownArrow;
   private int bpm = 120;
   public JLabel bpmText = new JLabel();
   public String size;
   private FrameController parent;
   public boolean toggle = false;
   public boolean manageToggle = false;
   public JPanel manageSequencesButton;
   
   public MenuPanel(String size, FrameController fc){
     this.setLayout(null);
     this.setBackground(new Color(255,255,255));
     this.setBounds(558, 0, 80, 480);
     this.size = size;
     this.parent = fc;

     
     bpmUpArrow = new ButtonSwitcher(path("/Resources/up_arrow_large.png"), path("/Resources/up_arrow_large_on.png"), new ManualAction(){
            public void click(){
                MenuPanel t = MenuPanel.this;
                t.modBPM("up");
            }
        }, false);
     bpmDownArrow = new ButtonSwitcher(path("/Resources/down_arrow_large.png"), path("/Resources/down_arrow_large_on.png"),  new ManualAction(){
            public void click(){
                MenuPanel t = MenuPanel.this;
                t.modBPM("down");
            }
        }, false);
     
     bpmUpArrow.setBounds(1, 260, bpmUpArrow.getWidth(), bpmUpArrow.getHeight());
     bpmDownArrow.setBounds(1, 300, bpmUpArrow.getWidth(), bpmUpArrow.getHeight());
     
     bpmText.setText(bpm + " BPM");
     bpmText.setBounds(10, 255, 80, 60);
     
     this.add(bpmText);
     this.add(bpmDownArrow);
     this.add(bpmUpArrow);
     
     JPanel openExistingButton = new ButtonSwitcher("Open Existing", new ManualAction(){
            public void click(){
                MenuPanel t = MenuPanel.this;
                t.toggle = (t.toggle) ? false : true;
                
                if(toggle) {

                    t.parent.browser = new ViewSaved(t.size, ((SequencerFrame)t.parent.sequencerFrame), false);
                    t.parent.browser.setBounds(t.parent.sequencerFrame.getX() - 5, t.parent.sequencerFrame.getY()-80, t.parent.sequencerFrame.getWidth(), t.parent.sequencerFrame.getHeight());
                    t.parent.sequencerFrame.add(t.parent.browser);
                    for (Component a : t.parent.sequencerFrame.getComponents()) {
                        if ((a.getClass().toString()).indexOf("ViewSaved") == -1) {
                            t.parent.sequencerFrame.remove(a);
                            t.parent.sequencerFrame.add(a);
                        }
                    }
                } else {
                    t.parent.sequencerFrame.remove(t.parent.browser);
                }
                t.parent.sequencerFrame.repaint();
                t.parent.window.repaint();
                
            }
        });
     openExistingButton.setBounds(0, 10, 80, 50);
     
     JPanel playPauseButton = new ButtonSwitcher(path("/Resources/playpause.png"), path("/Resources/playpause_on.png"),  new ManualAction(){
            public void click(){
                MenuPanel t = MenuPanel.this;
                t.pauseToggle();
            }
        }, "playpause"); 
     playPauseButton.setBounds(0, 80, 80, 50);
     
     JPanel saveSequenceButton = new ButtonSwitcher("Save Sequence",  new ManualAction(){
            public void click(){
                MenuPanel t = MenuPanel.this;
                if(t.parent.type.equals("conventional")){
                    ((SequencerFrame)t.parent.sequencerFrame).save(JOptionPane.showInputDialog(null, "Enter nickname (2 chars)"), JOptionPane.showInputDialog(null, "Enter full name (20 chars)"), (new SimpleDateFormat("EEE, MMM d, ''yy")).format(new Date()), String.valueOf(System.currentTimeMillis()), t.bpm);
                } else
                    System.out.println("NOT YET IMPLEMENTED");
                
            }
        });
     saveSequenceButton.setBounds(0, 132, 80, 50);

     manageSequencesButton = new ButtonSwitcher("Manage Sequences", new ManualAction(){
            public void click(){
                   MenuPanel t = MenuPanel.this;
                t.manageToggle = (t.manageToggle) ? false : true;
                
                if(manageToggle) {

                    t.parent.browser = new ViewSaved(t.size, ((SequencerFrame)t.parent.sequencerFrame), true);
                    t.parent.browser.setBounds(t.parent.sequencerFrame.getX() - 5, t.parent.sequencerFrame.getY()-80, t.parent.sequencerFrame.getWidth(), t.parent.sequencerFrame.getHeight());
                    t.parent.sequencerFrame.add(t.parent.browser);
                    for (Component a : t.parent.sequencerFrame.getComponents()) {
                        if ((a.getClass().toString()).indexOf("ViewSaved") == -1) {
                            t.parent.sequencerFrame.remove(a);
                            t.parent.sequencerFrame.add(a);
                        }
                    }
                } else {
                    t.parent.sequencerFrame.remove(t.parent.browser);
                }
                t.parent.sequencerFrame.repaint();
                t.parent.window.repaint();
                
            }
        });
     manageSequencesButton.setBounds(0, 184, 80, 50);
     
     JPanel switchToAdvancedButton = new ButtonSwitcher(path("/Resources/switch_to_advanced.png"), path("/Resources/switch_to_advanced_on.png"), null);
     switchToAdvancedButton.setBounds(0, 343, 80, 50);
     JPanel helpButton = new ButtonSwitcher("Help", null);
     helpButton.setBounds(0, 398, 80, 50);
     this.add(switchToAdvancedButton);
     this.add(helpButton);
     this.add(openExistingButton);
     this.add(playPauseButton);
     this.add(openExistingButton);
     this.add(saveSequenceButton);
     this.add(manageSequencesButton);
     
   }
   
   private void modBPM(String direction){
       
       if(((bpm == 400) && direction.equals("up")) || ((bpm == 5) && direction.equals("down")))
           return;

       bpm += (direction.equals("up")) ? 5 : -5;
       
       setBPM(bpm);
       if(parent.initialised) parent.timer.start();
   }
   
   public void setBPM (int bpm) {      
       this.bpm = bpm;
       bpmText.setText(bpm + " BPM");
       parent.timer.stop();
       parent.delay = 60000 / bpm;
       parent.timer = new Timer(parent.delay, parent.taskPerformer);
       for(Component a : ((MenuPanel)parent.menu).getComponents()){
           if ((a.getClass().toString()).indexOf("ButtonSwitcher") > -1)
                if(((ButtonSwitcher)a).actionCommand.equals("playpause")){
                  ((ButtonSwitcher)a).offAction();
                  ((ButtonSwitcher)a).toggle = false;
                }
       }
   }
   
   private void pauseToggle(){
       if(parent.timer.isRunning())
          parent.timer.stop();
       else
          parent.timer.start();
   }

   public String path(String uri){
        return this.getClass().getResource(uri).toString().replace("file:", "");
    }
   
}
