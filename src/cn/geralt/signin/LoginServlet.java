package cn.geralt.signin;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name="LoginServlet",urlPatterns={"/LoginServlet"})
public class LoginServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//获取请求表单中的数据  uid 和  password
		response.setContentType("text/html;charset=UTF-8");
		String uid = request.getParameter("uid");
		String password = request.getParameter("password");
		DBOperator dbo = new DBOperator();
		User user = dbo.findUser(uid, password);
		
		if(user!=null) {
			
			String login="yes";
			request.getSession().setAttribute("last_temper", null);
			request.getSession().setAttribute("last_time", null);
			String recordString  = dbo.findRecord(uid);
			if(recordString!=null)
			{
				String[] record = recordString.split("#");
				request.getSession().setAttribute("last_temper", record[0]);
				request.getSession().setAttribute("last_time", record[1]);
			}
			request.getSession().setAttribute("isLogin", login);
			request.getSession().setAttribute("uid", uid);			
			
			response.setHeader("refresh", "0,url=/signin/index.jsp");
		}else {
			response.getWriter().write("用户名或密码错误，将在3秒内跳转到登录界面");
			response.setHeader("refresh", "3;URL=/signin/login.jsp");
		}	
	}
}
