package com.davi.Screescreenmatch.model;

import com.davi.Screescreenmatch.service.ConsumoApi;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Getter(AccessLevel.PRIVATE)
@Setter(AccessLevel.PRIVATE)
public class BuscaTitulo {
    private String name;
    private List<DadosTemporada> temporadas = new ArrayList<>();
    private List<Episodio> todesEpisodes;
    ConsumoApi consumoApi = new ConsumoApi();

    public BuscaTitulo(String name) {
        setName(name);
        DadosSerie serie = consumoApi.ObterDadosJsonTitulo(getName());
        try {

            for (int i = 1; i < serie.totalTemporadas() + 1; i++) {
                var season = consumoApi.ObterDadosJsonSeason(serie.titulo(), i);
                temporadas.add(season);
            }
            todesEpisodes = temporadas.stream()
                    .flatMap(t -> t.episodios().stream()
                            .map(d -> new Episodio(t.numero(), d))
                    ).collect(Collectors.toList());

        } catch (NullPointerException e) {
            System.out.println("input invalid");
        }

    }


    public void listEpisodes() {
        todesEpisodes.forEach(System.out::println);

    }

    public void listTemporadas() {

        temporadas.forEach(t -> t.print());
    }


    public void topEpisode(int topX) {
        System.out.println("TOP " + topX + "\n");
        todesEpisodes.stream()
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(topX)
                .forEach(System.out::println);

    }

    public void filterDate(int ano) {
        LocalDate dateBusca = LocalDate.of(ano, 1, 1);
        try {
            System.out.println("Episodios a partir de " + ano + ": ");

            todesEpisodes.stream().filter(e -> e.getDataLancamento() != null &&
                            e.getDataLancamento().isAfter(dateBusca))
                    .forEach(System.out::println);

        } catch (Exception e) {

            System.out.println("ano invalido");

        }

    }


}







