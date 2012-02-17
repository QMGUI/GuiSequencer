package ModelPackage;

import  sun.audio.*;    //import the sun.audio package
import  java.io.*;

public class TonePlayer{

    public String url;
    private InputStream in;
    private AudioStream as;
    
    public TonePlayer(String url){
        this.url = "/Resources/"+url+".wav";
        this.playTone();
    }
    
    public void playTone(){
        try {
            this.in = new FileInputStream(url);
            this.as = new AudioStream(in);
        } catch (IOException e) {
                e.printStackTrace();
        } 
        AudioPlayer.player.start(as); 
    }
   
}




