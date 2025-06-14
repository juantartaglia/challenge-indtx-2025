# Ejercicio técnico - Price API

## Descripción
Aplicación Spring Boot que provee un endpoint REST para consultar el precio aplicable a un producto en una fecha determinada. Utiliza una base de datos H2 con la tabla PRICES que contiene los precios y tarifas aplicables por producto, cadena y rango de fechas.

El endpoint acepta los parámetros: applicationDate, productId y brandId. Devuelve: productId, brandId, priceListId (
tarifa aplicable), dateRange (rango de
fechas) y precio.

## Stack Tecnológico

- Java 21
- Spring Boot 3.4.5
- Base de datos en memoria H2
- Liquibase para migraciones de base de datos
- OpenAPI 2.8.5

## Documentación API

Se utiliza OpenAPI para documentar la API REST. La especificación OpenAPI está disponible en:

```
http://localhost:8080/api/v1/v3/api-docs
```

La definición completa de la API se encuentra en:

```
api/specs/openapi.json
```

## Configuración y Despliegue

En la raíz del proyecto se encuentran los siguientes archivos para facilitar el despliegue:

- `Dockerfile`: Para la construcción de la imagen de la aplicación (imagen multilayer para minimizar su tamaño)
- `docker-compose.yml`: Para la ejecución del entorno completo

Para iniciar la aplicación con Docker Compose, ejecutar el siguiente comando en la raíz del proyecto:

```
docker compose up --build
```

## API Endpoint

```
GET /product/{productId}/price?applicationDate={date_time}&brandId={brand_id}
```

### Parámetros:
- applicationDate: fecha de aplicación (YYYY-MM-DD hh:mm:ss)
- productId: id del producto
- brandId: id de la cadena

### Respuesta:
```json
{
  "productId": 35455,
  "brandId": 1,
  "priceListId": 1,
  "dateRange": {
    "startDate": "2020-06-14T00:00:00",
    "endDate": "2020-12-31T22:59:59"
  },
  "price": 35.5
}
```

## Pruebas

### Pruebas Unitarias

Se han implementado pruebas unitarias para los servicios y controladores de la aplicación. Para ejecutarlas, utilizar el
siguiente comando:

```
./gradlew test
```

### Pruebas End-to-End

En la carpeta `/test/e2e/postman` se encuentra:

- Colección de Postman para pruebas end-to-end
- Archivo de entorno de Postman para configuración

### Test de endpoint

Se realizan los siguientes tests al endpoint:

#### Test 1
- Fecha: 2020-06-14 10:00
- Producto: 35455
- Marca: 1
- **Criterio de aceptación**:
  - Tarifa aplicable debe ser "1"
  - Precio debe ser 35.50

#### Test 2
- Fecha: 2020-06-14 16:00
- Producto: 35455
- Marca: 1
- **Criterio de aceptación:**
  - Tarifa aplicable debe ser "2"
  - Precio debe ser 25.45

#### Test 3
- Fecha: 2020-06-14 21:00
- Producto: 35455
- Marca: 1
- **Criterio de aceptación:**
  - Tarifa aplicable debe ser "1"
  - Precio debe ser 35.50

#### Test 4
- Fecha: 2020-06-15 10:00
- Producto: 35455
- Marca: 1
- **Criterio de aceptación:**
  - Tarifa aplicable debe ser "3"
  - Precio debe ser 30.50

#### Test 5:
- Fecha: 2020-06-16 21:00
- Producto: 35455
- Marca: 1
- **Criterio de aceptación:**
  - Tarifa aplicable debe ser "4"
  - Precio debe ser 38.95