package com.onsale.deals;

import com.mongodb.*;
import com.onsale.deals.bo.Sample;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.BeanUtilsBean;

import java.util.Map;
import java.util.Set;

public class SampleMongo {
    private static final String mongoDbName = "onsale";

    public static void main(String[] args) throws Exception {
        Sample sample = new Sample();
        sample.setId(2L);
        sample.setEmail("test");

        Object objectSample = (Object)sample;

        Map sampleMap = BeanUtilsBean.getInstance().describe(objectSample);



        System.out.println("sampleMap: " + sampleMap);

        BasicDBObject sampleMongo = new BasicDBObject(sampleMap);
        System.out.println("sampleMongo: " + sampleMongo);

        Mongo mongoDB = new Mongo("172.16.229.130", 27017);
        DB dbCache = mongoDB.getDB(mongoDbName);
        DBCollection coll = dbCache.getCollection("com.onsale.deals.bo.Sample");

        //coll.insert(sampleMongo);

        BasicDBObject query = new BasicDBObject();
        query.put("id", "2");
        DBObject mongoObject = coll.findOne(query);

        Sample sampleBean = new Sample();

        BeanUtils.copyProperties(sampleBean, mongoObject);

        System.out.println("mongoObject: " + mongoObject);
        System.out.println("sampleObject: " + sampleBean);

        /*Mongo mongo = new Mongo("localhost", 27017);
        DB dbCache = mongo.getDB(mongoDbName);
        DBCollection coll = dbCache.getCollection("com.onsale.deals.bo.Sample");

        BasicDBObject query = new BasicDBObject();
        query.put("id", 6);
        DBObject delObj = coll.findOne(query);

System.out.println("obj to be deleted: " + delObj);
System.out.println("");


        coll.remove(delObj);

        DBCursor cur = coll.find();
        while(cur.hasNext()) {
            System.out.println(cur.next());
        }

        mongo.close();*/
    }




/*
        Set<String> colls = dbCache.getCollectionNames();
        for (String s : colls) {
            System.out.println(s);
        }
*/


       /* BasicDBObject doc = new BasicDBObject();
        doc.put("name", "MongoDB5");
        doc.put("type", "database2");
        doc.put("count", 2);
        BasicDBObject info = new BasicDBObject();
        info.put("x", 204);
        info.put("y", 104);
        doc.put("info", info);
        coll.update(doc, doc);*/

}
