package com.onsale.deals.bo;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.onsale.deals.cache.aspect.*;

import javax.persistence.*;
import java.io.Serializable;
import com.onsale.deals.cache.aspect.Cacheable;

@Cacheable
@Entity
@Table(name="sample")
public class Sample implements Serializable, MongoEntity<Sample> {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public BasicDBObject toMongoDocument() {
        BasicDBObject doc = new BasicDBObject();
        doc.put("table", this.getClass().getSimpleName());
        doc.put("id", getId());
        doc.put("email", getEmail());
        return doc;
    }

    public Sample getObject(DBObject doc) {
        this.id = (Long)doc.get("id");
        this.email = (String) doc.get("email");
        return this;
    }

}
