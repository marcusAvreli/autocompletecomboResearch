package myDdl24.gui;


/**
 * Custom DropDownBox column definition class.
 * 
 * @author rterrell
 *
 */
public class DropDownBoxColumnDefinition extends ColumnDefinition {

    /**
     * 
     */
    public DropDownBoxColumnDefinition() {
        super();
    }

    /**
     * @param propertyName
     * @param displayValue
     */
    public DropDownBoxColumnDefinition(String propertyName, String displayValue) {
        super(propertyName, displayValue, 0);

    }

    public DropDownBoxColumnDefinition(String propertyName,
            String displayValue, int index, int width) {
        super(propertyName, displayValue, index, width);
    }
}