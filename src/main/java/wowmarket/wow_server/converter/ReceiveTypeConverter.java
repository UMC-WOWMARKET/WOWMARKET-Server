package wowmarket.wow_server.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import wowmarket.wow_server.domain.ReceiveType;

@Converter
public class ReceiveTypeConverter implements AttributeConverter<ReceiveType, Long> {

    @Override
    public Long convertToDatabaseColumn(ReceiveType receiveType) {
        if (receiveType == null)
            return null;
        return receiveType.getCode();
    }

    @Override
    public ReceiveType convertToEntityAttribute(Long dbData) {
        return ReceiveType.ofReceiveType(dbData);
    }
}
