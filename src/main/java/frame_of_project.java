import java.awt.*;
import javax.swing.*;


public class frame_of_project extends JFrame {
    public CollectSinkPanel p1;

    public frame_of_project() {
        setSize(Screen.width, Screen.heigth);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());


        new FlinkStream();
        p1 = new CollectSinkPanel();
        p1.setLayout(null);
        p1.setBackground(Color.BLACK);
        add(p1);


        this.setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        getContentPane().setPreferredSize(new Dimension(Screen.width, Screen.heigth));
        pack();


        while (true) {
            p1.repaint();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
