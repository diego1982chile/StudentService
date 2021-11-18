# StudentService
API Rest Students Paginados

# Setup
Se trata de un proyecto Spring Boot, por lo que se necesita

- Java JDK 8+
- Maven 3+
- Descargar el proyecto desde el repositorio y descomprimir
- Sincronizar las dependencias del proyecto. Dirigirse a la raiz del proyecto y ejecutar el siguiente comando:
```
     ojet restore
```
# Run

Ejecutar el siguiente comando para servir la aplicación utilizando node.js
```
    ojet serve
```
# Build

Opcionalmente el proyecto se puede empaquetar en un war (generado en la carpeta /dist) mediante el siguiente comando
```
    ojet build --release
```

# Nota

La URL de la api esta definida utilizando localhost, si se desea cambiar, modificar la variable contextPath en el
archivo src/js/appController.js
