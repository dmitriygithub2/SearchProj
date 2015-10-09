package org.testproject.search.listener;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Statement;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.testproject.search.db.DBUtil;

/**
 * Application Lifecycle Listener implementation class ProjectListener
 *
 */
@WebListener
public class ProjectListener implements ServletContextListener {

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		String s = new String();
		StringBuilder sb = new StringBuilder();

		try {
			InputStreamReader reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream("init-sql.sql"));
			BufferedReader br = new BufferedReader(reader);
			while ((s = br.readLine()) != null) {
				sb.append(s);
			}
			br.close();
			String[] inst = sb.toString().split(";");
			Connection c = DBUtil.getConnection();
			Statement st = c.createStatement();
			for (int i = 0; i < inst.length; i++) {
				if (!inst[i].trim().equals("")) {
					st.executeUpdate(inst[i]);
					System.out.println(">>" + inst[i]);
				}
			}

		} catch (Exception e) {
			System.out.println("*** Error : " + e.toString());
			System.out.println("*** ");
			System.out.println("*** Error : ");
			e.printStackTrace();
			System.out.println("################################################");
			System.out.println(sb.toString());
		}
	}

}
