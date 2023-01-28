package myDdl24.jax.mgi.mtb.gui.completer;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;
import javax.swing.text.Document;
import javax.swing.text.PlainDocument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import myDdl24.gui.DropDownBoxColumnDefinition;

/**
 * @param <E>
 */
public class MXCompleterTextField<E> extends JTextField {

    // -------------------------------------------------------------- Constants
    // none

    // ----------------------------------------------------- Instance Variables
	private static final Logger logger = LoggerFactory.getLogger(MXCompleterTextField.class);
    private MXCompleterFilter completerFilter;

    // ----------------------------------------------------------- Constructors

    /**
     * default constructor shows the completer window when offering matches.
     * @param completeMatches
     */
    public MXCompleterTextField(List<E> completeMatches,DropDownBoxColumnDefinition colDef) {
        this(completeMatches, true,colDef);
    }

    /**
     * useWindow - true will popup the completer window to help with matches,
     * false will just complete in the textfield with no window.
     */
    public MXCompleterTextField(List<E> completeMatches, boolean useWindow,DropDownBoxColumnDefinition colDef) {
        super();
       
        completerFilter = new MXCompleterFilterWithWindow(completeMatches, this,colDef);
        
        PlainDocument pd = new PlainDocument();
        pd.setDocumentFilter(completerFilter);
        setDocument(pd);
    }


    // --------------------------------------------------------- Public Methods

    /**
     * Warning: Calling setDocument on a completerTextField will remove the 
     * completion mecanhism for this text field if the document is not derived 
     * from AbstractDocument.
     *
     * Only AbstractDocuments support the required DocumentFilter API for 
     * completion.
     */
    public void setDocument(Document doc) {
        super.setDocument(doc);
       // logger.info("setDocument_filter");
        if (doc instanceof AbstractDocument) {
        	//logger.info("before_set_document_filter");
            ((AbstractDocument)doc).setDocumentFilter(completerFilter);
            //logger.info("after_set_document_filter");
        }
    }

    public boolean isCaseSensitive() {
    	//logger.info("setCompleterMatches:");
        return completerFilter.isCaseSensitive();
    }

    public boolean isCorrectingCase() {
    	//logger.info("setCompleterMatches:");
        return completerFilter.isCorrectingCase();
    }

    public void setCaseSensitive(boolean caseSensitive) {
        completerFilter.setCaseSensitive(caseSensitive);
    }

    /**
     * Will change the user entered part of the string to match the case of the matched item.
     *
     * e.g.
     * "europe/lONdon" would be corrected to "Europe/London"
     *
     * This option only makes sense if case sensitive is turned off
     */
    public void setCorrectCase(boolean correctCase) {
    	//logger.info("setCompleterMatches:");
        completerFilter.setCorrectCase(correctCase);
    }

    /**
     * Set the list of objects to match against.
     * @param completeMatches
     */
    public void setCompleterMatches(List<E> completeMatches) {
    //logger.info("setCompleterMatches:");
      completerFilter.setCompleterMatches(completeMatches);
    }

    // ------------------------------------------------------ Protected Methods
    // none

    // -------------------------------------------------------- Private Methods
    // none
}