/*
 * 
 * ResultPageBean 
 * is part of Simple Organizer
 *
 *
 * Copyright (С) 2016 Nikolay Kirdin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License Version 3.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License Version 3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public 
 * License Version 3 along with this program.  If not, see 
 * <http://www.gnu.org/licenses/>.
 *
 */

package simpleorganizer.view;

import java.io.InputStream;
import java.io.Serializable;
import java.util.Properties;

import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import simpleorganizer.model.User;

@Named
@Singleton
@Startup
public class ResultPageBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "simpleorganizerPU")
    private EntityManager em;

    private boolean userTableGenerated = false;

    private String version;


    public void checkUserTable() {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        InputStream input = classLoader.getResourceAsStream("dbinit.properties");

        Properties properties = new Properties();
        try {
            properties.load(input);
            version = properties.getProperty("version");
            System.out.println("Simpleorganizer initDB(): version: " + version);
            System.out.println("Simpleorganizer initDB(): Check user table in simpleorganizerPU ...");
            System.out.println("Simpleorganizer initDB(): Retrieve \"admin\" account...");

            TypedQuery<User> query = em.createNamedQuery("User.getAllUsers",
                    User.class);
            User user = query.getSingleResult();
            System.out.println("Simpleorganizer initDB(): Users: " + user + " "
                    + em.contains(user));
            System.out.println("Simpleorganizer initDB(): Datasource \"simpleorganizerPU\" has \"admin\" account in User");
            userTableGenerated = true;
            System.out.println("Simpleorganizer initDB(): check ended successfully");
        } catch (Exception e) {
            System.err.println(
                    "Simpleorganizer initDB(): ERROR while retrieving \"admin\" account: " + e);
            System.err.println(
                    "Simpleorganizer initDB(): check failed");
            throw new RuntimeException(e);
        }
    }

    public boolean isUserTableGenerated() {
        return userTableGenerated;
    }

    public void setUserTableGenerated(boolean userTableGenerated) {
        this.userTableGenerated = userTableGenerated;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
