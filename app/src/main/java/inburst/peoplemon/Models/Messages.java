package inburst.peoplemon.Models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by lennyhicks on 11/6/16.
 */

public class Messages {

    @SerializedName("ConversationId")
    private Integer conversationId;
    @SerializedName("RecipientId")
    private String recipientId;
    @SerializedName("RecipientName")
    private String recipientName;
    @SerializedName("LastMessage")
    private String lastMessage;
    @SerializedName("Created")
    private String created;
    @SerializedName("MessageCount")
    private Integer messageCount;
    @SerializedName("AvatarBase64")
    private String avatarBase64;
    @SerializedName("SenderId")
    private String senderId;
    @SerializedName("SenderName")
    private String senderName;
    @SerializedName("RecipientAvatarBase64")
    private String recipientAvatarBase64;
    @SerializedName("SenderAvatarBase64")
    private String senderAvatarBase64;
    @SerializedName("Messages")
    private ArrayList<Messages> messages;
    @SerializedName("Count")
    private Integer count;
    @SerializedName("Message")
    private String message;


    public Messages(Integer conversationId, String recipientId, String recipientName, String lastMessage, String created, Integer messageCount, String avatarBase64, String senderId, String senderName, String recipientAvatarBase64, String senderAvatarBase64) {
        this.conversationId = conversationId;
        this.recipientId = recipientId;
        this.recipientName = recipientName;
        this.lastMessage = lastMessage;
        this.created = created;
        this.messageCount = messageCount;
        this.avatarBase64 = avatarBase64;
        this.senderId = senderId;
        this.senderName = senderName;
        this.recipientAvatarBase64 = recipientAvatarBase64;
        this.senderAvatarBase64 = senderAvatarBase64;
    }

    public Messages(Integer count, String recipientName, String senderName, ArrayList<Messages> messages) {
        this.count = count;
        this.recipientName = recipientName;
        this.messages = messages;
        this.senderName = senderName;
    }

    public Messages(String recipientId, String message) {
        this.recipientId = recipientId;
        this.message = message;
    }

    public Messages() {
    }
}
