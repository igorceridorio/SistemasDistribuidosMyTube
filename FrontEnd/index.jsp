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
 *      Arquivo index da interface de usuario do projeto.
 */
-->

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="content-type" content="text/html; charset=UTF-8">
        <meta charset="utf-8">
        <title>MyTube</title>
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
                <div class="row">
                    <div class="col-md-6">
                        <!-- Download -->
                        <div class="page-header">
                            <h1><span class="glyphicon glyphicon-cloud-download" aria-hidden="true"></span> Download</h1>
                        </div>
                        <form name="download" action="resultDownload.jsp" method="POST">
                            <div class="form-group">
                                <label for="idvideo">Informe o ID do vídeo</label>
                                <input type="text" class="form-control" name="idvideo" style="width:300px;">
                            </div>
                            <button type="submit" class="btn btn-success" value="Enviar">Enviar</button>
                        </form>
                        <!-- Download -->
                    </div>

                    <div class="col-md-6">
                        <!-- Upload -->
                        <div class="page-header">
                            <h1><span class="glyphicon glyphicon-cloud-upload" aria-hidden="true"></span> Upload</h1>
                        </div>
                        <form name="upload" action="resultUpload.jsp" method="POST" enctype="multipart/form-data">
                            <div class="form-group">
                                <label for="nomevideo">Escolha o arquivo</label>
                                <input type="file" name="nomevideo">
                                <p class="help-block">Clique no botão e escolha o vídeo desejado.</p>
                            </div>
                            <div class="form-group">
                                <label for="descricao">Descrição</label>
                                <input type="text" class="form-control" name="descricao" style="width:300px;">
                            </div>
                            <button type="submit" class="btn btn-success" value="Enviar">Enviar</button>
                        </form>
                        <!-- Upload -->
                    </div>
                </div>
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
