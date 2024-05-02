package dev.oleksa.user.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(description = "Password updating request")
public class PasswordUpdateRequest {
    private String email;
    private String oldPassword;
    private String newPassword;
}
