package milestone1.domain;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Quadre {
	
	private @Id @GeneratedValue int id;
	@Column(nullable=false, length=50)
	private String nom;
	@Column(nullable=true, length=50)
	private String autor;
	@Column(precision=8, scale=2)
	private Float preu;
	@Column(nullable=false)
	private Timestamp data;
	
	@ManyToOne
	private Botiga botiga;
	
	protected Quadre() {}
	
	public Quadre(String nom, String autor, Float preu, Timestamp data, Botiga botiga) {
		this.nom = nom;
		this.autor = autor;
		this.preu = preu;
		this.data = data;
		this.botiga = botiga;
	}
	
	public int getId() {
		return id;
	}
	
	public String getNom() {
		return nom;
	}
	
	public String getAutor() {
		return autor;
	}
	
	public Float getPreu() {
		return preu;
	}
	
	public Timestamp getData() {
		return data;
	}
	
	public Botiga getBotiga() {
		return botiga;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public void setNom(String nom) {
		this.nom = nom;
	}
	
	public void setAutor(String autor) {
		this.autor = autor;
	}
	
	public void setPreu(Float preu) {
		this.preu = preu;
	}
	
	public void setData(Timestamp data) {
		this.data = data;
	}
	
	public void setBotiga(Botiga botiga) {
		this.botiga = botiga;
	}
	
	@Override
	public String toString() {
		return "Quadre{ id" + id + ", nom=\'" + nom + "\', autor=\'" + autor + "\', "
				+ "preu=" + String.format("%.2f", preu) + ", "
				+ "data=\'" + data.toString() + "\', id=" + botiga.getId() + " }";
	}
	
}
