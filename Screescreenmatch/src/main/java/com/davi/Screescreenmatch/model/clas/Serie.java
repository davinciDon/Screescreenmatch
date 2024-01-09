package com.davi.Screescreenmatch.model.clas;

import com.davi.Screescreenmatch.model.Dados.DadosSerie;
import com.davi.Screescreenmatch.service.Categoria;
import lombok.Getter;
import lombok.Setter;

import java.util.OptionalDouble;

@Getter
@Setter
public class Serie {

    private String titulo;
    private Integer totalTemporadas;
    private Double avaliacao;
    private Categoria genero;
    private String atores;
    private String poster;
    private String sinopse;

    public Serie(DadosSerie dadosSerie) {
        setTitulo(dadosSerie.titulo());
        setTotalTemporadas(dadosSerie.totalTemporadas());
        setAvaliacao(OptionalDouble.of(Double.parseDouble(dadosSerie.avaliacao())).orElse(0));
        setGenero(Categoria.fromString(dadosSerie.genero().split(",")[0].trim()));
        setAtores(dadosSerie.atores());
        setPoster(dadosSerie.poster());
        //setSinopse(ConsultaChatGPT.obterTraducao(dadosSerie.sinopse()).trim());
    }

    @Override
    public String toString() {
        return " \nTitulo: " + getTitulo() + "\n" +
                "Nº temporadas: " + getTotalTemporadas() + "\n" +
                "Avaliação: " + getAvaliacao() + "\n" +
                "Genero: " + getGenero() + "\n" +
                "Atores: " + getAtores() + "\n" +
                "Sinopse: " + getSinopse() + "\n" +
                getPoster();

    }
}

