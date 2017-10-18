import java.awt.*;          // access to Container
import java.awt.event.*;    // access to WindowAdapter, WindowEvent
import javax.swing.*;       // access to JFrame and JComponents

public class AppletDemo extends JApplet {
	
	    // component(s) in window - these are just some samples
    private JLabel label1;
    private JTextField textField1;
    private JButton button1;
    private JCheckBox chkBox1;
    private JComboBox comboBox1;
    private String[] names = {"item1", "item2", "item3", "item4"};
                // list for comboBox1
    private JMenuItem startItem, exitItem;
    
    /** Creates a new instance of gui_test - sets up GUI */
    public void init() {
        // STEP 1: must call super() first
 //       super("Comment in Window Title Bar");
        
        // STEP 2: get content pane and set its layout
        Container container = getContentPane();
        container.setLayout( new FlowLayout() );
                    // other possible layouts: BorderLayout, GridLayout,
                    // BoxLayout, CardLayout, GridBagLayout
        
        // STEP 3: construct component(s), such as:
        //      JLabel - displays text or icons
        //      JTextField - accepts text input by user; event triggered
        //              when user hits 'Enter'
        //      JButton - triggers an event when user clicks on it
        //      JCheckBox - checked or unchecked by user
        //      JComboBox, JList - drop-down list of items, where user selects one
        //      JMenuBar, JMenuItem - used to manage menus located at the
        //                  top of the window
        label1 = new JLabel("This is a label");
        textField1 = new JTextField(20);    // width = 20
        button1 = new JButton("Text in button");
        chkBox1 = new JCheckBox("Text next to checkbox");
        comboBox1 = new JComboBox(names);   // names = Strings in list
        comboBox1.setMaximumRowCount(3);    
                    // optional; provides scrollbar if list is greater than 3
        // menu items are handled a little differently...here is a sample
        // menu with 2 items, "Start" and "Exit"
        JMenu fileMenu = new JMenu("File");
        startItem = new JMenuItem("Start");
        exitItem = new JMenuItem("Exit");
        JMenuBar bar = new JMenuBar();

        
        // STEP 4: add all components (except menu items) to the Container;
        //         add all JMenuItems to appropriate JMenu, setJMenuBar and
        //          add all JMenus to it
        container.add(label1);
        container.add(textField1);
        container.add(button1);
        container.add(chkBox1);
        container.add(comboBox1);
        fileMenu.add(startItem);
        fileMenu.add(exitItem); 
        setJMenuBar (bar);
        bar.add (fileMenu);
        
        // STEP 5: register any needed event handlers 
        //      - each sample component above (except the JLabel) needs 
        //      an event handler
        //      - for each *type* of component used, an event handler
        //      associated with that type should be instantiated; that
        //      handler can handle one or more components of that type;
        //      - each event handler is defined below as a private inner class -
        //      see section after main()
        //      - method addActionListener must be called for every 
        //      JTextField and JButton component; method addItemListener
        //      for every JCheckBox and JComboBox component; 
        //      where the argument is the appropriate type of event handler
        textField1.addActionListener(new TextFieldHandler());
		button1.addActionListener(new ButtonHandler());
        chkBox1.addItemListener(new CheckBoxHandler());
        comboBox1.addItemListener(new ComboBoxHandler());
        MenuItemHandler menuItemHandler = new MenuItemHandler();
        startItem.addActionListener(menuItemHandler);
        exitItem.addActionListener(menuItemHandler);

/*         
        // DON'T FORGET TO INCLUDE THIS CODE - otherwise you will not
        // be able to close your application!!!
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
        
        // STEP 6: set window size and show window
        setSize( 275, 170);
*/
        setVisible(true);
        
        // helpful if add a Key Listener
        setFocusable(true);
        
    }

    /**
     * execute application
     */
/*
    public static void main(String[] args) {
        GuiTest1 application = new GuiTest1();
    }
*/
    /******** PRIVATE INNER CLASSES FOR EVENT HANDLING ***************/
    /*
     * - Provide ActionListener event handlers for JTextField, JButton
     * - Provide ItemListener event handlers for JCheckBox, JComboBox events
     * - Provide MouseListener event handlers for mouse events
     * - Provide KeyListener event handlers for key events
     *
     * - if there is more than one component associated with a
     *  particular event handler, you can test which component caused
     *  the event using the method event.getSource()
     */
    private class TextFieldHandler implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            // replace with appropriate reaction to the text entered
            
            // sample code to test which event occurred and print out
            // text typed in text field
            if (event.getSource() == textField1) 
            { 
//                System.out.print("Text typed in textField1: ");
//                System.out.println(event.getActionCommand());
				JOptionPane.showMessageDialog(null, "Text typed in textField1: " +
														event.getActionCommand());
            }
        }
    }
    private class ButtonHandler implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            // replace with appropriate reaction to button press
            
            // sample code to show that button pressed
            if (event.getSource() == button1) {
//                System.out.print("Button1 pressed; label in it is: ");
//                System.out.println(event.getActionCommand());
				JOptionPane.showMessageDialog(null, "Button1 pressed; label in it is: " +
														event.getActionCommand());

            }
        }
    }

    private class CheckBoxHandler implements ItemListener {
        public void itemStateChanged (ItemEvent event) {
            // replace with appropriate reaction to checkbox change
            
            // sample code to test which checkbox changed and whether
            // changed to 'selected' or 'unselected'
            if (event.getSource() == chkBox1) {
                if (event.getStateChange() == ItemEvent.SELECTED)
//                    System.out.println("Chkbox1 just checked");
					JOptionPane.showMessageDialog(null, "Chkbox1 just checked");
                else
//                    System.out.println("Chkbox1 just unchecked");
					JOptionPane.showMessageDialog(null, "Chkbox1 just unchecked");
            }
        }   
    }


    private class ComboBoxHandler implements ItemListener {
        public void itemStateChanged (ItemEvent event) {
            // replace with appropriate reaction to list choice
            
            // sample code
            if (event.getSource() == comboBox1) {
                if ( event.getStateChange() == ItemEvent.SELECTED) {
                            // a particular item in list was selected
                    String itemSelected = names[comboBox1.getSelectedIndex()];
//                    System.out.println("Item selected: " + itemSelected);
					JOptionPane.showMessageDialog(null, "Item selected; " +
														itemSelected);
                }
            }
                
        }  
    }
    private class MenuItemHandler implements ActionListener {
        public void actionPerformed (ActionEvent event) {
            // replace with appropriate reaction to menu choice
            
            // sample code
            if (event.getSource() == startItem) {
                System.out.println("'Start' menu item selected");
				JOptionPane.showMessageDialog(null, "'Start' menu item selected");
            }
            else if (event.getSource() == exitItem ) {
                System.exit(0);     // EXIT
            }
        }   
    }

	
	
}
