package org.example.globbefitnessapp.database;

public interface DBSchema {

    // PLAN
    String TAB_PLAN = "plan";
    String PLAN_ID = "id_plan";
    String PLAN_NOMBRE = "nombre";
    String PLAN_DESCRIPCION = "descripcion";
    String PLAN_PRECIO = "precio_mensual";
    String PLAN_DURACION = "duracion";
    String PLAN_ACTIVO = "activo";

    // SOCIO
    String TAB_SOCIO = "socio";
    String SOCIO_ID = "id_socio";
    String SOCIO_NOMBRE = "nombre";
    String SOCIO_APELLIDOS = "apellidos";
    String SOCIO_DNI = "dni";
    String SOCIO_EMAIL = "email";
    String SOCIO_TELEFONO = "telefono";
    String SOCIO_FECHA_ALTA = "fecha_alta";
    String SOCIO_ESTADO = "estado";
    String SOCIO_ID_PLAN = "id_plan";

    // CLASE
    String TAB_CLASE = "clase";
    String CLASE_ID = "id_clase";
    String CLASE_NOMBRE = "nombre";
    String CLASE_DESCRIPCION = "descripcion";
    String CLASE_FECHA = "fecha";
    String CLASE_HORA = "hora";
    String CLASE_SALA = "sala";
    String CLASE_AFORO = "aforo_maximo";
    String CLASE_MONITOR = "monitor";
    String CLASE_ESTADO = "estado";

    // RESERVA
    String TAB_RESERVA = "reserva";
    String RESERVA_ID = "id_reserva";
    String RESERVA_FECHA = "fecha_reserva";
    String RESERVA_ESTADO = "estado";
    String RESERVA_ASISTENCIA = "asistencia";
    String RESERVA_ID_SOCIO = "id_socio";
    String RESERVA_ID_CLASE = "id_clase";

    // PRODUCTO
    String TAB_PRODUCTO = "producto";
    String PRODUCTO_ID = "id_producto";
    String PRODUCTO_NOMBRE = "nombre";
    String PRODUCTO_DESCRIPCION = "descripcion";
    String PRODUCTO_PRECIO = "precio";
    String PRODUCTO_STOCK = "stock";
    String PRODUCTO_ACTIVO = "activo";
    String PRODUCTO_ID_CATEGORIA = "id_categoria";
    String PRODUCTO_ID_OFERTA = "id_oferta";

    // VENTA
    String TAB_VENTA = "venta";
    String VENTA_ID = "id_venta";
    String VENTA_FECHA = "fecha_venta";
    String VENTA_METODO_PAGO = "metodo_pago";
    String VENTA_TOTAL = "total";
    String VENTA_ID_SOCIO = "id_socio";

    // DETALLE_VENTA
    String TAB_DETALLE_VENTA = "detalle_venta";
    String DETALLE_VENTA_ID = "id_detalle";
    String DETALLE_VENTA_CANTIDAD = "cantidad";
    String DETALLE_VENTA_PUNITARIO = "precio_unitario";
    String DETALLE_VENTA_TOTAL = "total";
    String DETALLE_VENTA_ID_VENTA = "id_venta";
    String DETALLE_VENTA_ID_PRODUCTO = "id_producto";
}
