package com.davi.Screescreenmatch.service;


public enum Categoria {
    ACAO("Action"),
    Adventure("Adventure"),
    COMEDIA("Comedy"),
    CRIME("Crime"),
    DRAMA("Drama"),
    Fantasy("Fantasy"),
    ROMANCE("Romance"),
    Thriller("Thriller");

    private final String categoriaOmdb;

    Categoria(String categoriaOmdb) {
        this.categoriaOmdb = categoriaOmdb;
    }

    public static Categoria fromString(String text) {

        for (Categoria categoria : Categoria.values()) {
            if (categoria.categoriaOmdb.equalsIgnoreCase(text)) {
                return categoria;
            }
        }
        throw new IllegalArgumentException("Nenhuma categoria encontrada para a string fornecida: " + text);
    }


}
