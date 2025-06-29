/**
 * GymMember class represents a base class for gym members
 * with common attributes and methods.
 */
public abstract class GymMember {
    // Common attributes for all members
    protected String id;
    protected String name;
    protected String location;
    protected String phone;
    protected String email;
    protected String gender;
    protected String dob;
    protected String membershipStartDate;
    protected int attendance;
    protected double loyaltyPoints;
    protected boolean activeStatus;

    // Additional attributes
    private String referralSource;
    private double paidAmount;
    private String removalReason;

    /**
     * Constructor for GymMember
     */
    public GymMember(String id, String name, String location, String phone,
                     String email, String gender, String dob,
                     String membershipStartDate, String referralSource,
                     double paidAmount) {
        this.id = id;
        this.name = name;
        this.location = location;
        this.phone = phone;
        this.email = email;
        this.gender = gender;
        this.dob = dob;
        this.membershipStartDate = membershipStartDate;
        this.referralSource = referralSource;
        this.paidAmount = paidAmount;
        this.attendance = 0;
        this.loyaltyPoints = 0;
        this.activeStatus = false; // New members are inactive by default
        this.removalReason = "";
    }

    // Getter and setter methods
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getMembershipStartDate() {
        return membershipStartDate;
    }

    public void setMembershipStartDate(String membershipStartDate) {
        this.membershipStartDate = membershipStartDate;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public boolean isActive() {
        return activeStatus;
    }

    public void setActive(boolean active) {
        this.activeStatus = active;
    }

    public int getAttendance() {
        return attendance;
    }

    public void setAttendance(int attendance) {
        this.attendance = attendance;
    }

    public double getLoyaltyPoints() {
        return loyaltyPoints;
    }

    public void setLoyaltyPoints(double loyaltyPoints) {
        this.loyaltyPoints = loyaltyPoints;
    }

    public String getRemovalReason() {
        return removalReason;
    }

    public void setRemovalReason(String removalReason) {
        this.removalReason = removalReason;
    }

    // Common methods for all members
    /**
     * Activates the membership
     */
    public void activateMembership() {
        this.activeStatus = true;
    }

    /**
     * Deactivates the membership
     */
    public void deactivateMembership() {
        if (this.activeStatus) {
            this.activeStatus = false;
        }
    }

    /**
     * Abstract method to mark attendance for the member
     */
    public abstract void markAttendance();

    /**
     * Resets the member to default state
     */
    public void resetMember() {
        this.activeStatus = false;
        this.attendance = 0;
        this.loyaltyPoints = 0;
    }

    /**
     * Abstract method to calculate fee
     * @return the calculated fee
     */
    public abstract double calculateFee();

    /**
     * Display method to show the member details
     */
    public void display() {
        System.out.println("ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Location: " + location);
        System.out.println("Phone: " + phone);
        System.out.println("Email: " + email);
        System.out.println("Gender: " + gender);
        System.out.println("Date of Birth: " + dob);
        System.out.println("Membership Start Date: " + membershipStartDate);
        System.out.println("Attendance: " + attendance);
        System.out.println("Loyalty Points: " + loyaltyPoints);
        System.out.println("Active Status: " + (activeStatus ? "Active" : "Inactive"));
    }

    @Override
    public String toString() {
        return "ID: " + id + "\n" +
                "Name: " + name + "\n" +
                "Location: " + location + "\n" +
                "Phone: " + phone + "\n" +
                "Email: " + email + "\n" +
                "Gender: " + gender + "\n" +
                "Date of Birth: " + dob + "\n" +
                "Membership Start Date: " + membershipStartDate + "\n" +
                "Referral Source: " + referralSource + "\n" +
                "Paid Amount: " + String.format("%.2f", paidAmount) + "\n" +
                "Active Status: " + (activeStatus ? "Active" : "Inactive") + "\n" +
                "Attendance Count: " + attendance + "\n" +
                "Loyalty Points: " + loyaltyPoints;
    }
}
