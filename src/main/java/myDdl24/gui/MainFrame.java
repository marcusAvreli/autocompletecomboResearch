package myDdl24.gui;

import java.awt.GridLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;


import myDdl24.ac.starlink.topcat.BasicCheckBoxList;
import myDdl24.ac.starlink.topcat.GenericList;
import myDdl24.jax.mgi.mtb.gui.completer.MXCompleterTextField;



public class MainFrame extends JFrame {
	private JPanel mainPanel;

	public MainFrame() {
		initComponents();
	}

	private void initComponents() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		mainPanel = new JPanel();
		Country country = new Country("c1", "c1 DN1");
		List<Country> countries = new ArrayList<Country>();
		countries.add(country);
		country = new Country("c2", "c2 DN2");
		countries.add(country);
		country = new Country("c3", "c3 DN3");
		countries.add(country);
		
		
	
		
		DropDownBoxColumnDefinition def = new DropDownBoxColumnDefinition("name", "displayName");
	


		MXCompleterTextField<Country> field  = new MXCompleterTextField<Country>(countries, true,def)  ;
	
	  
		 JPanel panel = new JPanel(new GridLayout(11, 2, 5, 2));
	    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
	    panel.add(new JLabel("Auto-complete text field:"));
	    panel.add(field);
		
		
		mainPanel.add(field);
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.LINE_AXIS));
		GroupLayout layout = new GroupLayout(getContentPane());
		getContentPane().setLayout(layout);
		layout.setHorizontalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)

				.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE));
		layout.setVerticalGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
				.addGroup(layout.createSequentialGroup()

						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(mainPanel, GroupLayout.DEFAULT_SIZE, 537, Short.MAX_VALUE)));

		pack();
		setVisible(true);
	}

}
