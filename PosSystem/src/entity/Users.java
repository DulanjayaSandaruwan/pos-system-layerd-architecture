package entity;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-23
 **/
public class Users {
    private String UserId;
    private String UserName;
    private String Nic;
    private String Address;
    private String Contact;
    private String Email;
    private String Password;
    private String UserRole;

    public Users() {
    }

    public Users(String userId, String userName, String nic, String address, String contact, String email, String password, String userRole) {
        UserId = userId;
        UserName = userName;
        Nic = nic;
        Address = address;
        Contact = contact;
        Email = email;
        Password = password;
        UserRole = userRole;
    }

    public Users(String userName, String password, String userRole) {
        UserName = userName;
        Password = password;
        UserRole = userRole;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getNic() {
        return Nic;
    }

    public void setNic(String nic) {
        Nic = nic;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getContact() {
        return Contact;
    }

    public void setContact(String contact) {
        Contact = contact;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getUserRole() {
        return UserRole;
    }

    public void setUserRole(String userRole) {
        UserRole = userRole;
    }
}
