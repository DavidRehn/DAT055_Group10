package src;

/** Request for a message.
 */
public class MessageRequest {
    private int messageId;

    /** Creates a request for the given message id.
     * @param messageId Id.
     */
    public MessageRequest(int messageId){
        this.messageId = messageId;
    }

    /** Returns the requested message id.
     * @return
     */
    public int GetMessageId(){
        return messageId;
    }
}
