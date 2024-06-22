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
    @Getter(AccessLevel.PRIVATE)
    private Serie serie;
    @Autowired
    private SerieRepository serieRepository;
    ConsumoApi consumoApi = new ConsumoApi();
    private List<Serie> serieList;
    Optional<Serie> serieOptional;

    MetodosTitulo(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
        serieList = this.serieRepository.findAll();
    }

    MetodosTitulo(String name, SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
        serieList = this.serieRepository.findAll();
        DadosSerie dadosSerie = consumoApi.ObterDadosJsonTitulo(name);
        try {
            for (int i = 1; i < dadosSerie.totalTemporadas() + 1; i++) {
                DadosTemporada season = consumoApi.ObterDadosJsonTitulo(dadosSerie.titulo(), i);
                dadosTemporadas.add(season);
            }
            serie = new Serie(dadosSerie, dadosTemporadas);
            addSerieList(serie);

        } catch (Exception e) {
            System.out.println("\n" + e + "\n" + e.getCause() + "\nclass Titulo\n");
        }
    }

    @SneakyThrows
    public void exibirSerie() {

        System.out.println(serieOptional.isPresent() ?
                "\nDados da série: \n" + serie
                : "\nSérie não encontrada no banco de dados!\"");
    }

    @SneakyThrows
    public void listTemporadas() {
        if (serie.getTipo().contains("series")) {
            serie.getEpisodios().stream()
                    .collect(Collectors.groupingBy(Episodio::getTemporada))
                    .forEach((temporada, episodiosDaTemporada) -> {
                        System.out.println("\nSeason: " + temporada);
                        episodiosDaTemporada.forEach(System.out::println);
                    });
        } else System.out.println("nao tem episodios pq é um filme");
    }

    @SneakyThrows
    public void buscarEpisodio(Integer temporada, Integer ep) {

        serie.getEpisodios().stream()
                .filter(t -> t.getTemporada().equals(temporada) && t.getNumeroEp().equals(ep))
                .findFirst().ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Episódio não encontrado"));

    }

    @SneakyThrows()
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
        if (serie.getTipo().contains("series")) {
            try {
                System.out.printf("espisodios a partir de %s:", ano);
                serie.getEpisodios().stream().filter(e -> e.getDataLancamento() != null &&
                                e.getDataLancamento().isAfter(dateBusca))
                        .forEach(System.out::println);
            } catch (Exception e) {
                System.out.println("ano invalido");
            }
        } else {
            System.out.println("nao tem episodios pq é um filme");
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
        serieOptional = serieRepository
                .findByTituloContainingIgnoreCase(serie.getTitulo());
        if (serie.getTipo().contains("series")) {
        if (serieOptional.isEmpty()) {
            serieRepository.save(serie);
            serieList = serieRepository.findAll();
            serieOptional = serieRepository
                    .findByTituloContainingIgnoreCase(serie.getTitulo());
            this.serie = serieOptional.get();}} else {
            System.out.println(serie.getTitulo()+ " nao é uma serie, é um filme");}
        }



//    protected void listaSeriesBancoDados() {
//        serieList
//                .stream()
//                .sorted(Comparator.comparing(Serie::getGenero))
//                .forEach(System.out::println);
//    }

    protected void buscarSerieExistenteDB(String name) {

        Optional<Serie> contains = serieRepository
                .findByTituloContainingIgnoreCase(name);
        System.out.println((contains.isPresent() ?
                contains.get() : "Serie nao existe no banco de dados ainda"));
    }

    protected void buscarAtor(String name) {
        List<Serie> contains = serieRepository
                .findByAtoresContainingIgnoreCase(name);
        System.out.printf("Series em que %s participou\n:", name);
        contains.forEach(s -> System.out.println(s.getTitulo()));
    }


}







