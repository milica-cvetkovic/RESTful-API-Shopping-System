package entity;

import entity.Artikal;
import entity.Narudzbina;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-01-26T21:07:49")
@StaticMetamodel(Stavka.class)
public class Stavka_ { 

    public static volatile SingularAttribute<Stavka, Integer> kolicina;
    public static volatile SingularAttribute<Stavka, Narudzbina> idnarudzbina;
    public static volatile SingularAttribute<Stavka, Artikal> idartikal;
    public static volatile SingularAttribute<Stavka, Float> cena;
    public static volatile SingularAttribute<Stavka, Integer> idstavka;

}