CREATE TABLE course (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    code TEXT NOT NULL UNIQUE,
    name TEXT NOT NULL,
    semester TEXT NOT NULL
);

CREATE TABLE assessment (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    course_id INTEGER NOT NULL,
    title TEXT NOT NULL,
    due_date TEXT NOT NULL,
    weight REAL NOT NULL CHECK (weight >= 0 AND weight <= 100),
    status TEXT NOT NULL DEFAULT 'NOT_STARTED',
    priority TEXT,
    notes TEXT,
    FOREIGN KEY (course_id) REFERENCES course(id) ON DELETE CASCADE
)