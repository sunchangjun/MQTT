package framework.entity.po.auth;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "t_auth_resource_authority")
public class ResourceAuthority implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	@Column(name = "resource_authority_id", unique = true, nullable = false)
	private Long resourceAuthorityId;

	@Column(name = "authority_id", insertable = false, updatable = false)
	private Long authorityId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "authority_id")
	private Authority authority;

	@Column(name = "resource_id", insertable = false, updatable = false)
	private Long resourceId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "resource_id")
	private Resource resource;

	public Long getResourceAuthorityId() {
		return resourceAuthorityId;
	}

	public void setResourceAuthorityId(Long resourceAuthorityId) {
		this.resourceAuthorityId = resourceAuthorityId;
	}

	public Long getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(Long authorityId) {
		this.authorityId = authorityId;
	}

	public Authority getAuthority() {
		return authority;
	}

	public void setAuthority(Authority authority) {
		this.authority = authority;
	}

	public Long getResourceId() {
		return resourceId;
	}

	public void setResourceId(Long resourceId) {
		this.resourceId = resourceId;
	}

	public Resource getResource() {
		return resource;
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

}