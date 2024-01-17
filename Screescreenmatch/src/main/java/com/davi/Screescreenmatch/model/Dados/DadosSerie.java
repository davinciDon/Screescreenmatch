package com.davi.Screescreenmatch.model.Dados;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.List;
import java.util.Objects;


@JsonIgnoreProperties(ignoreUnknown = true)

public record DadosSerie(
        @JsonAlias("Title") String titulo,
        @JsonAlias("totalSeasons") Integer totalTemporadas,
        @JsonAlias("imdbRating") String avaliacao,
        @JsonAlias("Genre") String genero,
        @JsonAlias("Actors") String atores,
        @JsonAlias("Poster") String poster,
        @JsonAlias("Plot") String sinopse) {

    @Override
    public Integer totalTemporadas() {
        return Objects.requireNonNullElse(totalTemporadas, 0);
    }

    public String toString() {
        return " \nTitulo: " + titulo() + "\n" +
                "Nº temporadas: " + totalTemporadas() + "\n" +
                "Avaliação: " + avaliacao() + "\n" +
                "Genero: " + genero() + "\n" +
                "Atores: " + atores() + "\n" +
                "Sinopse: " + sinopse() + "\n" +
                poster();

    }
}
