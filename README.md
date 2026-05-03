# Globbe Fitness - Miguel Ángel Restrepo Ayala

Proyecto intermodular de 1ºDAM basado en el desarrollo de una app de escritorio para la gestión de un gimnasio

## Descripcion
La aplicación está orientada a la gestión de socios, clases y reservas, una tienda con control de stock y planes mensuales y ofertas del gimnasio.

## Objetivo
Desarrollar una aplicación funcional en JavaFX conectada a base de datos mediante JDBC, integrando los contenidos vistos en todas las asignaturas de 1ºDAM.

## Tecnologías usadas
- Java
- JavaFX
- JDBC
- MariaDB (XAMPP)
- XML y XSD
- HTML y CSS

## Herramientas de desarrollo
- IntelliJ
- Visual Studio Code
- Git y GitHub

## Instrucciones de instalación/ejecución
- Clonar el repositorio
- Abrir el proyecto en IntelliJ
- Abrir XAMPP y darle a start en Apache y MySQL
- Crear la base de datos ejecutando los scripts SQL
- Ejecutar el Launcher en IntelliJ

## Estructura del repositorio

```
Globbe-Fitness-MiguelAngel-Restrepo-Ayala/
├── README.md
├── Bases de Datos/
│   ├── README.md
│   ├── Diagrama y Modelo Relacional/
│   │   ├── Diagrama Globbe-Fitness_MiguelAngel_Restrepo_Ayala.drawio
│   │   ├── Diagrama Globbe-Fitness_MiguelAngel_Restrepo_Ayala.drawio.png
│   │   └── Modelo_Relacional.png
│   └── Scripts SQL/
│       ├── Script_Creacion.pdf
│       ├── Script_Insertar.pdf
│       └── Script_Consultas.pdf
├── Entornos de Desarrollo/
│   └── README.md
├── Globbe-Fitness-APP/
│   ├── README.md
│   ├── pom.xml
│   └── src/
│       └── main/
│           ├── java/
│           │   └── org/example/globbefitnessapp/
│           │       ├── HelloApplication.java
│           │       ├── Launcher.java
│           │       ├── MainPruebas.java
│           │       ├── controller/
│           │       ├── dao/
│           │       ├── database/
│           │       └── model/
│           └── resources/
│               └── org/example/globbefitnessapp/
│                   ├── login-view.fxml
│                   ├── admin/
│                   ├── user/
│                   ├── css/
│                   └── img/
├── IPE/
│   └── Portfolio/
│       ├── Enlace.pdf
│       ├── index.html
│       ├── style.css
│       ├── app.js
│       └── images/
│           ├── S1.png
│           ├── S2.png
│           └── S3.png
├── Lenguaje de Marcas/
│   └── XML/
│       ├── reservas.xml
│       ├── reservas.xsd
│       └── reservas_invalido.xml
├── MPO/
│   └── README.md
├── Programacion/
│   └── README.md
└── Sistemas Informaticos/
    └── README.md
```
