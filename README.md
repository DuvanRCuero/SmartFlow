# SmartFlow

**SmartFlow** es una aplicación Android para gestionar productividad personal mediante un dashboard con chat, calendario, tareas y métricas de productividad.

---

## Tabla de contenidos

- [Descripción](#descripción)
- [Características](#características)
- [Arquitectura](#arquitectura)
- [Instalación](#instalación)
- [Uso](#uso)
- [Entrega Sprint 2](#entrega-sprint-2)
- [Entrega Sprint 1](#entrega-sprint-1)
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
3. **Temas** basados en Material 3 y personalización de colores.
4. **Persistencia local** con DataStore para sesión y Room para datos de usuario.
5. **Inyección de dependencias** con Hilt.
6. **Arquitectura MVVM** con ViewModels, StateFlow y Compose UI.

---

## Características

- **Registro y Login** de usuarios con validación básica.
- **Logout** que limpia sesión y devuelve al login.
- **Dashboard** con barra superior personalizada y pestañas inferiores.
- **Pantallas**:
    - **Chat:** interfaz de mensajería con visualización de conversaciones y entrada de texto.
    - **Calendario:** vista de eventos con calendario interactivo y progreso mensual/semanal.
    - **Tareas:** lista de tareas sugeridas con métricas de productividad y gráficos de completitud.
    - **Productividad:** bloques de tiempo y consejos personalizados para optimizar el trabajo.
- **Componentes comunes** para mantener consistencia de diseño en toda la aplicación.
- **Tema** dinámico con `MaterialTheme.colorScheme` y modo oscuro predeterminado.
- **Visualización de datos** con gráficos personalizados para métricas de productividad.

---

## Arquitectura

La aplicación sigue una arquitectura moderna basada en:

### Patrón MVVM (Model-View-ViewModel)
- **Models**: Representan los datos y la lógica de negocio
- **ViewModels**: Manejan el estado y la lógica de la UI, exponiendo StateFlows para cada pantalla
- **Views**: Implementadas con Jetpack Compose, observan y reaccionan a los estados

### Componentes principales:
- **Screens**: Contenedores principales para cada funcionalidad (ChatScreen, CalendarScreen, etc.)
- **Components**: Elementos de UI reutilizables específicos a cada pantalla
- **Common Components**: Elementos compartidos como tarjetas, botones y headers
- **Navigation**: Sistema de navegación centralizado con rutas definidas

### Estructura de carpetas:
```
com.example.smartflow
├── domain
│   └── repository
├── presentation
│   ├── auth
│   ├── calendar
│   ├── chat
│   ├── common
│   ├── home
│   ├── navigation
│   ├── productivity
│   ├── task
│   └── theme
└── ...
```

---

## Instalación

1. Clonar el repositorio:
   ```bash
   git clone https://github.com/tuUsuario/smartflow2.git
   cd smartflow2
   ```
2. Abrir en Android Studio.
3. Asegurarse de tener:
    - Android SDK 35
    - JDK 11
    - Compose BOM 2023.10.01
4. Sincronizar Gradle y compilar:
   > Build → Clean Project → Rebuild Project

---

## Uso

- Ejecutar la app en un emulador o dispositivo con Android API 26+.
- Crear cuenta o iniciar sesión.
- Navegar entre secciones desde la barra inferior.
- **Chat**: Enviar mensajes usando el campo de texto y botón de envío.
- **Calendario**: Ver eventos programados y navegar entre días del mes.
- **Tareas**: Visualizar métricas de completitud y agregar tareas sugeridas.
- **Productividad**: Revisar bloques de tiempo óptimos y seguir consejos personalizados.
- Logout desde el icono en la esquina superior derecha.

---

## Entrega Sprint 2

- **Commit de entrega**: `git commit -m "Sprint2: implementación de funcionalidades principales y mejoras de UI"`
    - Link: https://github.com/tuUsuario/smartflow2/commit/abcdef012345
- **Tag**: `v1.1-sprint2` → `git tag -a v1.1-sprint2 -m "Sprint 2: funcionalidades principales"`
- **APK de depuración**: `app/build/outputs/apk/debug/app-debug.apk`
- **Mejoras implementadas**:
    - Arquitectura MVVM completa con ViewModels para cada pantalla
    - Componentes UI modularizados y reutilizables
    - Navegación mejorada con back buttons y experiencia consistente
    - Visualización de datos con gráficos personalizados
    - Componente ScreenScaffold para estructura consistente entre pantallas
    - Implementación completa de flujos de estado con StateFlow

---

## Entrega Sprint 1

- **Commit de entrega**: `git commit -m "Sprint1: autenticación básica (registro, login, Home)"`
    - Ejemplo de link: https://github.com/DuvanRCuero/SmartFlow/commit/649368cf80454c0f505eb7622ca87e88f38df71c
- **Tag**: `v1.0-sprint1` → `git tag -a v1.0-sprint1 -m "Sprint 1: autenticación básica"`
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