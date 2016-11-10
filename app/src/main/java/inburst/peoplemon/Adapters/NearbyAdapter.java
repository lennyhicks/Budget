package inburst.peoplemon.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.Bind;
import butterknife.ButterKnife;
import inburst.budget.R;
import inburst.peoplemon.Components.Constants;
import inburst.peoplemon.Components.Utils;
import inburst.peoplemon.Models.User;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.Users> {

    public ArrayList<User> users;
    private Context context;

    public NearbyAdapter(ArrayList<User> user, Context context) {
        this.users = user;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(Users holder, int position) {
        final User caught = users.get(position);
        holder.bindCategory(caught);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, caught.getUserName() + " Caught", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public Users onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.caught_user, parent, false);
        return new Users(inflatedView);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    class Users extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView)
        ImageView imageView;

        @Bind(R.id.nameField)
        TextView nameField;

        @Bind(R.id.otherField)
        TextView radiusField;

        public Users(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindCategory(User user) {
            DecimalFormat df = new DecimalFormat("0.00");
            nameField.setText(user.getUserName());
            Bitmap image = Utils.decodeImage(Constants.IMAGE);
            if (image == null) {
                imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
            } else {
                imageView.setImageBitmap(image);
            }

            Collections.sort(users);
            radiusField.setText(user.getRadiusInMeters().toString());


        }
    }
}