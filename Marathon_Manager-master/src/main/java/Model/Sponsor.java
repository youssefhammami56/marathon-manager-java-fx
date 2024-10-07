package Model;

public class Sponsor {

        private int sponsorId;
        private String name;
        private String logo;
        private double sponsorshipAmount;

        // Constructor
        public Sponsor(int sponsorId, String name, String logo, double sponsorshipAmount) {
            this.sponsorId = sponsorId;
            this.name = name;
            this.logo = logo;
            this.sponsorshipAmount = sponsorshipAmount;
        }

        // Getters and Setters
        public int getSponsorId() {
            return sponsorId;
        }

        public void setSponsorId(int sponsorId) {
            this.sponsorId = sponsorId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public double getSponsorshipAmount() {
            return sponsorshipAmount;
        }

        public void setSponsorshipAmount(double sponsorshipAmount) {
            this.sponsorshipAmount = sponsorshipAmount;
        }


        private String logoPath;

        // Other attributes, constructor, and methods

        public String getLogoPath() {
            return logoPath;
        }

        public void setLogoPath(String logoPath) {
            this.logoPath = logoPath;
        }


    }

