package com.praveen.cms.api.repository;

import com.praveen.cms.api.entity.Message;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MessageRepository extends JpaRepository<Message,Long> {

    @Query("select p from Message p where p.customer.email = :email")
    //Optional<List<Message>> findLimitMessageList(@Param("email") String email);
    Page<Message> findLimitMessageList(Pageable var1, @Param("email") String email);
}

/*

    SELECT p.id AS col_0_0_,
        c.name AS col_1_0_,
        p.id AS id1_1_,
        p.locale AS locale2_1_,
        p.name AS name3_1_
        FROM   Person p
        INNER JOIN
        Country c
        ON
        ( p.locale = c.locale )
        ORDER BY
        p.id*/


   /* @Query("SELECT c.year AS yearComment, COUNT(c.year) AS totalComment "
            + "FROM Comment AS c GROUP BY c.year ORDER BY c.year DESC")
    List<ICommentCount> countTotalCommentsByYearInterface();*/