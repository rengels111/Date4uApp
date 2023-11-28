package com.udemy.date4u;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;

// Erstes Beispiel für eine Bean-Factory:
// 1. Fabrik-Klasse mit @Configuration annotieren:
@Configuration
public class AppUuidConfig {

    private final Logger log = LoggerFactory.getLogger( getClass() );

    // 2. Eine Methode mit @Bean annotieren:
    @Bean
    String appUuid() {
        String uuid = UUID.randomUUID().toString();
        log.info( "uuid -> {}", uuid );
        return uuid;
    }

    // Wir generieren eine zweite Bean, die jedoch die appUuid Methode vom oberen Bean nochmal anwendet.
    // Wir schauen mal was dabei rauskommt:
    @Bean
    String shorterAppUuid() {
        String uuid = appUuid().substring(0, appUuid().length() / 2 );
        log.info( "uuid -> {}", uuid );
        return uuid;
        // Wir haben in der Konsole die Log-Ausgabe beim Start gesehen, dass genau der gleiche Algorithmus wiedergegeben
        // wurde, nur um die Hälfte gekürzt.
        // Beim ClassObjekt wird im Log nicht die Klasse AppUuidConfig angezeigt, sondern ein Stellvertreter, ein
        // Proxy-Objekt. Spring baut eine Unterklasse von AppUuidConfig, überschreibt diese Methoden und wenn die
        // Methoden mehrfach aufgerufen werden, dann wird immer dieselbe Instanz zurückgegeben.
        // Der Proxy lässt sich auch deaktivieren, indem man oben neben @Configuration schreibt:
        // @Configuration(proxyBeanMethods = false)
    }

}
