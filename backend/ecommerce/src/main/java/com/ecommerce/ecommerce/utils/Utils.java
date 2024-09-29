package com.ecommerce.ecommerce.utils;


import org.springframework.data.domain.Page;

import com.ecommerce.ecommerce.common.PageResponse;

import java.util.List;
import java.util.function.Function;

public class Utils {
    public static <T, R> PageResponse<R> generatePageResponse(
            Page<T> page,
            Function<T, R> mapper
            ){
        List<R> mapped = page.stream()
                .map(mapper)
                .toList() ;

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
}
