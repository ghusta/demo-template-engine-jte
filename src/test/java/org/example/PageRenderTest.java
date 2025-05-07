package org.example;

import gg.jte.CodeResolver;
import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.TemplateOutput;
import gg.jte.output.StringOutput;
import gg.jte.resolve.DirectoryCodeResolver;
import org.example.model.Page;
import org.junit.jupiter.api.Test;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.assertj.core.api.Assertions.assertThat;

class PageRenderTest {

    @Test
    void searchJteRoot() {
        Path jteRootPath = Paths.get("src", "main", "jte", ".jteroot");
//        URL jteRootURL = PageRenderTest.class.getClassLoader().getResource(jteRootPath.toString().replace('\\', '/'));
        assertThat(jteRootPath).exists();
        System.out.println(".jteroot => " + jteRootPath);
    }

    @Test
    void renderPageExample() {
        Path jtePath = Paths.get("src", "main", "jte"); // should search from classpath ?
        Path precompiledTemplatesPath = Paths.get("target", "classes"); // templates precompiled by maven plugin

        // OPTION 1 : if templates are not precompiled first
        // i.e. developmentMode
        CodeResolver codeResolver = new DirectoryCodeResolver(jtePath); // This is the directory where your .jte files are located.
        // TemplateEngine templateEngine = TemplateEngine.create(codeResolver, ContentType.Html); // Two choices: Plain or Html

        // OPTION 2 : if templates are precompiled first
        // i.e. usePrecompiledTemplates
        TemplateEngine templateEngine = TemplateEngine.createPrecompiled(precompiledTemplatesPath, ContentType.Html); // Two choices: Plain or Html

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
            Path tempDir = Path.of("target", "test-data");
            if (!tempDir.toFile().exists()) {
                Files.createDirectory(tempDir);
            } else if (!tempDir.toFile().isDirectory()) {
                throw new IOException(tempDir + " is not a directory");
            }

            FileWriter fileWriter = new FileWriter(tempDir.resolve("test.html").toFile(), false);
            fileWriter.write(output.toString());
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
