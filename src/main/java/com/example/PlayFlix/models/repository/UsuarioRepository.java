package com.example.PlayFlix.models.repository;

import com.example.PlayFlix.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    List<Usuario> findByUsernameOrEmail(String username, String email);

    @Query("FROM Usuario u WHERE (u.username = ?1 OR u.email = ?2) AND u.id != ?3")
    List<Usuario> findRepeated(String username, String email, Integer id);

    Optional<Usuario> findByUsername(String username);
}