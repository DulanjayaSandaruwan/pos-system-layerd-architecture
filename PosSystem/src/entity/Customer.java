package entity;

/**
 * @author : D.D.Sandaruwan <dulanjayasandaruwan1998@gmail.com>
 * @Since : 2021-10-28
 **/
public class Customer {
    private String CustId;
    private String CustTitle;
    private String Custname;
    private String CustAddress;
    private String City;
    private String Province;
    private String PostalCode;

    public Customer() {
    }

    public Customer(String custId, String custTitle, String custname, String custAddress, String city, String province, String postalCode) {
        CustId = custId;
        CustTitle = custTitle;
        Custname = custname;
        CustAddress = custAddress;
        City = city;
        Province = province;
        PostalCode = postalCode;
    }

    public String getCustId() {
        return CustId;
    }

    public void setCustId(String custId) {
        CustId = custId;
    }

    public String getCustTitle() {
        return CustTitle;
    }

    public void setCustTitle(String custTitle) {
        CustTitle = custTitle;
    }

    public String getCustname() {
        return Custname;
    }

    public void setCustname(String custname) {
        Custname = custname;
    }

    public String getCustAddress() {
        return CustAddress;
    }

    public void setCustAddress(String custAddress) {
        CustAddress = custAddress;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }

    public String getPostalCode() {
        return PostalCode;
    }

    public void setPostalCode(String postalCode) {
        PostalCode = postalCode;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "CustId='" + CustId + '\'' +
                ", CustTitle='" + CustTitle + '\'' +
                ", Custname='" + Custname + '\'' +
                ", CustAddress='" + CustAddress + '\'' +
                ", City='" + City + '\'' +
                ", Province='" + Province + '\'' +
                ", PostalCode='" + PostalCode + '\'' +
                '}';
    }
}
