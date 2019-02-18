package com.granthc.levelsbeyondassessment;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotesController {

	// Create a new Note
	@RequestMapping(method = RequestMethod.POST, value = "/api/notes")
	public ResponseEntity<Note> postNote(@RequestBody Note note) {
		return new ResponseEntity<Note>(DatabaseController.saveNote(note.getBody()), HttpStatus.CREATED);
	}

	// Get a Note with a specific ID
	@RequestMapping(method = RequestMethod.GET, value = "/api/notes/{noteId}")
	public ResponseEntity<Note> getNote(@PathVariable("noteId") int noteId) {
		Note foundNote = DatabaseController.getNote(noteId);
		if (foundNote == null)
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Note>(foundNote, HttpStatus.FOUND);
	}

	// Get all notes + get all notes that contain a query in their body
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/api/notes")
	public ResponseEntity<ArrayList<Note>> getAllNotes(@RequestParam("query") Optional<String> query) {
		return new ResponseEntity<ArrayList<Note>>(DatabaseController.getAllNotes(query), HttpStatus.FOUND);
	}

	// Update a Note with ID
	@RequestMapping(method = RequestMethod.PUT, value = "/api/notes/{noteId}")
	public ResponseEntity<Note> updateNote(@PathVariable("noteId") int noteId, @RequestBody Note note) {
		return new ResponseEntity<Note>(DatabaseController.updateNote(noteId, note.getBody()), HttpStatus.OK);
	}

	// Delete a Note with ID
	@RequestMapping(method = RequestMethod.DELETE, value = "/api/notes/{noteId}")
	public ResponseEntity<Note> deleteNote(@PathVariable("noteId") int noteId) {
		String result = DatabaseController.deleteNote(noteId);

		if (result.equals(DatabaseController.NOTE_NOT_FOUND))
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Note>(HttpStatus.NO_CONTENT);
	}

	// Delete all Notes + delete all notes that contain a certain body
	@RequestMapping(method = RequestMethod.DELETE, value = "/api/notes")
	public ResponseEntity<Note> deleteAllNotes(@RequestParam("query") Optional<String> query) {
		String result = DatabaseController.deleteAllNotes(query);
		if (result.equals(DatabaseController.NOTE_NOT_FOUND))
			return new ResponseEntity<Note>(HttpStatus.NOT_FOUND);
		return new ResponseEntity<Note>(HttpStatus.NO_CONTENT);
	}

}
