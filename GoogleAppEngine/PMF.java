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
 *      Definicao de uma classe para um objeto PMF que contem os atributos:
 *          - pmfInstance. Instancia do PersistenceManagerFactory, responsavel
 *            por tornar persistente a descricao recebida do video.
 */

package org.mytube.appengine;

import javax.jdo.JDOHelper;
import javax.jdo.PersistenceManagerFactory;

public final class PMF {

	private static final PersistenceManagerFactory pmfInstance = JDOHelper.getPersistenceManagerFactory("transactions-optional");

	public PMF() {
	}

	public static PersistenceManagerFactory get() {
		return pmfInstance;
	}

}
