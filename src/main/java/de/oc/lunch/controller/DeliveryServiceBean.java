package de.oc.lunch.controller;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;

import de.oc.lunch.persistence.DeliveryServiceEntity;

@RequestScoped
//@ViewScoped
@ManagedBean
public class DeliveryServiceBean implements Serializable{
	private static final long serialVersionUID = 1L;
	private List<DeliveryServiceEntity> deliveryServices;

	@PostConstruct
	public void init() {
		deliveryServices = new DeliveryServiceEntity().findAll();
	}

	public List<DeliveryServiceEntity> getDeliveryServices() {
		return deliveryServices;
	}

	public void setDeliveryServices(List<DeliveryServiceEntity> deliveryServices) {
		this.deliveryServices = deliveryServices;
	}


}
