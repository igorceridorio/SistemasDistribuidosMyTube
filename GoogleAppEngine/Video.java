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
 *      Definicao de uma classe para um objeto Video que contem os atributos:
 *          - id. Armazena a id gerada dinamicamente.
 *          - descricao. Armazena a descricao informada pelo usuario.
 */

package org.mytube.video;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

// Definição do objeto que é feito persistente
@PersistenceCapable ( identityType = IdentityType.APPLICATION )
public class Video {

	// Chave que é gerada e utilizada pelas aplicações
	@PrimaryKey
	@Persistent ( valueStrategy = IdGeneratorStrategy.IDENTITY )
	private Long id;

	@Persistent
	private String descricao;

	public Video( String descricao ) {
		this.descricao = descricao;
	}

	public Long getId() {
		return id;
	}

	public void setDescricao( String descricao ) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
