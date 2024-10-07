package com.ecommerce.ecommerce.utils;

import com.ecommerce.ecommerce.common.PageResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class Utils {
    public static <T, R> PageResponse<R> generatePageResponse(
            Page<T> page,
            Function<T, R> mapper
            ){
        List<R> mapped = page.stream()
                .map(mapper)
                .collect(Collectors.toList());

        return PageResponse.<R>builder()
                .content(mapped)
                .number(page.getNumber())
                .size(page.getSize())
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .first(page.isFirst())
                .last(page.isLast())
                .build();
    }

    public static Pageable pageableSortedBy(
            int page,
            int size,
            String sorter,
            boolean ascending
    ){
        return PageRequest.of(
                page,
                size,
                ascending ? Sort.by(sorter): Sort.by(sorter).descending()
        );
    }

    public static long dollarToPennies(BigDecimal usdAmount) {
        var pennies = usdAmount.multiply(new BigDecimal(100)).setScale(0, RoundingMode.HALF_UP);
        return pennies.longValueExact();
    }

}
