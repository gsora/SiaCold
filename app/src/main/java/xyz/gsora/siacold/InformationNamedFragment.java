package xyz.gsora.siacold;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;


/**
 * A simple {@link Fragment} subclass, to handle wallet informations and such.
 */
public class InformationNamedFragment extends Fragment implements NamedFragment {

    private static final String FancyName = "Info";
    private static final String TAG = InformationNamedFragment.class.getSimpleName();

    private Crypto c;

    public InformationNamedFragment() {
        // Required empty public constructor
    }

    public static InformationNamedFragment newInstance() {
        return new InformationNamedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, v);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        c = new Crypto(getActivity(), getContext(), this, null);
        c.tryDecrypt();
        try {
            Log.d(TAG, "onActivityCreated: seed ->" + c.getDecryptedData());
        } catch (NoDecryptedDataException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getFragmentFancyName() {
        return FancyName;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SiaCold.REQUEST_CODE:
                // Challenge completed, proceed with using cipher
                if (resultCode == RESULT_OK) {
                    if (c.tryDecrypt()) {
                        try {
                            Log.d(TAG, "onActivityCreated: seed ->" + c.getDecryptedData());
                        } catch (NoDecryptedDataException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break;
        }
    }
}
