/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author milic
 */
@Entity
@Table(name = "prodaje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Prodaje.findAll", query = "SELECT p FROM Prodaje p"),
    @NamedQuery(name = "Prodaje.findByIdartikal", query = "SELECT p FROM Prodaje p WHERE p.idartikal = :idartikal")})
public class Prodaje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "idartikal")
    private Integer idartikal;
    @JoinColumn(name = "idartikal", referencedColumnName = "idartikal", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Artikal artikal;
    @JoinColumn(name = "idkorisnik", referencedColumnName = "username")
    @ManyToOne(optional = false)
    private Korisnik idkorisnik;

    public Prodaje() {
    }

    public Prodaje(Integer idartikal) {
        this.idartikal = idartikal;
    }

    public Integer getIdartikal() {
        return idartikal;
    }

    public void setIdartikal(Integer idartikal) {
        this.idartikal = idartikal;
    }

    public Artikal getArtikal() {
        return artikal;
    }

    public void setArtikal(Artikal artikal) {
        this.artikal = artikal;
    }

    public Korisnik getIdkorisnik() {
        return idkorisnik;
    }

    public void setIdkorisnik(Korisnik idkorisnik) {
        this.idkorisnik = idkorisnik;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idartikal != null ? idartikal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Prodaje)) {
            return false;
        }
        Prodaje other = (Prodaje) object;
        if ((this.idartikal == null && other.idartikal != null) || (this.idartikal != null && !this.idartikal.equals(other.idartikal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Prodaje[ idartikal=" + idartikal + " ]";
    }
    
}
