package cn.geralt.signin;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name="RegisterServlet",urlPatterns={"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RegisterServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		//获取 uid 和  password
		response.setContentType("text/html;charset=UTF-8");
		String uid = request.getParameter("uid");
		String password = request.getParameter("password");		
		
		User user = new User();
		user.setUid(uid);;
		user.setPassword(password);
		//向数据库插入用户
		DBOperator dbo = new DBOperator();
		boolean result = dbo.insertUser(user);		
		if(result==true) {
			//注册成功
			response.getWriter().write("注册成功，将在3秒内跳转到登录界面");
			response.setHeader("refresh", "3;URL=/signin/login.jsp");
		}else {
			//注册失败
			response.getWriter().write("注册失败，该用户名已被注册！<a href='/signin/register.jsp'>重新注册</a>");
		}
		
	}

}
