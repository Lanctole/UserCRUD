package lanctole.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class CreateOrUpdateUserDto {
    @NotBlank
    @Size(min = 3, max = 50)
    private String name;

    @NotBlank
    @Size(min = 3, max = 50)
    @Email
    private String email;

    @PositiveOrZero
    private Integer age;
}
