package com.davi.Screescreenmatch.model;

import com.davi.Screescreenmatch.Repository.SerieRepository;
import com.davi.Screescreenmatch.model.Dados.DadosSerie;
import com.davi.Screescreenmatch.model.Dados.DadosTemporada;
import com.davi.Screescreenmatch.model.clas.Episodio;
import com.davi.Screescreenmatch.model.clas.Serie;
import com.davi.Screescreenmatch.service.ConsumoApi;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;


public class MetodosTitulo {

    private final List<DadosTemporada> dadosTemporadas = new ArrayList<>();
    @Getter
    private Serie serie;
    @Autowired
    private SerieRepository serieRepository;
    private final DadosSerie dadossSerie;
    ConsumoApi consumoApi = new ConsumoApi();
    private List<Serie> serieList;


    MetodosTitulo(String name, SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
        serieList = this.serieRepository.findAll();
        dadossSerie = consumoApi.ObterDadosJsonTitulo(name);
        try {
            for (int i = 1; i < dadossSerie.totalTemporadas() + 1; i++) {
                DadosTemporada season = consumoApi.ObterDadosJsonSeason(dadossSerie.titulo(), i);
                dadosTemporadas.add(season);
            }
            serie = new Serie(dadossSerie, dadosTemporadas);
        } catch (Exception e) {
            System.out.println("\n" + e + "\n" + e.getCause() + "\nclass Titulo\n");
        }
    }

    public void exibirSerie() {
        System.out.println(getSerie());
    }

    public void listTemporadas() {
        serie.getEpisodios().stream()
                .collect(Collectors.groupingBy(Episodio::getTemporada))
                .forEach((temporada, episodiosDaTemporada) -> {
                    System.out.println("\nSeason: " + temporada);
                    episodiosDaTemporada.forEach(System.out::println);
                });
    }
    public void buscarEpisodio(Integer temporada, Integer ep) {
        serie.getEpisodios().stream()
                .filter(t -> t.getTemporada().equals(temporada) && t.getNumeroEp().equals(ep))
                .findFirst().ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Episódio não encontrado"));

    }
    public void topEpisode(int topX) {
        System.out.println("TOP " + topX + "\n");
        serie.getEpisodios().stream()
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(topX)
                .forEach(System.out::println);
    }
    @SneakyThrows
    public void filterDate(int ano) {
        LocalDate dateBusca = LocalDate.of(ano, 1, 1);
        try {
            System.out.printf("espisodios a partir de %s:", ano);

            serie.getEpisodios().stream().filter(e -> e.getDataLancamento() != null &&
                            e.getDataLancamento().isAfter(dateBusca))
                    .forEach(System.out::println);
        } catch (Exception e) {
            System.out.println("ano invalido");
        }
    }
    public void buscaEpisodePorNome(String title) {
        serie.getEpisodios().stream()
                .filter(t -> t.getTitulo().toLowerCase().contains(title.toLowerCase()))
                .findFirst().ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Episódio não encontrado"));
    }
    public void avalicaoPorTemporada() {
        dadosTemporadas.forEach(t -> {
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
    public void addSerieList(Serie serie) {
        Optional<Serie> serieOptional = serieList.stream()
                .filter(s -> s.getTitulo().equalsIgnoreCase(serie.getTitulo()))
                .findFirst();
        if (serieOptional.isEmpty()) {
            serieRepository.save(serie);
            serieList = serieRepository.findAll();
        }
    }
    protected void listaSeriesBancoDados() {
        serieList
                .stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }
}







