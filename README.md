# Biblioteca API

API REST para gestion de biblioteca construida con Spring Boot, MongoDB y arquitectura por capas.

## Capas

- `model`: documentos de MongoDB y clases del dominio.
- `repository`: interfaces de acceso a datos con Spring Data MongoDB.
- `dto`: objetos de entrada y salida del API.
- `service`: reglas de negocio.
- `controller`: endpoints HTTP.

## Requisitos

- Java 21
- Maven 3.9 o Maven embebido de IntelliJ
- MongoDB local o MongoDB Atlas

## Configuracion

Por defecto la app intenta conectarse a MongoDB local:

```properties
spring.data.mongodb.uri=mongodb://localhost:27017/biblioteca_db
spring.data.mongodb.database=biblioteca_db
```

Para Atlas, define variables de entorno:

```powershell
$env:MONGODB_URI="mongodb+srv://usuario:password@cluster.mongodb.net/biblioteca_db?retryWrites=true&w=majority"
$env:MONGODB_DATABASE="biblioteca_db"
```

## Ejecutar

```powershell
mvn spring-boot:run
```

Si Maven no esta en el PATH, en IntelliJ puedes ejecutar `BibliotecaApiApplication`.

## Endpoints principales

### Libros

- `POST /api/libros`
- `GET /api/libros`
- `GET /api/libros/{id}`
- `PUT /api/libros/{id}`
- `DELETE /api/libros/{id}`

### Usuarios

- `POST /api/usuarios`
- `GET /api/usuarios`
- `GET /api/usuarios/{id}`
- `PUT /api/usuarios/{id}`
- `DELETE /api/usuarios/{id}`

### Ejemplares

- `POST /api/ejemplares`
- `GET /api/ejemplares`
- `GET /api/ejemplares/{id}`
- `PUT /api/ejemplares/{id}`
- `DELETE /api/ejemplares/{id}`

### Prestamos

- `POST /api/prestamos`
- `GET /api/prestamos`
- `GET /api/prestamos/{id}`
- `PATCH /api/prestamos/{id}/devolucion`

Al crear un prestamo, el servicio valida que el usuario exista y que el ejemplar este en estado `DISPONIBLE`. Al registrar devolucion, el prestamo pasa a `DEVUELTO` y el ejemplar vuelve a `DISPONIBLE`.

## Ejemplo rapido

Crear libro:

```json
{
  "isbn": "978-0-13-468599-1",
  "titulo": "Clean Code",
  "autor": "Robert C. Martin",
  "anioPublicacion": 2008,
  "categoria": "Programacion"
}
```

Crear ejemplar:

```json
{
  "codigoEjemplar": "EJ-001",
  "libroId": "<id-libro>",
  "estado": "DISPONIBLE",
  "ubicacion": "Sala A"
}
```

Crear prestamo:

```json
{
  "usuarioId": "<id-usuario>",
  "ejemplarId": "<id-ejemplar>",
  "fechaDevolucionEsperada": "2026-06-02"
}
```
