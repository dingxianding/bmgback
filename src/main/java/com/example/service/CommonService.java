package com.example.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


/**
 * 描述：
 *
 * @author huchenqiang
 * @date 2018/8/29 09:32
 */
@Service
public class CommonService {

    public static Pageable getPageable(int page, int limit, String sort, String order) {
        if (page == 0) {
            page = 1;
        }
        if (limit == 0) {
            limit = 10;
        }
        Sort sortOrder = getSort(sort, order);

        Pageable pageable = new PageRequest(page - 1, limit, sortOrder);

        return pageable;
    }

    public static Sort getSort(String sort, String order) {
        Sort sortOrder = new Sort(Sort.Direction.DESC, "id");
        if (sort != null && !sort.isEmpty()) {
            if (order.equals("desc")) {
                sortOrder = new Sort(Sort.Direction.DESC, sort);
            } else {
                //默认升序
                sortOrder = new Sort(Sort.Direction.ASC, sort);
            }
        }
        return sortOrder;
    }

}
