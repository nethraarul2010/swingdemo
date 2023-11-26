import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * This class opens the main window, that has different elements illustrated in
 * it. It also doubles up as all the listeners for simplicity. Such a design is
 * not recommended in general.
 */

public class SwingFeaturesFrame extends JFrame implements ActionListener, ItemListener, ListSelectionListener {

  private JComboBox<String> combobox = new JComboBox<String>();
  JTextField textField = new JTextField(10);

  private JPanel mainPanel;
  private JScrollPane mainScrollPane;
  private JLabel comboboxDisplay;

  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JTextField numberTextField;
  private JCheckBox splitViewCheckBox;
  private String srcPath;
  private String destPath;
  private int manipulationValue;
  private int compressionValue;
  private int levelAdjustValueB;
  private int levelAdjustValueM;
  private int levelAdjustValueW;

  private JTextField valueTextField;

  public SwingFeaturesFrame() {
    super();
    setTitle("Swing features");
    setSize(400, 400);


    mainPanel = new JPanel();
    //for elements to be arranged vertically within this panel
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));
    //scroll bars around this main panel
    mainScrollPane = new JScrollPane(mainPanel);
    add(mainScrollPane);

    //show an image with a scrollbar
    JPanel imagePanel = new JPanel();
    //a border around the panel with a caption
    imagePanel.setBorder(BorderFactory.createTitledBorder("Showing an image"));
    imagePanel.setLayout(new GridLayout(1, 0, 10, 10));
    //imagePanel.setMaximumSize(null);
    mainPanel.add(imagePanel);

    //Pass the buffered image to the constructor and add the link of buffered image here
    String[] images = {"Jellyfish.jpg", "test-color-hist.jpg"};
    JLabel[] imageLabel = new JLabel[images.length];
    JScrollPane[] imageScrollPane = new JScrollPane[images.length];

    for (int i = 0; i < imageLabel.length; i++) {
      imageLabel[i] = new JLabel();
      imageScrollPane[i] = new JScrollPane(imageLabel[i]);

      ImageIcon icon = new ImageIcon(images[i]);
      int width = icon.getIconWidth();
      int height = icon.getIconHeight();

      if (width == 256 && height == 256) {
        // Set a fixed size for the 256x256 image
        imageScrollPane[i].setPreferredSize(new Dimension(256, 256));
      } else {
        // Set a preferred size for other images
        imageScrollPane[i].setPreferredSize(new Dimension(500, 600));
      }

      imageLabel[i].setIcon(icon);
      imagePanel.add(imageScrollPane[i]);
    }


