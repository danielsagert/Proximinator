package de.ffb.proximinator.interceptor;

import de.ffb.proximinator.web.Website;

import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JSInjector extends Interceptor {
    public JSInjector(Website website, String content) {
        super(website, content);
    }

    @Override
    public String intercept() {
        try {
            for (Path file : getPermanent()) {
                String script = new String(Files.readAllBytes(file));
                content = content.replace("</script>", "</script><script>" + script + "</script>");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return content;
    }

    private List<Path> getPermanent() throws Exception {
        String dirName = "js";
        Path path = null;
        Path pathWorkingDir = Paths.get(dirName);
        Path pathResource = Paths.get(ClassLoader.getSystemResource(dirName).toURI());

        if (Files.exists(pathWorkingDir)) {
            path = pathWorkingDir;
        } else if (Files.exists(pathResource)) {
            path = pathResource;
        }


        List<Path> files = new ArrayList<>();

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(path,
                entry -> !Files.isDirectory(entry)
                        && entry.getFileName().startsWith("permanent")
                        && entry.endsWith(".js"))) {
            stream.forEach(files::add);
        }

        return files;
    }
}
