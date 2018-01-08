package com.lanou.test;

import com.lanou.domain.User;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.List;

/**
 * Created by dllo on 18/1/8.
 */
public class MainTest {
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

    @Test
    public void add(){
        /*创建一个新对象*/
        User user=new User("王五","12356");
        /*保存一个实体类对象,是以插入insert的方式调用
        * 即要求该对象的主键id不能有值,否则会报错*/
//        session.save(user);
        /*save方法插入时不考虑id,只将非主键的值执行insert插入
        *
        * saveOrUpdate方法先去检查当前对象的主键id是否
        * 存在,如果存在执行主键的update更新操作
        * 如果当前实体类对象的主键id不存在,则执行的是
        * save操作,结insert插入*/
//        user.setId(7);
        session.saveOrUpdate(user);
    }

    @Test
    public void delete(){
        User user=new User("王五","12356");
        /*删除某个对象,如果穿入的实体类对象没有设置主键id
        * 则不能进行任何操作*/
        user.setId(6);//设置要删除的id
        session.delete(user);
        /*session自带的delete方法只根据id进行删除,
        不考虑其他删除条件*/
    }

    @Test
    public void update(){
        User user =new User("王铭","123");
        user.setId(4);//update必须要设置更新的id
        /*update更新方法要求主键id有值,指明要更新的对象*/
        session.update(user);
    }

    @Test
    public void query(){
        /*1.根据主键id查询单个的对象
        * 2.根据sql执行查询*/

        //根据id查询单个对象
        //get:第一个参数给的是要查询的实体类类名
        //    第二个参数是主键id
        User user =session.get(User.class,4);
        System.out.println(user);

        User user1= (User) session.get("com.lanou.domain.User",4);
        System.out.println(user1);

        /*根据sql语句查询
        * 创建一个query语句对象,creatQuery中的参数
        * 是从from开始,不需要加入前面的select
        * sql语句中涉及的类名和属性名都指的是实体类中,
        * 不是数据库中的,hibernate内部会自动进行转换*/

        /*给条件语句中的条件设置别名,根据别名设置对应的参数*/
        Query query=session.createQuery("from User where name=:a and password=:b");
        /*设置sql语句中?所对应的值*/
        query.setString("a","王铭");//第一个问号的替换值
        query.setString("b","123");//第二个问号的替换值
        List<User> users=query.list();
        System.out.println(users);
    }

    @Test
    public void query2(){
        Query query=session.createQuery(
                "from User where name=?");

        /*设置参数 即sql与剧中的问号对应的值
        * setString 内部实际上还是调用的setParameter
        * 只不过是直接给定参数的类型;而setParameter会
        * 根据属性类型自动匹配*/
        query.setParameter(0,"王五");//推荐使用的策略
        /*返回一个迭代器,门口人返回的是符合条件的对象主键id
        * 当进行结果遍历的时候需要进行二次查询(根据id)*/
        Iterator<User> iterator=query.iterate();
        /*
        遍历迭代器
         */
        while(iterator.hasNext()){
            User user=iterator.next();
            System.out.println(user);
        }
    }

    @Test
    public void query3(){
        Query query=session.createQuery("from User");
        /*分页处理*/
        int start=2;//起始页
        int pageSize=1;//每页大小
        query.setMaxResults(pageSize);
        /*返回结果集的偏移量*/
        query.setFirstResult((start-1)*pageSize);
        /*设置返回结果的最大条数 用它控制每页的数目*/
        /*符合条件的结果集*/
        List<User> users=query.list();
        System.out.println(users);
    }
}
