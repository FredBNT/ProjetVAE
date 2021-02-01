package fr.eni.vae.dal;

import java.util.List;

import fr.eni.vae.bo.Categorie;

public interface CategorieDAO {

	public Categorie libelleCategorie(int noCate) throws DALException;

	public Categorie numCategorie(String libel) throws DALException;

	public List<Categorie> recupCategorie() throws DALException;

}