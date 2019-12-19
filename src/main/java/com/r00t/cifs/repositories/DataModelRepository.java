package com.r00t.cifs.repositories;

import com.r00t.cifs.models.DataModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataModelRepository extends MongoRepository<DataModel, String> {
}
