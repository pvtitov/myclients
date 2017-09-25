package pvtitov.myclients.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import pvtitov.myclients.database.DatabaseWrapper.ClientsTable.Columns;
import pvtitov.myclients.model.Client;

/**
 * Created by Павел on 24.09.2017.
 */

public class ClientsCursorWrapper extends CursorWrapper {

    public ClientsCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Client getClient() {
        String firstName = getString(getColumnIndex(Columns.FIRST_NAME));
        String lastName = getString(getColumnIndex(Columns.LAST_NAME));
        String email = getString(getColumnIndex(Columns.EMAIL));
        String phone = getString(getColumnIndex(Columns.PHONE));
        String nationality = getString(getColumnIndex(Columns.NATIONALITY));
        String address = getString(getColumnIndex(Columns.ADDRESS));

        Client client = new Client();
        client.setFirstName(firstName);
        client.setLastName(lastName);
        client.setEmail(email);
        client.setPhone(phone);
        client.setNationality(nationality);
        client.setAddress(address);
        return client;
    }
}
