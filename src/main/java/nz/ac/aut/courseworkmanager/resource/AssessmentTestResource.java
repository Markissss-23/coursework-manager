package nz.ac.aut.courseworkmanager.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import nz.ac.aut.courseworkmanager.dao.AssessmentDao;
import nz.ac.aut.courseworkmanager.model.Assessment;
import nz.ac.aut.courseworkmanager.model.AssessmentStatus;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

@Path("/test/assessments")
public class AssessmentTestResource {

    @Inject
    private AssessmentDao assessmentDao;

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Assessment create(@QueryParam("courseId") int courseId) throws SQLException {
        Assessment assessment = new Assessment(courseId, "Test Assignment", LocalDate.now().plusDays(14),
                20.0, AssessmentStatus.NOT_STARTED, "High", "Created via test resource");
        return assessmentDao.create(assessment);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Assessment> findAll() throws SQLException {
        return assessmentDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Assessment findById(@PathParam("id") int id) throws SQLException {
        return assessmentDao.findById(id);
    }

    @GET
    @Path("/course/{courseId}")
    @Produces(MediaType.APPLICATION_JSON)
    public List<Assessment> findByCourseId(@PathParam("courseId") int courseId) throws SQLException {
        return assessmentDao.findByCourseId(courseId);
    }

    @PATCH
    @Path("/{id}/status")
    public boolean updateStatus(@PathParam("id") int id) throws SQLException {
        return assessmentDao.updateStatus(id, AssessmentStatus.COMPLETED);
    }

    @DELETE
    @Path("/{id}")
    public boolean delete(@PathParam("id") int id) throws SQLException {
        return assessmentDao.delete(id);
    }
}