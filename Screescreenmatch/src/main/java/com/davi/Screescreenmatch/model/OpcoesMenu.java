package com.davi.Screescreenmatch.model;

import com.davi.Screescreenmatch.Repository.SerieRepository;
import com.davi.Screescreenmatch.model.clas.Serie;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

public class OpcoesMenu {
    private final Scanner scanner = new Scanner(System.in);

    private MetodosTitulo titulo;

    private SerieRepository serieRepository;

    public OpcoesMenu(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
        titulo = new MetodosTitulo(serieRepository);
    }

    protected void Statistics() {
        buscarAdd();
        titulo.avalicaoPorTemporada();
    }

    protected void buscarPorNome() {
        buscarAdd();
        System.out.println("digite um trecho do titulo");
        titulo.buscaEpisodePorNome(scanner.nextLine());

    }

    protected void top10() {
        buscarAdd();
        titulo.topEpisode(10);

    }

    protected void listaEpisodios() {
        buscarAdd();
        titulo.listTemporadas();
    }

    protected void buscarEp() {
        buscarAdd();
        System.out.println("digite a temporada: ");
        int temporada = scanner.nextInt();
        System.out.println("digite o episodio: \n");
        int episodio = scanner.nextInt();
        titulo.buscarEpisodio(temporada, episodio);
    }

//    protected void listaSeriesBancoDados() {
//        titulo.listaSeriesBancoDados();
//    }

    protected void exibiSerieBuscada() {
        buscarAdd();
        titulo.exibirSerie();
    }

   protected void buscarSerieDB(){
       System.out.println("digite o nome da serie");
       titulo.buscarSerieExistenteDB(scanner.nextLine());

    }


    private void buscarAdd() {
        System.out.println("digite o nome da serie");
        titulo = new MetodosTitulo(scanner.nextLine(), serieRepository);
    }

    protected void buscarAtor(){
        System.out.println("Digite o nome do ator: ");
        titulo.buscarAtor(scanner.nextLine());
    }

}



