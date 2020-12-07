package dominio.presupuestos;

import java.util.*;

public enum CriterioDeSeleccionPresupuesto {
	ElUsuarioOlvidoElegirCriterio {//TODO borrar
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
    		return true;
    	}
    },
	SinCriterioDeSeleccion {
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
    		return true;
    	}
    }, 
	PresupuestoMasBarato {
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
        	return presupuestos.stream().min(Comparator.comparing(Presupuesto::getTotal))
        			.get().equals(presupuestoSeleccionado);
        }
    },
	PresupuestoMasCaro {//TODO borrar
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
    		return true;
    	}
    },
	LoDejoASuCriterio {//TODO borrar
        @Override
        public boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado) {
    		return true;
    	}
    };

	public abstract boolean verificar(List<Presupuesto> presupuestos, Presupuesto presupuestoSeleccionado);
}