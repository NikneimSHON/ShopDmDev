package dao;

import entity.AddressEntity;

import java.util.List;

public interface AddressDao extends BaseDao<AddressEntity>{
    List<AddressEntity> findAll();
}
