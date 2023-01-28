package myDdl24.ac.starlink.topcat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.AbstractListModel;
import javax.swing.ListCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class GenericList<E> extends AbstractListModel<E>{
	private static final Logger logger = LoggerFactory.getLogger(GenericList.class);
	private List<E> data;
	//mx filter
	 private List<E> arrFullList=null;
	 //mx filter
	    private List<E> listFiltered=null;
	    //mx filter
	private String strFilter;
	//mx filter
	boolean bCaseSensitive  = false;
	public GenericList( List<E> data ) {		
       setData(data);
       arrFullList = data;
       listFiltered = data;
       logger.info("size_of_arrFullList: "+arrFullList.size());
    }
	
	
	
	public List<E> getData() {
		 logger.info("size_of_arrFullList: "+arrFullList.size());
		return data;
	}


	public void setData(List<E> data) {
		if(null!=arrFullList) {
		 logger.info("size_of_arrFullList: "+arrFullList.size());
		}else {
			logger.info("size_of_arrFullList is empty");
		}
		this.data = data;
		
	}

	//mx filter
	  public void setFilter(String filter) {
		  logger.info("start_filter_size_of_arrFullList: "+arrFullList.size());
			 // listFiltered.clear();
			  
		  
	      
	        logger.info("start_filter_size_of_arrFullList: "+arrFullList.size());
	        for(int i = 0; i < arrFullList.size(); i++) {
	            Object obj = arrFullList.get(i);
	            logger.info("class of object:"+obj.getClass().getSimpleName());
	            if (obj.toString().length() < filter.length()) {
	                continue;
	            }

	            if (bCaseSensitive) {
	                if (obj.toString().startsWith(filter)) {
	                    listFiltered.add((E) obj);
	                }
	            } else {
	                if (obj.toString().substring(0, filter.length()).compareToIgnoreCase(filter) == 0) {
	                    listFiltered.add((E) obj);
	                }
	            }
	        }
	        
	        logger.info("before_fire_size_of_arrFullList: "+arrFullList.size());
	        fireContentsChanged(this, 0, listFiltered.size());
	        logger.info("after_fire_size_of_arrFullList: "+arrFullList.size());
	        
	        
	        logger.info("set_filter_finished");
	    }
	//mx filter
	   public void setCompleterMatches(List<E> objectsToMatch) {
		   logger.info("size_of_arrFullList: "+arrFullList.size());
	        arrFullList = objectsToMatch;
	        logger.info("size_of_arrFullList: "+arrFullList.size());
	        clearFilter();
	    }
	   //mx filter
	   public void clearFilter() {
		   logger.info("size_of_arrFullList: "+arrFullList.size());
	        strFilter = null;
	        listFiltered = arrFullList;
	        logger.info("size_of_arrFullList: "+arrFullList.size());
	    }
//abstract list model
	@Override
	public E getElementAt(int arg0) {	
		//logger.info("getElementAt");
		return data.get(arg0);
	}
	//abstract list model
	@Override
	public int getSize() {
		// TODO Auto-generated method stub
		return data.size();
	}

}
