package lk.ijse.dep11.ims.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourseTO implements Serializable {

    @Null(message = "Course Id should be empty")
    private Integer courseId;
    @NotBlank(message = "Course name should not be empty")
    private String courseName;
    @NotNull(message = "Duration cant be empty")
    private Integer durationInMonths;
}
