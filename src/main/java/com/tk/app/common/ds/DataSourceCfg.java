package com.tk.app.common.ds;

import com.alibaba.druid.pool.DruidDataSource;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

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


  private DataSource initDataSource(@NonNull final String url, @NonNull final Function<String, DataSource> fun) {
    return fun.apply(url);
  }


  @SneakyThrows
  private DataSource createDataSourceByUrl(@NonNull final String url) {
    DruidDataSource ds = new DruidDataSource();
    String driver = env.getProperty(propKey("driver"));
    String username = env.getProperty(propKey("username"));
    String password = env.getProperty(propKey("password"));
    int minIdle = env.getProperty(propKey("minIdle"), Integer.class);
    ds.setDriverClassName(driver);
    ds.setUsername(username);
    ds.setPassword(password);
    ds.setValidationQuery("select 1");
    ds.setMinIdle(minIdle);
    ds.setTestOnBorrow(false);
    ds.setTestOnReturn(false);
    ds.setPoolPreparedStatements(false);
    ds.setFilters("stat,wall");
    return ds;
  }

  private String propKey(@NonNull final String name) {
    StringBuilder sb = new StringBuilder("spring.dataSource.");
    return sb.append(name).toString();
  }


  @Autowired
  private Environment env;


  @Autowired
  @Qualifier("globalDataSource")
  private Map<Object, Object> globalDataSource;

}
