/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;

import ControllerPackage.FrameController;
import ModelPackage.ManualAction;
import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FilenameFilter;
import java.io.InputStreamReader;
import javax.swing.*;

/**
 *
 * @author gillm
 */
public class ViewSaved extends RoundedPanel {

    private boolean large;
    public JPanel heading = new JPanel();
    public JPanel[] rowContent;
    public JPanel[] deleteButtons;
    public JPanel tableBody;
    public SequencerFrame parent;
    public boolean showDelete = false;
    public int step = 0;
    final int max = 9;


    
    public ViewSaved(String size, SequencerFrame parent, boolean showDelete) {
        this.large = (size.equals("large")) ? true : false;
        this.parent = parent;
        this.showDelete = true;
        this.setLayout(null);
        if (large) {
            this.setBounds(5, 50, 550, 397);
        } else {
            this.setBounds(5, 50, 275, 169);
        }
        this.setBackground(new Color(240, 240, 240));


     
        tableBody = new RoundedPanel();
        tableBody.setBackground(Color.white);
        tableBody.setBounds(6, 6, this.getWidth() - 12, 385);
        heading.setBackground(new Color(255, 165, 100));
        heading.setLayout(null);
        heading.setBounds(7, 7, this.getWidth() - 12, 30);
        heading.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));


        File SaveDir = new File(path("/Storage/"));

        String[] children = SaveDir.list(new FilenameFilter() {

            public boolean accept(File dir, String name) {
                return name.endsWith(".sav");
            }
        });


        int rows = children.length;

        SaveFile[] files = new SaveFile[rows];

        
        if (children.length == 0) {
            TableRow blank = new TableRow(true, size, this);
            blank.setBounds(7, 38, this.getWidth() - 49, 35);
            this.add(blank);
        } else {
            for (int i = 0; i < children.length; i++) {
                files[i] = SaveFileFactory(path("/Storage/") + children[i]);
            }
        }


        
        
        
         
         
        rowContent = new TableRow[rows];
        
        

        for (int i = 0; i < rows; i++) {
            rowContent[i] = new TableRow(!((i % 2) == 0), (this.getWidth() - 12), files[i].nickName, files[i].fullName, files[i].date, size, files[i].fileName, this, showDelete);
            rowContent[i].setBounds(7, 38 + (i * 36), this.getWidth() - 49, 35);
            this.add(rowContent[i]);
        }

        JPanel upArrow = new ButtonSwitcher(" ", new Color(255, 165, 100), new Color(243, 125, 122), new ManualAction() {

            @Override
            public void click() {
                ViewSaved t = ViewSaved.this;
                t.viewScroll("up");
            }
        });
        upArrow.setBounds(this.getWidth() - 41, 38, 36, 35);
        upArrow.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        JPanel downArrow = new ButtonSwitcher(" ", new Color(255, 165, 100), new Color(243, 125, 122), new ManualAction() {

            @Override
            public void click() {
                ViewSaved t = ViewSaved.this;
                t.viewScroll("down");
            }
        });
        downArrow.setBounds(this.getWidth() - 41, 326, 36, 35);
        downArrow.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        this.add(upArrow);
        this.add(downArrow);

        
 
                
        
        JLabel nickname = new JLabel("<html>Nickname</html>");
        JLabel fullname = new JLabel("<html>Full Name</html>");
        JLabel date = new JLabel("<html>Date Created</html>");
        
        nickname.setBounds(5, -4, 100, 40);
        fullname.setBounds(120, -4, 100, 40);
        date.setBounds(((showDelete) ? 368: 400), -4, 100, 40);

        
        /*
         * 
         */

   
        heading.add(nickname);
        heading.add(fullname);
        heading.add(date);
        
        this.add(heading);
        this.add(tableBody);        

    }

    public void viewScroll(String direction) {
        
        

        
        int current = rowContent.length;
        if (current >= max) {
            this.remove(tableBody);
            if (direction.equals("up")) {
                
                if(step == 0)
                        return;
                
                step --;
                
                JPanel temp = rowContent[0];
                for (int i = 0; i < (current - 1); i++) {
                    this.remove(rowContent[i]);
                    
                    rowContent[i] = rowContent[i + 1];
                    rowContent[i].setBounds(7, 38 + (i * 31), this.getWidth() - 50, 35);
                }
                this.remove(rowContent[current - 1]);
                rowContent[current - 1] = temp;
                rowContent[current - 1].setBounds(7, 38 + ((current - 1) * 31), this.getWidth() - 50, 35);
                this.repaint();
            } else {
                
                if(step == (current - max))
                    return;
                
                step ++;
                
                JPanel temp = rowContent[rowContent.length - 1];
                for (int i = rowContent.length; i > 1; i--) {

                    this.remove(rowContent[i - 1]);
                    rowContent[i - 1] = rowContent[i - 2];
                    rowContent[i - 1].setBounds(7, 38 + ((i - 1) * 31), this.getWidth() - 56, 35);
                }
                this.remove(rowContent[0]);
                rowContent[0] = temp;
                rowContent[0].setBounds(7, 38, this.getWidth() - 50, 30);



            }

            for (int i = 0; i < rowContent.length; i++) {
                rowContent[i].setBounds(7, 38 + (i * 36), this.getWidth() - 50, 35);
                ((TableRow) rowContent[i]).mouseExited(null);
            }
            for (int i = 0; i < rowContent.length; i++) {
                this.add(rowContent[i]);
            }
            this.add(tableBody);
            this.repaint();
        }

    }

    public String path(String uri) {
        return this.getClass().getResource(uri).toString().replace("file:", "");
    }

    private SaveFile SaveFileFactory(String path) {
        String[] lines = getFileContent(path);
        return new SaveFile(getLineFrom(lines, "[nickname]"), getLineFrom(lines, "[fullname]"), getLineFrom(lines, "[date]"), path);
    }

    private String getLineFrom(String[] lines, String id) {
        int loadPoint;
        for (loadPoint = 0; loadPoint < lines.length; loadPoint++) {
            if (lines[loadPoint].equalsIgnoreCase(id)) {
                break;
            }
        }

        return lines[loadPoint + 1];
    }

    private class SaveFile {

        public String nickName;
        public String fullName;
        public String date;
        public String fileName;

        public SaveFile(String n, String f, String d, String fn) {
            this.nickName = n;
            this.fullName = f;
            this.date = d;
            this.fileName = fn;
        }
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
}
