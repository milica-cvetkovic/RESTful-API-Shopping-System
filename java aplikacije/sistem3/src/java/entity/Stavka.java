/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author milic
 */
@Entity
@Table(name = "stavka")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Stavka.findAll", query = "SELECT s FROM Stavka s"),
    @NamedQuery(name = "Stavka.findByIdstavka", query = "SELECT s FROM Stavka s WHERE s.idstavka = :idstavka"),
    @NamedQuery(name = "Stavka.findByCena", query = "SELECT s FROM Stavka s WHERE s.cena = :cena"),
    @NamedQuery(name = "Stavka.findByKolicina", query = "SELECT s FROM Stavka s WHERE s.kolicina = :kolicina")})
public class Stavka implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idstavka")
    private Integer idstavka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cena")
    private float cena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "kolicina")
    private int kolicina;
    @JoinColumn(name = "idartikal", referencedColumnName = "idartikal")
    @ManyToOne(optional = false)
    private Artikal idartikal;
    @JoinColumn(name = "idnarudzbina", referencedColumnName = "idnarudzbina")
    @ManyToOne(optional = false)
    private Narudzbina idnarudzbina;

    public Stavka() {
    }

    public Stavka(Integer idstavka) {
        this.idstavka = idstavka;
    }

    public Stavka(Integer idstavka, float cena, int kolicina) {
        this.idstavka = idstavka;
        this.cena = cena;
        this.kolicina = kolicina;
    }

    public Integer getIdstavka() {
        return idstavka;
    }

    public void setIdstavka(Integer idstavka) {
        this.idstavka = idstavka;
    }

    public float getCena() {
        return cena;
    }

    public void setCena(float cena) {
        this.cena = cena;
    }

    public int getKolicina() {
        return kolicina;
    }

    public void setKolicina(int kolicina) {
        this.kolicina = kolicina;
    }

    public Artikal getIdartikal() {
        return idartikal;
    }

    public void setIdartikal(Artikal idartikal) {
        this.idartikal = idartikal;
    }

    public Narudzbina getIdnarudzbina() {
        return idnarudzbina;
    }

    public void setIdnarudzbina(Narudzbina idnarudzbina) {
        this.idnarudzbina = idnarudzbina;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idstavka != null ? idstavka.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Stavka)) {
            return false;
        }
        Stavka other = (Stavka) object;
        if ((this.idstavka == null && other.idstavka != null) || (this.idstavka != null && !this.idstavka.equals(other.idstavka))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Stavka[ idstavka=" + idstavka + " ]";
    }
    
}
