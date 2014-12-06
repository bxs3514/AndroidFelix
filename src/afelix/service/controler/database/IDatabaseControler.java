package afelix.service.controler.database;

import java.util.ArrayList;
import java.util.HashMap;

public interface IDatabaseControler {
	public void addTable(String table, ArrayList<String> column, HashMap<String, String> type);
	public void dropTable(String table);
	public void Insert(String table, ArrayList<String> columnElements);
	public void Delete(String table,  ArrayList<String> factor);
	public void Replace(String table, ArrayList<String> columnElements);
	public ArrayList<HashMap<String,String>> Query(String SqlQuery);
	public ArrayList<HashMap<String,String>> Query(String[] select, 
			String table, String where);
}
