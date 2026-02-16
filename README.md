# 🎮 JustRPG API

> Lightweight utility framework for Bukkit/Spigot plugin development

[![Java](https://img.shields.io/badge/Java-8-orange.svg)](https://www.oracle.com/java/)
[![Bukkit](https://img.shields.io/badge/Bukkit-1.8+-brightgreen.svg)](https://bukkit.org/)
[![License](https://img.shields.io/badge/License-MIT-blue.svg)](LICENSE)

## 📖 About

JustRPG API is a streamlined utility framework for Bukkit/Spigot plugin development. It provides essential tools and abstractions to accelerate plugin development with clean, reusable components.

## ✨ Features

### 💾 Dual Database Support

Support for both MySQL and SQLite with automatic switching:

```java
// Configuration (config.yml)
config:
  database:
    mode: "mysql"  # or "sqlite"
    tableprefix: "rpg_"
    
    # MySQL settings
    mysql:
      host: "localhost"
      port: 3306
      user: "root"
      pass: "password"
      name: "minecraft"
    
    # SQLite settings
    sqlite:
      name: "database.db"

// Usage
Store store = RPG.getStore();

// Create table
RPG.addMYSQLTable("CREATE TABLE IF NOT EXISTS `%P%users` (...)");

// Query with callback
store.query("SELECT * FROM `%P%users`", resultSet -> {
    while (resultSet.next()) {
        String name = resultSet.getString("name");
    }
});

// Update (async)
store.update(false, "UPDATE `%P%users` SET points=10 WHERE uuid='...'");
```

**Features:**
- Automatic MySQL/SQLite switching
- Connection pooling
- Table prefix support (`%P%` placeholder)
- Async query support with callbacks
- Entry interface for data models

### 🎮 Command System

Simplified command registration:

```java
public class MyCommand extends Command {
    public MyCommand() {
        super("mycommand", "Command description", "/mycommand <args>", "my.permission");
    }
    
    @Override
    public boolean onExecute(CommandSender sender, String[] args) {
        sender.sendMessage("Command executed!");
        return true;
    }
}

// Register
RPG.registerCommand(new MyCommand());
```

**Features:**
- Permission-based access
- Usage help text
- Description metadata
- Automatic registration

### 📦 Inventory GUI System

Create interactive chest menus:

```java
InventoryGUI gui = new InventoryGUI(plugin, "&cMy Menu", 3); // 3 rows

// Set item with click action
gui.setItem(0, new ItemStack(Material.DIAMOND), (player, inventory, slot, event) -> {
    player.sendMessage("You clicked a diamond!");
    return true;
});

// Open action
gui.setOpenAction((player, inventory, slot, event) -> {
    player.sendMessage("Menu opened!");
    return true;
});

// Close action
gui.setCloseAction((player, inventory, slot, event) -> {
    player.sendMessage("Menu closed!");
    return true;
});

// Open for player
gui.openInventory(player);
```

**Features:**
- Click handlers per slot
- Open/Close event handlers
- Automatic event registration
- Builder pattern support

### 📝 Config System

Reflection-based configuration management:

```java
public class MyConfig extends ConfigCreator {
    public static String MY_VALUE;
    public static int MY_NUMBER;
    public static boolean MY_BOOLEAN;
    
    public MyConfig() {
        super("config.yml", "My Config");
    }
    
    @Override
    public void loadConfig() {
        MY_VALUE = getString("my.value", "default");
        MY_NUMBER = getInt("my.number", 100);
        MY_BOOLEAN = getBoolean("my.boolean", true);
    }
}

// Register
RPG.registerConfigCreator(new MyConfig());

// Reload
MyConfig.reloadConfig();
```

**Features:**
- Automatic field mapping
- Default value support
- Hot reload capability
- Multiple config files support
- Reflection-based field setting

### ⏱️ Timer System

Visual countdown timers:

```java
TimerUtil.createTimer(player, 10, (timer) -> {
    player.sendMessage("Timer finished!");
});
```

**Features:**
- Title-based display
- Countdown visualization
- Completion callbacks
- Automatic cleanup

### 🧰 Utility Classes

#### ItemBuilder
```java
ItemStack item = new ItemBuilder(Material.DIAMOND_SWORD)
    .setTitle("&cMy Sword")
    .addLore("&7Line 1", "&7Line 2")
    .addEnchantment(Enchantment.DAMAGE_ALL, 5)
    .build();
```

#### TimeUtil
```java
long millis = TimeUtil.SECOND.getTime(30);  // 30 seconds
long ticks = TimeUtil.MINUTE.getTick(5);    // 5 minutes
String formatted = TimeUtil.getDate(timestamp);
```

#### Reflection
```java
// Get NMS class
Class<?> nmsClass = Reflection.getNMSClass("EntityPlayer");

// Get CraftBukkit class
Class<?> craftClass = Reflection.getCraftBukkitClass("entity.CraftPlayer");

// Get field
Object value = Reflection.getField(object, "fieldName");
```

#### RandomUtil
```java
int random = RandomUtil.getRandInt(1, 100);
double randomDouble = RandomUtil.getRandDouble(0.0, 1.0);
boolean randomBool = RandomUtil.getChance(50); // 50% chance
```

#### Util
```java
// Color codes
String colored = Util.fixColor("&aGreen &cRed");

// Send message
Util.sendMsg(player, "&aMessage");

// Replace placeholders
String replaced = Util.replaceString("{PLAYER}", player.getName());
```

#### Logger
```java
Logger.info("Information message");
Logger.warning("Warning message");
Logger.severe("Error message");
Logger.sendFixFormatedColoredToConsole("&aColored &cmessage");
```

## 🚀 Getting Started

### Installation

1. **Add as dependency in your plugin:**

```xml
<!-- Maven -->
<dependency>
    <groupId>pl.justrpg</groupId>
    <artifactId>rpg-api</artifactId>
    <version>1.0.0</version>
</dependency>
```

2. **Add dependency in plugin.yml:**

```yaml
name: MyPlugin
version: 1.0.0
main: com.example.MyPlugin
depend: [RPGAPI]
```

3. **Place RPGAPI.jar in plugins folder**

### Configuration

Create `plugins/RPGAPI/config.yml`:

```yaml
config:
  enabled: true
  
  database:
    mode: "mysql"  # "mysql" or "sqlite"
    tableprefix: "rpg_"
    
    mysql:
      host: "localhost"
      port: 3306
      user: "root"
      pass: "password"
      name: "minecraft"
    
    sqlite:
      name: "database.db"
  
  # Optional: Socket server
  socket:
    password: "ZAQ!2wsx"
    port: 1337
  
  api:
    gui:
      name: "&c&lAPI Manager"
  
  server:
    type: "rpg"
```

### Basic Usage

```java
public class MyPlugin extends JavaPlugin {
    
    @Override
    public void onEnable() {
        // Register command
        RPG.registerCommand(new MyCommand());
        
        // Register listener
        RPG.registerListener(this, new MyListener());
        
        // Register config
        RPG.registerConfigCreator(new MyConfig());
        
        // Access database
        Store store = RPG.getStore();
        RPG.addMYSQLTable("CREATE TABLE IF NOT EXISTS `%P%mytable` (...)");
    }
}
```

## 📚 API Reference

### Core Classes

| Class | Purpose |
|-------|---------|
| `RPG` | Main API class |
| `Store` | Database interface |
| `Command` | Base command class |
| `ConfigCreator` | Config base class |
| `InventoryGUI` | GUI builder |
| `Entry` | Database entity interface |

### Store Modes

```java
public enum StoreMode {
    MYSQL,    // MySQL database
    SQLITE    // SQLite database
}
```

The API automatically selects the appropriate storage backend based on configuration.

### Database Operations

```java
// Synchronous query
ResultSet rs = store.query("SELECT * FROM users");

// Asynchronous query with callback
store.query("SELECT * FROM users", resultSet -> {
    // Process results
});

// Synchronous update
store.update(true, "INSERT INTO users VALUES (...)");

// Asynchronous update
store.update(false, "UPDATE users SET ...");
```

## 🔧 Advanced Features

### Entry Interface

Create auto-save entities:

```java
public class User implements Entry {
    private UUID uuid;
    private String name;
    private int points;
    
    @Override
    public void insert() {
        String sql = "INSERT INTO `%P%users` VALUES (...)";
        RPG.getStore().update(true, sql);
    }
    
    @Override
    public void update(boolean now) {
        String sql = "UPDATE `%P%users` SET ... WHERE uuid='" + uuid + "'";
        RPG.getStore().update(now, sql);
    }
    
    @Override
    public void delete() {
        String sql = "DELETE FROM `%P%users` WHERE uuid='" + uuid + "'";
        RPG.getStore().update(true, sql);
    }
}
```

### Custom Timer Callbacks

```java
TimerCallback callback = new TimerCallback() {
    @Override
    public void done(Timer timer) {
        Player player = timer.getPlayer();
        player.sendMessage("Timer completed!");
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
    }
};

TimerUtil.createTimer(player, 30, callback);
```

### Inventory Actions

```java
gui.setItem(10, diamond, (player, inv, slot, event) -> {
    // Access event details
    ClickType clickType = event.getClick();
    boolean isShiftClick = event.isShiftClick();
    
    // Perform actions
    if (clickType == ClickType.LEFT) {
        player.sendMessage("Left click!");
    }
    
    // Cancel event
    event.setCancelled(true);
    
    // Return true to handle
    return true;
});
```

## 📦 Package Structure

```
pl.justrpg.api/
├── RPG.java                    # Main API class
│
├── commands/                   # Command system
│   ├── Command.java
│   ├── CommandManager.java
│   └── PlayerCommand.java
│
├── configs/                    # Config system
│   ├── Config.java             # Main config
│   ├── ConfigCreator.java      # Base class
│   └── ConfigManager.java
│
├── inventory/                  # GUI system
│   ├── InventoryGUI.java
│   └── IAction.java
│
├── store/                      # Database
│   ├── Store.java
│   ├── Entry.java
│   ├── Callback.java
│   ├── StoreMode.java
│   └── modes/
│       ├── StoreMySQL.java
│       └── StoreSQLITE.java
│
├── timer/                      # Timer system
│   ├── TimerUtil.java
│   ├── TimerCallback.java
│   └── TimerManager.java
│
└── util/                       # Utilities
    ├── ItemBuilder.java
    ├── TimeUtil.java
    ├── Reflection.java
    ├── RandomUtil.java
    ├── Logger.java
    └── Util.java
```

## 🆚 Comparison with Other APIs

| Feature | JustRPG API | BlackWater API |
|---------|-------------|----------------|
| MySQL Support | ✅ | ✅ |
| SQLite Support | ✅ | ❌ |
| Socket Support | ⚠️ (Basic) | ✅ (WebSocket) |
| GUI System | ✅ | ✅ |
| Command System | ✅ | ✅ |
| Timer System | ✅ | ✅ |
| Reflection Utils | ✅ | ✅ |

## 📝 Example Plugin

```java
public class ExamplePlugin extends JavaPlugin {
    
    private Store store;
    
    @Override
    public void onEnable() {
        // Get database
        store = RPG.getStore();
        
        // Create tables
        RPG.addMYSQLTable(
            "CREATE TABLE IF NOT EXISTS `%P%players` (" +
            "uuid VARCHAR(36) PRIMARY KEY," +
            "name VARCHAR(16)," +
            "points INT" +
            ")"
        );
        
        // Register commands
        RPG.registerCommand(new PointsCommand());
        
        // Register listeners
        RPG.registerListener(this, new JoinListener());
    }
    
    public void addPoints(UUID uuid, int points) {
        store.update(false, 
            "UPDATE `%P%players` SET points = points + " + points + 
            " WHERE uuid = '" + uuid + "'"
        );
    }
}
```

## 🤝 Contributing

Contributions are welcome! Feel free to submit pull requests.

## 📄 License

This project is licensed under the MIT License.

## 👨‍💻 Author

**JustRPG Development**
- Package: `pl.justrpg.api`

---

**Lightweight. Simple. Effective.**
