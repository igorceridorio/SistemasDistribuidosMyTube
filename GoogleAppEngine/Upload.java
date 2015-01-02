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
 *      Servlet que suporta requisições HTTP POST. Possui os seguintes métodos:
 * 			- doPost. Recebe o nome e a descricao do video, cria um objeto do tipo
 * 			  "Video" e o torna persistente. Em seguida, o ID gerado para o objeto
 * 			  eh retornado para quem fez a requisicao.
 */

package org.mytube.controle;

import java.io.IOException;

import javax.jdo.PersistenceManager;
import javax.jdo.identity.LongIdentity;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.mytube.appengine.PMF;
import org.mytube.video.Video;

@SuppressWarnings ( "serial" )
public class Upload extends HttpServlet {

	@Override
	protected void doPost( HttpServletRequest req, HttpServletResponse resp ) throws ServletException, IOException {

		String descricao = req.getParameter("descricao");
		Long id = null;
		
		// Cria o objeto a ser armazenado
		Video video = new Video(descricao);

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			// Armazena o objeto criado, garantindo que as mudanças ocorram
			pm.makePersistent(video);
			pm.flush();
			
			// Guarda em "id" o identificador do objeto guardado
			id = ((LongIdentity) pm.getObjectId(video)).getKey();
		}
		finally {
			pm.close();
		}

		// Coloca no header o identificador que será pego pelo Web Service
		resp.setContentType("text/html");
		resp.setHeader("idGerado", id.toString());
		resp.getWriter().println(id);
	}
}
