package mb.pso.issuesystem.service.impl.core;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

//[ ] REFACTOR
@Component
public class DtoMapper {
    private final ModelMapper mapper;

    public DtoMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public <T, U> T toDto(U entity, Class<T> dtoClass) {
        return mapper.map(entity, dtoClass);
    }

    public <T, U> List<T> toDtoList(List<U> entities, Class<T> dtoClass) {
        return entities.stream()
                .map(entity -> mapper.map(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public <T, U> Page<T> toDtoPage(Page<U> entities, Class<T> dtoClass) {
        List<T> dtoList = entities.getContent().stream()
                .map(entity -> mapper.map(entity, dtoClass))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }
}
