package de.nosebrain.widget.cafeteria.database.mysql;

import static de.nosebrain.util.ValidationUtils.present;

import java.io.IOException;
import java.io.StringWriter;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import de.nosebrain.widget.cafeteria.database.mysql.param.CafeteriaParam;
import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.model.config.CafeteriaInfo;
import de.nosebrain.widget.cafeteria.model.config.UniversityInfo;
import de.nosebrain.widget.cafeteria.service.CafeteriaStore;

@Component
public class MySQLCafeteriaStore implements CafeteriaStore {
  
  @Autowired
  private SqlSessionFactory factory;
  
  @Autowired
  private Map<String, UniversityInfo> universityInfo;

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
        return this.convertToCafeteria(cafeteriaResult, false);
      } catch (final IOException e) {
        throw new RuntimeException(e);
      }
    } finally {
      session.close();
    }
  }

  private Cafeteria convertToCafeteria(final CafeteriaParam cafeteriaParam, final boolean includeInfo) throws IOException {
    final Cafeteria cafeteria = this.mapper.readValue(cafeteriaParam.getValue(), Cafeteria.class);
    cafeteria.setLastUpdated(cafeteriaParam.getLastUpdate());
    
    if (includeInfo) {
      final String key = cafeteriaParam.getKey();
      final String[] keyInfo = key.split("_");
      final String uniKey = keyInfo[0];
      if (this.universityInfo.containsKey(uniKey)) {
        final UniversityInfo uniInfo = this.universityInfo.get(uniKey);
        final int cafId = Integer.parseInt(keyInfo[1]);
        final CafeteriaInfo cafeteriaInfo = uniInfo.getCafeterias().get(cafId);
        cafeteria.setCafeteriaInfo(cafeteriaInfo);
      }
    }
    return cafeteria;
  }
  
  @Override
  public List<Cafeteria> getCafeterias(final int limit) {
    final SqlSession session = this.factory.openSession();
    try {
      final List<CafeteriaParam> cafeteriaParams = session.selectList("getCafeterias", limit);
      final List<Cafeteria> cafeterias = new LinkedList<Cafeteria>();
      
      if (present(cafeteriaParams)) {
        for (final CafeteriaParam cafeteriaParam : cafeteriaParams) {
          cafeterias.add(this.convertToCafeteria(cafeteriaParam, true));
        }
      }
      return cafeterias;
    } catch (final IOException e) {
      throw new RuntimeException(e);
    }finally {
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
