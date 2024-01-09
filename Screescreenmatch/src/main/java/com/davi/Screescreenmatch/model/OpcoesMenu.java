package com.davi.Screescreenmatch.model;

import com.davi.Screescreenmatch.model.clas.Serie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class OpcoesMenu {
    List<MetodosTitulo> listaTitulo = new ArrayList<>();
    private Scanner scanner = new Scanner(System.in);
    private List<Serie> serieList = new ArrayList<>();

    protected void Statistics() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.avalicaoPorTemporada();
        checkContains(titulo);
    }

    protected void buscarPorNome() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        System.out.println("digite um trecho do titulo");
        titulo.buscaEpisode(scanner.nextLine());
        checkContains(titulo);
    }

    protected void top10() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.topEpisode(10);
        checkContains(titulo);

    }

    protected void listaEpisodios() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.listTemporadas();
        checkContains(titulo);

    }

    protected void buscarEp() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        System.out.println("digite a temporada: ");
        int temporada = scanner.nextInt();
        System.out.println("digite o episodio: \n");
        int episodio = scanner.nextInt();
        titulo.buscarEpisodio(temporada, episodio);
        checkContains(titulo);
    }

    protected void listaSeriesBuscadas() {
        listaTitulo.forEach(t -> t.exibirSerie());
    }

    private void CriaListaSerie() {
        serieList = listaTitulo
                .stream()
                .flatMap(t -> t.DadosSeriesList
                        .stream()
                        .map(d -> new Serie(d))
                ).collect(Collectors.toList());
    }


    protected void exibiSerieBuscada() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.DadosSeriesList.forEach(System.out::println);
        checkContains(titulo);
    }

    private void checkContains(MetodosTitulo metodosTitulo) {

        listaTitulo = listaTitulo.stream()
                //se forem iguais sao apagados, se forem diferentes continuam
                .filter(t -> !t.thisName.equals(metodosTitulo.thisName))
                .collect(Collectors.toList());
        listaTitulo.add(metodosTitulo);
        System.out.println(listaTitulo);
            CriaListaSerie();
        }

    }

