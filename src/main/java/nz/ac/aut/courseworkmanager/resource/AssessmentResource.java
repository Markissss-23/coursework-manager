package nz.ac.aut.courseworkmanager.resource;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import nz.ac.aut.courseworkmanager.dao.AssessmentDao;
import nz.ac.aut.courseworkmanager.dao.CourseDao;
import nz.ac.aut.courseworkmanager.exception.NotFoundException;
import nz.ac.aut.courseworkmanager.exception.ValidationException;
import nz.ac.aut.courseworkmanager.model.Assessment;
import nz.ac.aut.courseworkmanager.model.AssessmentStatus;

import java.sql.SQLException;
import java.util.List;

@Path("/assessments")
public class AssessmentResource {

    @Inject
    private AssessmentDao assessmentDao;

    @Inject
    private CourseDao courseDao;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Assessment> findAll() throws SQLException {
        return assessmentDao.findAll();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Assessment findById(@PathParam("id") int id) throws SQLException {
        Assessment assessment = assessmentDao.findById(id);
        if (assessment == null) {
            throw new NotFoundException("No assessment found with id " + id);
        }
        return assessment;
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Assessment create(Assessment assessment) throws SQLException {
        validate(assessment);
        return assessmentDao.create(assessment);
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Assessment update(@PathParam("id") int id, Assessment assessment) throws SQLException {
        if (assessmentDao.findById(id) == null) {
            throw new NotFoundException("No assessment found with id " + id);
        }
        validate(assessment);
        assessment.setId(id);
        assessmentDao.update(assessment);
        return assessment;
    }

    @PATCH
    @Path("/{id}/status")
    @Consumes(MediaType.APPLICATION_JSON)
    public void updateStatus(@PathParam("id") int id, AssessmentStatus status) throws SQLException {
        if (assessmentDao.findById(id) == null) {
            throw new NotFoundException("No assessment found with id " + id);
        }
        assessmentDao.updateStatus(id, status);
    }

    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id") int id) throws SQLException {
        if (assessmentDao.findById(id) == null) {
            throw new NotFoundException("No assessment found with id " + id);
        }
        assessmentDao.delete(id);
    }

    private void validate(Assessment assessment) throws SQLException {
        if (assessment.getTitle() == null || assessment.getTitle().isBlank()) {
            throw new ValidationException("Title is required");
        }
        if (assessment.getDueDate() == null) {
            throw new ValidationException("Due date is required");
        }
        if (assessment.getWeight() < 0 || assessment.getWeight() > 100) {
            throw new ValidationException("Weight must be between 0 and 100");
        }
        if (assessment.getStatus() == null) {
            throw new ValidationException("Status is required");
        }
        if (courseDao.findById(assessment.getCourseId()) == null) {
            throw new ValidationException("No course found with id " + assessment.getCourseId());
        }
    }
}