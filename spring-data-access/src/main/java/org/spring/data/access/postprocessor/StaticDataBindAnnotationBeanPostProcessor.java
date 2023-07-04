package org.spring.data.access.postprocessor;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.spring.data.access.annotation.BindStaticData;
import org.spring.data.access.model.BaseEntity;
import org.spring.data.access.util.IDGenerator;
import org.spring.data.access.util.JsonConverter;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@Setter(onMethod = @__({@Autowired}))
public class StaticDataBindAnnotationBeanPostProcessor implements BeanPostProcessor {

    private JsonConverter jsonConverter;
    private IDGenerator idGenerator;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException{
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field field : declaredFields) {
            BindStaticData annotation = field.getAnnotation(BindStaticData.class);
            if (Objects.nonNull(annotation) && Map.class.isAssignableFrom(field.getType())) {
                log.info("Start data binding field: [{}:{}]", field.getType(), field.getName());
                Class<? extends BaseEntity> aClass = annotation.castTo();
                String fileLocation = annotation.fileLocation();
                Map<Long, BaseEntity> entityMap = instantiateCollection(aClass, fileLocation);
                field.setAccessible(true);
                ReflectionUtils.setField(field, bean, entityMap);
                log.info("Data successfully bind");
            }
        }
        return bean;
    }

    private Map<Long, BaseEntity> instantiateCollection(Class<? extends BaseEntity> aClass, String fileLocation) {
        Map<Long, BaseEntity> storage = new HashMap<>();
        List<? extends BaseEntity> entities = jsonConverter.fromJson(fileLocation, aClass);

        entities.forEach(entity -> {
            long entityID = idGenerator.generate(aClass);
            entity.setId(entityID);
            storage.put(entityID, entity);
        });
        return storage;
    }
}
