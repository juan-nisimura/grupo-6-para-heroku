package dominio.entidad;

import java.util.Comparator;
import java.util.List;

import dominio.presupuestos.Presupuesto;

public enum TipoEmpresa{
	MICRO{
		public String toString() {
			return "Micro";
		}
	},
	PEQUENA{
		public String toString() {
			return "Pequena";
		}
	}, 
	MEDIANA_1{
		public String toString() {
			return "Mediana 1";
		}
	}, 
	MEDIANA_2{
		public String toString() {
			return "Mediana 2";
		}
	},
}