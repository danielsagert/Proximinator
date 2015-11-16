package de.ffb.proximinator;

import de.ffb.proximinator.web.WebServer;
import de.ffb.proximinator.web.Website;

import java.io.IOException;

public class StartUp {
    public static void main(String[] args) {
        try {
            new WebServer(Website.BEAMERWAND).start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
