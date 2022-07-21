package jana60.Model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

@Entity
public class Pizza {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@NotEmpty(message = "Il nome è obbligatorio")
	private String nome;

	@NotEmpty(message = "La descrizione è obbligatoria")
	private String descrizione;

	@Column(nullable = false)
	@Min(3)
	private BigDecimal prezzo;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescrizione() {
		return descrizione;
	}

	public void setDescrizione(String descrizione) {
		this.descrizione = descrizione;
	}

	public @Min(3) BigDecimal getPrezzo() {
		return prezzo;
	}

	public void setPrezzo(@Min(3) BigDecimal prezzo) {
		this.prezzo = prezzo;
	}

}
