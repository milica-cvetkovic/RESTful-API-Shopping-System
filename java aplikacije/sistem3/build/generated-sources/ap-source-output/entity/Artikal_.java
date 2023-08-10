package entity;

import entity.Sadrzi;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-01-27T10:19:43")
@StaticMetamodel(Artikal.class)
public class Artikal_ { 

    public static volatile SingularAttribute<Artikal, String> naziv;
    public static volatile SingularAttribute<Artikal, Integer> idartikal;
    public static volatile SingularAttribute<Artikal, Integer> popust;
    public static volatile SingularAttribute<Artikal, Float> cena;
    public static volatile ListAttribute<Artikal, Sadrzi> sadrziList;
    public static volatile SingularAttribute<Artikal, String> opis;

}