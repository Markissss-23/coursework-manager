const API_BASE = "/coursework-manager/api";

let courseLookup = {};

async function loadCourses() {
    const response = await fetch(`${API_BASE}/courses`);
    const courses = await response.json();

    courseLookup = {};
    const select = document.getElementById("courseId");
    select.innerHTML = '<option value="">Select a course</option>';

    courses.forEach(course => {
        courseLookup[course.id] = course.code;

        const option = document.createElement("option");
        option.value = course.id;
        option.textContent = `${course.code} - ${course.name}`;
        select.appendChild(option);
    });
}

async function loadAssessments() {
    const response = await fetch(`${API_BASE}/assessments`);
    const assessments = await response.json();

    const tbody = document.getElementById("assessmentTableBody");
    tbody.innerHTML = "";

    assessments.forEach(assessment => {
        const row = document.createElement("tr");
        row.innerHTML = `
            <td>${assessment.title}</td>
            <td>${courseLookup[assessment.courseId] ?? assessment.courseId}</td>
            <td>${assessment.dueDate}</td>
            <td>${assessment.weight}%</td>
            <td>
                <select onchange="updateStatus(${assessment.id}, this.value)">
                    <option value="NOT_STARTED" ${assessment.status === "NOT_STARTED" ? "selected" : ""}>Not Started</option>
                    <option value="IN_PROGRESS" ${assessment.status === "IN_PROGRESS" ? "selected" : ""}>In Progress</option>
                    <option value="COMPLETED" ${assessment.status === "COMPLETED" ? "selected" : ""}>Completed</option>
                </select>
            </td>
            <td>${assessment.priority ?? ""}</td>
            <td><button onclick="deleteAssessment(${assessment.id})">Delete</button></td>
        `;
        tbody.appendChild(row);
    });
}

async function updateStatus(id, status) {
    await fetch(`${API_BASE}/assessments/${id}/status`, {
        method: "PATCH",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(status)
    });
    loadAssessments();
}

async function deleteAssessment(id) {
    if (!confirm("Delete this assessment?")) {
        return;
    }

    await fetch(`${API_BASE}/assessments/${id}`, { method: "DELETE" });
    loadAssessments();
}

document.getElementById("assessmentForm").addEventListener("submit", async (event) => {
    event.preventDefault();

    const assessment = {
        courseId: parseInt(document.getElementById("courseId").value),
        title: document.getElementById("title").value,
        dueDate: document.getElementById("dueDate").value,
        weight: parseFloat(document.getElementById("weight").value),
        status: "NOT_STARTED",
        priority: document.getElementById("priority").value,
        notes: document.getElementById("notes").value
    };

    const response = await fetch(`${API_BASE}/assessments`, {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(assessment)
    });

    if (response.ok) {
        event.target.reset();
        loadAssessments();
    } else {
        const error = await response.json();
        alert(error.error);
    }
});

loadCourses().then(loadAssessments);