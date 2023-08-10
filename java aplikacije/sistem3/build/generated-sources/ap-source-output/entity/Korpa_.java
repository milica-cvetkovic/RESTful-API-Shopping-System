package entity;

import entity.Korisnik;
import entity.Sadrzi;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-01-27T10:19:43")
@StaticMetamodel(Korpa.class)
public class Korpa_ { 

    public static volatile SingularAttribute<Korpa, Float> ukupnacena;
    public static volatile SingularAttribute<Korpa, Korisnik> idkorisnika;
    public static volatile ListAttribute<Korpa, Sadrzi> sadrziList;
    public static volatile SingularAttribute<Korpa, Integer> idkorpa;

}