package com.example.PlayFlix.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    String username;
    String nombre;
    String email;
    String password;
    //String createAt;
    Boolean enable;
}
