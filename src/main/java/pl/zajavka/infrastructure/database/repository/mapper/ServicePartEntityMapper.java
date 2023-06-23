package pl.zajavka.infrastructure.database.repository.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import pl.zajavka.infrastructure.database.entity.PartEntity;
import pl.zajavka.infrastructure.database.entity.ServicePartEntity;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ServicePartEntityMapper {
    ServicePartEntity mapToEntity(PartEntity partEntity);
}
