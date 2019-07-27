package dominio.servicios;

import dominio.GarantiaExtendida;
import dominio.Producto;
import dominio.repositorio.RepositorioGarantiaExtendida;
import dominio.repositorio.RepositorioProducto;

public class ProductoServicio {

	private RepositorioProducto repositorioProducto;
    private RepositorioGarantiaExtendida repositorioGarantia;
    
    
	public ProductoServicio(RepositorioProducto repositorioProducto,
			RepositorioGarantiaExtendida repositorioGarantia) {
		this.repositorioProducto = repositorioProducto;
		this.repositorioGarantia = repositorioGarantia;
	}
    
    
	public boolean tieneGarantiaExtendida(String codigo) {
		Producto producto = this.repositorioGarantia.obtenerProductoConGarantiaPorCodigo(codigo);
		if(producto != null) {
			return true;
		}else {
			return false;
		}
    }
	
	public boolean cuentaConGarantiaExtendida(String codigo) {
		String codigoFormato = codigo.toLowerCase();
    	int cantidadVocales = 0;
    	for(int i = 0; i < codigoFormato.length(); ++i) {
    		char caracter = codigoFormato.charAt(i);
    		if(caracter == 'a' || caracter == 'e' || caracter == 'i'  || caracter == 'o' || caracter == 'u') {
                    ++cantidadVocales;
                }
    	}
    	
    	//Si es mayor a 3 genera una excepcion
    	if(cantidadVocales >= 3) {
    		return false;
    	}else {
    		return true;
    	}
	}
	
	
}
