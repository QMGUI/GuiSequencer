/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewPackage;

import ModelPackage.ManualAction;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import javax.swing.JOptionPane;

/**
 *
 * @author gillm
 */
public class TableRow extends JPanel implements MouseListener {

    public boolean odd;
    public String nickname;
    public String fullName;
    public String created;
    public boolean large;
    public String fileName;
    public ViewSaved parent;
    public boolean showDelete = false;

    public TableRow(boolean blank, String size, ViewSaved parent) {
        large = (size.equals("large")) ? true : false;
        this.setLayout(null);
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));
        this.parent = parent;
        JLabel label = new JLabel("<html>You currently have no saved sequences.</html>");
        label.setBounds(13, 6, 250, 20);
        label.setFont(new Font("Segoe UI", Font.BOLD, large ? 13 : 10));
        this.add(label);
    }

    public TableRow(boolean odd, int width, String nickname, String fullName, String created, String size, String fileName, ViewSaved parent, boolean showDelete) {
        large = (size.equals("large")) ? true : false;
        //this.parent = parent;
        this.setLayout(null);
        this.showDelete = showDelete;
        addMouseListener(this);
        this.fileName = fileName;
        this.parent = parent;
        this.odd = odd;
        this.nickname = nickname;
        this.fullName = fullName;
        this.created = created;
        this.setBackground((odd) ? new Color(240, 240, 240) : Color.white);
        this.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0)));

        JLabel nicknameLabel = new JLabel("<html>" + nickname + "</html>");
        nicknameLabel.setBounds(30, 6, 80, 20);
        nicknameLabel.setFont(new Font("Segoe UI", Font.BOLD, large ? 13 : 10));

        JLabel fullNameLabel = new JLabel("<html>" + fullName + "</html>");
        fullNameLabel.setBounds(90, 6, 200, 20);
        fullNameLabel.setFont(new Font("Segoe UI", Font.BOLD, large ? 13 : 10));

        JLabel createdLabel = new JLabel("<html>" + created + "</html>");
        createdLabel.setBounds(((showDelete) ? 368 : 400), 6, 200, 20);
        createdLabel.setFont(new Font("Segoe UI", Font.BOLD, large ? 13 : 10));
        this.add(createdLabel);
        this.add(nicknameLabel);
        this.add(fullNameLabel);

        if (showDelete) {
            JPanel deleteButton = new PanelButton(new ManualAction("a") {

                public void click() {
                    TableRow t = TableRow.this;
                    int deleteResponse = JOptionPane.showConfirmDialog(null, "Are you sure you want to delete "+t.fullName+"?", "Input", JOptionPane.YES_NO_OPTION);
                    if (deleteResponse == 0) {
                        File f = new File(t.fileName);
                        f.delete();
                        ((ButtonSwitcher)((MenuPanel)t.parent.parent.parent.menu).manageSequencesButton).action.click();
                        ((ButtonSwitcher)((MenuPanel)t.parent.parent.parent.menu).manageSequencesButton).action.click();
                        
                    }
                }
            });
            deleteButton.setBounds(468, 2, 31, 31);
            deleteButton.setBorder(BorderFactory.createLineBorder(Color.black));
            deleteButton.setFont(new Font("Segoe UI", Font.BOLD, large ? 13 : 10));
            this.add(deleteButton);
        }

        /*
         * "Delete",  (new ModelPackage.ManualAction(){
        public void click(){
        System.out.println("a");
        }
        })
         */


    }

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setBackground((odd) ? new Color(220, 220, 220) : new Color(245, 245, 245));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setBackground((odd) ? new Color(240, 240, 240) : Color.white);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        ((SequencerFrame) parent.parent).load(fileName);



        for (Component a : parent.parent.parent.menu.getComponents()) {
            if ((a.getClass().toString()).indexOf("ButtonSwitcher") > -1) {
                if (((ButtonSwitcher) a).actionCommand.equals("Open Existing")) {
                    ((ButtonSwitcher) a).offAction();
                    ((ButtonSwitcher) a).mouseClicked(null);

                    //((ButtonSwitcher)a).toggle = false;
                    //((ButtonSwitcher)a). = false;

                }
            }
        }
        parent.parent.parent.menu.repaint();
        parent.parent.parent.sequencerFrame.remove(this.parent);
        parent.parent.parent.sequencerFrame.repaint();
    }
}
