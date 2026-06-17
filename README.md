# CryptBuddy 🔐

Локальный менеджер паролей для Android. Приложение позволяет создавать локальные аккаунты, хранить пароли от сайтов, генерировать надёжные пароли и импортировать/экспортировать данные в CSV.

## Возможности

- 👤 Регистрация и вход (локальные аккаунты, сессия сохраняется)
- 🔑 Хранилище паролей: сайт, логин, пароль, заметки
- 🔍 Поиск и сортировка паролей (по названию, по дате обновления)
- 🖼️ Отображение favicon сайта рядом с каждой записью
- 📋 Копирование логина/пароля в буфер обмена
- 🎲 Генератор паролей (длина 4–64, спецсимволы, цифры)
- 📥 Импорт / 📤 экспорт паролей в CSV (Storage Access Framework)
- ➕ Добавление и редактирование записей с валидацией URL

## Технологии

| Категория | Технологии |
|-----------|-----------|
| Язык | **Kotlin** 1.9.23 |
| UI | **Jetpack Compose** + **Material 3** |
| База данных | **Room** 2.6.1 (SQLite) |
| Навигация | **Navigation Compose** 2.7.7 |
| Изображения | **Coil** (coil-compose 2.6.0) — favicons |
| Сеть | **Ktor** client 1.6.7 |
| Время | kotlinx-datetime 0.6.0-RC.2 |
| Min SDK | 33 (Android 13+) |
| Target SDK | 34 |

## Запуск

1. Клонируй репозиторий:
   ```bash
   git clone https://github.com/Qwan-Chi/CryptBuddy.git
   ```
2. Открой в **Android Studio**
3. Дождись Gradle sync
4. Запусти на устройстве/эмуляторе (API ≥ 33)

```bash
./gradlew assembleDebug
```

## Структура проекта

```
app/src/main/java/com/qwanchi/cryptbuddy/
├── MainActivity.kt            # Точка входа, обработка SAF
├── DB/                        # Room: Database, Entities, DAOs
├── screens/                   # Start, Register, CryptApp, Password, Generator, Settings
├── dialogs/                   # AddPasswordDialog, EditPasswordDialog
├── components/                # PasswordCard
├── classes/                   # Checks, Generation, Exporter, Importer
├── nav/                       # Navigation (NavHost)
└── ui/theme/                  # Material 3 тема
```

## Лицензия

GNU GPL
