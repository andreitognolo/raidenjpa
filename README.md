Raiden JPA
=========

A implementation of JPA subset specification which main goal is be really fast in development environments.

Getting Started
=========

Repository (it is not in maven central yet):

    <repositories>
        <repository>
            <id>raidenjpa</id>
            <url>http://storage.googleapis.com/raiden-jpa</url>
        </repository>
    </repositories>
    
Dependency:

    <dependency>
        <groupId>org.raidenjpa</groupId>
        <artifactId>raidenjpa-core</artifactId>
        <version>0.0.1</version>
    </dependency>
    
In your code:

    // As soon as possible, it will be able to use <provider> in persistence.xml
    EntityManagerFactory emf = new RaidenEntityManagerFactory();

Development
=========

Build Status (by Travis):

[![Build Status](https://travis-ci.org/andreitognolo/raidenjpa.png)](http://travis-ci.org/andreitognolo/raidenjpa)

## @Before

You need Java 7+ and maven 3+

If you dont have maven3+ you can get it calling "ant prepare" && "source env/dextra/env.sh"
