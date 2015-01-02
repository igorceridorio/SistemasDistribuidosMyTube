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
 *      Interface criada para o rmi. Contem a definicao dos seguintes metodos:
 *          - upload. recebe um id e um byte[] vindos como parametros do cliente
 *            rmi (webservice) e armazena o video no mongoDB.
 *          - download. recebe um id vindo como parametro do cliente rmi 
 *            (webservice) e retorna um byte[] com o video vindo do mongoDB.
 */

package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface RMIUploadDownload extends Remote{
	
	//metodo para que o video passado como parametro seja armazenado no servidor
	public void upload( String vId, byte[] bVideo ) throws RemoteException;

	//metodo para que um id passado como parametro retorne o video do banco de dados
	public byte[] download( String id ) throws RemoteException;

}