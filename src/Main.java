import dao.AddressDao;
import dao.impl.AddressDaoImpl;
import entity.AddressEntity;
import util.ConnectionManager;

import java.sql.Driver;
import java.sql.SQLException;


public class Main {
    public static void main(String[] args) {


        var addressDao = AddressDaoImpl.getInstance();
        var res = addressDao.findAll();
        System.out.println(res);


    }
}