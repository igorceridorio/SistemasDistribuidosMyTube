/**
 * -------------------------------------------
 * Universidade Federal de Sao Carlos - UFSCar
 * ------------------------------------------- 
 * 
 * Sistemas Distribuidos - Prof. Dr. Fabio Luciano Verdi
 * 
 * Alunos:
 *      408093 - Giulianno Raphael Sbrugnera 
 *      408611 - Igor Felipe Ferreira Ceridorio
 *
 * Descricao do arquivo:
 *      Servlet que suporta requisições HTTP GET. Possui os seguintes métodos:
 * 			- doGet. Retorna o nome e a descricao do video dado um ID.
 */

package org.mytube.controle;

import java.io.IOException;
import java.util.List;

import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mytube.appengine.PMF;
import org.mytube.video.Video;

@SuppressWarnings ( "serial" )
public class Download extends HttpServlet {

	@Override
	protected void doGet( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		String method = req.getParameter("method");
		Query query = pm.newQuery(Video.class);
		List<Video> videoLista = null;

		try {
			videoLista = (List<Video>) query.execute();
		}
		finally {
			query.closeAll();
		}

		// Retorna o nome + descrição do vídeo armazenado no sistema de persistência da App Engine
		if ( method.equals("search") ) {
			Long id = Long.valueOf(req.getParameter("id")).longValue();
			Video video = null;

			// Procura pelo objeto que possui chave "id"
			for ( Video videoItem : videoLista ) {
				if ( videoItem.getId().equals(id) ) {
					video = videoItem;
					break;
				}
			}

			String descricao = video.getDescricao();
			id = video.getId();

			// Coloca no header a descrição que será pega pelo Web Service
			resp.setHeader("descricao", descricao);
			resp.getWriter().println(descricao);
		}
	}
}
