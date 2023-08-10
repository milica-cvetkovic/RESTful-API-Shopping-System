package entity;

import entity.Korisnik;
import entity.Narudzbina;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-01-27T10:19:43")
@StaticMetamodel(Grad.class)
public class Grad_ { 

    public static volatile SingularAttribute<Grad, String> drzava;
    public static volatile ListAttribute<Grad, Narudzbina> narudzbinaList;
    public static volatile SingularAttribute<Grad, Integer> idgrad;
    public static volatile SingularAttribute<Grad, String> naziv;
    public static volatile ListAttribute<Grad, Korisnik> korisnikList;

}