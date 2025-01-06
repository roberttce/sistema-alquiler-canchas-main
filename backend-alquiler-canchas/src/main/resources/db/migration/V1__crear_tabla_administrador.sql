    -- Eliminar las tablas en orden (las tablas intermedias primero, luego las dependientes)
    DROP TABLE IF EXISTS reserva;
    DROP TABLE IF EXISTS cancha_deporte;
    DROP TABLE IF EXISTS cancha;
    DROP TABLE IF EXISTS deporte;
    DROP TABLE IF EXISTS cliente;
    DROP TABLE IF EXISTS administrador;

    -- tabla: administrador
    create table administrador (
        id_administrador int auto_increment primary key,
        nombre varchar(50) not null,
        apellido varchar(100) not null,
        usuario varchar(50) unique not null,
        contrasena varchar(255) not null,
        correo_electronico varchar(100) unique not null,
        fecha_creacion datetime default current_timestamp,
        fecha_modificacion datetime default current_timestamp on update current_timestamp
    );

    -- tabla: cliente
    create table cliente (
        id_cliente int auto_increment primary key,
        nombre varchar(50) not null,
        apellido varchar(50) not null,
        correo_electronico varchar(100) unique not null,
        telefono varchar(15) not null,
        dni varchar(8) unique not null,
        fecha_creacion datetime default current_timestamp,
        fecha_modificacion datetime default current_timestamp on update current_timestamp
    );

    -- tabla: deporte
    create table deporte (
        id_deporte int auto_increment primary key,
        nombre_deporte varchar(50) not null,
        descripcion varchar(255),
        fecha_creacion datetime default current_timestamp,
        fecha_modificacion datetime default current_timestamp on update current_timestamp
    );

    -- tabla: cancha
    create table cancha (
        id_cancha int auto_increment primary key,
        nombre_cancha varchar(50) unique not null,
        costo_por_hora decimal(10, 2) not null,
        estado varchar(50) not null check (estado in ('disponible', 'reservada', 'mantenimiento')),
        fecha_creacion datetime default current_timestamp,
        fecha_modificacion datetime default current_timestamp on update current_timestamp
    );

    -- tabla intermedia: cancha_deporte
    create table cancha_deporte (
        id_cancha_deporte int auto_increment primary key,
        id_cancha int not null,
        id_deporte int not null,
        unique (id_cancha, id_deporte),
        foreign key (id_cancha) references cancha(id_cancha) on delete cascade,
        foreign key (id_deporte) references deporte(id_deporte) on delete cascade
    );

    -- tabla: reserva
    CREATE TABLE reserva (
        id_reserva INT AUTO_INCREMENT PRIMARY KEY,
        fecha_reserva DATE NOT NULL,
        hora_inicio TIME NOT NULL,
        hora_fin TIME NOT NULL,
        costo_total DECIMAL(10, 2) NOT NULL,
        adelanto DECIMAL(10, 2) NOT NULL,
        id_cliente INT NOT NULL,
        id_cancha_deporte INT NOT NULL,
        estado VARCHAR(50) NOT NULL,  -- Para almacenar el valor del Enum
        fecha_creacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
        fecha_modificacion TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
        CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES cliente(id_cliente),
        CONSTRAINT fk_cancha_deporte FOREIGN KEY (id_cancha_deporte) REFERENCES cancha_deporte(id_cancha_deporte)
    );

     
    