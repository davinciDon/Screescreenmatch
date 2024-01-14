package com.davi.Screescreenmatch.model;

import com.davi.Screescreenmatch.Repository.SerieRepository;
import com.davi.Screescreenmatch.model.clas.Serie;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class OpcoesMenu {
    @Autowired
    private SerieRepository serieRepository;
    List<Serie> serieList = new ArrayList<>();

    public OpcoesMenu(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    private final Scanner scanner = new Scanner(System.in);

    protected void Statistics() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.avalicaoPorTemporada();
        addSerieList(titulo.getSerie());
    }

    protected void buscarPorNome() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        System.out.println("digite um trecho do titulo");
        addSerieList(titulo.getSerie());
    }

    protected void top10() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.topEpisode(10);
        addSerieList(titulo.getSerie());

    }

    protected void listaEpisodios() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.listTemporadas();
        addSerieList(titulo.getSerie());

    }

    protected void buscarEp() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        System.out.println("digite a temporada: ");
        int temporada = scanner.nextInt();
        System.out.println("digite o episodio: \n");
        int episodio = scanner.nextInt();
        titulo.buscarEpisodio(temporada, episodio);

        addSerieList(titulo.getSerie());
    }

    protected void listaSeriesBuscadas() {
        serieList = serieRepository.findAll();
        serieList
                .stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    protected void exibiSerieBuscada() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.exibirSerie();
        addSerieList(titulo.getSerie());
    }

    private void addSerieList(Serie serie) {
                serieRepository.save(serie);
//        serieList = serieList.stream()
//                //se forem iguais sao apagados, se forem diferentes continuam
//                .filter(t -> !t.getTitulo().equals(serie.getTitulo()))
//                .collect(Collectors.toList());
//        serieList.add(serie);
    }


}

