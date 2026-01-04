package com.cms.customer_management.entity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Entity
@Table(
        name = "customer",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "nic")
        }
)
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    @Column(nullable = false, unique = true)
    private String nic;

    // ✅ Mobile numbers (optional)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CustomerMobile> mobileNumbers;

    // ✅ Addresses (optional)
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    // ✅ Family members
    @ManyToMany
    @JoinTable(
            name = "customer_family",
            joinColumns = @JoinColumn(name = "customer_id"),
            inverseJoinColumns = @JoinColumn(name = "family_member_id")
    )
    private Set<Customer> familyMembers;

    // ===== GETTERS & SETTERS =====

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public String getNic() {
        return nic;
    }

    public List<CustomerMobile> getMobileNumbers() {
        return mobileNumbers;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public Set<Customer> getFamilyMembers() {
        return familyMembers;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }

    public void setMobileNumbers(List<CustomerMobile> mobileNumbers) {
        this.mobileNumbers = mobileNumbers;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setFamilyMembers(Set<Customer> familyMembers) {
        this.familyMembers = familyMembers;
    }
}
