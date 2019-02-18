package com.granthc.levelsbeyondassessment;

import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;

public class DatabaseController {

	// SQL statements
	final static String SETUP_NOTE_TABLE = "CREATE TABLE IF NOT EXISTS NOTES (ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, BODY TEXT NOT NULL);";
	final static String CREATE_NOTE = "INSERT INTO NOTES (BODY) VALUES (?)";
	final static String GET_LAST_ID = "SELECT last_insert_rowid()";
	final static String GET_NOTE_WITH_ID = "SELECT * FROM NOTES WHERE ID = ?";
	final static String GET_ALL_NOTES_WITH_QUERY = "SELECT * FROM NOTES WHERE BODY LIKE ?";
	final static String UPDATE_NOTE_WITH_ID = "UPDATE NOTES SET BODY = ? WHERE ID = ?";
	final static String DELETE_NOTE_WITH_ID = "DELETE FROM NOTES WHERE ID = ?";
	final static String DELETE_ALL_NOTES_WITH_QUERY = "DELETE FROM NOTES WHERE BODY LIKE ?";

	// return strings
	final static String NOTE_NOT_FOUND = "Note not found";
	final static String NOTE_FOUND_AND_DELETED = "Note found and deleted";
	final static String ALL_NOTES_DELETED = "All notes Deleted";
	final static String NOTES_WITH_BODY_DELETED = "Notes with body deleted";

	public static void establishDbConnection() {
		Connection c = null;
		Statement initialize = null;

		try {
			Class.forName("org.sqlite.JDBC");

			// get connection
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");

			// create table if needed
			initialize = c.createStatement();
			initialize.executeUpdate(SETUP_NOTE_TABLE);

			initialize.close();
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static Note saveNote(String body) {
		Connection c = null;
		PreparedStatement insertStmt = null;
		Statement readStmt = null;
		int newNoteId = -1;

		try {
			// setup DB connection
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");

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

			rs.close();
			insertStmt.close();
			readStmt.close();
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

			rs.close();
			stmt.close();
			c.close();

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

			rs.close();
			stmt.close();
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return notesToReturn;
	}

	public static Note updateNote(int id, String updateBody) {
		Connection c = null;
		PreparedStatement stmt = null;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");

			stmt = c.prepareStatement(UPDATE_NOTE_WITH_ID);
			stmt.setString(1, updateBody);
			stmt.setString(2, Integer.toString(id));
			stmt.executeUpdate();

			stmt.close();
			c.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Note(id, updateBody);
	}

	public static String deleteNote(int id) {
		Connection c = null;
		PreparedStatement stmt = null;

		Note requestedNote = getNote(id);
		if (requestedNote == null)
			return NOTE_NOT_FOUND;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");

			stmt = c.prepareStatement(DELETE_NOTE_WITH_ID);
			stmt.setString(1, Integer.toString(id));
			stmt.execute();

			stmt.close();
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		return NOTE_FOUND_AND_DELETED;
	}

	public static String deleteAllNotes(Optional<String> query) {

		Connection c = null;
		PreparedStatement stmt = null;

		ArrayList<Note> notesWithBody = getAllNotes(query);
		if (notesWithBody.size() < 1)
			return NOTE_NOT_FOUND;

		try {
			Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:levelsBeyond.db");

			stmt = c.prepareStatement(DELETE_ALL_NOTES_WITH_QUERY);

			// add wildcards here because they aren't addable in the setString()
			String queryString = "";
			if (query.isPresent()) // check before get so we don't throw exception
				queryString = "%" + query.get() + "%";
			else
				queryString = "%";

			stmt.setString(1, queryString);
			stmt.execute();

			stmt.close();
			c.close();

		} catch (Exception e) {
			e.printStackTrace();
		}

		if (query.isPresent())
			return NOTES_WITH_BODY_DELETED;
		return ALL_NOTES_DELETED;
	}

}
