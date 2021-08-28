# SPRING BATCH 
Ce dépôt contient un exemple afin de présenter Spring Batch
 
## Démarrage rapide
Pour tester ce code vous pouvez demarrer votre IDE habituelle est l'integrer dans votre IDE préférer rien de plus...

## Prérequis
Creer un projet maven avec https://start.spring.io/

ajouter les dependances suivantes 

<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.5.4</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
	<groupId>com.moon</groupId>
	<artifactId>bank-spring-batch</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>bank-spring-batch</name>
	<description>Demo project for Spring Boot</description>
	<properties>
		<java.version>1.8</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-batch</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-data-jpa</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>

		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-devtools</artifactId>
			<scope>runtime</scope>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>com.h2database</groupId>
			<artifactId>h2</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<scope>runtime</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.springframework.batch</groupId>
			<artifactId>spring-batch-test</artifactId>
			<scope>test</scope>
		</dependency>
	</dependencies>

	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
				<configuration>
					<excludes>
						<exclude>
							<groupId>org.projectlombok</groupId>
							<artifactId>lombok</artifactId>
						</exclude>
					</excludes>
				</configuration>
			</plugin>
		</plugins>
	</build>

</project>


  * Java 8
 
### SPRING BATCH

Creation d'un projet Banque avec Spring batch
Nous avons un fichier data.csv qui presente des données 
id,AccountID,TranstactionDate,transactionType,amount
540300,10025436,17/10/2018-09:44,D,10000.90

Que fait ce traitement.

1) il va d'abord lire le fichiers data.csv
2) Ensuite, nous avons 2 ItemProcessor qui vont se charger de faire chacun un traitement particulier l'un va transformer la date contenu dans le fichier pour l'inserer dans la base H2
l'autre va calculer le montant total du Credit et Debit , le resultat sera consultable via l'api Rest definit dans notre RestController 



### Configuration du fichier application.properties
https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#application-properties.data

### BASE DE DONNEES
utilisation d'un base de données H2 pour test

### Configuration du fichier application.properties
inputFile=classpath:/data.csv
#ne demarre pas
spring.batch.job.enabled=false 
spring.datasource.initialize=true
spring.datasource.url=jdbc:h2:mem:BankProduit;DB_CLOSE_DELAY=- 1;DB_CLOSE_ON_EXIT=FALSE
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=
spring.h2.console.enabled=true

### Creation d'un controller REST
on execute notre JobExecution depuis une api Rest sur le local 
localhost:8080/StartJob

### Dossier images pour visualiser l'arborescence de l'application
un fichier data.csv est crée dans la racine du projet(Voir fichier application.properties)

