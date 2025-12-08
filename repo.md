# Inclinations Mod

**Mod ID**: `inclinations`  
**Mod Name**: Interrupting Octopus' Inclinations  
**Version**: 1.0.0  
**License**: MIT  
**Author**: InterruptingOctopus  
**Description**: A collection of spur of the moment ideas I wanted to bring to minecraft.

---

## Environment

- **Minecraft Version**: 1.21.10
- **NeoForge Version**: 21.10.56-beta
- **Java Version**: Java 21
- **Parchment Mappings**: 2025.10.12

---

## Project Structure

```
src/
├── main/
│   ├── java/com/interruptingoctopus/inclinations/
│   │   ├── Inclinations.java                           // Main mod class
│   │   ├── Config.java                                 // Configuration settings
│   │   ├── block/
│   │   │   ├── AltarBlock.java                        // Custom altar block implementation
│   │   │   ├── ModBlocks.java                         // Block registration
│   │   │   └── entity/
│   │   │       ├── AltarBlockEntity.java              // Custom block entity for altar
│   │   │       └── ModBlockEntities.java              // Block entity registration
│   │   ├── item/
│   │   │   ├── ModItems.java                          // Item registration
│   │   │   ├── ModCreativeModeTabs.java               // Creative mode tabs
│   │   │   └── custom/
│   │   │       └── ScrewDriver.java                   // Custom screwdriver item
│   │   ├── datagen/
│   │   │   ├── DataGenerators.java                    // Main data generation handler
│   │   │   ├── ModRecipeProvider.java                 // Recipe data generation
│   │   │   ├── ModLangProvider.java                   // Language file generation
│   │   │   ├── ModModelProvider.java                  // Model data generation
│   │   │   ├── ModBlockLootTableProvider.java         // Loot table data generation
│   │   │   ├── ModBlockTagProvider.java               // Block tag data generation
│   │   │   └── ModItemTagProvider.java                // Item tag data generation
│   │   ├── events/
│   │   │   └── ModEvents.java                         // Event handlers
│   │   ├── recipe/
│   │   │   └── ModRecipes.java                        // Recipe definitions
│   │   ├── screen/
│   │   │   ├── AltarMenu.java                         // Altar container/menu
│   │   │   ├── ModMenuTypes.java                      // Menu type registration
│   │   │   └── custom/
│   │   ├── util/
│   │   │   ├── ModMetals.java                         // Metal utilities
│   │   │   └── ModTags.java                           // Tag utilities
│   │   └── client/gui/
│   │       ├── ModCustomOverlay.java                  // Client-side custom overlay
│   │       ├── PlayerAttributeMenuScreen.java         // Player attribute menu GUI
│   │       ├── PlayerAttributeMenuTabButton.java      // Tab button for attribute menu
│   │       └── PlayerInventoryTabButton.java          // Tab button for inventory
│   └── resources/
│       ├── assets/inclinations/                       // Client-side assets
│       │   ├── textures/                              // Block/item textures
│       │   ├── models/                                // Block/item models
│       │   ├── blockstates/                           // Block state definitions
│       │   └── lang/                                  // Language files
│       ├── data/inclinations/                         // Server-side data
│       │   ├── recipes/                               // Recipe JSON files
│       │   └── loot_tables/                           // Loot table definitions
│       └── META-INF/
│           └── neoforge.mods.toml                     // Mod metadata
└── generated/resources/                               // Generated data (data generators output)
```

---

## Core Features

### Blocks

- **Altar Block** (`AltarBlock.java`): Custom block with block entity support
    - Implements `AltarBlockEntity.java` for custom block behavior
    - Full entity management and data persistence
    - Custom container/menu system via `AltarMenu.java`

### Items

- **Screwdriver** (`ScrewDriver.java`): Custom tool item implementation
- Dynamic creative mode tabs management
- Metal ingots and nuggets for three ore types (Silver, Mithril, Platinum)

### Recipes & Crafting

