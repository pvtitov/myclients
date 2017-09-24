package pvtitov.myclients.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pvtitov.myclients.ClientListActivity;
import pvtitov.myclients.MyApplication;
import pvtitov.myclients.api.RandomUserModel;
import pvtitov.myclients.api.Result;
import pvtitov.myclients.database.ClientsCursorWrapper;
import pvtitov.myclients.database.DatabaseHelper;
import pvtitov.myclients.database.DatabaseWrapper;
import pvtitov.myclients.database.DatabaseWrapper.ClientsTable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static pvtitov.myclients.database.DatabaseWrapper.ClientsTable.*;


public class ClientsFactory {

    private static ClientsFactory clientsFactory;
    private Context context;
    private SQLiteDatabase database;

    //public static final List<Client> ITEMS = new ArrayList<>();

    public static ClientsFactory getInstance(Context context) {
        if (clientsFactory == null) clientsFactory = new ClientsFactory(context);
        return clientsFactory;
    }

    private ClientsFactory(Context context) {
        this.context = context.getApplicationContext();
        database = new DatabaseHelper(this.context).getWritableDatabase();
    }

    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        ClientsCursorWrapper cursor = new ClientsCursorWrapper(
                database.query(NAME, null, null, null, null, null, null)
        );
        try {
            cursor.moveToLast();
            while (!cursor.isBeforeFirst()) {
                clients.add(cursor.getClient());
                cursor.moveToPrevious();
            }
        }
        finally {
            cursor.close();
        }
        return clients;
    }

    private static ContentValues getContentValues(Client client){
        ContentValues values = new ContentValues();
        values.put(Columns.FIRST_NAME, client.getFirstName());
        values.put(Columns.LAST_NAME, client.getLastName());
        values.put(Columns.EMAIL, client.getEmail());
        values.put(Columns.PHONE, client.getPhone());
        values.put(Columns.NATIONALITY, client.getNationality());
        values.put(Columns.ADDRESS, client.getAddress());
        return values;
    }

    public void addClients(List<Client> clients) {
        for (int i = 0; i < clients.size(); i++){
            ContentValues values = getContentValues(clients.get(i));
            database.insert(ClientsTable.NAME, null, values);
        }
    }
}
