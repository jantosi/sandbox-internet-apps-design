package pl.lodz.p.ftims.pai.repository;

import pl.lodz.p.ftims.pai.web.soap.DoubleKey;

import javax.persistence.AttributeConverter;

/**
 * Created by antosikj (Jakub Antosik) on 05/02/16.
 */
public class DoubleKeyJpaConverter implements AttributeConverter<DoubleKey, Object[]>{
    @Override
    public Object[] convertToDatabaseColumn(DoubleKey attribute) {
        Long[] longs = new Long[3];
        longs[0] = attribute.id;
        longs[1] = attribute.dataSourceId;
        longs[2] = attribute.sourceDepartmentId;

        Object[] objects = new Object[3];
        System.arraycopy(longs, 0, objects, 0, longs.length);
        return objects;
    }

    @Override
    public DoubleKey convertToEntityAttribute(Object[] dbData) {
        return new DoubleKey((Long)dbData[0], (Long)dbData[1], (Long)dbData[2]);
    }
}
