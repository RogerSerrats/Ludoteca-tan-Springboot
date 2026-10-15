package com.ccsw.tutorial.client;

import com.ccsw.tutorial.client.model.Client;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ClientTest {

  public static final Long EXISTS_CLIENT_ID = 1L;
  public static final Long NOT_EXISTS_CLIENT_ID = 0L;

  @Mock
  private ClientRepository clientRepository;

  @InjectMocks
  private ClientServiceImpl clientService;

  @Test
  public void getExistsClientIdShouldReturnClient() {

    Client client = mock(Client.class);
    when(client.getId()).thenReturn(EXISTS_CLIENT_ID);
    when(clientRepository.findById(EXISTS_CLIENT_ID)).thenReturn(Optional.of(client));

    Client clientResponse = clientService.get(EXISTS_CLIENT_ID);

    assertNotNull(clientResponse);

    assertEquals(EXISTS_CLIENT_ID, clientResponse.getId());
  }

  @Test
  public void getNotExistsClientIdShouldReturnNull() {

    when(clientRepository.findById(NOT_EXISTS_CLIENT_ID)).thenReturn(Optional.empty());

    Client client = clientService.get(NOT_EXISTS_CLIENT_ID);

    assertNull(client);
  }
}
