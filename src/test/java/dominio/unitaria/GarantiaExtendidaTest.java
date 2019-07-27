package dominio.unitaria;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import dominio.Producto;
import dominio.servicios.GarantiaExtendidaServicio;
import testdatabuilder.ProductoTestDataBuilder;

public class GarantiaExtendidaTest {
	
	
	private static final double PRECIO_PRODUCTO_MAYOR = 1000000.0; 
	private static final double PRECIO_PRODUCTO_MENOR = 400000.0;

	@Test
	public void calcularPrecioGarantiaMayor500() {
		
		// arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoTestDataBuilder.conPrecio(PRECIO_PRODUCTO_MAYOR).build(); 
	
		GarantiaExtendidaServicio garantiaExtendidaServicio = new GarantiaExtendidaServicio();
		
		double precioGarantiaEsperada = PRECIO_PRODUCTO_MAYOR * 0.2;
		double precioGarantiaCalculada = garantiaExtendidaServicio.calcularPrecioGarantia(producto);
		
		//assert
		assertEquals(precioGarantiaEsperada,precioGarantiaCalculada, 2);
	}
	
	@Test
	public void calcularPrecioGarantiaMenor500() {
		
		// arrange
		ProductoTestDataBuilder productoTestDataBuilder = new ProductoTestDataBuilder();
		Producto producto = productoTestDataBuilder.conPrecio(PRECIO_PRODUCTO_MENOR).build(); 
			
		GarantiaExtendidaServicio garantiaExtendidaServicio = new GarantiaExtendidaServicio();
		
		double precioGarantiaEsperada = PRECIO_PRODUCTO_MENOR * 0.1;
		double precioGarantiaCalculada = garantiaExtendidaServicio.calcularPrecioGarantia(producto);
		
		//assert
		assertEquals(precioGarantiaEsperada,precioGarantiaCalculada, 2);
	}


}
