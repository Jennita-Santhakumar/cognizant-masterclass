package week1.patterns;

/**
 * Factory Pattern - Creates objects without specifying exact classes
 */
public class Factory {
    
    public enum DatabaseType {
        MYSQL, POSTGRESQL, ORACLE
    }
    
    public static Database createDatabase(DatabaseType type) {
        switch (type) {
            case MYSQL:
                return new MySQLDatabase();
            case POSTGRESQL:
                return new PostgreSQLDatabase();
            case ORACLE:
                return new OracleDatabase();
            default:
                return null;
        }
    }
}

interface Database {
    void connect();
    void disconnect();
}

class MySQLDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connected to MySQL");
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnected from MySQL");
    }
}

class PostgreSQLDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connected to PostgreSQL");
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnected from PostgreSQL");
    }
}

class OracleDatabase implements Database {
    @Override
    public void connect() {
        System.out.println("Connected to Oracle");
    }
    
    @Override
    public void disconnect() {
        System.out.println("Disconnected from Oracle");
    }
}
