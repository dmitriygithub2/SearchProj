package org.testproject.search.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.testproject.search.db.DBUtil;

/**
 * Servlet implementation class MainServlet
 */
@WebServlet("/")
public class MainServlet extends HttpServlet {

	public static final String PATTERN = "[\\]\\[\\{\\}\\(\\);:,\\. !?\\-\\\"<>«»„“”]+";
	private static final long serialVersionUID = 1L;

	private class Word {
		private Integer id;

		public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		private String value;
		private boolean inText1 = false;
		private boolean inText2 = false;
		private boolean inText3 = false;
		private boolean inText4 = false;
		private boolean inText5 = false;

		public String getValue() {
			return value;
		}
		public void setValue(String value) {
			this.value = value;
		}
		public boolean isInText1() {
			return inText1;
		}
		public void setInText1(boolean inText1) {
			this.inText1 = inText1;
		}
		public boolean isInText2() {
			return inText2;
		}
		public void setInText2(boolean inText2) {
			this.inText2 = inText2;
		}
		public boolean isInText3() {
			return inText3;
		}
		public void setInText3(boolean inText3) {
			this.inText3 = inText3;
		}
		public boolean isInText4() {
			return inText4;
		}
		public void setInText4(boolean inText4) {
			this.inText4 = inText4;
		}
		public boolean isInText5() {
			return inText5;
		}
		public void setInText5(boolean inText5) {
			this.inText5 = inText5;
		}

		public boolean isNormal() {
			int i = 0;
			if (isInText1()) ++i;
			if (isInText2()) ++i;
			if (isInText3()) ++i;
			if (isInText4()) ++i;
			if (isInText5()) ++i;
			return i >= 2 ? true : false;
		}

		public int getCount() {
			int i = 0;
			if (isInText1()) ++i;
			if (isInText2()) ++i;
			if (isInText3()) ++i;
			if (isInText4()) ++i;
			if (isInText5()) ++i;
			return i;
		}
		public String getWhere() {
			String result = "";
			if (isInText1()) result += "1;";
			if (isInText2()) result += "2;";
			if (isInText3()) result += "3;";
			if (isInText4()) result += "4;";
			if (isInText5()) result += "5;";
			return result;
		}
	}
	
