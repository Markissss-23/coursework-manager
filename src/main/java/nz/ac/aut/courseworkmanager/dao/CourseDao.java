package nz.ac.aut.courseworkmanager.dao;

import jakarta.annotation.Resource;
import jakarta.enterprise.context.ApplicationScoped;
import nz.ac.aut.courseworkmanager.model.Course;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CourseDao {

    @Resource(lookup = "jdbc/courseworkmanager")
    private DataSource dataSource;

    public Course create(Course course) throws SQLException {
        String sql = "INSERT INTO course (code, name, semester) VALUES (?, ?, ?)";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            statement.setString(1, course.getCode());
            statement.setString(2, course.getName());
            statement.setString(3, course.getSemester());
            statement.executeUpdate();

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    course.setId(generatedKeys.getInt(1));
                }
            }
        }
        return course;
    }

    public List<Course> findAll() throws SQLException {
        String sql = "SELECT id, code, name, semester FROM course";
        List<Course> courses = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                courses.add(mapRow(resultSet));
            }
        }
        return courses;
    }

    public Course findById(int id) throws SQLException {
        String sql = "SELECT id, code, name, semester FROM course WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return mapRow(resultSet);
                }
            }
        }
        return null;
    }

    public boolean update(Course course) throws SQLException {
        String sql = "UPDATE course SET code = ?, name = ?, semester = ? WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setString(1, course.getCode());
            statement.setString(2, course.getName());
            statement.setString(3, course.getSemester());
            statement.setInt(4, course.getId());

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    public boolean delete(int id) throws SQLException {
        String sql = "DELETE FROM course WHERE id = ?";

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)) {

            statement.setInt(1, id);
            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        }
    }

    private Course mapRow(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setId(resultSet.getInt("id"));
        course.setCode(resultSet.getString("code"));
        course.setName(resultSet.getString("name"));
        course.setSemester(resultSet.getString("semester"));
        return course;
    }
}
