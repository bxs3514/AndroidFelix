package afelix.service.controler.database;

import java.util.ArrayList;
import java.util.HashMap;

public interface DatabaseControler {
	public void addTable(String table, ArrayList<String> column, HashMap<String, String> type);
	public void dropTable(String table);
	public void Insert(String table, ArrayList<String> columnElements);
	public void Delete(String table,  ArrayList<String> factor);
}
