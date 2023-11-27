import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Test extends JFrame {
  private JLabel mainImageLabel;
  private JLabel secondImageLabel;
  private JButton button1;
  private JButton button2;

  public Test() {
    setTitle("Image App");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    mainImageLabel = new JLabel(new ImageIcon("Penguins.jpg"));
    mainImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    add(mainImageLabel, BorderLayout.CENTER);

    JPanel secondColumnPanel = new JPanel();
    secondColumnPanel.setLayout(new BorderLayout());

    secondImageLabel = new JLabel(new ImageIcon("test-color-hist.jpg"));
    secondImageLabel.setHorizontalAlignment(SwingConstants.CENTER);
    secondColumnPanel.add(secondImageLabel, BorderLayout.NORTH);

    button1 = new JButton("Button 1");
    button2 = new JButton("Button 2");

    button1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Handle button1 click
      }
    });

    button2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        // Handle button2 click
      }
    });

    secondColumnPanel.add(button1, BorderLayout.CENTER);
    secondColumnPanel.add(button2, BorderLayout.SOUTH);

    add(secondColumnPanel, BorderLayout.EAST);

    pack();
    setLocationRelativeTo(null);
    setVisible(true);
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(() -> new Test());
  }
}
