package net.lantrack.framework.common.utils;

import com.mongodb.client.model.geojson.Polygon;
import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.GeoJson;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MongoDbUtils {

    @Autowired
    private MongoTemplate mongoTemplate;

    public Document saveJson(String json,String collName){
        if(StringUtils.isBlank(json) || StringUtils.isBlank(collName)){
            return null;
        }
        Document parse = Document.parse(json);
        return mongoTemplate.save(parse, collName);
    }

    public Document findById(String id,String collName){
        Document doc = mongoTemplate.findById(id, Document.class, collName);
        return doc;
    }

    public void save(Polygon polygon){
        mongoTemplate.save(polygon);
    }


    public <T> T findById(Class<T> entityClass, String id) {
        return mongoTemplate.findById(id, entityClass);
    }

    public <T> List<T> findAll(Class<T> entityClass) {
        return mongoTemplate.findAll(entityClass);
    }

    public <T> void remove(T entity) {
        mongoTemplate.remove(entity);
    }

    public <T> void add(T entity) {
        mongoTemplate.insert(entity);
    }

    public <T> void addAll(List<T> entity) {
        mongoTemplate.insertAll(entity);
    }

    public <T> void saveOrUpdate(T entity,String collName) {
        mongoTemplate.save(entity,collName);
    }

    public <T> T findOne(Class<T> entityClass) {
        return mongoTemplate.findOne(new Query(), entityClass);
    }

    public List<Polygon> findIntersective(GeoJson geoJson){
        Query query=new Query(Criteria.where("geometry").intersects(geoJson));
        List<Polygon> list=mongoTemplate.find(query,Polygon.class);
        return list;
    }

}
