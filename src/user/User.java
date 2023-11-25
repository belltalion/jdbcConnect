package user;

public class User {
	private String userid;
	private String password;
	private int gender;
	private String birth, createAt;

	public User() {}

	public User(String userid, String password, int gender, String birth, String createAt) {
		this.userid = userid;
		this.password = password;
		this.gender = gender;
		this.birth = birth;
		this.createAt = createAt;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getGender() {
		return gender;
	}

	public void setGender(int gender) {
		this.gender = gender;
	}

	public String getBirth() {
		return birth;
	}

	public void setBirth(String birth) {
		this.birth = birth;
	}

	public String getCreateAt() {
		return createAt;
	}

	public void setCreateAt(String createAt) {
		this.createAt = createAt;
	}

	@Override
	public String toString() {
		return "User [userid=" + userid + ", password=" + password + ", gender=" + gender + ", birth=" + birth
				+ ", createAt=" + createAt + "]";
	}
}
