package com.davi.Screescreenmatch.model;

import com.davi.Screescreenmatch.service.ConsumoApi;
import lombok.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class MetodosTitulo {
    private String name;
    private List<DadosSerie> seriesList = new ArrayList<>();
    private List<DadosTemporada> temporadas = new ArrayList<>();
    private List<Episodio> todesEpisodes;
    ConsumoApi consumoApi = new ConsumoApi();

    public MetodosTitulo(String name) {
        setName(name);
        DadosSerie serie = consumoApi.ObterDadosJsonTitulo(getName());

        try {
            seriesList.add(serie);
            for (int i = 1; i < serie.totalTemporadas() + 1; i++) {
                var season = consumoApi.ObterDadosJsonSeason(serie.titulo(), i);
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
        seriesList.forEach(System.out::println);
    }

    public void listEpisodes() {
        todesEpisodes.forEach(System.out::println);
    }

    public void listTemporadas() {
        temporadas.forEach(t -> t.print());
    }


    public void buscarEpisodio(Integer temporada, Integer ep) {
        todesEpisodes.stream()
                .filter(t -> t.getTemporada() == temporada && t.getNumeroEp() == ep)
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

    public void buscaEpisode(String title) {
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
                    "\nMedia da temporada: %.2f", est.getAverage());
        });
    }
}







