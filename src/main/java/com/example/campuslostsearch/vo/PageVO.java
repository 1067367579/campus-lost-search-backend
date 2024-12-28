package com.example.campuslostsearch.vo;

import lombok.Data;
import java.util.List;

@Data
public class PageVO<T> {
    private List<T> list;
    private Long total;
    private Integer pageNum;
    private Integer pageSize;

    public PageVO() {
    }

    public PageVO(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        this.list = list;
        this.total = total;
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public static <T> PageVO<T> of(List<T> list, Long total, Integer pageNum, Integer pageSize) {
        return new PageVO<>(list, total, pageNum, pageSize);
    }

    public static <T> PageVOBuilder<T> builder() {
        return new PageVOBuilder<>();
    }

    public static class PageVOBuilder<T> {
        private List<T> list;
        private Long total;
        private Integer pageNum;
        private Integer pageSize;

        PageVOBuilder() {
        }

        public PageVOBuilder<T> list(List<T> list) {
            this.list = list;
            return this;
        }

        public PageVOBuilder<T> total(Long total) {
            this.total = total;
            return this;
        }

        public PageVOBuilder<T> pageNum(Integer pageNum) {
            this.pageNum = pageNum;
            return this;
        }

        public PageVOBuilder<T> pageSize(Integer pageSize) {
            this.pageSize = pageSize;
            return this;
        }

        public PageVO<T> build() {
            return new PageVO<>(list, total, pageNum, pageSize);
        }
    }
} 