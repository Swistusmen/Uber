package CommonDataTypes;

import java.io.Serializable;

public class DriverData extends PersonalData implements Serializable {
    private String carLicenseID;
    private String licenseID;

    public DriverData(String name, String surname, String accountNumber, String telephoneNumber, boolean isClent, float rating, String carLicenseID, String licenseID ) throws Exception{
        super(name,surname,accountNumber,telephoneNumber,isClent,rating);
        this.carLicenseID=carLicenseID;
        this.licenseID=licenseID;
    }

    public String getCarLicenseID() {
        return carLicenseID;
    }

    public void setCarLicenseID(String carLicenseID) {
        this.carLicenseID = carLicenseID;
    }

    public String getLicenseID() {
        return licenseID;
    }

    public void setLicenseID(String licenseID) {
        this.licenseID = licenseID;
    }
}
