package entity;

import entity.Grad;
import entity.Korisnik;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-01-27T10:19:43")
@StaticMetamodel(Narudzbina.class)
public class Narudzbina_ { 

    public static volatile SingularAttribute<Narudzbina, Float> ukupnacena;
    public static volatile SingularAttribute<Narudzbina, Date> vreme;
    public static volatile SingularAttribute<Narudzbina, Grad> idgrad;
    public static volatile SingularAttribute<Narudzbina, String> adresa;
    public static volatile SingularAttribute<Narudzbina, Integer> idnarudzbina;
    public static volatile SingularAttribute<Narudzbina, Korisnik> idkorisnika;

}