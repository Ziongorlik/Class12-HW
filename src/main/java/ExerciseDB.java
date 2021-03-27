import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExerciseDB {
    private static final String USER_NAME = "Up7KuhcCQh";
    private static final String DATABASE_NAME = "Up7KuhcCQh";
    private static final String PASSWORD = "Ha4tQ3oiry";
    private static final String PORT = "3306";
    private static final String SERVER = "remotemysql.com";

    public static void main(String[] args) throws SQLException {
        Connection con = DriverManager.getConnection("jdbc:mysql://" + SERVER + ":" + PORT, USER_NAME, PASSWORD);

        // 1. Create the table
        ResultSet resultSet = con.getMetaData().getTables(null, null, "dogs", new String[]{"Table"});
        // If the table do not exist, create a new one
        if (!resultSet.next()) {
            String createStatement = "CREATE TABLE " + DATABASE_NAME + ".dogs " +
                    "(name VARCHAR(40) NOT NULL, " +
                    "age INT NOT NULL, " +
                    "breed VARCHAR(30) NOT NULL)";
            con.createStatement().execute(createStatement);
        }

        // 2. Insert 3 dogs
        String insertStatement = "INSERT INTO " + DATABASE_NAME + ".dogs (name, age, breed) " +
                                 "VALUES ('Lord','2','Shepherd')," +
                                        "('Peter','10','Pit Bull')," +
                                        "('Pongo','5','Dalmatian')";
        con.createStatement().execute(insertStatement);

        // 3. Update dog age
        String updateStatement = "UPDATE " + DATABASE_NAME + ".dogs " +
                                  "SET age = 20 " +
                                  "WHERE name = 'Peter'";
        con.createStatement().execute(updateStatement);

        // 4. Delete dog from table
        String deleteStatement = "DELETE FROM " + DATABASE_NAME + ".dogs " +
                                 "WHERE name = 'Pongo'";
        con.createStatement().execute(deleteStatement);

        // 5. Print all dogs names
        String statement = "SELECT name " +
                            "FROM " + DATABASE_NAME + ".dogs ";
        resultSet = con.createStatement().executeQuery(statement);
        while (resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }

        resultSet.close();
        con.close();
    }
}
