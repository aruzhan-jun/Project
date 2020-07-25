package com.company;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ListStudents extends Container {
    public JTextArea textArea;
    public ListStudents(){
        setLayout(null);
        setSize(500, 500);

        textArea = new JTextArea();
        textArea.setLocation(100, 100);
        textArea.setSize(300, 200);
        textArea.setEditable(false);
        add(textArea);

        JButton button = new JButton("BACK");
        button.setLocation(100, 400);
        button.setSize(300, 30);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainFrame.listStudents.setVisible(false);
                MainFrame.mainMenu.setVisible(true);
            }
        });
        add(button);
    }

}
