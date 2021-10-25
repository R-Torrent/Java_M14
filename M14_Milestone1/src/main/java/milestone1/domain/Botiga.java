package milestone1.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Botiga {
	
	private @Id @GeneratedValue int id;
	@Column(nullable=false, length=50)
	private String nom;
	private short capacitat;	// límit de quadres a la botiga
	
	@OneToMany(mappedBy = "botiga") @JsonIgnore	
	private List<Quadre> quadres;
	
	protected Botiga() {}
	
	public Botiga(String nom, short capacitat) {
		this.nom = nom;
		this.capacitat = capacitat;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}
		
	public short getCapacitat() {
		return capacitat;
	}
	
	public List<Quadre> getQuadres() {
		return quadres;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
		
	public void setCapacitat(short capacitat) {
		this.capacitat = capacitat;
	}
	
	public void setQuadres(List<Quadre> quadres) {
		this.quadres = quadres;
	}
	
	@Override
	public String toString() {
		return "Botiga{ id=" + id + ", nom=\'" + nom + "\', capacitat=" + capacitat + " }";
	}
	
}
