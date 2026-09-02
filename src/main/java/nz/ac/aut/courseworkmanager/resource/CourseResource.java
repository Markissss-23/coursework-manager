package nz.ac.aut.courseworkmanager.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import nz.ac.aut.courseworkmanager.dao.AssessmentDao;
import nz.ac.aut.courseworkmanager.dao.CourseDao;
import nz.ac.aut.courseworkmanager.exception.NotFoundException;
import nz.ac.aut.courseworkmanager.exception.ValidationException;
import nz.ac.aut.courseworkmanager.model.Assessment;
import nz.ac.aut.courseworkmanager.model.Course;

import java.sql.SQLException;
import java.util.List;

@Path("/courses")
public class CourseResource {

    @Inject
    private CourseDao courseDao;

    @Inject
    private AssessmentDao assessmentDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> findAll() throws SQLException {
        return courseDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Course findById(@PathParam("id") int id) throws SQLException {
        Course course = courseDao.findById(id);
        if (course == null) {
            throw new NotFoundException("No course found with id " + id);
        }
        return course;
    }

    @GET
    @Path("/{id}/assessments")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Assessment> findAssessments(@PathParam("id") int id) throws SQLException {
        if (courseDao.findById(id) == null) {
            throw new NotFoundException("No course found with id " + id);
        }
        return assessmentDao.findByCourseId(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Course create(Course course) throws SQLException {
        validate(course);
        return courseDao.create(course);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Course update(@PathParam("id") int id, Course course) throws SQLException {
        if (courseDao.findById(id) == null) {
            throw new NotFoundException("No course found with id " + id);
        }
        validate(course);
        course.setId(id);
        courseDao.update(course);
        return course;
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) throws SQLException {
        if (courseDao.findById(id) == null) {
            throw new NotFoundException("No course found with id " + id);
        }
        courseDao.delete(id);
    }

    private void validate(Course course) {
        if (course.getCode() == null || course.getCode().isBlank()) {
            throw new ValidationException("Course code is required");
        }
        if (course.getName() == null || course.getName().isBlank()) {
            throw new ValidationException("Course name is required");
        }
        if (course.getSemester() == null || course.getSemester().isBlank()) {
            throw new ValidationException("Semester is required");
        }
    }
}