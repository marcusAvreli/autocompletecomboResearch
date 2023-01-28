package myDdl24.jax.mgi.mtb.gui.completer;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowFocusListener;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JWindow;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


//import myDdl21.gui.ColumnCellRenderer;
import myDdl24.gui.DropDownBoxColumnDefinition;
import myDdl24.ac.starlink.topcat.BasicCheckBoxList;
import myDdl24.ac.starlink.topcat.GenericList;


/**
 * Copyright Neil Cochrane 2006
 * @author neilcochrane
 * @param <E>
 */
public class MXCompleterFilterWithWindow<E> extends MXCompleterFilter {
	private static final Logger logger = LoggerFactory.getLogger(MXCompleterFilterWithWindow.class);
    // -------------------------------------------------------------- Constants
    public static int MAX_VISIBLE_ROWS = 8;

    // ----------------------------------------------------- Instance Variables

    protected FilterWindowListener filterWL;
    protected JWindow jWin;
    protected TextFieldKeyListener keylisterTF;
    protected ListSelListener listenerList;
    protected ListMouseListener listenerMouse;
    //protected JList jList;
    BasicCheckBoxList<E> jList;
    protected JScrollPane jScrollPane;
    protected GenericList<E> filterListModel;

    protected boolean bAdjusting = false;

    // ----------------------------------------------------------- Constructors

    public MXCompleterFilterWithWindow(List<E>completerObjs, JTextField textField,DropDownBoxColumnDefinition colDef) {
        super(completerObjs, textField,colDef);
        _init();
    }

    // --------------------------------------------------------- Public Methods

    public void insertString(FilterBypass filterBypass, int offset,
                             String string, AttributeSet attributeSet)
    throws BadLocationException {
    	logger.info("insert string. completerObjs");
    	if(null != objectList) {
    		logger.info("objectList_size:"+objectList.size());
    	}else {
    		logger.info("objectList_is_empty");
    	}
        setFilterWindowVisible(false);
        super.insertString(filterBypass, offset, string, attributeSet);
    }

    public void remove(FilterBypass filterBypass, int offset, int length)
    throws BadLocationException {
    	logger.info("remove string");
    	if(null != objectList) {
    		logger.info("objectList_size:"+objectList.size());
    	}else {
    		logger.info("objectList_is_empty");
    	}
        setFilterWindowVisible(false);
        super.remove(filterBypass, offset, length);
    }

    public void replace(FilterBypass filterBypass, int offset, int length,
                        String string, AttributeSet attributeSet)
    throws BadLocationException {
    	logger.info("replace_started");
    	try {
    	
    	if(null != objectList) {
    		logger.info("objectList_size:"+objectList.size());
    	}else {
    		logger.info("objectList_is_empty");
    	}
        if (bAdjusting) {
            filterBypass.replace(offset, length, string, attributeSet);
            logger.info("exit1");
            return;
        }
       
        logger.info("before replace");
        super.replace(filterBypass, offset, length, string, attributeSet);
        logger.info("after replace");
        if(null != objectList) {
    		logger.info("objectList_size:"+objectList.size());
    	}else {
    		logger.info("objectList_is_empty");
    	}
        if (getLeadingSelectedIndex() == -1) {
            if (isFilterWindowVisible()) {
                setFilterWindowVisible(false);
            }
            logger.info("exit2");
            return;
        }
        logger.info("before set filter");
        filterListModel.setFilter(preText);
        logger.info("after set filter");
        if(null != objectList) {
    		logger.info("objectList_size:"+objectList.size());
    	}else {
    		logger.info("objectList_is_empty");
    	}
        if (isFilterWindowVisible()) {
            _setWindowHeight();
        } else {
            setFilterWindowVisible(true);
        }

        jList.setSelectedValue(jTextField.getText(), true);
    	}catch(UnsupportedOperationException ex) {
    		logger.error("UnsupportedOperationException",ex);
    	}
    	logger.info("replace_ended");
    }
    public boolean isFilterWindowVisible() {
    	//logger.info("isFilterWindowVisible");
        return ((jWin != null) && (jWin.isVisible()));
    }

