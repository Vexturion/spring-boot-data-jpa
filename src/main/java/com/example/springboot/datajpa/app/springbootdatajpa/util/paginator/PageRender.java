package com.example.springboot.datajpa.app.springbootdatajpa.util.paginator;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class PageRender<T> {

    private String url;

    private Page<T> page;

    private int totalPaginas;

    private int numElementosPorPagina;

    private int paginaActual;

    private List<PageItem> paginas;

    public PageRender(String url, Page<T> page) {
        this.url = url;
        this.page = page;
        this.paginas = new ArrayList<PageItem>();
        numElementosPorPagina = page.getSize();
        totalPaginas = page.getTotalPages();
        paginaActual = page.getNumber() + 1;

        int desde = Math.max(1, paginaActual - numElementosPorPagina / 2);
        int hasta = Math.min(desde + numElementosPorPagina - 1, totalPaginas);

        for (int i = desde; i <= hasta; i++) {
            paginas.add(new PageItem(i, paginaActual == i));
        }
    }

    public boolean isFirst(){
        return page.isFirst();
    }

    public boolean isLast(){
        return page.isLast();
    }

    public boolean isHasNext(){
        return page.hasNext();
    }

    public boolean isHasPrevious(){
        return page.hasPrevious();
    }

}
