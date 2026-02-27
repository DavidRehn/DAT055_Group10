package src;
import java.util.List;

public class View {
    private GUI gui;
    
    public View(clientModel model){
        gui = new GUI(model);
    }

    public void ShowLogin(){
        gui.showLogInScreen();
    }

    public void UpdateChatList(List<String> chatNames){
        gui.showHomeScreen(chatNames);
    }
}
