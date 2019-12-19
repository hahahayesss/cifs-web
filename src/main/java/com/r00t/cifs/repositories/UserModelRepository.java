package com.r00t.cifs.repositories;

import com.r00t.cifs.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModelRepository extends MongoRepository<UserModel, String> {

    UserModel findByUsername(String username);
}
