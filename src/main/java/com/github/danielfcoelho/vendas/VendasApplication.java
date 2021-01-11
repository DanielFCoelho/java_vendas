package com.github.danielfcoelho.vendas;

import com.github.danielfcoelho.vendas.domain.entity.cliente;
import com.github.danielfcoelho.vendas.domain.repository.clienteRepositoryJPA;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication // Anotação que indica que a classe que é responsável por iniciar uma aplicação
						// spring boot
public class VendasApplication {

	@Bean
	public CommandLineRunner init(@Autowired clienteRepositoryJPA clienteRepository) {
		return args -> {
			cliente c = new cliente("Alvaro");
			clienteRepository.save(c);
		};
	}

	// @Bean
	// public CommandLineRunner init(@Autowired clienteRepositoryJPA
	// clienterepository,
	// @Autowired pedidoRepositoryJPA pedidoRepository) {
	// return args -> {
	// System.out.println("Salvando os clientes");

	// cliente cliente1 = clienterepository.save(new cliente("Daniel"));

	// pedido p = new pedido();
	// p.setCliente(cliente1);
	// p.setDataPedido(LocalDate.now());
	// p.setTotal(BigDecimal.valueOf(100));

	// pedidoRepository.save(p);

	// cliente clienteComPedidos =
	// clienterepository.findClienteFetchPedidos(cliente1.getId());
	// System.out.println(clienteComPedidos);
	// // System.out.println(clienteComPedidos.getPedidos());

	// pedidoRepository.findByCliente(clienteComPedidos).forEach(System.out::println);

	// // clienterepository.save(new cliente("Alvaro"));

	// // Boolean existe = clienterepository.existsByNome("Da");
	// // System.out.println("Existe cliente Daniel? " + existe);

	// // existe = clienterepository.existsByNome("Biro Biro");
	// // System.out.println("Existe cliente Biro Biro? " + existe);

	// // List<cliente> clientes = clienterepository.findAll();
	// // clientes.forEach(System.out::println);

	// // System.out.println("Atualizando os clientes");
	// // clientes.forEach(k -> {
	// // k.setNome(k.getNome() + " atualizado.");
	// // clienterepository.save(k);
	// // });

	// // clientes = clienterepository.findAll();
	// // clientes.forEach(System.out::println);

	// // System.out.println("Buscando os clientes");
	// // clientes = clienterepository.encontraPorNomeSQL("varo");
	// // clientes.forEach(System.out::println);

	// // System.out.println("Apagando os clientes");
	// // clienterepository.findAll().forEach(c -> {
	// // clienterepository.deleteById(c.getId());
	// // });

	// // System.out.println("Buscando os clientes");
	// // clientes = clienterepository.findAll();
	// // if (clientes.isEmpty()) {
	// // System.out.println("nenhum cliente encontrado");
	// // } else {
	// // clientes.forEach(System.out::println);
	// // }
	// };
	// }

	public static void main(String[] args) {
		SpringApplication.run(VendasApplication.class, args);
	}

}
