package cn.msec.cbpay.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import cn.msec.ojpa.api.annotations.Tab;

@Tab(name="ROUT_TYPE")
@AllArgsConstructor
@NoArgsConstructor
public class RouteType extends RouteTypeKey {
   private String routefield;
   private String valid;
	
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((routefield == null) ? 0 : routefield.hashCode());
		result = prime * result + ((valid == null) ? 0 : valid.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		RouteType other = (RouteType) obj;
		if (routefield == null) {
			if (other.routefield != null)
				return false;
		} else if (!routefield.equals(other.routefield))
			return false;
		if (valid == null) {
			if (other.valid != null)
				return false;
		} else if (!valid.equals(other.valid))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RouteType [routeField=" + routefield + ", valid=" + valid + "]";
	}
	public String getRoutefield() {
		return routefield;
	}
	public void setRoutefield(String routefield) {
		this.routefield = routefield;
	}
	
   
}