package de.ffb.proximinator.web;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import de.ffb.proximinator.interceptor.Interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.URL;

public class WebsiteHandler implements HttpHandler {
    public static final String LINE_BREAK = "\n";
    private final Website website;

    public WebsiteHandler(Website website) {
        this.website = website;
    }

    private String getContent() {
        System.out.println("Parse website: " + website.getUrl());

        StringBuilder content = new StringBuilder();

        try (InputStream is = new URL(website.getUrl()).openStream();
             BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            String line;

            while ((line = br.readLine()) != null) {
                content.append(line).append(LINE_BREAK);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return content.toString();
    }

    private String intercept(String content) {
        for (Class<? extends Interceptor> interceptorClass : website.getInterceptorStack()) {
            try {
                Constructor<? extends Interceptor> constructor = interceptorClass.getDeclaredConstructor(Website.class, String.class);
                Interceptor interceptor = constructor.newInstance(website, content);
                content = interceptor.intercept();
            } catch (Exception e) {
                System.out.println("Exception while using " + interceptorClass + " interceptor");
                e.printStackTrace();
            }
        }

        return content;
    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        System.out.printf("Handle request %s from %s%n", httpExchange.getRequestURI(), httpExchange.getRemoteAddress());

        String content = getContent();
        content = intercept(content);

        httpExchange.sendResponseHeaders(200, content.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(content.getBytes());
        os.close();
    }
}
