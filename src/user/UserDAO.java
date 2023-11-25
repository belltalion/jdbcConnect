package user;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import jobPolicy.DBConnManager;

public class UserDAO {
	Connection CN = null;
	Statement ST = null; //ST = CN.createStatement()
	PreparedStatement PST = null; //PST=CN.prePareStatement("insert~")
	ResultSet RS = null; //RS = ST.executeQuery("select~");
	
	String msg = "";
	
	// 아이디 존재 여부 체크
	public int userIdCheck(String userid) {
		try {
			CN = DBConnManager.getConnection();
			
			msg = "select userid from user where userid = ?";
			PST = CN.prepareStatement(msg);
			PST.setString(1, userid);
			RS = PST.executeQuery();
			if(RS.next())
				return 1; // 아이디 존재 1 리턴
			return 0; //존재하지 않는 아이디 0 리턴 
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (RS != null)		try { RS.close(); }		catch (Exception e) {}
			if (PST != null)	try { PST.close(); }	catch (Exception e) {}
			if (CN != null)		try { CN.close(); }		catch (Exception e) {}
		}
		return -2; //오류
	}
	
	// 회원가입 저장
	public void userRegister(User user) {
		try {
			CN = DBConnManager.getConnection();
			//userid, password, gender, birth, createAt
			msg = "insert into user values(?,UNHEX(MD5(?)),?,?,default)";
			PST = CN.prepareStatement(msg);
			PST.setString(1, user.getUserid());
			PST.setString(2, user.getPassword());
			PST.setInt(3, user.getGender());
			PST.setString(4, user.getBirth());
			PST.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (PST != null)	try { PST.close(); }	catch (Exception e) {}
			if (CN != null)		try { CN.close(); }		catch (Exception e) {}
		}
	}
	
	// 로그인
	public int login(String userid, String password) {
		try {
			if (userIdCheck(userid) == -1) {
				return 0; //존재하지 않는 아이디 0 리턴
			}
			CN = DBConnManager.getConnection();

			msg = "select userid from user where userid = ? and password = UNHEX(MD5(?))";
			PST = CN.prepareStatement(msg);
			PST.setString(1, userid);
			PST.setString(2, password);
			RS = PST.executeQuery();
			
			//RS.next()을 실행했을 때 결과값이 존재한다면 true
			if(RS.next()) 
				return 1; //로그인 성공 정수 1 리턴
			return -1; //비밀번호 불일치 정수 -1 리턴
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (RS != null)		try { RS.close(); }		catch (Exception e) {}
			if (PST != null)	try { PST.close(); }	catch (Exception e) {}
			if (CN != null)		try { CN.close(); }		catch (Exception e) {}
		}
		return -2; //오류
	}
	
	// 회원 정보 조회
	public User userInfo(String userid) {
		User user = new User();
		try {
			CN = DBConnManager.getConnection();
			msg = "select * from user where userid = '"+userid+"'";
			ST = CN.createStatement();
			RS = ST.executeQuery(msg);
			//userid, password, gender, birth, createAt
			if(RS.next()==true) {
				user.setUserid(RS.getString("userid"));
				user.setPassword(RS.getString("password"));
				user.setGender(RS.getInt("gender"));
				user.setBirth(RS.getString("birth"));
				user.setCreateAt(RS.getString("createAt"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (RS != null)		try { RS.close(); }		catch (Exception e) {}
			if (ST != null)		try { ST.close(); }		catch (Exception e) {}
			if (CN != null)		try { CN.close(); }		catch (Exception e) {}
		}
		return user;
	}
	
	// 회원 탈퇴
	public void userDelete(String userid) {
		try {
			CN = DBConnManager.getConnection();
			msg = "delete from user where userid = '"+userid+"'";
			ST = CN.createStatement();
			ST.executeUpdate(msg);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (ST != null)		try { ST.close(); }		catch (Exception e) {}
			if (CN != null)		try { CN.close(); }		catch (Exception e) {}
		}
	}
}
