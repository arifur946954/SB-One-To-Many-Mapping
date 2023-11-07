package com.jpa.JpaCrud.dao;

import com.jpa.JpaCrud.entity.Course;
import com.jpa.JpaCrud.entity.Instructor;
import com.jpa.JpaCrud.entity.InstructorDetails;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class AppDaoImp implements AppDao{
    private EntityManager entityManager;
     @Autowired
    public AppDaoImp(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void save(Instructor theInstructor) {
         entityManager.persist(theInstructor);

    }

    @Override
    public Instructor findInstructorById(int theId) {
        return entityManager.find(Instructor.class ,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorById(int theId) {
      Instructor theInstructor=   entityManager.find(Instructor.class ,theId);
        System.out.println("Output test is "+theInstructor);
        entityManager.remove(theInstructor);
    }

    @Override
    public InstructorDetails findInstructoDetailsById(int theId) {
        return entityManager.find(InstructorDetails.class,theId);
    }

    @Override
    @Transactional
    public void deleteInstructorDetails(int theId) {
       InstructorDetails tempIns=  entityManager.find(InstructorDetails.class,theId);
        System.out.println("Output test is "+tempIns);
       tempIns.getInstructor().setInstructorDetails(null);
        entityManager.remove(tempIns);
        //this is bi
    }

    @Override
    public List<Course> findCourseByInstructor(int theId) {
        TypedQuery<Course> query=entityManager.createQuery(
                "from Course where instructor.id=:data", Course.class);
        query.setParameter("data",theId);
        List<Course> courses=query.getResultList();
        return courses;


    }

    @Override
    public Instructor findInstructorJoinFetch(int theId) {
         TypedQuery<Instructor> query=entityManager.createQuery(
                 "select i from Instructor i "
                 +" JOIN FETCH i.courses "
                 + " where i.id = :data", Instructor.class
         );
         query.setParameter("data",theId);
         Instructor instructor=query.getSingleResult();
        return instructor;
    }


}
