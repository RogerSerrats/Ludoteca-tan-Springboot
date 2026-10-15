package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.author.model.Author;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import org.springframework.data.domain.Page;

/**
 * @author ccsw
 *
 */
public interface LoanService {

  /**
   * Método para recuperar un listado paginado de {@link Author}
   *
   * @param dto dto de búsqueda
   * @return {@link Page} de {@link Author}
   */
  Page<Loan> findPage(LoanSearchDto dto);

  /**
   * Método para crear o actualizar un {@link Author}
   *
   * @param id PK de la entidad
   * @param dto datos de la entidad
   */
  void save(Long id, LoanDto dto);

  /**
   * Método para crear o actualizar un {@link Author}
   *
   * @param id PK de la entidad
   */
  void delete(Long id) throws Exception;

}

