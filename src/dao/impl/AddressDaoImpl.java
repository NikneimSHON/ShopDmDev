package dao.impl;

import exception.DaoException;
import util.ConnectionManager;
import entity.AddressEntity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AddressDaoImpl implements dao.AddressDao {

    private static AddressDaoImpl instance;
    private static final String DELETE_SQL = """
            DELETE FROM address
            WHERE id = ?""";

    private static final String SAVE_SQL = """
            insert into address (street,id_employs,city,house_number,apartment_number)
            values (?,?,?,?,?)
            """;
    private static final String UPDATE_SQL = """
            Update address
            set street = ?,id_employs = ?,city = ?,house_number = ?,apartment_number = ?
            where id = ?
            """;
    private static final String FIND_ALL_SQL = """
            select id,street,id_employs,city,house_number,apartment_number
            from address
            """;

    private static final String FIND_BY_ID_SQL = FIND_ALL_SQL + """
            where id = ? 
            """;


    public static synchronized AddressDaoImpl getInstance() {
        if (instance == null) {
            instance = new AddressDaoImpl();
        }
        return instance;
    }

    public static AddressEntity buildAddress(ResultSet resultSet) throws SQLException {
        return new AddressEntity(
                resultSet.getLong("id"),
                resultSet.getString("street"),
                resultSet.getLong("id_employs"),
                resultSet.getString("city"),
                resultSet.getLong("house_number"),
                resultSet.getLong("apartment_number")
        );
    }

    @Override
    public List<AddressEntity> findAll() {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_ALL_SQL)) {
            var resultSet = preparedStatement.executeQuery();
            List<AddressEntity> addressList = new ArrayList<>();
            while (resultSet.next()) {
                addressList.add(buildAddress(resultSet));

            }
            return addressList;

        } catch (SQLException e) {
            throw new DaoException(e + " Error in findAll method");
        }
    }

    @Override
    public Optional<AddressEntity> findById(Long id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(FIND_BY_ID_SQL)) {
            preparedStatement.setLong(1, id);

            var resultSet = preparedStatement.executeQuery();
            AddressEntity addressEntity = null;
            if (resultSet.next()) {
                addressEntity = buildAddress(resultSet);

            }
            return Optional.ofNullable(addressEntity);

        } catch (SQLException e) {
            throw new DaoException(e + " Error in findById method");
        }
    }


    @Override
    public AddressEntity save(AddressEntity entity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(SAVE_SQL, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, entity.getStreet());
            preparedStatement.setLong(2, entity.getIdEmploy());
            preparedStatement.setString(3, entity.getCity());
            preparedStatement.setLong(4, entity.getHouseNumber());
            preparedStatement.setLong(5, entity.getApartmentHumber());

            preparedStatement.executeUpdate();

            var resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                entity.setId(resultSet.getLong("id"));
            }
            return entity;
        } catch (SQLException e) {
            throw new DaoException(e + " Error in save method");
        }
    }

    @Override
    public boolean update(AddressEntity entity) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(UPDATE_SQL)) {
            preparedStatement.setString(1, entity.getStreet());
            preparedStatement.setLong(2, entity.getIdEmploy());
            preparedStatement.setString(3, entity.getCity());
            preparedStatement.setLong(4, entity.getHouseNumber());
            preparedStatement.setLong(5, entity.getApartmentHumber());
            preparedStatement.setLong(6, entity.getId());

            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e + " Error in update method");
        }
    }

    @Override
    public boolean delete(Long id) {
        try (var connection = ConnectionManager.getConnection();
             var preparedStatement = connection.prepareStatement(DELETE_SQL)) {
            preparedStatement.setLong(1, id);
            return preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            throw new DaoException(e + " Error in delete method");
        }

    }
}
