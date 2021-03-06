package upc.edu.eetac.dsa.dsaq31314g3.futbol.auth;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

public class ServletLogin extends HttpServlet {
        DataSource ds = null;
        private static final long serialVersionUID = 1L;

        public ServletLogin() {
                super();
                // TODO Auto-generated constructor stub
        }

        protected void doPost(HttpServletRequest request,
                        HttpServletResponse response) throws ServletException, IOException {

                String username = request.getParameter("username");
                String password = convertToMd5(request.getParameter("password"));

                Connection conn = null;
                Statement stmt = null;
                try {
                        conn = ds.getConnection();
                        stmt = conn.createStatement();
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                String sql = "select * from usuarios where username='" + username + "'";
                String exit = "";
                try {
                        ResultSet rs = stmt.executeQuery(sql);
                       
                        
                        	while (rs.next()) {
                                String login = rs.getString("password");
                                String rol =rs.getString("role");
                                System.out.println(rol);
                                
                             

                                if  (password.equals(login) && rol.equals("administrator")) {
                                        System.out.println("Login de admin ok!");
                                        exit = "successadmin";
                                } else if (password.equals(login) && rol.equals("registered"))
                                {
                                	System.out.println("Login de usuario ok!");
                                    exit = "successusuario";
                                	
                                }
                                
                                else if (!password.equals(login))
                                {
                                	System.out.println("usuario existe pero pass mal!");
                                    exit = "wrongpass";
                                }
                                
                                else {
                                        System.out.println("Login fail");
                                        exit = "fail";
                                }
                        }
                        
                } catch (SQLException e) {
                        e.printStackTrace();
                }
                // Devolver succes o fail
                response.setContentType("text/html");
                PrintWriter out = response.getWriter();
                out.write(exit);
                // TODO Auto-generated method stub
        }

        private static String convertToMd5(final String md5)
                        throws UnsupportedEncodingException {
                StringBuffer sb = null;
                try {
                        final java.security.MessageDigest md = java.security.MessageDigest
                                        .getInstance("MD5");
                        final byte[] array = md.digest(md5.getBytes("UTF-8"));
                        sb = new StringBuffer();
                        for (int i = 0; i < array.length; ++i) {
                                sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100)
                                                .substring(1, 3));
                        }
                        return sb.toString();
                } catch (final java.security.NoSuchAlgorithmException e) {
                }
                return sb.toString();
        }

        @Override
        public void init() throws ServletException {
                super.init();
                ds = DataSourceSPA.getInstance().getDataSource();
        }

}
