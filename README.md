
# 🛒 Back-End API Documentation

NB : Qu'il faut créer un base de données MySQL avant de lancer le projet avec le nom "product-trial-master-db" ci-dessous (J'ai utilisé la base MySQL et non H2 ).

Ce projet représente la partie **back-end** d'une application de gestion de produits, panier d'achat et liste d'envie, avec authentification sécurisée via **JWT** et documentation via **OpenAPI**.

---

## ✨ Fonctionnalités principales

- **🛍️ Gestion des produits** : CRUD complet sur les produits  
- **🧺 Panier d'achat & liste d'envie** : Ajout/suppression des articles favoris et du panier  
- **🔐 Authentification sécurisée** : JWT pour protéger les routes sensibles  
- **👤 Gestion des utilisateurs** : Création de compte, connexion avec génération de token

---

## ⚙️ Technologies utilisées

- **Spring Boot** – développement de l'API REST  
- **Spring Security + JWT** – sécurisation des routes  
- **OpenAPI / Swagger** – documentation interactive de l'API  
- **JPA / Hibernate** – persistance des données  
- **MySQL** – base de données relationnelle (ou toute base compatible)

---

## 🛠️ Configuration requise

### 📁 Base de données

Créer une base de données nommée :

```sql
product-trial-master-db
```

Configuration dans `application.yml` :

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/product-trial-master-db?useSSL=false&serverTimezone=UTC
    username: root
    password:
    driver-class-name: com.mysql.cj.jdbc.Driver
```

> 💡 Utilisez le script `product-trial-master-db.sql` si nécessaire. Sinon, l'application se lancera en mode `update`.

### 🔑 Variables JWT

```yaml
jwt:
  secret: "votre_clé_secrète"
  expiration: 3600000 # 1 heure en millisecondes
```

---

## 🔐 Authentification & Sécurité

L'authentification est gérée par **Spring Security** et sécurisée par JWT.

### 🔓 Endpoints Auth

| Méthode | Endpoint     | Description                                   |
|---------|--------------|-----------------------------------------------|
| POST    | `/account`   | Crée un nouvel utilisateur                    |
| POST    | `/token`     | Authentifie un utilisateur, retourne un token |

📌 Seul l'utilisateur avec l'email **`admin@admin.com`** peut gérer les produits (CRUD).

### 📦 Exemple de payload

#### `/account`

```json
{
  "username": "john_doe",
  "firstname": "John",
  "email": "john.doe@example.com",
  "password": "securepassword123"
}
```

#### `/token`

```json
{
  "email": "john.doe@example.com",
  "password": "securepassword123"
}
```

---

## 📚 API REST Endpoints

| Ressource        | POST               | GET                      | PATCH / PUT             | DELETE         |
|------------------|--------------------|--------------------------|--------------------------|----------------|
| `/products`      | Créer un produit   | Récupérer tous les produits | X                        | Supprimer tous |
| `/products/{id}` | X                  | Détails d'un produit     | Modifier un produit      | Supprimer un produit |

---

## 📖 OpenAPI / Swagger

La documentation est accessible via :

👉 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

> Un fichier `api-docs.json` est également disponible pour la génération externe de la documentation.

---

## 🧪 Tests

L'API peut être testée via :

- **Postman**
- **Swagger UI**

Démarrez l'application, puis rendez-vous sur :

🔗 [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## ✅ Conclusion

Ce projet fournit une solution back-end complète pour :

- La gestion de produits
- Le panier et les favoris
- L'authentification via JWT
- Une documentation complète via Swagger
