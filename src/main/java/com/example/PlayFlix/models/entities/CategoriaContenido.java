package com.example.PlayFlix.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "categoria_contenidos", schema = "public", catalog = "PlayFlixDB")
public class CategoriaContenido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contenido_id", referencedColumnName = "id", nullable = false)
    //@JsonIgnore
    private Contenido contenido;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "clasificacion_id", referencedColumnName = "id", nullable = false)
    //@JsonIgnore
    private Clasificacion clasificacion;
}
