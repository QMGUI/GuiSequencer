/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

/**
 *
 * @author gillm
 */
import ViewPackage.*;
import javax.swing.*;
import net.beadsproject.beads.core.*;
import net.beadsproject.beads.data.*;
import net.beadsproject.beads.ugens.*;

public class TheMusicPlayer <T> {

    public String url;
    public Sample selectedSample;
    public AudioContext ac;
    public float rate;
    public float volume;
    public String type = "debug";
    public boolean played = false;
    public int semitones;
    public String size;
    public T parent;
    public JPanel modifier = new JPanel();

    public TheMusicPlayer(String toneLetter, AudioContext a, int pitchOffset, String size, T parent) {
        this("piano.wav", pitchOffset, 1, a);
        this.size = size;
        this.url = toneLetter;
        this.parent = parent;
    }

    public TheMusicPlayer(String type, int semitones, float vol, AudioContext a) {
        ac = a;
        this.selectedSample = SampleManager.sample(path("/Resources/" + type));
        this.semitones = semitones;
        this.rate = new PitchRatioCalculator().semitoneRatio(261, semitones);
        this.volume = vol;
        this.type = type;
    }

    public void modTone(String type, int semitones, float vol, String txt) {
        this.selectedSample = SampleManager.sample(path("/Resources/" + type));
        this.semitones = semitones;
        this.rate = new PitchRatioCalculator().semitoneRatio(261, semitones);
        this.volume = vol;
        this.type = type;
        ((ToneSquare)parent).toneLetter = txt;
        ((ToneSquare)this.parent).letterLabel.setText(txt);
    }

    public void play() {
        SamplePlayer samplePlayer = new SamplePlayer(ac, selectedSample);

        samplePlayer.getPitchEnvelope().setValue(rate);

        samplePlayer.setKillOnEnd(true);
        float gainLevel = volume;
        Gain gain = new Gain(ac, 1, new Envelope(ac, gainLevel));
        gain.addInput(samplePlayer);
        gain.setKillListener(samplePlayer);
        ac.out.addInput(gain);
        ac.start();
        played = true;
    }

    public String path(String uri) {
        return this.getClass().getResource(uri).toString().replace("file:", "");
    }

    public JPanel modify() {
        modifier.setSize(551, 372);
        //modifier.setVisible(true);

        ToneModifier mod = new ToneModifier(this);
        modifier.add(mod);
        mod.setBounds(0,0,551,372);
        
        return mod;
        
        
        //((ToneSquare)this.parent).parent.remove(mod);
    }

    class PitchRatioCalculator {

        public float centRatio(double initialSamplePitch, double centIncrement) {

            double ratio;
            double targetPitch;

            targetPitch = initialSamplePitch * Math.pow(2.0, (centIncrement / 1200.0));
            ratio = targetPitch / initialSamplePitch;
            return (float) ratio;
        }

        public float semitoneRatio(double initialSamplePitch, double semitoneIncrement) {
            return centRatio(initialSamplePitch, semitoneIncrement * 100);
        }
    }
}