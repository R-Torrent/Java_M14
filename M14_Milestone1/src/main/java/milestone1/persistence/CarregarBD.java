package milestone1.persistence;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import milestone1.domain.Botiga;
import milestone1.domain.Quadre;

@Configuration
public class CarregarBD {
	
	private static final Logger log = LoggerFactory.getLogger(CarregarBD.class);
	
	@Bean
	CommandLineRunner initBD(RepositoriBotigues repositoriBotigues, RepositoriQuadres repositoriQuadres) {
		return args -> {
			Botiga b1 = repositoriBotigues.save(new Botiga("Holbein's Corner", (short)17));
			Botiga b2 = repositoriBotigues.save(new Botiga("LucianFreud Portraits", (short)22));
			Botiga b3 = repositoriBotigues.save(new Botiga("Turner Gallery", (short)30));
			Botiga b4 = repositoriBotigues.save(new Botiga("Waterhouse's Ladies", (short)21));
			
			repositoriQuadres.save(new Quadre("Zanahoria volando", "Roger Torrent", 1300.75f,
					java.sql.Timestamp.valueOf(LocalDateTime.of(1921, 6, 17, 14, 51)), b1));
			repositoriQuadres.save(new Quadre("Remolacha tímida", "Roger Torrent", 911.32f,
					java.sql.Timestamp.valueOf(LocalDateTime.of(1967, 1, 4, 0, 49)), b1));
			repositoriQuadres.save(new Quadre("La gran coliflor", "Roger Torrent", 3200.00f,
					java.sql.Timestamp.valueOf(LocalDateTime.of(1943, 11, 30, 23, 0)), b3));
			repositoriQuadres.save(new Quadre("Existencialismo reflectante", "Lara Saiz", 430.80f,
					java.sql.Timestamp.valueOf(LocalDateTime.of(2002, 10, 11, 8, 15)), b2));
			repositoriQuadres.save(new Quadre("Dialéctica del orden", "Lara Saiz", 12900.42f,
					java.sql.Timestamp.valueOf(LocalDateTime.now()), b3));
			repositoriQuadres.save(new Quadre("Funciones conformes", "Jordi Albiol", 362.05f,
					java.sql.Timestamp.valueOf(LocalDateTime.of(1980, 2, 29, 18, 7)), b3));
			repositoriQuadres.save(new Quadre("Ciclos meromórficos", "Jordi Albiol", 516.89f,
					java.sql.Timestamp.valueOf(LocalDateTime.of(1993, 2, 16, 14, 22)), b3));
			repositoriQuadres.save(new Quadre("Hiperbología", "Jordi Albiol", 241.46f,
					java.sql.Timestamp.valueOf(LocalDateTime.of(1943, 11, 30, 23, 0)), b4));
			repositoriQuadres.save(new Quadre("Fondo azul", "Andreu Vinyoles", 14381.72f,
					java.sql.Timestamp.valueOf(LocalDateTime.of(1935, 9, 9, 17, 26)), b4));
			
			log.info("Construint la base de dades \'whitecollar\'...");
			repositoriBotigues.findAll().forEach(botiga -> log.info("Carregant " + botiga + "..."));
			repositoriQuadres.findAll().forEach(quadre -> log.info("Carregant " + quadre + "..."));
		};
	}
	
}
