/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author milic
 */
@Entity
@Table(name = "narudzbina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Narudzbina.findAll", query = "SELECT n FROM Narudzbina n"),
    @NamedQuery(name = "Narudzbina.findByIdnarudzbina", query = "SELECT n FROM Narudzbina n WHERE n.idnarudzbina = :idnarudzbina"),
    @NamedQuery(name = "Narudzbina.findByUkupnacena", query = "SELECT n FROM Narudzbina n WHERE n.ukupnacena = :ukupnacena"),
    @NamedQuery(name = "Narudzbina.findByVreme", query = "SELECT n FROM Narudzbina n WHERE n.vreme = :vreme"),
    @NamedQuery(name = "Narudzbina.findByAdresa", query = "SELECT n FROM Narudzbina n WHERE n.adresa = :adresa")})
public class Narudzbina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idnarudzbina")
    private Integer idnarudzbina;
    @Basic(optional = false)
    @NotNull
    @Column(name = "ukupnacena")
    private float ukupnacena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "adresa")
    private String adresa;
    @JoinColumn(name = "idgrad", referencedColumnName = "idgrad")
    @ManyToOne(optional = false)
    private Grad idgrad;
    @JoinColumn(name = "idkorisnika", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Korisnik idkorisnika;

    public Narudzbina() {
    }

    public Narudzbina(Integer idnarudzbina) {
        this.idnarudzbina = idnarudzbina;
    }

    public Narudzbina(Integer idnarudzbina, float ukupnacena, Date vreme, String adresa) {
        this.idnarudzbina = idnarudzbina;
        this.ukupnacena = ukupnacena;
        this.vreme = vreme;
        this.adresa = adresa;
    }

    public Integer getIdnarudzbina() {
        return idnarudzbina;
    }

    public void setIdnarudzbina(Integer idnarudzbina) {
        this.idnarudzbina = idnarudzbina;
    }

    public float getUkupnacena() {
        return ukupnacena;
    }

    public void setUkupnacena(float ukupnacena) {
        this.ukupnacena = ukupnacena;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
    }

    public String getAdresa() {
        return adresa;
    }

    public void setAdresa(String adresa) {
        this.adresa = adresa;
    }

    public Grad getIdgrad() {
        return idgrad;
    }

    public void setIdgrad(Grad idgrad) {
        this.idgrad = idgrad;
    }

    public Korisnik getIdkorisnika() {
        return idkorisnika;
    }

    public void setIdkorisnika(Korisnik idkorisnika) {
        this.idkorisnika = idkorisnika;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idnarudzbina != null ? idnarudzbina.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Narudzbina)) {
            return false;
        }
        Narudzbina other = (Narudzbina) object;
        if ((this.idnarudzbina == null && other.idnarudzbina != null) || (this.idnarudzbina != null && !this.idnarudzbina.equals(other.idnarudzbina))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Narudzbina[ idnarudzbina=" + idnarudzbina + " ]";
    }
    
}
