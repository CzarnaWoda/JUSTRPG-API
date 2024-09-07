package pl.justrpg.api.store;

public enum StoreMode
{
    SQLITE("sqlite"), 
    MYSQL("mysql");
    
    private String name;
    
    private StoreMode(String name) {
        this.name = name;
    }
    
    public static StoreMode getByName(String name) {
        for (StoreMode sm : values()) {
            if (sm.getName().equalsIgnoreCase(name)) {
                return sm;
            }
        }
        return null;
    }
    
    public String getName() {
        return this.name;
    }
}
