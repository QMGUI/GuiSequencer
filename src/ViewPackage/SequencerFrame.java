/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;

import ControllerPackage.FrameController;
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.InputStreamReader;
import net.beadsproject.beads.core.AudioContext;

/**
 *
 * @author gillm
 */
public class SequencerFrame extends JPanel {

    public AudioContext ac = new AudioContext();
    private JPanel[] toneSquares = new ToneSquare[9];
    private JPanel[][] booleanSquares = new BooleanSquare[12][9];
    public int pos = 11;
    public String size;
    public FrameController parent;

    public SequencerFrame(String size, FrameController fc) {
        this.setLayout(null);
        this.size = size;
        this.parent = fc;
        this.setBackground(new Color(255, 255, 255));

        if (size.equals("large")) {
            this.setBounds(5, 80, 551, 372);
        } else if (size.equals("small")) {
            this.setBounds(5, 60, 265, 147);
        }

        createColumns();

    }

    public void outall() {
        System.out.println("called");
    }

    private void createColumns() {
        int width = this.getWidth() / 13;
        int height = this.getHeight();
        int[] pitches = {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};
        String[] alphabet = {"C", "D", "E", "F", "G", "A", "B", "UC", "UD"};

        for (int i = 0; i < 12; i++) {
            //for each column
            for (int j = 0; j < 9; j++) {

                //for each row in column 
                booleanSquares[i][j] = new BooleanSquare(i, j, size);
                booleanSquares[i][j].setBounds((size.equals("large") ? 45 : width + 3) + (i * width), (j * (height / 9) + 1), width - 2, (height / 9) - 2);
                this.add(booleanSquares[i][j]);
            }
        }

        for (int i = 0; i < toneSquares.length; i++) {
            toneSquares[i] = new ToneSquare(alphabet[i], pitches[i], 0.5, 200, size, ac, this);
            toneSquares[i].setBounds(2, (i * (height / 9)), width, (height / 9));
            this.add(toneSquares[i]);

        }

        RoundedPanel booleanBackground = new RoundedPanel();
        booleanBackground.setBounds(0, -1, this.getWidth() - 1, this.getHeight());
        booleanBackground.setBackground(new Color(230, 230, 230));
        this.add(booleanBackground);
    }

    public void step() {
        pos = (pos == -1) ? 0 : pos + 1;
        pos = (pos == 12) ? 0 : pos;

        //pos is row
        int prev = (pos - 1);
        prev = (prev < 0) ? 11 : prev;

        for (int i = 0; i < booleanSquares[pos].length; i++) {

            ((BooleanSquare) booleanSquares[pos][i]).isRow = true;
            ((BooleanSquare) booleanSquares[prev][i]).isRow = false;

            if (((BooleanSquare) booleanSquares[pos][i]).isEnabled == false) {
                booleanSquares[pos][i].setBackground(new Color(240, 240, 240));
            } else {
                ((ToneSquare) toneSquares[i]).makeNoise();

            }
            if (((BooleanSquare) booleanSquares[prev][i]).isEnabled == false) {
                booleanSquares[prev][i].setBackground(Color.white);
            }
            this.repaint();
        }
    }

    private void lightsOut() {
        for (int i = 0; i < booleanSquares.length; i++) {
            for (int row = 0; row < 9; row++) {
                ((BooleanSquare) booleanSquares[i][row]).setAsDisabled();
            }

        }
        this.repaint();
    }

    public void load(String save) {

        lightsOut();

        save = (save.indexOf("/") > -1) ? save : path("/Storage/" + save + ".sav");

        String[] lines = getFileContent(save);
        int loadPoint;
        for (loadPoint = 0; loadPoint < lines.length; loadPoint++) {
            if (lines[loadPoint].equalsIgnoreCase("[bool]")) {
                break;
            }
        }

        int row = 0;
        for (int i = loadPoint + 1; i < loadPoint + 10; i++) {
            char[] bools = lines[i].toCharArray();
            for (int j = 0; j < 12; j++) {
                if (bools[j] == '1') {
                    ((BooleanSquare) booleanSquares[j][row]).setAsEnabled();
                }
            }
            row++;
        }

        for (int j = 0; j < 9; j++) {
            for (loadPoint = 0; loadPoint < lines.length; loadPoint++) {
                if (lines[loadPoint].equalsIgnoreCase("[note" + j + "]")) {
                    break;
                }
            }
            ((ToneSquare) toneSquares[j]).player.modTone(lines[loadPoint + 1], Integer.parseInt(lines[loadPoint + 2]), new Float(lines[loadPoint + 3]), lines[loadPoint + 4]);
        }


        for (loadPoint = 0; loadPoint < lines.length; loadPoint++) {
            if (lines[loadPoint].equalsIgnoreCase("[bpm]")) {
                break;
            }
        }

        setBPM(Integer.parseInt(lines[loadPoint + 1]));



    }

    private void setBPM(int bpm) {
        ((MenuPanel) parent.menu).setBPM(bpm);
    }

    public void save(String nickname, String fullname, String date, String savename, int bpm) {
        String lines = "[bool]\n";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 12; j++) {
                lines += ((BooleanSquare) booleanSquares[j][i]).isEnabled ? "1" : "0";
            }
            lines += "\n";
        }
        lines += "[bpm]\n";
        lines += bpm + "\n";
        lines += "[nickname]\n";
        lines += nickname + "\n";
        lines += "[fullname]\n";
        lines += fullname + "\n";
        lines += "[date]\n";
        lines += date + "\n";

        for (int i = 0; i < 9; i++) {
            lines += "[note" + i + "]\n"
                    + ((ToneSquare) toneSquares[i]).player.type + "\n"
                    + ((ToneSquare) toneSquares[i]).player.semitones + "\n"
                    + ((ToneSquare) toneSquares[i]).player.volume + "\n"
                    + ((ToneSquare) toneSquares[i]).player.parent.toneLetter + "\n";

        }




        // Create file 
        System.out.println("/Storage/" + savename + ".sav");
        File file = new File(path("/Storage/" + savename + ".sav", false));
        if (!file.exists()) {
            try {
                file.createNewFile();

            } catch (Exception e) {//Catch exception if any
                System.out.println("Unable to save sequence " + e.getMessage());
            }
        } else {
            System.out.println("Overrite");


        }

        try {
            FileWriter fstream = new FileWriter(path("/Storage/" + savename + ".sav"));
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(lines);
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.out.println("Unable to save sequence ");
        }

        for (Component a : parent.menu.getComponents()) {
            if ((a.getClass().toString()).indexOf("ButtonSwitcher") > -1) {
                if (((ButtonSwitcher) a).actionCommand.equals("Save Sequence")) {
                    ((ButtonSwitcher) a).offAction();
                    ((ButtonSwitcher) a).toggle = false;
                }
            }
        }
    }

    public void stop() {
        pos = -1;
    }

    private String[] getFileContent(String filename) {
        String lines = "";
        try {
            FileInputStream fstream = new FileInputStream(filename);
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String strLine;
            while ((strLine = br.readLine()) != null) {
                lines += strLine + "\n";
            }
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
        return lines.split("\n");
    }

    public String path(String uri) {
        return this.getClass().getResource(uri).toString().replace("file:", "");
    }

    public String path(String uri, boolean f) {
        //use existing dummy file to return path (if file does not exist)
        return (this.getClass().getResource("/Resources/piano.wav").toString().replace("file:", "")).replace("/Resources/piano.wav", uri);
    }

    @Override
    public boolean isOptimizedDrawingEnabled() {
        return false;
    }
}
