package com.servlet;

import com.entity.User;
import com.service.UserDao;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.attribute.UserDefinedFileAttributeView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class UserServlet extends HttpServlet {

    UserDao userDao = new UserDao();
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        //response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        String uid=request.getParameter("uid");
        String upwd=request.getParameter("upwd");
        User user = userDao.findUser(uid,upwd);
        if(user != null){
            out.println(user.getUserNo());
        }
        else
            out.println("error");
        out.flush();
        out.close();
    }

}
