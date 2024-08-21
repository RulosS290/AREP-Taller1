# Taller diseño y estructuración de aplicaciones distribuidas en internet

## Descripción

Servidor web que soporte múlltiples solicitudes seguidas. Además de leer los archivos del disco local y retornar todos los archivos solicitados, incluyendo archivos como html, JavaScript, CSS e imágenes. Construido como una aplicación de comunicacioón asíncronica con unos servicios REST sin el uso de frameworks.

## Empezando

Estas instrucciones le permitirán obtener una copia del proyecto en funcionamiento en su máquina local para fines de desarrollo y prueba. Consulte la sección Implementación para obtener notas sobre cómo implementar el proyecto en un sistema en vivo.

### Requisitos

* [Git](https://git-scm.com/) - Control de versiones
* [Maven](https://maven.apache.org/) - Manejador de dependencias
* [Java](https://www.oracle.com/java/technologies/downloads/#java17) - Lenguaje de programación

* ### Instalación 🔧

Realice los siguientes pasos para clonar el proyecto en su máquina local.

```bash
git clone https://github.com/RulosS290/AREP-Taller1.git
```

## Ejecutando la aplicación ⚙️

Para ejecutar la aplicación, ejecute el siguiente comando:

```bash
mvn clean compile exec:java '-Dexec.mainClass=com.mycompany.servicerest.ServiceREST'
```

El anterior comando limpiará las contrucciones previas, compilará y empaquetará el código en un jar y luego ejecutará la aplicación.

Diríjase a su navegador de preferencia y vaya a la siguiente dirección: http://localhost:8080/index.html para ver la aplicación en funcionamiento.

## Pruebas

Se muestra el funcionamiento del Servicio Get-REST

* Llamos a archivo JavaScript,CSS y txt viendo el llamado GET en la consola y su estado 200.

![imagen](https://github.com/user-attachments/assets/79972590-e04a-4b82-b9e5-416123756ec3)

![imagen](https://github.com/user-attachments/assets/e0c8c3cb-ce9a-444b-89b8-8e68ffb8e6d8)

![imagen](https://github.com/user-attachments/assets/96b1599c-c6f4-4851-bf4e-eb66885026c6)




