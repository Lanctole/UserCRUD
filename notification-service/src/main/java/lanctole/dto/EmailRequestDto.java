package lanctole.dto;

import lombok.Data;

@Data
public class EmailRequestDto {
    private String email;
    private String subject;
    private String message;
}
