package dbManager;

public class DMLQuery {

    static String checkUser = "SELECT id FROM users where login = ?;";

    static String getSalt = "SELECT salt from users where login = ? ;";
    static String addUser = "INSERT INTO users(login, password, salt) VALUES (?, ?, ?)";

    static String addPerson = """
            INSERT INTO persons(user_id, person_id, name, coordinates_x, coordinates_y, creationdate, height, eyecolor, haircolor, nationality, location_x, location_y, location_z) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) 
            RETURNING id;
            """;


    static String deleteObject = "delete from persons where (user_id = ?) and (person_id = ?) returning id;";

    static String updateObject = """
            update persons
            set(person_id, name, coordinates_x, coordinates_y, creationdate, height, eyecolor, haircolor, nationality, location_x, location_y, location_z) = (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?) where (user_id = ?) and (person_id = ?) returning id;
            """;

    static String getUserId = """
            select id from users where (login = ?) and (password = ?);
            """;
    static String addObjects = """
            select * from persons;
            """;
}
