package pvtitov.myclients;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pvtitov.myclients.model.Client;


public class ClientDetailFragment extends Fragment {

    public static final String ARGUMENT_EMAIL = "client_email";

    private Client client;
    private Activity activity;
    private CollapsingToolbarLayout appBarLayout;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARGUMENT_EMAIL)) {
            // or use a Loader to load content from a content provider.
            client = new Client();
            client.setEmail(getArguments().getString(ARGUMENT_EMAIL));
        }
        activity = this.getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.client_detail, container, false);

        if (client != null)
            ((TextView) rootView.findViewById(R.id.client_detail)).setText(client.getEmail());

        appBarLayout = activity.findViewById(R.id.toolbar_layout);
        if (appBarLayout != null)
            appBarLayout.setTitle(client.getEmail());

        return rootView;
    }
}
