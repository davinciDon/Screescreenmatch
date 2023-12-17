package com.davi.Screescreenmatch.model;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

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
    public Episodio(Integer numero, DadosEpisodio d) {

        this.setTemporada(numero);
        this.setTitulo(d.titulo());
        this.setNumeroEp(d.numeroEp());
        try {
            this.setAvaliacao(Double.parseDouble(d.avaliacao()));
        } catch (NumberFormatException ex) {
            this.setAvaliacao(0);
        }
        try {
            this.setDataLancamento(LocalDate.parse(d.dataLancamento()));
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

