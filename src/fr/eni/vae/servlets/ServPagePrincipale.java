package fr.eni.vae.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import fr.eni.vae.bll.CategorieManager;
import fr.eni.vae.bo.Categorie;
import fr.eni.vae.dal.DALException;

/**
 * Servlet implementation class ServPagePrincipale
 */
@WebServlet("/ServPagePrincipale")
public class ServPagePrincipale extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

		//récupérer la liste des catégories
		List<Categorie> listeCategorie = new ArrayList<>();
		try {
			listeCategorie = CategorieManager.recupCategorie();
		} catch (DALException e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("categorie", listeCategorie);

		if (request.getParameter("successCreateVente") != null
				&& request.getParameter("successCreateVente").equals("1")) {
			request.setAttribute("MessageAjoutVente", "Votre annonce vient d'etre publiee");
		}
		this.getServletContext().getRequestDispatcher("/WEB-INF/jsp/PagePrincipale.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

	}

}
