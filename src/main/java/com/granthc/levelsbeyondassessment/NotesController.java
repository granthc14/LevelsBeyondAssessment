package com.granthc.levelsbeyondassessment;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotesController {

	//Create a new Note
	@RequestMapping(method = RequestMethod.POST, value = "/api/notes")
	public Note postNote(@RequestBody Note note) {
		return DatabaseController.saveNote(note.getBody());
	}

	//Get a Note with a specific ID
	@RequestMapping(method = RequestMethod.GET, value = "/api/notes/{noteId}")
	public Note getNote(@PathVariable("noteId") int noteId) {
		return DatabaseController.getNote(noteId);
	}
	
	//Get all notes + get all notes that contain a query in their body
	@ResponseBody
	@RequestMapping(method = RequestMethod.GET, value = "/api/notes")
	public ArrayList<Note> getAllNotes(@RequestParam("query") Optional<String> query) {
		return DatabaseController.getAllNotes(query);
	}
	
	//Update a Note with ID
	@RequestMapping(method = RequestMethod.POST, value = "/api/notes/{noteId}")
	public Note updateNote(@PathVariable("noteId") int noteId, @RequestBody Note note) {
		return DatabaseController.updateNote(noteId, note.getBody()); 
	}
	
	//Update all Notes + update all notes that contain a certain body
	
	//Delete a Note with ID
		
	//Delete all Notes + delete all notes that contain a certain body
	

}
