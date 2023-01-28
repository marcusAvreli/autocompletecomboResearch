package myDdl24.gui;

public class Country {
	private String name;
	private String displayName;
	
	public Country() {
		
	}
	
	public Country(String name,String displayName) {
		setName(name);
		setDisplayName(displayName);
	}
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayName() {
		return displayName;
	}
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

}
