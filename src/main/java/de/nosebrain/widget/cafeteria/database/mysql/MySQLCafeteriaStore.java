package de.nosebrain.widget.cafeteria.database.mysql;

import static de.nosebrain.util.ValidationUtils.present;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.nosebrain.widget.cafeteria.database.mysql.param.CafeteriaParam;
import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.service.CafeteriaStore;

@Component
public class MySQLCafeteriaStore implements CafeteriaStore {
  
  @Autowired
  private SqlSessionFactory factory;

  private final ObjectMapper mapper = new ObjectMapper();

  @Override
  public Cafeteria getCafeteria(final String key) {
    final SqlSession session = this.factory.openSession();
    try {
      final CafeteriaParam cafeteriaResult = session.selectOne("getCafeteria", key);
      if (!present(cafeteriaResult)) {
        return null;
      }
      try {
        final Cafeteria cafeteria = this.mapper.readValue(cafeteriaResult.getValue(), Cafeteria.class);
        cafeteria.setLastUpdated(cafeteriaResult.getLastUpdate());
        return cafeteria;
      } catch (final IOException e) {
        throw new RuntimeException(e);
      }
    } finally {
      session.close();
    }
  }

  @Override
  public void storeCafeteria(final String key, final Cafeteria cafeteria) {
    try {
      final StringWriter writer = new StringWriter();
      this.mapper.writeValue(writer, cafeteria);
      
      final CafeteriaParam param = new CafeteriaParam();
      param.setKey(key);
      param.setValue(writer.toString());
      
      final SqlSession session = this.factory.openSession();
      try {
        session.insert("insertCafeteria", param);
        session.commit();
      } finally {
        session.close();
      }
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }
  }
  
  @Override
  public void storeMetaData(final String key, final String metaData) {
    final SqlSession session = this.factory.openSession();
    try {
      final CafeteriaParam param = new CafeteriaParam();
      param.setKey(key);
      param.setValue(metaData);
      
      session.insert("insertMetaData", param);
      session.commit();
    } finally {
      session.close();
    }
  }

}
