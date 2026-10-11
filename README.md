# API REST - Sistema de Gestión de Apartamentos

## Descripción de la API

El sistema está compuesto por una arquitectura basada en servicios REST diseñada para una empresa dedicada a la renta de habitaciones y casas. Su objetivo es gestionar eficientemente propiedades, clientes, reservaciones y pagos, dividiéndose en las siguientes APIs principales:

- **API de Clientes (`/api/clientes`):** Administra la información de los usuarios del sistema, diferenciando entre arrendatarios y propietarios. Permite su registro, actualización y baja.
- **API de Propiedades (`/api/propiedades`):** Registra casas y pisos completos, gestionando sus características físicas (dirección, capacidad, precio) y vinculándolas a sus respectivos propietarios.
- **API de Unidades Rentables (`/api/unidades`):** Gestiona los espacios individuales (habitaciones o pisos) que pertenecen a una propiedad general.
- **API de Reservas (`/api/reservas`):** Gestiona la disponibilidad del sistema. Crea reservaciones vinculando a un cliente con una unidad en un rango de fechas específico, evitando solapamientos.
- **API de Pagos (`/api/pagos`):** Da seguimiento a los estados de cuenta de cada reserva. Permite registrar pagos pendientes, confirmarlos, reembolsarlos y reportar morosidad.

## Tecnologías utilizadas

- **Lenguaje:** Java
- **Framework:** Spring Boot
- **Persistencia de Datos:** Spring Data JPA / Hibernate
- **Base de Datos:** MySQL
- **Seguridad:** BCrypt (Para encriptación de contraseñas)

## Pasos de instalación y ejecución local

1. Asegúrate de tener instalado Java, un IDE (como Eclipse, IntelliJ o VS Code) y MySQL Server ejecutándose localmente.
2. Crea una base de datos en MySQL llamada `apartamentos` (o el nombre que prefieras).
3. Clona este repositorio en tu máquina local usando `git clone https://github.com/Maaattt4/SOA-API-Apartamentos.git`.
4. Importa el proyecto como un proyecto Maven en tu IDE.
5. Modifica el archivo `src/main/resources/application.properties` con tus credenciales de MySQL (usuario y contraseña) y asegúrate de que el puerto coincida.
6. Ejecuta la clase principal `ApartamentosApplication.java`. El servidor iniciará (por defecto en el puerto 1500) y creará las tablas automáticamente.

## Variables de entorno necesarias

Configura tu archivo `application.properties` con los siguientes parámetros básicos antes de arrancar la aplicación:

```properties
spring.application.name=apartamentos
server.port=1500
spring.datasource.url=jdbc:mysql://localhost:3306/apartamentos?useSSL=false&serverTimezone=UTC
spring.datasource.username=root
spring.datasource.password=TU_PASSWORD_AQUI
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true

```

## Listado de endpoints

**Clientes (`/api/clientes`)**

* `GET /api/clientes`: Obtiene la lista de todos los clientes.
* `GET /api/clientes/{id}`: Obtiene los datos de un cliente específico.
* `POST /api/clientes`: Crea un nuevo cliente.
* `PUT /api/clientes/{id}`: Actualiza la información de un cliente.
* `DELETE /api/clientes/{id}`: Elimina un cliente.

**Propiedades (`/api/propiedades`)**

* `GET /api/propiedades`: Obtiene el catálogo completo de propiedades.
* `GET /api/propiedades/{id}`: Obtiene una propiedad específica.
* `GET /api/propiedades/tipo/{tipo}`: Busca propiedades por su tipo.
* `GET /api/propiedades/propietario/{id}`: Busca todas las propiedades de un propietario.
* `POST /api/propiedades`: Registra una nueva propiedad.
* `PUT /api/propiedades/{id}`: Actualiza una propiedad existente.
* `DELETE /api/propiedades/{id}`: Elimina una propiedad.

**Unidades (`/api/unidades`)**

* `GET /api/unidades`: Lista todas las unidades rentables.
* `POST /api/unidades`: Crea una nueva unidad vinculada a una propiedad.

**Reservas (`/api/reservas`)**

* `GET /api/reservas`: Lista todas las reservaciones.
* `POST /api/reservas`: Registra una nueva reserva (valida disponibilidad).
* `PUT /api/reservas/{id}`: Actualiza una reserva.
* `DELETE /api/reservas/{id}`: Elimina o cancela una reserva.

**Pagos (`/api/pagos`)**

* `GET /api/pagos`: Lista todos los registros de pagos.
* `POST /api/pagos`: Crea un nuevo pago asociado a una reserva.
* `POST /api/pagos/{id}/confirmar`: Cambia el estado de un pago a PAGADO.
* `POST /api/pagos/{id}/reembolsar`: Cambia el estado de un pago a REEMBOLSADO.

**Reportes (`/api/reportes`)**

* `GET /api/reportes/ingresos`: Muestra el total de ingresos por pagos confirmados.
* `GET /api/reportes/ocupacion`: Muestra el total de reservas activas.
* `GET /api/reportes/morosidad`: Muestra la lista de pagos vencidos o atrasados.

## Ejemplos de Peticiones y Respuestas

**1. Registrar un Cliente (POST `/api/clientes`)**
Cuerpo de la Petición (JSON):

```json
{
  "nombres": "Carlos",
  "apellidos": "López",
  "email": "carlos.lopez@email.com",
  "password": "password123"
}

```

Respuesta Esperada (JSON):

```json
{
  "id": 1,
  "tipo": "CLIENTE",
  "nombres": "Carlos",
  "apellidos": "López",
  "email": "carlos.lopez@email.com",
  "fechaRegistro": "2026-10-10T20:00:00",
  "estatus": "ACTIVO"
}

```

**2. Registrar una Propiedad (POST `/api/propiedades`)**
Cuerpo de la Petición (JSON):

```json
{
  "propietario": { "id": 1 },
  "tipo": "Casa",
  "titulo": "Casa en la Playa",
  "descripcion": "Hermosa casa con vista al mar",
  "direccion": "Bulevar Costero 123",
  "ciudad": "Veracruz",
  "estadoFederativo": "Veracruz",
  "codigoPostal": "94290",
  "pais": "Mexico",
  "latitud": 19.1738,
  "longitud": -96.1342,
  "precioNoche": 2500.00,
  "capacidad": 6,
  "numHabitaciones": 3,
  "numBanos": 2,
  "metrosCuadrados": 120
}

```

**3. Registrar una Reserva (POST `/api/reservas`)**
Cuerpo de la Petición (JSON):

```json
{
  "cliente": { "id": 1 },
  "unidad": { "idUnidad": 1 },
  "fechaEntrada": "2026-11-10T15:00:00",
  "fechaSalida": "2026-11-15T12:00:00",
  "importeTotal": 12500.00
}

```
