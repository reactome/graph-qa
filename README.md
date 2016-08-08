<!--![Logo](https://cdn.evbuc.com/images/3621635/40070539972/1/logo.png)-->

# Reactome Graph Database Quality Assurance Module

## What is the Reactome Graph Quality Assurance Module

The QA module is used to point out data inconsistencies of the Reactome Graph Database. The module acts as a collection of tests performed against the database using the graph core library. Depending on the outcome of each test a report will be created, collecting suspicious entries. Since data in the graph database is dependent on the manually curated data annotated in the Reactome MySQL database files should be forwarded to Reactome curators 

#### Project components used:

* Reactome graph library 

#### Project usage: 

The QA-module can be executed by running the executable jar file. Please ensure that Neo4j database is running and correct properties are specified.

**Properties**

When executing the jar file following properties have to be set.

    -h  Reactome Neo4j host. DEFAULT: localhost
    -b  Reactome Neo4j port. DEFAULT: 7474
    -u  Reactome Neo4j user. DEFAULT: neo4j
    -p  Reactome Neo4j password. DEFAULT: neo4j
    -v  Verbose output 