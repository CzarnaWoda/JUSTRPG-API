package pl.justrpg.api.store.modes;

import pl.justrpg.api.RPG;
import pl.justrpg.api.configs.Config;
import pl.justrpg.api.store.Callback;
import pl.justrpg.api.store.Store;
import pl.justrpg.api.store.StoreMode;
import pl.justrpg.api.util.Logger;
import pl.justrpg.api.util.Timming;

import java.io.File;
import java.sql.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class StoreSQLITE implements Store
{
    private String name;
    private String prefix;
    private Connection conn;
    private Executor executor;
    
    public StoreSQLITE(String name, String prefix) {
        super();
        this.name = name;
        this.prefix = prefix;
        this.executor = Executors.newSingleThreadExecutor();
    }
    
    @Override
    public boolean connect() {
        Timming t = new Timming("SQLite ping").start();
        try {
            Class.forName("org.sqlite.JDBC");
            this.conn = DriverManager.getConnection("jdbc:sqlite:" + RPG.getPlugin().getDataFolder() + File.separator + Config.DATABASE_SQLITE_NAME);
            Logger.info("Connected to the SQLite server!", "Connection ping " + t.stop().getExecutingTime() + "ms!");
            return true;
        }
        catch (ClassNotFoundException e) {
            Logger.warning("JDBC driver not found!", "Error: " + e.getMessage());
            e.printStackTrace();
        }
        catch (SQLException e2) {
            Logger.warning("Can not connect to a SQLite server!", "Error: " + e2.getMessage());
            e2.printStackTrace();
        }
        return false;
    }
    
    @Override
    public void update(boolean now, final String update) {
        Runnable r = new Runnable() {
            @Override
            public void run() {
                try {
                    StoreSQLITE.this.conn.createStatement().executeUpdate(update.replace("{P}", StoreSQLITE.this.prefix));
                }
                catch (SQLException e) {
                    Logger.warning("An error occurred with given query '" + update.replace("{P}", StoreSQLITE.this.prefix) + "'!", "Error: " + e.getMessage());
                    e.printStackTrace();
                }
            }
        };
        if (now) {
            r.run();
        }
        else {
            this.executor.execute(r);
        }
    }
    
    @Override
    public ResultSet update(String update) {
        try {
            Statement statement = this.conn.createStatement();
            statement.executeUpdate(update.replace("{P}", this.prefix), 1);
            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                return rs;
            }
        }
        catch (SQLException e) {
            Logger.warning("An error occurred with given query '" + update.replace("{P}", this.prefix) + "'!", "Error: " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }
    
    @Override
    public void disconnect() {
        if (this.conn != null) {
            try {
                this.conn.close();
            }
            catch (SQLException e) {
                Logger.warning("Can not close the connection to the MySQL server!", "Error: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
    
    @Override
    public void reconnect() {
        this.connect();
    }
    
    @Override
    public boolean isConnected() {
        try {
            return !this.conn.isClosed() || this.conn == null;
        }
        catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @Override
    public ResultSet query(String query) {
        try {
            return this.conn.createStatement().executeQuery(query.replace("{P}", this.prefix));
        }
        catch (SQLException e) {
            Logger.warning("An error occurred with given query '" + query.replace("{P}", this.prefix) + "'!", "Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    
    @Override
    public void query(String query, Callback<ResultSet> cb) {
    }
    
    @Override
    public Connection getConnection() {
        return this.conn;
    }
    
    @Override
    public StoreMode getStoreMode() {
        return StoreMode.SQLITE;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public Connection getConn() {
        return this.conn;
    }
    
    public Executor getExecutor() {
        return this.executor;
    }
    
    public void setConn(Connection conn) {
        this.conn = conn;
    }
    
    public void setExecutor(Executor executor) {
        this.executor = executor;
    }
    
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof StoreSQLITE)) {
            return false;
        }
        StoreSQLITE other = (StoreSQLITE)o;
        if (!other.canEqual(this)) {
            return false;
        }
        Object this$name = this.getName();
        Object other$name = other.getName();
        Label_0065: {
            if (this$name == null) {
                if (other$name == null) {
                    break Label_0065;
                }
            }
            else if (this$name.equals(other$name)) {
                break Label_0065;
            }
            return false;
        }
        Object this$prefix = this.getPrefix();
        Object other$prefix = other.getPrefix();
        Label_0102: {
            if (this$prefix == null) {
                if (other$prefix == null) {
                    break Label_0102;
                }
            }
            else if (this$prefix.equals(other$prefix)) {
                break Label_0102;
            }
            return false;
        }
        Object this$conn = this.getConn();
        Object other$conn = other.getConn();
        Label_0139: {
            if (this$conn == null) {
                if (other$conn == null) {
                    break Label_0139;
                }
            }
            else if (this$conn.equals(other$conn)) {
                break Label_0139;
            }
            return false;
        }
        Object this$executor = this.getExecutor();
        Object other$executor = other.getExecutor();
        if (this$executor == null) {
            if (other$executor == null) {
                return true;
            }
        }
        else if (this$executor.equals(other$executor)) {
            return true;
        }
        return false;
    }
    
    protected boolean canEqual(Object other) {
        return other instanceof StoreSQLITE;
    }
    
    @Override
    public int hashCode() {
        int result = 1;
        Object $name = this.getName();
        result = result * 59 + (($name == null) ? 0 : $name.hashCode());
        Object $prefix = this.getPrefix();
        result = result * 59 + (($prefix == null) ? 0 : $prefix.hashCode());
        Object $conn = this.getConn();
        result = result * 59 + (($conn == null) ? 0 : $conn.hashCode());
        Object $executor = this.getExecutor();
        result = result * 59 + (($executor == null) ? 0 : $executor.hashCode());
        return result;
    }
    
    @Override
    public String toString() {
        return "StoreSQLITE(name=" + this.getName() + ", prefix=" + this.getPrefix() + ", conn=" + this.getConn() + ", executor=" + this.getExecutor() + ")";
    }
}
