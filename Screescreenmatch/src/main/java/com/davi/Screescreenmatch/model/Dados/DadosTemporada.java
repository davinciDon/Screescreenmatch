package com.davi.Screescreenmatch.model.Dados;

import com.davi.Screescreenmatch.model.Dados.DadosEpisodio;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Title") String title,
                             @JsonAlias("Season") Integer numero,
                             @JsonAlias("Episodes") List<DadosEpisodio> episodios) {
    @Override
    public String toString() {
        return ("\n" + title() + " " + numero() + "° Season");
    }
    public void print(){
        System.out.println("\n" + title() + " " + numero() + "° Season\n");
        episodios().forEach(System.out::println);
    }

}


