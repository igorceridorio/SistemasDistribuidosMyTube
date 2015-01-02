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
 *      WebService da aplicacao que contem o cliente rmi. Possui os seguintes
 *      metodos:
 *          - upload. Armazena o nome e a descricao do video na nuvem, gera
 *            a id para o video, envia o byte[] e a id gerada para armazenamento
 *            no servidor rmi.
 *          - download. Dada uma id realiza a busca da descricao na nuvem, 
 *            faz o donwload deste video no servidor rmi.
 *          - enviarDescricao. Armazena a descricao do video na nuvem e gera uma
 *            id para o mesmo.
 *          - receberDescricao. Dada uma id passada como parametro retorna a
 *            descricao do video correspondente que esta armazena na nuvem.
 */

package rmi;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import java.util.StringTokenizer;

import javax.jws.WebService;
import javax.jws.WebMethod;
import javax.jws.WebParam;

@WebService(serviceName = "RMICliente")
public class RMICliente implements Serializable {

    @WebMethod(operationName = "upload")
    public String upload( @WebParam(name = "vNome") String vNome, @WebParam(name = "vDesc") String vDesc, @WebParam(name = "vBytes") byte[] vBytes ) throws RemoteException {

        String vIdGerada = null;

        try {
            //localizando o servidor rmi para execucao local
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            //localizando o servidor rmi para execucao em outra maquina
            //Registry reg = LocateRegistry.getRegistry("10.10.1.235", 1099);
            
            //obtendo interface do servidor rmi
            RMIUploadDownload rmi = (RMIUploadDownload) reg.lookup("server");
            System.out.println("WebService/RMICliente: Conectado ao servidor RMI");

            //chamada do Google AppEngine, envio da descricao e geracao da id
            Video video = new Video();
            video.setDescricao(URLEncoder.encode(vNome + "@" + vDesc, "UTF-8"));
            vIdGerada = enviarDescricao(video);

            //chamada do metodo de upload do servidor rmi
            rmi.upload(vIdGerada, vBytes);

        }
        catch ( RemoteException | NotBoundException | UnsupportedEncodingException e ) {
            System.out.println("WebService/RMICliente: Exceção no envio do vídeo: " + e.toString());
        }

        return vIdGerada;
    }

    @WebMethod(operationName = "download")
    public Video download( @WebParam(name = "id") String id ) throws RemoteException {

        //instanciacao do objeto Video que recebera o byte[] e a descricao
        Video video = new Video();

        try {
            //localizando o servidor rmi para execucao local
            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            //localizando o servidor rmi para execucao em outra maquina            
            //Registry reg = LocateRegistry.getRegistry("10.10.1.235", 1099);
            
            //obtendo interface do servidor rmi
            RMIUploadDownload rmi = (RMIUploadDownload) reg.lookup("server");
            System.out.println("WebService/RMICliente: Conectado ao servidor RMI");

            //chamada do Google AppEngine, envio do id e obtencao do nome e descricao
            String nameAndExtension = receberDescricao(id);
            
            //chamada do metodo de download do servidor rmi caso o id exista na nuvem
            if(nameAndExtension != null){
                System.out.println("Google AppEngine: ID encontrada com sucesso!");
            
                //quebra o retorno da nuvem, armazena ID, nome e descricao no objeto Video
                StringTokenizer tokenizer = new StringTokenizer(nameAndExtension, "@");

                video.setId(Long.parseLong(id));
                video.setNome(tokenizer.nextToken());
                video.setDescricao(tokenizer.nextToken());
                
                //chamada do servidor rmi
                video.setVideo(rmi.download(id));
            }

        }
        catch ( RemoteException | NotBoundException | UnsupportedEncodingException e ) {
            System.out.println("Exceção no download do vídeo: " + e.toString());
        }

        //retorna o objeto Video para o usuario (FrontEnd)
        return video;
    }

    //Google AppEngine. Metodo para upload da descricao e geracao da ID.
    private static String enviarDescricao( Video video ) throws UnsupportedEncodingException {
        try {
            // Google App Engine
            URL url = new URL("http://test-mercador.appspot.com/upload");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            try ( OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream()) ) {
                writer.write("descricao=" + video.getDescricao());
            }
            if ( connection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                System.out.println("Google AppEngine: Arquivo enviado para a nuvem com sucesso!");
                String idGerado = connection.getHeaderField("idGerado");
                return idGerado;
            }
            else {
                throw new UnsupportedEncodingException();
            }
        }
        catch ( MalformedURLException e ) {
            System.out.println("Exceção no envio da descrição: " + e.toString());
        }
        catch ( IOException e ) {
            System.out.println("Exceção no envio da descrição: " + e.toString());
        }

        return null;
    }

    //Google App Engine. Metodo para obter a descricao dado um ID.
    private static String receberDescricao( String id ) throws UnsupportedEncodingException {
        try {
            // Google App Engine
            URL url = new URL("http://test-mercador.appspot.com/download?method=search&id=" + id);
            // Local
            //URL url = new URL("http://localhost:8888/download?method=search&id=" + id);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("GET");
            String descricao;
            descricao = connection.getHeaderField("descricao");
            System.out.println("Google AppEngine: recebendo descricao");
            System.out.println("Dados da nuvem:");
            System.out.println("descricao: " + descricao);
            if ( connection.getResponseCode() == HttpURLConnection.HTTP_OK ) {
                System.out.println("Google AppEngine: Arquivo recebido da nuvem com sucesso!");
            }
            else {
                System.out.println("Google AppEngine: Erro!");
                System.out.println("Servidor retornou  erro HTTP: " + connection.getResponseCode());
            }
            return descricao;
        }
        catch ( MalformedURLException e ) {
            System.out.println("Google AppEngine. Exceção na recepção da descrição: " + e.toString());
        }
        catch ( IOException e ) {
            System.out.println("Google AppEngine. Exceção na recepção da descrição: " + e.toString());
        }

        return null;
    }
}
