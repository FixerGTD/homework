package com.velsera.homework.util.filter;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public final class FilterUtil {

    private FilterUtil() {
    }

    public static Pageable getPageable(FiltrationParameters filtrationParameters) {
        Sort orderBy = Sort.unsorted();
        Pageable pagination;

        String direction = filtrationParameters.direction();
        String sort = filtrationParameters.sort();
        Integer limit = filtrationParameters.limit();
        Integer page = filtrationParameters.page();

        if ((direction != null && !direction.isBlank()) && (sort != null && !sort.isBlank())) {
            var sorting = Sort.by(sort);
            orderBy = "ASC".equalsIgnoreCase(direction) ? sorting.ascending() : sorting.descending();
        }

        if (limit == null) {
            page = 1;
            limit = Integer.MAX_VALUE;
        }

        //    Change with -1 is done because EMS UI is based on pages starting from 1,
        //    but Spring is based on pages starting from 0
        page = (page == null) ? 0 : page - 1;
        pagination = PageRequest.of(page, limit, orderBy);

        return pagination;
    }
}
