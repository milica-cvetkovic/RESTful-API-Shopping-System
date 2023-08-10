package entity;

import entity.Narudzbina;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.7.10.v20211216-rNA", date="2023-01-26T21:07:49")
@StaticMetamodel(Transakcija.class)
public class Transakcija_ { 

    public static volatile SingularAttribute<Transakcija, Float> suma;
    public static volatile SingularAttribute<Transakcija, Date> vreme;
    public static volatile SingularAttribute<Transakcija, Integer> idtransakcija;
    public static volatile SingularAttribute<Transakcija, Narudzbina> idnarudzbina;

}