# LevelsBeyondAssessment


To Run:
I packaged the code into a single executable jar. When you download the repo
the command to run it should be:

java -jar target/levels-beyond-assessment-0.0.1-SNAPSHOT.jar


If you want to run the frontend and backend in development mode
1) compile and run the main method located in LevelsBeyondAssessmentApplication.java
2) navigate to the levels-beyond-frontend and run 'npm start'

Note, if you decide to run them separately, you'll need to direct API calls
to localhost:3000

Design Decisions:
I used Spring boot to create a simple application and put it onto a tomcat server. The
annotations in the Spring Web Framework were my primary reason for using it.

I used a SQLite DB to store records locally.

I used ReactJS, and while I am still relatively new to the technology and have much
to learn, I was able to effectively query the API and return the desired information.



Backend:

Address to hit: localhost:8080

Create:
POST /api/notes - when given a JSON body, creates the note and saves to db

Read:
GET /api/notes - reads all notes
GET /api/notes?query=X - returns all notes whose body contains X
GET /api/notes/{id} - reads the note with the id of id

Update:
PUT /api/notes/{id} - when given a JSON body, updates the note and saves to db

Delete:
DELETE /api/notes - deletes all notes
DELETE /api/notes?query=X - deletes all notes whose body contains X
DELETE /api/notes/{id} - deletes the note with the id of id



Frontend:

I decided to go with a very basic UI. All data is displayed as requested. UI/UX
is something that I'd like to practice more, as I'm primarily a backend dev.
