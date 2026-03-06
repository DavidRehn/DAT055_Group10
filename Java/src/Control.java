package src;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import src.Model.Entities.ChatUser;
import src.Model.Entities.ImageMessage;
import src.Model.Entities.TextMessage;
//import java.swing.*; 

public class Control implements ActionListener {
    private String[] chatJoinButtons;
    private GUI gui;
    private clientModel cModel;
    private AddImageRequest msgRequest = null;

    public Control(GUI gui) {
        this.gui = gui;
        this.cModel = gui.GetClientModel();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();


        if (obj instanceof JButton) {
            String command = e.getActionCommand();
            if (command.equals("Login")) {
                String username = gui.GetUsername();
                String password = gui.GetPassword();
                if (username.length() > 0 && password.length() > 0) {
                    try {
                        cModel.SendObject(new LoginRequest(new ChatUser(username, password)));
                        System.out.println("Sent login request");
                    } catch (Exception a) {
                        a.printStackTrace();
                    }
                }
            } else if (command.equals("Create Chat")) {
                gui.showCreateChatroomWindow();
            } else if (command.equals("setChatFocus")) {
                JButton b = (JButton) obj;
                String name = b.getName();
                cModel.SetCurrentChat(name);
                try {
                    cModel.SendObject(new GetMessagesRequest(name));
                } catch (IOException a) {
                    System.out.println("Could not send message");
                }
                System.out.println("Set focus to " + name);
            } else if (command.equals("cancelChatCreation")) {
                gui.chatroomName.setText("");
                gui.removeCreateChatroomWindow();
            } else if (command.equals("confirmChatCreation")) {
                try {
                    if (gui.GetChatTitle().length() > 0) {
                        cModel.SendObject(new ChatCreateMsg(gui.GetChatTitle()));
                        System.out.println("Added chat " + gui.GetChatTitle());
                        gui.chatroomName.setText("");
                        gui.removeCreateChatroomWindow();
                    }
                } catch (Exception a) {
                    a.printStackTrace();
                }
            } else if (command.equals("Send")) {
                try {
                    LocalDateTime time = LocalDateTime.now();
                    String msg = gui.message.getText();
                    if (msg.length() > 0) {
                        cModel.SendObject(new AddMessageRequest(new TextMessage(time, cModel.GetCurrentChat(), msg, "text")));
                        gui.message.setText("");
                        System.out.println("Sent text mesage");
                    }
                    if (msgRequest != null) {
                        msgRequest.UpdateTimestamp();
                        cModel.SendObject(msgRequest);
                        msgRequest = null;
                        System.out.println("Sent image mesage");
                    }
                } catch (IOException a) {
                    a.printStackTrace();
                }

            } else if (command.equals("addImage")) {
                System.out.println("add image");
                JFileChooser fileChooser = new JFileChooser();
                int result = fileChooser.showOpenDialog(gui);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    String fileName = selectedFile.getName();

                    // Removes the format from the filename (because all valid files are converted to png)
                    int dotIndex = fileName.lastIndexOf('.');
                    if (dotIndex > 0) {
                        fileName = fileName.substring(0, dotIndex);
                    }
                    System.out.println(fileName);
                    try {
                        BufferedImage img = ImageIO.read(selectedFile);
                        ByteArrayOutputStream b = new ByteArrayOutputStream();
                        ImageIO.write(img, "png", b);    // Automaticly converts all valid image formats to png
                        byte[] imageBytes = b.toByteArray();
                        if (img != null) {
                            msgRequest = new AddImageRequest(imageBytes, fileName, new ImageMessage(LocalDateTime.now(), cModel.GetCurrentChat(), "image"), cModel.GetCurrentChat());
                        } else {
                            System.out.println("Not a supported image format.");
                        }
                    } catch (IOException a) {
                        System.out.println("Error reading file.");
                        a.printStackTrace();
                    }

                } else if (result == JFileChooser.CANCEL_OPTION) {
                    System.out.println("Canceled file upload");
                }
            } else {
                System.out.println("Invalid command");
            }
        }

    }
}
