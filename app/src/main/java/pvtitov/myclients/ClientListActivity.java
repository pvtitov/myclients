package pvtitov.myclients;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


import pvtitov.myclients.api.RandomUserModel;
import pvtitov.myclients.api.Result;
import pvtitov.myclients.model.Client;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

/**
 * An activity representing a list of Clients. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ClientDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ClientListActivity extends AppCompatActivity implements Callback<RandomUserModel>{

    private static final int COUNT = 150;

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private ArrayList<Client> allClients = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        //MyApplication.getRandomUserApi().getUsers(COUNT).enqueue(this);
        for (int i = 0; i < COUNT; i++){
            allClients.add(new Client());
        }


        View recyclerView = findViewById(R.id.client_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);


        if (findViewById(R.id.client_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(allClients));
    }

    @Override
    public void onResponse(Call<RandomUserModel> call, Response<RandomUserModel> response) {
        if (response.body() != null) {
            Toast.makeText(this, response.body().getResults().get(13).getEmail(), Toast.LENGTH_LONG).show();
            for (int i = 0; i < COUNT; i++){
                allClients.add(new Client(response.body().getResults(), i));
            }
        }
    }

    @Override
    public void onFailure(Call<RandomUserModel> call, Throwable t) {
        Toast.makeText(this, t.getMessage(), Toast.LENGTH_LONG).show();
    }


    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<Client> clients;

        public SimpleItemRecyclerViewAdapter(List<Client> items) {
            clients = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.client_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.client = clients.get(position);
            holder.firstTextView.setText(clients.get(position).getFirstName());
            holder.secondTextView.setText(clients.get(position).getAddress());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ClientDetailFragment.ARG_ITEM_ID, holder.client.getFirstName());
                        ClientDetailFragment fragment = new ClientDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.client_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ClientDetailActivity.class);
                        intent.putExtra(ClientDetailFragment.ARG_ITEM_ID, holder.client.getFirstName());

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return clients.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public final View itemView;
            public final TextView firstTextView;
            public final TextView secondTextView;
            public Client client;

            public ViewHolder(View view) {
                super(view);
                itemView = view;
                firstTextView = view.findViewById(R.id.first_textview);
                secondTextView = view.findViewById(R.id.second_textview);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + secondTextView.getText() + "'";
            }
        }
    }
}
