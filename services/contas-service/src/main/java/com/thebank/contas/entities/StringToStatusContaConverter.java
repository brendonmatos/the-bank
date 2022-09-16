package com.thebank.contas.entities;

import org.springframework.core.convert.converter.Converter;
import com.thebank.contas.entities.Conta.StatusConta;

public class StringToStatusContaConverter implements Converter<String, StatusConta> {
    @Override
    public StatusConta convert(String source) {
        return StatusConta.valueOf(source.toUpperCase());
    }
}