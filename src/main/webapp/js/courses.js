const API_BASE = "/coursework-manager/api";

async function loadCourses() {
    const response = await fetch(`${API_BASE}/courses`);
    const courses = await response.json();

    const tbody = document.getElementById("courseTableBody");
    tbody.innerHTML = "";

    courses.forEach(course => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${course.code}</td>
            <td>${course.name}</td>
            <td>${course.semester}</td>
            <td><button onclick="deleteCourse(${course.id})">Delete</button></td>
        `;
        tbody.appendChild(row);
    });
}

async function deleteCourse(id) {
    if (!confirm("Delete this course and all its assessments?")) {
        return;
    }

    await fetch(`${API_BASE}/courses/${id}`, { method: "DELETE" });
    loadCourses();
}

loadCourses();

document.getElementById("courseForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const course = {
        code: document.getElementById("code").value,
        name: document.getElementById("name").value,
        semester: document.getElementById("semester").value
    };

    const response = await fetch(`${API_BASE}/courses`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(course)
    });

    if (response.ok) {
        event.target.reset();
        loadCourses();
    } else {
        const error = await response.json();
        alert(error.error);
    }
});