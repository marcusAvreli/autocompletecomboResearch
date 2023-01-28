package myDdl24.ac.starlink.topcat;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.DefaultListCellRenderer;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//https://www.comp.nus.edu.sg/~cs3283/ftp/Java/swingConnect/tech_topics/jlist_1/jlist.html
public abstract class CheckBoxList<T> extends JList<T> {
	private static final Logger logger = LoggerFactory.getLogger(CheckBoxList.class);
	private static final long serialVersionUID = 5034061175372559540L;
	private final boolean canSelect_;
	// private final CheckBoxCellRenderer renderer_;
	// private final DragListener dragger_;
	private final List<ListDataListener> listeners_;
	private String[] msgLines_;
	private final CheckBoxCellRenderer renderer_;

	public CheckBoxList(ListModel<T> model, boolean canSelect, JComponent entryRenderer) {
		super(model);
		canSelect_ = canSelect;
		listeners_ = new ArrayList<ListDataListener>();
		model.addListDataListener(new ListDataListener() {
			public void contentsChanged(ListDataEvent evt) {
				for (ListDataListener l : listeners_) {
					l.contentsChanged(evt);
				}
			}

			public void intervalAdded(ListDataEvent evt) {
				for (ListDataListener l : listeners_) {
					l.intervalAdded(evt);
				}
			}

			public void intervalRemoved(ListDataEvent evt) {
				for (ListDataListener l : listeners_) {
					l.intervalRemoved(evt);
				}
			}
		});

		/* Set up cell rendering. */
		renderer_ = new CheckBoxCellRenderer(entryRenderer);
		 setCellRenderer( renderer_ );
	}
	  /**
     * This method is called whenever the list cell needs to be painted.
     *
     * @param  entryRenderer  renderer object supplied at construction time
     * @param  item   list entry
     * @param  index  index in list at which entry appears
     */
    protected abstract void configureEntryRenderer( JComponent entryRenderer,
                                                    T item, int index );

    /**
     * Indicates whether the checkbox for a given item is selected.
     *
     * @param  item   list entry
     * @return  true iff item is selected
     */
    public abstract boolean isChecked( T item );

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

	}
	/*
	 * GenericList getContents() { logger.info("get contents"); return
	 * (GenericList)getModel(); }
	 */

	private class CheckBoxCellRenderer extends JPanel implements ListCellRenderer<T> {

		/**
		 * 
		 */
		private static final long serialVersionUID = -2261952869182558757L;
		private final JComponent entryRenderer_;
		//private final JCheckBox checkBox_;
		//private final JLabel handle_;
		private final DefaultListCellRenderer dfltRenderer_;

		/**
		 * Constructor.
		 *
		 * @param entryRenderer renderer for list entry contents (excluding drag and
		 *                      checkbox decorations)
		 */
		CheckBoxCellRenderer(JComponent entryRenderer) {
			entryRenderer_ = entryRenderer;
			setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
			//checkBox_ = new JCheckBox();
			//checkBox_.setOpaque(false);
			//handle_ = new JLabel("handle");
			dfltRenderer_ = new DefaultListCellRenderer();
			setOpaque(true);
			//add(handle_);
			//add(checkBox_);
			add(entryRenderer_);
		}

		public CheckBoxCellRenderer getListCellRendererComponent(JList<? extends T> list, T item, int index,
				boolean isSel, boolean hasFocus) {
			dfltRenderer_.getListCellRendererComponent(list, item, index, isSel, hasFocus);
			setBackground(dfltRenderer_.getBackground());
			setForeground(dfltRenderer_.getForeground());
			setBorder(dfltRenderer_.getBorder());
			final int itemWidth;
			if (item != null) {
			//	checkBox_.setSelected(isChecked(item));
				configureEntryRenderer(entryRenderer_, item, index);
				entryRenderer_.validate();
				itemWidth = entryRenderer_.getPreferredSize().width;
			} else {
				itemWidth = 0;
			}

			/*
			 * Desperate measures to get the sizing of this component right. For reasons
			 * I've tried very hard, and failed, to understand, if this preferred width is
			 * not set explicitly here, the preferred size of the JList is sometimes
			 * reported wrong (the size of the first entry).
			 */
			Insets insets = getInsets();
			int totWidth = 100;
			int height = super.getPreferredSize().height;
			setPreferredSize(new Dimension(totWidth, height));
			return this;
		}

		/**
		 * Indicates whether a given point within this component is positioned over the
		 * checkbox part.
		 *
		 * @param point graphics position
		 * @return true iff point is over the checkbox
		 */
		boolean isCheckBox(Point p) {
			return false;
		}

		/**
		 * Indicates whether a given point within this component is positioned over the
		 * drag handle part.
		 *
		 * @param point graphics position
		 * @return true iff point is over the drag handle
		 */
		boolean isHandle(Point p) {
			return false;
		}

		/**
		 * From what it says about list cell renderers in the JList javadocs, I thought
		 * that this was what {@link javax.swing.JComponent#paint(java.awt.Graphics)}
		 * did, but it seems not.
		 */
		public void stamp(Graphics g) {
			Rectangle bounds = getBounds();
			g.translate(bounds.x, bounds.y);
			paintComponent(g);
			paintBorder(g);
			paintChildren(g);
			g.translate(-bounds.x, -bounds.y);
		}
	}

	/**
	 * Returns a new action that can be used to check or uncheck all the items in
	 * the list at once.
	 *
	 * @param isChecked true to check all, false to uncheck all
	 */

}
