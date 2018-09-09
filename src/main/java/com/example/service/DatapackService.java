package com.example.service;

import com.example.dto.Header;
import com.example.dto.PageQueryDatapackDTO;
import com.example.entity.User;
import com.example.utils.SatTimeConvert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * https://www.cnblogs.com/web424/p/6756342.html
 */

@Repository
public class DatapackService {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 根据条件获取数量
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public int countData(PageQueryDatapackDTO params) {
        String sql = "select count(0) count from okw_" + params.getDataName() + " where 1=1 ";
        QueryParams queryParams = new QueryParams();
        queryParams = getWhereSql(params);
        sql += queryParams.whereSql;

        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    /**
     * 根据条件获取数据
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findData(PageQueryDatapackDTO params) {
        String sql = "select * from okw_" + params.getDataName() + " where 1=1 ";
        QueryParams queryParams = new QueryParams();
        queryParams = getWhereSql(params);
        sql += queryParams.whereSql;
        sql += queryParams.orderBySql;
        sql += queryParams.limitSql;
        // public java.util.List<java.util.Map<java.lang.String, java.lang.Object>> queryForList (java.lang.String sql)   throws DataAccessException
        //对于in_tinme列，queryForList的结果是2018-06-13 00:48:29.0，但是前端收到就是1528044415000格式，通过配置文件将格式转换了
        //list处理一下数据，再返回前端
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return resultHandle(list);
    }

    /**
     * 根据条件，返回指定列的数据，为画图列准备的数据
     *
     * @param params
     * @return
     */
    @Transactional(readOnly = true)
    public List<Map<String, Object>> findColumnData(PageQueryDatapackDTO params) {
        String sql = "select " + getColumnSql(params.getColumn()) + " from okw_" + params.getDataName() + " where 1=1 ";
        QueryParams queryParams = new QueryParams();
        queryParams = getWhereSql(params);
        sql += queryParams.whereSql;
        //TODO 排序要处理
        sql += " order by in_time asc";
        List<Map<String, Object>> list = jdbcTemplate.queryForList(sql);
        return resultHandle(list);
    }

    /**
     * 为画图生成返回列
     *
     * @param column a,b,c
     * @return
     */
    private String getColumnSql(String column) {
        StringBuilder columnSql = new StringBuilder();
        columnSql.append(" in_time x");
        String[] columnList = column.split(",");
        for (int i = 0; i < columnList.length; i++) {
            columnSql.append(" ," + columnList[i] + " y" + (i + 1));
        }
        return columnSql.toString();
    }

    private QueryParams getWhereSql(PageQueryDatapackDTO pageParams) {
        QueryParams queryParams = new QueryParams();
        StringBuilder whereSql = new StringBuilder();
        StringBuilder orderBySql = new StringBuilder();
        List params = new ArrayList();
        if (pageParams.getFromTime() != null) {
            //long time = SatTimeConvert.dateToSatTime(pageParams.getFromTime());//星上时间转换
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //不需要设置时区，因为数据库的连接url中已经设置了timezone
            //formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            String dateString = formatter.format(pageParams.getFromTime());
            whereSql.append(" and in_time>='" + dateString + "'");
        }
        if (pageParams.getToTime() != null) {
            //long time = SatTimeConvert.dateToSatTime(pageParams.getToTime());//星上时间转换
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //formatter.setTimeZone(TimeZone.getTimeZone("GMT"));
            String dateString = formatter.format(pageParams.getToTime());
            whereSql.append(" and in_time<='" + dateString + "'");
        }
        if (pageParams.getSort() != "" && pageParams.getSort() != null) {
            orderBySql.append(" order by " + pageParams.getSort() + " " + pageParams.getOrder());
        }
        if (pageParams.getSort() == "" || pageParams.getSort() == null) {
            orderBySql.append(" order by id desc");
        }
        int currentPage = 1;
        int pageSize = 10;
        if (pageParams.getCurrentPage() > 0) {
            currentPage = pageParams.getCurrentPage();
        }
        if (pageParams.getPageSize() > 0) {
            pageSize = pageParams.getPageSize();
        }
        queryParams.limitSql = " limit " + (currentPage - 1) * pageSize + " ," + pageSize;

        queryParams.whereSql = whereSql.toString();
        queryParams.orderBySql = orderBySql.toString();
        return queryParams;
    }

    private class QueryParams {
        String whereSql;
        String orderBySql;
        String limitSql;

        // 构造函数
        public QueryParams() {
            super();
        }
        // getters/setters(略)
    }

    /**
     * 处理查询结果，如星上时间转换，枚举型的列处理
     * 星上时间列名secondInteger
     * map操作参考https://blog.csdn.net/u013456370/article/details/62040079
     *
     * @param resultList
     * @return
     */
    private List<Map<String, Object>> resultHandle(List<Map<String, Object>> resultList) {
        for (Map<String, Object> map : resultList) {
            try {
                if (map.containsKey("secondInteger")) {
                    Long satTime = Long.valueOf(map.get("secondInteger").toString());
                    Date date = SatTimeConvert.satTimeToDate(satTime);
                    map.put("secondInteger", date);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return resultList;
    }


    @Transactional(readOnly = true)
    public List<User> findAll() {
        return jdbcTemplate.query("select * from users", new UserRowMapper());
    }

    @Transactional(readOnly = true)
    public User findUserById(int id) {
        return jdbcTemplate.queryForObject("select * from users where id=?", new Object[]{id}, new UserRowMapper());
    }

    public User create(final User user) {
        final String sql = "insert into users(name,role) values(?,?)";

        KeyHolder holder = new GeneratedKeyHolder();

        jdbcTemplate.update(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection connection)
                    throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, user.getName());
                ps.setInt(2, user.getRole());
                return ps;
            }
        }, holder);

        int newUserId = holder.getKey().intValue();
        user.setId(newUserId);
        return user;
    }

    public void delete(final Integer id) {
        final String sql = "delete from users where id=?";
        jdbcTemplate.update(sql,
                new Object[]{id},
                new int[]{java.sql.Types.INTEGER});
    }

    public void update(final User user) {
        jdbcTemplate.update(
                "update users set name=?,role=? where id=?",
                new Object[]{user.getName(), user.getRole(), user.getId()});
    }
}

class UserRowMapper implements RowMapper<User> {

    @Override
    public User mapRow(ResultSet rs, int rowNum) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setName(rs.getString("name"));
        user.setRole(rs.getInt("role"));

        return user;
    }

}


