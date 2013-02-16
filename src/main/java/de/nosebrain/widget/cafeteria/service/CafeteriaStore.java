package de.nosebrain.widget.cafeteria.service;

import de.nosebrain.widget.cafeteria.model.Cafeteria;

public interface CafeteriaStore {
  
  public Cafeteria getCafeteria(final String key);
  
  public void storeCafeteria(final String key, final Cafeteria cafeteria);
}
