# presenter-account-api

### Overview
An api used for creating presenter accounts. A presenter account is needed to file via the XMLGW.

### Requirements
In order to run the service locally you will need the following:
- [Java 21](https://www.oracle.com/java/technologies/downloads/#java21)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)

### Getting started
To checkout and build the service:
1. Clone [Docker CHS Development](https://github.com/companieshouse/docker-chs-development) and follow the steps in the README.
2. Run `chs-dev modules enable presenter-account-api`
3. Run either `chs-dev development enable presenter-account-api` or use the jib plugin to replace your local image
4. Run docker using `chs-dev up` in the `docker-chs-development` directory.

These instructions are for a local docker environment.

### Endpoints

The full path for each public endpoints:
`${API_URL}/presenter-account/`

Method    | Path                                                          | Description
:---------|:--------------------------------------------------------------|:-----------
**GET**   | `/healthcheck`                                                | Returns `HTTPS.OK` which means api is up and runing
