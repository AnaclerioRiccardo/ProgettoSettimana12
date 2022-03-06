package it.catalogo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
@Data
public class Libro {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true, nullable = false)
	private String titolo;
	@Column(nullable = false)
	private Integer annoPubblicazione;
	@Column(nullable = false)
	private Double prezzo;
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "libro_autore",
	joinColumns = @JoinColumn(name = "libro_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "autore_id", referencedColumnName = "id"))
	private List<Autore> autori = new ArrayList<>();
	
	@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
	@ManyToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE})
	@JoinTable(name = "libro_categoria",
	joinColumns = @JoinColumn(name = "libro_id", referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name = "categoria_id", referencedColumnName = "id"))
	private List<Categoria> categorie = new ArrayList<>();
	
	public void aggiungiCategoria(Categoria c) {
		categorie.add(c);
	}
	
	public void aggiungiAutore(Autore a) {
		autori.add(a);
	}

}
