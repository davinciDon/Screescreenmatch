package com.davi.Screescreenmatch;

import com.davi.Screescreenmatch.model.DadosSerie;
import com.davi.Screescreenmatch.service.ConsumoApi;
import com.davi.Screescreenmatch.service.ConverteDados;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Application {

    public void app() throws Exception {
        ConsumoApi consumoApi = new ConsumoApi();
        ConverteDados converteDados = new ConverteDados();
        ObjectMapper mapper = new ObjectMapper();
        var json = consumoApi.ObterDadosJsonTitulo("lost");
        DadosSerie D = converteDados.obterDados(json, DadosSerie.class);
        DadosSerie X = mapper.readValue(json, DadosSerie.class);


     //   System.out.println(json);
        System.out.println(D);
        System.out.println(X);
    }
    }

