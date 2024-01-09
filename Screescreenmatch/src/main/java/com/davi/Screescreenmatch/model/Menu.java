package com.davi.Screescreenmatch.model;

import com.davi.Screescreenmatch.model.clas.Serie;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Menu {

    Scanner scanner = new Scanner(System.in);
    private List<Serie> serieList = new ArrayList<>();
    OpcoesMenu opcoesMenu = new OpcoesMenu();


    public void exibirMenu() {
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

                System.out.println("\n" + menu);
                opcao = scanner.nextInt();
                scanner.nextLine();

                switch (opcao) {
                    case 1:
                        opcoesMenu.exibiSerieBuscada();
                        break;
                    case 2:
                        opcoesMenu.buscarEp();
                        break;
                    case 3:
                        opcoesMenu.listaSeriesBuscadas();
                        break;
                    case 4:
                        opcoesMenu.listaEpisodios();
                        break;
                    case 5:
                        opcoesMenu.top10();
                        break;
                    case 6:
                        opcoesMenu.buscarPorNome();
                        break;
                    case 7:
                        opcoesMenu.Statistics();
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
            System.out.println("Problema: " + e +
                    "\n" + e.getCause());
            scanner.nextLine();

        }
        exibirMenu();
    }
}
