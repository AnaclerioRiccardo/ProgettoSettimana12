package it.catalogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import it.catalogo.model.Autore;
import it.catalogo.model.Libro;

public interface LibroRepository extends JpaRepository<Libro, Long>{

	Optional<Libro> findByTitolo(String titolo);

}