- Recipe provider for data generation
- Customizable recipe definitions in `ModRecipes.java`
- Loot table generation for ore blocks
- Block and item tag system for flexible configuration

### GUI & UI

- **Player Attribute Menu** (`PlayerAttributeMenuScreen.java`): Custom GUI screen for player attributes
- **Menu System** (`ModMenuTypes.java`, `AltarMenu.java`): Container-based menu system
- **Tab Button System**: Extensible tab button interface for menu navigation
    - `PlayerAttributeMenuTabButton.java`
    - `PlayerInventoryTabButton.java`
- **Custom Overlay** (`ModCustomOverlay.java`): HUD overlay for client-side information display

### Events & Gameplay

- Comprehensive event handlers in `ModEvents.java`
- Custom overlay system for client-side UI

### Utilities

- **Metal Utilities** (`ModMetals.java`): Extensible metal support system
- **Tag Utilities** (`ModTags.java`): Block and item tag management

### Configuration

- `Config.java`: Mod configuration system for customizable settings

---

## Items & Blocks Registry

### Metals

The mod supports three custom metals with full block and item support:

#### Silver

- **Silver Block**: Metal block with strength 3.0, blast resistance 6.0
- **Silver Ore**: Stone ore with strength 3.0, blast resistance 3.0
- **Raw Silver Block**: For storing raw ore
- **Silver Ingot**: Crafted metal form
- **Silver Nugget**: Crafting ingredient for ingot

#### Platinum

- **Platinum Block**: Stronger metal block with strength 5.0, blast resistance 6.0
- **Platinum Ore**: Stone ore with strength 3.0, blast resistance 3.0
- **Raw Platinum Block**: For storing raw ore
- **Platinum Ingot**: Crafted metal form
- **Platinum Nugget**: Crafting ingredient for ingot

#### Mithril

- **Mithril Block**: Extremely durable with strength 50.0, blast resistance 1200.0
- **Mithril Ore**: Rare ore with strength 30.0, blast resistance 1200.0
- **Raw Mithril Block**: For storing raw ore
- **Mithril Ingot**: Crafted metal form
- **Mithril Nugget**: Crafting ingredient for ingot

### Custom Items

- **Screw Driver**: Custom tool item for specialized crafting

### Blocks

- **Altar Block**: Custom interactive block with block entity support and menu system

---

## Build Configuration

### Gradle Wrapper

- Gradle version: Latest (via wrapper)
- Build system: Gradle 8.x

### Build Tasks

- `build` - Compile and package the mod
- `runClient` - Run Minecraft client with mod
- `runServer` - Run Minecraft server with mod (headless)
- `runGameTestServer` - Run game test server
- `runData` - Run data generators

### Key Gradle Properties

```properties
org.gradle.jvmargs=-Xmx1G
org.gradle.daemon=true
org.gradle.parallel=true
org.gradle.caching=true
org.gradle.configuration-cache=true
```

---

## Mod Metadata

### Package Group

`com.interruptingoctopus.inclinations`

### Mod Configuration

- Metadata template: `src/main/templates/META-INF/neoforge.mods.toml`
- Generated resources: `src/generated/resources/`
- Mapped names: Official Mojang mappings via Parchment

---

## Development Setup

### IDE Setup

- **Recommended IDE**: IntelliJ IDEA Community Edition or Eclipse
- IDEA is configured to:
    - Download sources for dependencies
    - Download Javadoc for dependencies
    - Enable IDE sync with `generateModMetadata` task

### First Time Setup

1. Clone the repository
2. Open in IDE (IntelliJ IDEA or Eclipse)
3. IDE should auto-sync Gradle configuration
4. If issues occur, run: `gradlew --refresh-dependencies`
5. To reset build cache: `gradlew clean`

### Running the Mod

- **Client**: Use `runClient` configuration in IDE or `gradlew runClient`
- **Server**: Use `runServer` configuration in IDE or `gradlew runServer`
- **Data Generation**: Use `runData` configuration or `gradlew runData`

