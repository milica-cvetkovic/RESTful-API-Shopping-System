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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author milic
 */
@Entity
@Table(name = "transakcija")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Transakcija.findAll", query = "SELECT t FROM Transakcija t"),
    @NamedQuery(name = "Transakcija.findByIdtransakcija", query = "SELECT t FROM Transakcija t WHERE t.idtransakcija = :idtransakcija"),
    @NamedQuery(name = "Transakcija.findBySuma", query = "SELECT t FROM Transakcija t WHERE t.suma = :suma"),
    @NamedQuery(name = "Transakcija.findByVreme", query = "SELECT t FROM Transakcija t WHERE t.vreme = :vreme")})
public class Transakcija implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "idtransakcija")
    private Integer idtransakcija;
    @Basic(optional = false)
    @NotNull
    @Column(name = "suma")
    private float suma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "vreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date vreme;
    @JoinColumn(name = "idnarudzbina", referencedColumnName = "idnarudzbina")
    @ManyToOne(optional = false)
    private Narudzbina idnarudzbina;

    public Transakcija() {
    }

    public Transakcija(Integer idtransakcija) {
        this.idtransakcija = idtransakcija;
    }

    public Transakcija(Integer idtransakcija, float suma, Date vreme) {
        this.idtransakcija = idtransakcija;
        this.suma = suma;
        this.vreme = vreme;
    }

    public Integer getIdtransakcija() {
        return idtransakcija;
    }

    public void setIdtransakcija(Integer idtransakcija) {
        this.idtransakcija = idtransakcija;
    }

    public float getSuma() {
        return suma;
    }

    public void setSuma(float suma) {
        this.suma = suma;
    }

    public Date getVreme() {
        return vreme;
    }

    public void setVreme(Date vreme) {
        this.vreme = vreme;
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
        hash += (idtransakcija != null ? idtransakcija.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Transakcija)) {
            return false;
        }
        Transakcija other = (Transakcija) object;
        if ((this.idtransakcija == null && other.idtransakcija != null) || (this.idtransakcija != null && !this.idtransakcija.equals(other.idtransakcija))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.Transakcija[ idtransakcija=" + idtransakcija + " ]";
    }
    
}
