package pvtitov.myclients.model;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import pvtitov.myclients.api.RandomUserApi;
import pvtitov.myclients.api.RandomUserModel;
import pvtitov.myclients.database.ClientsCursorWrapper;
import pvtitov.myclients.database.DatabaseHelper;
import pvtitov.myclients.database.DatabaseWrapper.ClientsTable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static pvtitov.myclients.database.DatabaseWrapper.ClientsTable.*;


public class ClientsFactory implements Callback<RandomUserModel> {

    private static final int COUNT = 150;

    private static ClientsFactory clientsFactory;
    private Context context;
    private SQLiteDatabase database;


    public static ClientsFactory getInstance(Context context) {
        if (clientsFactory == null) clientsFactory = new ClientsFactory(context);
        return clientsFactory;
    }

    private RandomUserApi randomUserApi;
    private List<Client> allClients = new ArrayList<>();

    private ClientsFactory(Context context) {
        this.context = context.getApplicationContext();
        database = new DatabaseHelper(this.context).getWritableDatabase();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://randomuser.me")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        randomUserApi = retrofit.create(RandomUserApi.class);
        for (int i = 0; i < COUNT; i++) {
            randomUserApi.getUser().enqueue(this);
        }

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
        //Before adding a bunch of new clients deletes existing data
        database.delete(ClientsTable.NAME, null, null);
        for (int i = 0; i < clients.size(); i++){
            ContentValues values = getContentValues(clients.get(i));
            database.insert(ClientsTable.NAME, null, values);
        }
    }

    @Override
    public void onResponse(Call<RandomUserModel> call, Response<RandomUserModel> response) {
        if (response.body() != null) {
            allClients.add(new Client(response.body().getResults(), 0));
        }
        // 101 because some requests (first) crash
        if (allClients.size() == 101) addClients(allClients);
    }

    @Override
    public void onFailure(Call call, Throwable t)  {
        t.getStackTrace();
    }
}