    public void setCaseSensitive(boolean caseSensitive) {
        super.setCaseSensitive(caseSensitive);
      //  filterListModel.setCaseSensitive(caseSensitive);
    }

    public void setFilterWindowVisible(boolean visible) {
    	logger.info("setFilterWindowVisible");
        if (visible) {
            _initWindow();
            if(null != objectList) {
        		logger.info("objectList_size:"+objectList.size());
        	}else {
        		logger.info("objectList_is_empty");
        	}
            jList.setModel(filterListModel);
            //jList.setCellRenderer(new ColumnCellRenderer<E>(jList));
            jWin.setVisible(true);
            jTextField.requestFocus();
            jTextField.addFocusListener(filterWL);
        } else {
        	if(null != objectList) {
        		logger.info("objectList_size:"+objectList.size());
        	}else {
        		logger.info("objectList_is_empty");
        	}
            if (jWin == null) {
                return;
            }

            jWin.setVisible(false);
            if(null != objectList) {
        		//logger.info("objectList_size:"+objectList.size());
        	}else {
        		//logger.info("objectList_is_empty");
        	}
            jWin.removeFocusListener(filterWL);
            Window ancestor = SwingUtilities.getWindowAncestor(jTextField);
            ancestor.removeMouseListener(filterWL);
            jTextField.removeFocusListener(filterWL);
            jTextField.removeAncestorListener(filterWL);
            jList.removeMouseListener(listenerMouse);
            jList.removeListSelectionListener(listenerList);
            listenerList = null;
            listenerMouse = null;
            jWin.dispose();
            jWin = null;
            jList = null;
        }
    }
    public void setCompleterMatches(List objectsToMatch) {
    	logger.info("setCompleterMatches");
        if (isFilterWindowVisible()) {
            setFilterWindowVisible(false);
        }

        super.setCompleterMatches(objectsToMatch);
        filterListModel.setCompleterMatches(objectsToMatch);
    }

    // ------------------------------------------------------ Protected Methods
    // none

    // -------------------------------------------------------- Private Methods
    private void _init() {
    	logger.info("_init");
    	if(null != objectList) {
    		//logger.info("objectList_size:"+objectList.size());
    	}else {
    		//logger.info("objectList_is_empty");
    	}
        filterWL = new FilterWindowListener();
        filterListModel = new GenericList<E>(objectList);
        keylisterTF = new TextFieldKeyListener();
        jTextField.addKeyListener(keylisterTF);
     
        EscapeAction escape = new EscapeAction();
        jTextField.registerKeyboardAction(escape,
                KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,0),
                JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }


