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
 *          - id. Armazena a id gerada pela nuvem.
 *          - descricao. Armazena a descricao informada pelo usuario.
 *          - video. Contem o video em formato byte array.
 *          - nome. Contem o nome do video juntamente com sua extensao.
 */

package rmi;

public class Video {

    private Long id;
    private String descricao;
    private byte[] video;
    private String nome;

    public Video() {

    }

    public Video( Long id, String descricao ) {
        this.id = id;
        this.descricao = descricao;
    }

    public void setId( Long id ) {
        this.id = id;
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

    public byte[] getVideo() {
        return video;
    }

    public void setVideo( byte[] video ) {
        this.video = video;
    }

    public String getNome() {
        return nome;
    }

    public void setNome( String nome ) {
        this.nome = nome;
    }

}
