/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author milic
 */
@Embeddable
public class SadrziPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "idkorpa")
    private int idkorpa;
    @Basic(optional = false)
    @NotNull
    @Column(name = "idartikal")
    private int idartikal;

    public SadrziPK() {
    }

    public SadrziPK(int idkorpa, int idartikal) {
        this.idkorpa = idkorpa;
        this.idartikal = idartikal;
    }

    public int getIdkorpa() {
        return idkorpa;
    }

    public void setIdkorpa(int idkorpa) {
        this.idkorpa = idkorpa;
    }

    public int getIdartikal() {
        return idartikal;
    }

    public void setIdartikal(int idartikal) {
        this.idartikal = idartikal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idkorpa;
        hash += (int) idartikal;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof SadrziPK)) {
            return false;
        }
        SadrziPK other = (SadrziPK) object;
        if (this.idkorpa != other.idkorpa) {
            return false;
        }
        if (this.idartikal != other.idartikal) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.SadrziPK[ idkorpa=" + idkorpa + ", idartikal=" + idartikal + " ]";
    }
    
}
