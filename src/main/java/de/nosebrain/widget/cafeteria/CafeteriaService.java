package de.nosebrain.widget.cafeteria;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import voldemort.client.StoreClient;
import voldemort.versioning.Versioned;
import de.nosebrain.widget.cafeteria.model.Cafeteria;
import de.nosebrain.widget.cafeteria.parser.MenuParser;

/**
 * 
 * @author nosebrain
 */
public class CafeteriaService {
	private static final Logger LOGGER = LoggerFactory.getLogger(CafeteriaService.class);

	@Autowired
	private Map<String, MenuParser> parserMap;
	@Autowired
	private StoreClient<String, Cafeteria> client;

	public Cafeteria getCafeteria(final String uni, final int id, final int week) {
		Versioned<Cafeteria> cafeteriaVersion = this.client.get(key(uni, id, week));
		if (cafeteriaVersion != null) {
			return cafeteriaVersion.getValue();
		}

		final MenuParser parser = this.parserMap.get(uni + "_parser");
		if (parser == null) {
			throw new IllegalArgumentException(uni + " not supported");
		}

		try {
			final Cafeteria cafeteria = parser.updateCafeteria(id, week);
			if (cafeteria != null) {
				// save in database
				cafeteriaVersion = new Versioned<Cafeteria>(cafeteria);

				this.client.put(key(uni, id, week), cafeteriaVersion);
				return cafeteria;
			}
		} catch (final Exception e) {
			LOGGER.error("", e);
		}

		// TODO: log try

		return null;
		// throw new ResourceNotFoundException();
	}

	private static String key(final String uni, final int id, final int week) {
		return uni + "_" + id + "_" + week;
	}
}
