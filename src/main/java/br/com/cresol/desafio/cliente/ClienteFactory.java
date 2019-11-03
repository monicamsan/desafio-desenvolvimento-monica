package br.com.cresol.desafio.cliente;

public class ClienteFactory {

	public static Long codigoCliente = 1L;
	public static Long codigoEmprestimo = 1L;

	public static Cliente getCliente1() {
		Cliente c1 = new Cliente();
		c1.setCodigo(codigoCliente++);
		c1.setCpf(60920473067L);
		c1.setNome("Monica Santana");
		c1.setEmail("monicasantana@gmail.com");
		return c1;
	}

	public static Cliente getCliente2() {
		Cliente c2 = new Cliente();
		c2.setCodigo(codigoCliente++);
		c2.setCpf(79828027020L);
		c2.setNome("Cliente 2");
		c2.setEmail("cliente2@gmail.com");
		return c2;
	}

	public static Cliente getCliente3() {
		Cliente c3 = new Cliente();
		c3.setCodigo(codigoCliente++);
		c3.setCpf(59412348053L);
		c3.setNome("Cliente 3");
		c3.setEmail("cliente3@gmail.com");
		return c3;
	}

}
