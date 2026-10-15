package com.ccsw.tutorial.game;

import com.ccsw.tutorial.game.model.Game;
import com.ccsw.tutorial.game.model.GameDto;

import java.util.List;

/**
 * @author ccsw
 *
 */
public interface GameService {

  /**
   * Recupera un {@link Game} a través de su ID
   *
   * @param id PK de la entidad
   * @return {@link Game}
   */
  Game get(Long id);

  List<Game> find(String title, Long idCategory);

  /**
   * Método para crear o actualizar un {@link Game}
   *
   * @param id PK de la entidad
   * @param dto datos de la entidad
   */
  void save(Long id, GameDto dto);

}


