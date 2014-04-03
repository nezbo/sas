[1mdiff --git a/Netbeans Project/src/java/Controller/Controller.java b/Netbeans Project/src/java/Controller/Controller.java[m
[1mindex 51ee570..f37f110 100644[m
[1m--- a/Netbeans Project/src/java/Controller/Controller.java[m	
[1m+++ b/Netbeans Project/src/java/Controller/Controller.java[m	
[36m@@ -40,6 +40,6 @@[m [mpublic interface Controller {[m
     [m
     public boolean updateUserInfo(String userName, String name, String address, String hobbies, String friends);[m
     [m
[31m-    public boolean updateUser(String oldUsername, String password, String name, String address, String hobbies, String friends);[m
[32m+[m[32m    public boolean updatePassword(String userName, String password);[m
             [m
 }[m
[1mdiff --git a/Netbeans Project/src/java/Controller/FacadeController.java b/Netbeans Project/src/java/Controller/FacadeController.java[m
[1mindex 6d01304..7504676 100644[m
[1m--- a/Netbeans Project/src/java/Controller/FacadeController.java[m	
[1m+++ b/Netbeans Project/src/java/Controller/FacadeController.java[m	
[36m@@ -77,13 +77,13 @@[m [mpublic class FacadeController implements Controller {[m
             return new String(hash);[m
     }[m
     @Override[m
[31m-    public boolean updateUser(String oldUsername, String password, String name, String address, String hobbies, String friends) {[m
[31m-        return DBConnection.updateUser(oldUsername, password, name, address, hobbies, friends);[m
[32m+[m[32m    public boolean updateUserInfo(String oldUsername, String name, String address, String hobbies, String friends) {[m
[32m+[m[32m        return DBConnection.updateUserInfo(oldUsername, name, address, hobbies, friends);[m
     }[m
 [m
     @Override[m
[31m-    public boolean updateUserInfo(String userName, String name, String address, String hobbies, String friends) {[m
[31m-        return DBConnection.updateUserInfo(userName, name, address, hobbies, friends);[m
[32m+[m[32m    public boolean updatePassword(String userName, String password) {[m
[32m+[m[32m        return DBConnection.updatePassword(userName, password);[m
     }[m
     [m
 }[m
[1mdiff --git a/Netbeans Project/src/java/Controller/SecurityController.java b/Netbeans Project/src/java/Controller/SecurityController.java[m
[1mindex d5f34c1..0c24e37 100644[m
[1m--- a/Netbeans Project/src/java/Controller/SecurityController.java[m	
[1m+++ b/Netbeans Project/src/java/Controller/SecurityController.java[m	
[36m@@ -66,17 +66,13 @@[m [mpublic class SecurityController implements Controller {[m
     }[m
 [m
     @Override[m
[31m-    public boolean updateUser(String username, String password, String name, String address, String hobbies, String friends) {[m
[32m+[m[32m    public boolean updatePassword(String username, String password) {[m
         // Check length of values[m
[31m-        if(username.length() > 31 [m
[31m-                || password.length() > 255 [m
[31m-                || name.length() > 255 [m
[31m-                || address.length() > 255 [m
[31m-                || friends.length() > 255)[m
[32m+[m[32m        if(username.length() > 31 || password.length() > 255)[m
             return false;[m
         [m
         // TODO SECURITY[m
[31m-        return FacadeController.getInstance().updateUser(username, password, name, address, hobbies, friends);[m
[32m+[m[32m        return FacadeController.getInstance().updatePassword(username, password);[m
     }[m
 [m
     @Override[m
[1mdiff --git a/Netbeans Project/src/java/Database/DBConnection.java b/Netbeans Project/src/java/Database/DBConnection.java[m
[1mindex 50cfcdd..ccb737d 100644[m
[1m--- a/Netbeans Project/src/java/Database/DBConnection.java[m	
[1m+++ b/Netbeans Project/src/java/Database/DBConnection.java[m	
[36m@@ -65,7 +65,7 @@[m [mpublic class DBConnection {[m
         System.out.println(validUserLogin("usder", "password"));[m
         System.out.println(validUserLogin("user", "pasdsword"));[m
         System.out.println(validUserLogin("user", "password"));[m
[31m-        System.out.println(updateUser("bingo", "password", "Sparta", "Spartacus", "Shouting 'THIS IS SPARTA!'", "whatever dude"));[m
[32m+[m[32m        System.out.println(updatePassword("bingo", "password2"));[m
         System.out.println(getUser("user"));[m
     }[m
 [m
[36m@@ -97,14 +97,11 @@[m [mpublic class DBConnection {[m
         }[m
     }[m
 [m
[31m-    public static boolean updateUser(String oldUsername, String cleartextPassword, String name, String address, String hobbies, String friends) {[m
[32m+[m[32m    public static boolean updatePassword(String username, String cleartextPassword) {[m
         try {[m
[31m-            PreparedStatement stmt = getUpdateUserStatement();[m
[32m+[m[32m            PreparedStatement stmt = getUpdatePasswordStatement();[m
             stmt.setString(1, cleartextPassword);[m
[31m-            stmt.setString(2, name);[m
[31m-            stmt.setString(3, address);[m
[31m-            stmt.setString(4, hobbies);[m
[31m-            stmt.setString(5, oldUsername);[m
[32m+[m[32m            stmt.setString(2, username);[m
 [m
             return stmt.executeUpdate() == 1;[m
         } catch (SQLException ex) {[m
[36m@@ -182,11 +179,11 @@[m [mpublic class DBConnection {[m
         return pCreateUserStmt;[m
     }[m
 [m
[31m-    private static PreparedStatement getUpdateUserStatement() {[m
[32m+[m[32m    private static PreparedStatement getUpdatePasswordStatement() {[m
         if (pUpdateUserStmt == null) {[m
             try {[m
                 Connection conn = getUserConnection();[m
[31m-                pUpdateUserStmt = conn.prepareStatement("UPDATE `sassy`.`User` SET `password` = MD5(?),`name` = ?,`address` = ?,`hobbies` = ? WHERE `username` = ?;");[m
[32m+[m[32m                pUpdateUserStmt = conn.prepareStatement("UPDATE `sassy`.`User` SET `password` = MD5(?) WHERE `username` = ?;");[m
             } catch (SQLException ex) {[m
                 //TODO: Error handling [m
             }[m
[1mdiff --git a/Netbeans Project/web/userInfo.xhtml b/Netbeans Project/web/userInfo.xhtml[m
[1mindex 3be9712..d82b852 100644[m
[1m--- a/Netbeans Project/web/userInfo.xhtml[m	
[1m+++ b/Netbeans Project/web/userInfo.xhtml[m	
[36m@@ -11,7 +11,7 @@[m
     </h:head>[m
     <h:body>[m
         <h:form>[m
[31m-        This is another userss page<br/>[m
[32m+[m[32m        This is another user's page<br/>[m
         Name:[m
         <h:outputText value="#{ViewUserBean.name}"/><br/>[m
         Address:[m
