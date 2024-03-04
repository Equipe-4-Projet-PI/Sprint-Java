package services;

import java.sql.SQLException;
import java.util.List;

public interface IServiceU  <T>{
    public void ADD(T t ) throws SQLException;
    public void UPDATE(T t ) throws SQLException;
    public void DELETE(T t ) throws SQLException;
    public List<T> DISPLAY() throws SQLException;
}
