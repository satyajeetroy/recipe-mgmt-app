# Read Me First
Recipe Management is a standalone application built using spring boot. It uses H2 database for storing recipes onto filesystem. H2 database can be configured to store data in-memory as well.  
  
Recipe Management application allows adding, updating, removing and fetching recipes. Additionally, users can also use filters to refine their search based on various criteria. Like:  
Dish Type. eg. Veg/Non-Veg  
Servings. eg. 2  
Specific Ingredients. eg. Cheese  
Text Search. 
- All vegetarian recipes  
- Recipes that can serve 4 persons and have “potatoes” as an ingredient  
- Recipes without “salmon” as an ingredient that has “oven” in the instructions  


# OpenAPI Docs
Recipe Management application has exposed various rest api's for interaction with H2 database. The open api document can be accessed from below url. The api mostly consists of CRUD operations onto repository.

http://localhost:8080/v3/api-docs/

# Accessing Swagger UI
Spring doc openapi internally uses swagger. You can use the below link to access swagger.  

http://localhost:8080/swagger-ui/index.html

# Accessing H2 DB UI
From application.properties, h2 ui can be enabled or disabled. Please set the below two properties enabling and accessing the ui.  
  
spring.h2.console.enabled=true  
spring.h2.console.path=/h2-ui  

Once enabled they can be accessed using below url. Default username is sa and password is blank. password property gets overwritten once specified in application properties file.

http://localhost:8080/h2-ui  
username: sa  
password: password  

# Maven Tests
To skip all tests use below command.  
mvn clean install -DskipTests  
  
To skip integration test use below command.  
mvn clean install -DskipITs  
  
To run integration test use below command.  
mvn install -Dskip.it=false  
  
# Running the application  
To run the application you can execute the java command in below format to start the application.  
  
For WINDOWS  
$JAVA_HOME/java -jar {JAR_LOCATION}/recipe.jar com.roys.recipemgmt.RecipeMgmtApplication  
eg.   
java -jar recipe.jar com.roys.recipemgmt.RecipeMgmtApplication  

For LINUX  
$JAVA_HOME/java -jar {JAR_LOCATION}/recipe.jar com.roys.recipemgmt.RecipeMgmtApplication &  
eg.  
java -jar recipe.jar com.roys.recipemgmt.RecipeMgmtApplication &  


