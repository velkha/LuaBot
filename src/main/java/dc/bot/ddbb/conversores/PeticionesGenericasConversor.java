package dc.bot.ddbb.conversores;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.sql.rowset.CachedRowSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
 * Convierte crs en datos genericos ya sean arrays de strings o single strings
 */
public class PeticionesGenericasConversor {
	private Gson gson = new GsonBuilder().create();
	
	public String[] convertCRSToPageContentArray(CachedRowSet crs) throws SQLException {
		List<String> returnText = new ArrayList<String>();
		while(crs.next()) {
			returnText.add(crs.getString("page_contents"));
		}
		return returnText.toArray(new String[0]);
	}
	
	public String getStringValueForWatson(CachedRowSet crs, String columnName) throws SQLException {
		String str=null;
		if (crs.next())
		str=crs.getString(columnName);
		return str;
	}
}
