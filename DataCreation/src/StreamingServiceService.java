import java.sql.CallableStatement;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class StreamingServiceService {
	private String ServiceFilepath = "../DataCreation/outputService.txt";
	private DatabaseConnectionService dbService = null;
	
	public StreamingServiceService(DatabaseConnectionService dbService) {
		this.dbService = dbService;
	}

	public void addService() {
		FileID fileReader = new FileID(ServiceFilepath);
		ArrayList<StreamingService> SS = fileReader.getService();
		for(StreamingService service : SS) {
			try{	
				CallableStatement cs = dbService.getConnection().prepareCall("{? = call addService(?, ?)}");
				cs.setLong(2, service.ID);
				cs.setString(3, service.name);
				cs.registerOutParameter(1, Types.INTEGER);
				
				cs.execute();
				int returnValue = cs.getInt(1);
				if(returnValue == 1) {
					JOptionPane.showMessageDialog(null, "ID already exists");
				}
				if(returnValue == 2) {
					JOptionPane.showMessageDialog(null, "ID cannot be null");
				}
				if(returnValue == 3) {
					JOptionPane.showMessageDialog(null, "ID cannot be negative");
				}
				if(returnValue == 4) {
					JOptionPane.showMessageDialog(null, "Streaming Service name cannot be null");
				}
				
				
			}catch(SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
