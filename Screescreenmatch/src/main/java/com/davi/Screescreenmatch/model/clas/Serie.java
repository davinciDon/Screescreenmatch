package com.davi.Screescreenmatch.model.clas;

import com.davi.Screescreenmatch.model.Dados.DadosSerie;
import com.davi.Screescreenmatch.service.Categoria;
import com.davi.Screescreenmatch.service.ConsultaChatGPT;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;

@Getter
@Setter
@Entity
@Table(name = "series")
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
    @Transient
    private List<Episodio> episodios = new ArrayList<>();

    public Serie(){}

    public Serie(DadosSerie dadosSerie) {
        setTitulo(dadosSerie.titulo());
        setTotalTemporadas(dadosSerie.totalTemporadas());
        setAvaliacao(OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0));
        setGenero(Categoria.fromString(dadosSerie.genero().split(",")[0].trim()));
        setAtores(dadosSerie.atores());
        setPoster(dadosSerie.poster());
        setSinopse(ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim());
    }

    @Override
    public String toString() {

        return "\nGenero: " + getGenero() + "\n" +
                "Titulo: " + getTitulo() + "\n" +
                "Nº temporadas: " + getTotalTemporadas() + "\n" +
                "Avaliação: " + getAvaliacao() + "\n" +
                "Atores: " + getAtores() + "\n" +
                "Sinopse: " + getSinopse() + "\n" +
                getPoster();
    }
}

