package com.granthc.levelsbeyondassessment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DatabaseController {

	final static String SETUP_NOTE_TABLE = "CREATE TABLE IF NOT EXISTS NOTES (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, BODY TEXT NOT NULL);";
	final static String CREATE_NOTE = "INSERT INTO NOTES (BODY) VALUES (?)";
	final static String GET_LAST_ID = "SELECT last_insert_rowid()";
	final static String GET_NOTE_WITH_ID = "SELECT * FROM NOTES WHERE ID = ?";
	final static String GET_ALL_NOTES_WITH_QUERY = "SELECT * FROM NOTES WHERE BODY LIKE ?";
	final static String UPDATE_NOTE_WITH_ID = "UPDATE NOTES SET BODY = ? WHERE ID = ?";
	
	/**
	 * I don't really like adding the "throws exception" format, I'd rather handle
	 * the exception, but in this case, I wanted to make sure that my connections
	 * were closed in the finally block and didn't want to add another try / catch.
	 * Can explain more if wanted.
	 * 
	 * @throws SQLException
	 */
	public static void establishDbConnection() throws SQLException {
		Connection c = null;
		Statement initialize = null;

		try {
			Class.forName("org.sqlite.JDBC");

			// get connection
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");

			// create table if needed
			initialize = c.createStatement();
			initialize.executeUpdate(SETUP_NOTE_TABLE);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// close connections
			initialize.close();
			c.close();
		}
	}

	public static Note saveNote(String body) {
		Connection c = null;
		PreparedStatement insertStmt = null;
		Statement readStmt = null;
		int newNoteId = -1;

		try {
			// setup db connection
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");
			c.setAutoCommit(false);

			// Insert the note using parameterized queries so that sql injection is not
			// possible
			insertStmt = c.prepareStatement(CREATE_NOTE);
			insertStmt.setString(1, body);
			insertStmt.executeUpdate();

			// retrieve the ID of the recently inserted Note
			readStmt = c.createStatement();
			ResultSet rs = readStmt.executeQuery(GET_LAST_ID);
			while (rs.next()) {
				newNoteId = rs.getInt("last_insert_rowid()");
			}

			// TODO: move these to a finally
			rs.close();
			insertStmt.close();
			readStmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Note(newNoteId, body);
	}

	public static Note getNote(int id) {
		Connection c = null;
		PreparedStatement stmt = null;
		String noteBody = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");

			stmt = c.prepareStatement(GET_NOTE_WITH_ID);
			stmt.setString(1, Integer.toString(id));
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				noteBody = rs.getString("body");
			}
			// TODO: Close everything

		} catch (Exception e) {
			e.printStackTrace();
		}

		return (noteBody == null) ? null : new Note(id, noteBody);
	}

	public static ArrayList<Note> getAllNotes(Optional<String> query) {
		Connection c = null;
		PreparedStatement stmt = null;
		ArrayList<Note> notesToReturn = new ArrayList<Note>();

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");

			stmt = c.prepareStatement(GET_ALL_NOTES_WITH_QUERY);

			// add wildcards here because they aren't addable in the setString()
			String queryString = "";
			if (query.isPresent()) // check before get so we don't throw exception
				queryString = "%" + query.get() + "%";
			else
				queryString = "%";

			stmt.setString(1, queryString);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				notesToReturn.add(new Note(rs.getInt("id"), rs.getString("body")));
			}
// TODO: close things
		} catch (Exception e) {
			e.printStackTrace();
		}

		return notesToReturn;
	}
	
	public static Note updateNote(int id, String updateBody) {
		Connection c = null;
		PreparedStatement insertStmt = null;

		try {
			// setup db connection
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");
			c.setAutoCommit(false);

			// Insert the note using parameterized queries so that sql injection is not
			// possible
			insertStmt = c.prepareStatement(UPDATE_NOTE_WITH_ID);
			insertStmt.setString(1, updateBody);
			insertStmt.setString(2,  Integer.toString(id));
			insertStmt.executeUpdate();
			

			// TODO: move these to a finally
			insertStmt.close();
			c.commit();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Note(id, updateBody);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
