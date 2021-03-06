package org.rapidpm.microdao.model.dao.actions;

import com.zaxxer.hikari.HikariDataSource;
import org.rapidpm.microdao.Constants;
import org.rapidpm.microservice.persistence.jdbc.JDBCConnectionPools;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Copyright (C) 2015 RapidPM
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * Created by RapidPM - Team on 13.02.16.
 */
public interface Update {

  default int update(JDBCConnectionPools connectionPools) {
    final HikariDataSource dataSource = connectionPools.getDataSource(dbPoolName());
    try {
      final Connection connection = dataSource.getConnection();
      final Statement statement = connection.createStatement();
      final String sql = createSQL();
      final int count = statement.executeUpdate(sql);
      statement.close();
      dataSource.evictConnection(connection);
      return count;
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return -1;
  }

  default String dbPoolName() {
    return Constants.POOL_NAME_WRITER.value();
  }


  String createSQL();

}
