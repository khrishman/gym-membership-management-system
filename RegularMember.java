/**
 * RegularMember class represents a regular gym member
 * with specific attributes and behaviors
 */
public class RegularMember extends GymMember {
    // Constants for regular membership
    private final int attendanceLimit;

    // Additional attributes for regular members
    private boolean isEligibleForUpgrade;
    private String removalReason;
    private String referralSource;
    private String plan;
    private double price;

    /**
     * Constructor for RegularMember
     */
    public RegularMember(String id, String name, String location, String phone,
                         String email, String gender, String dob,
                         String membershipStartDate, String referralSource,
                         double paidAmount, String plan) {
        super(id, name, location, phone, email, gender, dob,
                membershipStartDate, referralSource, paidAmount);
        this.attendanceLimit = 30;
        this.isEligibleForUpgrade = false;
        this.removalReason = "";
        this.referralSource = referralSource;
        this.plan = "basic";  // Default plan is basic
        this.price = 6500;    // Default price for basic plan

        // Set plan and price if provided
        if (plan != null && !plan.isEmpty()) {
            this.plan = plan.toLowerCase();
            this.price = getPlanPrice(this.plan);
        }
    }

    // Getter and setter methods
    public int getAttendanceLimit() {
        return attendanceLimit;
    }

    public boolean isEligibleForUpgrade() {
        return isEligibleForUpgrade;
    }

    public void setEligibleForUpgrade(boolean eligibleForUpgrade) {
        isEligibleForUpgrade = eligibleForUpgrade;
    }

    @Override
    public String getRemovalReason() {
        return removalReason;
    }

    @Override
    public void setRemovalReason(String removalReason) {
        this.removalReason = removalReason;
    }

    public String getReferralSource() {
        return referralSource;
    }

    public void setReferralSource(String referralSource) {
        this.referralSource = referralSource;
    }

    public String getPlan() {
        return plan;
    }

    public void setPlan(String plan) {
        this.plan = plan;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Method to get plan price based on plan name
     * @param plan the plan name
     * @return the plan price
     */
    public double getPlanPrice(String plan) {
        switch (plan.toLowerCase()) {
            case "basic":
                return 6500;
            case "standard":
                return 12500;
            case "deluxe":
                return 18500;
            default:
                return -1;  // Invalid plan
        }
    }

    /**
     * Method to upgrade the member's plan
     * @param newPlan the new plan to upgrade to
     * @return message indicating success or failure
     */
    public String upgradePlan(String newPlan) {
        // Check if the member is eligible for upgrade
        if (attendance >= attendanceLimit) {
            isEligibleForUpgrade = true;
        }

        if (!isEligibleForUpgrade) {
            return "Member is not eligible for plan upgrade. Need at least " + attendanceLimit + " attendances.";
        }

        // Check if the plan is the same as current plan
        if (plan.equalsIgnoreCase(newPlan)) {
            return "Member is already subscribed to " + plan + " plan.";
        }

        // Try to get the price for the new plan
        double newPrice = getPlanPrice(newPlan);

        // Check if the plan is valid
        if (newPrice == -1) {
            return "Invalid plan selected. Available plans: Basic, Standard, Deluxe";
        }

        // Update plan and price
        this.plan = newPlan.toLowerCase();
        this.price = newPrice;

        return "Plan successfully upgraded to " + newPlan + " with price " + price;
    }

    /**
     * Method to mark attendance for the member
     */
    @Override
    public void markAttendance() {
        if (activeStatus) {
            attendance++;
            loyaltyPoints += 5;

            // Check if eligible for upgrade after marking attendance
            if (attendance >= attendanceLimit) {
                isEligibleForUpgrade = true;
            }
        }
    }

    /**
     * Method to revert regular member
     * @param removalReason the reason for removal
     */
    public void revertRegularMember(String removalReason) {
        resetMember();
        this.removalReason = removalReason;
        this.isEligibleForUpgrade = false;
        this.plan = "basic";
        this.price = 6500;
    }

    /**
     * Calculate the membership fee for regular members
     * @return the calculated fee
     */
    @Override
    public double calculateFee() {
        return price;
    }

    /**
     * Display method to show the regular member details
     */
    @Override
    public void display() {
        super.display();
        System.out.println("Plan: " + plan);
        System.out.println("Price: " + price);

        if (removalReason != null && !removalReason.isEmpty()) {
            System.out.println("Removal Reason: " + removalReason);
        }
    }

    @Override
    public String toString() {
        String result = super.toString() + "\n" +
                "Member Type: Regular Member\n" +
                "Plan: " + plan + "\n" +
                "Price: " + price;

        if (removalReason != null && !removalReason.isEmpty()) {
            result += "\nRemoval Reason: " + removalReason;
        }

        return result;
    }
}
