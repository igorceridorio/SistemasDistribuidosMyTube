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
 *      Implementacao do servidor RMI que possui os seguintes metodos:
 *          - upload. Recebe uma id e um video em byte[] do WS, reliza 
 *            uma conexao com o mongoDB e armazena o arquivo.
 *          - download. Dada uma id passada como parametro, busca o video
 *            no mongoDB e retorna um byte[] do arquivo ao WS.
 *          - main. Cria um registro rmi e sobe o servidor.
 */

package rmi;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import com.mongodb.*;
import com.mongodb.gridfs.*;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class RMIServidor extends UnicastRemoteObject implements RMIUploadDownload{
    
    public RMIServidor() throws RemoteException{
        super();
    }

    @Override
    public void upload(String vId, byte[] bVideo) throws RemoteException {
       
        try{
			
            //conecta ao servidor do mongo
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            //conecta ao banco de dados
            DB db = mongoClient.getDB("videos");
            System.out.println("Metodo upload: Conectado com sucesso ao banco de dados");
            
            //cria um objeto GridFS
            GridFS fs = new GridFS(db, "videos");
            GridFSInputFile in = fs.createFile(bVideo);

            //seta o filename do registro com o id gerado na nuvem
            in.setFilename(vId);
            in.save();

            System.out.println("Metodo upload: Video inserido no banco de dados");

        } catch(Exception e){
            System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }
        
    }

    @Override
    public byte[] download(String id) throws RemoteException {
        
    String videoId = id;

        try{

            //conecta ao servidor do mongo
            MongoClient mongoClient = new MongoClient("localhost", 27017);

            //conecta ao banco de dados
            DB db = mongoClient.getDB("videos");
            System.out.println("Metodo download: Conectado com sucesso ao banco de dados");

            //busca no bd pelo video com a id passada como parametro no metodo
            GridFS gfsVideo = new GridFS(db, "videos");
            GridFSDBFile videoFound = gfsVideo.findOne(videoId);

            //aloca um array de bytes do tamanho do video encontrado no bd
            byte[] bVideo = new byte[new Long(videoFound.getLength()).intValue()];

            //armazena o arquivo vindo do bd em um InputStream
            InputStream gfsInputStream = videoFound.getInputStream();

            //cria um buffer para a conversao do InputStream bara byte[]
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            //armazena o video vindo do mongoDB no buffer
            int nRead;
            while((nRead = gfsInputStream.read(bVideo, 0, bVideo.length)) != -1){
                    buffer.write(bVideo, 0, nRead);
            }
            buffer.flush();

            //retorna o byte array do video requisitado para download
            System.out.println("Metodo download: Retorno com sucesso do banco de dados");
            return buffer.toByteArray();

        } catch(Exception e){
                        System.err.println( e.getClass().getName() + ": " + e.getMessage() );
        }	
        
        //caso o video nao for encontrado retorna null
        return null;
    }
    
    public static void main(String args[]){
        try{
            //define qual sera o ip onde o rmi devera subir e cria o registro
            //System.setProperty( "java.rmi.server.hostname" , "10.10.1.235" );
            Registry reg = LocateRegistry.createRegistry(1099);
            reg.rebind("server", new RMIServidor());
            System.out.println("Servidor inicializado...");
        } catch (Exception e){
            System.out.println(e);
        }
    }
    
}
