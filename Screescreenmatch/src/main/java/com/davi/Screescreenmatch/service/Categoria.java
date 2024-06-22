package com.davi.Screescreenmatch.service;


public enum Categoria {
    ACAO("Action", "ação"),
    ROMANCE("Romance", "romance"),
    COMEDIA("Comedy", "comédia"),
    DRAMA("Drama", "drama"),
    CRIME("Crime", "crime"),
    Fantasy("Fantasy", "fantasia"),
    Musical("Musical", "musical"),
    Thriller("Thriller", "suspense"),
    SCI_FI("Sci-Fi", "ficção científica"),
    ADVENTURE("Adventure", "aventura"),
    ADVENTUR("Adventure", " aventura"),
    HORROR("Horror", "tacerror"),
    ANIMATION("Animation", "animação"),
    DOCUMENTARY("Documentary", "documentário"),
    MYSTERY("Mystery", "mistério"),
    WESTERN("Western", "velho oeste"),
    HISTORICAL("Historical", "histórico"),
    BIOGRAPHY("Biography", "biografia");

    private final String categoriaOmdb;

    private String categoriaPortugues;

    Categoria(String categoriaOmdb,String categoriaPortugues) {

        this.categoriaOmdb = categoriaOmdb;
        this.categoriaPortugues = categoriaPortugues;
    }

    public static Categoria fromString(String text) {

        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.toLowerCase().contains(text.toLowerCase())) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }
    public static Categoria fromStringPortugues(String text) {

        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaPortugues.contains(text.toLowerCase())) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }


}
