package pvtitov.myclients;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import pvtitov.myclients.model.ClientsFactory;

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

        ImageView imageView = (ImageView) findViewById(R.id.picture_detail);
        Picasso.with(this).load(
                ClientsFactory.getInstance(this).findClientByEmail(getIntent().getStringExtra(ClientDetailFragment.ARGUMENT_EMAIL)
                ).getPicture()).into(imageView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Phone call or email", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
