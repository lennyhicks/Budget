package inburst.peoplemon.Adapters;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;
import inburst.budget.R;
import inburst.peoplemon.Components.Utils;
import inburst.peoplemon.Dialogs.SendMessage;
import inburst.peoplemon.Models.User;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class CaughtAdapter extends RecyclerView.Adapter<CaughtAdapter.Users> {

    public ArrayList<User> user;
    private Context context;

    public CaughtAdapter(ArrayList<User> user, Context context) {
        this.user = user;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(Users holder, int position) {
        final User caught = user.get(position);
        holder.bindCategory(caught);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SendMessage sendMessage = new SendMessage(context, caught);
                sendMessage.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {

                    }
                });
                sendMessage.show();
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
        return user.size();
    }

    class Users extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView)
        ImageView imageView;

        @Bind(R.id.nameField)
        TextView nameField;

        @Bind(R.id.otherField)
        TextView dateField;

        public Users(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindCategory(User user) {
            nameField.setText(user.getUserName());
            Bitmap image = Utils.decodeImage(user.getAvatarBase64());
            if (image == null) {
                imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
            } else {
                imageView.setImageBitmap(image);
            }
            String strCurrentDate = user.getCreated();
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSSSZ");
            try {
                Date newDate = format.parse(strCurrentDate);
                format = SimpleDateFormat.getDateInstance();
                String date = format.format(newDate);
                dateField.setText(date);

            } catch (ParseException e) {
                e.printStackTrace();

                dateField.setText("Error Parsing");
            }
        }
    }
}