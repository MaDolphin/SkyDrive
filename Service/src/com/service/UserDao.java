package com.service;
import org.hibernate.*;
import java.util.*;
import com.entity.*;
import com.util.HibernateSessionFactory;

/**
 * Created by xfcq on 2016/7/2.
 */
public class UserDao {
    public List<User> queryAllUser(){
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from User");
        List<User> list = query.list();
        return list;
    }

    public Boolean CheckUser(String name,String pwd){
        boolean flag=false;
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from User where userName='"+name+"'");
        List<User> list = query.list();
        if(list!=null)
            if(list.get(0).getUserPwd().equals(pwd))
                flag=true;
        return flag;
    }

    public User findUser(String name,String pwd){
        Session session = HibernateSessionFactory.getSession();
        Query query = session.createQuery("from User a where a.userName=? and a.userPwd=?");
        query.setParameter(0,name);
        query.setParameter(1,pwd);
        List<User> list = query.list();
        if(list.size() != 0){
            return (User)list.get(0);
        }else{
            return null;
        }
    }

}
