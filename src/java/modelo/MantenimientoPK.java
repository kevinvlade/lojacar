/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author kevin
 */
@Embeddable
public class MantenimientoPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "vehiculo_idvehiculo")
    private int vehiculoIdvehiculo;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cliente_idcliente")
    private int clienteIdcliente;

    public MantenimientoPK() {
    }

    public MantenimientoPK(int vehiculoIdvehiculo, int clienteIdcliente) {
        this.vehiculoIdvehiculo = vehiculoIdvehiculo;
        this.clienteIdcliente = clienteIdcliente;
    }

    public int getVehiculoIdvehiculo() {
        return vehiculoIdvehiculo;
    }

    public void setVehiculoIdvehiculo(int vehiculoIdvehiculo) {
        this.vehiculoIdvehiculo = vehiculoIdvehiculo;
    }

    public int getClienteIdcliente() {
        return clienteIdcliente;
    }

    public void setClienteIdcliente(int clienteIdcliente) {
        this.clienteIdcliente = clienteIdcliente;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) vehiculoIdvehiculo;
        hash += (int) clienteIdcliente;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MantenimientoPK)) {
            return false;
        }
        MantenimientoPK other = (MantenimientoPK) object;
        if (this.vehiculoIdvehiculo != other.vehiculoIdvehiculo) {
            return false;
        }
        if (this.clienteIdcliente != other.clienteIdcliente) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "modelo.MantenimientoPK[ vehiculoIdvehiculo=" + vehiculoIdvehiculo + ", clienteIdcliente=" + clienteIdcliente + " ]";
    }
    
}
