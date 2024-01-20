package com.davi.Screescreenmatch.model.clas;


import com.davi.Screescreenmatch.model.Dados.DadosEpisodio;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.OptionalDouble;

@Getter
@Setter(AccessLevel.PRIVATE)

@Entity
@Table(name = "episodio")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter(AccessLevel.PROTECTED)
    @ManyToOne
    private Serie serie;
    private Integer temporada;
    private String titulo;
    private Integer numeroEp;
    private double avaliacao;
    private LocalDate dataLancamento;
    public  Episodio(){}
    public Episodio(DadosEpisodio dadosEpisodio,Integer temporada) {

        this.setTemporada(temporada);
        this.setTitulo(dadosEpisodio.titulo());
        this.setNumeroEp(dadosEpisodio.numeroEp());
        try {
            this.setAvaliacao(OptionalDouble.of(Double.parseDouble(dadosEpisodio.avaliacao())).orElse(0));
        } catch (NumberFormatException ex) {
            this.setAvaliacao(0);
        }
        try {
            this.setDataLancamento(LocalDate.parse(dadosEpisodio.dataLancamento()));
        } catch (DateTimeParseException ex) {
            this.setDataLancamento(null);
        }
    }

    @Override
    public String toString() {
        return getDataLancamento() +
                " | N° " + numeroEp + " | Title: " +
                titulo + " Nota: " + avaliacao + "\n";
    }

}

