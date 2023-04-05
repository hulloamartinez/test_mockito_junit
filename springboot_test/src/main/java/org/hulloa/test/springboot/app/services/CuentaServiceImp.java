package org.hulloa.test.springboot.app.services;

import org.hulloa.test.springboot.app.models.Banco;
import org.hulloa.test.springboot.app.models.Cuenta;
import org.hulloa.test.springboot.app.repositories.BancoRepository;
import org.hulloa.test.springboot.app.repositories.CuentaRepository;

import java.math.BigDecimal;

public class CuentaServiceImp implements CuentaService {

    private CuentaRepository cuentaRepository;
    private BancoRepository bancoRepository;


    public CuentaServiceImp(CuentaRepository cuentaRepository, BancoRepository bancoRepository) {
        this.cuentaRepository = cuentaRepository;
        this.bancoRepository = bancoRepository;
    }

    @Override
    public Cuenta findById(Long id) {
        return this.cuentaRepository.findById(id);
    }

    @Override
    public int revisarTotalTransferencias(Long bancoId) {
        Banco banco = bancoRepository.findById(bancoId);
        return banco.getTotalTransferencias();
    }

    @Override
    public BigDecimal revisarSaldo(Long cuentaId) {
        Cuenta cuenta = cuentaRepository.findById(cuentaId);
        return cuenta.getSaldo();
    }

    @Override
    public void transferir(Long numeroCuentaOrigen, Long numeroCuentaDestino, BigDecimal monto, Long bancoId) {



        Cuenta cuentaOrigen = cuentaRepository.findById(numeroCuentaOrigen);
        cuentaOrigen.debito(monto);
        cuentaRepository.update(cuentaOrigen);

        Cuenta cuentaDestino = cuentaRepository.findById(numeroCuentaDestino);

        cuentaDestino.credito(monto);

        cuentaRepository.update(cuentaDestino);

        Banco banco = bancoRepository.findById(bancoId);
        int totalTransferencias = banco.getTotalTransferencias();
        banco.setTotalTransferencias(++ totalTransferencias);
        bancoRepository.update(banco);

    }
}
