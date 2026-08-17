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

