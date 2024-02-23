package ru.omstu.monographreview.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private Long id;
    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;
    @NotBlank(message = "Адрес электронной почты не должен быть пустым")
    @Email
    private String email;
    @NotBlank(message = "Пароль не должен быть пустым")
    private String password;
}
