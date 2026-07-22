# Hill Climb Racing - Infinite Fuel

## 🚗 BRZA INSTALACIJA ZA ANDROID

### 1️⃣ PREUZMI APK

**Lako:** 
Klikni DESNO → **"Releases"** ili **"Actions"** → Preuzmi `*.apk` fajl

**Link:**
https://github.com/selmirsabic91-spec/HillClimbRacing-Infinite/releases

### 2️⃣ INSTALIRAJ NA TELEFONU

1. Preuzi `app-release.apk` ili `app-debug.apk`
2. Spremi APK na Android telefon
3. Otvori File Manager na telefonu
4. Nađi APK fajl
5. Klikni i odaberi **"Install"**
6. Potvrdi instalaciju
7. **Eto! Igrica je instalirana!** 🎉

### 3️⃣ IGRAJ ODMAH!

**Kontrola:**
- **Dodir LEVO** ← = **KOČENJE** (brake)
- **Dodir DESNO** → = **UBRZANJE** (gas)

**Benzin:** ⛽ ∞ (BESKONAČAN!)

---

## 💻 ZA DEVELOPERE - Build sam

### Zahtevi:
- Android Studio 4.0+
- JDK 11+
- Android SDK 21+

### Koraci:

```bash
# 1. Kloniraj projekat
git clone https://github.com/selmirsabic91-spec/HillClimbRacing-Infinite.git
cd HillClimbRacing-Infinite

# 2. Otvori u Android Studio
# (File > Open > Odaberi folder)

# 3. Build APK iz terminala:
./gradlew assembleRelease

# 4. APK se nalazi na:
app/build/outputs/apk/release/app-release.apk

# 5. Ili Install direktno na conectovani urečaj:
./gradlew installDebug
```

---

## 🎮 KARAKTERISTIKE

✅ **Beskonačan benzin** - Nema limitiranja! ⛽∞
✅ **Realistična fizika** - Physics engine
✅ **Dinamički tereni** - Raznovrsni horizzontalni i nagibili
✅ **Offline** - Bez potrebe za internetom
✅ **Bez oglasa** - 100% čist kod
✅ **Besplatan** - Zauvijek!

---

## 🚗 KAKO SE IGRA

1. **Dodir levo** za kočenje
2. **Dodir desno** za ubrzanje
3. **Balancira** automobil prekřstožja
4. **Dostignite što veću distancu**
5. **Ne pada**s sa terena!

---

## 📁 Struktura Projekta

```
HillClimbRacing-Infinite/
├── app/
│   ├── src/main/
│   │   ├── java/com/example/hillclimbracing/
│   │   │   ├── MainActivity.kt
│   │   │   └── game/
│   │   │       ├── GameView.kt
│   │   │       ├── Car.kt
│   │   │       ├── Terrain.kt
│   │   │       └── GameThread.kt
│   │   ├── res/
│   │   └── AndroidManifest.xml
│   ├── build.gradle
│   └── proguard-rules.pro
├── build.gradle
├── settings.gradle
├── gradle.properties
├── README.md
├── INSTALL.md
└── .gitignore
```

---

## 🔐 Licenca

MIT License - Slobodno koristi, modifikuj i deli!

---

## 👤 Autor

**@selmirsabic91-spec**

---

## 🙋 Podrška

Ako imate pitanja ili problema:
1. Otvorite GitHub Issue
2. Opisite problem
3. Dodajte screenshot ako je moguće

**Hvala što igrate! 🌟**
