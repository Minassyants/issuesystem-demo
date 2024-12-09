package mb.pso.issuesystem.service.impl.core;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;

import mb.pso.issuesystem.dto.core.GoodDto;
import mb.pso.issuesystem.dto.core.SubjectDto;
import mb.pso.issuesystem.dto.core.VehicleDto;
import mb.pso.issuesystem.entity.core.Good;
import mb.pso.issuesystem.entity.core.Subject;
import mb.pso.issuesystem.entity.core.Vehicle;


@Component
public class DtoMapper {
    private final ModelMapper mapper;

    public DtoMapper(ModelMapper mapper) {

        mapper.typeMap(Vehicle.class, VehicleDto.class);

        mapper.typeMap(Good.class, GoodDto.class);

        mapper.addConverter(context -> {
            Subject source = context.getSource();
            if (source instanceof Vehicle) {
                return mapper.map(source, VehicleDto.class);
            } else if (source instanceof Good) {
                return mapper.map(source, GoodDto.class);
            }
            return null;
        }, Subject.class, SubjectDto.class);

        this.mapper = mapper;
    }

    public <T, U> T toDto(U entity, Class<T> dtoClass) {
        return mapper.map(entity, dtoClass);
    }

    public <T, U> List<T> toDtoList(Collection<U> entities, Class<T> dtoClass) {
        return entities.stream()
                .map(entity -> mapper.map(entity, dtoClass))
                .collect(Collectors.toList());
    }

    public <T, U> Set<T> toDtoSet(Collection<U> entities, Class<T> dtoClass) {
        return entities.stream()
                .map(entity -> mapper.map(entity, dtoClass))
                .collect(Collectors.toSet());
    }

    public <T, U> Iterable<T> toDtoIterable(Iterable<U> entities, Class<T> dtoClass) {

        return StreamSupport.stream(entities.spliterator(), false).map(entity -> mapper.map(entity, dtoClass)).toList();
    }

    public <T, U> Page<T> toDtoPage(Page<U> entities, Class<T> dtoClass) {
        List<T> dtoList = entities.getContent().stream()
                .map(entity -> mapper.map(entity, dtoClass))
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, entities.getPageable(), entities.getTotalElements());
    }
}
