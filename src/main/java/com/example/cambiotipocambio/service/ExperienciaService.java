package com.example.cambiotipocambio.service;

import com.example.cambiotipocambio.domain.ExchangeOperation;
import com.example.cambiotipocambio.dto.ExchangeRequestDTO;
import com.example.cambiotipocambio.dto.ExchangeResponseDTO;
import com.example.cambiotipocambio.exception.ResourceNotFoundException;
import com.example.cambiotipocambio.repository.ExchangeOperationRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

/**
 * Servicio para procesar operaciones de tipo de cambio y gestionar la lógica de negocio relacionada con la experiencia del usuario.
 */
@Service
public class ExperienciaService {

    private final UserValidationService userValidationService;
    private final SoporteService soporteService;
    private final ExchangeOperationRepository exchangeOperationRepository;

    /**
     * Constructor para inyectar las dependencias necesarias.
     *
     * @param userValidationService Servicio para validar y obtener información del usuario.
     * @param soporteService Servicio para gestionar los tipos de cambio.
     * @param exchangeOperationRepository Repositorio para almacenar operaciones de tipo de cambio.
     */
    public ExperienciaService(UserValidationService userValidationService,
                              SoporteService soporteService,
                              ExchangeOperationRepository exchangeOperationRepository) {
        this.userValidationService = userValidationService;
        this.soporteService = soporteService;
        this.exchangeOperationRepository = exchangeOperationRepository;
    }

    /**
     * Procesa una solicitud de tipo de cambio.
     *
     * @param request DTO con los datos necesarios para realizar el tipo de cambio.
     * @return Un Mono con la respuesta del tipo de cambio, incluyendo detalles de la operación.
     */
    public Mono<ExchangeResponseDTO> processExchange(ExchangeRequestDTO request) {
        return userValidationService.validateUserAndGetName(request.getUserId())
                .flatMap(userName -> soporteService.findRate(request.getMonedaOrigen(), request.getMonedaDestino())
                        .switchIfEmpty(Mono.error(new ResourceNotFoundException("No se encontró tipo de cambio")))
                        .flatMap(rate -> {
                            double finalAmount = request.getMonto() * rate.getRate();
                            ExchangeOperation operation = ExchangeOperation.builder()
                                    .userName(userName)
                                    .initialAmount(request.getMonto())
                                    .finalAmount(finalAmount)
                                    .exchangeRate(rate.getRate())
                                    .currencyFrom(request.getMonedaOrigen())
                                    .currencyTo(request.getMonedaDestino())
                                    .processDate(LocalDateTime.now())
                                    .build();
                            return exchangeOperationRepository.save(operation)
                                    .map(op -> {
                                        ExchangeResponseDTO response = new ExchangeResponseDTO();
                                        response.setUserName(op.getUserName());
                                        response.setInitialAmount(op.getInitialAmount());
                                        response.setFinalAmount(op.getFinalAmount());
                                        response.setCurrencyFrom(op.getCurrencyFrom());
                                        response.setCurrencyTo(op.getCurrencyTo());
                                        response.setExchangeRate(op.getExchangeRate());
                                        response.setProcessDate(op.getProcessDate());
                                        return response;
                                    });
                        })
                );
    }
}