package nz.ac.aut.courseworkmanager.dao;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import nz.ac.aut.courseworkmanager.model.Assessment;
import nz.ac.aut.courseworkmanager.model.AssessmentStatus;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class AssessmentDao {

    @Resource(lookup = "jdbc/courseworkmanager")
    private DataSource dataSource;

    public Assessment create(Assessment assessment) throws SQLException {
        String sql = "INSERT INTO assessment (course_id, title, due_date, weight, status, priority, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setInt(1, assessment.getCourseId());
            statement.setString(2, assessment.getTitle());
            statement.setObject(3, assessment.getDueDate().toString());
            statement.setDouble(4, assessment.getWeight());
            statement.setString(5, assessment.getStatus().name());
            statement.setString(6, assessment.getPriority());
            statement.setString(7, assessment.getNotes());

            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    assessment.setId(generatedKeys.getInt(1));
                } 
            }
        }
        return assessment;
    }

    public List<Assessment> findAll() throws SQLException {
        String sql = "SELECT id, course_id, title, due_date, weight, status, priority, notes FROM assessment";
        List<Assessment> assessments = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                assessments.add(mapRow(rs));
            }
        }

        return assessments;
    }

    public Assessment findById(int id) throws SQLException {
        String sql = "SELECT id, course_id, title, due_date, weight, status, priority, notes " +
                "FROM assessment WHERE id = ?";

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRow(rs);
                }
            }
        }

        return null;
    }

    public List<Assessment> findByCourseId(int courseId) throws SQLException {
        String sql = "SELECT id, course_id, title, due_date, weight, status, priority, notes " +
                "FROM assessment WHERE course_id = ?";
        List<Assessment> assessments = new ArrayList<>();

        try (Connection conn = dataSource.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, courseId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    assessments.add(mapRow(rs));
                }
            }
        }

        return assessments;
    }

    private Assessment mapRow(ResultSet rs) throws SQLException {
        Assessment assessment = new Assessment();
        assessment.setId(rs.getInt("id"));
        assessment.setCourseId(rs.getInt("course_id"));
        assessment.setTitle(rs.getString("title"));
        assessment.setDueDate(LocalDate.parse(rs.getString("due_date")));
        assessment.setWeight(rs.getDouble("weight"));
        assessment.setStatus(AssessmentStatus.valueOf(rs.getString("status")));
        assessment.setPriority(rs.getString("priority"));
        assessment.setNotes(rs.getString("notes"));
        return assessment;
    }
}
