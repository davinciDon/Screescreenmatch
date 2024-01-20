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
    private  DadosSerie dadosSerie;
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
        dadosSerie = consumoApi.ObterDadosJsonTitulo(name);
        try {
            for (int i = 1; i < dadosSerie.totalTemporadas() + 1; i++) {
                DadosTemporada season = consumoApi.ObterDadosJsonTitulo(dadosSerie.titulo(), i);
                dadosTemporadas.add(season);
            }

            serie = new Serie(dadosSerie, dadosTemporadas);
            addSerieList(getSerie());

        } catch (Exception e) {
            System.out.println("\n" + e + "\n" + e.getCause() + "\nclass Titulo\n");
        }
    }

    public void exibirSerie() {

        System.out.println(serieOptional.isPresent()?
        "\nDados da série: \n" + serieOptional.get()
               :"\"Série não encontrada no banco de dados!\"");
  }

    public void listTemporadas() {
        if (serieOptional.get().getTipo().contains("series")) {
            serieOptional.get().getEpisodios().stream()
                    .collect(Collectors.groupingBy(Episodio::getTemporada))
                    .forEach((temporada, episodiosDaTemporada) -> {
                        System.out.println("\nSeason: " + temporada);
                        episodiosDaTemporada.forEach(System.out::println);
                    });
        } else System.out.println("nao tem episodios pq é um filme");
    }

    public void buscarEpisodio(Integer temporada, Integer ep) {


        serieOptional.get().getEpisodios().stream()
                .filter(t -> t.getTemporada().equals(temporada) && t.getNumeroEp().equals(ep))
                .findFirst().ifPresentOrElse(
                        System.out::println,
                        () -> System.out.println("Episódio não encontrado"));

    }

    public void topEpisode(int topX) {
        System.out.println("TOP " + topX + "\n");

        serieOptional.get().getEpisodios().stream()
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                .limit(topX)
                .forEach(System.out::println);
    }

    @SneakyThrows
    public void filterDate(int ano) {
        LocalDate dateBusca = LocalDate.of(ano, 1, 1);
        if (serieOptional.get().getTipo().contains("series")) {
            try {
                System.out.printf("espisodios a partir de %s:", ano);
                serieOptional.get().getEpisodios().stream().filter(e -> e.getDataLancamento() != null &&
                                e.getDataLancamento().isAfter(dateBusca))
                        .forEach(System.out::println);
            } catch (Exception e) {
                System.out.println("ano invalido");
            }
        }else {
            System.out.println("nao tem episodios pq é um filme");
        }
    }

    public void buscaEpisodePorNome(String title) {
        serieOptional.get().getEpisodios().stream()
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
        Optional<Serie> serieListOptional = serieList.stream()
                .filter(s -> s.getTitulo().equalsIgnoreCase(serie.getTitulo()))
                .findFirst();
        if (serieListOptional.isEmpty()) {
            serieRepository.save(serie);
            serieList = serieRepository.findAll();
        }
        serieOptional = serieRepository
                .findByTituloContainingIgnoreCase(getSerie().getTitulo());
    }

    protected void listaSeriesBancoDados() {

        serieList
                .stream()
                .sorted(Comparator.comparing(Serie::getGenero))
                .forEach(System.out::println);
    }

    protected void buscarSerieExistenteDB(String name){

        Optional<Serie> contains = serieList.stream()
                .filter(s -> s.getTitulo().equalsIgnoreCase(name)).findFirst();

        System.out.println((contains.isPresent()?
                contains.get():"Serie nao existe no banco de dados ainda"));
    }


}







