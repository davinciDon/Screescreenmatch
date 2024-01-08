package com.davi.Screescreenmatch.Application;

import com.davi.Screescreenmatch.model.MetodosTitulo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Application {
    List<MetodosTitulo> listaTitulo = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);

    public void app() {

            loop();
    }

    private void loop() {
        var opcao = -1;
        try {
            while (opcao != 0) {

                var menu = """
                        1 - Buscar séries
                        2 - Buscar episódios
                        3 - Listar séries buscadas
                        4 - Exibir Lista de episodios
                        5 - Top 10 episodios
                        6 - Buscar episodio por nome
                        7 - Estastiticas da temporada
                        0 - Sair                                
                        """;

                System.out.println("\n"+menu);
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        buscarSerieWeb();
                        break;
                    case 2:
                        buscarEp();
                        break;
                    case 3:
                        listaSeriesBuscadas();
                        break;
                    case 4:
                        listaEpisodios();
                        break;
                    case 5:
                        top10();
                        break;
                    case 6:
                        buscarPorNome();
                        break;
                    case 7:
                        Statistics();
                        break;
                    case 0:
                        System.out.println("Saindo...");
                        break;
                    default:
                        System.out.println("Opção inválida");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            loop();
        }
    }

    private void Statistics() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.avalicaoPorTemporada();
        if (!listaTitulo.contains(titulo)) {
            listaTitulo.add(titulo);
        }
    }
    private void buscarPorNome() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        System.out.println("digite um trecho do titulo");
        titulo.buscaEpisode(scanner.nextLine());
        if (!listaTitulo.contains(titulo)) {
            listaTitulo.add(titulo);
        }
    }

    private void top10() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.topEpisode(10);
        if (!listaTitulo.contains(titulo)) {
            listaTitulo.add(titulo);
        }

    }

    private void listaEpisodios() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.listTemporadas();
        if (!listaTitulo.contains(titulo)) {
            listaTitulo.add(titulo);
        }

    }

    private void buscarEp() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        System.out.println("digite a temporada: ");
        int temporada = scanner.nextInt();
        System.out.println("digite o episodio: \n");
        int episodio = scanner.nextInt();
        titulo.buscarEpisodio(temporada, episodio);
        if (!listaTitulo.contains(titulo)) {
            listaTitulo.add(titulo);
        }
    }

    private void listaSeriesBuscadas() {
        listaTitulo.forEach(MetodosTitulo::exibirSerie);
    }

    private void buscarSerieWeb() {
        System.out.println("digite o nome da serie");
        MetodosTitulo titulo = new MetodosTitulo(scanner.nextLine());
        titulo.exibirSerie();
        if (!listaTitulo.contains(titulo)) {
            listaTitulo.add(titulo);
        }
    }
}