package pvtitov.myclients;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import pvtitov.myclients.model.Client;
import pvtitov.myclients.model.ClientsFactory;
import retrofit2.http.HTTP;


/**
 * An activity representing a single Client detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ClientListActivity}.
 */
public class ClientDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        final Client client = ClientsFactory.getInstance(this).findClientByEmail(getIntent().getStringExtra(ClientDetailFragment.ARGUMENT_EMAIL));
        final PackageManager packageManager = getPackageManager();

        ImageView imageView = (ImageView) findViewById(R.id.picture_detail);
        if (client != null) Picasso.with(this).load(client.getPicture()).into(imageView);

        FloatingActionButton fabCall = (FloatingActionButton) findViewById(R.id.fab_call);
        fabCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri number = Uri.parse("tel:" + client.getPhone());
                Intent callIntent = new Intent(Intent.ACTION_DIAL, number);
                List<ResolveInfo> activities = packageManager.queryIntentActivities(callIntent, 0);
                boolean isIntentSafe = activities.size() > 0;
                if (isIntentSafe) startActivity(callIntent);
            }
        });


        FloatingActionButton fabMail = (FloatingActionButton) findViewById(R.id.fab_mail);
        fabMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.setType("text/plain");
                emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[] {client.getEmail()});
                List<ResolveInfo> activities = packageManager.queryIntentActivities(emailIntent, PackageManager.MATCH_DEFAULT_ONLY);
                boolean isIntentSafe = activities.size() > 0;
                if (isIntentSafe) startActivity(emailIntent);

            }
        });


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        if (savedInstanceState == null) {

            Bundle arguments = new Bundle();
            arguments.putString(ClientDetailFragment.ARGUMENT_EMAIL,
                    getIntent().getStringExtra(ClientDetailFragment.ARGUMENT_EMAIL));
            ClientDetailFragment fragment = new ClientDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.client_detail_container, fragment)
                    .commit();
        }
    }
}
