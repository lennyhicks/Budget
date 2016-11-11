package inburst.peoplemon.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import flow.Flow;
import flow.History;
import inburst.budget.R;
import inburst.peoplemon.Components.CurrentDataStore;
import inburst.peoplemon.Components.Utils;
import inburst.peoplemon.Models.Messages;
import inburst.peoplemon.Stages.MessagesListStage;

import static inburst.peoplemon.PeopleMon.getMainFlow;

/**
 * Created by lennyhicks on 11/8/16.
 */

public class MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.Message> {

    public ArrayList<Messages> messages;
    private Context context;
    public static Messages messageView;

    public MessageListAdapter(ArrayList<Messages> messages, Context context) {
        this.messages = messages;
        this.context = context;
    }


    @Override
    public void onBindViewHolder(final Message holder, int position) {
        Messages message = messages.get(position);
        CurrentDataStore.currentMessage = message;
        holder.bindCategory(message);
    }

    @Override
    public Message onCreateViewHolder(ViewGroup parent, int viewType) {
        View inflatedView = LayoutInflater.from(parent.getContext()).inflate(R.layout.caught_user, parent, false);
        return new Message(inflatedView);
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class Message extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.imageView)
        ImageView imageView;

        @Bind(R.id.nameField)
        TextView nameField;

        @Bind(R.id.otherField)
        TextView messageCount;

        private Messages mess;
        public Message(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);

        }

        public void bindCategory(Messages message) {
            mess = message;
            Bitmap image = null;
            nameField.setText(message.getRecipientName());
            if (message.getRecipientAvatarBase64() != null && message.getRecipientAvatarBase64().length() > 200) {
                 image = Utils.decodeImage(message.getRecipientAvatarBase64());
            }
            if (image == null) {
                imageView.setImageDrawable(context.getResources().getDrawable(R.mipmap.ic_launcher));
            } else {
                imageView.setImageBitmap(image);
            }

            messageCount.setText(message.getMessageCount() + "");

        }


        @Override
        public void onClick(View view) {
                        Flow flow = getMainFlow();
                        History newHistory = flow.getHistory().buildUpon()
                                .push(new MessagesListStage(mess.getConversationId()))
                                .build();
                        flow.setHistory(newHistory, Flow.Direction.FORWARD);

            }

    }
}