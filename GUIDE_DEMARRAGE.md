# 🚀 Guide de Démarrage Rapide - Banking App

## Option 1 : Android Studio (Recommandé pour développement)

### Étape 1 : Installer Android Studio
- Télécharger depuis : https://developer.android.com/studio
- Installer avec les composants par défaut

### Étape 2 : Ouvrir le Projet
1. Lancer Android Studio
2. **File > Open** (ou **Open** sur l'écran d'accueil)
3. Sélectionner le dossier `testAndroid`
4. Cliquer **OK**

### Étape 3 : Configuration Initiale
1. Android Studio va **automatiquement** :
   - Télécharger Gradle
   - Synchroniser les dépendances
   - Configurer le projet

2. Attendre la fin de la synchronisation (barre de progression en bas)

### Étape 4 : Configurer Google Maps API (IMPORTANT)

**Obtenir une clé API Google Maps (GRATUIT)** :
1. Aller sur https://console.cloud.google.com/
2. Créer un nouveau projet ou sélectionner un existant
3. Activer **"Maps SDK for Android"**
4. Aller dans **APIs & Services > Credentials**
5. **Create Credentials > API Key**
6. Copier la clé générée

**Modifier le projet** :
1. Ouvrir `app/src/main/AndroidManifest.xml`
2. Ligne 23, remplacer `YOUR_GOOGLE_MAPS_API_KEY` par votre clé
3. Sauvegarder (Ctrl+S / Cmd+S)

```xml
<meta-data
    android:name="com.google.android.geo.API_KEY"
    android:value="VOTRE_CLE_API_ICI" />
```

### Étape 5 : Créer un Émulateur Android

1. Cliquer sur **Tools > Device Manager**
2. Cliquer **Create Device** (➕)
3. Choisir un modèle : **Pixel 6** ou **Pixel 7**
4. Cliquer **Next**
5. Sélectionner une image système :
   - **API 34** (Android 14) - Recommandé
   - Si demandé, cliquer sur **Download** à côté de l'image
6. Cliquer **Next** puis **Finish**

### Étape 6 : Lancer l'Application

1. Vérifier que votre émulateur est sélectionné dans la liste déroulante (en haut)
2. Cliquer sur le bouton **Run** ▶️ (ou appuyer sur **Shift + F10**)
3. L'émulateur va démarrer et l'application s'installer automatiquement

**Première utilisation** :
- Créer un compte (Sign Up)
- Définir un PIN à 4 chiffres
- Explorer l'application !

---

## Option 2 : Ligne de Commande (Pour tests rapides)

### Prérequis
- Java JDK 17 installé
- Android SDK installé

### Compilation

```bash
cd testAndroid

# Donner les permissions d'exécution
chmod +x gradlew

# Compiler le projet
./gradlew build

# Installer sur un appareil/émulateur connecté
./gradlew installDebug
```

### Avec un appareil physique

1. **Activer le mode développeur** sur votre téléphone Android :
   - Aller dans **Paramètres > À propos du téléphone**
   - Taper 7 fois sur **Numéro de build**

2. **Activer le débogage USB** :
   - Aller dans **Paramètres > Options pour les développeurs**
   - Activer **Débogage USB**

3. **Connecter le téléphone** via USB

4. **Vérifier la connexion** :
```bash
adb devices
```

5. **Installer l'app** :
```bash
./gradlew installDebug
```

---

## 🔧 Résolution de Problèmes

### Gradle sync failed
```bash
# Dans Android Studio
File > Invalidate Caches > Invalidate and Restart
```

### Émulateur ne démarre pas
- Vérifier que la virtualisation est activée dans le BIOS
- Essayer un émulateur avec une API plus ancienne (API 30 ou 31)

### Erreur de compilation
```bash
# Nettoyer et rebuilder
./gradlew clean build
```

### Google Maps ne s'affiche pas
- Vérifier que la clé API est correctement configurée
- Vérifier que l'API "Maps SDK for Android" est activée sur Google Cloud Console

---

## 📱 Utilisation de l'Application

### Première utilisation
1. **Sign Up** : Créer un compte avec nom, email, mot de passe
2. **Créer un PIN** : Définir un code à 4 chiffres
3. **Confirmer le PIN** : Re-saisir le même code

### Connexion suivante
1. **Login** : Email + mot de passe
2. **Entrer le PIN** : Saisir votre code à 4 chiffres

### Fonctionnalités disponibles
- ✅ Voir le solde et les transactions
- ✅ Effectuer un paiement (simulation)
- ✅ Visualiser le budget avec graphique
- ✅ Trouver des DAB sur la carte
- ✅ Changer le thème (Dark/Light mode)
- ✅ Voir le profil et se déconnecter

---

## 💡 Conseils

- **Données de démo** : Solde initial de $5,000 avec 6 transactions pré-remplies
- **PIN de test** : Vous pouvez utiliser n'importe quel code à 4 chiffres (ex: 1234)
- **Paiements** : Sont simulés avec un délai de 1.5 secondes
- **DAB** : 5 DAB sont simulés autour de Paris (position par défaut)

---

## 📞 Support

Si vous rencontrez des problèmes :
1. Vérifier que Android Studio est à jour
2. Vérifier que Java 17 est installé
3. Nettoyer le cache Gradle : `./gradlew clean`
4. Invalider les caches d'Android Studio

Bon développement ! 🎉
