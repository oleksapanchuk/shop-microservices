package dev.oleksa.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Schema(description = "Data Transfer Object for User")
public class UserDto {

    @Schema(description = "The unique ID of the user")
    private Long id;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Schema(description = "The unique email of the user")
    private String email;

    @Schema(description = "The first name of the user")
    private String firstName;

    @Schema(description = "The last name of the user")
    private String lastName;

    @Schema(description = "The phone number of the user")
    private String phoneNumber;

    @Schema(description = "Indicates whether the user is subscribed to mail")
    private boolean subscribed;

    @Schema(description = "Indicates whether the user is verified")
    private boolean verified;

    @NotNull(message = "Role is mandatory")
    @Schema(description = "The role of the user")
    private String role;
}
