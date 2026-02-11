package src;
/**
 * Describes the Message class of a chat system, contains a message, sender and time of creation
 */
public class Message{
    // TODO
    private int sender_id;
    private int message_date;
    private int message_id;
    private int chat_id;
    private string msg_type;
    Private string content;

    public Message ( int sender_id, int message_date, int message_id,  int chat_id,  string msg_type, string content){
    this.sender_id =     sender_id;
    this.message_date =  message_date;
    this.message_id =    message_id;
    this.chat_id =       chat_id;
    this.msg_type =      msg_type;
    this.content =       content;

    }
};
