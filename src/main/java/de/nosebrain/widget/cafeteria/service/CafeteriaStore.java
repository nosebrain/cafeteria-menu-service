package de.nosebrain.widget.cafeteria.service;

import java.util.List;

import de.nosebrain.widget.cafeteria.model.Cafeteria;

public interface CafeteriaStore {
  
  public Cafeteria getCafeteria(final String key);
  
  public List<Cafeteria> getCafeterias(int limit);
  
  public void storeCafeteria(final String key, final Cafeteria cafeteria);
  
  public void storeMetaData(final String key, final String metaData);
}
