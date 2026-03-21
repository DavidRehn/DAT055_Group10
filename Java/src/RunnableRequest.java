package src;

import src.Model.DAO.DataStorage;

public interface RunnableRequest{
  public void runRequest(DataStorage D_CON, HandlerInterface h);
}
