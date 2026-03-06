package src;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import src.Model.Entities.Message;

public class View implements Observer {
    private GUI gui;
    private boolean chatroomShown = false;

    public View(clientModel model) {
        gui = new GUI(model);
        model.addObserver(this);
    }

    public void ShowLogin() {
        gui.showLogInScreen();
    }

    public void RemoveLoginScreen() {
        gui.removeLogInScreen();
    }

    public void UpdateChatList(List<String> chatNames) {
        gui.UpdateChats(chatNames);
        gui.revalidate();
        gui.repaint();
    }

    public void ShowHomeScreen(List<String> chatNames) {
        gui.showHomeScreen(chatNames);
    }

    public void ShowChatroom(List<Message> messages) {
        gui.showChatroom(messages);
    }

    public void UpdateMessages(List<Message> messages) {
        gui.UpdateMessages(messages);
        gui.revalidate();
        gui.repaint();
    }

    @Override
    public void update(clientModel m) {
        final String event = m.GetUIEvent();
        SwingUtilities.invokeLater(() -> {
            if ("LOGIN_SUCCESS".equals(event)) {
                RemoveLoginScreen();
                ShowHomeScreen(m.GetChats());
            } else if ("CHAT_LIST_UPDATED".equals(event)) {
                UpdateChatList(m.GetChats());
            } else if ("MESSAGES_UPDATED".equals(event)) {
                if (!chatroomShown) {
                    ShowChatroom(m.GetMessages());
                    chatroomShown = true;
                }
                UpdateMessages(m.GetMessages());
            } else if ("AUTH_FAILED".equals(event)) {
                JOptionPane.showMessageDialog(gui, m.GetAuthError(), "Authentication failed", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
