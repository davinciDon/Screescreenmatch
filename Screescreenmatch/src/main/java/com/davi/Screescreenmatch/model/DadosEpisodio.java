package com.davi.Screescreenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodio(@JsonAlias("Title") String titulo,
                            @JsonAlias("Episode") Integer numeroEp,
                            @JsonAlias("imdbRating") String avaliacao,
                            @JsonAlias("Released") String dataLancamento) {

    @Override
    public String toString() {
        DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate data;
        String d = null;
        try {
        data = LocalDate.parse(dataLancamento);
        d = data.format(format);
        } catch (DateTimeParseException ex) {
            data = null;
        }
        return d + "| N° "+ numeroEp +" | Title: " + titulo + " Nota: " + avaliacao+"\n";
    }
}
