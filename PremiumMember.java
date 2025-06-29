/**
 * PremiumMember class represents a premium gym member
 * with specific attributes and behaviors
 */
public class PremiumMember extends GymMember {
    // Constants for premium membership
    private final double premiumCharge;

    // Additional attributes for premium members
    private String personalTrainer;
    private boolean isFullPayment;
    private double paidAmount;
    private double discountAmount;

    /**
     * Constructor for PremiumMember
     */
    public PremiumMember(String id, String name, String location, String phone,
                         String email, String gender, String dob,
                         String membershipStartDate, String referralSource,
                         double initialPaidAmount, String personalTrainer) {
        super(id, name, location, phone, email, gender, dob,
                membershipStartDate, referralSource, initialPaidAmount);
        this.premiumCharge = 50000.0;
        this.personalTrainer = personalTrainer;
        this.isFullPayment = false;
        this.paidAmount = 0.0;
        this.discountAmount = 0.0;

        // Process initial payment if provided
        if (initialPaidAmount > 0) {
            payDueAmount(initialPaidAmount);
        }
    }

    // Getter and setter methods
    public double getPremiumCharge() {
        return premiumCharge;
    }

    public String getPersonalTrainer() {
        return personalTrainer;
    }

    public void setPersonalTrainer(String personalTrainer) {
        this.personalTrainer = personalTrainer;
    }

    public boolean isFullPayment() {
        return isFullPayment;
    }

    public void setFullPayment(boolean fullPayment) {
        isFullPayment = fullPayment;
    }

    public double getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(double paidAmount) {
        this.paidAmount = paidAmount;
    }

    public double getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(double discountAmount) {
        this.discountAmount = discountAmount;
    }

    /**
     * Method to pay due amount
     * @param amount the amount to pay
     * @return message indicating success or failure
     */
    public String payDueAmount(double amount) {
        // Check if payment is already full
        if (isFullPayment) {
            return "Payment is already complete. No due amount remaining.";
        }

        // Add to the paid amount
        double newTotal = this.paidAmount + amount;

        // Check if the new total exceeds the premium charge
        if (newTotal > premiumCharge) {
            return "Payment amount exceeds the premium charge. Maximum amount: " + (premiumCharge - this.paidAmount);
        }

        // Update paid amount
        this.paidAmount = newTotal;

        // Update payment status
        if (this.paidAmount >= premiumCharge) {
            this.isFullPayment = true;
            return "Payment successful! Your membership is now fully paid.";
        } else {
            double remainingAmount = premiumCharge - this.paidAmount;
            return "Payment successful! Remaining amount to be paid: " + remainingAmount;
        }
    }

    /**
     * Method to calculate discount
     * @return message indicating success or calculation result
     */
    public String calculateDiscount() {
        if (isFullPayment) {
            // 10% discount on premium charge
            this.discountAmount = premiumCharge * 0.01;
            return "Discount calculated successfully! You received a 10% discount of " + discountAmount;
        } else {
            return "No discount available. Full payment is required to get a discount.";
        }
    }

    /**
     * Method to revert premium member
     */
    public void revertPremiumMember(String removalReason) {
        resetMember();
        this.personalTrainer = "";
        this.isFullPayment = false;
        this.paidAmount = 0.0;
        this.discountAmount = 0.0;
        setRemovalReason(removalReason);
    }

    /**
     * Method to mark attendance
     */
    @Override
    public void markAttendance() {
        if (activeStatus) {
            attendance++;
            loyaltyPoints += 10;  // Premium members get more loyalty points
        }
    }

    /**
     * Calculate the membership fee for premium members
     * @return the premium membership charge minus any discount
     */
    @Override
    public double calculateFee() {
        return premiumCharge - discountAmount;
    }

    /**
     * Display method to show the premium member details
     */
    @Override
    public void display() {
        super.display();
        System.out.println("Personal Trainer: " + personalTrainer);
        System.out.println("Paid Amount: " + paidAmount);
        System.out.println("Full Payment: " + (isFullPayment ? "Yes" : "No"));

        double remainingAmount = premiumCharge - paidAmount;
        System.out.println("Remaining Amount: " + remainingAmount);

        if (isFullPayment) {
            System.out.println("Discount Amount: " + discountAmount);
        }
    }

    @Override
    public String toString() {
        String result = super.toString() + "\n" +
                "Member Type: Premium Member\n" +
                "Personal Trainer: " + personalTrainer + "\n" +
                "Paid Amount: " + paidAmount + "\n" +
                "Full Payment: " + (isFullPayment ? "Yes" : "No");

        double remainingAmount = premiumCharge - paidAmount;
        result += "\nRemaining Amount to be Paid: " + remainingAmount;

        if (isFullPayment) {
            result += "\nDiscount Amount: " + discountAmount;
        }

        return result;
    }
}
