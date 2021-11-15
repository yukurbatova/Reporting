package ru.netology.data;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@RequiredArgsConstructor
public class RegistrationInfo {
    private final String city;
    private final LocalDate date;
    private final String name;
    private final String phone;
}