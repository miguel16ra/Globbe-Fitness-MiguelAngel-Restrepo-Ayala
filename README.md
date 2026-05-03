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
- Crear la base de datos ejecutando los scripts SQL
- Conectar con JDBC
- Ejecutar el Main

## Estructura del repositorio
´miguel16ra/Globbe-Fitness-MiguelAngel-Restrepo-Ayala/
│
├── README.md (Raíz del proyecto)
│
├── 📁 Bases de Datos/
│   ├── README.md
│   ├── .$Diagrama Globbe-Fitness_MiguelAngel_Restrepo_Ayala.drawio.bkp
│   │
│   ├── 📁 Diagrama y Modelo Relacional/
│   │   ├── Diagrama Globbe-Fitness_MiguelAngel_Restrepo_Ayala.drawio
│   │   ├── Diagrama Globbe-Fitness_MiguelAngel_Restrepo_Ayala.drawio.png
│   │   └── Modelo_Relacional.png
│   │
│   └── 📁 Scripts SQL/
│       ├── Script_Creacion.pdf
│       ├── Script_Insertar.pdf
│       └── Script_Consultas.pdf
│
├── 📁 Entornos de Desarrollo/
│   └── README.md
│
├── 📁 Globbe-Fitness-APP/ (Aplicación Principal - Java con Maven)
│   ├── .gitignore
│   ├── README.md
│   ├── pom.xml (Configuración Maven)
│   ├── mvnw (Maven Wrapper para Linux/Mac)
│   ├── mvnw.cmd (Maven Wrapper para Windows)
│   ├── favoritos.txt
│   │
│   ├── 📁 .idea/ (Configuración IntelliJ IDEA)
│   ├── 📁 .mvn/ (Maven Wrapper)
│   │
│   └── 📁 src/
│       └── 📁 main/
│           ├── 📁 java/
│           │   ├── module-info.java
│           │   └── 📁 org/example/globbefitnessapp/
│           │       ├── HelloApplication.java
│           │       ├── Launcher.java
│           │       ├── MainPruebas.java
│           │       │
│           │       ├── 📁 controller/ (Controladores)
│           │       ├── 📁 dao/ (Data Access Objects)
│           │       ├── 📁 database/ (Conexión a BD)
│           │       └── 📁 model/ (Modelos de datos)
│           │
│           └── 📁 resources/
│               └── 📁 org/example/globbefitnessapp/
│                   ├── login-view.fxml (Vista de Login)
│                   │
│                   ├── 📁 admin/ (Vistas admin)
│                   ├── 📁 user/ (Vistas usuario)
│                   │
│                   ├── 📁 css/ (Estilos CSS)
│                   └── 📁 img/ (Imágenes)
│
├── 📁 IPE/ (Ingeniería de Proyectos Empresariales)
│   ├── README.md
│   │
│   └── 📁 Portfolio/
│       ├── Enlace.pdf
│       ├── index.html
│       ├── style.css
│       ├── app.js
│       │
│       └── 📁 images/
│           ├── S1.png
│           ├── S2.png
│           └── S3.png
│
├── 📁 Lenguaje de Marcas/
│   ├── README.md
│   │
│   ├── 📁 Prueba/
│   │   └── Screenshot 2026-05-02 025404.png
│   │
│   └── 📁 XML/
│       ├── reservas.xml
│       ├── reservas.xsd
│       └── reservas_invalido.xml
│
├── 📁 MPO/ (Módulo)
│   └── README.md
│
├── 📁 Programacion/
│   └── README.md
│
└── 📁 Sistemas Informaticos/
    ├── README.md
    │
    └── 📁 Funcionamiento/
        └── Prueba Globbe.mp4
´
