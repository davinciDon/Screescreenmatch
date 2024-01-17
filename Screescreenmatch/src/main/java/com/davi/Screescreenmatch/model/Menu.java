package com.davi.Screescreenmatch.model;

import com.davi.Screescreenmatch.Repository.SerieRepository;

import java.util.Scanner;

public class Menu {

    Scanner scanner = new Scanner(System.in);
    OpcoesMenu opcoesMenu;


   public Menu(SerieRepository serieRepository){

       opcoesMenu = new OpcoesMenu(serieRepository);
    }
    public void exibirMenu() {
        var opcao = -1;
        try {
            while (opcao != 0) {

                var menu = """
                        1 - Buscar titulo
                        2 - Buscar episódio
                        3 - Listar titulos buscados
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
                        opcoesMenu.listaSeriesBancoDados();
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
            System.out.println("Serie nao encontrada");
            System.out.println("Problema: " + e +
                    "\n" + e.getCause());
            scanner.nextLine();

        }
        exibirMenu();
    }
}
