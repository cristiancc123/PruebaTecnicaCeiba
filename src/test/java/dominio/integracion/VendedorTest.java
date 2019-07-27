package dominio.integracion;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import dominio.Vendedor;
import dominio.GarantiaExtendida;
import dominio.Producto;
import dominio.excepcion.GarantiaExtendidaException;
import dominio.repositorio.RepositorioProducto;
import dominio.servicios.ProductoServicio;
import dominio.repositorio.RepositorioGarantiaExtendida;
import persistencia.sistema.SistemaDePersistencia;
import testdatabuilder.ProductoTestDataBuilder;

public class VendedorTest {

	private static final String COMPUTADOR_LENOVO = "Computador Lenovo";
	private static final String CODIGO_VOCALES = "A01EOA015U";
	private static final String NOMBRE_CLIENTE = "Cristian Cano";
	
	private SistemaDePersistencia sistemaPersistencia;
	
	private RepositorioProducto repositorioProducto;
	private RepositorioGarantiaExtendida repositorioGarantia;

	@Before
	public void setUp() {
		
		sistemaPersistencia = new SistemaDePersistencia();
		
		repositorioProducto = sistemaPersistencia.obtenerRepositorioProductos();
		repositorioGarantia = sistemaPersistencia.obtenerRepositorioGarantia();
		
		sistemaPersistencia.iniciar();
	}
	

	@After
	public void tearDown() {
		sistemaPersistencia.terminar();
	}

	@Test
	public void generarGarantiaTest() {

		// arrange
		Producto producto = new ProductoTestDataBuilder().conNombre(COMPUTADOR_LENOVO).build();
		repositorioProducto.agregar(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
		ProductoServicio productoServicio = new ProductoServicio(repositorioProducto, repositorioGarantia);
    	
		// act
		vendedor.generarGarantia(producto, NOMBRE_CLIENTE);

		// assert
		Assert.assertTrue(productoServicio.tieneGarantiaExtendida(producto.getCodigo()));
		Assert.assertNotNull(repositorioGarantia.obtenerProductoConGarantiaPorCodigo(producto.getCodigo()));

	}
	
	
	@Test
	public void generarGarantiaConNombreCliente() {

		// arrange
		Producto producto = new ProductoTestDataBuilder().conNombre(COMPUTADOR_LENOVO).build();
		repositorioProducto.agregar(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
    	
		// act
		vendedor.generarGarantia(producto, NOMBRE_CLIENTE);
		GarantiaExtendida garantiaExtendida = repositorioGarantia.obtener(producto.getCodigo());

		// assert
		Assert.assertEquals(NOMBRE_CLIENTE, garantiaExtendida.getNombreCliente());
	}
	

	@Test
	public void productoYaTieneGarantiaTest() {

		// arrange
		Producto producto = new ProductoTestDataBuilder().conNombre(COMPUTADOR_LENOVO).build();
		
		repositorioProducto.agregar(producto);
		
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);

		
		try {
			// act
			vendedor.generarGarantia(producto, NOMBRE_CLIENTE);
			
		} catch (GarantiaExtendidaException e) {
			// assert
			Assert.assertEquals(Vendedor.EL_PRODUCTO_TIENE_GARANTIA, e.getMessage());
		}
	}
	
	
	@Test
	public void productoNoCuentaConGarantiaTest() {

		// arrange
		Producto producto = new ProductoTestDataBuilder().conNombre(COMPUTADOR_LENOVO).conCodigo(CODIGO_VOCALES).build();
		repositorioProducto.agregar(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);

		try {
			// act
			vendedor.generarGarantia(producto, NOMBRE_CLIENTE);
			
		} catch (GarantiaExtendidaException e) {
			// assert
			Assert.assertEquals(Vendedor.EL_PRODUCTO_NO_CUENTA_CON_GARANTIA, e.getMessage());
		}
	}
	
	@Test
	public void garantiaAlmacenaFechayPrecio() {

		// arrange
		Producto producto = new ProductoTestDataBuilder().conNombre(COMPUTADOR_LENOVO).build();
		repositorioProducto.agregar(producto);
		Vendedor vendedor = new Vendedor(repositorioProducto, repositorioGarantia);
    	
		// act
		vendedor.generarGarantia(producto, NOMBRE_CLIENTE);
		GarantiaExtendida garantiaExtendida = repositorioGarantia.obtener(producto.getCodigo());

		// assert
		assertNotNull(garantiaExtendida.getFechaFinGarantia());
		assertNotNull(garantiaExtendida.getPrecioGarantia());
	}
	
	
	
}
