package com.davi.Screescreenmatch.model;

import com.davi.Screescreenmatch.model.Dados.DadosSerie;
import com.davi.Screescreenmatch.model.Dados.DadosTemporada;
import com.davi.Screescreenmatch.model.clas.Episodio;
import com.davi.Screescreenmatch.model.clas.Serie;
import com.davi.Screescreenmatch.service.ConsumoApi;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;



public class MetodosTitulo {

    private final List<DadosTemporada> temporadas = new ArrayList<>();
    private List<Episodio> todesEpisodes;
    @Getter
    private Serie serie;
    ConsumoApi consumoApi = new ConsumoApi();


    MetodosTitulo(String name) {
        DadosSerie dadossSerie = consumoApi.ObterDadosJsonTitulo(name);


        try {
            serie = new Serie(dadossSerie);
            for (int i = 1; i < dadossSerie.totalTemporadas() + 1; i++) {
                DadosTemporada season = consumoApi.ObterDadosJsonSeason(dadossSerie.titulo(), i);
                temporadas.add(season);
            }
            todesEpisodes = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(d -> new Episodio(t.numero(), d))
                    ).collect(Collectors.toList());

        } catch (NullPointerException e) {
            System.out.println("\n" + e + "\nInput invalid class Titulo\n");
        }
    }

    public void exibirSerie() {
        System.out.println(getSerie());
    }

    public void listEpisodes() {
        todesEpisodes.forEach(System.out::println);
    }

    public void listTemporadas() {
        temporadas.forEach(DadosTemporada::print);
    }


    public void buscarEpisodio(Integer temporada, Integer ep) {
        todesEpisodes.stream()
                .filter(t -> t.getTemporada().equals(temporada) && t.getNumeroEp().equals(ep))
                .findFirst().ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Episódio não encontrado"));


    }

    public void topEpisode(int topX) {
        System.out.println("TOP " + topX + "\n");
        todesEpisodes.stream()
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(topX)
                .forEach(System.out::println);

    }

    @SneakyThrows
    public void filterDate(int ano) {
        LocalDate dateBusca = LocalDate.of(ano, 1, 1);
        try {
            System.out.printf("espisodios a partir de %s:", ano);

            todesEpisodes.stream().filter(e -> e.getDataLancamento() != null &&
                            e.getDataLancamento().isAfter(dateBusca))
                    .forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("ano invalido");
        }

    }

    public void buscaEpisodePorNome(String title) {
        todesEpisodes.stream()
                .filter(t -> t.getTitulo().toLowerCase().contains(title.toLowerCase()))
                .findFirst().ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Episódio não encontrado"));

    }

    public void avalicaoPorTemporada() {

        temporadas.forEach(t -> {
            DoubleSummaryStatistics est = t.episodios().stream()
                    .mapToDouble(ep -> {
                        try {
                            return Double.parseDouble(ep.avaliacao());
                        } catch (NumberFormatException e) {
                            return Double.NaN;
                        }
                    }).filter(a -> a > 0)
                    .summaryStatistics();
            System.out.printf("\n\nStatistics for season " + t.numero() + ": " +
                    "\nQuantidade de episodios: " + est.getCount() +
                    "\nAvaliçao minima " + est.getMin() +
                    "\nAvaliçao maxima: " + est.getMax() +
                    "\nMedia da temporada: %.2f\n   ", est.getAverage());
        });
    }
}







