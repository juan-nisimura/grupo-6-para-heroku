package dominio.compra;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class DocumentoComercial {

	@Id
	@GeneratedValue
	private Long id;
	
	private int numero_documento;
	private TipoDocumentoComercial tipoDocumentoComercial;
	
	public void setNumDocumento(int num_documento){
		this.numero_documento = num_documento;
	}
	
	public void setTipoDocumento(Long tipo_doc){
		this.tipoDocumentoComercial = null;
		
    	if(tipo_doc == 0)
    	this.tipoDocumentoComercial = TipoDocumentoComercial.SIN_DOCUMENTO;
    	if(tipo_doc == 1) 
    	this.tipoDocumentoComercial = TipoDocumentoComercial.FACTURA;
    	if(tipo_doc == 2)
    	this.tipoDocumentoComercial = TipoDocumentoComercial.TICKET;
	}
	
    public long getId(){
    	  return this.id;
      }
}
