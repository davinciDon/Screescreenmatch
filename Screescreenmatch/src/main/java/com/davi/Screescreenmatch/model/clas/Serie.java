package com.davi.Screescreenmatch.model.clas;

import com.davi.Screescreenmatch.model.Dados.DadosEpisodio;
import com.davi.Screescreenmatch.model.Dados.DadosSerie;
import com.davi.Screescreenmatch.model.Dados.DadosTemporada;
import com.davi.Screescreenmatch.service.Categoria;
import com.davi.Screescreenmatch.service.ConsultaChatGPT;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import java.util.stream.Collectors;

@Getter
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "serie")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private int totalTemporadas;
    private Double avaliacao;
   @Enumerated(EnumType.STRING)
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;
    private String tipo;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie() {
    }

    public Serie(DadosSerie dadosSerie, List<DadosTemporada> dadosTemporadas) {
        setTitulo(dadosSerie.titulo());
        setTotalTemporadas(dadosSerie.totalTemporadas());
        setAvaliacao(OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0));
       // if (dadosSerie.genero().toLowerCase() == "adventure"){
       //     setGenero(Categoria.ACAO);}
       // else{
        setGenero(Categoria.fromString(dadosSerie.genero().split(",")[0].trim()));
        setAtores(dadosSerie.atores());
        setPoster(dadosSerie.poster());
      //  setSinopse(ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim());
        setSinopse(dadosSerie.sinopse());
        setTipo(dadosSerie.tipo());
        setEpisodios(dadosTemporadas
                .stream()
                .flatMap(t -> t.episodios().stream()
                        .map(e -> new Episodio(e, t.numero()))
                ).collect(Collectors.toList()));
    }

    private void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        this.episodios = episodios;
    }

    @Override
    public String toString() {

        return //"\nGenero: " + getGenero() + "\n" +
                "Titulo: " + getTitulo() + "\n" +
                "Nº temporadas: " + getTotalTemporadas() + "\n" +
                "Avaliação: " + getAvaliacao() + "\n" +
                "Atores: " + getAtores() + "\n" +
                "Sinopse: " + getSinopse() + "\n" +
                "Tipo: " + getTipo() + "\n" +
                getPoster();
    }
}

