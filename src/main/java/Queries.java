class Queries {

    private Queries(){}

    static String crateTable() {
        return "CREATE TABLE usr(" +
                "id int not null," +
                "username varchar(45)," +
                "email varchar(45));";
    }

    static String addValues(int id) {
        return String.format("INSERT INTO usr(id, username, email) VALUES('%s', 'user%s', 'user%s@mail.com');",
                id, id, id);
    }

    static String selectAll() {
        return "SELECT * FROM usr";
    }

    static String selectById(int id) {
        return String.format("SELECT * FROM usr WHERE id = %s", id);
    }

    static String updateValues() {
        return "";
    }

    static String deleteValues() {
        return "";
    }
}
