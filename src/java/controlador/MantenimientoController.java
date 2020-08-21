package controlador;

import modelo.Mantenimiento;
import controlador.util.JsfUtil;
import controlador.util.JsfUtil.PersistAction;
import bean.MantenimientoFacade;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

@ManagedBean(name = "mantenimientoController")
@SessionScoped
public class MantenimientoController implements Serializable {

    @EJB
    private bean.MantenimientoFacade ejbFacade;
    private List<Mantenimiento> items = null;
    private Mantenimiento selected;

    public MantenimientoController() {
    }

    public Mantenimiento getSelected() {
        return selected;
    }

    public void setSelected(Mantenimiento selected) {
        this.selected = selected;
    }

    protected void setEmbeddableKeys() {
        selected.getMantenimientoPK().setVehiculoIdvehiculo(selected.getVehiculo().getIdvehiculo());
        selected.getMantenimientoPK().setClienteIdcliente(selected.getCliente().getIdcliente());
    }

    protected void initializeEmbeddableKey() {
        selected.setMantenimientoPK(new modelo.MantenimientoPK());
    }

    private MantenimientoFacade getFacade() {
        return ejbFacade;
    }

    public Mantenimiento prepareCreate() {
        selected = new Mantenimiento();
        initializeEmbeddableKey();
        return selected;
    }

    public void create() {
        persist(PersistAction.CREATE, ResourceBundle.getBundle("/Bundle").getString("MantenimientoCreated"));
        if (!JsfUtil.isValidationFailed()) {
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public void update() {
        persist(PersistAction.UPDATE, ResourceBundle.getBundle("/Bundle").getString("MantenimientoUpdated"));
    }

    public void destroy() {
        persist(PersistAction.DELETE, ResourceBundle.getBundle("/Bundle").getString("MantenimientoDeleted"));
        if (!JsfUtil.isValidationFailed()) {
            selected = null; // Remove selection
            items = null;    // Invalidate list of items to trigger re-query.
        }
    }

    public List<Mantenimiento> getItems() {
        if (items == null) {
            items = getFacade().findAll();
        }
        return items;
    }

    private void persist(PersistAction persistAction, String successMessage) {
        if (selected != null) {
            setEmbeddableKeys();
            try {
                if (persistAction != PersistAction.DELETE) {
                    getFacade().edit(selected);
                } else {
                    getFacade().remove(selected);
                }
                JsfUtil.addSuccessMessage(successMessage);
            } catch (EJBException ex) {
                String msg = "";
                Throwable cause = ex.getCause();
                if (cause != null) {
                    msg = cause.getLocalizedMessage();
                }
                if (msg.length() > 0) {
                    JsfUtil.addErrorMessage(msg);
                } else {
                    JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
                }
            } catch (Exception ex) {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
                JsfUtil.addErrorMessage(ex, ResourceBundle.getBundle("/Bundle").getString("PersistenceErrorOccured"));
            }
        }
    }

    public List<Mantenimiento> getItemsAvailableSelectMany() {
        return getFacade().findAll();
    }

    public List<Mantenimiento> getItemsAvailableSelectOne() {
        return getFacade().findAll();
    }

    @FacesConverter(forClass = Mantenimiento.class)
    public static class MantenimientoControllerConverter implements Converter {

        private static final String SEPARATOR = "#";
        private static final String SEPARATOR_ESCAPED = "\\#";

        @Override
        public Object getAsObject(FacesContext facesContext, UIComponent component, String value) {
            if (value == null || value.length() == 0) {
                return null;
            }
            MantenimientoController controller = (MantenimientoController) facesContext.getApplication().getELResolver().
                    getValue(facesContext.getELContext(), null, "mantenimientoController");
            return controller.getFacade().find(getKey(value));
        }

        modelo.MantenimientoPK getKey(String value) {
            modelo.MantenimientoPK key;
            String values[] = value.split(SEPARATOR_ESCAPED);
            key = new modelo.MantenimientoPK();
            key.setVehiculoIdvehiculo(Integer.parseInt(values[0]));
            key.setClienteIdcliente(Integer.parseInt(values[1]));
            return key;
        }

        String getStringKey(modelo.MantenimientoPK value) {
            StringBuilder sb = new StringBuilder();
            sb.append(value.getVehiculoIdvehiculo());
            sb.append(SEPARATOR);
            sb.append(value.getClienteIdcliente());
            return sb.toString();
        }

        @Override
        public String getAsString(FacesContext facesContext, UIComponent component, Object object) {
            if (object == null) {
                return null;
            }
            if (object instanceof Mantenimiento) {
                Mantenimiento o = (Mantenimiento) object;
                return getStringKey(o.getMantenimientoPK());
            } else {
                Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, "object {0} is of type {1}; expected type: {2}", new Object[]{object, object.getClass().getName(), Mantenimiento.class.getName()});
                return null;
            }
        }

    }

}
