import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class pathAcceptor extends JFrame {
    private JTextField txtF;
    private String result;

    public pathAcceptor() {
        super("Receive file path");

        Container cont = getContentPane();
        cont.setLayout(new FlowLayout());

        txtF = new JTextField(20);
        cont.add(txtF);
        txtF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String em = txtF.getText();
                    System.out.println(em);
                }
            }
        });

        setSize(300, 100);
        setVisible(true);
    }

    public String getPath() {
        return "mainata wi";
    }

    public static void main(String[] args) {
        pathAcceptor app = new pathAcceptor();
        String path = app.getPath();
        System.out.println(path);
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
