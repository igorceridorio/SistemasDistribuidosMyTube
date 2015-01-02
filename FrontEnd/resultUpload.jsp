<!-- 
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
 *      Arquivo de resultado de upload de arquivo da interface de usuario do 
 *      projeto.
 */
-->

<%@ page import="java.io.*,java.util.*, javax.servlet.*" %>
<%@ page import="javax.servlet.http.*" %>
<%@ page import="org.apache.commons.fileupload.*" %>
<%@ page import="org.apache.commons.fileupload.disk.*" %>
<%@ page import="org.apache.commons.fileupload.servlet.*" %>
<%@ page import="org.apache.commons.io.output.*" %>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>MyTube | Upload</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!--[if lt IE 9]>
                <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <link href="css/styles.css" rel="stylesheet">
    </head>
    <body>
        <!-- Wrap all page content here -->
        <div id="wrap">

            <!-- Fixed navbar -->
            <div class="navbar navbar-default navbar-fixed-top">
                <div class="container">
                    <div class="navbar-header">
                        <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                            <span class="icon-bar"></span>
                        </button>
                        <a class="navbar-brand" href="index.jsp">MyTube</a>
                    </div>
                </div>
            </div>

            <div class="container"> <!-- Conteúdo principal -->

                <div class="page-header">
                    <h1><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Dados do upload</h1>
                </div>


                <%
                    //final String myPath = "/home/giulianno/Documents/tmp";
                    final String myPath = "/home/igorceridorio/fileupload/tmp";

                    //cria um factory para os arquivos baseados em disco
                    DiskFileItemFactory factory = new DiskFileItemFactory();

                    //configura um repositorio local para os arquivos
                    factory.setRepository(new File(myPath));

                    //cria um novo manipulador de upload para arquivos
                    ServletFileUpload upload = new ServletFileUpload(factory);

                    //realiza o parse da requisicao
                    List<FileItem> items = upload.parseRequest(request);

                    //processa os itens do upload
                    Iterator<FileItem> iter = items.iterator();

                    //variaveis que armazenam as informacoes
                    String output = "";
                    String descricaoVideo = null;
                    String fileName = null;
                    byte[] vBytes = null;

                    while ( iter.hasNext() ) {
                        FileItem item = iter.next();

                        if ( item.isFormField() ) {
                            // obtem o campo de descricao do arquivo
                            output += "<strong>Descrição do vídeo: </strong>" + item.getString() + "<br>";
                            descricaoVideo = item.getString();
                        }
                        else {
                            //armazena o arquivo em um byte[]
                            vBytes = item.get();
                            
                            //exibe algumas informacoes sobre o arquivo e armazena o nome do mesmo
                            output += "<strong>Nome do arquivo: </strong>" + item.getName() + "<br>";
                            output += "<strong>Tipo do conteúdo: </strong>" + item.getContentType() + "<br>";
                            fileName = item.getName();
                        }
                    }

                    out.println(output + "<hr>");

                    //chama o metodo de upload para o web service
                    try {
                        rmi.RMICliente_Service service = new rmi.RMICliente_Service();
                        rmi.RMICliente port = service.getRMIClientePort();

                        java.lang.String result = port.upload(fileName, descricaoVideo, vBytes);
                        out.println("<br><strong>ID gerada para este vídeo:</strong> " + result);
                        out.println("<br><p>Utilize esse ID para fazer o download do vídeo assim que desejar.</p>");
                    }
                    catch ( Exception ex ) {
                        out.println("<br>Ocorreu um erro durante a geração da ID.");
                        ex.printStackTrace();
                    }

                %>
            </div>
        </div>

        <div id="footer">
            <div class="container">
                <p class="text-muted credit">Projeto Final &mdash; Sistemas Distribuídos &mdash; Universidade Federal de São Carlos, campus Sorocaba</p>
            </div>
        </div>

        <!-- referencias de scripts utilizados -->
        <script src="js/jquery-1.11.1.min.js"></script>
        <script src="js/bootstrap.min.js"></script>

    </body>
</html>
