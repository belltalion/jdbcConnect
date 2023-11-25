package jobPolicy;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import user.UserDAO;
import user.User;

public class Main {
    public static void main(String[] args) throws SQLException {
        //테스트용 데이터
        String userid = "user11";
        String password = "1234";
        int gender = 1;
        String birth = "2005-11-13";

        UserDAO dao = new UserDAO();
        //아이디 중복 체크 테스트
        int idCheck = dao.userIdCheck(userid);
        System.out.println("아이디 존재 1 / 존재하지 않는 아이디 0 / 오류 -2");
        System.out.println("idCheck = "+idCheck);

        userid = "user100";
        User user = new User();

        //회원가입 테스트
        user.setUserid(userid);
        user.setPassword(password);
        user.setGender(gender);
        user.setBirth(birth);
        dao.userRegister(user);
        System.out.println(user.toString());

        //로그인 테스트
        int login = dao.login(userid, password);
        System.out.println("로그인 성공 1 / 존재하지 않는 아이디 0 / 비밀번호 불일치 -1 / 오류 -2");
        System.out.println("login = "+login);

        //회원 정보 조회 테스트
        user = dao.userInfo(userid);
        System.out.println(user.toString());

        //회원탈퇴 테스트
        dao.userDelete(userid);
    }
}