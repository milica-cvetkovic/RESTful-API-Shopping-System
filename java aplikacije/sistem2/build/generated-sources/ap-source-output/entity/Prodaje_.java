package entity;

import entity.Artikal;
import entity.Korisnik;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-01-26T20:06:21")
@StaticMetamodel(Prodaje.class)
public class Prodaje_ { 

    public static volatile SingularAttribute<Prodaje, Korisnik> idkorisnik;
    public static volatile SingularAttribute<Prodaje, Artikal> artikal;
    public static volatile SingularAttribute<Prodaje, Integer> idartikal;

}