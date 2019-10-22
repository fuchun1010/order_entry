package com.tk.app.common.ds;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.Map;
import java.util.function.Function;

/**
 * @author tank198435163.com
 */
@Configuration
public class DataSourceCfg {


  @Bean("publicDs")
  public DataSource orderPublicDataSource() {
    String url = this.env.getProperty("spring.dataSource.public.url");
    return this.initDataSource(url, this::createDataSourceByUrl);
  }

  @Bean("orderDs0")
  public DataSource orderInd0DataSource() {
    String url = this.env.getProperty("spring.dataSource.ind_0.url");
    return this.initDataSource(url, this::createDataSourceByUrl);
  }

  @Bean("orderDs1")
  public DataSource orderInd1DataSource() {
    String url = this.env.getProperty("spring.dataSource.ind_1.url");
    return this.initDataSource(url, this::createDataSourceByUrl);
  }

  @Bean("orderDs2")
  public DataSource orderInd2DataSource() {
    String url = this.env.getProperty("spring.dataSource.ind_2.url");
    return this.initDataSource(url, this::createDataSourceByUrl);
  }

  @Bean("orderDs3")
  public DataSource orderInd3DataSource() {
    String url = this.env.getProperty("spring.dataSource.ind_3.url");
    return this.initDataSource(url, this::createDataSourceByUrl);
  }

  @Bean
  public OrderSourceRouter initOrderSourceRouter() {
    OrderSourceRouter orderSourceRouter = new OrderSourceRouter();
    orderSourceRouter.setTargetDataSources(this.globalDataSource);
    orderSourceRouter.setDefaultTargetDataSource(this.orderPublicDataSource());
    return orderSourceRouter;
  }

  @Bean
  public JdbcTemplate initJdbcTemplate(@NonNull @Autowired final OrderSourceRouter orderSourceRouter) {
    JdbcTemplate jdbcTemplate = new JdbcTemplate(orderSourceRouter);
    return jdbcTemplate;
  }

  private DataSource initDataSource(@NonNull final String url, @NonNull final Function<String, DataSource> fun) {
    return fun.apply(url);
  }


  @SneakyThrows
  private DataSource createDataSourceByUrl(@NonNull final String url) {
    DruidDataSource ds = new DruidDataSource();
    String driver = propKey("driver");
    String username = propKey("username");
    String password = propKey("password");
    int minIdle = propKey("minIdle", Integer.class);
    ds.setDriverClassName(driver);
    ds.setUrl(url);
    ds.setUsername(username);
    ds.setPassword(password);
    ds.setValidationQuery(propKey("validationQuery"));
    ds.setMinIdle(minIdle);
    ds.setTestOnBorrow(false);
    ds.setTestOnReturn(false);
    ds.setPoolPreparedStatements(false);
    ds.setFilters("stat,wall");
    ds.setTestWhileIdle(true);
    ds.setTimeBetweenEvictionRunsMillis(6000);
    ds.setKeepAlive(true);
    ds.setMinIdle(1);
    ds.setInitialSize(1);
    return ds;
  }

  private String propKey(@NonNull final String name) {
    return this.propKey(name, String.class);
  }

  private <T> T propKey(@NonNull final String name, Class<T> clazz) {
    StringBuilder sb = new StringBuilder("spring.dataSource.");
    String key = sb.append(name).toString();
    return this.env.getProperty(key, clazz);
  }


  @Autowired
  private Environment env;

  @Autowired
  @Qualifier("globalDataSource")
  private Map<Object, Object> globalDataSource;

}
