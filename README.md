# Social Media Project

![CI](https://github.com/Maxwell-lt/social-media-project/workflows/CI/badge.svg)

Case study project for Per Scholas using Spring Boot, Bootstrap, JPA, Maven, Hibernate, and more

### Set up instructions (step 1)
#### Windows (Powershell)
    git clone https://github.com/Maxwell-lt/social-media-project.git
    cd social-media-project
    cat ./database-schema.sql | mysql -u root -p
    mkdir images
    echo "maxwell_lt.socialmediaproject.resourceroot=$((Resolve-Path -Path .\images).ToString().Replace('\', '\\'))" > .\src\main\resources\application-local.properties

Path escaping here only works with backslashes, will probably fail on paths with spaces.

#### Linux (Bash)
    git clone https://github.com/Maxwell-lt/social-media-project.git
    cd social-media-project
    cat ./database-schema.sql | mysql -u root -p
    mkdir images
    echo "maxwell_lt.socialmediaproject.resourceroot=`readlink -f ./images`" > src/main/resources/application-local.properties

### Set up instructions (step 2)
Modify database username and password in `src/main/resources/application.properties`

### Run tests
    ./mvnw test

### Start server
    ./mvnw spring-boot:run
