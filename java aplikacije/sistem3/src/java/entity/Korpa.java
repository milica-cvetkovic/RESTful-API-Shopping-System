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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author milic
 */
@Entity
@Table(name = "korpa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Korpa.findAll", query = "SELECT k FROM Korpa k"),
    @NamedQuery(name = "Korpa.findByIdkorpa", query = "SELECT k FROM Korpa k WHERE k.idkorpa = :idkorpa"),
    @NamedQuery(name = "Korpa.findByUkupnacena", query = "SELECT k FROM Korpa k WHERE k.ukupnacena = :ukupnacena")})
public class Korpa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idkorpa")
    private Integer idkorpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupnacena")
    private float ukupnacena;
    @JoinColumn(name = "idkorisnika", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Korisnik idkorisnika;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "korpa")
    private List<Sadrzi> sadrziList;

    public Korpa() {
    }

    public Korpa(Integer idkorpa) {
        this.idkorpa = idkorpa;
    }

    public Korpa(Integer idkorpa, float ukupnacena) {
        this.idkorpa = idkorpa;
        this.ukupnacena = ukupnacena;
    }

    public Integer getIdkorpa() {
        return idkorpa;
    }

    public void setIdkorpa(Integer idkorpa) {
        this.idkorpa = idkorpa;
    }

    public float getUkupnacena() {
        return ukupnacena;
    }

    public void setUkupnacena(float ukupnacena) {
        this.ukupnacena = ukupnacena;
    }

    public Korisnik getIdkorisnika() {
        return idkorisnika;
    }

    public void setIdkorisnika(Korisnik idkorisnika) {
        this.idkorisnika = idkorisnika;
    }

    @XmlTransient
    public List<Sadrzi> getSadrziList() {
        return sadrziList;
    }

    public void setSadrziList(List<Sadrzi> sadrziList) {
        this.sadrziList = sadrziList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idkorpa != null ? idkorpa.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Korpa)) {
            return false;
        }
        Korpa other = (Korpa) object;
        if ((this.idkorpa == null && other.idkorpa != null) || (this.idkorpa != null && !this.idkorpa.equals(other.idkorpa))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Korpa[ idkorpa=" + idkorpa + " ]";
    }
    
}
