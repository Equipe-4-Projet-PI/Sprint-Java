package services;

import java.sql.SQLException;
import java.util.List;

public interface IService<T>{
    public  void ajouter(T t ) throws SQLException;
    public  void modifier(T t ) throws SQLException;
    public  void supprimer(T t )throws SQLException;
    public List<T> maList(int t)throws SQLException;
    public List<T> saleList()throws SQLException;
    public List<T> NosaleList()throws SQLException;
    public List<T> afficher() throws SQLException;
    public List<T> FilterShow(String t) throws SQLException;

    public void ADD(T t) throws SQLException;

    public void UPDATE(T t) throws SQLException;

    public void DELETE(T t) throws SQLException ;

    public List<T> DISPLAY() throws SQLException;
}
