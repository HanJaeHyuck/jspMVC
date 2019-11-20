package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.plaf.synth.SynthSplitPaneUI;

import vo.BoardVO;
import vo.UserVO;

public class MemberDAO {
	// Functions that make MeberDAO available to the controller
	public static MemberDAO instance = new MemberDAO();
	
	
	// DB Connection
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			String cString = "jdbc:mysql://gondr.asuscomm.com/yy_20122?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul";
			String id = "yy_20122";
			String password = "han1232";
			conn = DriverManager.getConnection(cString, id, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB Connection Failed");
		}
		return conn;
	}
	



	//register column
	public boolean register(String name, String id, String password) {
		//sql statement
		String sql = "INSERT INTO users(id, name, password) VALUES(?, ?, ?)";
		boolean result = false;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			
			//Storing Values ​​in VO
			pstmt.setString(1, id);
			pstmt.setString(2, name);
			pstmt.setString(3, password);
			
			//Query submission
			int n = pstmt.executeUpdate();
			
			if (n > 0) result = true;

		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (conn != null)	conn.close(); } catch (SQLException e) {}
		}
		return result;
	}

	//login column
	public UserVO login(String id, String password) {
		//sql statement
		String sql = "SELECT * FROM users WHERE id = ? AND password = ?";
		boolean result = false;
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			//Storing Values ​​in VO
			pstmt.setString(1, id);
			pstmt.setString(2, password);
			//Query submission
			rs = pstmt.executeQuery();
			
			if(!rs.next()) {
				return null;
			} else {
				UserVO user = new UserVO();
				user.setId(rs.getString("id"));
				user.setName(rs.getString("name"));
				
				return user;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (conn != null) conn.close(); } catch (SQLException e) {}
		}
		return null;
	}


}
