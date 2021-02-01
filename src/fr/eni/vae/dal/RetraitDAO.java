package fr.eni.vae.dal;

import java.sql.SQLException;

import fr.eni.vae.bo.Retrait;

public interface RetraitDAO {

	public void ajouterRetrait(Retrait retrait) throws DALException;

	public Retrait AfficherRetrait(int numVente) throws DALException, SQLException;

	public void supprimerUnRetrait(int numVente) throws DALException;

}
