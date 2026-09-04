const API_BASE = "/coursework-manager/api";

async function loadPage() {
    const [coursesRes, assessmentsRes] = await Promise.all([
        fetch(`${API_BASE}/courses`),
        fetch(`${API_BASE}/assessments`)
    ]);
    const courses = await coursesRes.json();
    const assessments = await assessmentsRes.json();

    renderSemesterLabel(courses);
    renderStats(courses, assessments);
    renderCourseCards(courses, assessments);
}

function renderSemesterLabel(courses) {
    document.getElementById("semesterLabel").textContent =
        courses.length > 0 ? courses[0].semester : "";
}

function renderStats(courses, assessments) {
    const now = new Date();
    const weekFromNow = new Date();
    weekFromNow.setDate(now.getDate() + 7);

    const dueThisWeek = assessments.filter(a => {
        const due = new Date(a.dueDate);
        return a.status !== "COMPLETED" && due >= now && due <= weekFromNow;
    }).length;

    const completed = assessments.filter(a => a.status === "COMPLETED").length;

    document.getElementById("statsRow").innerHTML = `
        <div class="stat"><div class="n">${courses.length}</div><div class="l">Courses</div></div>
        <div class="stat"><div class="n">${assessments.length}</div><div class="l">Assessments</div></div>
        <div class="stat"><div class="n accent">${dueThisWeek}</div><div class="l">Due this week</div></div>
        <div class="stat"><div class="n">${completed}</div><div class="l">Completed</div></div>
    `;
}

function renderCourseCards(courses, assessments) {
    const container = document.getElementById("courseCards");
    container.innerHTML = "";

    courses.forEach(course => {
        const count = assessments.filter(a => a.courseId === course.id).length;

        const card = document.createElement("div");
        card.className = "course";
        card.innerHTML = `
            <button class="remove" onclick="deleteCourse(${course.id})">Remove</button>
            <div class="code">${course.code}</div>
            <div class="name">${course.name}</div>
            <div class="meta">${count} assessment${count === 1 ? "" : "s"}</div>
        `;
        container.appendChild(card);
    });
}

async function deleteCourse(id) {
    if (!confirm("Delete this course and all its assessments?")) {
        return;
    }

    await fetch(`${API_BASE}/courses/${id}`, { method: "DELETE" });
    loadPage();
}

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
        loadPage();
    } else {
        const error = await response.json();
        alert(error.error);
    }
});

loadPage();