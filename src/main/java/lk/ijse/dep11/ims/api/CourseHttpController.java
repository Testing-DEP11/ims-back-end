package lk.ijse.dep11.ims.api;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/courses")
@CrossOrigin
public class CourseHttpController {
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(produces = "application/json", consumes = "application/json")
    public void createCourse(){
        System.out.println("createCourse()");
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PatchMapping(value = "/{course_id}", consumes = "application/json")
    public void updateCourse(){
        System.out.println("updateCourse()");

    }
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{course_id}")
    public void deleteCourse(){
        System.out.println("deleteCourse()");

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{course_id}")
    public void getCourse(){
        System.out.println("getCourse()");

    }
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(produces = "application/json")
    public void getAllCourses(){
        System.out.println("getAllCourses()");

    }


}
