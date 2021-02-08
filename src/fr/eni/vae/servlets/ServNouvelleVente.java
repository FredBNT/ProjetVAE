package fr.eni.vae.servlets;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import fr.eni.vae.bll.CategorieManager;
import fr.eni.vae.bll.RetraitManager;
import fr.eni.vae.bll.VenteManager;
import fr.eni.vae.bo.Categorie;
import fr.eni.vae.bo.Retrait;
import fr.eni.vae.bo.Vente;
import fr.eni.vae.dal.DALException;

@WebServlet(urlPatterns = { "/ServNouvelleVente" })
@MultipartConfig(location = "D:\\PHP SQL JAVA\\Java\\eclipse3\\ProjetVenteAuxEnchères\\WebContent\\pic", maxFileSize = 10485760L) // 10Mo.
public class ServNouvelleVente extends HttpServlet {

	private static final long serialVersionUID = 1L;
	// parametres de l'upload
	private static final int TAILLE_BUFFER = 10240;
	private static final String TYPE_CONTENU = "content-disposition";
	private static final String NOM_TYPE_CONTENU = "filename";
	private static final boolean MODE_MULTIPART = true;

	// affichage de la page de chargement
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String rue;
		String cp;
		String ville;
		rue = (String) session.getAttribute("Rue");
		cp = (String) session.getAttribute("Cp");
		ville = (String) session.getAttribute("Ville");
		request.setAttribute("rue", rue);
		request.setAttribute("cp", cp);
		request.setAttribute("ville", ville);

		List<Categorie> listeCategorie = new ArrayList<>();
		try {
			listeCategorie = CategorieManager.recupCategorie();
		} catch (DALException e) {
			e.printStackTrace();
		}

		request.setAttribute("categorie", listeCategorie);

		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/nouvelleVente.jsp").forward(request, response);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String nomFichierUpload = null;
		// parcourir chaque paramètre reçu
		for (Part p : request.getParts()) {
			String typeContenu = p.getContentType();
			if (typeContenu != null) {
				// upload
				nomFichierUpload = this.uploadFichier(p);
			}
		}
		// retour sur la page d'upload

		Vente nVente = null;
		String selectionner = "Selectionner";

		// Récupération des données de la nouvelle vente
		String vArticle = request.getParameter("sArticle");
		String vDescription = request.getParameter("sDescription");
		// ajout vro adresse photo
		String vPhoto = nomFichierUpload;
		String vPrix = request.getParameter("sPrix");
		String vDate = request.getParameter("sDate");
		String vAdresse = request.getParameter("sAdresse");
		String vCP = request.getParameter("sCP");
		String vVille = request.getParameter("sVille");
		String vCate = request.getParameter("sCate");
		HttpSession session = request.getSession();
		int numUtil = (int) session.getAttribute("numUtil");

		LocalDate dateFin = LocalDate.parse(vDate);
		int prixInit = Integer.parseInt(vPrix);
		int numCate = Integer.parseInt(vCate);
		
		Retrait retrait;

		if (request.getParameter("sPublier") != null) {
			nVente = new Vente(vArticle, vDescription, dateFin, prixInit, prixInit, numUtil, numCate, vPhoto, true);
		} else {
			nVente = new Vente(vArticle, vDescription, dateFin, prixInit, prixInit, numUtil, numCate, vPhoto, false);
		}

		try {
			VenteManager.ajouter(nVente);
		} catch (DALException e) {
			e.printStackTrace();
		}

		retrait = new Retrait(nVente.getNumVente(), vAdresse, vCP, vVille);

		try {
			RetraitManager.ajouterRetrait(retrait);
		} catch (DALException e) {
			e.printStackTrace();
		}
		response.sendRedirect("./ServPagePrincipale?successCreateVente=1");

	}

	// upload du fichier
	private String uploadFichier(Part part) throws IOException {
		// retrouver le nom du fichier uploadé
		String filename = this.getNomFichier(part); //récupère le nom du fichier
		String nomFichierUpload;
		// mise en forme du nom
		String prefix = filename;
		String suffix = "";
		if (filename.contains(".")) { //si le fichier a une extension
			prefix = filename.substring(0, filename.lastIndexOf('.'));
			System.out.println(prefix);
			suffix = filename.substring(filename.lastIndexOf('.'));
			System.out.println(suffix);
		}

		// écrire le fichier
		File file = File.createTempFile(prefix + "_", suffix,
				new File(this.getClass().getAnnotation(MultipartConfig.class).location()));
		nomFichierUpload = file.getName();
		// copie simple
		if (MODE_MULTIPART) {
			part.write(file.getName());
		}
		// copie streaming
		else {
			InputStream input = null;
			OutputStream output = null;
			try {
				input = new BufferedInputStream(part.getInputStream(), TAILLE_BUFFER);
				output = new BufferedOutputStream(new FileOutputStream(file), TAILLE_BUFFER);
				byte[] buffer = new byte[TAILLE_BUFFER];
				for (int length = 0; ((length = input.read(buffer)) > 0);) {
					output.write(buffer, 0, length);
				}
			} finally {
				if (output != null)
					try {
						output.close();
					} catch (IOException e1) {
						/**/ }
				if (input != null)
					try {
						input.close();
					} catch (IOException e2) {
						/**/ }
			}
		}
		// détruire la copie de l'objet
		part.delete();
		// retourner l'objet
		return nomFichierUpload;
	}

	// retourner le nom d'un fichier envoyé
	private String getNomFichier(Part part) {
		for (String cd : part.getHeader(TYPE_CONTENU).split(";")) { //Si plusieurs fichiers, séparés par un point-virgule
			if (cd.trim().startsWith(NOM_TYPE_CONTENU)) {
				System.out.println(cd.substring(cd.indexOf('=') + 1).trim().replace("\"", ""));
				return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}

}