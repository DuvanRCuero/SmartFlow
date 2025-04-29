# SmartFlow

**SmartFlow** es una aplicación Android para gestionar productividad personal mediante un dashboard con chat, calendario, tareas y métricas de productividad.

---

## Tabla de contenidos

- [Descripción](#descripción)
- [Características](#características)
- [Instalación](#instalación)
- [Uso](#uso)
- [Entrega Sprint 1](#entrega-sprint-1)
- [Roles y permisos](#roles-y-permisos)
- [Licencia](#licencia)

---

## Descripción

SmartFlow ayuda a los usuarios a mantener su flujo de trabajo organizado:

1. **Autenticación de usuarios** (registro, login, logout) y persistencia de sesión.
2. **Dashboard** con navegación inferior a cuatro secciones:
    - Chat
    - Calendario
    - Tareas
    - Productividad
3. **Temas** basados en Material 3 y personalización de colores.
4. **Persistencia local** con DataStore para sesión y Room para datos de usuario.
5. **Inyección de dependencias** con Hilt.

---

## Características

- **Registro y Login** de usuarios con validación básica.
- **Logout** que limpia sesión y devuelve al login.
- **Dashboard** con barra superior personalizada y pestañas inferiores.
- **Pantallas**:
    - **Chat:** interfaz de mensajería.
    - **Calendario:** vista de eventos.
    - **Tareas:** lista y métricas de tareas completadas.
    - **Productividad:** gráficos de rendimiento.
- **Tema** dinámico con `MaterialTheme.colorScheme`.

---

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tuUsuario/smartflow2.git
   cd smartflow2
   ```
2. Abrir en Android Studio.
3. Asegurarse de tener:
    - Android SDK 35
    - JDK 11
4. Sincronizar Gradle y compilar:
   > Build → Clean Project → Rebuild Project

---

## Uso

- Ejecutar la app en un emulador o dispositivo con Android API 26+.
- Crear cuenta o iniciar sesión.
- Navegar entre secciones desde la barra inferior.
- Logout desde el icono en la esquina superior derecha.

---

## Entrega Sprint 1

- **Commit de entrega**: `git commit -m "Sprint1: autenticación básica (registro, login, Home)"`
    - Ejemplo de link: https://github.com/tuUsuario/smartflow2/commit/abcdef012345
- **Tag**: `v1.0-sprint1` → `git tag -a v1.0-sprint1 -m "Sprint 1: autenticación básica"`
- **APK de depuración**: `app/build/outputs/apk/debug/app-debug.apk`
- **Demo en video**: muestra flujo de registro y login con al menos dos usuarios.

---

## Roles y permisos

- **Rol: Usuario estándar**
    - Puede registrarse, iniciar sesión, navegar por todas las secciones y cerrar sesión.

- **Permisos Android**:
    - `INTERNET` (para futuras llamadas a API REST).
    - `ACCESS_NETWORK_STATE` (detectar conectividad).
    - No se requieren permisos adicionales en este sprint.

---

## Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

