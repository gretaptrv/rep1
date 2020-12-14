package textProcessingApp;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class attempt {

    public static class FileToTextArea extends JFrame {
        private JTextArea txtAr;
        //private JLabel labelF;

        public FileToTextArea(String linesFromFile) {
            super("Updated text file contents");

            Container cont = getContentPane();
            cont.setLayout(new FlowLayout());

            txtAr = new JTextArea(linesFromFile);
            txtAr.setLineWrap(true);
            txtAr.setWrapStyleWord(true);
            JScrollPane scroller = new JScrollPane(txtAr,
                    JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                    JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            //cont.add(txtAr);
            cont.add(scroller);
            setSize(300, 300);
            setVisible(true);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        }
    }

    public static class GetPathToFile extends JFrame {
        private JTextField txtF;
        private JLabel labelF;
        private JButton butt;

        public GetPathToFile(List<String> pathFileList) {
            super("Find path");

            Container cont = getContentPane();
            cont.setLayout(new FlowLayout());

            labelF = new JLabel("Enter path to file");
            cont.add(labelF);
            txtF = new JTextField(20);
            cont.add(txtF);
            butt = new JButton("Done");
            butt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    pathFileList.add(txtF.getText());
                    System.out.println(pathFileList.get(0));
                    setVisible(false);
                }
            });
            cont.add(butt);
            setSize(300, 100);
            setVisible(true);
        }
    }

    public static class lineIndexer extends JFrame {
        private JTextField txtF;
        private JTextField txtFF;
        private JButton butt;
        private JLabel labelFF;
        private JLabel labelF;

        public lineIndexer(int[] indexes) {
            super("Line choosing");

            Container cont = getContentPane();
            cont.setLayout(new FlowLayout());

            labelF = new JLabel("Enter line index");
            txtF = new JTextField(3);
            cont.add(labelF);
            cont.add(txtF);

            labelFF = new JLabel("Enter line index");
            txtFF = new JTextField(3);
            cont.add(labelFF);
            cont.add(txtFF);

            butt = new JButton("Switch lines");
            butt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                        String[] first = txtF.getText().split(",");
                        indexes[0] = Integer.parseInt(first[0]);
                        indexes[1] = Integer.parseInt(first[1]);
                        String[] second = txtFF.getText().split(",");
                        indexes[2] = Integer.parseInt(second[0]);
                        indexes[3] = Integer.parseInt(second[1]);
                        setVisible(false);
                }
            });

            cont.add(butt);
            setSize(300, 300);
            setVisible(true);
        }
    }

    public static class wordIndexer extends JFrame{
        private JTextField txtF;
        private JTextField txtFF;
        private JLabel labelF;
        private JButton butt;

        public wordIndexer(int[] indexes) {
            super("Word choosing");

            Container cont = getContentPane();
            cont.setLayout(new FlowLayout());

            labelF = new JLabel("Enter line and word indexes");
            cont.add(labelF);

            txtF = new JTextField(5);
            txtF.setToolTipText("first line then word index\nseparate only with a comma");
            txtFF = new JTextField(5);
            txtFF.setToolTipText("first line then word index\nseparate only with a comma");
            cont.add(txtF);
            cont.add(txtFF);

            butt = new JButton("Switch words");
            butt.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String[] first = txtF.getText().split(",");
                    indexes[0] = Integer.parseInt(first[0]);
                    indexes[1] = Integer.parseInt(first[1]);
                    String[] second = txtFF.getText().split(",");
                    indexes[2] = Integer.parseInt(second[0]);
                    indexes[3] = Integer.parseInt(second[1]);
                    setVisible(false);
                    //                    JOptionPane.showMessageDialog(null, "Let's hope they switched");
                }
            });
            cont.add(butt);
            setSize(300, 300);
            setVisible(true);

        }
    }

    public static class OpChooser extends JFrame {
        private JButton button1;
        private JButton button2;
        private JButton button3;
        private JLabel labelF;
        private JLabel labelFF;

        public OpChooser(List<Integer> answer) {
            super("Set Options");

            Container cont = getContentPane();
            cont.setLayout(new FlowLayout());

            labelF = new JLabel("Choose next");
            cont.add(labelF);
            labelFF = new JLabel();
            cont.add(labelFF);
            button1 = new JButton("Switch two lines");
            //button1.setBounds(0, 200, 100, 30);
            button1.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    answer.add(1);
                    //first option, switch lines
                }
            });
            cont.add(button1);

            button2 = new JButton("Switch two words");
            //button2.setBounds(150, 80, 100, 30);
            button2.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    answer.add(2);
                    //second option, switch words
                }
            });
            cont.add(button2);

            button3 = new JButton("Exit the program");
            //button3.setBounds(150, 80, 100, 30);
            button3.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    setVisible(false);
                    answer.add(3);
                }
            });
            cont.add(button3);

            setSize(300, 300);
            setVisible(true);

        }
    }

    public static void main(String[] args) {
        System.out.println();
        List<String> list = new ArrayList<>();
        GetPathToFile ff = new GetPathToFile(list);
        System.out.println("emo");

    }
}
