# Informe técnico de entorno de ejecución de Globbe Fitness

## Descripción general
Globbe Fitness es una aplicación de escritorio orientada a la gestión de un gimnasio, con funcionalidades relacionadas con usuarios, socios, clases, reservas y productos, conectada a una base de datos relacional mediante JDBC.
En los archivos del proyecto se observa una implementación Java con conexión a MySQL, uso de JavaFX para la interfaz y gestión local del entorno de desarrollo.

### Tipo de sistema de ejecución
El entorno más adecuado para esta aplicación es un PC o equipo de escritorio local con la base de datos instalada en el mismo equipo o en la misma red local.

## Requisitos de hardware
Los requisitos mínimos recomendados son:
- CPU: Intel core i3
- RAM: 4GB
- Almacenamiento: 1GB

## Sistema operativo recomendado
El sistema operativo recomendado es Windows 10 u 11.
Se debe usar Windows ya que las dependencias usadas en JavaFX son para este sistemas operativo, además de las rutas, que también pertenecen a este sistema

## Instalación del entorno
Para ejecutar con mayor facilidad la app, debemos descargarnos, IntelliJ y XAMPP, en ese orden

### Dependencias
Las dependencias que necesitamos insertar en IntelliJ son JavaFX, Lombok y JDBC

### Configuracion
Debemos dirigirnos a la estructura del proyecto ( en el apartado File), y configurar un SDK: OpenJDK-26, aunque podríamos usar otro, pero este es recomendable.

## Usuarios, permisos y estructura

### Usuarios
- En la app exiten dos tipos de usuarios:
- `/Admin`: pueden insertar, actualizar o eliminar usuarios, productos, clases o reservas
- `/User`: pueden mirar su informacion, reservas clases y comprar productos

### Estructura de carpetas sugerida
- Dentro de src/main/java/org.example.globbefitnessapp/, se recomienda tener las carpetas: controller, dao, database y model. Y dentro de controller, admin y user
- Dentro de src/main/resources/org.example.globbefitnessapp/m se recomienda tener las carpetas: admin, css, img y user

## Mantenimiento básico
El mantenimiento mínimo debe centrarse en la actualización del entorno Java, el estado del servidor MySQL, la revisión de dependencias y la consistencia del acceso JDBC.

Se recomienda revisar estos puntos:
- funcionamiento de la conexión con la base de datos
- integridad de tablas y datos
- actualización de JDK, MySQL y dependencias Maven

En caso de fallo, el procedimiento básico sería comprobar primero si MySQL está activo, verificar credenciales y nombre de base de datos, revisar el log de consola y probar la conexión JDBC antes de modificar la interfaz.


