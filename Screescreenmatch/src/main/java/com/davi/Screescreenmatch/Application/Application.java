package com.davi.Screescreenmatch.Application;

import com.davi.Screescreenmatch.Repository.SerieRepository;
import com.davi.Screescreenmatch.model.Menu;

public class Application {
    public Application() {

    }

    public void app(SerieRepository serieRepository) {
        Menu menu = new Menu(serieRepository);
        menu.exibirMenu();

    }

}
