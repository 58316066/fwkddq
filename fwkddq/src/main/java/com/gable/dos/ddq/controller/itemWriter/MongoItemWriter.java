package com.gable.dos.ddq.controller.itemWriter;

import com.gable.dos.ddq.model.DimensionRule2;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.List;

@Data
@Slf4j
public class MongoItemWriter extends org.springframework.batch.item.data.MongoItemWriter {


    public MongoItemWriter() {
        super();
    }

    @Override
    public void setDelete(boolean delete) {
        super.setDelete(delete);
    }

    @Override
    public void setTemplate(MongoOperations template) {
        super.setTemplate(template);
    }

    @Override
    public void setCollection(String collection) {
        super.setCollection(collection);
    }

    @Override
    public void write(List items) throws Exception {
        System.out.println("itemsList = " + items);
        List<DimensionRule2> dimensionRule2 = new ArrayList<>();
        for (Object object : items) {
            System.out.println("Object of itemsList = " + object);
            for (Object d2 : (ArrayList) object) {
                System.out.println("Object DimensionRule2 = " + d2);
                dimensionRule2.add((DimensionRule2) d2);
            }
        }

        super.write(dimensionRule2);
    }

    @Override
    protected void doWrite(List items) {
        super.doWrite(items);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
