# Inclinations

A NeoForge Minecraft mod project.

## Project Information

- **Project Name**: Inclinations
- **Version**: 0.0.3
- **License**: MIT
- **Author**: InterruptingOctopus
- **Description**: Stuff I was inclined to make.
- **Mod ID**: `inclinations`

## Environment

- **Minecraft Version**: 1.21.1
- **NeoForge Version**: 21.1.215
- **Java Version**: 21
- **Gradle**: Gradle Wrapper (included)

## Project Structure

```
src/
├── main/
│   ├── java/
│   │   └── com/interruptingoctopus/inclinations/
│   ├── resources/
│   │   ├── assets/
│   │   └── data/
│   └── templates/
│       └── META-INF/
└── generated/
    └── resources/

build/
├── classes/
├── libs/
├── reports/
└── ...

gradle/
└── wrapper/
    ├── gradle-wrapper.jar
    └── gradle-wrapper.properties
```

## Build Configuration

- **Build Tool**: Gradle with NeoForge ModDev plugin
- **Plugin Version**: 2.0.116
- **Mapping**: Parchment (Version: 2024.11.17)
- **Package**: `com.interruptingoctopus.inclinations`

## Run Configurations

The project includes the following Gradle run configurations:
- **client**: Launches the Minecraft client with the mod
- **server**: Launches a Minecraft server with the mod
- **gameTestServer**: Runs registered game tests
- **data**: Generates mod data (recipes, loot tables, etc.)

## Build and Setup

### Initial Setup

1. Open the project in IntelliJ IDEA or Eclipse
2. If you're missing libraries, run:
   ```
   gradlew --refresh-dependencies
   ```

### Building

The project uses Gradle with the NeoForge ModDev plugin:
- `gradlew build` - Build the mod
- `gradlew clean` - Clean build artifacts
- `gradlew runClient` - Run Minecraft client with the mod
- `gradlew runServer` - Run Minecraft server with the mod

## Dependencies

- **NeoForge**: Core mod framework
- **Parchment**: Enhanced method/field names for Minecraft classes

## Mapping Information

The project uses official mapping names from Mojang for methods and fields in the Minecraft codebase. These names are covered by a specific license. For the latest license text, see: https://github.com/NeoForged/NeoForm/blob/main/Mojang.md

## Resources

- **Community Documentation**: https://docs.neoforged.net/
- **NeoForged Discord**: https://discord.neoforged.net/

## Configuration

- **Gradle Memory**: 1GB (configurable in `gradle.properties`)
- **Character Encoding**: UTF-8
- **IDE Support**: Downloads sources and Javadoc automatically for better development experience
