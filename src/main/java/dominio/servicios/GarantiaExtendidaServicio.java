package dominio.servicios;

import java.util.Calendar;
import java.util.Date;

import dominio.Producto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import dominio.repositorio.RepositorioProducto;

public class GarantiaExtendidaServicio {

	private RepositorioProducto repositorioProducto;
    private RepositorioGarantiaExtendida repositorioGarantia;
    private static final double PRECIO_LIMITE_GARANTIA_EXTENDIDA_10P = 500000.0;
    
	public GarantiaExtendidaServicio(RepositorioProducto repositorioProducto,
			RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;
	}
	
	public GarantiaExtendidaServicio() {
	}
	
	
	public double calcularPrecioGarantia(Producto producto) {
		
		double precioGarantia = 0.0;
		if(producto.getPrecio() > PRECIO_LIMITE_GARANTIA_EXTENDIDA_10P) {
    		//Se calcula el precio de la garantia
    		 precioGarantia = producto.getPrecio()*0.2;
    		
    	}else {
    		//Se calcula el precio de la garantia
    		precioGarantia = producto.getPrecio()*0.1;
    		
    	}
		return precioGarantia;
	}
	
	
	public Date calcularFechaExtendida(Producto producto) {
		
		Date fechaFinalizacionGarantia;
		Calendar calendario = Calendar.getInstance();
    	calendario.setTime(new Date());
    	int candidadLunes = 0;
    	
		if(producto.getPrecio() > PRECIO_LIMITE_GARANTIA_EXTENDIDA_10P) {

    		//Se le agregan la cantidad de dias de la garantia
    		for(int i = 1; i < 200; ++i) {
    			calendario.add(Calendar.DATE, 1);
    			
    			//Se valida si es lunes
    			if(calendario.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
    				candidadLunes++;
    		}
    		
    		//Se resta la cantidad de lunes
    		calendario.add(Calendar.DATE, -candidadLunes);
    		
    	}else {
    		
    		//Se le agregan la cantidad de dias de la garantia
    		for(int i = 1; i < 100; ++i) {
    			calendario.add(Calendar.DATE, 1);
    			
    			//Se valida si es lunes
    			if(calendario.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY)
    				candidadLunes++;
    		}
    		
    		//Se resta la cantidad de lunes
    		calendario.add(Calendar.DATE, -candidadLunes);
    	}
    	
		//Se valida si el dia de finalizacion de la garantia finaliza un domingo
    	if(calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
    		//Si es domingo se agrega un dia más
    		calendario.add(Calendar.DATE, 1);
    	}
		
    	fechaFinalizacionGarantia = calendario.getTime();
    	return fechaFinalizacionGarantia;
	}
	
	
	
}
