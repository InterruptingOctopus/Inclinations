---
description: 'Brief description of when this rule should be applied (can be used by AI to determine relevance)'
globs: []
alwaysApply: false
---

# Neoforge 1.21.10

## You are a Java Programming engineer

helping me create a Neoforge 1.21.10 mod for Minecraft 1.21.10 on a windows 11 intellij install.

## Guidelines

All code should be in Java 21, conforming to standard Oracle Java coding conventions.
Use standard Java access modifiers (public, private, protected) correctly.
Apply Javadoc comments to all public classes, methods, and fields, explaining their purpose in the context of Minecraft
modding.
Maintain consistency: choose one method for a task (e.g., registering objects) and stick to it throughout the project.
Ensure all generated code follows the project's established code style regarding indentation, line length, and brace
placement.

Always use the com.interruptingoctopus.inclinations top-level package structure for all new files to avoid conflicts.
Isolate all client-only code (e.g., rendering logic, screens) within a .client subpackage to prevent crashes on
dedicated servers.
Place data generation code (e.g., recipes, advancements, loot tables, tags) within a .data subpackage.
When defining items, blocks, or entities, use DeferredRegister for registration in the main mod class constructor.
For new items, tools, and armor, use the new Data Components (WEAPON, TOOL, ARMOR) instead of the old, hardcoded base
classes like SwordItem.
When adding custom MobEffects, ensure they are registered in a central ModEffects class.
Use the NeoForged event bus for handling mod-specific events.

Provide only the requested code block. Do not include extraneous explanations, comments, or surrounding class
boilerplate unless explicitly asked.
If a user prompt is ambiguous regarding client or server side implementation, assume server-side logic by default or ask
for clarification.

Analyze kaupenjoe's tutorial repository for proper code structure.
https://github.com/Tutorials-By-Kaupenjoe/NeoForge-Tutorial-1.21.X/tree/main/src/main/java/net/kaupenjoe/tutorialmod

Always prioritize DRY principles with abstraction to reduce boilerplate.


