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
        costo_por_hora decimal(10, 2) not null,
        fecha_creacion datetime default current_timestamp,
        fecha_modificacion datetime default current_timestamp on update current_timestamp
    );

    -- tabla: cancha
    create table cancha (
        id_cancha int auto_increment primary key,
        nombre_cancha varchar(50) unique not null,
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
    create table reserva (
        id_reserva int auto_increment primary key,
        fecha_reserva date not null,
        hora_inicio time not null,
        hora_fin time not null,
        costo_total decimal(10, 2) not null,
        id_cliente int not null,
        id_cancha_deporte int not null,
        fecha_creacion datetime default current_timestamp,
        fecha_modificacion datetime default current_timestamp on update current_timestamp,
        foreign key (id_cliente) references cliente(id_cliente) on delete cascade,
        foreign key (id_cancha_deporte) references cancha_deporte(id_cancha_deporte) on delete cascade
    );

    -- Inserciones para la tabla administrador
    INSERT INTO administrador (nombre, apellido, usuario, contrasena, correo_electronico) 
    VALUES 
    ('Carlos', 'Pérez', 'cperez', 'contrasena123', 'cperez@correo.com'),
    ('Ana', 'López', 'alvarez', '1234seguro', 'ana@correo.com'),
    ('Luis', 'Gómez', 'lgomez', 'seguridad2024', 'luis@correo.com');

    -- Inserciones para la tabla cliente
    INSERT INTO cliente (nombre, apellido, correo_electronico, telefono, dni) 
    VALUES 
    ('Juan', 'Ramírez', 'juanr@correo.com', '987654321', '12345678'),
    ('María', 'Vega', 'mariavega@correo.com', '912345678', '23456789'),
    ('Pedro', 'Sánchez', 'pedros@correo.com', '634567890', '34567890');

    -- Inserciones para la tabla deporte
    INSERT INTO deporte (nombre_deporte, descripcion, costo_por_hora) 
    VALUES 
    ('Fútbol', 'Juego en equipo con balón', 50.00),
    ('Tenis', 'Deporte individual con raqueta', 30.00),
    ('Básquetbol', 'Deporte de equipo con balón en canasta', 40.00);

    -- Inserciones para la tabla cancha
    INSERT INTO cancha (nombre_cancha, estado) 
    VALUES 
    ('Cancha Principal', 'disponible'),
    ('Cancha Secundaria', 'reservada'),
    ('Cancha de Mantenimiento', 'mantenimiento');

    -- Inserciones para la tabla intermedia cancha_deporte
    INSERT INTO cancha_deporte (id_cancha, id_deporte) 
    VALUES 
    (1, 1),  -- Cancha Principal con Fútbol
    (2, 2),  -- Cancha Secundaria con Tenis
    (3, 3);  -- Cancha de Mantenimiento con Básquetbol

    -- Inserciones para la tabla reserva
    INSERT INTO reserva (fecha_reserva, hora_inicio, hora_fin, costo_total, id_cliente, id_cancha_deporte) 
    VALUES 
    ('2024-12-25', '10:00:00', '12:00:00', 100.00, 1, 1),  -- Cliente Juan reserva Fútbol en Cancha Principal
    ('2024-12-26', '14:00:00', '16:00:00', 60.00, 2, 2),  -- Cliente María reserva Tenis en Cancha Secundaria
    ('2024-12-27', '18:00:00', '20:00:00', 80.00, 3, 3);  -- Cliente Pedro reserva Básquetbol en Cancha de Mantenimiento
