package com.davi.Screescreenmatch.model.clas;


import com.davi.Screescreenmatch.model.Dados.DadosEpisodio;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.OptionalDouble;

@Getter
@Setter(AccessLevel.PRIVATE)

public class Episodio {

    private Integer temporada;
    private String titulo;
    private Integer numeroEp;
    private double avaliacao;
    private LocalDate dataLancamento;
    String data;
    DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public Episodio(Integer numero, DadosEpisodio dadosEpisodio) {

        this.setTemporada(numero);
        this.setTitulo(dadosEpisodio.titulo());
        this.setNumeroEp(dadosEpisodio.numeroEp());
        try {
            this.setAvaliacao(OptionalDouble.of(Double.valueOf(dadosEpisodio.avaliacao())).orElse(0));
        } catch (NumberFormatException ex) {
            this.setAvaliacao(0);
        }
        try {
            this.setDataLancamento(LocalDate.parse(dadosEpisodio.dataLancamento()));
            this.setData(getDataLancamento().format(format));
        } catch (DateTimeParseException ex) {
            this.setDataLancamento(null);
        }
    }
    @Override
    public String toString() {
        return "Season: " + getTemporada() + "\n"+
                getData()+
                " | N° " + numeroEp + " | Title: " +
                titulo + " Nota: " + avaliacao + "\n";
    }

}

