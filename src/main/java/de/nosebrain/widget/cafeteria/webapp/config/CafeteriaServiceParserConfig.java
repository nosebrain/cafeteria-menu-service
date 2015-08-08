package de.nosebrain.widget.cafeteria.webapp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import de.nosebrain.widget.cafeteria.parser.KasselParser;
import de.nosebrain.widget.cafeteria.parser.WuerzburgParser;

@Configuration
public class CafeteriaServiceParserConfig {
  
  @Bean(name = { "uni-wue_parser", "uni-bam_parser"})
  public WuerzburgParser wuerzburgParser() {
    return new WuerzburgParser();
  }
  
  @Bean(name = "uni-ks_parser")
  public KasselParser kasselParser() {
    return new KasselParser();
  }
}
