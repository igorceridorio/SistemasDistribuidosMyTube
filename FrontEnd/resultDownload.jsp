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
 *      Arquivo de resultado de download de arquivo da interface de usuario do 
 *      projeto.
 */
-->

<%@page import="java.io.FileOutputStream"%>
<%@page import="java.io.File"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ page import="rmi.Video" %>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>MyTube | Download</title>
        <meta name="generator" content="Bootply" />
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
        <link href="css/bootstrap.min.css" rel="stylesheet">
        <!--[if lt IE 9]>
                <script src="//html5shim.googlecode.com/svn/trunk/html5.js"></script>
        <![endif]-->
        <link href="css/styles.css" rel="stylesheet">
    </head>
    <body>
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
                    <h1><span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span> Dados do download</h1>
                </div>

                <%
                    //final String myPath = "/home/giulianno/Documents/MyTubeDownloads";
                    final String myPath = "/home/igorceridorio/MyTubeDownloads/";
                    
                    try {
                        rmi.RMICliente_Service service = new rmi.RMICliente_Service();
                        rmi.RMICliente port = service.getRMIClientePort();
                        java.lang.String id = request.getParameter("idvideo");

                        //chama o metodo download do WS e recebe um objeto Video como retorno
                        rmi.Video result = port.download(id);

                        //se a descricao nao for nula, exibe as informacoes do video
                        if ( result.getDescricao() != null ) {
                            //exibe as informacoes do video baixado
                            out.println("<h3>Vídeo retornado com sucesso!</h3><br>");
                            out.println("Identificador: <strong>" + result.getId() + "</strong><br>");
                            out.println("Nome: <strong>" + result.getNome() + "</strong><br>");
                            out.println("Descrição: <strong>" + result.getDescricao() + "</strong>");

                            // Recria o arquivo de saída
                            File file = new File(myPath + result.getNome());
                            FileOutputStream fos = new FileOutputStream(file);
                            fos.write(result.getVideo());
                            fos.flush();
                            fos.close();
                        }
                        else {
                            //caso contrario, nao ha video com tal id no banco de dados
                            out.println("<h3>Não há vídeo com esta ID presente no banco de dados.</h3><br>");
                        }
                    }
                    catch ( Exception ex ) {
                        System.out.println("Exceção no resultDownload.jsp: " + ex);
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
