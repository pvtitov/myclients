package pvtitov.myclients.database;

/**
 * Created by Павел on 24.09.2017.
 */

public class DatabaseWrapper {
    public static final class ClientsTable {
        public static final String NAME = "clients";

        public static final class Columns {
            public static final String FIRST_NAME = "first_name";
            public static final String LAST_NAME = "last_name";
            public static final String EMAIL = "email";
            public static final String PHONE = "phone";
            public static final String NATIONALITY = "nationality";
            public static final String ADDRESS = "address";
            public static final String PICTURE = "picture";
        }
    }
}
