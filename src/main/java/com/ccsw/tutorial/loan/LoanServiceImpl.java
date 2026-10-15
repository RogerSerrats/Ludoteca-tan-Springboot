package com.ccsw.tutorial.loan;

import com.ccsw.tutorial.client.ClientService;
import com.ccsw.tutorial.game.GameService;
import com.ccsw.tutorial.loan.model.Loan;
import com.ccsw.tutorial.loan.model.LoanDto;
import com.ccsw.tutorial.loan.model.LoanSearchDto;
import jakarta.transaction.Transactional;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

/**
 * @author ccsw
 *
 */
@Service
@Transactional
public class LoanServiceImpl implements LoanService {

  @Autowired
  LoanRepository loanRepository;

  @Autowired
  GameService gameService;

  @Autowired
  ClientService clientService;

  /**
   * {@inheritDoc}
   */
  @Override
  public Page<Loan> findPage(LoanSearchDto dto) {

    Specification<Loan> specification = LoanSpecification.search(dto.getGameId(), dto.getClientId(), dto.getDate());

    Pageable pageable = dto.getPageable().getPageable();

    return this.loanRepository.findAll(specification, pageable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void save(Long id, LoanDto dto) {

    Loan loan;

    if (id == null) {
      loan = new Loan();
    } else {
      loan = this.loanRepository.findById(id).orElse(null);
    }

    BeanUtils.copyProperties(dto, loan, "id", "game", "client");

    loan.setGame(gameService.get(dto.getGame().getId()));
    loan.setClient(clientService.get(dto.getClient().getId()));

    if (loan.getDatefin().isBefore(loan.getDateini())) {
      throw new RuntimeException("La fecha de fin no puede ser anterior a la fecha de inicio");
    }

    long days = ChronoUnit.DAYS.between(loan.getDateini(), loan.getDatefin());

    if (days > 14) {
      throw new RuntimeException("El préstamo no puede superar los 14 días");
    }

    List<Loan> loans = this.loanRepository.findAll();

    boolean gameAlreadyLoaned = loans.stream().filter(l -> !l.getId().equals(id)).filter(l -> l.getGame().getId().equals(loan.getGame().getId()))
      .anyMatch(l -> !loan.getDatefin().isBefore(l.getDateini()) && !loan.getDateini().isAfter(l.getDatefin()));

    if (gameAlreadyLoaned) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El juego ya está prestado para alguna de las fechas seleccionadas");
    }

    LocalDate currentDate = loan.getDateini();

    while (!currentDate.isAfter(loan.getDatefin())) {

      LocalDate dateToCheck = currentDate;

      long activeLoans = loans.stream().filter(l -> id == null || !l.getId().equals(id)).filter(l -> l.getClient().getId().equals(loan.getClient().getId()))
        .filter(l -> !dateToCheck.isBefore(l.getDateini()) && !dateToCheck.isAfter(l.getDatefin())).count();

      if (activeLoans >= 2) {
        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El cliente ya tiene 2 préstamos activos en alguna de las fechas seleccionadas");
      }

      currentDate = currentDate.plusDays(1);
    }

    this.loanRepository.save(loan);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void delete(Long id) throws Exception {

    if (this.loanRepository.findById(id).orElse(null) == null) {
      throw new Exception("Not exists");
    }

    this.loanRepository.deleteById(id);
  }

}

