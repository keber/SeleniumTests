[![Java CI](https://github.com/keber/SeleniumTests/actions/workflows/testrun.yml/badge.svg)](https://github.com/keber/SeleniumTests/actions/workflows/testrun.yml)
[![Tests](https://img.shields.io/endpoint?url=https%3A%2F%2Fgist.githubusercontent.com%2Fkeber%2Fbf1bff0a38948277a263377401536440%2Fraw%2FSeleniumTests-junit-tests.json)](https://keber.github.io/SeleniumTests/)

# SeleniumTests – Automatización funcional sobre Wallet Digital

<img width="1350" height="754" alt="image" src="https://github.com/user-attachments/assets/53c902d5-4a76-43ba-a58e-f7bd876a926c" />

---

Este proyecto corresponde a la entrega del Módulo 4 del curso de Automatización de Pruebas Funcionales. Contiene una suite de pruebas automatizadas sobre la aplicación [Wallet Digital](https://wallet.keber.cl), validando los flujos críticos de **registro** e **inicio de sesión**.

---

## Objetivos

- Validar el correcto funcionamiento de los formularios de **registro** y **login**.
- Detectar errores funcionales en etapas tempranas mediante pruebas automatizadas.
- Ejecutar pruebas en **Chrome y Firefox** usando **Selenium WebDriver** y **TestNG**.
- Generar evidencias (capturas, logs y reportes) para respaldar los resultados de ejecución.
- Integrar la suite en un flujo de CI/CD con GitHub Actions y publicar reportes automáticamente.

---

## Estructura del Proyecto

```
├── pom.xml           # Configuración del proyecto Maven y perfiles
├── screenshots/      # Capturas de pantalla de errores o ejecuciones
├── selenium-ide/     # Flujo grabado inicial en Selenium IDE (.side)
└── src/
│   ├── main/java/org/example/
│   │   ├── drivers/      # WebDriverManager + patrón Strategy
│   │   └── testng/       # Clase dummy (Login.java)
│   └── test/java/org/example/
│       ├── junit/        # demo de tests con playwright y selenium
│       ├── pages/        # Page Objects (LoginPage, etc.)
│       ├── testng/       # Pruebas TestNG (LoginTest.java)
│       ├── utils/        # Utilidades generales (Utils.java)
│       └── resources/
│           ├── testng-login.xml       # Suite de pruebas TestNG con parámetros
│           ├── testng-register.xml    # Suite de pruebas TestNG con parámetros
│           ├── credentials.tsv        # Datos de prueba login (TSV)
│           └── registerTest.csv       # Datos de prueba register (CSV)
└── testng-login.xml      # Configuración de suite
```

## Requisitos

- Java 17+
- Maven 3+
- Chrome y Firefox instalados (o usar runners en GitHub Actions)

---

## Ejecución local

1. Clonar el repositorio:

```bash
git clone https://github.com/keber/SeleniumTests.git
cd SeleniumTests
```

Ejecutar pruebas en ambos navegadores:

```bash
mvn clean verify -P testng-tests
```
Nota: El navegador se define por parámetro browser desde testng-login.xml.

## Reportes
* Las ejecuciones generan capturas de pantalla en /screenshots.
* Los reportes de pruebas en este repositorio se publican automáticamente en [Reporte en GitHub Pages](https://keber.github.io/SeleniumTests)

## Escenarios cubiertos
* Registro exitoso (flujo grabado en Selenium IDE)
* Registro fallido (campos vacíos, formatos inválidos)
* Login exitoso con credenciales válidas
* Login fallido con múltiples combinaciones (desde CSV)
* Validaciones de mensajes de error y comportamiento esperado

## Herramientas utilizadas
* Java 17
* Selenium WebDriver
* TestNG + DataProvider
* WebDriverManager
* Maven (con perfiles para Selenium, TestNG y Playwright)
* GitHub Actions
* GitHub Pages
* Allure Reports (opcionalmente integrable)

## Autor
* Keber Flores
* https://portfolio.keber.cl
* https://wallet.keber.cl
* https://github.com/keber
* https://linkedin.com/in/keber
