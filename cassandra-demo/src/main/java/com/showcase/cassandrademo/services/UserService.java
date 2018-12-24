package com.showcase.cassandrademo.services;

import com.datastax.driver.core.PagingState;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.querybuilder.QueryBuilder;
import com.datastax.driver.core.querybuilder.Select;
import com.showcase.cassandrademo.domain.Users;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.cassandra.core.CassandraTemplate;
import org.springframework.data.cassandra.core.convert.CassandraConverter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private static final Logger LOGGER= LoggerFactory.getLogger(UserService.class);

    @Autowired
    private CassandraTemplate cassandraTemplate;

    @Autowired
    private Session session;


    public Page<Users> getUserPage(String pageState,int pageSie) {

        Select users1 = QueryBuilder.select().from("users");
        List<Users> users = findAllPagination(users1,pageState,pageSie);
        return new PageImpl<>(users);
    }

    public void createUsers(Users users) {

        cassandraTemplate.insert(users);
    }


    public  List<Users> findAllPagination(Select select , String page, int fetchSize) {

        select.setFetchSize(fetchSize);

        if (StringUtils.isNotBlank(page)) {
            select.setPagingState(PagingState.fromString(page));
        }

        CassandraConverter converter = cassandraTemplate.getConverter();

        ResultSet resultSet = session.execute(select);

        PagingState newPagingState = resultSet.getExecutionInfo().getPagingState();
        int remaining = resultSet.getAvailableWithoutFetching();
        List<Users> datas = new ArrayList<>();

        for (Row row : resultSet) {
            Users data = converter.read(Users.class, row);
            datas.add(data);
            if (--remaining == 0) {
                break;
            }
        }
        String serializedNewPagingState = newPagingState != null ? newPagingState.toString() : null;

        return datas;
    }
}
