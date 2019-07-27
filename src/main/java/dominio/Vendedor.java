package dominio;

import dominio.repositorio.RepositorioProducto;
import dominio.servicios.GarantiaExtendidaServicio;
import dominio.servicios.ProductoServicio;
import dominio.excepcion.GarantiaExtendidaException;
import dominio.repositorio.RepositorioGarantiaExtendida;

import java.util.Calendar;
import java.util.Date;

public class Vendedor {

    public static final String EL_PRODUCTO_TIENE_GARANTIA = "El producto ya cuenta con una garantia extendida";
    public static final String EL_PRODUCTO_NO_CUENTA_CON_GARANTIA = "Este producto no cuenta con garantía extendida";

    private RepositorioProducto repositorioProducto;
    private RepositorioGarantiaExtendida repositorioGarantia;

    public Vendedor(RepositorioProducto repositorioProducto, RepositorioGarantiaExtendida repositorioGarantia) {
        this.repositorioProducto = repositorioProducto;
        this.repositorioGarantia = repositorioGarantia;

    }

    public void generarGarantia(Producto producto, String nombreCliente) {
    	
    	ProductoServicio productoServicio = new ProductoServicio(repositorioProducto, repositorioGarantia);
    	GarantiaExtendidaServicio garantiaExtendidaServicio = new GarantiaExtendidaServicio(repositorioProducto, repositorioGarantia);
    	
    	//Se valida si el producto ya tiene una garantia extendida
    	if(productoServicio.tieneGarantiaExtendida(producto.getCodigo())) {
    		throw new GarantiaExtendidaException(EL_PRODUCTO_TIENE_GARANTIA);
    	}
    	
    	//Se valida si el producto puede tener una garantia extendida
    	if(!productoServicio.cuentaConGarantiaExtendida(producto.getCodigo())) {
    		throw new GarantiaExtendidaException(EL_PRODUCTO_NO_CUENTA_CON_GARANTIA);
    	}
    	
    	
    	//Se calcula el precio y la fecha de finalizacion de la garantia
    	double precioGarantia = garantiaExtendidaServicio.calcularPrecioGarantia(producto);
    	Date fechaSolicitudGarantia = new Date();
    	Date fechaFinalizacionGarantia = garantiaExtendidaServicio.calcularFechaExtendida(producto);
    	
    	
    	//Se crea la garantia extendida
    	GarantiaExtendida garantiaExtendida = new GarantiaExtendida(producto, fechaSolicitudGarantia, fechaFinalizacionGarantia, precioGarantia, nombreCliente);

    	//Se almacena en la base datos
    	repositorioGarantia.agregar(garantiaExtendida);

    }

}