---

## Resource Management

### Data Generation

The mod uses Gradle-based data generation for:

- **Recipes**: `ModRecipeProvider.java` - Crafting recipe definitions
- **Language Files**: `ModLangProvider.java` - Localization strings (en_us)
- **Block/Item Models**: `ModModelProvider.java` - Model JSON files
- **Loot Tables**: `ModBlockLootTableProvider.java` - Block drop definitions
- **Block Tags**: `ModBlockTagProvider.java` - Block grouping and categorization
- **Item Tags**: `ModItemTagProvider.java` - Item grouping and categorization

Generated resources are output to `src/generated/resources/` and automatically included in the build.

### Run Data Generation

Execute data generation with:

```bash
gradlew runData
```

---

## Publishing

### Maven Publication

- Maven repository configured at: `repo/` (local filesystem)
- Publication ID: `mavenJava`
- Build artifact name: `inclinations`

To publish: `gradlew publish`

---

## Dependencies

### Runtime Dependencies

Currently no external mod dependencies configured. Can be added to `build.gradle` dependencies block.

### Optional Dependencies

Template includes examples for:

- JEI (Just Enough Items) mod integration
- Dependency management via Maven repositories or local libs

---

## Logging

### Log Level

- Default: `DEBUG`
- Markers: `REGISTRIES` (for registry events)

### Recommended Markers for Debugging

- `SCAN` - For mod scanning
- `REGISTRIES` - For registry events
- `REGISTRYDUMP` - For registry contents

---

## License & Attribution

- **Mod License**: MIT
- **Minecraft Mappings**: Official Mojang mappings (see https://github.com/NeoForged/NeoForm/blob/main/Mojang.md)

---

## Community Resources

- **NeoForged Documentation**: https://docs.neoforged.net/
- **NeoForged Discord**: https://discord.neoforged.net/
- **License Mapping File**: https://github.com/NeoForged/NeoForm/blob/main/Mojang.md

---

## Troubleshooting

### Common Issues & Solutions

**Issue**: Missing libraries in IDE

- **Solution**: Run `gradlew --refresh-dependencies` to refresh the local Gradle cache

**Issue**: Build errors after environment changes

- **Solution**: Run `gradlew clean` to reset the build cache, then rebuild

**Issue**: Gradle daemon issues

- **Solution**: Run `gradlew --stop` to halt any running daemon, then try again

**Issue**: IDE not recognizing generated resources

- **Solution**: Rebuild the project in your IDE or run `gradlew build`

### Debug Commands

```bash
# Full clean rebuild
gradlew clean build

# Run data generation
gradlew runData

# Launch game client with mod
gradlew runClient

# Launch dedicated server with mod
gradlew runServer

# Refresh Gradle dependencies
gradlew --refresh-dependencies
```

---

## Development Notes

- **Java Version**: Requires Java 21 (as per Minecraft 1.21.10 requirements)
- **IDE Recommendations**: IntelliJ IDEA Community Edition or Eclipse
- **NeoForge Documentation**: https://docs.neoforged.net/
- **Parchment Mappings**: Using official Mojang names with Parchment for better IDE support
- **Lombok Usage**: Not required; all code uses standard Java patterns

---

## Build Artifacts

### Generated Logs

- `build_*.log` - Various build attempt logs
- `output.log`, `output.txt` - Output from builds/data generation
- `rundata.txt`, `full_rundata.log` - Data generation output
- `compile_*.txt` - Compilation output snapshots

These files are for debugging and can be safely deleted.

---

## Quick Reference

| Task                 | Command                          |
|----------------------|----------------------------------|
| Build Mod            | `gradlew build`                  |
| Run Client           | `gradlew runClient`              |
| Run Server           | `gradlew runServer`              |
| Generate Data        | `gradlew runData`                |
| Clean Cache          | `gradlew clean`                  |
| Refresh Dependencies | `gradlew --refresh-dependencies` |

