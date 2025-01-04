package com.example.backend_alquiler_canchas;

import javax.sql.DataSource;
import org.mariadb.jdbc.MariaDbPoolDataSource; // Clase correcta de MariaDB
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MariaDbConfig {

    @Bean
    DataSource dataSource() {
        MariaDbPoolDataSource dataSource = null; 
        try {
            dataSource = new MariaDbPoolDataSource();
            dataSource.setUrl("jdbc:mariadb://localhost:3306/bd_alquiler_canchas");
            dataSource.setUser("root"); 
            dataSource.setPassword(""); 
            System.out.println("Conexión establecida con éxito.");

        } catch (Exception e) {
            e.printStackTrace(); 
            System.out.println("Error al conectar con la base de datos.");
        }
        return dataSource;
    }
}
