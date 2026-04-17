# Bases de Datos - Globbe Fitness

## Descripción

El objetivo es diseñar una base de datos completa, coherente y funcional para la app de gestión de un gimnasio.

La base de datos está pensada para almacenar y relacionar la información necesaria para gestionar:

- Socios
- Planes
- Clases
- Reservas
- Productos
- Categorías
- Ofertas
- Ventas
- Detalles de venta

## Objetivo del diseño

El propósito principal de esta base de datos es representar de forma realista el funcionamiento de un gimnasio, permitiendo que la app pueda:

- Consultar información de socios
- Asociar cada socio a un plan
- Gestionar reservas de clases
- Registrar productos vendidos
- Guardar ventas y sus líneas de detalle
- Realizar consultas útiles mediante SQL

## Entidades incluidas

La base de datos está formada por las siguientes tablas:

- `plan`
- `socio`
- `clase`
- `reserva`
- `categoria_producto`
- `oferta_producto`
- `producto`
- `venta`
- `detalle_venta`

## Archivos incluidos

Dentro de la carpeta `Scripts SQL/` se incluyen los siguientes scripts:

- `Script_Consultas` -> creación de la base de datos y tablas
- `Script_Consultas` -> inserción de datos de ejemplo
- `Script_Consultas` -> consultas SQL útiles para comprobar el funcionamiento

## Diseño de la base de datos

### Tablas principales

#### `plan`
Almacena los distintos planes de suscripción del gimnasio

#### `socio`
Guarda los datos personales de los socios y su plan

#### `clase`
Contiene la información de las clases dirigidas disponibles

#### `reserva`
Relaciona socios con clases reservadas

#### `categoria_producto`
Clasifica los productos del gimnasio por tipo

#### `oferta_producto`
Recoge promociones y descuentos aplicables a productos

#### `producto`
Contiene el catálogo de artículos en venta

#### `venta`
Registra cada operación de compra realizada por un socio

#### `detalle_venta`
Desglosa los productos incluidos en cada venta
