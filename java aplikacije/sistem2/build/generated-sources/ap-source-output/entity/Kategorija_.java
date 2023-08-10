package entity;

import entity.Artikal;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-01-26T20:06:21")
@StaticMetamodel(Kategorija.class)
public class Kategorija_ { 

    public static volatile SingularAttribute<Kategorija, Integer> idnadkategorija;
    public static volatile SingularAttribute<Kategorija, String> naziv;
    public static volatile SingularAttribute<Kategorija, Integer> idkategorija;
    public static volatile ListAttribute<Kategorija, Artikal> artikalList;

}