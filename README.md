 # Sistema de Alquiler de Canchas Deportivas 🏀⚽️

Sistema web completo para la gestión y reserva de canchas deportivas. Este proyecto está construido con Spring Boot para el backend y Angular para el frontend.

## Vista Previa del Proyecto 🎯
_(Aquí puedes agregar capturas de pantalla de tu aplicación cuando estén disponibles)_

## Características Principales ✨

- Gestión de reservas de canchas deportivas
- Panel de administración
- Sistema de autenticación y autorización
- Gestión de clientes y administradores
- Registro de canchas y deportes
- Reportes y estadísticas

## Tecnologías Utilizadas 🛠️

### Backend
- Java 17
- Spring Boot 3.x
- MySQL 8.0
- Spring Data JPA
- Spring Security
- JWT para autenticación
- Maven

### Frontend
- Angular 16+
- TypeScript
- Angular Material
- RxJS
- Bootstrap 5

### Herramientas de Desarrollo
- Git
- VS Code
- IntelliJ IDEA/Eclipse
- MySQL Workbench

## Requisitos Previos 📋

- Java JDK 17
- Node.js 16.x o superior
- MySQL 8.0
- Maven 3.x
- Angular CLI

## Instalación y Configuración 🔧

### Clonar el Repositorio
```bash
git clone https://github.com/roberttce/sistema-alquiler-canchas.git
cd sistema-alquiler-canchas
```

### Configurar Base de Datos
```sql
CREATE DATABASE db_alquiler_canchas;
```

### Backend (Spring Boot)
```bash
cd backend
# Instalar dependencias y compilar
mvn clean install
# Ejecutar aplicación
mvn spring-boot:run
```

### Frontend (Angular)
```bash
cd frontend
# Instalar dependencias
npm install
# Ejecutar en modo desarrollo
ng serve
```

## Estructura de la Base de Datos 📊

### Tablas Principales
- administrador
- cliente
- deporte
- cancha
- cancha_deporte
- reserva

## Documentación de API RESTful 📚

### Endpoints de Administradores
```
GET    /api/administradores     → Listar todos
GET    /api/administradores/{id}→ Obtener uno
POST   /api/administradores     → Crear nuevo
PUT    /api/administradores/{id}→ Actualizar
DELETE /api/administradores/{id}→ Eliminar
```

### Endpoints de Clientes
```
GET    /api/clientes           → Listar todos
GET    /api/clientes/{id}      → Obtener uno
POST   /api/clientes          → Crear nuevo
PUT    /api/clientes/{id}     → Actualizar
DELETE /api/clientes/{id}     → Eliminar
```

### Endpoints de Deportes
```
GET    /api/deportes          → Listar todos
GET    /api/deportes/{id}     → Obtener uno
POST   /api/deportes         → Crear nuevo
PUT    /api/deportes/{id}    → Actualizar
DELETE /api/deportes/{id}    → Eliminar
```

### Endpoints de Canchas
```
GET    /api/canchas          → Listar todas
GET    /api/canchas/{id}     → Obtener una
POST   /api/canchas         → Crear nueva
PUT    /api/canchas/{id}    → Actualizar
DELETE /api/canchas/{id}    → Eliminar
```

### Endpoints de Reservas
```
GET    /api/reservas          → Listar todas
GET    /api/reservas/{id}     → Obtener una
POST   /api/reservas         → Crear nueva
PUT    /api/reservas/{id}    → Actualizar
DELETE /api/reservas/{id}    → Eliminar
GET    /api/reservas/cliente/{id} → Por cliente
```

## Estructura del Proyecto 📁

### Backend
```
src/
├── main/
│   ├── java/com/alquiler/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   ├── security/
│   │   └── util/
│   └── resources/
│       └── application.properties
└── test/
```

### Frontend
```
src/
├── app/
│   ├── components/
│   ├── services/
│   ├── models/
│   ├── guards/
│   └── shared/
├── assets/
└── environments/
```

## Variables de Entorno 🔐

### Backend (application.properties)
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/db_alquiler_canchas
spring.datasource.username=root
spring.datasource.password=
spring.jpa.hibernate.ddl-auto=update
jwt.secret=your-secret-key
```

### Frontend (environment.ts)
```typescript
export const environment = {
  production: false,
  apiUrl: 'http://localhost:8080/api'
};
```

## Estándares de Código y Commits 📝

### Formato de Commits
```
[ADD] - Nuevas funcionalidades
[FIX] - Correcciones de bugs
[UPDATE] - Actualizaciones o mejoras
[DELETE] - Eliminación de código
[DOC] - Cambios en documentación
[TEST] - Cambios en pruebas
```

### Ejemplo:
```bash
git commit -m "[ADD] - Implementación de módulo de reservas"
```

## Testing 🧪

### Backend
```bash
mvn test
```

### Frontend
```bash
ng test
```

## Despliegue 🚀

### Backend
```bash
mvn clean package
java -jar target/backend-0.0.1-SNAPSHOT.jar
```

### Frontend
```bash
ng build --prod
```

## Contribución 🤝

1. Fork del proyecto
2. Crear rama de feature (`git checkout -b feature/NuevaCaracteristica`)
3. Commit de cambios (`git commit -m '[ADD] - Agrega nueva característica'`)
4. Push a la rama (`git push origin feature/NuevaCaracteristica`)
5. Abrir Pull Request

## Control de Versiones

- Las releases seguirán el formato de Semantic Versioning (MAJOR.MINOR.PATCH)
- Develop será la rama principal de desarrollo
- Main/Master contendrá el código en producción

## Autor ✒️

* **Roberto** - *Desarrollo Full Stack* - [roberttce](https://github.com/roberttce)

## Licencia 📄

Este proyecto está bajo la Licencia MIT - ver el archivo [LICENSE.md](LICENSE.md) para detalles

## Agradecimientos 🎁

* Comenta a otros sobre este proyecto 📢
* Invita una cerveza 🍺 o un café ☕ al equipo
* Da las gracias públicamente 🤓

---
⌨️ con ❤️ por [Roberto](https://github.com/roberttce) 😊
