# 🚀 Space Invaders — Design Patterns

A Java 17 / JavaFX project demonstrating how classic object-oriented design patterns can be applied to a complete game architecture.

![Java](https://img.shields.io/badge/Java-17%2B-orange)
![JavaFX](https://img.shields.io/badge/JavaFX-21%2B-blue)
![Maven](https://img.shields.io/badge/Maven-3.8%2B-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

## Overview

This project is an enhanced **Space Invaders** implementation built to demonstrate maintainable, extensible software design.

The architecture uses four complementary patterns:

| Pattern | Role |
|---|---|
| **State** | Manages menu, gameplay, pause and game-over states |
| **Factory** | Centralizes creation of enemies, projectiles and power-ups |
| **Decorator** | Adds player abilities dynamically through power-ups |
| **Composite** | Represents individual enemies and enemy formations uniformly |

The project also applies principles such as **SOLID**, separation of responsibilities, logging and UML documentation.

## Key features

- Multiple enemy types with different behaviors
- Progressive waves and scoring
- Temporary power-ups
- Menu, gameplay, pause and game-over states
- JavaFX graphical interface
- Log4j2 application logging
- Unit and integration testing
- UML architecture documentation

## Tech stack

- **Java 17+**
- **JavaFX 21**
- **Maven**
- **Log4j2**
- **PlantUML**

## Getting started

### Prerequisites

- Java 17+
- Maven 3.8+

### Run

```bash
git clone https://github.com/MedAmineGobji/ProjetDesignPattern.git
cd ProjetDesignPattern
mvn clean compile
mvn javafx:run
```

### Test

```bash
mvn test
```

## Architecture

The source code is organized around the four patterns:

```text
src/main/java/fr/university/spaceinvaders/
├── state/
├── factory/
├── decorator/
├── composite/
└── entities/
```

The UML documentation is available in `docs/`.

## What this project demonstrates

- Object-oriented design
- Pattern selection and justification
- Extensible architecture
- Clean separation of responsibilities
- Practical Java/JavaFX development
- Testing and technical documentation

## License

MIT

---

**Author:** [Med Amine Gobji](https://github.com/MedAmineGobji)
