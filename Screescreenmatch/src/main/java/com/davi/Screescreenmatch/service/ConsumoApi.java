package com.davi.Screescreenmatch.service;

import com.davi.Screescreenmatch.model.DadosSerie;
import com.davi.Screescreenmatch.model.DadosTemporada;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ConsumoApi {

    public DadosSerie ObterDadosJsonTitulo(String titulo){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.omdbapi.com/?t=" + titulo.replace(" ", "+") + "&apikey=7e3f7c1a"))
                .build();
        HttpResponse<String> response;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("nome invalid");

            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("nome invalid");
            throw new RuntimeException(e);
        }
        String json = response.body();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return  mapper.readValue(json, DadosSerie.class);
        } catch (JsonProcessingException e) {
            System.out.println("Erro em gerar json");
            throw new RuntimeException(e);
        }
    }

    public String ObterDadosJsonEpisode(String titulo, Integer season, Integer episode)  {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.omdbapi.com/?t=" +
                        titulo.replace(" ", "+") + "&season=" + season +
                        "&episode=" + episode + "&apikey=7e3f7c1a"))
                .build();
        HttpResponse<String> response;

        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("nome invalid");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("input invalid");
            throw new RuntimeException(e);
        }
        String json = response.body();
        return json;
    }

    public DadosTemporada ObterDadosJsonSeason(String titulo, Integer season) {
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://www.omdbapi.com/?t=" +
                        titulo.replace(" ", "+") + "&season=" + season +
                        "&apikey=7e3f7c1a"))
                .build();
        HttpResponse<String> response;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("input invalid");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("input invalid");
            throw new RuntimeException(e);
        }
        String json = response.body();
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(json, DadosTemporada.class);
        } catch (JsonProcessingException e) {
            System.out.println("Erro em gerar json");
            throw new RuntimeException(e);
        }
    }

    public String ObterDadosJsonApi(String enderecoHttp)  {

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(enderecoHttp))
                .build();
        HttpResponse<String> response;
        try {
            response = client
                    .send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            System.out.println("input invalid");
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            System.out.println("input invalid");
            throw new RuntimeException(e);
        }
        String json = response.body();
        return json;
    }
}
