package lk.ijse.dep11.ims.to;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TeacherTO implements Serializable {

    @Null(message = "ID should be empty")
    private Integer id;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z ]+$", message = "Invalid Name")
    private String name;
    @NotBlank
    @Pattern(regexp = "\\d{3}-\\d{7}", message = "Invalid Contact Number")
    private String contact;
}
