package org.example;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class PageRenderTest {

    @Test
    void searchJteRoot() {
        Path jteRootPath = Paths.get("templates", "jte", ".jteroot");
        URL jteRootURL = PageRenderTest.class.getClassLoader().getResource(jteRootPath.toString().replace('\\', '/'));
        assertThat(jteRootURL).isNotNull();
        System.out.println(".jteroot => " + jteRootURL);
    }

    @Test
    void renderPageExample() {
        Path jtePath = Paths.get("src", "main", "jte"); // should search from classpath ?
        CodeResolver codeResolver = new DirectoryCodeResolver(jtePath); // This is the directory where your .jte files are located.
        TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html); // Two choices: Plain or Html
        templateEngine.setTrimControlStructures(true);

        Page page = Page.builder()
                .title("Hello")
                .description("Prints Hello")
                .build();

        TemplateOutput output = new StringOutput();
        templateEngine.render("example.jte", page, output);

        assertThat(output.toString()).isNotEmpty();
        System.out.println(output);

        try {
            FileWriter fileWriter = new FileWriter(Path.of("temp", "test.html").toFile());
            fileWriter.write(output.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
