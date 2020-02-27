package com.gable.dos.ddq.controller.itemWriter;

import com.gable.dos.ddq.model.Dimension;
import com.gable.dos.ddq.model.DimensionRule2;
import org.springframework.data.mongodb.core.MongoOperations;

import java.util.ArrayList;
import java.util.List;

public class writerDimension_rule3 extends org.springframework.batch.item.data.MongoItemWriter {

    public writerDimension_rule3() {
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
        List<Dimension> dimensions = new ArrayList<>();
        for (Object object : items) {
            System.out.println("Object of itemsList = " + object);
            for (Object d3 : (ArrayList) object) {
                System.out.println("Object DimensionRule3 = " + d3);
                dimensions.add((Dimension) d3);
            }
        }

        super.write(dimensions);
    }

    @Override
    protected void doWrite(List items) {
        super.doWrite(items);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }
}
