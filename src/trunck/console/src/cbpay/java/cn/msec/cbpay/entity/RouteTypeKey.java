package cn.msec.cbpay.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class RouteTypeKey {
	private String merchantid;
    private String routetype;
	public String getMerchantid() {
		return merchantid;
	}
	public void setMerchantid(String merchantid) {
		this.merchantid = merchantid;
	}
	public String getRoutetype() {
		return routetype;
	}
	public void setRoutetype(String routetype) {
		this.routetype = routetype;
	}
	@Override
	public String toString() {
		return "RouteTypeKey [merchantid=" + merchantid + ", routetype="
				+ routetype + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((merchantid == null) ? 0 : merchantid.hashCode());
		result = prime * result
				+ ((routetype == null) ? 0 : routetype.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RouteTypeKey other = (RouteTypeKey) obj;
		if (merchantid == null) {
			if (other.merchantid != null)
				return false;
		} else if (!merchantid.equals(other.merchantid))
			return false;
		if (routetype == null) {
			if (other.routetype != null)
				return false;
		} else if (!routetype.equals(other.routetype))
			return false;
		return true;
	}
	
    
}