/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author milic
 */
@Entity
@Table(name = "artikal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Artikal.findAll", query = "SELECT a FROM Artikal a"),
    @NamedQuery(name = "Artikal.findByIdartikal", query = "SELECT a FROM Artikal a WHERE a.idartikal = :idartikal"),
    @NamedQuery(name = "Artikal.findByNaziv", query = "SELECT a FROM Artikal a WHERE a.naziv = :naziv"),
    @NamedQuery(name = "Artikal.findByOpis", query = "SELECT a FROM Artikal a WHERE a.opis = :opis"),
    @NamedQuery(name = "Artikal.findByCena", query = "SELECT a FROM Artikal a WHERE a.cena = :cena"),
    @NamedQuery(name = "Artikal.findByPopust", query = "SELECT a FROM Artikal a WHERE a.popust = :popust")})
public class Artikal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idartikal")
    private Integer idartikal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "naziv")
    private String naziv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "opis")
    private String opis;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private float cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "popust")
    private int popust;
    @JoinColumn(name = "idkategorija", referencedColumnName = "idkategorija")
    @ManyToOne(optional = false)
    private Kategorija idkategorija;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "artikal")
    private Prodaje prodaje;

    public Artikal() {
    }

    public Artikal(Integer idartikal) {
        this.idartikal = idartikal;
    }

    public Artikal(Integer idartikal, String naziv, String opis, float cena, int popust) {
        this.idartikal = idartikal;
        this.naziv = naziv;
        this.opis = opis;
        this.cena = cena;
        this.popust = popust;
    }

    public Integer getIdartikal() {
        return idartikal;
    }

    public void setIdartikal(Integer idartikal) {
        this.idartikal = idartikal;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public int getPopust() {
        return popust;
    }

    public void setPopust(int popust) {
        this.popust = popust;
    }

    public Kategorija getIdkategorija() {
        return idkategorija;
    }

    public void setIdkategorija(Kategorija idkategorija) {
        this.idkategorija = idkategorija;
    }

    public Prodaje getProdaje() {
        return prodaje;
    }

    public void setProdaje(Prodaje prodaje) {
        this.prodaje = prodaje;
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
        if (!(object instanceof Artikal)) {
            return false;
        }
        Artikal other = (Artikal) object;
        if ((this.idartikal == null && other.idartikal != null) || (this.idartikal != null && !this.idartikal.equals(other.idartikal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Artikal[ idartikal=" + idartikal + " ]";
    }
    
}
