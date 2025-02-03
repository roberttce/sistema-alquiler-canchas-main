export interface ClienteDTO {
    idCliente?: number;    
    nombre: string;          
    apellido: string;        // Apellido del cliente
    correoElectronico: string; // Correo electrónico
    telefono: string;        // Teléfono del cliente
    dni: string;           // DNI del cliente
    direccion: string;
    fechaNacimiento: Date;
    reservasIncompletas?: number;
}