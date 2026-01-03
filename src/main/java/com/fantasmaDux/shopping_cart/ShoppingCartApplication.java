package com.fantasmaDux.shopping_cart;

import org.flywaydb.core.Flyway;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

@SpringBootApplication
public class ShoppingCartApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShoppingCartApplication.class, args);
	}

	@Bean
	public CommandLineRunner checkFlyway(DataSource dataSource) {
		return args -> {
			System.out.println("=== ПРОВЕРКА FLYWAY ===");
			try {
				Flyway flyway = Flyway.configure()
						.dataSource(dataSource)
						.locations("classpath:db/migration")
						.baselineOnMigrate(true)
						.baselineVersion(String.valueOf(1))
						.load();

				System.out.println("Flyway создан успешно!");
				System.out.println("Миграций найдено: " + flyway.info().pending().length);

				// Выполнить миграции
				flyway.migrate();
				System.out.println("Миграции выполнены!");
			} catch (Exception e) {
				System.out.println("ОШИБКА Flyway: " + e.getMessage());
				e.printStackTrace();
			}
		};
	}
}
