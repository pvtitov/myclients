package pvtitov.myclients;

import android.app.Activity;
import android.net.Uri;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import pvtitov.myclients.model.Client;
import pvtitov.myclients.model.ClientsFactory;


public class ClientDetailFragment extends Fragment {

    public static final String ARGUMENT_EMAIL = "client_email";

    private Client client;
    private Activity activity;
    private CollapsingToolbarLayout appBarLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        activity = this.getActivity();
        if (getArguments().containsKey(ARGUMENT_EMAIL))
            client = ClientsFactory.getInstance(activity).findClientByEmail(getArguments().getString(ARGUMENT_EMAIL));
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.client_detail, container, false);


        appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null)
            appBarLayout.setTitle(client.getFirstName() + " " + client.getLastName());

        ImageView imageView = rootView.findViewById(R.id.picture);
        Picasso.with(activity).load(client.getPicture()).into(imageView);


        if (client != null)
            ((TextView) rootView.findViewById(R.id.client_detail)).setText(
                    client.getFirstName()
                    + "\n" + client.getLastName()
                    + "\n" + client.getEmail()
                    + "\n" + client.getPhone()
                    + "\n" + client.getNationality()
                    + "\n" + client.getAddress()
            );

        return rootView;
    }
}
