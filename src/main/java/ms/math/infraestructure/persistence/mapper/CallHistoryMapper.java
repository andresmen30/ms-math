package ms.math.infraestructure.persistence.mapper;

import org.mapstruct.Mapper;

import ms.math.domain.model.CallHistoryModel;
import ms.math.infraestructure.persistence.entity.CallHistory;

@Mapper(componentModel = "spring")
public interface CallHistoryMapper {

   CallHistoryModel toModel(final CallHistory entity);

   CallHistory toEntity(final CallHistoryModel model);

}
