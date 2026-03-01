package src;
import java.util.List;
import src.Model.Entities.Message;

public class View {
    private GUI gui;
    
    public View(clientModel model){
        gui = new GUI(model);
    }

    public void ShowLogin(){
        gui.showLogInScreen();
    }

    public void RemoveLoginScreen(){
        gui.removeLogInScreen();
    }

    public void UpdateChatList(List<String> chatNames){
        gui.UpdateChats(chatNames);
        gui.revalidate();
        gui.repaint();
    }

    public void ShowHomeScreen(List<String> chatNames){
        gui.showHomeScreen(chatNames);
    }

    public void ShowChatroom(List<Message> messages){
        gui.showChatroom(messages);
    }
}
