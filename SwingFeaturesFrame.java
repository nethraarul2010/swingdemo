import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;

import java.util.Hashtable;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextField;
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
  private JLabel fileOpenDisplay;
  private JLabel fileSaveDisplay;
  private JTextField numberTextField;
  private JCheckBox splitViewCheckBox;
  private String srcPath;
  private String destPath;
  private int manipulationValue;
  private int splitValue;

  private JButton okButton;
  private JButton cancelButton;
  private int levelAdjustValueB;
  private int levelAdjustValueM;
  private int levelAdjustValueW;

  private boolean saveClicked = false;

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
    imagePanel.setLayout(new FlowLayout());


    //Pass the buffered image to the constructor and add the link of buffered image here
// Regular Image with ScrollPane
    String srcImg = "Jellyfish.jpg";
    JLabel srcImageLabel = new JLabel();
    JScrollPane srcImgScroll = new JScrollPane(srcImageLabel);
    ImageIcon srcImgIcon = new ImageIcon(srcImg);
    srcImageLabel.setIcon(srcImgIcon);
    srcImgScroll.setPreferredSize(new Dimension(1000, 600));
    imagePanel.add(srcImgScroll);

// Histogram Image
    String histImg = "test-color-hist.jpg";
    JLabel histImageLabel = new JLabel();
    ImageIcon histImgIcon = new ImageIcon(histImg);
    histImageLabel.setIcon(histImgIcon);
    imagePanel.add(histImageLabel);

    mainPanel.add(imagePanel);

// Check box for split
    JPanel splitViewPanel = new JPanel();
    splitViewPanel.setLayout(new BoxLayout(splitViewPanel, BoxLayout.PAGE_AXIS));
    mainPanel.add(splitViewPanel);
    numberTextField = new JTextField(10);
    numberTextField.setVisible(false);
    numberTextField.addActionListener(this);
    splitViewPanel.add(numberTextField);

    splitViewCheckBox = new JCheckBox("Preview");
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

   // comboboxDisplay = new JLabel("Image manipulation operations");
    //comboboxPanel.add(comboboxDisplay);
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
  private void handleSizeOptionAction() {
    String selectedOption = (String) combobox.getSelectedItem();
    splitViewCheckBox.setEnabled(false);
    if (!saveClicked) {
      JOptionPane.showMessageDialog(this, "Please click 'Save' before selecting another manipulation operation.", "Error", JOptionPane.ERROR_MESSAGE);
      return;
    }
    // Show/hide text field based on the selected option
    if (selectedOption.equals("Color-correct") || selectedOption.equals("Sepia")
        || selectedOption.equals("Levels-adjust")
        || selectedOption.equals("Luma")|| selectedOption.equals("Sharpen")
        || selectedOption.equals("Brighten")|| selectedOption.equals("Blur")) {
      // Enable splitViewCheckBox for specific operations
      splitViewCheckBox.setEnabled(true);
    }
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
    saveClicked=false;
  }

  private void handleSplitViewAction(boolean currentState) {
    System.out.println("Came inside split view");
    boolean previousState = !currentState;

    if (currentState) {

      JPanel panel = new JPanel(new GridLayout(3,1)); // 2 rows, 1 column
      panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
      JScrollPane ScrollPane = new JScrollPane(mainPanel);
      add(ScrollPane);
      JLabel lbl = new JLabel(new ImageIcon("Penguins.jpg"));
      JScrollPane lblScrollPane = new JScrollPane(lbl);
      lblScrollPane.setPreferredSize(new Dimension(1000,600));
      panel.add(lblScrollPane);

      JLabel percentLabel = new JLabel("Split Percentage:");
      panel.add(percentLabel);
      JSlider splitPercent = new JSlider(0,100,50);
      splitPercent.setMajorTickSpacing(10);
      splitPercent.setMinorTickSpacing(1);
      splitPercent.setPaintLabels(true);
      Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
      labelTable.put(0, new JLabel("0"));
      labelTable.put(50, new JLabel("50"));
      labelTable.put(100, new JLabel("100"));
      splitPercent.setLabelTable(labelTable);
      panel.add(splitPercent);

      JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
      // Create buttons
      JButton okButton = new JButton("OK");
      okButton.setActionCommand("splitOk");
      JButton cancelButton = new JButton("Cancel");
      cancelButton.setActionCommand("splitCancel");


      okButton.addActionListener(e -> {
        JOptionPane.getRootFrame().dispose();
         splitValue = splitPercent.getValue();
          System.out.println(splitValue);
      });

      cancelButton.addActionListener(e -> {
        JOptionPane.getRootFrame().dispose();
      });

      buttonPanel.add(okButton);
      buttonPanel.add(cancelButton);

      // Add the button panel to the main panel
      panel.add(buttonPanel);

      int result = JOptionPane.showOptionDialog(
          null,
          panel,
          "ImageDialog with Buttons",
          JOptionPane.OK_CANCEL_OPTION,
          JOptionPane.PLAIN_MESSAGE,
          null,
          new Object[]{},
          null);

/*
      if (result == JOptionPane.OK_OPTION) {
        System.out.println("OK Button Clicked");
      } else {
        System.out.println("Cancel Button Clicked or Dialog Closed");
      }*/
    }

  }

  @Override
  public void actionPerformed(ActionEvent arg0) {
    if (arg0.getActionCommand().equals("Size options")) {
      // Skip the initial call when the program starts
      if (!combobox.getSelectedItem().equals("Red-component")) {
        handleSizeOptionAction();
      }
    } else if (arg0.getActionCommand().equals("Open file")) {
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
      System.out.println("src" + srcPath);
    } else if (arg0.getActionCommand().equals("Save file")) {
      final JFileChooser fchooser = new JFileChooser(".");
      int retvalue = fchooser.showSaveDialog(SwingFeaturesFrame.this);
      if (retvalue == JFileChooser.APPROVE_OPTION) {
        File f = fchooser.getSelectedFile();
        fileSaveDisplay.setText(f.getAbsolutePath());
        destPath = f.getAbsolutePath().toString();
        saveClicked = true;
      }
    }
    // ... (rest of the actionPerformed method)
  }

/*  @Override
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
          saveClicked = true;
        }
      }
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