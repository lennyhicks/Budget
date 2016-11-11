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
    private ArrayList<MessageView> messages;
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

    public Messages(Integer count, String recipientName, String senderName, ArrayList<MessageView> messages) {
        this.count = count;
        this.recipientName = recipientName;
        this.senderName = senderName;
        this.messages = messages;

    }

    public Messages(String recipientId, String message) {
        this.recipientId = recipientId;
        this.message = message;
    }

    public Messages() {
    }

    public ArrayList<MessageView> getMessages() {
        return messages;
    }

    public Integer getConversationId() {
        return conversationId;
    }

    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    public String getRecipientId() {
        return recipientId;
    }

    public void setRecipientId(String recipientId) {
        this.recipientId = recipientId;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(String lastMessage) {
        this.lastMessage = lastMessage;
    }

    public String getCreated() {
        return created;
    }

    public void setCreated(String created) {
        this.created = created;
    }

    public Integer getMessageCount() {
        return messageCount;
    }

    public void setMessageCount(Integer messageCount) {
        this.messageCount = messageCount;
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecipientAvatarBase64() {
        return recipientAvatarBase64;
    }

    public void setRecipientAvatarBase64(String recipientAvatarBase64) {
        this.recipientAvatarBase64 = recipientAvatarBase64;
    }

    public String getSenderAvatarBase64() {
        return senderAvatarBase64;
    }

    public void setSenderAvatarBase64(String senderAvatarBase64) {
        this.senderAvatarBase64 = senderAvatarBase64;
    }

    public void setMessages(ArrayList<MessageView> messages) {
        this.messages = messages;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
