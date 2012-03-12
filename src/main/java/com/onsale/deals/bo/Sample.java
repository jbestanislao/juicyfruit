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
public class Sample implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("id").append(id);
        sb.append("email").append(email);
        return sb.toString();
    }
}
