package org.hulloa.test.springboot.app;

import org.hulloa.test.springboot.app.models.Banco;
import org.hulloa.test.springboot.app.models.Cuenta;

import java.math.BigDecimal;

public class Datos {
    //public static final Cuenta CUENTA_O01 = new Cuenta(1L,"Andres", new BigDecimal("1000"));
    //public static final Cuenta CUENTA_O02 = new Cuenta(2L,"john", new BigDecimal("2000"));


    //public static final Banco BANCO = new Banco(1L,"Banco Chile",0);

    public static Cuenta crearCuenta001(){
        return new Cuenta(1L,"Andres", new BigDecimal("1000"));
    }
    public static Cuenta crearCuenta002(){
        return new Cuenta(2L,"john", new BigDecimal("2000"));
    }

    public static Banco crearBanco001(){
        return   new Banco(1L,"Banco Chile",0);
    }
}
