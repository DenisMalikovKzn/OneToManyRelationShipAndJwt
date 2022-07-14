package com.praveen.cms.api.entity;


import lombok.Getter;
import lombok.Setter;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.GenericField;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
//import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name="customer_detail")
//@Indexed(backend = "customer")
public class Customer extends BaseEntity {

    //@FullTextField(analyzer = "name")
    @Column(name="first_name")
    private String firstName;

    //@FullTextField(analyzer = "name")
    @Column(name="last_name")
    private String lastName;

    @Column(name="email")
    private String email;

    @Column(name="password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY,mappedBy = "customer",cascade = CascadeType.ALL)
    private List<Message> messageList;

    public Customer(){

    }
    public Customer(String firstName, String lastName, String email,String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public void addMessageToUser(Message msg) {
        if (messageList == null) {
            messageList = new ArrayList<>();
        }
        messageList.add(msg);
        msg.setCustomer(this);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
