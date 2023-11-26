package com.udemy.date4u;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Date4uApplication {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Date4uApplication.class, args);

        for (String name : ctx.getBeanDefinitionNames()) {
            System.out.println(name);
        }


        // Wir können aus ctx direkt unsere FileSystem Klasse herausholen.
        // mit getBean rufen wir unsere Klasse auf und speichern sie in fileSystem.
        // FileSystem fileSystem = ctx.getBean(FileSystem.class);
        // Nun können wir mit der Variable z.B. die Methode getFreeDiskSpace() aufrufen.
        // Die Methode DataSize wandelt die Bytes um, toGigabyte gibt die Größe in Gigabyte aus.
        //System.out.printf("%d GB%n", DataSize.ofBytes(fileSystem.getFreeDiskSpace()).toGigabytes());
    }
}