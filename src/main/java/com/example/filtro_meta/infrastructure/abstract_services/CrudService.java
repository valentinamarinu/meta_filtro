package com.example.filtro_meta.infrastructure.abstract_services;

import org.springframework.data.domain.Page;

public interface CrudService<RQ, RS, ID> {
    public Page<RS> getAll(int page, int size);
    
    public RS get(ID id);

    public RS create(RQ request);
    
    public RS update(RQ request, ID id);

    public void delete(ID id);
}
