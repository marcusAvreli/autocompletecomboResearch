package myDdl24.jax.mgi.mtb.gui.completer;

import java.util.List;

import javax.swing.JTextField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import myDdl24.gui.DropDownBoxColumnDefinition;

/**
 * A filter that will attempt to autocomplete enties into a textfield with the 
 * string representations of objects in a given array.
 *
 * Add this filter class to the Document of the text field.
 *
 * The first match in the array is the one used to autocomplete. So sort your 
 * array by most important objects first.
 * @param <E>
 */
public class MXCompleterFilter<E> extends MXAbstractCompleterFilter {
	private static final Logger logger = LoggerFactory.getLogger(MXCompleterFilter.class);
    // -------------------------------------------------------------- Constants
    // none

    // ----------------------------------------------------- Instance Variables

    protected JTextField jTextField;
    protected List<E> objectList;

    // ----------------------------------------------------------- Constructors

    /**
     * Creates a new instance of MXCompleterFilter
     * 
     * @param completerObjs an array of objects used to attempt completion
     * @param textField the text component to receive the completion
     */
    public MXCompleterFilter(List<E> completerObjs, JTextField textField,DropDownBoxColumnDefinition colDef) {
    	logger.info("=====================================");
        objectList = completerObjs;
        jTextField = textField;
        setColDef(colDef);
    }

    // --------------------------------------------------------- Public Methods

    public int getCompleterListSize() {
    	//logger.info("getCompleterListSize");
        return objectList.size();
    }

    public Object getCompleterObjectAt(int i) {
    	//logger.info("getCompleterObjectAt");
        return objectList.get(i);
    }

    public JTextField getTextField() {
    	logger.info("getTextField");
        return jTextField;
    }

    /**
     * Set the list of objects to match against.
     * @param objectsToMatch
     */
  
    // ------------------------------------------------------ Protected Methods
    // none

	public void setCompleterMatches(List<E> objectsToMatch) {
		// TODO Auto-generated method stub
		logger.info("setCompleterMatches. objectsToMatch:"+objectsToMatch.size());
		logger.info("setCompleterMatches. objectList:"+objectList.size());
		   objectList = objectsToMatch;
	        firstSelectedIndex = -1;
	}

    // -------------------------------------------------------- Private Methods
    // none

}