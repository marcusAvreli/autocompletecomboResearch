package myDdl24;

import javax.swing.SwingUtilities;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import myDdl24.gui.MainFrame;


//https://github.com/Starlink/starjava/blob/10debdc47d917da4ad409e027616a5ce19faab93/topcat/src/main/uk/ac/starlink/topcat/CheckBoxList.java
public class App 
{
	private static final Logger logger = LoggerFactory.getLogger(App.class);
public static void main( String[] args )
{
   logger.info("application started");
  
	SwingUtilities.invokeLater(new Runnable() {

			public void run() {
				new MainFrame().setVisible(true);
			}
		});
}
}