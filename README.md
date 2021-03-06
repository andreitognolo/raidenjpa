Continuous Integration status
=========

[![Build Status](https://snap-ci.com/andreitognolo/raidenjpa/branch/master/build_image)](https://snap-ci.com/andreitognolo/raidenjpa/branch/master)

Raiden JPA
=========

A really fast JPA subset implementation to be use in development environments. 

STOP using mocks to improve your tests velocity, START using RaidenJPA.

"if it (test) takes ten seconds just to start testing, toss it!" by Uncle Bob - bit.ly/WbZTTU

Hibernate vs Raiden JPA
=========

(55 sec) https://www.youtube.com/watch?v=BPrVga6dRRA

Is it ready to use?
=========

Currently, there are already two (private) projects using it. Although it is not complete, you can try it in your project without fear, because its intention is not go to production, just help you in development environment. You can see issues in GitHub.

Do you want help to put it on your project? Send me an email: andreitognolo@gmail.com

Getting Started
=========
    
Dependency:

    <dependency>
        <groupId>org.raidenjpa</groupId>
        <artifactId>raidenjpa-core</artifactId>
        <version>0.0.4</version>
    </dependency>
    
In your code:

    // As soon as possible, it will be able to use <provider> in persistence.xml
    EntityManagerFactory emf = new RaidenEntityManagerFactory();

Development
=========

## @Before

You need Java 7+ and maven 3+

## Compile, run tests and eclipse

    $ # Compile 
    $ mvn clean install
    
    $ # Run tests
    $ mvn test (run tests using Raiden)
    $ mvn test -Dhibernate (run the same tests, but using Hibernate)

    $ # Configure eclipse files
    $ mvn eclipse:clean eclipse:eclipse
