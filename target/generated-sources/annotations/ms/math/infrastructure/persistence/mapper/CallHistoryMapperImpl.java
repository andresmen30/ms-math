package ms.math.infrastructure.persistence.mapper;

import javax.annotation.processing.Generated;
import ms.math.domain.model.CallHistoryModel;
import ms.math.infrastructure.persistence.entity.CallHistory;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-04-29T20:59:26-0500",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.6 (Amazon.com Inc.)"
)
@Component
public class CallHistoryMapperImpl implements CallHistoryMapper {

    @Override
    public CallHistoryModel toModel(CallHistory entity) {
        if ( entity == null ) {
            return null;
        }

        CallHistoryModel.CallHistoryModelBuilder callHistoryModel = CallHistoryModel.builder();

        callHistoryModel.id( entity.getId() );
        callHistoryModel.endpoint( entity.getEndpoint() );
        callHistoryModel.parameters( entity.getParameters() );
        callHistoryModel.request( entity.getRequest() );
        callHistoryModel.response( entity.getResponse() );
        callHistoryModel.status( entity.getStatus() );
        callHistoryModel.timestamp( entity.getTimestamp() );

        return callHistoryModel.build();
    }

    @Override
    public CallHistory toEntity(CallHistoryModel model) {
        if ( model == null ) {
            return null;
        }

        CallHistory callHistory = new CallHistory();

        callHistory.setId( model.getId() );
        callHistory.setEndpoint( model.getEndpoint() );
        callHistory.setParameters( model.getParameters() );
        callHistory.setRequest( model.getRequest() );
        callHistory.setResponse( model.getResponse() );
        callHistory.setStatus( model.getStatus() );
        callHistory.setTimestamp( model.getTimestamp() );

        return callHistory;
    }
}
