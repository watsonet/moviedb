import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Types;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import ui.Main;

public class WatchedService {
	// check the filepath
	private DatabaseConnectionService dbService = null;

//	private int MID;
	public WatchedService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public void addWatched(int MID) {
//		FileID fileReader = new FileID(TVFilepath);

		try {
			CallableStatement cs = dbService.getConnection().prepareCall("{? = call addWatched(?, ?)}");
			cs.setString(2, "'" + Main.currentUser + "'");
			cs.setLong(3, MID);
			cs.registerOutParameter(1, Types.INTEGER);

			cs.execute();
			int returnValue = cs.getInt(1);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
