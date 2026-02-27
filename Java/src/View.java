package src;
import java.util.List;

public class View {
    private GUI gui;
    
    public View(){
        gui = new GUI();
    }

    public void UpdateChatList(List<String> chatNames){
        gui.showHomeScreen(chatNames);
    }
}
