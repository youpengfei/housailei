package com.showcase.cassandrademo.domain;

import com.datastax.driver.core.DataType.Name;
import java.util.Map;
import lombok.Data;
import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("users")
@Data
public class Users  {

    @PrimaryKey("user_id")
    private String userId;

    @Column("emails")
    private Map<String,String> emails;

    @Column("first_name")
    private String firstName;

    @Column("last_name")
    private String lastName;

}
