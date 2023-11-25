package jobPolicy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class DBSample2 {

	Connection conn = null;

	//생성자
	DBSample2() {}
	
	//소멸자
	protected void finalize() {
		closeDBConnection();
	}
	
	//DB 연결이 유효한지 확인. 유효하지 않으면 새로운 연결 수립
	void validateDBConnection() {
		try {
			if (conn == null) {
				conn = DBConnManager.getConnection();
				System.err.println("DB가 연결되었습니다.");
			}
			else if (!conn.isValid(15)) { //15초 이내에 정상적인 응답이 없으면,
				conn.close();
				conn = DBConnManager.getConnection();
				System.err.println("DB가 재연결되었습니다.");
			}
		}
		catch (SQLException e) {
			System.err.println("DB에 접근할 수 없습니다.");
			e.printStackTrace();
		}
	}
	
	//DB 연결 해제
	void closeDBConnection() {
		try {
			DBConnManager.closeConnection(conn);
			System.err.println("DB 연결이 해제되었습니다.");
		}
		catch (SQLException e) {
			System.err.println("DB 연결 해제 중 에러!");
			e.printStackTrace();
		}
	}
	
	public void SelectTest() {
		PreparedStatement stmt = null;
		ResultSet rs = null;

		// DB 연결 확인
		validateDBConnection();
		
		try {
			// SQL 실행
			//stmt = conn.prepareStatement("select ID, NAME, PHONE from STUDENT where ID = 20116745;");
			stmt = conn.prepareStatement("select ID, NAME, PHONE from STUDENT;");
			rs = stmt.executeQuery();
			
			// Result Set 처리
			System.out.println("ID\t\t이 름\t전화번호");
			System.out.println("-------------------------------------");
			while (rs.next()) {
				System.out.println(rs.getInt(1) + "\t" + rs.getString(2) + "\t" + rs.getString("PHONE"));
			}
		}
		catch (SQLException e) {
			System.err.println("DB에 접근할 수 없거나 SQL을 실행할 수 없습니다.");
			e.printStackTrace();
		}
		// 리소스 반환
		finally {
			if (rs != null)		try { rs.close(); }		catch (Exception e) {}
			if (stmt != null)	try { stmt.close(); }	catch (Exception e) {}
		}		
	}

	
	public void InputSelectTest() {
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			// 1. 사용자로부터 검색할 ID를 입력받음
			BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
			System.out.print("검색할 ID를 입력하세요. ID: ");
			String str = reader.readLine();
			
			// 2. DB 연결 확인
			validateDBConnection();
			
			// 3. SQL 실행
			stmt = conn.prepareStatement("select NAME, PHONE from STUDENT where ID = ?;");
			stmt.setInt(1, Integer.parseInt(str));
			rs = stmt.executeQuery();
			
			// 4. Result Set 처리
			System.out.println("이 름\t전화번호");
			System.out.println("----------------------");
			while (rs.next()) {
				System.out.println(rs.getString(1) + "\t" + rs.getString(2));
			}
		}
		catch (IOException e) {
			System.out.println("입력 에러!");
		}
		catch (SQLException e) {
			System.out.println("DB에 접근할 수 없거나 SQL을 실행할 수 없습니다.");
			e.printStackTrace();
		}
		// 5. 리소스 반환
		finally {
			if (rs != null)		try { rs.close(); }		catch (Exception e) {}
			if (stmt != null)	try { stmt.close(); }	catch (Exception e) {}
		}		
	}

	
	public static void main(String[] args) {
		DBSample2 sample = new DBSample2();
		
		sample.SelectTest();
		sample.InputSelectTest();
		
		sample.closeDBConnection();
	}
}
