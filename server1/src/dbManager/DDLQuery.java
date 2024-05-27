package dbManager;

public class DDLQuery {
    static String createDbTables = """
           
           CREATE TABLE IF NOT EXISTS users(
           id serial primary key,
           password text,
           login text,
           salt text);
           
            CREATE TABLE IF NOT EXISTS persons (
            id serial primary key,
            user_id int,
            person_id int,
            name text,
            coordinates_x double precision,
            coordinates_y int,
            creationdate text,
            eyecolor text,
            haircolor text,
            nationality text,
            location_x double precision,
            location_y bigint,
            location_z int,
            foreign key (user_id) references users(id));
            """;
}
