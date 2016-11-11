package inburst.peoplemon.Adapters;

import android.content.Context;
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
import inburst.peoplemon.Components.CurrentDataStore;
import inburst.peoplemon.Components.Utils;
import inburst.peoplemon.Models.MessageView;
import inburst.peoplemon.Models.User;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class MessageListViewAdapter extends RecyclerView.Adapter<MessageListViewAdapter.MessagesView> {

    public static ArrayList<MessageView> messagesFromUser;
    private Context context;


    public MessageListViewAdapter(ArrayList<MessageView> user, Context context) {
        this.messagesFromUser = user;
        this.context = context;
    }

    @Override
    public void onBindViewHolder(MessagesView holder, int position) {
        final MessageView caught = messagesFromUser.get(position);
        holder.bindCategory(caught);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public MessagesView onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_message, parent, false);
        return new MessagesView(inflatedView);
    }

    @Override
    public int getItemCount() {
        return messagesFromUser.size();
    }

    class MessagesView extends RecyclerView.ViewHolder {
        @Bind(R.id.imageView)
        ImageView imageView;

        @Bind(R.id.messageField)
        TextView messageField;

        @Bind(R.id.otherField)
        TextView dateField;

        Bitmap imageHolder = Utils.getRandomPokemon(context);
        Bitmap imageHolder2 = Utils.getRandomPokemon(context);

        public MessagesView(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void bindCategory(MessageView message) {
            User user;
            messageField.setText(message.getMessage());
            if(message.getSenderUserId() == CurrentDataStore.sender.getUserId()){
                user = CurrentDataStore.recipient;
            } else {
                user = CurrentDataStore.sender;
            }
            Bitmap image = Utils.decodeImage(user.getAvatarBase64());
            if (image == null) {
                if(user == CurrentDataStore.sender) {
                    imageView.setImageBitmap(imageHolder);
                } else {
                    imageView.setImageBitmap(imageHolder2);

                }
            } else {
                imageView.setImageBitmap(image);
            }
            String strCurrentDate = message.getCreated();
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