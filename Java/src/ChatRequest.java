package src;

/** Request for a Chat.
 */
public class ChatRequest {
    private int chatId;

    /** Creates a request for chat with given id.
     * @param chatId Id.
     */
    public ChatRequest(int chatId){
        this.chatId = chatId;
    }

    /** Returns the requested chat id.
     * @return
     */
    public int GetChatId(){
        return chatId;
    }
}
