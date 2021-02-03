package fr.eni.vae.bo;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 */
// Constructeur par d�faut
public class Vente implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numVente;
	private String nomArticle;
	private String description;
	private LocalDate dateFinEnchere;
	private int prixInitial;
	private int prixvente;
	private int numUtil;
	private int numCate;
	private String photo;

	public Vente(String nomArticle, String description, LocalDate dateFinEnchere, int prixInitial, int prixvente,
			int numUtil, int numCate, String photo, boolean publier) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateFinEnchere = dateFinEnchere;
		this.prixInitial = prixInitial;
		this.prixvente = prixvente;
		this.numUtil = numUtil;
		this.numCate = numCate;
		this.photo = photo;
		this.publier = publier;
	}

	public Vente() {
		super();
	}

	public Vente(String nomArticle, String description, LocalDate dateFinEnchere, int prixInitial, int numUtil,
			int numCate, String photo, boolean publier) {
		super();
		this.nomArticle = nomArticle;
		this.description = description;
		this.dateFinEnchere = dateFinEnchere;
		this.prixInitial = prixInitial;
		this.numUtil = numUtil;
		this.numCate = numCate;
		this.photo = photo;
		this.publier = publier;
	}

	public int getNumVente() {
		return numVente;
	}

	public void setNumVente(int numVente) {
		this.numVente = numVente;
	}

	public String getNomArticle() {
		return nomArticle;
	}

	public void setNomArticle(String nomArticle) {
		this.nomArticle = nomArticle;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getDateFinEnchere() {
		return dateFinEnchere;
	}

	public void setDateFinEnchere(LocalDate dateFinEnchere) {
		this.dateFinEnchere = dateFinEnchere;
	}

	public int getPrixInitial() {
		return prixInitial;
	}

	public void setPrixInitial(int prixInitial) {
		this.prixInitial = prixInitial;
	}

	public int getPrixvente() {
		return prixvente;
	}

	public void setPrixvente(int prixvente) {
		this.prixvente = prixvente;
	}

	public int getNumUtil() {
		return numUtil;
	}

	public void setNumUtil(int numUtil) {
		this.numUtil = numUtil;
	}

	public int getNumCate() {
		return numCate;
	}

	public void setNumCate(int numCate) {
		this.numCate = numCate;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public boolean isPublier() {
		return publier;
	}

	public void setPublier(boolean publier) {
		this.publier = publier;
	}

	private boolean publier;

	@Override
	public String toString() {
		return "Vente [numVente=" + numVente + ", nomArticle=" + nomArticle + ", description=" + description
				+ ", dateFinEnchere=" + dateFinEnchere + ", prixInitial=" + prixInitial + ", prixvente=" + prixvente
				+ ", numUtil=" + numUtil + ", numCate=" + numCate + ", photo=" + photo + ", publier=" + publier + "]";
	}

}