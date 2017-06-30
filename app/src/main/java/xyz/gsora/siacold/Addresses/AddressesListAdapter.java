package xyz.gsora.siacold.Addresses;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmRecyclerViewAdapter;
import io.realm.RealmResults;
import xyz.gsora.siacold.General.Address;
import xyz.gsora.siacold.General.Utils;
import xyz.gsora.siacold.R;

/**
 * Created by gsora on 6/29/17.
 * <p>
 * Realm RecyclerView addresses list adapter.
 */

public class AddressesListAdapter extends RealmRecyclerViewAdapter<Address, AddressesListAdapter.ViewHolder> {

    private static final String TAG = AddressesListAdapter.class.getSimpleName();
    public int scrollDist = 0;
    public boolean isVisible = true;
    private Context parentCtx;

    public AddressesListAdapter(RealmResults<Address> data, Context ctx) {
        super(data, true);
        setHasStableIds(true);
        parentCtx = ctx;
    }

    @Override
    public AddressesListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parentCtx).inflate(R.layout.address_row, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.addressText.setText(getItem(position).getAddress());
        holder.addressDescription.setText(getItem(position).getDescription());
        holder.addressText.setOnClickListener(l -> {
            Utils.shareAddress(getItem(position), parentCtx);
        });
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public long getItemId(int index) {
        return index;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.addressText)
        TextView addressText;

        @BindView(R.id.addressDescription)
        TextView addressDescription;

        public ViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

}