	public MainServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String id = request.getParameter("id");
		if (id == null) {
			id = (String) request.getAttribute("id");
		}
		if (id != null) {
			List<Map<String, Object>> result = new ArrayList<>();
			Connection con = null;
			Statement st = null;
			try {
				con = DBUtil.getConnection();
				st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * from word where search_id = " + id);
				while (rs.next()) {
					Map<String, Object> row = new HashMap<String, Object>();
					row.put("word", rs.getString(3));
					row.put("count", rs.getInt(4));
					row.put("where", rs.getString(5));
					result.add(row);
			    }
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {}
				}
				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {}
				}
			}
			try {
				con = DBUtil.getConnection();
				st = con.createStatement();
				ResultSet rs = st.executeQuery("SELECT * from search_result where id = " + id);
				while (rs.next()) {
					request.setAttribute("text1", rs.getString(2));
					request.setAttribute("text2", rs.getString(3));
					request.setAttribute("text3", rs.getString(4));
					request.setAttribute("text4", rs.getString(5));
					request.setAttribute("text5", rs.getString(6));
			    }
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {}
				}
				if (st != null) {
					try {
						st.close();
					} catch (SQLException e) {}
				}
			}
			request.setAttribute("result", result);
			getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
			return;
		}
		request.setAttribute("text1",  request.getParameter("text1"));
		request.setAttribute("text2",  request.getParameter("text2"));
		request.setAttribute("text3",  request.getParameter("text3"));
		request.setAttribute("text4",  request.getParameter("text4"));
		request.setAttribute("text5",  request.getParameter("text5"));
		getServletContext().getRequestDispatcher("/main.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		String text1 = request.getParameter("text1");
		String text2 = request.getParameter("text2");
		String text3 = request.getParameter("text3");
		String text4 = request.getParameter("text4");
		String text5 = request.getParameter("text5");
		if (isValid(text1, text2, text3, text4, text5, request)) {
			List<Word> words = new ArrayList<Word>();
			Set<String> literals1 = new HashSet<String>(Arrays.asList(text1.toLowerCase().split(PATTERN)));
			Set<String> literals2 = new HashSet<String>(Arrays.asList(text2.toLowerCase().split(PATTERN)));
			Set<String> literals3 = new HashSet<String>(Arrays.asList(text3.toLowerCase().split(PATTERN)));
			Set<String> literals4 = new HashSet<String>(Arrays.asList(text4.toLowerCase().split(PATTERN)));
			Set<String> literals5 = new HashSet<String>(Arrays.asList(text5.toLowerCase().split(PATTERN)));
			Set<String> literals = new HashSet<String>();
			literals.addAll(literals1);
			literals.addAll(literals2);
			literals.addAll(literals3);
			literals.addAll(literals4);
			literals.addAll(literals5);
			int i = 0;
			for (String literal : literals) {
				if (literal.length() >= 3) {
					Word word = new Word();
					word.setValue(literal);
					if (literals1.contains(literal)) {
						word.setInText1(true);
					}
					if (literals2.contains(literal)) {
						word.setInText2(true);
					}
					if (literals3.contains(literal)) {
						word.setInText3(true);
					}
					if (literals4.contains(literal)) {
						word.setInText4(true);
					}
					if (literals5.contains(literal)) {
						word.setInText5(true);
					}
					if (word.isNormal()) {
						words.add(word);
						i++;
					}
					if (i >= 20) break;
				}
			}
			Connection con = null;
			PreparedStatement prst= null;
			Integer searchID = null;
			try {
				con = DBUtil.getConnection();
				prst = con.prepareStatement("INSERT INTO search_result (text1, text2, text3, text4, text5) VALUES (?, ?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
				prst.setString(1, text1);
				prst.setString(2, text2);
				prst.setString(3, text3);
				prst.setString(4, text4);
				prst.setString(5, text5);
				prst.executeUpdate();
				ResultSet set = prst.getGeneratedKeys();
				set.next();
				searchID = set.getInt(1);
			} catch (SQLException e) {
				e.printStackTrace();
			} finally {
				if (con != null) {
					try {
						con.close();
					} catch (SQLException e) {}
				}
				if (prst != null) {
					try {
						prst.close();
					} catch (SQLException e) {}
				}
			}
			for (Word word: words) {
				try {
					con = DBUtil.getConnection();
					prst = con.prepareStatement("INSERT INTO word (search_id, value, count, whereF) VALUES (?, ?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
					prst.setInt(1, searchID);
					prst.setString(2, word.getValue());
					prst.setInt(3, word.getCount());
					prst.setString(4, word.getWhere());
					prst.executeUpdate();
					ResultSet set = prst.getGeneratedKeys();
					set.next();
					word.setId(set.getInt(1));
				} catch (SQLException e) {
					e.printStackTrace();
				} finally {
					if (con != null) {
						try {
							con.close();
						} catch (SQLException e) {}
					}
					if (prst != null) {
						try {
							prst.close();
						} catch (SQLException e) {}
					}
				}
			}
			request.setAttribute("id", "" + searchID);
 		}
		doGet(request, response);
	}

	private boolean isValid(String text1, String text2, String text3, String text4, String text5, HttpServletRequest request) {
		if (text1 == null || text1.trim().length() > 1000 || text1.trim().length() <= 0) {
			request.setAttribute("error1", true);
			return false;
		}
		if (text2 == null || text2.trim().length() > 1000 || text2.trim().length() <= 0) {
			request.setAttribute("error2", true);
			return false;
		}
		if (text3 == null || text3.trim().length() > 1000 || text3.trim().length() <= 0) {
			request.setAttribute("error3", true);
			return false;
		}
		if (text4 == null || text4.trim().length() > 1000 || text4.trim().length() <= 0) {
			request.setAttribute("error4", true);
			return false;
		}
		if (text5 == null || text5.trim().length() > 1000 || text5.trim().length() <= 0) {
			request.setAttribute("error5", true);
			return false;
		}
		return true;
	}

}
