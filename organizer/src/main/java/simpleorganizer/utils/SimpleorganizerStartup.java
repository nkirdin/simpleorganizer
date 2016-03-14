package simpleorganizer.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.EJBException;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;

@Named
@Singleton
@Startup
public class SimpleorganizerStartup {

    private String version;

    @PostConstruct
    private void startUp() {

        ClassLoader classLoader = Thread.currentThread()
                .getContextClassLoader();
        InputStream input = classLoader
                .getResourceAsStream("organizer.properties");

        Properties properties = new Properties();

        try {
            properties.load(input);
        } catch (IOException ioe) {
            System.err.println(
                    "Simpleorganizer SimpleorganizerStartup startUp(): simpleorganizer: "
                            + " hasn't started.");

            throw new EJBException(ioe);
        }

        version = properties.getProperty("version");

        System.out.println(
                "Simpleorganizer SimpleorganizerStartup startUp(): simpleorganizer version: "
                        + version + " starting...");
    }

    @PreDestroy
    private void shuttingDown() {
        System.out.println(
                "Simpleorganizer SimpleorganizerStartup shuttingDown(): simpleorganizer version: "
                        + version + " is going down");

    }

    public String getVersion() {
        return version;
    }
}
