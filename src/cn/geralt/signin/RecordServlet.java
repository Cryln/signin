package cn.geralt.signin;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(asyncSupported = true, urlPatterns = { "/RecordServlet" })
public class RecordServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public RecordServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		float temper = Float.parseFloat(request.getParameter("temper"));
		String uid = request.getSession().getAttribute("uid").toString();
		DBOperator dbo = new DBOperator();
		String last_time=request.getSession().getAttribute("last_time").toString();
		System.out.println(last_time);
		try {
			if(dbo.isInsertable(last_time)) {
				boolean result = dbo.insertRecord(uid, temper);
				if(result) {
					String recordString  = dbo.findRecord(uid);
					if(recordString!=null)
					{
						String[] record = recordString.split("#");
						request.getSession().setAttribute("last_temper", record[0]);
						request.getSession().setAttribute("last_time", record[1]);
					}
					response.getWriter().write("上报成功，将在3秒内跳转到主界面");
					response.setHeader("refresh", "3;URL=/signin/index.jsp");
				}
				else {
					response.getWriter().write("插入数据库失败，将在3秒内跳转到主界面");
					response.setHeader("refresh", "3;URL=/signin/index.jsp");
				}
			}
			else {
				response.getWriter().write("上报失败，今天你已经上报过了");
				response.setHeader("refresh", "3;URL=/signin/index.jsp");
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
