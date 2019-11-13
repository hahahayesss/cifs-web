package com.r00t.cifs.repositories;

import com.r00t.cifs.models.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserModel, String> {

    UserModel findByUsername(String username);
}
