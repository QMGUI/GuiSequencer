/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;

import ModelPackage.*;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.*;

/**
 *
 * @author gillm
 */
public class ToneModifier extends JPanel implements MouseMotionListener, ActionListener {

    public TheMusicPlayer parent;
    private String[] soundFiles = {"piano.wav", "kick.aif", "snare.aif", "hat.wav", "crash.wav", "kick.wav", "step.wav", "unbelievable.wav"};
    private JButton[] btns = new JButton[soundFiles.length];
    private JSlider pitchMod = new JSlider(-10, 10);
    private JSlider volMod = new JSlider(0, 10);
    private JButton save = new JButton("Save");
    private JButton cancel = new JButton("Cancel");
    private JTextField txt = new JTextField();

    public ToneModifier(TheMusicPlayer parent) {
        this.parent = parent;
        this.setLayout(null);
        

        this.setBackground(Color.white);
        if (parent.size.equals("large")) {
            this.setBounds(0, 0, 551, 372);
        }

        JLabel title = new JLabel("Modify settings for tonesquare " + parent.url);




        int i = 0;
        for (String label : soundFiles) {

            btns[i] = new JButton(label.substring(0, label.length() - 4));
            btns[i].setBounds((i * 70), 40, 70, 30);
            btns[i].setActionCommand(label);
            if (label.equals(parent.type)) {
                btns[i].setBackground(Color.yellow);
                btns[i].setOpaque(true);
            }

            btns[i].addActionListener(this);
            this.add(btns[i]);
            i++;
            this.repaint();
        }



        pitchMod.setBounds(10, 100, 300, 40);

        volMod.setBounds(10, 150, 300, 40);
        txt.setBounds(10, 200, 200, 20);
        txt.setText(((ToneSquare)parent.parent).toneLetter);
        cancel.setActionCommand("CANCEL");
        cancel.addActionListener(this);
        cancel.setBounds(200, 250, 70, 50);
        save.setActionCommand("SAVE");
        save.addActionListener(this);

        save.setBounds(10, 250, 70, 50);
        title.setBounds(10, 2, 300, 40);
        this.add(title);
        this.add(pitchMod);
        this.add(volMod);
        this.add(save);
        this.add(txt);
        this.add(cancel);
    }

    @Override
    public void mouseDragged(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void mouseMoved(MouseEvent me) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        if (ae.getActionCommand().equals("SAVE")) {
            String newType = "piano";
            String newTxt = txt.getText();
            int newSemitones = pitchMod.getValue();
            float newVol = volMod.getValue();
            for (int i = 0; i < soundFiles.length; i++) {
                if (btns[i].getBackground().equals(Color.yellow)) {
                    newType = btns[i].getActionCommand();
                    break;
                }
            }

            parent.modTone(newType, newSemitones, newVol, newTxt);
        }   else if (ae.getActionCommand().equals("CANCEL")) {
            ((ToneSquare)this.parent.parent).parent.remove(this);
            ((ToneSquare)this.parent.parent).parent.repaint();

        } else {

            int clickedBtn = 0;
            for (int i = 0; i < soundFiles.length; i++) {
                if (soundFiles[i].equals(ae.getActionCommand())) {
                    clickedBtn = i;
                    break;
                }
            }
            for (int i = 0; i < soundFiles.length; i++) {
                if (i != clickedBtn) {
                    btns[i].setBackground(null);
                    btns[i].setOpaque(false);
                } else {
                    btns[i].setBackground(Color.yellow);
                    btns[i].setOpaque(true);
                }
            }
        }

    }
}
