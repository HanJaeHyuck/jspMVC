package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import vo.BoardVO;

public class BoardDAO {
	//Functions that make BoardDAO available to the controller
	public static BoardDAO instance = new BoardDAO();

	// DB Connection
	private Connection getConnection() {
		Connection conn = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver"); //DB connection method
			String cString = "jdbc:mysql://gondr.asuscomm.com/yy_20122?useUnicode=true&characterEncoding=utf8&useSSL=false&serverTimezone=Asia/Seoul"; // DB address
			String id = "yy_20122"; // DB ID
			String password = "han1232"; // DB passwrod
			conn = DriverManager.getConnection(cString, id, password);
		} catch (ClassNotFoundException e) {
			System.out.println("Driver Not Found");
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("DB Connection Failed");
		}
		return conn;
	}

	// write column
	public int write(BoardVO data) {
		//sql statement
		String sql = "INSERT INTO boards (title, content, writer, files) VALUES(?, ?, ?, ?)";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);

			//Storing Values ​​in VO
			pstmt.setString(1, data.getTitle());
			pstmt.setString(2, data.getContent());
			pstmt.setString(3, data.getWriter());
			pstmt.setString(4, data.getFiles());

			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
			try { if (conn != null)	conn.close(); } catch (SQLException e) {}
		}
	}

	// view column
	public BoardVO view(int id) {
		//sql statement
		String sql = "SELECT * FROM boards WHERE id = ?";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			rs = pstmt.executeQuery();

			if (rs.next()) {
				BoardVO data = new BoardVO();
				//Storing Values ​​in VO
				data.setId(rs.getInt("id"));
				data.setTitle(rs.getString("title"));
				data.setContent(rs.getString("content"));
				data.setWriter(rs.getString("writer"));
				data.setFiles(rs.getString("files"));
				
				return data;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (conn != null)	conn.close(); } catch (SQLException e) {}
		}

		return null;
	}

	// board column
	public List<BoardVO> getList(int page) { //Put objects in list form
		//sql statement
		String sql = "SELECT * FROM boards ORDER BY id ASC LIMIT ?, 10";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		// Get multiple values ​​in a list
		List<BoardVO> list = new ArrayList<BoardVO>();

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, (page - 1) * 10);
			//Query submission
			rs = pstmt.executeQuery();

			while (rs.next()) {
				BoardVO temp = new BoardVO();
				//Storing Values ​​in VO
				temp.setId(rs.getInt("id"));
				temp.setTitle(rs.getString("title"));
				temp.setWriter(rs.getString("writer"));
				list.add(temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (conn != null)	conn.close(); } catch (SQLException e) {}
		}
		return list;

	}

	// delete column
	public int delete(int id) {
		//sql statement
		String sql = "DELETE FROM boards WHERE id = ?";
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, id);
			//Query submission
			int rs = pstmt.executeUpdate();

			return rs;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
			}
			try {
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
			}
		}

	}

	// selectboard modify column
	public BoardVO selectBoard(int id) {
		//sql statement
			String sql = "SELECT id, title, content, writer, files FROM boards WHERE id =? ";
			Connection conn = null;
			ResultSet rs = null;
			PreparedStatement pstmt = null;
			
			try {
				conn = getConnection();
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, id);
				//Query submission
				rs = pstmt.executeQuery();
				
				if(rs.next()) {
					BoardVO data = new BoardVO();
					//Storing Values ​​in VO
					data.setId(rs.getInt("id"));
					data.setTitle(rs.getString("title"));
					data.setContent(rs.getString("content"));
					data.setWriter(rs.getString("writer"));
					data.setFiles(rs.getString("files"));
					
					return data;
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
				try { if (rs != null) rs.close(); } catch (SQLException e) {}
				try { if (conn != null)	conn.close(); } catch (SQLException e) {}
			}
			return null;
		}

	public int modify(BoardVO data) {
		//sql statement
		String sql = "UPDATE boards SET title = ? ,content = ?, files = ? WHERE id = ?";
		Connection conn = null;
		ResultSet rs = null;
		PreparedStatement pstmt = null;
		
		try {
			conn = getConnection();
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, data.getTitle());
			pstmt.setString(2, data.getContent());
			pstmt.setString(3, data.getFiles());
			pstmt.setInt(4, data.getId());

			//Query submission
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}finally {
			try { if (pstmt != null) pstmt.close(); } catch (SQLException e) {}
			try { if (rs != null) rs.close(); } catch (SQLException e) {}
			try { if (conn != null)	conn.close(); } catch (SQLException e) {}
		}
		
	}
}
