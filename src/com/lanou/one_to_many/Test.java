package com.lanou.one_to_many;

import com.lanou.domain.Classes;
import com.lanou.domain.Student;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;

/**
 * Created by dllo on 18/1/8.
 */
public class Test {
    /*数据库连接工厂对象*/
    protected SessionFactory sessionFactory;
    /*数据库连接对象 真正用于数据库的crud操作*/
    protected Session session;
    /*数据库事物对象*/
    protected Transaction transaction;

    @Before
    public void init(){
        /*1.构建配置对象*/
        Configuration congif=new Configuration();
        /*摸人加载src下的配置文件Hibernate.cfg.xml*/
        congif.configure();
        /*初始化工厂对象*/
        sessionFactory=congif.buildSessionFactory();
        /*得到连接对象*/
        session=sessionFactory.openSession();
        /*开启一个事物*/
        transaction=session.beginTransaction();
    }

    @After
    public void destory(){
        /*提交事物*/
        transaction.commit();
        /*关闭连接*/
        session.close();
    }

    @org.junit.Test
    public void save(){
        Student student=new Student("王文","123");
        Classes classes=new Classes("J1005","Java班");
        //维护关系的一方 学生 绑定 班级
        student.setClasses(classes);
        /*到这student和classes均是临时状态*/
        session.save(student);
    }

    @org.junit.Test
    public void query(){
        //获取地为1的学生对象
        Student student=session.get(Student.class,1);
        System.out.println(student);
        //获取学生的所在班级对象
        /*在级联关系中,默认情况是懒加载,即获取学生对象
        * 时不会直接调用外键边的查询,只有当需要时才会执行关联表的查询*/
        System.out.println(student.getClasses());
    }
    @org.junit.Test
    public void test1(){
        /*1.保存班级级联保存学生*/
        Classes classes=new Classes("J1106","Java班");
        Student student=new Student("朱海伦","234");
        //学生绑定班级
        student.setClasses(classes);
        session.save(classes);//保存班级
        //需要多调一次保存 对学生对想的保存
        session.save(student);

        /*在单向的一对多的关系中,如果在多的一方维护关系
        * 则1的一方没办法完成级联的保存*/

    }

}
