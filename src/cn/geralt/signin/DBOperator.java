package cn.geralt.signin;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.el.parser.BooleanNode;

public class DBOperator {
	private String url;
	private String username;
	private String password;
	private Connection conn;
	private Statement stat;
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public DBOperator() {
		setUrl("jdbc:mysql://"+Sql_config.mysql_addr+":"+Sql_config.port+"/"+Sql_config.database+
				"?useUnicode=true&characterEncoding=UTF-8&serverTimezone=UTC");
		setUsername(Sql_config.username);
		setPassword(Sql_config.password);
		conn = null;
		stat = null;
	}
	public DBOperator(String url,String username,String password) {
		setUrl(url);
		setUsername(username);
		setPassword(password);
		conn = null;
		stat = null;
	}
	public void setStatement() throws SQLException,ClassNotFoundException{
		Class.forName("com.mysql.jdbc.Driver");
		conn = DriverManager.getConnection(url,username,password);
		stat = conn.createStatement();
	}
	public void release() {
		//System.out.println("release...");
		if(stat!=null) {
			try {
			//	System.out.println("stat closing");
				stat.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			stat=null;
		}
		if(conn!=null) {
			try {
			//	System.out.println("conn closing");
				conn.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			conn=null;
		}
	}
	public void release(ResultSet rs) {
		if(rs!=null) {
			try {
		//		System.out.println("release(rs)...");
				rs.close();
			}catch(SQLException e) {
				e.printStackTrace();
			}
			rs=null;
		}
		release();
	}

	public boolean insertUser(User user) {
		ResultSet rs=null;
		try {
			//获得Statement
			setStatement();
			String sql = "insert into users(uid,pwd) values("
					+ "'"+user.getUid()+"','"+user.getPassword()+"')";
			int num = stat.executeUpdate(sql);
			if(num>0) {
				return true;
			}
			return false;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			release(rs);
		}
		return false;
	}
	
	public boolean insertRecord(String uid,float temperature) {
        Date day = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(df.format(day));
		ResultSet rs=null;
		try {
			//获得Statement
			setStatement();
			String sql = "insert into records(uid,temper,dt) values("
					+ "'"+uid+"','"+temperature+"','"+df.format(day)+"');";
			//System.out.println(sql);
			int num = stat.executeUpdate(sql);
			if(num>0) {
				return true;
			}
			return false;
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			release(rs);
		}
		return false;
	}
	
	public boolean isInsertable(String past) throws ParseException {
		if(past==null)return true;
        Date now = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("now:"+df.format(now)+"past:"+past);
        Date d_past = df.parse(past);
        if((now.getYear()*360+now.getMonth()*30+now.getDate())-(d_past.getYear()*360+d_past.getMonth()*30+d_past.getDate())>0) {
        	return true;
        }
        else return false;
	}
	
	public User findUser(String uid,String password) {
		ResultSet rs=null;
		try {
			System.out.println("finduser try block");
			//获得Statement
			setStatement();
			//sql
			String sql="select * from users where uid='"+uid+"' and pwd='"+password+"';";
			rs = stat.executeQuery(sql);
			//System.out.println(sql);
			if(rs.next()) {
				System.out.println("re processing");
				User user = new User();
				user.setUid(rs.getString("uid"));
				user.setPassword(rs.getString("pwd"));
				return user;
			}
			return null;
		}catch(Exception e) {
		//	System.out.println("finduser catch block");
			e.printStackTrace();
		}finally {
		//	System.out.println("finduser finally block");
			release(rs);
		}
		return null;
	}
	public String findRecord(String uid) {
		ResultSet rs=null;
		String record;
		try {
		//	System.out.println("finduser try block");
			//获得Statement
			setStatement();
			//sql
			String sql="select * from records where ('"+uid+
					"',dt) in (select uid, max(dt) from records group by uid);";
			rs = stat.executeQuery(sql);
		//	System.out.println(sql);
			if(rs.next()) {
		//		System.out.println("rs processing");
				record = rs.getString("temper")+"#"+rs.getString("dt");
				return record;
			}
			return null;
		}catch(Exception e) {
		//	System.out.println("finduser catch block");
			e.printStackTrace();
		}finally {
		//	System.out.println("finduser finally block");
			release(rs);
		}
		return null;
	}
}
