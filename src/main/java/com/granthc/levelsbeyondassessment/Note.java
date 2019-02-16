package com.granthc.levelsbeyondassessment;

public class Note {

	private int id;
	private final String body;

	public Note(int id, String body) {
		this.id = id;
		this.body = body;
	}

	public Note(String body) {
		this.body = body;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getBody() {
		return body;
	}
}