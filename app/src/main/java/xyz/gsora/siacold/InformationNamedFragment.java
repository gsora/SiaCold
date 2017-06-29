package xyz.gsora.siacold;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;
import net.glxn.qrgen.android.QRCode;
import xyz.gsora.siacold.General.Address;
import xyz.gsora.siacold.General.Crypto;
import xyz.gsora.siacold.General.Utils;


/**
 * A simple {@link Fragment} subclass, to handle wallet informations and such.
 */
public class InformationNamedFragment extends Fragment implements NamedFragment {

    private static final String FancyName = "Info";
    private static final String TAG = InformationNamedFragment.class.getSimpleName();

    @BindView(R.id.qr)
    ImageButton qr;

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
        c = new Crypto(getActivity(), getContext(), this, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_information, container, false);
        ButterKnife.bind(this, v);

        qr.setImageBitmap(
                QRCode.from(getLastAddress().getAddress()).withSize(750, 750).bitmap()
        );

        qr.setOnClickListener(vr -> {
            Utils.shareAddress(getLastAddress(), getContext());
        });

        return v;
    }

    @Override
    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    @Override
    public String getFragmentFancyName() {
        return FancyName;
    }

    private Address getLastAddress() {
        return Utils.getRealm().where(Address.class).findAllSorted("id").last();

    }
}
