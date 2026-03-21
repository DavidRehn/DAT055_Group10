package src;

import src.Model.Entities.User;

public interface HandlerInterface{

  public User getUser();
  public Boolean getAuthenticated();
  public String getFocus();
  public String getServerImageDir();
  public String getClientImageDir();
  
  public void setUser(User u);
  public void setAuthenticated(Boolean a);
  public void setFocus(String c);
  
  
  public void sendObject(Object obj) throws IOException;
  public void broadcastMessages(String chat);
  public void broadcastChatsToUser();
  public void broadcastImage(String chat, byte[] imageBytes, String fileName);
}
