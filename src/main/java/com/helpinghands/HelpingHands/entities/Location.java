package com.helpinghands.HelpingHands.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Location {
    @Id
    private String postalcode;

    @NotBlank(message = "District must not be blank")
    private String district;


    private long totaldisaster;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "postal")
    private List<Centralrepositoryofincident> centralrepositoryofincidentList;

    public List<Users> getUsers() {
        return users;
    }

    public void setUsers(List<Users> users) {
        this.users = users;
    }

    public Admin getAdmin() {
        return admin;
    }

    public void setAdmin(Admin admin) {
        this.admin = admin;
    }

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "postal")
    private List<Temporarydatabaseofincident> temporarydatabaseofincidents;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "postal")
    private List<Users> users;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "admin")
    private Admin admin;


}
