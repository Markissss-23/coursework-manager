package nz.ac.aut.courseworkmanager.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import nz.ac.aut.courseworkmanager.dao.CourseDao;
import nz.ac.aut.courseworkmanager.model.Course;

import java.sql.SQLException;
import java.util.List;

@Path("/test/courses")
public class CourseTestResource {

    @Inject
    private CourseDao courseDao;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Course create() throws SQLException {
        Course course = new Course("COMP713", "Distributed Systems", "S2 2026");
        return courseDao.create(course);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> findAll() throws SQLException {
        return courseDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Course findById(@PathParam("id") int id) throws SQLException {
        return courseDao.findById(id);
    }

    @PUT
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Course update(@PathParam("id") int id) throws SQLException {
        Course course = courseDao.findById(id);
        course.setSemester("S1 2027");
        courseDao.update(course);
        return course;
    }

    @DELETE
    @Path("/{id}")
    public boolean delete(@PathParam("id") int id) throws SQLException {
        return courseDao.delete(id);
    }
}