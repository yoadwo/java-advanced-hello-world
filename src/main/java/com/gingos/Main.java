package com.gingos;

import com.gingos.hibernate.HibernateHelperService;
import com.gingos.models.Student;
import com.gingos.models.User;
import com.gingos.services.UsersHttpService;
import jakarta.persistence.Query;
import org.hibernate.Session;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        var hibernateHelper = new HibernateHelperService();
        var usersHttp = new UsersHttpService();
        CompletableFuture<User> user;
        try {
            user = usersHttp.GetRandomUserAsync();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        try (var session = hibernateHelper.openSession()) {
            //CREATE
            saveStudentRecord(session, user.get());
            //READ
            var students = fetchStudentRecord(session);
            //UPDATE
            updateStudentRecord(session, students.get(0));
            //READ
            fetchStudentRecord(session);
            //DELETE
            deleteStudentRecord(session, students.get(0));
            //READ
            fetchStudentRecord(session);
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static void deleteStudentRecord(Session session, Student student) {
        int id = student.getId(); // update arbitrary students
        Student student1 = (Student) session.get(Student.class, id);
        session.beginTransaction();
        session.remove(student1);
        session.getTransaction().commit();
        System.out.println("Record deleted succesfully...");
    }

    private static void updateStudentRecord(Session session, Student student) {
        int id = student.getId(); // update arbitrary students
        Student student1 = (Student) session.get(Student.class, id);
        student1.setLastName(student1.getLastName() + " Jr");
        session.beginTransaction();
        session.merge(student1);
        session.getTransaction().commit();
        System.out.println("Record updated succesfully...");
    }

    private static List<Student> fetchStudentRecord(Session session) {
        Query query = session.createQuery("FROM Student");
        List<Student> students = query.getResultList();
        //students.forEach(obj -> System.out.println(obj.getFirstName()));
        System.out.println("Reading student records...");
        for (Student studentObj : students) {
            System.out.println("First Name: " + studentObj.getFirstName());
            System.out.println("Last Name: " + studentObj.getLastName());
            System.out.println("Email: " + studentObj.getStandard());
            System.out.println("--");
        }
        System.out.println();
        return students;
    }

    private static void saveStudentRecord(Session session, User user) {
        Student s1 = new Student();
        s1.setFirstName(user.firstname);
        s1.setLastName(user.lastname);
        s1.setRoll(01);
        s1.setStandard(user.email);
        session.beginTransaction();
        session.persist(s1);
        session.getTransaction().commit();
        System.out.println("Record saved succesfully...");
    }
}