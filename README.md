# Introduction

Beer tap dispenser es una aplicación diseñada gestionar y hacer uso de unos dispensadores de cerveza virtuales. La aplicación permite añadir dispensadores de cervezas y observar los detalles y usos de cada dispensador.
La aplicación está dividida en las siguientes partes:
* DB
* BACKEND
* FRONTEND

## DB
Base de datos MySQL compuesta por las siguientes tablas:
``` sql
CREATE TABLE dispenser (
  `id` varchar(36) NOT NULL,
  `flow_volumen` float NOT NULL,
  `price_per_liter` float NOT NULL,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`id`)
) ;

CREATE TABLE `dispenserusages` (
  `id` int NOT NULL AUTO_INCREMENT,
  `openet_at` datetime NOT NULL,
  `close_at` datetime DEFAULT NULL,
  `total_spent` float DEFAULT NULL,
  `dispenser_id` varchar(36) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `dispenser_id` (`dispenser_id`),
  CONSTRAINT `dispenserusages_ibfk_1` FOREIGN KEY (`dispenser_id`) REFERENCES `dispenser` (`id`)
) ;

```
En la tabla dispenser se almacenan los distintos dispenser y su status para comprobar si está siendo usado, el volumen de flujo que realiza y el precio por cada litro.
En la tabla dispenserusage se almacena cada uno de cada dispenser con su hora de inicio/fin y el generado en cada uso del dispensador. 

## BACKEND
El backend ha sido generado con java 1.8 y springboot 2.7.5 en forma de microservicio basado en la arquitectura hexagonal con los siguientes endpoints.


```http
GET /dispenser
```
#### Request
#### Response
```javascript
{
  "id" : string,
  "flow_volume" : number,
  "amount" : number,
  "usages" : [
    "id": number,
    "openet_at": string,
    "close_at": string",
    "total_spent": number],
  "price_per_liter" : number,
  "status" : number
}
```

El `status` solo puede tener dos valores, CLOSE(0) y OPEN (1).
Los campos `close_at` y `total_spent` pueden ser campos con valor null causados a que en el momento de solicitarla, el dispenser se encontraba abierto.
El `amount` es la suma de todos los usos y el campo `total_spent` es la ganancia de un único uso.
Los campos `openet_at` y `close_at` tienen este formato de fecha `yyyy-MM-dd'T'HH:mm:ss'Z'`
#### Status code
| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 400 | `BAD REQUEST` |
| 500 | `INTERNAL SERVER ERROR` |

---

```http
POST /dispenser
```
#### Request
```javascript
{
  "flow_volume" : number,
  "price_per_liter" : number,
}
```
#### Response
```javascript
{
  "id" : string,
  "flow_volume" : number,
  "amount" : number,
  "usages" : [
    "id": number,
    "openet_at": string,
    "close_at": string",
    "total_spent": number],
  "price_per_liter" : number,
  "status" : number
}
```
El `status` solo puede tener dos valores, CLOSE(0) y OPEN (1).
Los campos `close_at` y `total_spent` pueden ser campos con valor null causados a que en el momento de solicitarla, el dispenser se encontraba abierto.
El `amount` es la suma de todos los usos y el campo `total_spent` es la ganancia de un único uso.
Los campos `openet_at` y `close_at` tienen este formato de fecha `yyyy-MM-dd'T'HH:mm:ss'Z'`

#### Status code
| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 400 | `BAD REQUEST` |
| 500 | `INTERNAL SERVER ERROR` |

---
```http
PUT /dispenser/{id}/status
```
#### Request

```javascript
{
  "status" : number,
  "updated_at" : string,
}
```
El parámetro `{id}` corresponde a la id del dispenser.
El `status` solo puede tener dos valores, CLOSE(0) y OPEN (1).
Los campos `updated_at` debe tener este formato de fecha `yyyy-MM-dd'T'HH:mm:ss'Z'`
#### Response
```javascript
{
  "status" : number,
  "updated_at" : string,
}
```
#### Status code
| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 400 | `BAD REQUEST` |
| 404 | `NOT FOUND` |
| 409 | `CONFLICT` |
| 500 | `INTERNAL SERVER ERROR` |

---
```http
GET /dispenser/{id}/spending
```
#### Request
El parámetro `{id}` corresponde a la id del dispenser.

#### Response
```javascript
{
  "amount" : number,
  "usages" : [
    "id": number,
    "openet_at": string,
    "close_at": string",
    "total_spent": number]
}
```
Los campos `close_at` y `total_spent` pueden ser campos con valor null causados a que en el momento de solicitarla, el dispenser se encontraba abierto.
El `amount` es la suma de todos los usos y el campo `total_spent` es la ganancia de un único uso.
Los campos `openet_at` y `close_at` tienen este formato de fecha `yyyy-MM-dd'T'HH:mm:ss'Z'`
#### Status code
| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 400 | `BAD REQUEST` |
| 404 | `NOT FOUND` |
| 500 | `INTERNAL SERVER ERROR` |

---

## FRONTEND
Aplicación creada la versión 14.2.5 de nextjs y bootstrap para los estilos más genéricos. 
Cuenta con las siguientes 3 páginas:
* / -> Permite interactuar con los dispenser virtuales.
* /login -> Login de entrada para el modo administrador.
* /admin -> Permite crear y ver los detalles de cada dispenser.


## DEPLOY
Cada una de las partes de este proyecto tiene creado un dockerfile para generar una imagen de docker. 


#### DB
Desde la raíz del proyecto ejecutar el siguiente comando:
```docker
docker build . -t beerdispenserdb:latest .
```
#### BACKEND
Desde la raíz del proyecto ejecutar los siguiente comandos:
```docker
cd backend
```
```docker
docker build . -t beerdispenserjar:latest .
```

#### FRONTEND
Desde la raíz del proyecto ejecutar los siguiente comandos:
```docker
cd frontend
```
```docker
docker build . -t beerdispenserfront:latest .
```

Una vez generada las imágenes, comprobar si están creadas correctamente con `docker images`

Si todo el proceso anterior ha sido exitoso solo tendríamos que ejecutar el comando `docker-compose up` desde la raíz del proyecto. 
Es posible que la primera vez de un error porque el network necesario no ha sido creado, para eso ejecutamos el siguiente comando
```docker
docker network create ext-network
```



