package com.Controller;

import com.Entity.User;
import com.Service.UserService;
import com.Service.UserServiceImpl.UserServiceimpl;
import com.Util.BaseServlet;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "UserServlet", urlPatterns = "/userServlet")
public class UserServlet extends BaseServlet {
    //生成服务对象
    UserService userService = new UserServiceimpl();

    public String login(HttpServletRequest request, HttpServletResponse response) {
        //接收请求，调用服务，跳转界面
        String username = request.getParameter("username");
        String userPassword = request.getParameter("password");
        String checkCode = request.getParameter("checkCode");
        if (checkCode == null) {
            //登录失败，仍在原界面
            return "login.jsp";
        }
        if (username == null) {
            username = "213131";
            return "login.jsp";
        }
        if (userPassword == null) {
            userPassword = "21313";
            return "login.jsp";
        }
        //调用服务，取出对应用户名的用户id与密码
        User user = userService.queryByUserName(username);
        HttpSession session = request.getSession();
        String right_code = (String) session.getAttribute("checkCode_session");
        //判断密码是否一致
        //System.out.println(user);
        if (checkCode.equalsIgnoreCase(right_code)) {
            if (user != null && user.getPassword().equals(userPassword)) {
                //使用Session存入user信息
                session = request.getSession();
                session.setAttribute("User", user);
                request.setAttribute("userName", username);
                return "WEB-INF/Exam/studentIndex.jsp";
            }
        }
        //登录失败，仍在原界面
        return "login.jsp";
    }

    public String register(HttpServletRequest request, HttpServletResponse response) {
        //接收请求，调用服务，跳转界面
        //将前端的数据整合在一个user对象中
        User user = new User();
        user.setUsername(request.getParameter("username"));
        user.setPassword(request.getParameter("password"));
        user.setName(request.getParameter("name"));
        user.setAge(request.getParameter("age"));
        user.setSex(request.getParameter("sex"));
        user.setRole(request.getParameter("role"));
        user.setTelephone(request.getParameter("telephone"));
        //调用服务
        userService.insert(user);
        //跳转界面
        return "login.jsp";
    }

    public String exit(HttpServletRequest request, HttpServletResponse response) {
        request.getSession().removeAttribute("user");
        return "login.jsp";
    }

}
