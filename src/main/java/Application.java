import java.sql.*;
import java.util.Properties;

class Application {

    static Connection conn;

    static void init() throws SQLException, ClassNotFoundException {

        String url = "jdbc:postgresql://localhost/test_db";

        Properties properties = new Properties();
        properties.setProperty("user", "postgres");
        properties.setProperty("password", "root");

        Class.forName("org.postgresql.Driver");
        conn = DriverManager.getConnection(url, properties);
        // Connection conn = DriverManager.getConnection(url, "username", "password");

    }

    static void statement() throws SQLException {

        Statement stm = conn.createStatement();
        stm.execute("INSERT INTO usr(id, username, email) VALUES('11', 'Daniel', 'dan@mail.com');");
        stm.close();

    }

    static void statementWithParamsInString() throws SQLException {

        Statement stm = conn.createStatement();
        int id = 12;
        String username = "Josh";
        String email = "josh@mail.com";
        String query = String.format("INSERT INTO usr(id, username, email) VALUES('%s', '%s', '%s');",
                id, username, email);
        stm.executeUpdate(query);
        stm.close();

    }

    static void preparedStatement() throws SQLException {

        PreparedStatement statement = conn.prepareStatement("insert into usr(id,username,email) values(?,?,?)");
        statement.setInt(1, 13);
        statement.setString(2, "John");
        statement.setString(3, "john@gmail.com");
        statement.executeUpdate();
        statement.close();

    }

    static void callableStatement() throws SQLException {

        CallableStatement statement = conn.prepareCall("{ ? = call upper( ? ) }");
        statement.registerOutParameter(1, Types.VARCHAR);
        statement.setString(2, "this is going to be converted to uppercase");
        statement.execute();
        String upperCased = statement.getString(1);
        System.out.println(upperCased);
        statement.close();

    }

    static void resultSet() throws SQLException {

        Statement statement = conn.createStatement();
        ResultSet rs = statement.executeQuery("select * from usr");
        while (rs.next()) {
            System.out.println(rs.getInt(1) + " : " + rs.getString(2));
            System.out.println(rs.getInt("id") + " : " + rs.getString("username"));
        }
        rs.close();

    }


    static void transaction() throws SQLException {

        conn.setAutoCommit(true);
        Statement statement = conn.createStatement();
        try {
            statement.execute("insert into usr(id, username, email) values(18, 'John', 'johnwick@mail.com')");
            statement.execute("insert into usr(id, username, email) values(qwe, 'Jack', 'jacksaw@mail.com')");
            conn.commit();
        } catch (SQLException e)  {
            System.out.println("Something went wrong!");
            conn.rollback();
            throw new RuntimeException(e);
        }

    }

    static void batchExecution() throws SQLException {

        PreparedStatement statement = conn.prepareStatement("insert into usr(id,username,email) values(?,?,?)");
        statement.setInt(1, 14);
        statement.setString(2, "Tom");
        statement.setString(3, "tomthecat@mail.com");
        statement.addBatch();
        statement.setInt(1, 15);
        statement.setString(2, "Jerry");
        statement.setString(3, "jerrythemouse@mail.com");
        statement.addBatch();
        statement.executeBatch();
        statement.close();

    }

    static void dbMetadata() throws SQLException {

        DatabaseMetaData dmd = conn.getMetaData();
        System.out.println("Driver Name: " + dmd.getDriverName());
        System.out.println("Driver Version: " + dmd.getDriverVersion());
        System.out.println("UserName: " + dmd.getUserName());
        System.out.println("Database Product Name: " + dmd.getDatabaseProductName());
        System.out.println("Database Product Version: " + dmd.getDatabaseProductVersion());
        ResultSet tables = dmd.getTables("test_db", "public", "%", null);
        tables.next();
        System.out.println(tables.getString("TABLE_NAME"));

    }

    public static void main(String[] args) throws SQLException, ClassNotFoundException {

        init();

//        statement();
//
//        statementWithParamsInString();
//
//        preparedStatement();
//
//        callableStatement();
//
//        resultSet();
//
//        batchExecution();
//
        transaction();

//        dbMetadata();

    }

}


