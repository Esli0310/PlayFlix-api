package com.example.PlayFlix.models.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "usuarios", schema = "public", catalog = "PlayFlixDB")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "nombre", nullable = false, length = 80)
    private String nombre;

    @Column(name = "email", nullable = false, length = 80)
    private String email;

    @Column(name = "contrasena", nullable = false, length = 80)
    private String contrasena;
}
