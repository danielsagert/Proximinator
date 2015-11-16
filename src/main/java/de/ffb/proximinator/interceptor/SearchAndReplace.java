package de.ffb.proximinator.interceptor;

import de.ffb.proximinator.web.Website;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class SearchAndReplace extends Interceptor {
    public SearchAndReplace(Website website, String content) {
        super(website, content);
    }

    @Override
    public String intercept() {
        content = staticReplace();
        content = fileReplace();

        return content;
    }

    private String staticReplace() {
        content = content.replace(" src=\"", " src=\"" + website.getDomain() + "/");
        content = content.replace(" src='", " src='" + website.getDomain() + "/");
        content = content.replace(" href=\"", " href=\"" + website.getDomain() + "/");
        content = content.replace(" href='", " href='" + website.getDomain() + "/");
        return content;
    }

    private String fileReplace() {
        try {
            String fileName = getClass().getSimpleName() + ".txt";
            Path path = null;
            Path pathWorkingDir = Paths.get(fileName);
            Path pathResource = Paths.get(ClassLoader.getSystemResource(fileName).toURI());

            if (Files.exists(pathWorkingDir)) {
                path = pathWorkingDir;
            } else if (Files.exists(pathResource)) {
                path = pathResource;
            }

            if (path != null) {
                List<String> lines = Files.readAllLines(path, Charset.defaultCharset());

                for (String line : lines) {
                    String[] splitted = line.split("\\|\\|");
                    content = content.replace(splitted[0], splitted[1]);
                    System.out.println("replace: " + line);
                }
            } else {
                System.out.println(fileName + " not found");
            }
        } catch (Exception e) {
            System.out.println("Exception while replacing be file");
            e.printStackTrace();
        }

        return content;


    }
}
