package myDdl24.ac.starlink.topcat;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.ListModel;

import myDdl24.core.util.RMT2ReflectionUtil;

public class BasicCheckBoxList<T> extends CheckBoxList<T>{

	public BasicCheckBoxList(ListModel<T> model, boolean canSelect) {
		super(model, canSelect,new JLabel() );
		// TODO Auto-generated constructor stub
	}

	@Override
	  protected void configureEntryRenderer( JComponent entryRenderer, T item,  int index ) {
			((JLabel) entryRenderer).setText( toString( item ) );
}

	@Override
	public boolean isChecked(T item) {
		// TODO Auto-generated method stub
		return false;
	}
	 /**
     * Maps list items to string values that can be displayed in the list.
     * This method is just called by {@link #configureEntryRenderer}.
     *
     * @param  item  list entry
     * @return  strinification of <code>item</code> for user display
     */
    protected String toString( T item ) {
    	
        return item == null ? null : (String) RMT2ReflectionUtil.getFieldValue(item, "displayName");
    }

}
