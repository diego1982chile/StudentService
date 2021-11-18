# StudentService
API Rest Students Paginados

# Setup
Se trata de un proyecto Spring Boot, por lo que se necesita

- Java JDK 8+
- Maven 3+
- Descargar el proyecto desde el repositorio y descomprimir
- Sincronizar las dependencias del proyecto. Dirigirse a la raiz del proyecto y ejecutar el siguiente comando:
```
     mvn clean install
```
# Run

Ejecutar el siguiente comando para servir la aplicación utilizando tomcat embebido en la aplicación
```
    ./mvnw spring-boot run
```
# Build

Para empaquetado war del proyect
```
    mvn clean package
```
# Nota

Si se va a servir la aplicación utilizando el tomcat embebido, comentar el scope en la dependencia, para que se incluya
el servidor dentro de la aplicación

     <dependency>
          <groupId>org.springframework.boot</groupId>
          <artifactId>spring-boot-starter-tomcat</artifactId>
          <!--scope>provided</scope-->
     </dependency>
