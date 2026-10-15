package com.ccsw.tutorial.loan.model;

import com.ccsw.tutorial.client.model.ClientDto;
import com.ccsw.tutorial.game.model.GameDto;

import java.time.LocalDate;

/**
 * @author ccsw
 *
 */
public class LoanDto {

  private Long id;

  private GameDto game;

  private ClientDto client;

  private LocalDate dateini;

  private LocalDate datefin;

  public Long getId() {
    return id;
  }

  public ClientDto getClient() {
    return client;
  }

  public GameDto getGame() {
    return game;
  }

  public LocalDate getDateini() {
    return dateini;
  }

  public LocalDate getDatefin() {
    return datefin;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public void setGame(GameDto game) {
    this.game = game;
  }

  public void setClient(ClientDto client) {
    this.client = client;
  }

  public void setDateini(LocalDate dateini) {
    this.dateini = dateini;
  }

  public void setDatefin(LocalDate datefin) {
    this.datefin = datefin;
  }
}
