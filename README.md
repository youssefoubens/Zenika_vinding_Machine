
# Distributeur Automatique - API REST

## 📦 Instructions d’installation et de lancement

1. **Cloner le projet :**
   ```bash
   git clone   https://github.com/youssefoubens/Zenika_vinding_Machine.git 
   cd Zenika_vinding_Machine
   ```

2. **Construire le projet avec Maven :**
   ```bash
   ./mvnw clean install
   ```

3. **Lancer l’application :**
   ```bash
   ./mvnw spring-boot:run
   ```

4. **Accéder à la documentation Swagger :**
   ![Swagger UI - Partie 1](./images/swagger1.png)  
   ![Swagger UI - Partie 2](./images/swagger2.png)  
   ![Swagger UI - Vue complète](./images/fullswaggerimage.png)
## 📮 Démonstration de l’API (via postman)

### 🛒 Obtenir la liste des produits disponibles
GET http://localhost:8087/api/vending/products
![getproducts](./images/getproducts.png)

### 💰 Insérer une pièce
POST http://localhost:8087/api/vending/insert-coin?amount=2.0
![insertcoin](./images/insertcoin.png)


### 📦 Sélectionner un produit
POST http://localhost:8087/api/vending/select-product?productId=1&quantity=1
![select_product](./images/select_product.png)


### ✅ Compléter l’achat
POST http://localhost:8087/api/vending/purchase
![purchase](./images/purchase.png)


### ❌ Annuler la transaction
POST http://localhost:8087/api/vending/cancel
![cancel](./images/cancel.png)


### 🧾 Obtenir le solde actuel
GET http://localhost:8087/api/vending/balance
![getbalance](./images/getbalance.png)


### 🔍 Vérifier le statut d’une transaction
GET http://localhost:8087/api/vending/transactions/1
![gettransaction](./images/gettransaction.png)

💾 Moyens de stockage de données
ArrayList : Utilisé pour le stockage dynamique des données en mémoire, offrant flexibilité et simplicité.

Map : Exploité pour implémenter un algorithme glouton (greedy) dans la gestion optimale des pièces de monnaie ou des produits.

Base de données H2 : Employée pour stocker de manière persistante les informations relatives aux transactions, produits et éléments de transaction. Elle facilite le développement et les tests grâce à sa nature légère et embarquée.
## 🧠 Hypothèses ou ajouts personnels

- Le système utilise un solde global simulé en mémoire pour représenter les pièces insérées.
- Les produits sont préchargés à l'initialisation de l'application.
- Aucun mécanisme d’authentification n’est requis.
- Le changement rendu (si applicable) est géré automatiquement dans `completePurchase`.
  J'ai utilisé l'algorithme glouton (greedy) pour minimiser le rendu de monnaie.

![Changement](./images/change.png)


## 📊  Diagramme de conception

![Vending Machine Diagram](./images/diagram.png)


## 🖥️ Simple Frontend

A simple frontend is available to interact with the vending machine API:  
🔗 [Frontend Repository](https://github.com/youssefoubens/coin-crunch-vending-api.git)

### 📸 Screenshots

- **🏠 Home Page**  
  ![Home Page](./images/homefrentend.png)

- **⚠️ Insufficient Coins Alert**  
  ![Insufficient Coins](./images/insufisentcoins.png)

- **💰 After Adding Balance**  
  ![After Adding Balance](./images/afteraddingbalance.png)

- **✅ Validate Transaction Information**  
  ![Validate Transaction](./images/validateTransaction.png)

- **❌ Cancel Transaction**  
  ![Cancel Transaction](./images/canceltransaction.png)

- **🗄️ H2 Database - Transaction Table**  
  ![H2 Database Transactions](./images/h2databasetansaction.png)
