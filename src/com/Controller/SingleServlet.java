package com.Controller;

import com.Entity.User;
import com.Entity.grade;
import com.Entity.single;
import com.Service.GradeService;
import com.Service.GradeServiceImpl.GradeServiceimpl;
import com.Service.SingleService;
import com.Service.SingleServiceImpl.SingleServiceimpl;
import com.Util.BaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

@WebServlet(name = "SingleServlet",urlPatterns = "/singleservlet")
//试题集controller
public class SingleServlet extends BaseServlet{
    SingleService service=new SingleServiceimpl();
    GradeService gradeService=new GradeServiceimpl();
    public String checkTest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String paper=request.getParameter("paper");
        Enumeration<String> parameter=request.getParameterNames();
        List<String> answerList=service.queryAnswer(paper);
        List<String> userAnswerList=new ArrayList<>();
        int count=0;
        while (parameter.hasMoreElements()){
               String number=parameter.nextElement();
               try{
                   int index=Integer.parseInt(number);
                   String userAnswer=request.getParameter(number);
                   if(userAnswer.equals(answerList.get(index))) count++;
                   userAnswerList.add(userAnswer);
               }catch (NumberFormatException e){
                   continue;
               }
        }
        //分数
        int fraction= count*(100/answerList.size());
         User user =(User)request.getSession().getAttribute("User");
         grade  g=new grade();
         g.setUser_id(user.getUser_id());
         g.setPaper(paper);
         g.setScore(fraction);
         gradeService.insertGrade(g);
        request.setAttribute("fraction",fraction);
        request.setAttribute("userAnswerList",userAnswerList);
        request.setAttribute("answerList",answerList);
       return "WEB-INF/Exam/result.jsp";
    }
    public String getsubjectList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        List<String>  list=  service.queryBySubject();
        request.setAttribute("subjectList",list);
     return "WEB-INF/Exam/SubjectList.jsp";
    }
    public String getParperList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String paper=request.getParameter("paper");
         List<single> list=  service.queryBypaper(paper);
        request.setAttribute("paperList",list);
        request.setAttribute("paper",paper);
       return "WEB-INF/Exam/exam1.jsp";
}
}
