# Banking App - Android Jetpack Compose

Une application bancaire moderne et complète développée avec Jetpack Compose pour Android.

## Fonctionnalités

### Authentification et Sécurité
- **Login/Signup** : Interface d'inscription et de connexion avec validation des champs
- **PIN de sécurité** : Écran de création et de vérification d'un code PIN à 4 chiffres pour sécuriser l'accès à l'application
- Gestion de session utilisateur avec DataStore

### Page d'accueil (Home)
- Affichage du solde total du compte
- Numéro de compte utilisateur
- Liste des transactions récentes (crédit/débit)
- Actions rapides : Envoyer de l'argent, Budget, Trouver un DAB

### Paiements
- Simulation complète de paiement
- Sélection de catégorie de transaction (Nourriture, Transport, Shopping, Divertissement, Factures, etc.)
- Validation du solde disponible
- Mise à jour en temps réel du solde et des transactions
- Écran de confirmation de paiement

### Visualisation du Budget
- Graphique en camembert (Pie Chart) des dépenses par catégorie
- Liste détaillée avec pourcentages et montants
- Animation fluide du graphique
- Calcul automatique des dépenses totales

### Carte des DAB
- Intégration Google Maps
- Localisation des DAB à proximité (simulation de 5 DAB)
- Informations sur chaque DAB : nom, adresse, distance, disponibilité
- Marqueurs colorés selon la disponibilité
- Liste déroulante des DAB avec détails

### Paramètres
- Profil utilisateur avec informations du compte
- **Mode Sombre/Clair** : Switch pour basculer entre les thèmes
- Options de sécurité, notifications et support
- Fonction de déconnexion avec confirmation

## Architecture

### Technologies utilisées
- **Jetpack Compose** : Framework UI déclaratif moderne
- **Material Design 3** : Design system avec support du thème sombre
- **MVVM Architecture** : Séparation claire des responsabilités
- **Hilt/Dagger** : Injection de dépendances
- **DataStore** : Stockage persistant des préférences
- **Kotlin Coroutines & Flow** : Programmation asynchrone réactive
- **Navigation Compose** : Navigation entre les écrans
- **Google Maps Compose** : Intégration des cartes
- **Accompanist** : Bibliothèques complémentaires (permissions, etc.)

### Structure du projet
```
com.bankingapp/
├── data/
│   ├── model/           # Modèles de données (User, Transaction, ATM)
│   └── repository/      # Repositories (BankingRepository, UserPreferencesRepository)
├── di/                  # Dependency Injection (Hilt modules)
├── ui/
│   ├── theme/          # Theme, Colors, Typography
│   ├── navigation/     # Navigation setup
│   ├── screens/
│   │   ├── auth/       # Login & Signup
│   │   ├── pin/        # PIN screen
│   │   ├── home/       # Home screen
│   │   ├── payment/    # Payment screen
│   │   ├── budget/     # Budget visualization
│   │   ├── map/        # ATM Map
│   │   └── settings/   # Settings
│   └── components/     # Composants réutilisables
└── MainActivity.kt
```

## Installation

### Prérequis
- Android Studio Hedgehog (2023.1.1) ou supérieur
- JDK 17
- Android SDK avec API Level 34
- Gradle 8.2+

### Configuration

1. Clonez le repository :
```bash
git clone https://github.com/yourusername/banking-app.git
cd banking-app
```

2. Configurez Google Maps API :
   - Obtenez une clé API Google Maps depuis [Google Cloud Console](https://console.cloud.google.com/)
   - Remplacez `YOUR_GOOGLE_MAPS_API_KEY` dans `AndroidManifest.xml` par votre clé API

3. Ouvrez le projet dans Android Studio

4. Synchronisez les dépendances Gradle

5. Lancez l'application sur un émulateur ou un appareil physique

## Utilisation

### Premier lancement

1. **Inscription** : Créez un compte avec votre nom, email et mot de passe
2. **Création du PIN** : Définissez un code PIN à 4 chiffres pour sécuriser votre compte
3. **Confirmation du PIN** : Confirmez votre code PIN

### Connexion

1. Entrez votre email et mot de passe
2. Saisissez votre code PIN pour accéder à l'application

### Effectuer un paiement

1. Depuis la page d'accueil, cliquez sur "Send Money"
2. Remplissez les informations :
   - Destinataire
   - Montant
   - Catégorie
   - Description (optionnelle)
3. Confirmez le paiement
4. Le solde et les transactions sont mis à jour automatiquement

### Visualiser le budget

1. Cliquez sur "Budget" depuis la page d'accueil
2. Consultez le graphique circulaire des dépenses par catégorie
3. Visualisez les détails avec pourcentages et montants

### Trouver un DAB

1. Cliquez sur "Find ATM" depuis la page d'accueil
2. Autorisez l'accès à la localisation (optionnel)
3. Explorez la carte avec les DAB à proximité
4. Consultez la liste des DAB avec distances et disponibilités

### Changer le thème

1. Accédez aux paramètres via l'icône en haut à droite
2. Dans la section "Appearance", activez/désactivez le "Dark Mode"
3. Le thème change instantanément

## Données de démonstration

L'application utilise des données simulées :
- **Solde initial** : $5,000 (login) ou $1,000 (signup)
- **Transactions pré-remplies** : 6 transactions d'exemple
- **DAB simulés** : 5 distributeurs à proximité de Paris
- Les paiements sont simulés avec un délai de 1.5 secondes

## Fonctionnalités techniques

### Gestion des états
- StateFlow pour la gestion réactive des états
- ViewModel pour la persistance lors des changements de configuration
- DataStore pour la persistance des données utilisateur

### Sécurité
- PIN crypté stocké localement
- Validation des montants de paiement
- Vérification du solde disponible

### UI/UX
- Animations fluides avec Compose
- Design responsive Material Design 3
- Support complet du mode sombre
- Transitions de navigation fluides
- États de chargement et d'erreur

## Améliorations futures

- Backend réel pour la gestion des comptes et transactions
- Authentification biométrique (empreinte digitale, reconnaissance faciale)
- Historique complet des transactions avec filtres
- Notifications push pour les transactions
- Support multi-devises
- Transferts entre utilisateurs en temps réel
- Intégration d'API réelle pour les DAB
- Export des relevés de compte (PDF)
- Limites de dépenses personnalisables

## Licence

Ce projet est un projet de démonstration éducatif.

## Auteur

Développé avec Jetpack Compose et Material Design 3.
