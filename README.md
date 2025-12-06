# SmartInvs (Overcast Community Fork)
Advanced Inventory API for your Minecraft Bukkit plugins.

*Tested Minecraft versions: 1.7, 1.8, 1.9, 1.10, 1.11, 1.12, 1.13, 1.14*
**You can use this as a Plugin, or use it as a library** (see [the docs](https://minuskube.gitbook.io/smartinvs/))

## Features
* Inventories of any type (workbench, chest, furnace, ...)
* Customizable size when possible (chest, ...)
* Custom titles
* Allows to prevent the player from closing its inventory
* Custom listeners for the event related to the inventory
* Iterator for inventory slots
* Page system
* Util methods to fill an inventory's row/column/borders/...
* Actions when player clicks on an item
* Update methods to edit the content of the inventory every tick

## Exclusive to this fork
* **Character Mask Layouts** - Define inventory layouts using character mask patterns, where each character represents a specific item.


## Usage
To use the SmartInvs API, either:
- Put it in the `plugins` folder of your server, add it to your dependencies in your plugin.yml (e.g. `depend: [SmartInvs]`) and add it to the dependencies in your IDE.
- Put it inside your plugin jar, initialize an `InventoryManager` in your plugin (don't forget to call the `init()` method), and add a `.manager(invManager)` to your SmartInventory Builders.

You can also use a build system:
### Gradle
```gradle
repositories {
    maven { url 'https://repo.pgm.fyi/snapshots' }
}

dependencies {
    compile 'fr.minuskube.inv:smart-invs:1.2.7-OCC'
}
```

### Maven
```xml
<dependency>
  <groupId>fr.minuskube.inv</groupId>
  <artifactId>smart-invs</artifactId>
  <version>1.2.7-OCC</version>
</dependency>
```

## Character Mask Layouts

Define inventory layouts visually using character patterns, where each character represents a specific region or slot type.

```java
SmartInventory.builder()
    .id("my-menu")
    .title("My Menu")
    .layout(new CharacterMaskLayout(
        "#########",
        "#   A   #",
        "#  BBB  #",
        "#   C   #",
        "### X ###"
    ))
    .provider((player, contents) -> {
        contents.fillChar('#', borderItem);      // Fill borders
        contents.fillChar('A', infoButton);      // Info slot
        contents.fillChar('B', actionButtons);   // Action slots
        contents.fillChar('C', settingsButton);  // Settings slot
        contents.fillChar('X', closeButton);     // Close button
    })
    .build();
```

**Key features:**
- Space characters (`' '`) represent empty slots
- Specific characters represent different items
- Perfect for creating custom shapes, borders, and visual layouts
- Speeds up development time
- Fully backward compatible with existing code
