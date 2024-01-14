package com.davi.Screescreenmatch.model.Dados;

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
        String date = null;
        try {
        data = LocalDate.parse(dataLancamento);
        date = data.format(format);
        } catch (DateTimeParseException e) {
           System.out.println(e);
        }
        return date + "| N° "+ numeroEp +" | Title: " + titulo + " Nota: " + avaliacao+"\n";
    }
}