    private void _initWindow() {
    	logger.info("_initWindow");
    	if(null != objectList) {
    		//logger.info("objectList_size:"+objectList.size());
    	}else {
    		//logger.info("objectList_is_empty");
    	}
        Window ancestor = SwingUtilities.getWindowAncestor(jTextField);
        jWin = new JWindow(ancestor);
        jWin.addWindowFocusListener(filterWL);
        jTextField.addAncestorListener(filterWL);
        ancestor.addMouseListener(filterWL);
        listenerList = new ListSelListener();
        listenerMouse = new ListMouseListener();

        jList = new BasicCheckBoxList<E>(filterListModel,true);
        jList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        jList.setFocusable(false);
       // jList.setPrototypeCellValue("Prototype");
        jList.addListSelectionListener(listenerList);
        jList.addMouseListener(listenerMouse);

        jScrollPane = new JScrollPane(jList,
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane.setFocusable(false);
        jScrollPane.getVerticalScrollBar().setFocusable(false);

        _setWindowHeight();
        jWin.setLocation(jTextField.getLocationOnScreen().x,
                         jTextField.getLocationOnScreen().y +
                             jTextField.getHeight());
        jWin.getContentPane().add(jScrollPane);
    }

    private void _setWindowHeight() {
        int height =
                jList.getFixedCellHeight() *
                Math.min(MAX_VISIBLE_ROWS, filterListModel.getSize());
        height += jList.getInsets().top + jList.getInsets().bottom;
        height += jScrollPane.getInsets().top + jScrollPane.getInsets().bottom;
        if(null != objectList) {
    		//logger.info("objectList_size:"+objectList.size());
    	}else {
    		//logger.info("objectList_is_empty");
    	}
        jWin.setSize(jTextField.getWidth(), height);
        // bottom border fails to draw without this
        jScrollPane.setSize(jTextField.getWidth(), height);
    }


    class EscapeAction extends AbstractAction {
        public void actionPerformed(ActionEvent e) {
            if (isFilterWindowVisible()) {
                setFilterWindowVisible(false);
            }
        }
    }

    private class FilterWindowListener extends MouseAdapter
            implements AncestorListener, FocusListener, WindowFocusListener {
        public void ancestorMoved(AncestorEvent event) {
            setFilterWindowVisible(false);
        }
        public void ancestorAdded(AncestorEvent event) {
            setFilterWindowVisible(false);
        }
        public void ancestorRemoved(AncestorEvent event) {
            setFilterWindowVisible(false);
        }

        public void focusLost(FocusEvent e) {
            if (e.getOppositeComponent() != jWin) {
                setFilterWindowVisible(false);
            }
        }

        public void focusGained(FocusEvent e){}

        public void windowLostFocus(WindowEvent e) {
            Window w = e.getOppositeWindow();

            if (w.getFocusOwner() != jTextField) {
                setFilterWindowVisible(false);
            }
        }
        public void windowGainedFocus(WindowEvent e) {}


        public void mousePressed(MouseEvent e) {
            setFilterWindowVisible(false);
        }
    }

    private class TextFieldKeyListener extends KeyAdapter {

        public void keyPressed(KeyEvent e) {
            if (!((e.getKeyCode() == KeyEvent.VK_DOWN) ||
                    (e.getKeyCode() == KeyEvent.VK_UP) ||
                    ((e.getKeyCode() == KeyEvent.VK_PAGE_DOWN)
                            && (isFilterWindowVisible())) ||
                    ((e.getKeyCode() == KeyEvent.VK_PAGE_UP)
                            && (isFilterWindowVisible())) ||
                    (e.getKeyCode() == KeyEvent.VK_ENTER))) {
                return;
            }

            if ((e.getKeyCode() == KeyEvent.VK_DOWN) &&
                    !isFilterWindowVisible()) {
                preText = jTextField.getText();
                filterListModel.setFilter(preText);

                if (filterListModel.getSize() > 0) {
                    setFilterWindowVisible(true);
                } else {
                    return;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                if (isFilterWindowVisible()) {
                    setFilterWindowVisible(false);
                }

                jTextField.setCaretPosition(jTextField.getText().length());
                return;
            }

            int index = -1;

            if (e.getKeyCode() == KeyEvent.VK_DOWN) {
                index = Math.min(jList.getSelectedIndex() + 1,
                                 jList.getModel().getSize()-1);
            } else if (e.getKeyCode() == KeyEvent.VK_UP) {
                index = Math.max(jList.getSelectedIndex() - 1, 0);
            } else if (e.getKeyCode() == KeyEvent.VK_PAGE_UP) {
                index = Math.max(jList.getSelectedIndex() - MAX_VISIBLE_ROWS, 0);
            } else if (e.getKeyCode() == KeyEvent.VK_PAGE_DOWN) {
                index = Math.min(jList.getSelectedIndex() + MAX_VISIBLE_ROWS,
                                 jList.getModel().getSize()-1);
            }

            if (index == -1) {
                return;
            }

            jList.setSelectedIndex(index);
            jList.scrollRectToVisible(jList.getCellBounds(index, index));
        }
    }

    private class ListSelListener implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            bAdjusting = true;
            jTextField.setText(jList.getSelectedValue().toString());
            bAdjusting = false;
            jTextField.select(preText.length(), jTextField.getText().length());
        }
    }

    private class ListMouseListener extends MouseAdapter {
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                setFilterWindowVisible(false);
            }
        }
    }

}
