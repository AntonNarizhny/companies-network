package ru.wanderer.companies.domain.dto;

import lombok.Value;

import java.util.Map;

@Value
public class Email {
    String to;
    String subject;
    String template;

    Map<String, Object> properties;
}
