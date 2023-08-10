/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author milic
 */
@Entity
@Table(name = "grad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Grad.findAll", query = "SELECT g FROM Grad g"),
    @NamedQuery(name = "Grad.findByIdgrad", query = "SELECT g FROM Grad g WHERE g.idgrad = :idgrad"),
    @NamedQuery(name = "Grad.findByNaziv", query = "SELECT g FROM Grad g WHERE g.naziv = :naziv"),
    @NamedQuery(name = "Grad.findByDrzava", query = "SELECT g FROM Grad g WHERE g.drzava = :drzava")})
public class Grad implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "drzava")
    private String drzava;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idgrad")
    private List<Korisnik> korisnikList;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idgrad")
    private Integer idgrad;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idgrad")
    private List<Narudzbina> narudzbinaList;

    public Grad() {
    }

    public Grad(Integer idgrad) {
        this.idgrad = idgrad;
    }

    public Grad(Integer idgrad, String naziv, String drzava) {
        this.idgrad = idgrad;
        this.naziv = naziv;
        this.drzava = drzava;
    }

    public Integer getIdgrad() {
        return idgrad;
    }

    public void setIdgrad(Integer idgrad) {
        this.idgrad = idgrad;
    }


    @XmlTransient
    public List<Narudzbina> getNarudzbinaList() {
        return narudzbinaList;
    }

    public void setNarudzbinaList(List<Narudzbina> narudzbinaList) {
        this.narudzbinaList = narudzbinaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idgrad != null ? idgrad.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Grad)) {
            return false;
        }
        Grad other = (Grad) object;
        if ((this.idgrad == null && other.idgrad != null) || (this.idgrad != null && !this.idgrad.equals(other.idgrad))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Grad[ idgrad=" + idgrad + " ]";
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getDrzava() {
        return drzava;
    }

    public void setDrzava(String drzava) {
        this.drzava = drzava;
    }

    @XmlTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }
    
}
