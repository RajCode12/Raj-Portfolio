import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;

public class Main extends Graph{
    public static void main(String[] args) {
        Graph g = new Graph();
        createGraph(g);
        JFrame frame = new JFrame();
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(-10, 0, 1300, 680);
        frame.setTitle("Route Project");
        //Contaier
        Container c = frame.getContentPane();
        c.setBackground(Color.cyan);
        c.setLayout(null);
        frame.setResizable(false);
        //text
        JLabel label1 = new JLabel("<html><u>Your Route Planner</u></html>");
        label1.setBounds(450, 50, 500, 100);
        label1.setFont(new Font("Poppins",Font.BOLD,40));
        c.add(label1);
        Font font = new Font("Poppins", Font.BOLD, 30);
        JLabel label3 = new JLabel("Enter Source");
        label3.setBounds(350, 150, 300, 40);
        label3.setFont(font);
        c.add(label3);
        JLabel label4 = new JLabel("Enter Destination");
        label4.setBounds(750, 150, 300, 40);
        label4.setFont(font);
        c.add(label4);
        //image
        ImageIcon img = new ImageIcon("bank");
        JLabel label2 = new JLabel(img);
        label2.setBounds(100, 50, 100, 100);
        c.add(label2);
        //combo-box
        String s[] = {"Select", "Betiah", "Motihari", "Sitamarhi", "Gopalganj", "Siwan",
                "Patna", "Muzzafarpur", "Darbhanga", "Gaya", "Madhubani", "Chapra",
                "Buxar", "Ara", "Arawal", "Bihar Sharif", "Begusarai", "Saharsa", "Munger",
                "Bhagalpur","Samastipur"
        };
        JComboBox<String> box1 = new JComboBox<>(s);
        box1.setBounds(350, 200, 200, 30);
        box1.setSelectedItem("Source");
        box1.setForeground(Color.BLUE);
        box1.setMaximumRowCount(5);
        c.add(box1);
        JComboBox<String> box2 = new JComboBox<>(s);
        box2.setBounds(750, 200, 200, 30);
        box2.setForeground(Color.BLUE);
        box2.setMaximumRowCount(5);
        c.add(box2);
        //button
        JButton button1 = new JButton("Search");
        button1.setBounds(575, 275, 150, 50);
        button1.setBackground(Color.GREEN);
        button1.setFont(new Font("Arial", Font.PLAIN, 20));
        button1.setCursor(new Cursor(Cursor.HAND_CURSOR));
        c.add(button1);
        //min distance
        Font font2 = new Font("Arial",Font.PLAIN,20);
        JLabel label5 = new JLabel("");
        label5.setBounds(100, 300, 900, 200);
        label5.setFont(font2);
        c.add(label5);
        //min time
        JLabel label6 = new JLabel("");
        label6.setBounds(100, 350, 1000, 200);
        c.add(label6);
        label6.setFont(font2);
        //distance wise shortest path
        JLabel label7 = new JLabel("");
        label7.setBounds(100, 400, 800, 200);
        c.add(label7);
        label7.setFont(font2);
        //Interchanges
        JLabel label8 = new JLabel("");
        label8.setBounds(100, 450, 1200, 200);
        c.add(label8);
        label8.setFont(font2);
        //action-listener
        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item1 = (String) box1.getSelectedItem();
                String item2 = (String) box2.getSelectedItem();
                ArrayList<String> str = g.getInterchanges(g.getMinimumDistance(item1,item2));
                int len = str.size();
                String ans = g.getPath(item1,item2);
                if (!item1.equals("Select") && !item2.equals("Select")) {
                    label5.setText("SHORTEST DISTANCE FROM " + item1 + " TO " + item2 + " : " + g.dijAlgo(item1, item2,false) + "KM\n");
                    label6.setText("SHORTEST TIME FROM (" + item1 + ") TO (" + item2 + ") : " + g.dijAlgo(item1, item2,true) / 60 +
                            " HOURS " + g.dijAlgo(item1, item2,true) % 60 + " MINUTES\n\n");
                    label7.setText("NUMBER OF STATAIONS : " + len/2);
                    label8.setText("SHORTEST PATH : " + ans);
                } else {
                    JOptionPane.showMessageDialog(frame,"Please Select Source and Destination");
                }
            }
        });

        //Menu bar
        JMenuBar menu = new JMenuBar();
        JMenu first = new JMenu("Main");
        JMenuItem i1 = new JMenuItem("New");
        JMenuItem i2 = new JMenuItem("Open");
        JMenuItem i3 = new JMenuItem("Save");
        i1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                box1.setSelectedIndex(0);
                label5.setText("");
                label6.setText("");
                label7.setText("");
                label8.setText("");
            }
        });
        first.add(i1);
        first.add(i2);
        first.add(i3);
        menu.add(first);
        frame.setJMenuBar(menu);
        JMenu second = new JMenu("View");
        JMenuItem i4 = new JMenuItem("Show Stations");
        JMenuItem i5 = new JMenuItem("Show Map");
        i4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame2 = new JFrame();
                frame2.setVisible(true);
                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame2.setBounds(-10, 0, 1300, 680);
                frame2.setTitle("Route Project");
                //Contaier
                Container c2 = frame2.getContentPane();
                c2.setBackground(Color.magenta);
                c2.setLayout(null);
                frame.setResizable(false);
                //text
                JLabel label2 = new JLabel("<html><u>Stations</u></html>");
                label2.setBounds(550, 50, 500, 100);
                label2.setFont(new Font("Poppins",Font.BOLD,40));
                c2.add(label2);
                //exit-button
                JButton button2 = new JButton("Back");
                button2.setFont(new Font("Arial", Font.PLAIN, 20));
                button2.setBounds(25,550,150,50);
                button2.setBackground(Color.white);
                button2.setCursor(new Cursor(Cursor.HAND_CURSOR));
                c2.add(button2);
                button2.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.setVisible(true);
                    }
                });
                //display stations
                String res = g.showStations();
                JTextArea text2 = new JTextArea();
                text2.setText(res);
                text2.setBounds(500, 150, 250, 400);
                text2.setEditable(false);
                Font font2 = new Font("Arial",Font.PLAIN,16);
                text2.setFont(font2);
                c2.add(text2);
            }
        });
        second.add(i4);
        i5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame3 = new JFrame();
                frame3.setVisible(true);
                frame3.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame3.setBounds(-10, 0, 1300, 680);
                frame3.setTitle("Route Project");
                //Contaier
                Container c3 = frame3.getContentPane();
                c3.setBackground(Color.magenta);
                c3.setLayout(null);
                frame3.setResizable(false);
                //text
                JLabel label3 = new JLabel("<html><u>Map</u></html>");
                label3.setBounds(550, 50, 500, 100);
                label3.setFont(new Font("Poppins",Font.BOLD,40));
                c3.add(label3);
                //exit-button
                JButton button3 = new JButton("Back");
                button3.setFont(new Font("Arial", Font.PLAIN, 20));
                button3.setBounds(25,550,150,50);
                button3.setBackground(Color.white);
                button3.setCursor(new Cursor(Cursor.HAND_CURSOR));
                c3.add(button3);
                button3.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        frame.setVisible(true);
                    }
                });
                //display map
                String res = g.display_Map();
                JTextArea text3 = new JTextArea();
                text3.setText(res);
                text3.setBounds(100, 130, 1100, 380);
                text3.setEditable(false);
                Font font3 = new Font("Arial",Font.PLAIN,16);
                text3.setFont(font3);
                c3.add(text3);
                //scroll bar
                JScrollPane scroll = new JScrollPane(text3);
                scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
                scroll.setBounds(100, 150, 1100, 400);
                c3.add(scroll);
            }
        });
        second.add(i5);
        menu.add(second);
        JMenu third = new JMenu("Options");
        JMenuItem i6 = new JMenuItem("Exit");
        i6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        third.add(i6);
        menu.add(third);
    }
}