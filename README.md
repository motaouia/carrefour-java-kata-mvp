# Carrefour Java kata MVP

![Licence](https://img.shields.io/badge/licence-MIT-blue.svg)

## Table des Matières

1. [Présentation](#présentation)
2. [Fonctionnalités](#fonctionnalités)
3. [Technologies Utilisées](#technologies-utilisées)
4. [Démarrage](#démarrage)
   - [Prérequis](#prérequis)
   - [Installation](#installation)
   - [Configuration](#configuration)
5. [Configuration de la Base de Données](#configuration-de-la-base-de-données)
   - [Changelogs Liquibase](#changelogs-liquibase)
6. [Documentation de l'API](#documentation-de-lapi)
   - [Swagger UI](#swagger-ui)
7. [Endpoints de l'API](#endpoints-de-lapi)
   - [Gestion des Créneaux Horaires](#gestion-des-créneaux-horaires)
   - [Gestion des Clients](#gestion-des-clients)
   - [Gestion des Commandes](#gestion-des-commandes)
8. [Tests](#tests)
   - [Utilisation de Postman](#utilisation-de-postman)
9. [Structure du Projet](#structure-du-projet)
10. [Contribution](#contribution)
11. [Licence](#licence)
12. [Remerciements](#remerciements)

---

## Présentation

L'**API de Carrefour Java kata MVP ** est un service RESTful développé avec **Spring Boot** qui permet aux clients de créer et de gérer des commandes, d'assigner des modes de livraison et de planifier des créneaux horaires. Cette API assure une intégrité robuste des données, une documentation complète via Swagger/OpenAPI et des contrôles d'accès sécurisés.

---

## Fonctionnalités

- **Gestion des Créneaux Horaires :**
  - Récupérer les créneaux disponibles en fonction du mode de livraison et de la date.
  
- **Gestion des Clients :**
  - Créer de nouveaux clients.
  - Récupérer les détails d'un client spécifique.
  
- **Gestion des Commandes :**
  - Créer de nouvelles commandes pour un client.
  - Définir le mode de livraison d'une commande.
  - Assigner un créneau horaire à une commande.
  - Récupérer tous les modes de livraison disponibles.
  
- **Validation des Données :**
  - Assure que toutes les entrées respectent les contraintes définies pour l'intégrité des données.
  
- **Documentation Complète de l'API :**
  - Documentation interactive de l'API via Swagger/OpenAPI.
  
  
- **Gestion de la Base de Données :**
  - Gestion des versions du schéma et des données avec Liquibase.
  
- **Gestion des Exceptions :**
  - Gestion centralisée des erreurs pour des réponses cohérentes et significatives.

---

## Technologies Utilisées

- **Java 21**
- **Spring Boot 3.x**
- **Spring Data JPA**
- **Hibernate**
- **MapStruct** pour le mappage des DTO
- **Liquibase** pour les migrations de base de données
- **Swagger/OpenAPI** pour la documentation de l'API
- **Postman** pour les tests de l'API
- **Maven** pour la gestion du projet

---

## Démarrage

### Prérequis

- **Java 21** ou supérieur installé
- **Maven** installé
- **Git** installé

### Installation

1. **Cloner le Référentiel**

   ```bash
   git clone git clone git@gitlab.com:m6806/carrefour-java-kata-mvp.git
   cd carrefour-java-kata-mvp
2. Exception Handling
Custom exception handling is in place to return meaningful error messages when validation fails or resources are not found.

3. Data Persistence with H2 and JPA
The product data is stored in an in-memory H2 database for testing purposes. Spring Data JPA is used to manage CRUD operations on the database.

4. Database Versioning with Liquibase
The database schema is versioned and managed using Liquibase, allowing easy migrations and tracking of changes in the database schema.

5. Unit Testing
Unit tests are written using JUnit 5 and Mockito to ensure the correctness of the business logic and API behavior.

6. API Documentation with Swagger
Swagger is integrated to provide interactive API documentation. You can access the Swagger UI to explore the API and test endpoints.

Installation
Clone the repository:
```bash
git clone https://github.com/motaouia/e-commmerce-alten-api.git
cd e-commmerce-alten-ap
```

Build the project using Maven:
```bash
mvn clean install -U -X
```
Run the application:
```bash
mvn spring-boot:run
```
## H2 Console
Access the H2 Console: Once the application is running, you can access the H2 database console at:

[h2-console](http://localhost:9988/h2-console) The database URL and credentials can be found in the application-dev.properties file.

## Swagger UI 
Access Swagger UI: You can explore the API and test the endpoints via Swagger UI at:

[Swagger UI](http://localhost:9988/swagger-ui) 
## Endpoints de l'API

### Gestion des Clients

| Ressource         | POST                  | GET                              | PATCH | PUT | DELETE               |
| ----------------- | --------------------- | -------------------------------- | ----- | --- | -------------------- |
| **/clients**      | Créer un nouveau client | X                                | X     | X   | X                    |
| **/clients/{id}** | X                     | Récupérer les détails du client  | X     | X   | X                    |

### Gestion des Commandes

| Ressource                                               | POST                                  | GET                           | PATCH | PUT                                       | DELETE |
| ------------------------------------------------------- | ------------------------------------- | ------------------------------ | ----- | ----------------------------------------- | ------ |
| **/api/livraisons**                                         | Créer une nouvelle commande pour un client | Récupérer tous les modes de livraison | X     | X                                     | X      |
| **/api/livraisons/{livraisonId}/clients/{clientId}**            | X                                     | X                              | X     | Définir le mode de livraison              | X      |
| **/api/livraisons/{clientId}/livraisons/{livraisonId}/timeslot**    | X                                     | X                              | X     | Assigner un créneau horaire à une commande | X      |

### Gestion des Créneaux Horaires

| Ressource                           | POST | GET                                                                 | PATCH | PUT | DELETE |
| ----------------------------------- | ---- | ------------------------------------------------------------------- | ----- | --- | ------ |
| **/time-slots/{mode}/timeslots**    | X    | Récupérer les créneaux horaires pour un mode de livraison et une date spécifiques | X     | X   | X      |


## Contributors
Mohamed MOTAOUIA