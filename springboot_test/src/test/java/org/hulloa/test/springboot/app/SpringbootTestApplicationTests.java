package org.hulloa.test.springboot.app;

import org.hulloa.test.springboot.app.exceptions.DineroInsuficienteException;
import org.hulloa.test.springboot.app.models.Banco;
import org.hulloa.test.springboot.app.models.Cuenta;
import org.hulloa.test.springboot.app.repositories.BancoRepository;
import org.hulloa.test.springboot.app.repositories.CuentaRepository;
import org.hulloa.test.springboot.app.services.CuentaService;
import org.hulloa.test.springboot.app.services.CuentaServiceImp;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hulloa.test.springboot.app.Datos.*;

@SpringBootTest
class SpringbootTestApplicationTests {

	CuentaRepository cuentaRepository;
	BancoRepository bancoRepository;
	CuentaService cuentaService;

	@BeforeEach
	void setUp() {
		cuentaRepository = mock(CuentaRepository.class);
		bancoRepository = mock(BancoRepository.class);
		cuentaService = new CuentaServiceImp(cuentaRepository,bancoRepository);
	}

	@Test
	void contextLoads() {
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco001());

		BigDecimal saldoCuentaOrigen = cuentaService.revisarSaldo(1L);
		BigDecimal saldoCuentaDestino = cuentaService.revisarSaldo(2L);

		assertEquals("1000",saldoCuentaOrigen.toPlainString());
		assertEquals("2000", saldoCuentaDestino.toPlainString());


		cuentaService.transferir(1L,2L,new BigDecimal("100"),1L);

		saldoCuentaOrigen = cuentaService.revisarSaldo(1L);
		saldoCuentaDestino = cuentaService.revisarSaldo(2L);

		assertEquals("900",saldoCuentaOrigen.toPlainString());
		assertEquals("2100", saldoCuentaDestino.toPlainString());

		int total= cuentaService.revisarTotalTransferencias(1L);

		assertEquals(1, total);

		verify(cuentaRepository,times(3)).findById(1L);
		verify(cuentaRepository,times(3)).findById(2L);

		verify(cuentaRepository,times(2)).update(any(Cuenta.class));
		verify(bancoRepository, times(2)).findById(1L);
		verify(bancoRepository).update(any(Banco.class));

		verify(cuentaRepository,never()).findAll();
		verify(cuentaRepository,times(6)).findById(anyLong());



	}

	@Test
	void contextLoadsDos() {
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());
		when(cuentaRepository.findById(2L)).thenReturn(crearCuenta002());
		when(bancoRepository.findById(1L)).thenReturn(crearBanco001());

		BigDecimal saldoCuentaOrigen = cuentaService.revisarSaldo(1L);
		BigDecimal saldoCuentaDestino = cuentaService.revisarSaldo(2L);

		assertEquals("1000",saldoCuentaOrigen.toPlainString());
		assertEquals("2000", saldoCuentaDestino.toPlainString());

		assertThrows(DineroInsuficienteException.class,()->{

			cuentaService.transferir(1L,2L,new BigDecimal("1200"),1L);

		});


		saldoCuentaOrigen = cuentaService.revisarSaldo(1L);
		saldoCuentaDestino = cuentaService.revisarSaldo(2L);

		assertEquals("1000",saldoCuentaOrigen.toPlainString());
		assertEquals("2000", saldoCuentaDestino.toPlainString());

		int total= cuentaService.revisarTotalTransferencias(1L);

		assertEquals(1, total);

		verify(cuentaRepository,times(3)).findById(1L);
		verify(cuentaRepository,times(2)).findById(2L);
		verify(cuentaRepository,never()).update(any(Cuenta.class));
		verify(bancoRepository, times(1)).findById(1L);
		verify(bancoRepository,never()).update(any(Banco.class));

		verify(cuentaRepository,times(5)).findById(anyLong());
		verify(cuentaRepository,never()).findAll();

	}

	@Test
	void contextLoadsTres() {
		when(cuentaRepository.findById(1L)).thenReturn(crearCuenta001());

		Cuenta cuenta1 = cuentaService.findById(1L);
		Cuenta cuenta2 = cuentaService.findById(1L);

		assertSame(cuenta1,cuenta2);
		assertTrue(cuenta1==cuenta2);

		assertEquals("Andres", cuenta1.getPersona());
		assertEquals("Andres", cuenta2.getPersona());
		verify(cuentaRepository,times(2)).findById(1L);
	}
}