// Check box for split
    JPanel splitViewPanel = new JPanel();
    splitViewPanel.setLayout(new BoxLayout(splitViewPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(splitViewPanel);
    numberTextField = new JTextField(10);
    numberTextField.setVisible(false);
    numberTextField.addActionListener(this);
    splitViewPanel.add(numberTextField);

    splitViewCheckBox = new JCheckBox("Split view");
    splitViewCheckBox.setActionCommand("Split view");
    splitViewCheckBox.setSelected(false);
    splitViewCheckBox.addItemListener(this);
    splitViewPanel.add(splitViewCheckBox);

    // Explicitly set the preferred and maximum sizes for better layout control
    numberTextField.setPreferredSize(new Dimension(300, 25)); // Adjust the size as needed
    numberTextField.setMaximumSize(new Dimension(100, 25)); // Adjust the size as needed

    splitViewCheckBox.setPreferredSize(new Dimension(100, 25)); // Adjust the size as needed
    splitViewCheckBox.setMaximumSize(new Dimension(100, 25)); // Adjust the size as needed


    //combo boxes
    JPanel comboboxPanel = new JPanel();
 //   comboboxPanel.setBorder(BorderFactory.createTitledBorder("Combo boxes"));
    comboboxPanel.setLayout(new BoxLayout(comboboxPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(comboboxPanel);

    comboboxDisplay = new JLabel("Image manipulation operations");
    comboboxPanel.add(comboboxDisplay);
    String[] options = {"Red-component", "Green-Component", "Blue-Component", "Blur","Sharpen",
        "Sepia","Grey-scale","Color-correct","Compress","Levels-adjust","Brighten","Darken"};

    combobox.setPreferredSize(new Dimension(80, 25));
    //the event listener when an option is selected
    combobox.setActionCommand("Size options");
    combobox.addActionListener(this);
    for (int i = 0; i < options.length; i++) {
      combobox.addItem(options[i]);
    }

    comboboxPanel.add(combobox);

    // Create the text field

    textField.setVisible(false); // Initially hidden

    // Add components to the main panel
    mainPanel.add(textField);



    //dialog boxes
    JPanel dialogBoxesPanel = new JPanel();
    dialogBoxesPanel.setBorder(BorderFactory.createTitledBorder("Loading and saving of file"));
    dialogBoxesPanel.setLayout(new BoxLayout(dialogBoxesPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(dialogBoxesPanel);

    //file open
    JPanel fileopenPanel = new JPanel();
    fileopenPanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(fileopenPanel);
    JButton fileOpenButton = new JButton("Open a file");
    fileOpenButton.setActionCommand("Open file");
    fileOpenButton.addActionListener(this);
    fileopenPanel.add(fileOpenButton);
    fileOpenDisplay = new JLabel("File path will appear here");
    fileopenPanel.add(fileOpenDisplay);

    //file save
    JPanel filesavePanel = new JPanel();
    filesavePanel.setLayout(new FlowLayout());
    dialogBoxesPanel.add(filesavePanel);
    JButton fileSaveButton = new JButton("Save a file");
    fileSaveButton.setActionCommand("Save file");
    fileSaveButton.addActionListener(this);
    filesavePanel.add(fileSaveButton);
    fileSaveDisplay = new JLabel("File path will appear here");
    filesavePanel.add(fileSaveDisplay);



  }
  // New method to handle the "Size options" action
  private void handleSizeOptionAction() {
    String selectedOption = (String) combobox.getSelectedItem();

    // Show/hide text field based on the selected option
    if (selectedOption.equals("Brighten") || selectedOption.equals("Darken")
        || selectedOption.equals("Compress")) {
      String input = JOptionPane.showInputDialog(null,
          "Enter a value:");
      valueTextField.setVisible(true);
      valueTextField.setText(input);
      manipulationValue = Integer.parseInt(input);
      System.out.print(manipulationValue);
    }
   else if(selectedOption.equals("Levels-adjust")) {
      levelAdjustValueB = Integer.parseInt(JOptionPane.showInputDialog(null,
          "Enter a b value:"));
      levelAdjustValueM = Integer.parseInt(JOptionPane.showInputDialog(null,
          "Enter a m value:"));
      levelAdjustValueW = Integer.parseInt(JOptionPane.showInputDialog(null,
          "Enter a w value:"));
    }
    else {
      numberTextField.setVisible(false);
      numberTextField.setText(""); // Clear the text field when unchecked
    }
  }
  // Modify handleSplitViewAction to accept the checkbox state as a parameter
  private void handleSplitViewAction(boolean currentState) {
    System.out.println("Came inside split view");
    boolean previousState = !currentState;

    if (currentState) {
      // Display an input dialog to get the Split View number
      String input = JOptionPane.showInputDialog(null,
          "Enter Split View number:");

      // Check if the user clicked "Cancel" or entered an empty value
      if (input == null || input.trim().isEmpty()) {
        splitViewCheckBox.setSelected(previousState); // Restore the checkbox state
        numberTextField.setVisible(false);
      } else {
        // User entered a valid number, show the text field
        numberTextField.setVisible(true);
        numberTextField.setText(input);
        compressionValue = Integer.parseInt(input);
        System.out.println(compressionValue);
      }
    } else {
      numberTextField.setVisible(false);
      numberTextField.setText(""); // Clear the text field when unchecked
    }
  }
  @Override
  public void actionPerformed(ActionEvent arg0) {
      switch (arg0.getActionCommand()) {
        case "Size options":
          handleSizeOptionAction();
          break;
        case "Open file": {
          final JFileChooser fchooser = new JFileChooser(".");
          FileNameExtensionFilter filter = new FileNameExtensionFilter(
              "JPG & GIF Images", "jpg", "gif");
          fchooser.setFileFilter(filter);
          int retvalue = fchooser.showOpenDialog(SwingFeaturesFrame.this);
          if (retvalue == JFileChooser.APPROVE_OPTION) {
            File f = fchooser.getSelectedFile();
            fileOpenDisplay.setText(f.getAbsolutePath());
            srcPath = f.getAbsolutePath().toString();
          }
          System.out.println("src"+srcPath);
        }
        break;
        case "Save file": {
          final JFileChooser fchooser = new JFileChooser(".");
          int retvalue = fchooser.showSaveDialog(SwingFeaturesFrame.this);
          if (retvalue == JFileChooser.APPROVE_OPTION) {
            File f = fchooser.getSelectedFile();
            fileSaveDisplay.setText(f.getAbsolutePath());
            destPath = f.getAbsolutePath().toString();
          }
        }
        break;
      }
    }


/*
  @Override
  public void actionPerformed(ActionEvent arg0) {
    System.out.println("Action Performed: " + arg0.getActionCommand());  // Add this line
    // TODO Auto-generated method stub
    switch (arg0.getActionCommand()) {
      case "Size options":
        handleSizeOptionAction();
        break;
      case "Open file": {
        final JFileChooser fchooser = new JFileChooser(".");
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JPG & GIF Images", "jpg", "gif");
        fchooser.setFileFilter(filter);
        int retvalue = fchooser.showOpenDialog(SwingFeaturesFrame.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          fileOpenDisplay.setText(f.getAbsolutePath());
        }
      }
      break;
      case "Split view":
        System.out.println("Inside case block");
        handleSplitViewAction();
        break;
      case "Save file": {
        final JFileChooser fchooser = new JFileChooser(".");
        int retvalue = fchooser.showSaveDialog(SwingFeaturesFrame.this);
        if (retvalue == JFileChooser.APPROVE_OPTION) {
          File f = fchooser.getSelectedFile();
          fileSaveDisplay.setText(f.getAbsolutePath());
        }
      }
      break;
      case "Message":
        JOptionPane.showMessageDialog(SwingFeaturesFrame.this, "This is a demo message", "Message",
            JOptionPane.PLAIN_MESSAGE);
        JOptionPane.showMessageDialog(SwingFeaturesFrame.this, "You are about to be deleted.",
            "Last Chance", JOptionPane.WARNING_MESSAGE);
        JOptionPane.showMessageDialog(SwingFeaturesFrame.this, "You have been deleted.", "Too late",
            JOptionPane.ERROR_MESSAGE);
        JOptionPane.showMessageDialog(SwingFeaturesFrame.this, "Please start again.",
            "What to do next", JOptionPane.INFORMATION_MESSAGE);
        break;
    }
  }*/

  @Override
  public void valueChanged(ListSelectionEvent e) {
    // We don't know which list called this callback, because we're using it
    // for two lists.  In practice, you should use separate listeners
    JOptionPane.showMessageDialog(SwingFeaturesFrame.this,
            "The source object is " + e.getSource(), "Source", JOptionPane.PLAIN_MESSAGE);
    // Regardless, the event information tells us which index was selected
    JOptionPane.showMessageDialog(SwingFeaturesFrame.this,
            "The changing index is " + e.getFirstIndex(), "Index", JOptionPane.PLAIN_MESSAGE);
  }

  @Override
  public void itemStateChanged(ItemEvent e) {
    if (e.getSource() == splitViewCheckBox) {
      boolean currentState = splitViewCheckBox.isSelected();
      handleSplitViewAction(currentState);
    }

  }
}
