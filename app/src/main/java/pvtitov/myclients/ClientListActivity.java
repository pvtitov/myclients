package pvtitov.myclients;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pvtitov.myclients.model.Client;
import pvtitov.myclients.model.ClientsFactory;

import java.util.List;

public class ClientListActivity extends AppCompatActivity{


    RecyclerView recyclerView;
    SimpleItemRecyclerViewAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        recyclerView = (RecyclerView) findViewById(R.id.client_list);
        assert recyclerView != null;
        setupRecyclerView(recyclerView);

    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        List<Client> clients = ClientsFactory.getInstance(this).getAllClients();
        if (adapter == null) {
            adapter = new SimpleItemRecyclerViewAdapter(clients);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setClients(clients);
            adapter.notifyDataSetChanged();
        }
    }


    class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private List<Client> clients;

        SimpleItemRecyclerViewAdapter(List<Client> items) {
            clients = items;
        }

        void setClients(List<Client> clients) {
            this.clients = clients;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.client_list_content, parent, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            holder.client = clients.get(position);
            Picasso.with(ClientListActivity.this).load(clients.get(position).getPicture()).into(holder.imageView);
            holder.firstTextView.setText(clients.get(position).getFirstName());
            holder.secondTextView.setText(clients.get(position).getLastName());
            holder.imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("plain/text");
                    emailIntent.putExtra(Intent.EXTRA_TEXT,
                            clients.get(position).getFirstName()
                                + "\n" + clients.get(position).getLastName()
                                + "\n" + clients.get(position).getEmail()
                                + "\n" + clients.get(position).getPhone()
                                + "\n" + clients.get(position).getNationality()
                                + "\n" + clients.get(position).getAddress());
                    PackageManager packageManager = getPackageManager();
                    List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
                    boolean isIntentSafe = activities.size() > 0;
                    if (isIntentSafe) startActivity(emailIntent);
                }
            });

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ClientDetailActivity.class);
                    intent.putExtra(ClientDetailFragment.ARGUMENT_EMAIL, holder.client.getEmail());

                    context.startActivity(intent);
                }
            });
        }

        @Override
        public int getItemCount() {
            return clients.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            final View itemView;
            final ImageView imageView;
            final TextView firstTextView;
            final TextView secondTextView;
            final ImageButton imageButton;
            Client client;

            ViewHolder(View view) {
                super(view);
                itemView = view;
                imageView = view.findViewById(R.id.picture_list);
                firstTextView = view.findViewById(R.id.first_textview);
                secondTextView = view.findViewById(R.id.second_textview);
                imageButton = view.findViewById(R.id.share);
            }
        }
    }
}
