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
import net.beadsproject.beads.core.AudioContext;

public class ToneSquare extends RoundedPanel implements MouseListener {

    public String toneLetter = "ZZ";
    public JLabel letterLabel = new JLabel("ZZ");
    public boolean hover = false;
    public int pitch;
    public double volume = 0.5;
    public int length = 200;
    private boolean large;
    public TheMusicPlayer<ToneSquare> player;
    public SequencerFrame parent;

    public ToneSquare(String toneLetter, int pitch, double volume, int length, String size, AudioContext a, SequencerFrame parent) {
        super((size.equals("small") ? true : false));
        this.parent = parent;
        large = size.equals("small") ? false : true;
        this.toneLetter = toneLetter;
        this.pitch = pitch;
        this.volume = volume;
        this.length = length;
        letterLabel.setText(toneLetter);
        letterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        letterLabel.setForeground(Color.white);
        letterLabel.setBackground(new Color(255, 183, 119));
        if (large) {
            letterLabel.setBounds(0, 8, 42, 24);
        } else {
            letterLabel.setBounds(((toneLetter.length() == 1) ? 5 : 1), -4, 24, 24);
        }
        this.setBackground(new Color(255, 183, 119));
        this.add(letterLabel);
        addMouseListener(this);
        this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        this.player = new TheMusicPlayer<ToneSquare>(toneLetter, a, pitch, size, this);
    }

    public void mouseEntered(MouseEvent e) {
        this.setBackground(new Color(190, 225, 139));
    }

    public void mouseExited(MouseEvent e) {
        this.setBackground(new Color(255, 183, 119));
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mousePressed(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
        JPanel panel = player.modify();

        panel.setBounds(this.getX(), this.getX(), this.parent.getWidth(), this.parent.getHeight());
        this.parent.add(panel);

        for (Component a : this.parent.getComponents()) {
            if ((a.getClass().toString()).indexOf("ToneModifier") == -1) {
                this.parent.remove(a);
                this.parent.add(a);
            }
        }


        this.parent.repaint();
    }

    public void makeNoise() {
        // tone.playTone();
        try {
            player.play();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
