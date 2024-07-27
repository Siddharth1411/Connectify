package com.connectify.forms;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString

public class Userform {
    @NotBlank(message = "name is required")
    @Size(min = 3, message = "Min 3 characters required")
    private String name;
    @NotBlank(message = "email is required")
    private String email;
    @NotBlank(message = "password is required")
    @Size(min = 6, message = "Min 6 characters required")
    private String password;
    @NotBlank(message = "contact no. is required")
    @Size(min = 8, max = 12, message = "Min 8 and Max 12 digits required")
    private String phoneNumber;
    @NotBlank(message = "About is required")
    private String about;
}
