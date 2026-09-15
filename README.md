# Coursework Manager

A small distributed web application for managing university courses and assessments. Built for COMP713 Assignment 2, Option A. The backend is a Jakarta EE REST API, data is stored in SQLite, and the client is plain HTML and JavaScript.

## Requirements

- JDK 21
- Maven 3.9 or later
- Payara Server 7
- DB Browser for SQLite (or an equivalent client, needed only for initial setup)

## Setup

**1. Add the drivers to Payara**

Copy the following jars into `<payara install>/glassfish/lib/`:
- `sqlite-jdbc-3.46.1.0.jar`
- `slf4j-api-1.7.36.jar`
- `slf4j-nop-1.7.36.jar`

These are available under `~/.m2/repository/` once the project has been built (`mvn clean package` downloads them automatically).

**2. Configure the connection pool**

Start Payara with `asadmin start-domain`, then open the admin console at `localhost:4848`.

Under **Resources > JDBC > JDBC Connection Pools > New**, create a pool named `CourseworkManagerPool` using `org.sqlite.SQLiteDataSource` as the datasource classname. Add a property `url` pointing to the intended database file location, for example `jdbc:sqlite:C:/path/to/coursework-manager/coursework.db`.

Under **Resources > JDBC > JDBC Resources > New**, create a resource with JNDI name `jdbc/courseworkmanager`, linked to the pool above.

Use the Ping button to confirm the connection succeeds before proceeding.

**3. Create the database tables**

The database file is created automatically once the pool connects, but the tables are not. Open the file in DB Browser, go to the Execute SQL tab, and run the contents of `src/main/resources/schema.sql`. Write the changes to save them.

**4. Build and deploy**

\`\`\`
mvn clean package
asadmin deploy --force target/coursework-manager.war
\`\`\`

## Running the system

Open `http://localhost:8080/coursework-manager/` in a browser. This loads the courses page; the assessments page is reachable from the sidebar.

## Testing the main functions

Through the interface: create a course, create an assessment under it, change its status using the dropdown, then delete the course and confirm its assessments are removed as well. Invalid input, such as a blank required field or an assessment linked to a course that does not exist, should return a clear error rather than a server failure.

Through the API directly, the base path is `http://localhost:8080/coursework-manager/api`, with the following endpoints:

- `/courses` (GET, POST, GET/{id}, PUT/{id}, DELETE/{id})
- `/courses/{id}/assessments` (GET)
- `/assessments` (GET, POST, GET/{id}, PUT/{id}, DELETE/{id})
- `/assessments/{id}/status` (PATCH)

## Configuration

- Context path: `/coursework-manager`
- Application port: `8080`; admin console port: `4848`
- JNDI name: `jdbc/courseworkmanager`
- No environment variables are required; configuration is handled through the connection pool setup above

## Known limitations

- Creating a course with a duplicate code returns a raw 500 response instead of a proper 400. The duplicate is caught by the database's unique constraint, but this is not checked before the insert is attempted.
- This project uses SQLite rather than the MySQL server provided for the course. A connection pool and driver were configured for the provided server, and the port was confirmed reachable, but the connection consistently failed with a communications link error across multiple networks. Further detail is provided in the project report.