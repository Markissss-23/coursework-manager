Session 1 - Aug 3
Set up the project today. Made the pom.xml file, created the folder structure I'd planned out earlier, and got the project building properly.

Also got Git and GitHub set up - first commit completed. No features yet, just foundation. Payara and the database connection are next.

Session 2 - Aug 5
Got Payara connected to the db today. Installed the SQLite driver, then set up a connection pool.

Hit an error along the way, turned out the driver needed another small library to work properly, so had to add that too. Took a bit of digging through the logs to figure out what was actually wrong, but got there in the end.

Connection is working now. Next up is actually building out features.

Session 3 – Aug 17
Trung sent through a provided database server option, so I tried switching to it. Got the driver and pool set up correctly, but couldn't get a connection through. It kept failing with a "communications link failure" even after checking the port was reachable, trying different network setups, and confirming all the config settings were correct. 

Decided not to keep persuing it and went back to SQLite, which was already working. Cleaned up the leftover MySQL config so the project's back to how it was before. 

Session 4 - Aug 21
I wrote the course and assessment classes today, including a status enum instead of plain text for the status field as I thought it would be better to limit it to fixed values (NOT_STARTED, IN_PROGRESS, COMPLETED). 

I then wrote the scheme.sql for the two tables, i decided that when a course gets deleted all the assessments get removed too rather than being left behind or stopping the delete (cascade delete).

I wasn't able to download the normal SQLite browser to download so I downloaded the portable version instead. I then opened up my coursework.db file and ran the schema, both tables show up now, i wrote the changes and committed the schema.sql. 

Session 5 - Aug 25 
I started the DAO stuff today. Starting with writing the coursedao with the base set of methods. All using a shared datasource. 

I remembered to use "try" for closing connections and used preparedstatement everywhere instead of using SQL strings directly to prevent SQL injection. 

Session 6 - Aug 26
I continued with the DAO layer by creating the AssessmentDao, being a similar but slightly different version of the courseDao. but with two extra conversions, specifically with LocalDate and the status enum. 

I havent gotten to update and delete crud ops yet and havent tested any of the functionality, i'll need to build a test class next time

Session 7 - Aug 30
Started testing the DAO layer today. I found an extension called "Thunder Client" and it really helped. I had to make a few other classes first though. I immedietely found a bug where in CourseDao i had "courses" instead of "course" in the sql. 

Afterwhich i tested the whole flow, but the deletion of a course didnt work as it should have. 

Session 8 - Sep 1
Fixed the cascade delete bug from last session. Added a ConnectionProvider utility that turns on foreign key support every time a connection is grabbed, then updated both DAOs to use it instead of calling the DataSource directly. I then re-tested the full functionality: create course, create assessment, delete course, check assessment. This time it came back empty, confirming the cascade actually works now.

Also built out the exception handling layer today. Added ValidationException and NotFoundException, plus an ErrorResponse class so every error comes back in the same format. Added a mapper for each one using @Provider, which means JAX-RS automatically catches these exceptions and converts them to the right HTTP status without needing try/catch in every resource method.

Nothing throws these yet, that's next session when I build the real CourseResource and AssessmentResource.

Session 9 - Sep 2 
I built CourseResource today, replacing the old test resource with the real one. Added validation for required fields and proper handling for missing courses, using the exception classes and mappers from last session. I tested a valid create, an invalid create, get by valid and invalid id, list all, and the nested assessments endpoint. All working as expected. Also built AssessmentResource today. 


Session 10 - Sep 4
I built the courses page today. Hit a small bug straight away, index.html had ended up inside WEB-INF which isn't accessible, so it 404. Moved it and it loaded fine after. Added a form to create courses, and delete buttons with a confirm prompt since deleting a course cascades to its assessments too. Validation errors show up as an alert.

Then built the assessments page the same way, but with a course dropdown for creating assessments and a lookup so the table shows course codes instead of raw ids. Status updates happen through a dropdown on each row, mapping to the status endpoint.

Session 11 - Sep 5
I styled both of the pages today. I thought of a few different themes but I know I wanted a dashboard. I settled on something that resembles Discord's UI: dark blue backgrounds, with a sidebar for different pages (like channels)

The courses page now has stat cards displaying the current data. likewise, the Assesments page has a dropdown list. 