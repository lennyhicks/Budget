package inburst.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

/**
 * Created by lennyhicks on 11/10/16.
 */

public class MessageView {
    @SerializedName("MessageId")
    private Integer messageId;

    @SerializedName("Message")
    private String message;

    @SerializedName("Created")
    private String created;

    @SerializedName("RecipientUserId")
    private String recipentUserId;

    @SerializedName("SenderUserId")
    private String senderUserId;

    public MessageView() {
    }

    public MessageView(Integer messageId, String message, String created, String recipentUserId, String senderUserId) {
        this.messageId = messageId;
        this.message = message;
        this.created = created;
        this.recipentUserId = recipentUserId;
        this.senderUserId = senderUserId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public String getRecipentUserId() {
        return recipentUserId;
    }

    public void setRecipentUserId(String recipentUserId) {
        this.recipentUserId = recipentUserId;
    }

    public String getSenderUserId() {
        return senderUserId;
    }

    public void setSenderUserId(String senderUserId) {
        this.senderUserId = senderUserId;
    }
}
