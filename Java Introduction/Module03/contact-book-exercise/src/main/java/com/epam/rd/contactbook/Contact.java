package com.epam.rd.contactbook;

public class Contact {

    private String name;
    private ContactInfo phone;
    private ContactInfo[] emails;
    private ContactInfo[] socials;

    private int emailsCount;
    private int socialsCount;

    public Contact(String contactName) {
        this.name = contactName;
        this.emails = new ContactInfo[3];
        this.socials = new ContactInfo[5];
        this.emailsCount = 0;
        this.socialsCount = 0;
    }

    private class NameContactInfo implements ContactInfo {
        @Override
        public String getTitle() {
            return ("Name");
        }

        @Override
        public String getValue() {
            return (name);
        }
    }

    public static class Email implements ContactInfo {
        private String value;

        public Email(String localPart, String domain) {
            value = localPart + "@" + domain;
        }

        @Override
        public String getTitle() {
            return ("Email");
        }

        @Override
        public String getValue() {
            return (value);
        }
    }

    public static class Social implements ContactInfo {
        private String title;
        private String id;

        public Social(String title, String id) {
            this.title = title;
            this.id = id;
        }

        @Override
        public String getTitle() {
            return (title);
        }

        @Override
        public String getValue() {
            return (id);
        }
    }

    private void incrementEmails() { emailsCount++; }
    private void incrementSocials() { socialsCount++; }

    public void rename(String newName) {
        if (this.name != null && newName != null && !newName.trim().isEmpty()) {
            this.name = newName;
        }
    }

    public Email addEmail(String localPart, String domain) {
        if (emailsCount < emails.length) {
            Email toAdd = new Email(localPart, domain);
            emails[emailsCount] = toAdd;
            incrementEmails();
            return (toAdd);
        }
        return (null);
    }

    public Social addSocialMedia(String title, String id) {
        if (socialsCount < socials.length) {
            Social toAdd = new Social(title, id);
            socials[socialsCount] = toAdd;
            incrementSocials();
            return (toAdd);
        }
        return (null);
    }

    public Email addEpamEmail(String firstname, String lastname) {
        if (emailsCount < emails.length) {
            Email toAdd = new Email(firstname + "_" + lastname , "epam.com") {
                @Override
                public String getTitle() {
                    return ("Epam Email");
                }
            };
            emails[emailsCount] = toAdd;
            incrementEmails();
            return (toAdd);
        }
        return (null);
    }

    public ContactInfo addPhoneNumber(int code, String number) {
        if (phone == null) {
            ContactInfo toAdd = new ContactInfo() {
                @Override
                public String getTitle() {
                    return ("Tel");
                }

                @Override
                public String getValue() {
                    return ("+" + code + " " + number);
                }
            };
            phone = toAdd;
            return (toAdd);
        }
        return (null);
    }

    public Social addTwitter(String twitterId) {
        return( addSocialMedia("Twitter", twitterId) );
    }

    public Social addInstagram(String instagramId) {
        return( addSocialMedia("Instagram", instagramId) );
    }

    public ContactInfo[] getInfo() {
        int size = 1;
        if (phone != null) {
            size++;
        }
        size +=  emailsCount + socialsCount;

        ContactInfo[] infos = new ContactInfo[size];
        int idx = 0;
        infos[idx++] = new NameContactInfo();
        if (phone != null) {
            infos[idx++] = phone;
        }
        for (int i = 0; i < emailsCount; i++) {
            infos[idx++] = emails[i];
        }
        for (int i = 0; i < socialsCount; i++) {
            infos[idx++] = socials[i];
        }
        return (infos);
    }

}
