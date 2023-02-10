package ui;
import java.util.ArrayList;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class MainFrame {
	private DatabaseConnectionService dbcs;
	private String[][] movieList = new String[0][0];
	private String[][] sortedMovieList = new String[0][0];
	private String[] columnNames = {"Title", "Rating", "Release Date", "Runtime"};

	
	public void createFrame(DatabaseConnectionService dbcs) {
		this.dbcs = dbcs;
		
		JFrame frame = new JFrame();
		frame.setLayout(new BorderLayout());
		
		JPanel searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.Y_AXIS));
		JLabel text = new JLabel("Select search terms");
		
		this.movieList = getMovieInfo();
				
		JTable table = new JTable();
		table.setModel(new DefaultTableModel(movieList, columnNames));
		JScrollPane tablePane = new JScrollPane(table);
		
		JPanel searchOptionsPanel = new JPanel();
		searchOptionsPanel.setLayout(new BoxLayout(searchOptionsPanel, BoxLayout.X_AXIS));
		
		JPanel dropDownPanel = new JPanel();
		createDropDowns(dropDownPanel);
		searchOptionsPanel.add(dropDownPanel);
		
		JPanel textFieldPanel = new JPanel();
		createTextBoxes(textFieldPanel, table);
		searchOptionsPanel.add(textFieldPanel);
		

		
		searchPanel.add(text);
		searchPanel.add(searchOptionsPanel);
		frame.add(searchPanel, BorderLayout.NORTH);
		frame.add(tablePane, BorderLayout.SOUTH);
		
		frame.setVisible(true);
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	
	private String[][] getMovieInfo() {
		String movieQuery = "SELECT * FROM Media m JOIN Movie mo ON m.ID=mo.MediaID ORDER BY m.Title ASC";
		ArrayList<String[]> movieTitles = new ArrayList<>();
		
		try {
			CallableStatement cs = dbcs.getConnection().prepareCall(movieQuery);//should probably write sprocs for this but it's easy enough to justt write new strings so meh   

		    boolean isResultSet = cs.execute();

		    if (!isResultSet) {
		    	System.out.println("The first result is not a ResultSet.");
		    }else {
		    	ResultSet rs = cs.getResultSet();
			while (rs.next()) {
				String[] movieData = new String[4];
				movieData[0] = rs.getString("Title");
				String rating = rs.getString("Rating");
				movieData[1] = rating.substring(0, 3);
				movieData[2] = rs.getString("ReleaseDate");
				movieData[3] = rs.getString("Runtime");
				movieTitles.add(movieData);
			}
		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return convertArrayListToArray(movieTitles);
	}
	
	public void createDropDowns(JPanel panel) {
//		String[] mediaTypes = {"Select media type", "Movie", "TV Show"};
//		String[] genres = {"Select genre", "Horror", "Comedy", "etc", "Use API to get actual genres"};

		
		
		
		
//		JComboBox<String> mediaType = new JComboBox<String>(mediaTypes);
//		panel.add(mediaType);
		
//		JComboBox<String> genre = new JComboBox<String>(genres);
//		panel.add(genre);
	}
	
	public void createTextBoxes(JPanel panel, JTable table) {
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel charStartLabel = new JLabel("Search by starting letter (empty is all)");
		JTextField charField = new JTextField(1);
		panel.add(charStartLabel);
		panel.add(charField);
		
		charField.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String text = charField.getText();
				text = text.toLowerCase();
				if (text.length() <= 0) {
					sortMovieList('\0', table);
				} else {
					char first = text.charAt(0);
					
					sortMovieList(first, table);
				}
			}
		});
		
	}
	
	private String[][] convertArrayListToArray(ArrayList<String[]> l) {
		String[][] a = l.toArray(new String[0][0]);
		return a;
	}
	
	private void sortMovieList(char first, JTable table) {
		if (first == '\0') {
			table.setModel(new DefaultTableModel(movieList, columnNames));
			return;
		}
		
		ArrayList<String[]> sorted = new ArrayList<>();
		for (String[] s: movieList) {
			// s[0] will always be Title
			if (s[0].toLowerCase().charAt(0) == first) {
				sorted.add(s);
			}
		}
		
		this.sortedMovieList = convertArrayListToArray(sorted);
		
		table.setModel(new DefaultTableModel(sortedMovieList, columnNames));
	}
	
}
