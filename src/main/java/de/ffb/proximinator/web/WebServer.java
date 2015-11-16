package de.ffb.proximinator.web;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;

public class WebServer {
    public static final int PORT = 8000;
    private final Website website;

    public WebServer(Website website) {
        this.website = website;
    }

    public void start() throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/" + website.getContext(), new WebsiteHandler(website));
        server.setExecutor(null);
        server.start();

        System.out.printf("Webserver started: %s:%s/%s%n", "http://localhost", PORT, website.getContext());
        System.out.printf("Original website: %s%n", website.getUrl());
    }
}
