/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ModelPackage;

/**
 *
 * @author gillm
 */

import java.io.*;
import net.beadsproject.beads.core.*;
import net.beadsproject.beads.data.*;
import net.beadsproject.beads.ugens.*;


public class Player implements Runnable {

    public String url;
    public Sample selectedSample;
    public AudioContext ac;
    public float rate;
    public float volume;
    public String type = "debug";

    public Player(String url) {
       // this.url = path("/Resources/" + url + ".wav");
       // ac = new AudioContext();
       // selectedSample = SampleManager.sample(this.url);
        this("piano", 1, 1);
    }
    
    public Player(String type, int semitones, int vol){
        ac = new AudioContext();
        if(type.equals("piano")){
            selectedSample = SampleManager.sample(path("/Resources/C.wav")); 
            
            this.rate = new PitchRatioCalculator().semitoneRatio(440, semitones);
            this.volume = vol;
            this.type = type;

        }
    }

    public void run() {
        SamplePlayer samplePlayer = new SamplePlayer(ac, selectedSample);
        if(type.equals("piano"))
            samplePlayer.getPitchEnvelope().setValue(rate);
        samplePlayer.setKillOnEnd(true);
        float gainLevel = 1f;
        Gain gain = new Gain(ac, 1, new Envelope(ac, gainLevel));
        gain.addInput(samplePlayer);
        gain.setKillListener(samplePlayer);
        ac.out.addInput(gain);
        ac.start();
    }

    public void play() throws FileNotFoundException {
      new Thread(this).start();
    }

    public String path(String uri) {
      return this.getClass().getResource(uri).toString().replace("file:", "");
    }
    
    
    
    class PitchRatioCalculator {
	public float centRatio(double initialSamplePitch, double centIncrement){
		
		double ratio;
		double targetPitch;
		
		targetPitch = initialSamplePitch * Math.pow(2.0, (centIncrement / 1200.0 ));
		ratio = targetPitch / initialSamplePitch;		
		return (float)ratio;
	}

	public float semitoneRatio(double initialSamplePitch, double semitoneIncrement){
		return centRatio(initialSamplePitch, semitoneIncrement*100);	
	}		
    }

}