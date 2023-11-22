package lk.ijse.dep11.ims.api;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lk.ijse.dep11.ims.to.CourseTO;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PreDestroy;
import javax.validation.Valid;
import java.sql.*;
import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/courses")
@CrossOrigin
public class CourseHttpController {

    private final HikariDataSource pool;

    public CourseHttpController() {
        HikariConfig config = new HikariConfig();
        config.setUsername("root");
        config.setPassword("mysql");
        config.setJdbcUrl("jdbc:mysql://localhost:3306/dep11_ims");
        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("maximumPoolSize", 10);
        pool = new HikariDataSource(config);
    }
    @PreDestroy
    public void destroy(){

        pool.close();
    }


    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public CourseTO createCourse(@RequestBody @Validated CourseTO course){
        try (Connection connection = pool.getConnection()){
            PreparedStatement stm = connection
                    .prepareStatement("INSERT INTO course (name, duration_in_months) VALUES (?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            stm.setString(1, course.getCourseName());
            stm.setInt(2, course.getDurationInMonths());
            stm.executeUpdate();
            ResultSet generatedKeys = stm.getGeneratedKeys();
            generatedKeys.next();
            int id = generatedKeys.getInt(1);
            course.setCourseId(id);
            return course;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{course_id}", consumes = "application/json")
    public void updateCourse(@PathVariable int course_id, @RequestBody @Validated CourseTO course){
        try (Connection connection = pool.getConnection()) {
            PreparedStatement stmExist = connection
                    .prepareStatement("SELECT * FROM course WHERE id = ?");
            stmExist.setInt(1, course_id);
            if (!stmExist.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found");
            }

            PreparedStatement stm = connection
                    .prepareStatement("UPDATE course SET name = ?, duration_in_months=? WHERE id=?");
            stm.setString(1, course.getCourseName());
            stm.setInt(2, course.getDurationInMonths());
            stm.setInt(3, course_id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{course_id}")
    public void deleteCourse(@PathVariable int course_id){
        try(Connection connection = pool.getConnection()){
            PreparedStatement stmExist = connection
                    .prepareStatement("SELECT * FROM course WHERE id = ?");
            stmExist.setInt(1, course_id);
            if (!stmExist.executeQuery().next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found");
            }

            PreparedStatement stm = connection.prepareStatement("DELETE FROM course WHERE id=?");
            stm.setInt(1, course_id);
            stm.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{course_id}")
    public CourseTO getCourse(@PathVariable int course_id){
        try(Connection connection = pool.getConnection()){
            PreparedStatement stmExist = connection
                    .prepareStatement("SELECT * FROM course WHERE id = ?");
            stmExist.setInt(1, course_id);
            ResultSet rst = stmExist.executeQuery();
            if (!rst.next()){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Task Not Found");
            }

            int id = rst.getInt("id");
            String name = rst.getString("name");
            int durationInMonths = rst.getInt("duration_in_months");
            return new CourseTO(id, name, durationInMonths);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public List<CourseTO> getAllCourses(){
        try(Connection connection = pool.getConnection()){
            Statement stm = connection.createStatement();
            ResultSet rst = stm.executeQuery("SELECT * FROM course ORDER BY id");
            List<CourseTO> courseList = new LinkedList<>();
            while (rst.next()){
                int id = rst.getInt("id");
                String name = rst.getString("name");
                int durationInMonths = rst.getInt("duration_in_months");
                courseList.add(new CourseTO(id, name, durationInMonths));
            }
            return courseList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
