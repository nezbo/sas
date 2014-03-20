<%@ page import="com.mysql.jdbc.MySQLConnection" %>
<%@ page import="javax.naming.Context" %>
<%@ page import="javax.naming.InitialContext" %>
<%@ page import="javax.sql.DataSource" %>
<%@ page import="java.sql.*" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.TreeMap" %>
<%@ page import="java.io.StringWriter" %>
<%@ page import="java.io.PrintWriter" %>
<%!
    String formatValue(String name, Object rawValue) {
        String value;
        try {
            if (name.indexOf("classpath") >= 0 ||
                    name.indexOf("java.library.path") >= 0 ||
                    name.indexOf("ws.ext.dirs") >= 0 ||
                    name.indexOf("java.class.path") >= 0 ||
                    name.indexOf("sun.boot.class.path") >= 0) {
                value = "<pre>";

                String classpath = rawValue == null ? "" : rawValue.toString();
                String[] arrClasspath = classpath.split(System.getProperty("path.separator"));
                for (int i = 0; i < arrClasspath.length; i++) {
                    value += arrClasspath[i] + System.getProperty("path.separator") + "\n";
                }
                value += "</pre>";
            } else if (
                    (name.toLowerCase().indexOf("password") >= 0) ||
                            (name.toLowerCase().indexOf("secret") >= 0)) {
                value = "***";
            } else {
                value = rawValue == null ? "" : rawValue.toString();
            }
        } catch (Exception e) {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            pw.flush();
            value = "Exception formatting <code>" + rawValue.getClass() + "</code><br/><pre>" + sw + "</pre>";
        }
        return value;
    }
%>
<html>
<body>
<h1>DataSource demo</h1>

<h2>DataSource declaration</h2>

<p/>

<h2>Sample of code</h2>
<table border="1">
    <tr>
        <td>
<pre>
Context ctx = new InitialContext();
DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");
Connection conn = ds.getConnection();
ResultSet rst = stmt.executeQuery("select 1");
while (rst.next()) {
    out.print("resultset result: " + rst.getString(1));
}
rst.close();
stmt.close();
conn.close();
<code>
</code>
</pre>
        </td>
    </tr>
</table>
<p>
        <%
        Context ctx = new InitialContext();
        DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/mydb");
        Connection conn = ds.getConnection();
        DatabaseMetaData cnnMetaData = conn.getMetaData();
        %>

<h1>Connection Meta Data</h1>
<table border="1">
    <tr>
        <td>databaseProductName</td>
        <td><%= cnnMetaData.getDatabaseProductName() %>
        </td>
    </tr>
    <tr>
        <td>databaseProductVersion</td>
        <td><%= cnnMetaData.getDatabaseProductVersion() %>
        </td>
    </tr>
    <tr>
        <td>JdbcDriverName</td>
        <td><%= cnnMetaData.getDriverName() %>
        </td>
    </tr>
    <tr>
        <td>JdbcDriverVersion</td>
        <td><%= cnnMetaData.getDriverVersion() %>
        </td>
    </tr>
    <tr>
        <td>JdbcUrl</td>
        <td><%= cnnMetaData.getURL() %>
        </td>
    </tr>
    <tr>
        <td>DefaultTransactionIsolation</td>
        <td><%= cnnMetaData.getDefaultTransactionIsolation() %>
        </td>
    </tr>
    <tr>
        <td>MaxConnections</td>
        <td><%= cnnMetaData.getMaxConnections() %>
        </td>
    </tr>
    <tr>
        <td>ResultSetHoldability</td>
        <td><%= cnnMetaData.getResultSetHoldability() %>
        </td>
    </tr>
</table>
<h1>Connection Attributes</h1>
<table border="1">
    <tr>
        <td>Catalog</td>
        <td><%= conn.getCatalog() %>
        </td>
    </tr>
    <tr>
        <td>AutoCommit</td>
        <td><%= conn.getAutoCommit() %>
        </td>
    </tr>
    <tr>
        <td>Holdability</td>
        <td><%= conn.getHoldability() %>
        </td>
    </tr>
    <tr>
        <td>TransactionIsolation</td>
        <td><%= conn.getTransactionIsolation() %>
        </td>
    </tr>
</table>
<h1>Client Info Properties</h1>
<%
    ResultSet clientInfoProperties = cnnMetaData.getClientInfoProperties();
    ResultSetMetaData clientInfoPropertiesMetaData = clientInfoProperties.getMetaData();
    int columnCount = clientInfoPropertiesMetaData.getColumnCount();
    out.println("<table border=\"1\">");
    out.print("<tr>");
    for (int i = 1; i <= columnCount; i++) {
        out.print("<th>" + clientInfoPropertiesMetaData.getColumnName(i) + "</th>");
    }
    out.print("</tr>");
    while (clientInfoProperties.next()) {
        out.print("<tr>");
        for (int i = 1; i <= columnCount; i++) {
            out.print("<td>" + clientInfoProperties.getObject(i) + "</td>");
        }
        out.print("</tr>");
    }
    out.println("</table>");

%>
<h1>Client Info</h1>
<%
    out.println("<table border=\"1\">");
    out.print("<tr><th>Name</th><th>Value</th></tr>");
    for (Map.Entry<Object, Object> entry : conn.getClientInfo().entrySet()) {
        out.println("<tr><td>" + entry.getKey() + "</td><td>" + formatValue(entry.getKey().toString(), entry.getValue()) + "</td></tr>");

    }
    out.println("</table>");

%>
<h1>MySQL Connection Properties</h1>
<%
    MySQLConnection mysqlConn = conn.unwrap(MySQLConnection.class);
    out.println("<table border=\"1\">");
    out.print("<tr><th>Name</th><th>Value</th></tr>");
    for (Map.Entry entry : new TreeMap<Object,Object>(mysqlConn.getProperties()).entrySet()) {
        out.println("<tr><td>" + entry.getKey() + "</td><td>" + formatValue(entry.getKey().toString(), entry.getValue()) + "</td></tr>");

    }
    out.println("</table>");

%>

<h1>"Select 1" test query</h1>
<%
    Statement stmt = conn.createStatement();
    ResultSet rst = stmt.executeQuery("select 1");
    while (rst.next()) {
        out.print("resultset result: " + rst.getString(1));
    }
    rst.close();
    stmt.close();
    conn.close();
%>
</p>
<strong>Success! Your DataSource works!</strong>
</body>
</html>
