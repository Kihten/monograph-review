package ru.omstu.monographreview.models.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MonographStatus {
    APPLICATION_CHECK("Проверка заявки"),
    REVIEWER_SELECTION("Подбор рецензентов"),
    REVIEWING("Рецензирование"),
    PENDING_DECISION("Ожидание решения НТС");

    private String text;
}
