package dinhtrong.app.backupsms.database.filter;


public class FilterPerform {
	public static final String DEFAULT_STRING = null;
	public static final int DEFAULT_INT = 0;
	
	protected String[] columns = null, selectionArgs = null;
	protected String selection = DEFAULT_STRING , groupBy = DEFAULT_STRING, having = DEFAULT_STRING, orderBy = DEFAULT_STRING, limit = DEFAULT_STRING;
	
	private int id = DEFAULT_INT;
	
	public void reset() {
		this.id = DEFAULT_INT;
		
		columns = null;
		selectionArgs= null;
		
		selection = DEFAULT_STRING ;
		groupBy = DEFAULT_STRING;
		having = DEFAULT_STRING;
		orderBy = DEFAULT_STRING;
		limit = DEFAULT_STRING;
	}
	
	protected void buildSelection(String key, String value){
		if(selection == null || selection.trim().isEmpty()){
			selection = key + "=" + value;
		}
		else{
			selection += " AND " + key + "=" + value;
		}
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
		buildSelection("id", id+"");
	}

	public String[] getColumns() {
		return columns;
	}

	public void setColumns(String[] columns) {
		this.columns = columns;
	}

	public String[] getSelectionArgs() {
		return selectionArgs;
	}

	public void setSelectionArgs(String[] selectionArgs) {
		this.selectionArgs = selectionArgs;
	}

	public String getSelection() {
		return selection;
	}

	public void setSelection(String selection) {
		this.selection = selection;
	}

	public String getGroupBy() {
		return groupBy;
	}

	public void setGroupBy(String groupBy) {
		this.groupBy = groupBy;
	}

	public String getHaving() {
		return having;
	}

	public void setHaving(String having) {
		this.having = having;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getLimit() {
		return limit;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}
}
